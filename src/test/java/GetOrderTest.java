import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateOrderRequest;
import model.GetOrderResponse;
import model.OrderErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static clients.OrderApiClient.createOrderRequest;
import static clients.OrderApiClient.getUserOrdersListRequest;
import static helpers.OrderHelper.getOrderList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetOrderTest extends BaseTest {

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Должен вернуться статус код '200', а в теле сообщения информация о заказах")
    public void authUserGetOrderTest() {
        List<String> orderList = getOrderList();
        createOrderRequest(authToken, new CreateOrderRequest(orderList));
        Response getOrderRequest = getUserOrdersListRequest(authToken);
        GetOrderResponse orderResponse = getOrderRequest.then().statusCode(SC_OK).assertThat().body("success", equalTo(true)).log().all().and().extract().as(GetOrderResponse.class);
        Assert.assertNotNull(orderResponse.getOrders());
        Assert.assertNotNull(orderResponse.getTotalToday());
        Assert.assertNotNull(orderResponse.getTotal());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Должен вернуться статус код '401' а в теле ошибка 'You should be authorised'")
    public void notAuthUserGetOrderTest() {
        Response getOrderRequest = getUserOrdersListRequest("");
        OrderErrorResponse errorResponse = getOrderRequest.then().statusCode(SC_UNAUTHORIZED).assertThat().body("success", equalTo(false)).log().all().and().extract().as(OrderErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "You should be authorised", errorResponse.getMessage());
    }

}
