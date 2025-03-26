package com.ach.memories_back.service;

import org.springframework.http.ResponseEntity;

import com.ach.memories_back.common.dto.request.auth.IdCheckRequestDto;
import com.ach.memories_back.common.dto.request.auth.SignInRequestDto;
import com.ach.memories_back.common.dto.request.auth.SignUpRequestDto;
import com.ach.memories_back.common.dto.response.ResponseDto;
import com.ach.memories_back.common.dto.response.auth.SignInResponseDto;

public interface AuthService {
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
