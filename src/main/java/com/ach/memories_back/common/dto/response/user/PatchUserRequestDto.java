package com.ach.memories_back.common.dto.response.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserRequestDto {
    private String name;
    private String profileImage;
    @NotBlank
    private String address;
    private String detailAddress;
    @Pattern(regexp="^(man|woman)$")
    private String gender;
    
    @Min(0)
    private Integer age;
    
}
