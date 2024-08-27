package com.exam.service;

import java.util.List;

import com.exam.dto.NoticeDTO;
import com.exam.entity.Notice;

public interface NoticeService {
	Notice sendNotice(Notice notice);
    List<NoticeDTO> getNoticesForBranch(String branchId);
    List<NoticeDTO> getAllGlobalNotices();
    List<NoticeDTO> findAllNotices();
    void deleteNoticeById(Long noticeId);  // 공지 삭제
}
