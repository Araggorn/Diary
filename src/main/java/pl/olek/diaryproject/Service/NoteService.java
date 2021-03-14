package pl.olek.diaryproject.Service;

import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<NoteDto> historyById(Long id);

    List<NoteDto> getListOfAllNotes();

    Optional<NoteDto> findById(Long id);

    NoteDto updateNote(EditNoteDto noteDto, Long id);

    NoteDto addNote (NoteDto noteDto);

    void deleteById (Long id);



}
