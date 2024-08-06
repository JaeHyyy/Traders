//package com.exam.controller;

//
//import com.exam.dto.MovementDTO;
//import com.exam.service.MovementService;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.qrcode.QRCodeWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class QrCodeDivisionDataController4 {
//
//    @Autowired
//    private MovementService movementService;
//
//    @GetMapping("/qrcodeDivision")
//    public ResponseEntity<ByteArrayResource> getQrCodeDivision(@RequestParam String date) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate parsedDate;
//
//            try {
//                parsedDate = LocalDate.parse(date, formatter);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(new ByteArrayResource("날짜 형식을 확인해주세요. Use yyyy-MM-dd.".getBytes()));
//            }
//
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//            List<MovementDTO> movements = groupedData.get(parsedDate);
//
//            if (movements == null || movements.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            StringBuilder data = new StringBuilder();
//            for (MovementDTO movement : movements) {
//                data.append(movement.toString()).append("\n");
//            }
//
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//
//            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//            ByteArrayResource byteArrayResource = new ByteArrayResource(pngOutputStream.toByteArray());
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode_division_" + parsedDate + ".png\"")
//                    .contentType(MediaType.IMAGE_PNG)
//                    .contentLength(byteArrayResource.contentLength())
//                    .body(byteArrayResource);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/qrcodeDivisions")
//    public ResponseEntity<Map<LocalDate, String>> getQrCodeDivisions() {
//        try {
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//            Map<LocalDate, String> qrCodeData = new HashMap<>();
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//            for (Map.Entry<LocalDate, List<MovementDTO>> entry : groupedData.entrySet()) {
//                LocalDate date = entry.getKey();
//                List<MovementDTO> movements = entry.getValue();
//
//                StringBuilder data = new StringBuilder();
//                for (MovementDTO movement : movements) {
//                    data.append(movement.toString()).append("\n");
//                }
//
//                BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//                byte[] pngData = pngOutputStream.toByteArray();
//                String base64Png = Base64.getEncoder().encodeToString(pngData);
//
//                qrCodeData.put(date, base64Png);
//            }
//
//            return ResponseEntity.ok(qrCodeData);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//}

//package com.exam.controller;
//
//import com.exam.dto.MovementDTO;
//import com.exam.service.MovementService;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.qrcode.QRCodeWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class QrCodeDivisionDataController4 {
//
//    @Autowired
//    private MovementService movementService;
//
//    @GetMapping("/qrcodeDivision")
//    public ResponseEntity<ByteArrayResource> getQrCodeDivision(@RequestParam String date) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate parsedDate;
//
//            try {
//                parsedDate = LocalDate.parse(date, formatter);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(new ByteArrayResource("날짜 형식을 확인해주세요. Use yyyy-MM-dd.".getBytes()));
//            }
//
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//            List<MovementDTO> movements = groupedData.get(parsedDate);
//
//            if (movements == null || movements.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            StringBuilder data = new StringBuilder();
//            for (MovementDTO movement : movements) {
//                data.append(movement.toString()).append("\n");
//            }
//
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//
//            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//            ByteArrayResource byteArrayResource = new ByteArrayResource(pngOutputStream.toByteArray());
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode_division_" + parsedDate + ".png\"")
//                    .contentType(MediaType.IMAGE_PNG)
//                    .contentLength(byteArrayResource.contentLength())
//                    .body(byteArrayResource);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/qrcodeDivisions")
//    public ResponseEntity<Map<LocalDate, String>> getQrCodeDivisions() {
//        try {
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//            Map<LocalDate, String> qrCodeData = new HashMap<>();
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//            for (Map.Entry<LocalDate, List<MovementDTO>> entry : groupedData.entrySet()) {
//                LocalDate date = entry.getKey();
//                List<MovementDTO> movements = entry.getValue();
//
//                StringBuilder data = new StringBuilder();
//                for (MovementDTO movement : movements) {
//                    data.append(movement.toString()).append("\n");
//                }
//
//                BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//                byte[] pngData = pngOutputStream.toByteArray();
//                String base64Png = Base64.getEncoder().encodeToString(pngData);
//
//                qrCodeData.put(date, base64Png);
//            }
//
//            return ResponseEntity.ok(qrCodeData);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body(new HashMap<>());
//        }
//    }
//}

package com.exam.controller;

import com.exam.dto.MovementDTO;
import com.exam.service.MovementService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper; // ++
import com.fasterxml.jackson.databind.ObjectMapper; // ++
@RestController
@RequestMapping("/api")
public class QrCodeDivisionDataController4 {

    @Autowired
    private MovementService movementService;

//    // 모든 QR 데이터
//    @GetMapping("/qrcodeDivisions")
//    public ResponseEntity<Map<String, Map<String, String>>> getQrCodeDivisions() {
//        try {
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//            Map<String, Map<String, String>> qrCodeData = new HashMap<>();
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//            for (Map.Entry<LocalDate, List<MovementDTO>> entry : groupedData.entrySet()) {
//                LocalDate date = entry.getKey();
//                List<MovementDTO> movements = entry.getValue();
//
//                StringBuilder data = new StringBuilder();
//                for (MovementDTO movement : movements) {
//                    data.append(movement.toString()).append("\n");
//                }
//
//                BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//                byte[] pngData = pngOutputStream.toByteArray();
//                String base64Png = Base64.getEncoder().encodeToString(pngData);
//
//                Map<String, String> qrData = new HashMap<>();
//                qrData.put("image", base64Png);
//                qrData.put("text", data.toString());
//
//                qrCodeData.put(date.toString(), qrData);
//            }
//
//            return ResponseEntity.ok(qrCodeData);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
    
 // 물품상태업데이트(대기->완료)
//    @PostMapping("/updateStatuses")
//    public ResponseEntity<List<MovementDTO>> updateStatuses(@RequestBody List<MovementDTO> movementsToUpdate) {
//        try {
//            System.out.println("업데이트할 데이터: " + movementsToUpdate);
//            List<MovementDTO> updatedMovements = movementService.updateStatuses(movementsToUpdate);
//            return ResponseEntity.ok(updatedMovements);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    
   
}

