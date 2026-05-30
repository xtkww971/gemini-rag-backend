package com.documentSearcher.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel; // 💡 공통 인터페이스 임포트
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        // 💡 주입받은 chatModel을 builder()의 인자로 직접 전달합니다.
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                    당신은 전문 문서 작성가입니다. 
                    입력되는 정돈되지 않은 텍스트를 분석하여 구조화된 기술 문서(Markdown) 형태로 가공해 주세요.
                    반드시 핵심 요약, 상세 내용, 결론 또는 액션 아이템의 구조를 갖춰야 합니다.
                    """)
                .build();
    }
}