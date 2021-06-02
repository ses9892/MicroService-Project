package service.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//클라이언트 요청으로 부터 사용자로그인 정보를 저장하기위한 클래스
@Data
public class RequestLogin {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2,message = "Email not be less than 2 char")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 4,message = "Password not be less than 4 char")
    private String pwd;

}
