package com.exam.controller;

import com.exam.dto.MovementDTO;

import com.exam.service.MovementService;
import com.exam.service.QrCodeService;
import com.mysql.cj.log.Log;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class QrCodeController {
	public QrCodeService qrCodeService;
    

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;

    }

    @GetMapping("/{branchid}/qrcode")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String branchid, @RequestParam("date") String date) {
        try {
            // 이동할 URL을 branchid와 date만 포함하도록 생성
            String qrCodeText = "http://traders5reactbucket.s3-website-ap-northeast-1.amazonaws.com/mobile/login?branchid=" + URLEncoder.encode(branchid, "UTF-8")
                              + "&date=" + URLEncoder.encode(date, "UTF-8");
//            String qrCodeText = "http://192.168.0.109:3000/mobile/login?branchid=" + URLEncoder.encode(branchid, "UTF-8")
//            + "&date=" + URLEncoder.encode(date, "UTF-8");
            System.out.println("Generated QR Code URL: " + qrCodeText);

            // qr코드 이미지 생성
            byte[] qrCodeImage = qrCodeService.generateQRCode(qrCodeText, 250, 250);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcode.png");

            return ResponseEntity.ok().headers(headers).body(qrCodeImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    


}