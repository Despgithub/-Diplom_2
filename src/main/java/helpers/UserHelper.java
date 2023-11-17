package helpers;

import io.restassured.response.Response;
import model.UserErrorResponse;
import model.UserResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserHelper {

    public static UserResponse userDeserialization(Response response) {
        return response.then().assertThat().body("success", equalTo(true)).statusCode(SC_OK).log().all().and().extract().as(UserResponse.class);
    }

    public static UserErrorResponse userForbiddenErrorDeserialization(Response response) {
        return response.then().assertThat().body("success", equalTo(false)).statusCode(SC_FORBIDDEN).log().all().and().extract().as(UserErrorResponse.class);
    }

    public static UserErrorResponse userUnauthorizedErrorDeserialization(Response response) {
        return response.then().assertThat().body("success", equalTo(false)).statusCode(SC_UNAUTHORIZED).log().all().and().extract().as(UserErrorResponse.class);
    }

}
