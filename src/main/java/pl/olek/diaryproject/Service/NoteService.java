package pl.olek.diaryproject.Service;

import pl.olek.diaryproject.Dto.NoteDto;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<NoteDto> historyById(Long id);

    List<NoteDto> getNotes();

    Optional<NoteDto> findById(Long id);

    NoteDto updateNote (NoteDto noteDto);

    NoteDto addNote (NoteDto noteDto);

    void deleteById (Long id);



}
