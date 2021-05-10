package ru.pronichev.phonebook.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.entities.User;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class NoteDtoTest {

    @Autowired
    private JacksonTester<NoteDto> jacksonTester;

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

    @Test
    void testSerializeUser() throws IOException {
        User user = new User();
        user.setLogin("TestLogin");
        user.setId(49L);

        Note note = new Note();
        note.setId(5L);
        note.setName("TestValue");
        note.setPhone("7862000");
        note.setUser(user);

        NoteDto noteDto = NoteDto.toDto(note);

        assertThat(this.jacksonTester.write(noteDto)).isStrictlyEqualToJson("NoteDto.json");
    }

    @Test
    void testDeserializeUser() throws IOException {
        NoteDto noteDto = this.jacksonTester.read("NoteDto.json").getObject();

        assertEquals(5L, noteDto.getId());
        assertEquals("7862000", noteDto.getPhone());
        assertEquals("TestValue", noteDto.getName());
        assertEquals(49L, noteDto.getUserId());
    }
}