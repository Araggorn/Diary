package pl.olek.diaryproject.Service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.olek.diaryproject.Converter.NoteConverter;
import pl.olek.diaryproject.Dto.NoteDto;
import pl.olek.diaryproject.Entity.Note;
import pl.olek.diaryproject.Repository.NoteRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;

    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public NoteDto addNote(NoteDto noteDto) {
        Note note = Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent()).build();
        note.setIsDeleted(false);
        note.setVersion(1L);
        Note noteToUpdate = noteRepo.save(note);
        log.info("adding note with id {}", noteToUpdate.getId());
        noteToUpdate.setNoteId(noteToUpdate.getId());
        noteRepo.save(noteToUpdate);
        return NoteConverter.toDto(noteRepo.getOne(note.getId()));
    }


    @Override
    public List<NoteDto> getListOfAllNotes() {
        log.info("Show whole list of notes");
        return noteRepo.findAllByIsDeletedIsFalse()
                .stream()
                .map(NoteConverter::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<NoteDto> findById(Long id) {
        log.info("is looking for note id: {}", id);

        return noteRepo
                .findById(id)
                .map(NoteConverter::toDto);
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<NoteDto> historyById(Long id) {
        return null;
    }
}
