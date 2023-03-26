package servises;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UserApi {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://petstore.swagger.io/v2");
    public static final String BASE_PATH = "/user";

    public RequestSpecification reqSpec;

    public UserApi() {
        reqSpec = given().baseUri(BASE_URL).basePath(BASE_PATH).contentType(ContentType.JSON);
    }
    public ValidatableResponse createUser(UserDTO user) {
        return given(reqSpec)
                .body(user)
                .log().all()
                .when()
                .post()
                .then()
                .log().all();
    }

    public ValidatableResponse getUser(String userName) {
        return given(reqSpec)
                .get("/"+userName)
                .then()
                .log().all();
    }

}
