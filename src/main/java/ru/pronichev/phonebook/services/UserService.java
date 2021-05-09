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
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserDto saveOrUpdate(UserDto userDto) {
        if (userDto.getLogin() == null || userDto.getLogin().isBlank()) {
            throw new NotValidException("login not valid");
        }

        var user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());

        return new UserDto(userRepository.save(user));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found"))
                        .getId()
        );
    }
}
