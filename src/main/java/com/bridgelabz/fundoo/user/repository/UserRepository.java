package com.bridgelabz.fundoo.user.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
	Optional<User> findByEmail(String email);
	//UserDTO findById(UserDTO userDto);
	
}
