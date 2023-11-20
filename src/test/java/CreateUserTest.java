import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import static clients.UserApiClient.createUserRequest;
import static helpers.DataGenerator.*;
import static helpers.UserHelper.userDeserialization;
import static helpers.UserHelper.userForbiddenErrorDeserialization;

public class CreateUserTest extends BaseTest {

    @DisplayName("Создание пользователя")
    @Description("Должен вернуться код '200', а в теле сообщения 'success:true'")
    @Test
    public void createUserTest() {
        userDeserialization(response);
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'User already exists'")
    @Test
    public void createDuplicateUserTest() {
        UserErrorResponse errorResponse = userForbiddenErrorDeserialization(createUserRequest(data));
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "User already exists");
    }

    @DisplayName("Создание пользователя, без всех обязательных полей")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserEmptyFieldsTest() {
        data = getEmptyUser();
        UserErrorResponse errorResponse = userForbiddenErrorDeserialization(createUserRequest(data));
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Email, password and name are required fields");
    }

    @DisplayName("Создание пользователя, без email")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutEmailTest() {
        data = getUserWithoutEmail();
        UserErrorResponse errorResponse = userForbiddenErrorDeserialization(createUserRequest(data));
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Email, password and name are required fields");
    }

    @DisplayName("Создание пользователя, без имени")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutNameTest() {
        data = getUserWithoutName();
        UserErrorResponse errorResponse = userForbiddenErrorDeserialization(createUserRequest(data));
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Email, password and name are required fields");
    }

    @DisplayName("Создание пользователя, без пароля")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutPasswordTest() {
        data = getUserWithoutPassword();
        UserErrorResponse errorResponse = userForbiddenErrorDeserialization(createUserRequest(data));
        Assert.assertEquals("Неверный текст ошибки", errorResponse.getMessage(), "Email, password and name are required fields");
    }

}
