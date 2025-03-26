package com.ach.memories_back.service.implement;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ach.memories_back.common.dto.request.openai.ChatRequestDto;
import com.ach.memories_back.common.dto.response.openai.ChatResponseDto;
import com.ach.memories_back.common.vo.GptMessageVO;
import com.ach.memories_back.service.OpenAIService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImplement implements OpenAIService {

    private final WebClient webClient;

    @Override
    public String chat() {
        String content = "ChatGPT를 설명해줘.";
        List<GptMessageVO> message = List.of(new GptMessageVO("user", content));

        ChatRequestDto requestBody = new ChatRequestDto("gpt-4o-mini", message);

        ChatResponseDto responseBody = webClient.post()
                                                .uri("/chat/completions")
                                                .bodyValue(requestBody)
                                                .retrieve()
                                                .bodyToMono(ChatResponseDto.class)
                                                .block();

        if (responseBody == null || responseBody.getChoices() == null || responseBody.getChoices().isEmpty()) {
            return null;
        }

        return responseBody.getChoices().get(0).getMessage().getContent();
    }
    
}
