package ru.pronichev.phonebook.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pronichev.phonebook.dto.UserDto;
import ru.pronichev.phonebook.entities.User;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;
import ru.pronichev.phonebook.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void findAll() {
        List<User> userList = new ArrayList<>();

        for (long i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setLogin("TestValue" + i);
        }

        given(this.userRepository.findAll())
                .willReturn(userList);

        List<UserDto> userDtoList = userService.findAll();

        assertEquals(userList.size(), userDtoList.size());

        for (int i = 0; i < userDtoList.size(); i++) {
            User dbUser = userList.get(i);
            UserDto userDto = userDtoList.get(i);

            assertEquals(dbUser.getId(), userDto.getId());
            assertEquals(dbUser.getLogin(), userDto.getLogin());
        }
    }

    @Test
    void findById() {
        long id = 42L;

        User dbUser = new User();
        dbUser.setId(id);
        dbUser.setLogin("TestValue");

        given(this.userRepository.findById(id))
                .willReturn(Optional.of(dbUser));

        UserDto userDto = userService.findById(id);

        assertEquals(dbUser.getId(), userDto.getId());
        assertEquals(dbUser.getLogin(), userDto.getLogin());

        assertThrows(
                NotFoundException.class,
                () -> userService.findById(id + 1)
        );
    }

    @Test
    void saveOrUpdate() {
        List<UserDto> userDtoList = List.of(
                new UserDto(1L, ""),
                new UserDto(45L, "  ")
        );

        for (UserDto userDto : userDtoList) {
            assertThrows(
                    NotValidException.class,
                    () -> userService.saveOrUpdate(userDto)
            );
        }

        long id = 42L;

        User dbUser = new User();
        dbUser.setId(id);
        dbUser.setLogin("TestValue");

        given(this.userRepository.save(any()))
                .willReturn(dbUser);

        UserDto userDto = new UserDto(null, "TestValue");

        UserDto dbUserDto = userService.saveOrUpdate(userDto);

        assertEquals(dbUserDto.getLogin(), userDto.getLogin());
        assertNotEquals(dbUserDto.getId(), userDto.getId());
    }

    @Test
    void deleteById() {
        given(this.userRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> userService.deleteById(1L)
        );
    }

    @Test
    void getUserById() {
        given(this.userRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> userService.findById(1L)
        );
    }

    @Test
    void findByLogin() {
        given(this.userRepository.findByLoginContainingIgnoreCase(""))
                .willReturn(Collections.emptyList());

        assertThrows(
                NotFoundException.class,
                () -> userService.findByLogin("Mark")
        );
    }
}