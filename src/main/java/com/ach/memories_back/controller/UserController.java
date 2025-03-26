package com.ach.memories_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ach.memories_back.common.dto.response.ResponseDto;
import com.ach.memories_back.common.dto.response.user.GetSignInUserResponseDto;
import com.ach.memories_back.common.dto.response.user.PatchUserRequestDto;
import com.ach.memories_back.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/sign-in")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(userId);
        return response;
    }  

    @PatchMapping({"", "/"})
    public ResponseEntity<ResponseDto> patchUser(
        @RequestBody @Valid PatchUserRequestDto resquestBody,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = userService.patchUser(resquestBody, userId);
        return response;
    }
}
