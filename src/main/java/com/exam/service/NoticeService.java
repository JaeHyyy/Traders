package com.exam.service;

import java.util.List;

import com.exam.entity.Notice;

public interface NoticeService {
	Notice sendNotice(Notice notice);
    List<Notice> getNoticesForBranch(String branchId);
    List<Notice> getAllGlobalNotices();
}
