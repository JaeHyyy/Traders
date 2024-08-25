package com.exam.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NoticeDTO {
	
	private Long noticeId;
    private String content;
    private LocalDate noticedate;
    private Boolean isGlobal;
    private String branchId;
    private String branchName;

}
