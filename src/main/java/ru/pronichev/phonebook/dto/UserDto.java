package ru.pronichev.phonebook.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pronichev.phonebook.entities.User;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String login;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
    }
}
