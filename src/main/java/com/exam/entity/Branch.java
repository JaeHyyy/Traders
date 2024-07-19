package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
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
public class Branch {

    @Id
    @Column(name = "branchid")
    private String branchId;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "branchnum")
    private String branchNum;

    @Column(name = "branchimage")
    private String branchImage;

    @Column(name = "post")
    private String post;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "phonenum")
    private String phoneNum;
}
