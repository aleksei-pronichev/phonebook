package ru.pronichev.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private Long id;
    private Long userId;
    private String name;
    private String phone;

    public static NoteDto toDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getUser().getId(),
                note.getName(),
                note.getPhone()
        );
    }

    public Note toDomain(User user) {
        var note = new Note();
        note.setId(this.id);
        note.setUser(user);
        note.setName(this.name);
        note.setPhone(this.phone);

        return note;
    }
}