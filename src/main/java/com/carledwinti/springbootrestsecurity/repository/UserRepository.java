package com.carledwinti.springbootrestsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carledwinti.springbootrestsecurity.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	public User findByUsername(String username);
}
