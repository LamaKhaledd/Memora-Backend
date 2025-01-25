package com.lin.service;

import com.lin.entity.Note;
import com.lin.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Get all notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Get a note by its ID
    public Optional<Note> getNoteById(String id) {
        return noteRepository.findById(id);
    }

    // Get all notes for a specific user
    public List<Note> getNotesByUserId(String userId) {
        return noteRepository.findAll().stream()
                .filter(note -> note.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // Add or update a note
    public Note saveOrUpdateNote(Note note) {
        return noteRepository.save(note);
    }

    // Delete a note by ID
    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

    // Change the color of a note
    public Optional<Note> changeNoteColor(String id, int newColor) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            note.setColor(newColor);  // Update the color
            noteRepository.save(note); // Save the updated note
            return Optional.of(note);
        }
        return Optional.empty();
    }
}
