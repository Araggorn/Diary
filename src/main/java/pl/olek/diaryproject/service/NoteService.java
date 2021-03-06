package pl.olek.diaryproject.service;

import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.dto.NoteSnapshotDto;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<NoteSnapshotDto> historyById(Long id);

    List<NoteDto> getListOfAllNotes();

    Optional<NoteDto> findById(Long id);

    NoteDto updateNote(EditNoteDto noteDto, Long id);

    NoteDto addNote(CreateNoteDto noteDto);

    void deleteById(Long id);


}
