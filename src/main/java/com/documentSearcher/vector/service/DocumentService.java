package com.documentSearcher.vector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ChatClient chatClient;

    public String convertToStructuredDocument(String rawText) {
        return this.chatClient.prompt()
                .user(rawText)
                .call()
                .content(); // 가공된 마크다운 결과물 텍스트 반환
    }
}
