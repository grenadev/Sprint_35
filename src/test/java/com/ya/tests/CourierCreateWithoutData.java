package com.ya.tests;

import com.ya.Courier;
import com.ya.CourierClient;
import com.ya.generator.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateWithoutData {

    CourierGenerator generator;
    CourierClient courierClient;
    Courier courier;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    @DisplayName("Create courier without login")
    public void createCourierWithoutLogin() {
        Courier courier = generator.generateCourierData();
        courier.setLogin("");
        ValidatableResponse loginResponse = courierClient.CreateWithCredits(courier);

        int statusCode = loginResponse.extract().statusCode();
        String createdError = loginResponse.extract().body().path("message");


        assertThat("Courier create without login", statusCode, equalTo(400));
        assertThat("Unknown error message ", createdError , equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without firstname")
    public void createCourierWithoutFirstsName() {
        courier = generator.generateCourierData();
        courier.setFirstName("");
        ValidatableResponse loginResponse = courierClient.CreateWithCredits(courier);

        int statusCode = loginResponse.extract().statusCode();
        String createdError = loginResponse.extract().body().path("message");

        assertThat("Courier create without FirstName", statusCode, equalTo(400));
        assertThat("Unknown error message ", createdError , equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    public void createCourierWithoutPassword() {
        courier = generator.generateCourierData();
        courier.setPassword("");
        ValidatableResponse loginResponse = courierClient.CreateWithCredits(courier);

        int statusCode = loginResponse.extract().statusCode();
        String createdError = loginResponse.extract().body().path("message");

        assertThat("Courier create without password", statusCode, equalTo(400));
        assertThat("Unknown error message ", createdError , equalTo("Недостаточно данных для создания учетной записи"));
    }
}
