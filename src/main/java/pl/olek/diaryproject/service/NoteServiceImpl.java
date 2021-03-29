package pl.olek.diaryproject.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.olek.diaryproject.converter.NoteConverter;
import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.entity.Note;
import pl.olek.diaryproject.entity.NoteSnapshot;
import pl.olek.diaryproject.repository.NoteRepo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public NoteDto addNote(CreateNoteDto noteDto) {
        Note note = Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .noteSnapshots(new HashSet<>())
                .build();
        note.setDeleted(false);
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
        return noteRepo.findAllByDeletedIsFalse()
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

//        Note note = noteRepository.getOne(noteDto.getId());
//        log.info("updating note with id {}",note.getId());
//        note.setDeleted(true);
//        noteRepository.save(note);
//        Note noteToUpdate = new Note();
//        noteToUpdate.setOriginalId(note.getOriginalId());
//        noteToUpdate.setTitle(noteDto.getTitle());
//        noteToUpdate.setContent(noteDto.getContent());
//        noteToUpdate.setCreated(note.getCreated());
//        noteToUpdate.setDeleted(false);
//        noteToUpdate.setVersion(note.getVersion()+1);
//        Note savedNote = noteRepository.save(noteToUpdate);
//        log.info("updated note has id {}",savedNote.getId());
//        return NoteConverter.toDto(savedNote);

        Note note = noteRepo.getOne(id);
        log.info("updating note id {}", note.getId());
        note.setDeleted(true);
        noteRepo.save(note);

        Note updatedNote = new Note();
        updatedNote.setTitle(noteDto.getTitle());
        updatedNote.setContent(noteDto.getContent());
        updatedNote.setDeleted(false);
        updatedNote.setCreateTime(note.getCreateTime());
       //  updatedNote.setUpdateTime(LocalDateTime.now());  TODO trzeba?

        Set<NoteSnapshot> ns = updatedNote.getNoteSnapshots();
        Optional<Integer> currentVersion = ns.stream()
                .map(n -> n.getNoteVersion())
                .reduce(Integer::max);


        NoteSnapshot noteSnapshot = NoteSnapshot.builder()
                .note(updatedNote)
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .noteVersion(1 + currentVersion.orElse(0))
                .build();

        updatedNote.getNoteSnapshots().add(noteSnapshot);
        Note savedNote = noteRepo.save(updatedNote);
        log.info("updated note with id {}", savedNote.getId());
        return NoteConverter.toDto(savedNote);
    }

    @Override
    public void deleteById(Long id) {
        Note note = noteRepo.getOne(id);
        log.info("deleting note id: {}", id);
        note.setDeleted(true);
        noteRepo.save(note);
    }

    @Override
    public Set<NoteSnapshot> historyById(Long id) {
        log.info("is looking for history of note id {}", id);
        Set<NoteSnapshot> noteSnapshots = noteRepo.getOne(id).getNoteSnapshots();
        return noteSnapshots;
    }
}
