import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginUserRequest;
import model.UserErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import static clients.UserApiClient.loginUserRequest;
import static helpers.UserHelper.userDeserialization;
import static helpers.UserHelper.userUnauthorizedErrorDeserialization;

public class LoginUserTest extends BaseTest {

    @DisplayName("Логин под существующим пользователем")
    @Description("Должен вернуться код '200', а в теле сообщения 'success:true'")
    @Test
    public void LoginTest() {
        Response loginResponse = loginUserRequest(new LoginUserRequest(data.getEmail(), data.getPassword()));
        userDeserialization(loginResponse);
    }

    @DisplayName("Логин с неверным логином и паролем")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongEmailAndPasswordTest() {
        Response loginResponse = loginUserRequest(new LoginUserRequest(faker.internet().emailAddress(), faker.internet().password()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
    }

    @DisplayName("Логин с неверным логином")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongLoginTest() {
        Response loginResponse = loginUserRequest(new LoginUserRequest(faker.internet().emailAddress(), data.getPassword()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
    }

    @DisplayName("Логин с неверным паролем")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongPasswordTest() {
        Response loginResponse = loginUserRequest(new LoginUserRequest(data.getEmail(), faker.internet().password()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
    }

}
