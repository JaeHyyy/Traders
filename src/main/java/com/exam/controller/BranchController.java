package com.exam.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.SecurityConfig;
import com.exam.dto.BranchDTO;
import com.exam.service.BranchService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody BranchDTO branchDTO) {
        try {
            log.debug("logger:{}", branchDTO);
            
            // branchId 보안설정
            branchDTO.setBranchId(UUID.randomUUID().toString());

            // 비밀번호 암호화
            String encryptedPassword = new BCryptPasswordEncoder().encode(branchDTO.getPasswd());
            branchDTO.setPasswd(encryptedPassword);

            branchService.save(branchDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(branchDTO);
        } catch (Exception e) {
            log.error("Error during signup", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("회원가입실패: " + e.getMessage());
        }
    }


    @GetMapping("/hello") // 테스트를 위해 보안 없이 접근 가능하도록 설정
    public List<BranchDTO> findAll() {
        return branchService.findAll();
    }
}
