package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;



import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {
    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Login with crenedtials")
    public ValidatableResponse login(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body("{\"login\":\"" + courier.getLogin() + "\","
                        + "\"password\":\"" + courier.getPassword() + "\"}")
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Create courier")
    public ValidatableResponse CreateWithCredits(Courier courier) {

        String bodyString = "{\"login\":\"" + courier.getLogin() + "\","
                + "\"password\":\"" + courier.getPassword() + "\","
                + "\"firstName\":\"" + courier.getFirstName() + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(bodyString)
                .when()
                .post(COURIER_PATH)
                .then();
    }

  @Step("Delete courier")
    public ValidatableResponse delete (int courierId) {
      return given()
              .spec(getBaseSpec())
              .body("{\"id\":\"" + courierId + "\"}")
              .when()
              .delete(COURIER_PATH + courierId)
              .then();
  }


    @Step("Create orders")
    public ValidatableResponse CreateOrders(String color) {
        return given()
                .spec(getBaseSpec())
                .body("{\"firstName\":\"Naruto\",\"lastName\":\"Uchiha\",\"address\":\"Konoha, 142 apt.\",\"metroStation\":4,\"phone\":\"+7 800 355 35 35\",\"rentTime\":5,\"deliveryDate\":\"2020-06-06\",\"comment\":\"Saske, come back to Konoha\",\"color\":["+ color +"]}")
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get Orders")
    public ValidatableResponse getOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}
