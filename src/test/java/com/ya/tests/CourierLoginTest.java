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

public class CourierLoginTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierGenerator generator;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = generator.generateCourierData();
        courierClient.CreateWithCredits(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Сourier tries to login with invalid credentials")
    public void courierCantLoginWithNonValidCredentials() {
        courier.setPassword("test");
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier cannot login", statusCode, equalTo(404));
        assertThat("Unknown error message ", errorMessage , equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Courier can login with valid credentials")
    public void courierCanLoginWithValidCredentials() {
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");

        assertThat("Courier cannot login", statusCode, equalTo(200));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }



    @Test
    @DisplayName("Courier cant login without login")
    public void courierCantLoginWithOutLogin() {
        courier.setLogin("");
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier success login without login", statusCode, equalTo(400));
        assertThat("Unknown error message ", errorMessage , equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Courier cant login without password")
    public void courierCantLoginWithOutPassword() {
        courier.setPassword("");
        ValidatableResponse loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().body().path("message");

        assertThat("Courier success login without login", statusCode, equalTo(400));
        assertThat("Unknown error message ", errorMessage , equalTo("Недостаточно данных для входа"));

    }

}
