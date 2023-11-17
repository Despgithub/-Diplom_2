import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateUserRequest;
import model.LoginUserRequest;
import model.UserErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import static clients.UserApiClient.*;
import static helpers.DataGenerator.getUser;
import static helpers.UserHelper.userDeserialization;
import static helpers.UserHelper.userUnauthorizedErrorDeserialization;

public class LoginUserTest {

    CreateUserRequest data;
    Response response;
    Faker faker;

    @DisplayName("Логин под существующим пользователем")
    @Description("Должен вернуться код '200', а в теле сообщения 'success:true'")
    @Test
    public void LoginTest() {
        data = getUser();
        response = createUserRequest(data);
        Response loginResponse = loginUserRequest(new LoginUserRequest(data.getEmail(), data.getPassword()));
        userDeserialization(loginResponse);
        deleteUserRequest(getAuthToken(response));
    }

    @DisplayName("Логин с неверным логином и паролем")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongEmailAndPasswordTest() {
        faker = new Faker();
        Response loginResponse = loginUserRequest(new LoginUserRequest(faker.internet().emailAddress(), faker.internet().password()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
    }

    @DisplayName("Логин с неверным логином")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongLoginTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        Response loginResponse = loginUserRequest(new LoginUserRequest(faker.internet().emailAddress(), data.getPassword()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
        deleteUserRequest(getAuthToken(response));
    }

    @DisplayName("Логин с неверным паролем")
    @Description("Должен вернуться код '401', а в теле сообщения 'success:false' и ошибка 'email or password are incorrect'")
    @Test
    public void LoginWithWrongPasswordTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        Response loginResponse = loginUserRequest(new LoginUserRequest(data.getEmail(), faker.internet().password()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(loginResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "email or password are incorrect");
        deleteUserRequest(getAuthToken(response));
    }

}
