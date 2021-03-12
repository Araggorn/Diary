package pl.olek.diaryproject.Converter;

import pl.olek.diaryproject.Dto.NoteDto;
import pl.olek.diaryproject.Entity.Note;

public class NoteConverter {

    public static NoteDto toDto(Note note){
        return NoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .createTime(note.getCreateTime())
                .updateTime(note.getUpdateTime())
                .id(note.getId())
                .build();
    }
}
