package clients;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import model.CreateUserRequest;
import model.LoginUserRequest;
import model.UpdateUserRequest;

import static config.ConfigApp.*;
import static io.restassured.RestAssured.given;

public class UserApiClient extends BaseApiClient {


    @Step("Создание пользователя")
    public static Response createUserRequest(CreateUserRequest createUserRequest) {
        return given()
                .spec(getSpec())
                .body(createUserRequest)
                .when()
                .filter(new AllureRestAssured())
                .post(CREATE_USER_URL);
    }

    @Step("Логин пользователя")
    public static Response loginUserRequest(LoginUserRequest loginUserRequest) {
        return given()
                .spec(getSpec())
                .body(loginUserRequest)
                .when()
                .filter(new AllureRestAssured())
                .post(AUTH_URL);
    }

    @Step("Обновление данных пользователя")
    public static Response UpdateDataUserRequest(String accessToken, UpdateUserRequest updateUserRequest) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body(updateUserRequest)
                .when()
                .filter(new AllureRestAssured())
                .patch(PATCH_USER_URL);
    }

    @Step("Удаление пользователя")
    public static Response deleteUserRequest(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .filter(new AllureRestAssured())
                .delete(DELETE_USER_URL);
    }

    @Step("Получение токена")
    public static String getAuthToken(Response response) {
        return response.body().jsonPath().get("accessToken");
    }
}
