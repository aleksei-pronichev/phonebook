package ru.pronichev.phonebook.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.pronichev.phonebook.dto.NoteDto;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;
import ru.pronichev.phonebook.repositories.NoteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteDto findById(Long id) {
        return NoteDto.toDto(getNoteById(id));
    }

    public NoteDto saveOrUpdate(NoteDto noteDto) {
        if (Strings.isBlank(noteDto.getName())) {
            throw new NotValidException("Name not valid");
        }
        if (Strings.isBlank(noteDto.getPhone())) {
            throw new NotValidException("Phone not valid");
        }
        var note = noteDto.toDomain(
                userService.getUserById(noteDto.getUserId())
        );
        return NoteDto.toDto(noteRepository.save(note));
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(getNoteById(id).getId());
    }

    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException(String.format("Note with id: %s not found", noteId)));
    }

    public List<NoteDto> findByPhone(Long userId, String phone) {
        List<Note> notes = noteRepository.findByUserAndPhoneContaining(userService.getUserById(userId), phone);
        if (notes.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return convertNotes(notes);
    }

    private List<NoteDto> convertNotes(List<Note> notes) {
        return notes.stream()
                .map(NoteDto::toDto)
                .collect(Collectors.toList());
    }

    public List<NoteDto> findAllByUserId(Long userId) {
        return convertNotes(
                noteRepository.findAllByUserOrderByName(userService.getUserById(userId))
        );
    }
}