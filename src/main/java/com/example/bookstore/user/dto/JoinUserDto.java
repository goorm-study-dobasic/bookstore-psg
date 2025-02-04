package com.example.bookstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinUserDto {

    @NotBlank(message = "이메일을 입력해야 합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다")
    @Size(min = 8, max = 16, message = "비밀번호는 8글자 이상, 16글자 이하여야 합니다")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$", message = "영문 대소문자, 숫자, 특수문자를 포함하여야 합니다")
    private String password;

    @NotBlank(message = "전화번호를 입력해야 합니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식은 000-0000-0000이어야 합니다.")
    private String phone;

    @NotBlank(message = "닉네임을 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 최소 2자리, 최대 10자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "주소를 입력해야 합니다.")
    private String zipcode;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String streetAddr;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String detailAddr;
}
