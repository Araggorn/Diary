package pl.olek.diaryproject.service;

import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.entity.NoteSnapshot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NoteService {

    Set<NoteSnapshot> historyById(Long id);

    List<NoteDto> getListOfAllNotes();

    Optional<NoteDto> findById(Long id);

    NoteDto updateNote(EditNoteDto noteDto, Long id);

    NoteDto addNote (CreateNoteDto noteDto);

    void deleteById (Long id);



}
