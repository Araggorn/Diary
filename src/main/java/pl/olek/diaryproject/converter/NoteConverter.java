package pl.olek.diaryproject.converter;

import pl.olek.diaryproject.dto.NoteDto;
import pl.olek.diaryproject.entity.Note;

public class NoteConverter {

    public static NoteDto toDto(Note note) {
        return NoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .createTime(note.getCreateTime())
                .updateTime(note.getUpdateTime())
                .id(note.getId())
                .deleted(note.isDeleted())
                .build();
    }
}
