package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static config.ConfigApp.BASE_URL;

public class BaseApiClient {
    public static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .log(LogDetail.ALL)
                .build();
    }
}

