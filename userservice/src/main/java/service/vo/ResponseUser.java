package service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
// 유저 조회시 응답할 Json으로 만들 객체
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
//데이터가 채워지지 않을경우 Null이 들어갈텐데 불필요한 데이터를 제어하기위한 어노테이션 포함한다 not null 만
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private  String encryptedPwd;  //암호화된 pw

    private List<ResponseOrder> orders;

}
