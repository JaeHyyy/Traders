package com.exam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            log.debug("Received signup request: {}", userDTO);

            // 비밀번호 암호화
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPasswd());
            userDTO.setPasswd(encryptedPassword);

            userService.save(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (Exception e) {
            log.error("Signup error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed: " + e.getMessage());
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
    
    // 로그인 성공시 뜨도록 했는데 react 서버를 띄우면 될려나..?
	@GetMapping("/success")
	public String login_success() {
		log.debug("logger:{}", "hello");
		return "hello world";
	}
	

    @GetMapping("/hello") // 테스트를 위해 보안 없이 접근 가능하도록 설정
    public List<UserDTO> findAll() {
        return userService.findAll();
    }
}

