package pl.olek.diaryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.olek.diaryproject.entity.NoteSnapshot;

import java.util.Set;

@Repository
public interface NoteSnapshotRepo extends JpaRepository<NoteSnapshot, Long> {
 
    Set<NoteSnapshot> findAllByDeletedIsFalse();

}
