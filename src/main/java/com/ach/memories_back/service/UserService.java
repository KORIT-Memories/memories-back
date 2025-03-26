package com.ach.memories_back.service;

import org.springframework.http.ResponseEntity;

import com.ach.memories_back.common.dto.response.ResponseDto;
import com.ach.memories_back.common.dto.response.user.GetSignInUserResponseDto;
import com.ach.memories_back.common.dto.response.user.PatchUserRequestDto;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
    ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto, String userId);
}
