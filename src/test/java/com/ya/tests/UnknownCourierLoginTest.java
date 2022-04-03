package com.ya.tests;

import com.ya.model.Courier;
import com.ya.client.CourierClient;
import com.ya.utils.CourierGenerator;
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
        courierClient.createWithCredits(courier);
        ValidatableResponse loginResponse = courierClient.login(courier);
        courierId = loginResponse.extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier tries to login with non-existent data")
    public void unknownCourierLoginTest() {
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier success login", statusCode, equalTo(404));
        assertThat("Unknown error message ", errorMessage, equalTo("Учетная запись не найдена"));
    }
}