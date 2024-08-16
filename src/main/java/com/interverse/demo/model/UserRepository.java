package com.interverse.demo.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.interverse.demo.dto.UserDTO;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByAccountNumber(String accountNumber);

	@Query("SELECT new com.interverse.demo.dto.UserDTO(u.id, u.accountNumber, u.email, u.nickname, u.added) FROM User u WHERE u.id = :id")
    Optional<UserDTO> findUserDTOById(@Param("id") Integer id);


}
