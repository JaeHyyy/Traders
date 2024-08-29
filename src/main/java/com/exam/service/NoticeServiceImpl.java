package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MovementDTO;
import com.exam.dto.NoticeDTO;
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
	public List<NoticeDTO> getNoticesForBranch(String branchId) {
		ModelMapper mapper = new ModelMapper();
	    List<Notice> list = noticeRepository.findByUser_BranchId(branchId);
	    List<NoticeDTO> noticeList = list.stream()
	    		.map(e->mapper.map(e, NoticeDTO.class))
                .collect(Collectors.toList());
	    return noticeList;
	}

	@Override
	public List<NoticeDTO> getAllGlobalNotices() {
		ModelMapper mapper = new ModelMapper();
	    List<Notice> list = noticeRepository.findByIsGlobalTrue();
	    List<NoticeDTO> noticeList = list.stream()
	    		.map(e->mapper.map(e, NoticeDTO.class))
                .collect(Collectors.toList());
	    return noticeList;
	}
	
	@Override
    public List<NoticeDTO> findAllNotices() {
		ModelMapper mapper = new ModelMapper();
        List<Notice> list = noticeRepository.findAll();
        return list.stream()
                   .map(e -> mapper.map(e, NoticeDTO.class))
                   .collect(Collectors.toList());
    }
	
	@Override
    public void deleteNoticeById(Long noticeId) {
        noticeRepository.deleteById(noticeId);  // 공지 삭제
    }

}
