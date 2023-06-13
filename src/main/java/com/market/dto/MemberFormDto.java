package com.market.dto;

import com.market.constant.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberFormDto {

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password; // 비밀번호

    @NotEmpty(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordCheck; // 비밀번호 확인

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name; // 이름

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email; // 이메일

    @NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{10,11}", message = "휴대폰 번호는 10자리 또는 11자리의 숫자로 입력해주세요.")
    private String phone; // 휴대폰 번호

    @NotNull(message = "년은 필수 입력 값입니다.")
    private Integer birthYear; // 년

    @NotNull(message = "월은 필수 입력 값입니다.")
    private Integer birthMonth; // 월

    @NotNull(message = "일은 필수 입력 값입니다.")
    private Integer birthDay; // 일

    private Gender gender; // 성별

//    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address1; // 기본주소

//    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address2; // 상세주소

    private LocalDateTime regDate; // 가입일

}
