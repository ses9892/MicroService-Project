package service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUpdateUser {
    @NotNull(message = "pwd cannot be null")
    @Size(min = 4,message = "pwd less than more 4")
    private String pwd;
    @NotNull(message = "pwd cannot be null")
    @Size(min = 4,message = "updatePwd less than more 4")
    private String updatePwd;
}

