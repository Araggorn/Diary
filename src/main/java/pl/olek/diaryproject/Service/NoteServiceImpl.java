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
        Note note = noteRepo.getOne(noteDto.getId());
        log.info("updating note id {}", note.getId());
        note.setIsDeleted(true);
        noteRepo.save(note);
        Note updatedNote = new Note();
        updatedNote.setNoteId(note.getNoteId());
        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(noteDto.getContent());
        updatedNote.setCreateTime(note.getCreateTime());
        updatedNote.setIsDeleted(false);
        updatedNote.setVersion(note.getVersion() + 1);
        Note savedNote = noteRepo.save(updatedNote);
        log.info("updated note with id {}", savedNote.getId());
        return NoteConverter.toDto(savedNote);
    }

    @Override
    public void deleteById(Long id) {
        Note note = noteRepo.getOne(id);
        log.info("deleting note id: {}", id);
        note.setIsDeleted(true);
        noteRepo.save(note);
    }

    @Override
    public List<NoteDto> historyById(Long id) {
        log.info("is looking for history of note id {}", id);
        Long noteId = noteRepo.getOne(id).getNoteId();
        return noteRepo
                .findAllByNoteId(noteId)
                .stream()
                .map(NoteConverter::toDto)
                .collect(Collectors.toList());
    }
}
