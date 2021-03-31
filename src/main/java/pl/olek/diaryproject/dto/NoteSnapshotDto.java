package pl.olek.diaryproject.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteSnapshotDto {

    private Long noteId;
    private Integer noteVersion;
    private String title;
    private String content;


}
