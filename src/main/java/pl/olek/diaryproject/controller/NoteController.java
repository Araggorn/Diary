package pl.olek.diaryproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.entity.Note;
import pl.olek.diaryproject.entity.NoteSnapshot;
import pl.olek.diaryproject.repository.NoteRepo;
import pl.olek.diaryproject.service.NoteService;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
// @CrossOrigin
@RequestMapping(path = "/v1/notes")
public class NoteController {

private final NoteService noteService;
private final NoteRepo noteRepo;

    public NoteController(NoteService noteService, NoteRepo noteRepo) {
        this.noteService = noteService;
        this.noteRepo = noteRepo;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<NoteDto> noteAdd(@RequestBody CreateNoteDto noteDto){
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
    public Set<NoteSnapshot> getHistoryOfTheNote(@PathVariable Long id){
        return noteService.historyById(id);

    }

    @PostConstruct
    void createSampleData() {
        noteService.addNote(CreateNoteDto.builder()
                .title("Ale")
                .content("BB")
                .build());

//        noteRepo.save(Note.builder()
//                .noteSnapshots(null)
//                .title("AAAA")
//                .content("as").build());
    }
}
