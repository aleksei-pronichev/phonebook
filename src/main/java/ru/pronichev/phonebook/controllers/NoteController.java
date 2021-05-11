package ru.pronichev.phonebook.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pronichev.phonebook.dto.NoteDto;
import ru.pronichev.phonebook.services.NoteService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(noteService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<NoteDto>> findAllNoteByUserId(@PathVariable(value = "user_id") Long userId) {
        return new ResponseEntity<>(noteService.findAllByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNewNote(@RequestBody NoteDto noteDto) {
        noteDto.setId(null);
        return new ResponseEntity<>(noteService.saveOrUpdate(noteDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto noteDto) {
        return new ResponseEntity<>(noteService.saveOrUpdate(noteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        noteService.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}/find")
    public ResponseEntity<List<NoteDto>> findByPhone(
            @PathVariable(value = "user_id") Long userId,
            @RequestParam(value = "phone") String phone
    ) {
        return new ResponseEntity<>(noteService.findByPhone(userId, phone), HttpStatus.OK);
    }
}