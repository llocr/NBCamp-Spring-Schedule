package com.sparta.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.schedule.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@EntityGraph(attributePaths = "scheduleList")
	Optional<User> findById(Long id);

	Optional<User> findByUsername(String username);

}
