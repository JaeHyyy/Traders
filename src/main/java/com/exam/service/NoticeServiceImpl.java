package com.exam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.exam.entity.Notice;
import com.exam.repository.NoticeRepository;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
	
	NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public Notice sendNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public List<Notice> getNoticesForBranch(String branchId) {
		return noticeRepository.findByUser_BranchId(branchId);
	}

	@Override
	public List<Notice> getAllGlobalNotices() {
		return noticeRepository.findByIsGlobalTrue();
	}

}
