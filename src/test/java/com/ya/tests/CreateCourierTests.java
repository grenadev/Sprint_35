package com.ya.tests;

import com.ya.Courier;
import com.ya.CourierClient;
import com.ya.generator.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class CreateCourierTests {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierGenerator generator;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @After
    public void tearDown() {
        ValidatableResponse loginResponseForDelete = courierClient.login(courier);
        courierId = loginResponseForDelete.extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    public void createCourierWithRandomData() {
        courier = generator.generateCourierData();
        ValidatableResponse loginResponse = courierClient.CreateWithCredits(courier);

        int statusCode = loginResponse.extract().statusCode();
        boolean createdStatus = loginResponse.extract().body().path("ok");


        assertThat("Courier cannot login", statusCode, equalTo(201));
        assertThat("Unknown error message ", createdStatus , equalTo(true));
    }

    @Test
    @DisplayName("Create courier with same data")
    public void createCourierWithSameData() {
        courier =  generator.generateCourierData();
        courierClient.CreateWithCredits(courier);
        ValidatableResponse loginResponse = courierClient.CreateWithCredits(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier cannot login", statusCode, equalTo(409));
        assertThat("Unknown error message ", errorMessage , equalTo("Этот логин уже используется"));

    }

}
