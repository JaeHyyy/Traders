package com.exam.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
@RequestMapping("/api")
public class QrCodeController {

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode() {
        String qrContent = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EA%B2%80%EC%88%98"; 
        //qr코드를 찍으면 연결 될 url+ localhost 대신 ip주소 현재는 ssg ip 10.10.10.197
        
        //qr코드의 크기와 색 설정
        int width = 300;
        int height = 300;
        int onColor = 0x00000000; // QR 코드의 검은색 부분
        int offColor = 0xFFFFFFFF; // QR 코드의 흰색 부분

        QRCodeWriter qrCodeWriter = new QRCodeWriter(); //qr코드를 만들때 쓰는 클래스
        BitMatrix bitMatrix;
        try { //QRCodeWriter은 예외처리가 필수
            bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            return ResponseEntity.status(500).body(null);
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? onColor : offColor);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }

        byte[] imageBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity.ok().headers(headers).body(imageBytes);
    }
}