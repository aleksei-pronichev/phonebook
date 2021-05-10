package ru.pronichev.phonebook.dto;

import org.junit.jupiter.api.Test;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteDtoTest {

    @Test
    void toDto() {
        User user = new User();
        user.setLogin("TestLogin");
        user.setId(87L);

        Note note = new Note();
        note.setId(45L);
        note.setName("TestName");
        note.setPhone("8986633");
        note.setUser(user);

        NoteDto noteDto = NoteDto.toDto(note);

        assertEquals(note.getId(), noteDto.getId());
        assertEquals(note.getName(), noteDto.getName());
        assertEquals(note.getPhone(), noteDto.getPhone());
        assertEquals(user.getId(), noteDto.getUserId());
    }

    @Test
    void toDomain() {
        NoteDto noteDto = new NoteDto(56L, 47L, "TestDto", "843290");
        User user = new User();
        user.setLogin("Test login");
        user.setId(noteDto.getUserId());

        Note note = noteDto.toDomain(user);

        assertEquals(note.getId(), noteDto.getId());
        assertEquals(note.getPhone(), noteDto.getPhone());
        assertEquals(note.getName(), noteDto.getName());
        assertEquals(note.getUser().getId(), noteDto.getUserId());
    }
}