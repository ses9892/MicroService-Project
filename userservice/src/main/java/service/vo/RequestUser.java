package service.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// 클라이언트에게 요청값 JSON 을받고 JSON 형태가 RequestUser 타입형태로 변환된다.
// 요청값받기용 + 유효성검사용
@Data
public class RequestUser {

    @NotNull(message = "이메일을 반드시 비어져있으면 안됩니다!")
    @Size(min = 1)
    @Email
    private  String email;

    @NotNull(message = "비밀번호 반드시 입력합니다.")
    @Size(min = 4,message = "비밀번호는 최소 4글자 이상 입력합니다.")
    private String pwd;

    @NotNull(message = "이름은 반드시 비어져있으면 안됩니다!")
    @Size(min = 2,message = "이름은 최소 2글자 이상 입력합니다.")
    private String name;
}
