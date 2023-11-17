import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import model.CreateUserRequest;
import model.OrderErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static clients.OrderApiClient.createOrderRequest;
import static clients.UserApiClient.*;
import static helpers.DataGenerator.getUser;
import static helpers.OrderHelper.*;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;


public class CreateOrderTest {

    CreateUserRequest data;
    Response response;

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя с ингредиентами")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказе")
    public void authUserOrderWithIngredientsTest() {
        data = getUser();
        response = createUserRequest(data);
        List<String> orderList = getOrderList();
        String authToken = getAuthToken(response);
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest(orderList));
        CreateOrderResponse orderResponse = orderDeserialization(orderRequest);
        Assert.assertNotNull(orderResponse.getName());
        Assert.assertNotNull(orderResponse.getOrder());
        deleteUserRequest(authToken);
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя с ингредиентами")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказе")
    public void notAuthUserOrderWithIngredientsTest() {
        List<String> orderList = getOrderList();
        Response orderRequest = createOrderRequest("", new CreateOrderRequest(orderList));
        CreateOrderResponse orderResponse = orderDeserialization(orderRequest);
        Assert.assertNotNull(orderResponse.getName());
        Assert.assertNotNull(orderResponse.getOrder());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя без ингредиентов")
    @Description("Должен вернуться статус код '400', а в теле ошибка 'Ingredient ids must be provided'")
    public void authUserOrderWithoutIngredientsTest() {
        data = getUser();
        response = createUserRequest(data);
        String authToken = getAuthToken(response);
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest());
        OrderErrorResponse errorResponse = orderBadRequestErrorDeserialization(orderRequest);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Ingredient ids must be provided");
        deleteUserRequest(authToken);
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя и без ингредиентов")
    @Description("Должен вернуться статус код '400', а в теле ошибка 'Ingredient ids must be provided'")
    public void notAuthUserOrderWithoutIngredientsTest() {
        Response orderRequest = createOrderRequest("", new CreateOrderRequest());
        OrderErrorResponse errorResponse = orderBadRequestErrorDeserialization(orderRequest);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя и неверным хешем ингредиентов")
    @Description("Должен вернуться статус код '500'")
    public void authUserOrderWithErrorIngredientsTest() {
        data = getUser();
        response = createUserRequest(data);
        List<String> orderList = getErrorOrderList();
        String authToken = getAuthToken(response);
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest(orderList));
        orderRequest.then().statusCode(SC_INTERNAL_SERVER_ERROR);
        deleteUserRequest(authToken);
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя и неверным хешем ингредиентов")
    @Description("Должен вернуться статус код '500'")
    public void notAuthUserOrderWithErrorIngredientsTest() {
        List<String> orderList = getErrorOrderList();
        Response orderRequest = createOrderRequest("", new CreateOrderRequest(orderList));
        orderRequest.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

}
