package ru.pronichev.phonebook.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pronichev.phonebook.dto.UserDto;
import ru.pronichev.phonebook.entities.User;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;
import ru.pronichev.phonebook.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return convertUsers(userRepository.findAll());
    }

    public UserDto findById(Long id) {
        return UserDto.toDto(getUserById(id));
    }

    public UserDto saveOrUpdate(UserDto userDto) {
        if (userDto.getLogin() == null || userDto.getLogin().isBlank()) {
            throw new NotValidException("login not valid");
        }

        return UserDto.toDto(userRepository.save(userDto.toDomain()));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(getUserById(id).getId()
        );
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", userId)));
    }

    public List<UserDto> findByLogin(String login) {
        List<User> users = userRepository.findByLoginContainingIgnoreCase(login);
        if (users.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return convertUsers(users);
    }

    private List<UserDto> convertUsers(List<User> users) {
        return users.stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }
}
