package com.documentSearcher.vector.controller;

import com.documentSearcher.DTO.DocumentRequest;
import com.documentSearcher.vector.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @PostMapping("/format")
    public ResponseEntity<String> formatText(@RequestBody DocumentRequest request) {
        if (request.getRawText() == null || request.getRawText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("본문이 없습니다.");
        }

        // 제미나이를 통해 변환된 마크다운 문서 생성
        String formattedDocument = documentService.convertToStructuredDocument(request.getRawText());
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "doc_" + timestamp + ".md";

        try {
            // 3. 프로젝트 루트 하위의 'generated_files' 폴더에 저장하도록 설정
            Path directoryPath = Paths.get("generated");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath); // 폴더가 없으면 자동 생성
            }

            Path filePath = directoryPath.resolve(fileName);

            // 4. 마크다운 파일 쓰기
            Files.writeString(filePath, formattedDocument, StandardCharsets.UTF_8);
            System.out.println("MD 파일이 성공적으로 저장되었습니다: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(formattedDocument);
    }
}
