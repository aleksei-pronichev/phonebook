package ru.pronichev.phonebook.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pronichev.phonebook.entities.Note;

@Data
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private Long userId;
    private String name;
    private String phone;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.userId = note.getUser().getId();
        this.name = note.getName();
        this.phone = note.getPhone();
    }
}