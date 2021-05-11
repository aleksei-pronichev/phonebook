package ru.pronichev.phonebook.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pronichev.phonebook.dto.NoteDto;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.entities.User;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;
import ru.pronichev.phonebook.repositories.NoteRepository;
import ru.pronichev.phonebook.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class NoteServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @Test
    void findById() {
        long note_id = 42L;
        long user_id = 5L;

        User dbUser = new User();
        dbUser.setId(user_id);
        dbUser.setLogin("TestValue");

        Note dbNote = new Note();
        dbNote.setId(note_id);
        dbNote.setUser(dbUser);
        dbNote.setName("TestUser");

        given(this.userRepository.findById(user_id))
                .willReturn(Optional.of(dbUser));

        given(this.noteRepository.findById(note_id))
                .willReturn(Optional.of(dbNote));

        NoteDto noteDto = noteService.findById(note_id);

        assertEquals(dbNote.getId(), noteDto.getId());
        assertEquals(dbNote.getName(), noteDto.getName());
        assertEquals(dbNote.getPhone(), noteDto.getPhone());
        assertEquals(dbUser.getId(), noteDto.getUserId());

        assertThrows(
                NotFoundException.class,
                () -> noteService.findById(note_id + 1)
        );
    }

    @Test
    void saveOrUpdate() {
        List<NoteDto> noteDtoList = List.of(
                new NoteDto(1L, 1L, "", ""),
                new NoteDto(1L, 1L, "   ", "   "),
                new NoteDto(1L, 1L, "", "   "),
                new NoteDto(1L, 1L, "   ", "")
        );

        for (NoteDto noteDto : noteDtoList) {
            assertThrows(
                    NotValidException.class,
                    () -> noteService.saveOrUpdate(noteDto)
            );
        }

        long note_id = 42L;
        long user_id = 5L;
        String testName = "TestName";
        String testPhone = "TestPhone";

        User dbUser = new User();
        dbUser.setId(user_id);
        dbUser.setLogin("TestUser");

        Note dbNote = new Note();
        dbNote.setId(note_id);
        dbNote.setUser(dbUser);
        dbNote.setUser(dbUser);
        dbNote.setName(testName);
        dbNote.setPhone(testPhone);

        given(this.userRepository.findById(any()))
                .willReturn(Optional.of(dbUser));
        given(this.noteRepository.save(any()))
                .willReturn(dbNote);

        NoteDto noteDto = new NoteDto(note_id, user_id, testName, testPhone);

        NoteDto dbNoteDto = noteService.saveOrUpdate(noteDto);

        assertEquals(noteDto.getId(), dbNoteDto.getId());
        assertEquals(noteDto.getUserId(), dbNoteDto.getUserId());
        assertEquals(noteDto.getName(), dbNoteDto.getName());
        assertEquals(noteDto.getPhone(), dbNoteDto.getPhone());
    }

    @Test
    void deleteById() {
        given(this.noteRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                NotValidException.class,
                () -> noteService.deleteById(1L)
        );
    }

    @Test
    void getNoteById() {
        given(this.noteRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> noteService.findById(1L)
        );
    }

    @Test
    void findByPhone() {
        long note_id = 42L;
        long user_id = 5L;
        String phone = "33";

        User dbUser = new User();
        dbUser.setId(user_id);
        dbUser.setLogin("TestValue");

        List<NoteDto> noteDtoList = List.of(
                new NoteDto(1L, 1L, "Teacher", "445-33-1"),
                new NoteDto(2L, 1L, "Dad", "76-55-2"),
                new NoteDto(3L, 1L, "Mum", "333-44-22"),
                new NoteDto(4L, 1L, "Cuisine", "44-33")
        );

        List<Note> notes = noteDtoList.stream()
                .map(e -> e.toDomain(dbUser))
                .collect(Collectors.toList());

        given(this.noteRepository.findByUserAndPhoneContaining(dbUser, phone))
                .willReturn(Collections.emptyList());

        given(this.userRepository.findById(any()))
                .willReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> noteService.findByPhone(user_id, phone)
        );

        given(this.userRepository.findById(user_id))
                .willReturn(Optional.of(dbUser));
        assertThrows(
                NotFoundException.class,
                () -> noteService.findByPhone(user_id, phone)
        );

        given(this.noteRepository.findByUserAndPhoneContaining(dbUser, phone))
                .willReturn(notes);

        List<NoteDto> dbNoteDtoList = noteService.findByPhone(user_id, phone);

        assertEquals(noteDtoList.size(), dbNoteDtoList.size());
    }

    @Test
    void findAllByUserId() {
        given(this.userRepository.findById(any()))
                .willReturn(Optional.empty());

        List<NoteDto> noteDtoList = noteService.findAllByUserId(1L);

        assertTrue(noteDtoList.isEmpty());
    }
}