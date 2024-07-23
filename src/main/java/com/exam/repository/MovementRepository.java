package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

}
