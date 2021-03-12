package pl.olek.diaryproject.Service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.olek.diaryproject.Dto.NoteDto;
import pl.olek.diaryproject.Repository.NoteRepo;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;

    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public List<NoteDto> historyById(Long id) {
        return null;
    }

    @Override
    public List<NoteDto> getNotes() {
        return null;
    }

    @Override
    public Optional<NoteDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        return null;
    }

    @Override
    public NoteDto addNote(NoteDto noteDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
