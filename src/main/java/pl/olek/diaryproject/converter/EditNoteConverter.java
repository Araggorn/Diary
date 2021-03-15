package pl.olek.diaryproject.converter;

import pl.olek.diaryproject.dto.EditNoteDto;
import pl.olek.diaryproject.entity.Note;

public class EditNoteConverter {

    public static EditNoteDto toDto(Note note) {
        return EditNoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
