package pl.olek.diaryproject.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.olek.diaryproject.Dto.NoteDto;
import pl.olek.diaryproject.Service.NoteService;

import java.util.List;

@Slf4j
@RestController
// @CrossOrigin
public class NoteController {

private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/list")
    public List<NoteDto> noteList() {
        return noteService.getListOfAllNotes();
    }

    @DeleteMapping("note/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        log.info("Request to deleto note: {}", id);
        noteService.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
