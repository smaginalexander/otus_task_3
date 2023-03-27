package users;


import dto.UserDTO;
import dto.UserOutDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servises.UserApi;

public class UsersTests {


    @Test
    public void createNewUser() {
//      Создание пользователя
        UserApi userApi = new UserApi();
        UserDTO user = UserDTO.builder()
                .email("user@otus.ru")
                .username("user1")
                .userStatus(10L)
                .id(1L)
                .firstName("first")
                .phone("123123-123123")
                .lastName("last")
                .password("pass")
                .build();

        ValidatableResponse responseAfterCreateUser = userApi.createUser(user);
        responseAfterCreateUser.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/createUser.json"));
        UserOutDTO userOut = responseAfterCreateUser.extract().body().as(UserOutDTO.class);

//      Проверка ответа после создания пользователя
        Assertions.assertAll("Check status",
                () -> Assertions.assertEquals(200, userOut.getCode()),
                () -> Assertions.assertEquals("unknown", userOut.getType()),
                () -> Assertions.assertEquals("1", userOut.getMessage())
        );

//      Запрос на получение пользователя
        ValidatableResponse responseToGetUser = userApi.getUser(user.getUsername());
        responseToGetUser.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getUser.json"));

        UserDTO userResponse = responseToGetUser.extract().body().as(UserDTO.class);

//      Проверка соответствия полей пользователя
        Assertions.assertAll("Check user params",
                () -> Assertions.assertEquals(user.getEmail(), userResponse.getEmail()),
                () -> Assertions.assertEquals(user.getUsername(), userResponse.getUsername()),
                () -> Assertions.assertEquals(user.getUserStatus(), userResponse.getUserStatus()),
                () -> Assertions.assertEquals(user.getId(), userResponse.getId()),
                () -> Assertions.assertEquals(user.getFirstName(), userResponse.getFirstName()),
                () -> Assertions.assertEquals(user.getPhone(), userResponse.getPhone()),
                () -> Assertions.assertEquals(user.getLastName(), userResponse.getLastName()),
                () -> Assertions.assertEquals(user.getPassword(), userResponse.getPassword())
        );

    }

}
