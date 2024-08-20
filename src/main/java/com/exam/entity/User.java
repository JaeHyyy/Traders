package com.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

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
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "branchid")
    private String branchId; // 아이디(이메일)

    @Column(name = "passwd")
    private String passwd; // 비밀번호

    @Column(name = "branchname")
    private String branchName; // 사업자명
    
    @Column(name = "branchnum")
    private String branchNum; // 사업자번호

    @Lob
    @Column(name = "branchimage")
    private String branchImage; // 사업자등록증

    @Column(name = "post")
    private String post; // 우편번호

    @Column(name = "addr1")
    private String addr1; // 지점주소

    @Column(name = "addr2")
    private String addr2; // 지점주소2

    @Column(name = "phonenum")
    private String phoneNum; // 전화번호
    
    @Column(name = "role")
    private String role; //역할
}
