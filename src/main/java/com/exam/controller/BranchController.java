package com.exam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.BranchDTO;
import com.exam.entity.Branch;
import com.exam.security.JwtTokenResponse;
import com.exam.security.JwtTokenService;
import com.exam.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BranchController {
	
	BranchService branchService;
	JwtTokenService jwtTokenService; // 토큰생성

	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}
	
    // 회원가입
    @PostMapping(value={"/signup"})
    public ResponseEntity<BranchDTO> signup(@RequestBody BranchDTO branchDTO) {
        log.debug("logger:{}", branchDTO);
        
        // 비번 암호화 필수
        String encryptedPassword = new BCryptPasswordEncoder().encode(branchDTO.getPasswd());
        branchDTO.setPasswd(encryptedPassword);
        
        branchService.save(branchDTO);
        
        return ResponseEntity.ok(branchDTO);
    }
    
	
	@GetMapping("/hello") //test 하려고 security 피해서 적은거임. 쓰셈!
	public List<BranchDTO> findAll(){
		return branchService.findAll();
	}

}
