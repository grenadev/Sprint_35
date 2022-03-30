package com.ya.tests;

import com.ya.Courier;
import com.ya.CourierClient;
import com.ya.generator.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UnknownCourierLoginTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierGenerator generator;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = generator.generateCourierData();
        courierClient.CreateWithCredits(courier);
        ValidatableResponse loginResponse = courierClient.login(courier);
        courierId = loginResponse.extract().path("id");
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Courier tries to login with non-existent data")
    public void UnknownCourierLoginTest() {
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier success login", statusCode, equalTo(404));
        assertThat("Unknown error message ", errorMessage , equalTo("Учетная запись не найдена"));
    }
}