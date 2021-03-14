package pl.olek.diaryproject.Service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.olek.diaryproject.Entity.NoteSnapshot;
import pl.olek.diaryproject.Repository.NoteSnapshotRepo;
import pl.olek.diaryproject.converter.NoteConverter;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.Entity.Note;
import pl.olek.diaryproject.Repository.NoteRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
   // private final NoteSnapshotRepo noteSnapshotRepo;

    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public NoteDto addNote(NoteDto noteDto) {
        Note note = Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent()).build();
        note.setIsDeleted(false);
        NoteSnapshot noteSnapshot = NoteSnapshot.builder()
                .noteVersion(1)
                .note(note)
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .build();

        note.getNoteSnapshots().add(noteSnapshot);
        Note noteSaved = noteRepo.save(note);
        log.info("adding note with id {}", noteSaved.getId());

        return NoteConverter.toDto(noteSaved);
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
    public NoteDto updateNote(EditNoteDto noteDto, Long id) {
        Note note = noteRepo.getOne(id);
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
        NoteSnapshot noteSnapshot = noteRepo.getOne(id).getNoteSnapshots();
        return noteRepo
                .findAllByNoteId(noteId)
                .stream()
                .map(NoteConverter::toDto)
                .collect(Collectors.toList());
    }
}
