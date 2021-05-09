package ru.pronichev.phonebook.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.pronichev.phonebook.dto.NoteDto;
import ru.pronichev.phonebook.entities.Note;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;
import ru.pronichev.phonebook.repositories.NoteRepository;
import ru.pronichev.phonebook.repositories.UserRepository;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteDto findById(Long id) {
        return noteRepository.findById(id)
                .map(NoteDto::new)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public NoteDto saveOrUpdate(NoteDto noteDto) {
        if (Strings.isBlank(noteDto.getName())) {
            throw new NotValidException("Name not valid");
        }
        if (Strings.isBlank(noteDto.getPhone())) {
            throw new NotValidException("phone not valid");
        }
        var note = new Note();

        note.setId(noteDto.getId());
        note.setUser(
                userRepository.findById(noteDto.getUserId())
                        .orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", noteDto.getUserId())))
        );
        note.setName(noteDto.getName());
        note.setPhone(noteDto.getPhone());

        return new NoteDto(noteRepository.save(note));
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(
                noteRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Note not found"))
                        .getId()
        );
    }
}