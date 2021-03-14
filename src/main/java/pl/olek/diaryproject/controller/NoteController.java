package pl.olek.diaryproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.Service.NoteService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
// @CrossOrigin
@RequestMapping(path = "/v1/notes")
public class NoteController {

private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDto> noteList() {
        return noteService.getListOfAllNotes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id){
        return noteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<NoteDto> noteAdd(NoteDto noteDto){
        NoteDto savedNote = noteService.addNote(noteDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> editNote(EditNoteDto noteDto, @PathVariable Long id){
        NoteDto updatedNote = noteService.updateNote(noteDto, id);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/{id}/history")
    public List<NoteDto> getHistoryOfTheNote(@PathVariable Long id){
        return noteService.historyById(id);

    }
}
