package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>{
	List<Notice> findByUser_BranchId(String branchId);
    List<Notice> findByIsGlobalTrue();
}
