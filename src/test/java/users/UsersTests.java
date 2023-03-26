package users;


import dto.UserDTO;
import dto.UserOutDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servises.UserApi;

public class UsersTests {


    // Создание пользователя 1 и проверка по значениям полей
    @Test
    public void createNewUser1() {
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

        ValidatableResponse response = userApi.createUser(user);
        UserOutDTO userOut = response.extract().body().as(UserOutDTO.class);

        Assertions.assertAll("Check response",
                () -> Assertions.assertEquals(200, userOut.getCode()),
                () -> Assertions.assertEquals("unknown", userOut.getType()),
                () -> Assertions.assertEquals("1", userOut.getMessage())
        );
    }

    //     Создание пользователя 2 и проверка по схеме
    @Test
    public void createNewUser2() {
        UserApi userApi = new UserApi();
        UserDTO user = UserDTO.builder()
                .email("test@test.ru")
                .username("user2")
                .userStatus(10L)
                .id(2L)
                .firstName("first")
                .phone("123123-123123")
                .lastName("last")
                .password("pass")
                .build();

        ValidatableResponse response = userApi.createUser(user);
        response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/createUser.json"));
    }

    // получение юзера 1 проверка по значениям полей
    @Test
    public void getUser1() {
        UserApi userApi = new UserApi();
        ValidatableResponse response = userApi.getUser("user1");
        UserDTO user = response.extract().body().as(UserDTO.class);

        Assertions.assertAll("Check response",
                () -> Assertions.assertEquals(1, user.getId()),
                () -> Assertions.assertEquals("user1", user.getUsername())
        );
    }

    // Получение юзера 2 и проверка по схеме
    @Test
    public void getUser2() {
        UserApi userApi = new UserApi();
        ValidatableResponse response = userApi.getUser("user2");
        response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getUser.json"));
    }
}
