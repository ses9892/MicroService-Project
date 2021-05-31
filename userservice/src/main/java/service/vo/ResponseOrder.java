package service.vo;

import lombok.Data;

import java.util.Date;
// 유저 조회시 응답할 Json으로 만들어서 ResponseUser 에 담을 객체
@Data
public class ResponseOrder {
    private String productId;
    private int qty;
    private int unitPrice;
    private int totalPrice;
    private Date creatAt;

    private String orderId;
}
