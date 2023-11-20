import com.github.javafaker.Faker;
import io.restassured.response.Response;
import model.CreateUserRequest;
import org.junit.After;
import org.junit.Before;

import static clients.UserApiClient.*;
import static helpers.DataGenerator.getUser;

public class BaseTest {
    CreateUserRequest data;
    Response response;
    Faker faker;
    String authToken;

    @Before
    public void startUp() {
        faker = new Faker();
        data = getUser();
        response = createUserRequest(data);
        authToken = getAuthToken(response);
    }

    @After
    public void tearDown() {
        if (authToken != null) {
            deleteUserRequest(authToken);
        }
    }
}
