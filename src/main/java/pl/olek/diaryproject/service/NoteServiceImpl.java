package pl.olek.diaryproject.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.olek.diaryproject.converter.NoteConverter;
import pl.olek.diaryproject.converter.NoteSnapshotConverter;
import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.dto.NoteSnapshotDto;
import pl.olek.diaryproject.entity.Note;
import pl.olek.diaryproject.entity.NoteSnapshot;
import pl.olek.diaryproject.repository.NoteRepo;
import pl.olek.diaryproject.repository.NoteSnapshotRepo;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final NoteSnapshotRepo noteSnapshotRepo;

    public NoteServiceImpl(NoteRepo noteRepo, NoteSnapshotRepo noteSnapshotRepo) {
        this.noteRepo = noteRepo;
        this.noteSnapshotRepo = noteSnapshotRepo;
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
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .build();
        note.addNoteSnapshot(noteSnapshot);
        Note noteSaved = noteRepo.save(note);
        log.info("adding note with id {}", noteSaved.getId());

        return NoteConverter.toDto(noteSaved);
    }


    @Override
    public List<NoteDto> getListOfAllNotes() {
        log.info("Show whole list of notes");
        return noteRepo
                .findAllByDeletedIsFalse()
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
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        Set<NoteSnapshot> ns = note.getNoteSnapshots();
        Optional<Integer> currentVersion = ns.stream()
                .map(n -> n.getNoteVersion())
                .reduce(Integer::max);

        NoteSnapshot noteSnapshot = NoteSnapshot.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .noteVersion(currentVersion.orElse(0) + 1)
                .build();

        note.addNoteSnapshot(noteSnapshot);
        Note savedNote = noteRepo.save(note);
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
    public List<NoteSnapshotDto> historyById(Long id) {
        log.info("is looking for history of note id {}", id);
        return noteRepo
                .getOne(id)
                .getNoteSnapshots()
                .stream()
                .map(NoteSnapshotConverter::toDto)
                .sorted(Comparator.comparing(NoteSnapshotDto::getNoteVersion))
                .collect(Collectors.toList());
    }

}
