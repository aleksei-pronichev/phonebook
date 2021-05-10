package ru.pronichev.phonebook.dto;

import org.junit.jupiter.api.Test;
import ru.pronichev.phonebook.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

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
        UserDto userDto = new UserDto(56l, "TestDto");
        User user = userDto.toDomain();

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getLogin(), userDto.getLogin());
    }
}