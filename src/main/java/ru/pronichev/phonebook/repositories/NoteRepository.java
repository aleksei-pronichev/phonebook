package ru.pronichev.phonebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pronichev.phonebook.entities.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}