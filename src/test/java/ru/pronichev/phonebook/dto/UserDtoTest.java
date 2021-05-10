package ru.pronichev.phonebook.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.pronichev.phonebook.entities.User;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class UserDtoTest {

    @Autowired
    private JacksonTester<UserDto> jacksonTester;

    @Test
    void toDto() {
        User user = new User();
        user.setLogin("TestLogin");
        user.setId(87L);

        UserDto userDto = UserDto.toDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getLogin(), userDto.getLogin());
    }

    @Test
    void toDomain() {
        UserDto userDto = new UserDto(56L, "TestDto");
        User user = userDto.toDomain();

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getLogin(), userDto.getLogin());
    }

    @Test
    void testSerializeUser() throws IOException {
        User user = new User();
        user.setLogin("TestLogin");
        user.setId(87L);
        UserDto userDto = UserDto.toDto(user);

        assertThat(this.jacksonTester.write(userDto)).isStrictlyEqualToJson("user-test.json");
    }

    @Test
    void testDeserializeUser() throws IOException {
        UserDto userDto = this.jacksonTester.read("user-test.json").getObject();

        assertEquals(87L, userDto.getId());
        assertEquals("TestLogin", userDto.getLogin());
    }
}