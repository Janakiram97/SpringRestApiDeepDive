package com.example.springrestapideepdive.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
   List<UserDetailsEntity> findByRole(String role);
}
