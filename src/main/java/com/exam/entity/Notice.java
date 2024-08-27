package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "Notice") //공지 메세지 테이블
public class Notice {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long noticeId;

	@Column(nullable = false, columnDefinition = "TEXT")
	String content;
	
	LocalDate noticedate;
	
	@Column(nullable = false)
	Boolean isGlobal;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private User user;
	
	
	@PrePersist
	protected void onCreate() {
		this.noticedate = LocalDate.now();
	}
}
