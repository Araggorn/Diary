package pl.olek.diaryproject.converter;

import pl.olek.diaryproject.dto.NoteSnapshotDto;
import pl.olek.diaryproject.entity.NoteSnapshot;

public class NoteSnapshotConverter {

    public static NoteSnapshotDto toDto(NoteSnapshot noteSnapshot) {
        return NoteSnapshotDto.builder()
                .title(noteSnapshot.getTitle())
                .content(noteSnapshot.getContent())
                .noteVersion(noteSnapshot.getNoteVersion())
                .noteId(noteSnapshot.getNote().getId())
                .build();

    }
}
