package pl.olek.diaryproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.dto.NoteSnapshotDto;
import pl.olek.diaryproject.repository.NoteRepo;
import pl.olek.diaryproject.service.NoteService;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
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

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") Long id) {
        return noteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteDto> noteAdd(@RequestBody CreateNoteDto noteDto) {
        NoteDto savedNote = noteService.addNote(noteDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedNote);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<NoteDto> editNote(@RequestBody EditNoteDto noteDto, @PathVariable Long id) {
        NoteDto noteDtoResult = noteService.updateNote(noteDto, id);
        return ResponseEntity.ok(noteDtoResult);
    }

    @GetMapping("/{id:\\d+}/history")
    public List<NoteSnapshotDto> getHistoryOfTheNote(@PathVariable Long id) {
        return noteService.historyById(id);

    }

    @PostConstruct
    void createSampleData() {
        noteService.addNote(CreateNoteDto.builder()
                .title("Ale")
                .content("BB")
                .build());

    }

    @GetMapping("/{id:\\d+}/originalversion")
    public NoteSnapshotDto getOriginalVersion(@PathVariable Long id) {
        List<NoteSnapshotDto> noteSnapshotDtos = noteService.historyById(id);
        NoteSnapshotDto result = noteSnapshotDtos.stream()
                .filter(n -> n.getNoteVersion() == 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        return result;
    }

    @GetMapping("note/{id:\\d+}/version/{versionId:\\d+}")
    public NoteSnapshotDto getChosenVersion(@PathVariable Long id, @PathVariable Integer versionId) {
        List<NoteSnapshotDto> noteSnapshotDtos = noteService.historyById(id);
        return noteSnapshotDtos.stream()
                .filter(n -> n.getNoteVersion() == versionId)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("not found"));
    }

}
