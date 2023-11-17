package clients;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import model.CreateOrderRequest;

import static config.ConfigApp.GET_ALL_ORDERS_URL;
import static config.ConfigApp.GET_USER_ORDERS_URL;
import static io.restassured.RestAssured.given;

public class OrderApiClient extends BaseApiClient {
    @Step("Создание заказа")
    public static Response createOrderRequest(String accessToken, CreateOrderRequest createOrderRequest) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body(createOrderRequest)
                .when()
                .filter(new AllureRestAssured())
                .post(GET_USER_ORDERS_URL);
    }

    @Step("Получение списка заказов клиента")
    public static Response getUserOrdersListRequest(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .filter(new AllureRestAssured())
                .get(GET_USER_ORDERS_URL);
    }

    @Step("Получение списка всех заказов")
    public static Response getAllOrdersListRequest(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .filter(new AllureRestAssured())
                .get(GET_ALL_ORDERS_URL);
    }
}
