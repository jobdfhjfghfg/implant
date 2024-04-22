package project.teamproject.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private Long id;

    @NotEmpty(message = "회원 아이디는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String position;
    private String department;
    private String phone;
    private String grade;
}
