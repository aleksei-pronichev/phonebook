package ru.pronichev.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pronichev.phonebook.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin()
        );
    }

    public User toDomain() {
        var user = new User();
        user.setId(this.getId());
        user.setLogin(this.getLogin());

        return user;
    }
}
