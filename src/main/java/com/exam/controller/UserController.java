package com.exam.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.config.SecurityConfig;
import com.exam.dto.UserDTO;
import com.exam.entity.User;
import com.exam.security.JwtTokenResponse;
import com.exam.security.JwtTokenService;
import com.exam.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestParam("branchId") String branchId,
            @RequestParam("passwd") String passwd,
            @RequestParam("branchName") String branchName,
            @RequestParam("nickname") String nickname,
            @RequestParam("branchNum") String branchNum,
            @RequestParam("post") String post,
            @RequestParam("addr1") String addr1,
            @RequestParam("addr2") String addr2,
            @RequestParam("phoneNum") String phoneNum,
            @RequestParam("branchImage") MultipartFile branchImage) {

        String uploadDir = "C:\\springboot_study\\sts-4.22.1.RELEASE\\workspace\\Traders\\src\\main\\resources\\static\\images";
        try {
            // 비밀번호 암호화
            String encryptedPassword = new BCryptPasswordEncoder().encode(passwd);

            // 파일 저장
            String fileName = branchImage.getOriginalFilename();
            Path path = Paths.get(uploadDir).resolve(fileName);
            Files.createDirectories(path.getParent());
            try (InputStream inputStream = branchImage.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }

            // UserDTO 객체 생성 및 설정
            UserDTO userDTO = new UserDTO();
            userDTO.setBranchId(branchId);
            userDTO.setPasswd(encryptedPassword);
            userDTO.setBranchName(branchName);
//            userDTO.setNickname(nickname);
            userDTO.setBranchNum(branchNum);
            userDTO.setPost(post);
            userDTO.setAddr1(addr1);
            userDTO.setAddr2(addr2);
            userDTO.setPhoneNum(phoneNum);
            userDTO.setBranchImage(fileName); // 파일 이름만 저장 (경로가 아닌)

            // 데이터베이스에 저장
            userService.save(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (DataIntegrityViolationException e) {
            log.error("데이터 무결성 오류: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("데이터 무결성 오류: " + e.getMessage());
        } catch (Exception e) {
            log.error("회원가입 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }




    
    // 로그인 처리 + token 생성후 반환
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> map) {
        try {
            String branchId = map.get("branchId");
            String passwd = map.get("passwd");

            log.debug("Login attempt with branchId: {}", branchId);
            log.debug("Login attempt with passwd: {}", passwd);

            User user = userService.findByBranchId(branchId);
            log.debug("Found user: {}", user);

            if (user != null) {
                log.debug("Stored encrypted passwd: {}", user.getPasswd());
                if (new BCryptPasswordEncoder().matches(passwd, user.getPasswd())) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);

                    String token = jwtTokenService.generateToken(auth);
                    log.info("Generated token: {}", token);

                    return ResponseEntity.ok(new JwtTokenResponse(token));
                } else {
                    log.warn("Password does not match for branchId={}", branchId);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Incorrect password");
                }
            } else {
                log.warn("No user found with branchId={}", branchId);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: User not found");
            }
        } catch (Exception e) {
            log.error("Login error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
        }
    }
}