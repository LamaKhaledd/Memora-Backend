package com.lin.controller;

import com.lin.entity.Note;
import com.lin.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Get all notes
    @GetMapping(value = "/all")
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    // Get a note by its ID
    @GetMapping(value = "/{id}")
    public Optional<Note> getNoteById(@PathVariable("id") String id) {
        return noteService.getNoteById(id);
    }

    // Get all notes for a specific user
    @GetMapping(value = "/user/{userId}")
    public List<Note> getNotesByUserId(@PathVariable("userId") String userId) {
        return noteService.getNotesByUserId(userId);
    }

    // Add or update a note
    @PostMapping(value = "/save")
    public Note saveOrUpdateNote(@RequestBody Note note) {
        return noteService.saveOrUpdateNote(note);
    }

    // Delete a note by ID
    @DeleteMapping(value = "/delete/{id}")
    public void deleteNoteById(@PathVariable("id") String id) {
        noteService.deleteNoteById(id);
    }

    // Change the color of a note
    @PostMapping(value = "/color/{id}")
    public Optional<Note> changeNoteColor(@PathVariable("id") String id, @RequestParam int color) {
        return noteService.changeNoteColor(id, color);
    }
}
