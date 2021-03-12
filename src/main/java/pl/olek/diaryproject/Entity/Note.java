package pl.olek.diaryproject.Entity;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "diaryNotes")
public class Note extends ParentEntity{

    private String title;
    private String content;
    private Long version;
    private Long noteId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        if (!super.equals(o)) return false;
        Note note = (Note) o;
        return Objects.equal(title, note.title) && Objects.equal(content, note.content) && Objects.equal(version, note.version) && Objects.equal(noteId, note.noteId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), title, content, version, noteId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Note.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("content='" + content + "'")
                .add("version=" + version)
                .add("noteId=" + noteId)
                .toString();
    }
}
