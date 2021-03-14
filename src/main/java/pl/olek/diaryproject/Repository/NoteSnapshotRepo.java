package pl.olek.diaryproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.olek.diaryproject.Entity.NoteSnapshot;

@Repository
public interface NoteSnapshotRepo extends JpaRepository <NoteSnapshot, Long> {


}
