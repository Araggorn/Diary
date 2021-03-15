package pl.olek.diaryproject.converter;

import pl.olek.diaryproject.dto.CreateNoteDto;
import pl.olek.diaryproject.entity.Note;

public class CreateNoteConverter {

    public static CreateNoteDto toDto(Note note) {
        return CreateNoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
