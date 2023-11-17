import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateUserRequest;
import model.UpdateUserRequest;
import model.UserErrorResponse;
import model.UserResponse;
import org.junit.Assert;
import org.junit.Test;

import static clients.UserApiClient.*;
import static helpers.DataGenerator.getUser;
import static helpers.UserHelper.userDeserialization;
import static helpers.UserHelper.userUnauthorizedErrorDeserialization;

public class ChangeUserTest {

    CreateUserRequest data;
    Response response;
    Faker faker;

    @Test
    @DisplayName("Обновление всех данных авторизованного пользователя")
    @Description("Должен вернуться статус код '200', а в теле сообщения обновленные данные пользователя")
    public void updateUserDataTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        String authToken = getAuthToken(response);
        Response updateResponse = UpdateDataUserRequest(authToken, new UpdateUserRequest(faker.internet().emailAddress(), faker.name().firstName()));
        UserResponse userResponse = userDeserialization(updateResponse);
        Assert.assertNotEquals("Email не изменился", data.getEmail(), userResponse.getUser().getEmail());
        Assert.assertNotEquals("Имя не изменилось", userResponse.getUser().getName());
        deleteUserRequest(authToken);
    }

    @Test
    @DisplayName("Обновление всех данных неавторизованного пользователя")
    @Description("Должен вернуться статус код '401', а в теле сообщения 'success:false' и ошибка 'You should be authorised'")
    public void updateNoAuthorizeUserDataTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        Response updateResponse = UpdateDataUserRequest("", new UpdateUserRequest(faker.internet().emailAddress(), faker.name().firstName()));
        UserErrorResponse errorResponse = userUnauthorizedErrorDeserialization(updateResponse);
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "You should be authorised");
        deleteUserRequest(getAuthToken(response));
    }

    @Test
    @DisplayName("Обновление почты пользователя")
    @Description("Должен вернуться статус код '200', а в теле сообщения обновленные данные пользователя")
    public void updateUserEmailTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        String authToken = getAuthToken(response);
        String email = faker.internet().emailAddress();
        Response updateResponse = UpdateDataUserRequest(authToken, new UpdateUserRequest(email, data.getName()));
        UserResponse userResponse = userDeserialization(updateResponse);
        Assert.assertEquals("Email не изменился", email, userResponse.getUser().getEmail());
        deleteUserRequest(authToken);
    }

    @Test
    @DisplayName("Обновление имени пользователя")
    @Description("Должен вернуться статус код '200', а в теле сообщения обновленные данные пользователя")
    public void updateUserNameTest() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        String authToken = getAuthToken(response);
        String name = faker.name().firstName();
        Response updateResponse = UpdateDataUserRequest(authToken, new UpdateUserRequest(data.getEmail(), name));
        UserResponse userResponse = userDeserialization(updateResponse);
        Assert.assertEquals("Имя не изменилось", name, userResponse.getUser().getName());
        deleteUserRequest(authToken);
    }
}
