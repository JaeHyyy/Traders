package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {

}
