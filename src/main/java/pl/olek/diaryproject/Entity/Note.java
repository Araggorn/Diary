package pl.olek.diaryproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    Set<NoteSnapshot> noteSnapshots = new HashSet<>();



    @Override
    public String toString() {
        return new StringJoiner(", ", Note.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("content='" + content + "'")
                .toString();
    }
}
