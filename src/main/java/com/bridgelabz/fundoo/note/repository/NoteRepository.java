package com.bridgelabz.fundoo.note.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.model.User;

@Repository
public interface NoteRepository extends JpaRepository<Note ,Long>{
	List<Note> findByUser(User user);

}
