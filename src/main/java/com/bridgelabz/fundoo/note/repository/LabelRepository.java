package com.bridgelabz.fundoo.note.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.user.model.User;

public interface LabelRepository extends  JpaRepository<Label, Long>{
	Optional<Label> findByName(String name);
	Optional<Label> findByUserId(Long userid);
	//List<Label> findByUser(User user);
	List<Label> findAllByUser(User user);
	Optional<Label> findByLableIdAndUser(long labelId,User user);

}
