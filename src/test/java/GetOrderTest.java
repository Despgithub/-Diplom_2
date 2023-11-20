import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateOrderRequest;
import model.CreateUserRequest;
import model.GetOrderResponse;
import model.OrderErrorResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static clients.OrderApiClient.createOrderRequest;
import static clients.OrderApiClient.getUserOrdersListRequest;
import static clients.UserApiClient.*;
import static helpers.DataGenerator.getUser;
import static helpers.OrderHelper.*;

public class GetOrderTest extends BaseTest{

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказах")
    public void authUserGetOrderTest() {
        List<String> orderList = getOrderList();
        createOrderRequest(authToken, new CreateOrderRequest(orderList));
        Response getOrderRequest = getUserOrdersListRequest(authToken);
        GetOrderResponse orderResponse = getorderDeserialization(getOrderRequest);
        Assert.assertNotNull(orderResponse.getOrders());
        Assert.assertNotNull(orderResponse.getTotalToday());
        Assert.assertNotNull(orderResponse.getTotal());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Должен вернуться статус код '401' а в теле ошибка 'You should be authorised'")
    public void notAuthUserGetOrderTest() {
        Response getOrderRequest = getUserOrdersListRequest("");
        OrderErrorResponse errorResponse = getorderUnauthorizedErrorDeserialization(getOrderRequest);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "You should be authorised");
    }

}
