package pl.olek.diaryproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "note_snapshots")
public class NoteSnapshot extends ParentEntity{

    private Integer noteVersion;
    private String title;
    private String content;

    @ManyToOne
    private Note note;

}
