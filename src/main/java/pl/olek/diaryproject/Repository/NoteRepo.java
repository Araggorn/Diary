package pl.olek.diaryproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.olek.diaryproject.Entity.Note;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository <Note, Long> {

    List<Note> findAllByIsDeletedIsFalse();


}
