package helpers;

import com.github.javafaker.Faker;
import model.CreateUserRequest;

public class DataGenerator {
    public static CreateUserRequest getUser() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = faker.internet().password();
        return new CreateUserRequest(email, name, password);
    }

    public static CreateUserRequest getEmptyUser() {
        return new CreateUserRequest("", "", "");
    }

    public static CreateUserRequest getUserWithoutEmail() {
        Faker faker = new Faker();
        String email = "";
        String name = faker.name().firstName();
        String password = faker.internet().password();
        return new CreateUserRequest(email, name, password);
    }

    public static CreateUserRequest getUserWithoutName() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = "";
        String password = faker.internet().password();
        return new CreateUserRequest(email, name, password);
    }

    public static CreateUserRequest getUserWithoutPassword() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = "";
        return new CreateUserRequest(email, name, password);
    }
}
