import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserErrorResponse;
import org.junit.Assert;
import org.junit.Test;

import static clients.UserApiClient.createUserRequest;
import static helpers.DataGenerator.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateUserTest extends BaseTest {

    @DisplayName("Создание пользователя")
    @Description("Должен вернуться код '200', а в теле сообщения 'success:true'")
    @Test
    public void createUserTest() {
        response.then().statusCode(SC_OK).assertThat().body("success", equalTo(true)).log().all();
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'User already exists'")
    @Test
    public void createDuplicateUserTest() {
        response = createUserRequest(data);
        UserErrorResponse errorResponse = response.then().statusCode(SC_FORBIDDEN).assertThat().body("success", equalTo(false)).log().all().and().extract().as(UserErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "User already exists", errorResponse.getMessage());
    }

    @DisplayName("Создание пользователя, без всех обязательных полей")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserEmptyFieldsTest() {
        response = createUserRequest(getEmptyUser());
        UserErrorResponse errorResponse = response.then().statusCode(SC_FORBIDDEN).assertThat().body("success", equalTo(false)).log().all().and().extract().as(UserErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Email, password and name are required fields", errorResponse.getMessage());
    }

    @DisplayName("Создание пользователя, без email")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutEmailTest() {
        response = createUserRequest(getUserWithoutEmail());
        UserErrorResponse errorResponse = response.then().statusCode(SC_FORBIDDEN).assertThat().body("success", equalTo(false)).log().all().and().extract().as(UserErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Email, password and name are required fields", errorResponse.getMessage());
    }

    @DisplayName("Создание пользователя, без имени")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutNameTest() {
        response = createUserRequest(getUserWithoutName());
        UserErrorResponse errorResponse = response.then().statusCode(SC_FORBIDDEN).assertThat().body("success", equalTo(false)).log().all().and().extract().as(UserErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Email, password and name are required fields", errorResponse.getMessage());
    }

    @DisplayName("Создание пользователя, без пароля")
    @Description("Должен вернуться код '403', а в теле сообщения 'success:false' и ошибка 'Email, password and name are required fields'")
    @Test
    public void createUserWithoutPasswordTest() {
        response = createUserRequest(getUserWithoutPassword());
        UserErrorResponse errorResponse = response.then().statusCode(SC_FORBIDDEN).assertThat().body("success", equalTo(false)).log().all().and().extract().as(UserErrorResponse.class);
        Assert.assertEquals("Неверный текст ошибки", "Email, password and name are required fields", errorResponse.getMessage());
    }

}
