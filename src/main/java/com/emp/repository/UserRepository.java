package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emp.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
