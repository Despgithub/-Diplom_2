import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import model.OrderErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static clients.OrderApiClient.createOrderRequest;
import static helpers.OrderHelper.getErrorOrderList;
import static helpers.OrderHelper.getOrderList;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.IsEqual.equalTo;


public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя с ингредиентами")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказе")
    public void authUserOrderWithIngredientsTest() {
        List<String> orderList = getOrderList();
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest(orderList));
        CreateOrderResponse orderResponse = orderRequest.then().statusCode(SC_OK).assertThat().body("success", equalTo(true)).log().all().and().extract().as(CreateOrderResponse.class);
        Assert.assertNotNull(orderResponse.getName());
        Assert.assertNotNull(orderResponse.getOrder());
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя с ингредиентами")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказе")
    public void notAuthUserOrderWithIngredientsTest() {
        List<String> orderList = getOrderList();
        Response orderRequest = createOrderRequest("", new CreateOrderRequest(orderList));
        CreateOrderResponse orderResponse = orderRequest.then().statusCode(SC_OK).assertThat().body("success", equalTo(true)).log().all().and().extract().as(CreateOrderResponse.class);
        Assert.assertNotNull(orderResponse.getName());
        Assert.assertNotNull(orderResponse.getOrder());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя без ингредиентов")
    @Description("Должен вернуться статус код '400', а в теле ошибка 'Ingredient ids must be provided'")
    public void authUserOrderWithoutIngredientsTest() {
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest());
        OrderErrorResponse errorResponse = orderRequest.then().statusCode(SC_BAD_REQUEST).assertThat().body("success", equalTo(false)).log().all().and().extract().as(OrderErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Ingredient ids must be provided", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя и без ингредиентов")
    @Description("Должен вернуться статус код '400', а в теле ошибка 'Ingredient ids must be provided'")
    public void notAuthUserOrderWithoutIngredientsTest() {
        Response orderRequest = createOrderRequest("", new CreateOrderRequest());
        OrderErrorResponse errorResponse = orderRequest.then().statusCode(SC_BAD_REQUEST).assertThat().body("success", equalTo(false)).log().all().and().extract().as(OrderErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Ingredient ids must be provided", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя и неверным хешем ингредиентов")
    @Description("Должен вернуться статус код '500'")
    public void authUserOrderWithErrorIngredientsTest() {
        List<String> orderList = getErrorOrderList();
        Response orderRequest = createOrderRequest(authToken, new CreateOrderRequest(orderList));
        orderRequest.then().statusCode(SC_INTERNAL_SERVER_ERROR);
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
