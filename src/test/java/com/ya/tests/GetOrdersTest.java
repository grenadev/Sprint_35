package com.ya.tests;

import com.ya.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {

    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Get orders list")
    public void getOrderTest() {
        ValidatableResponse loginResponse = courierClient.getOrders();
        List<ArrayList> idList = loginResponse.extract().body().path("orders.id");

        assertThat("No order in list", idList.isEmpty(), equalTo(false));
    }
}
