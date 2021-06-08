package service.vo;

import lombok.Data;

import java.util.Date;
// 유저 조회시 응답할 Json으로 만들어서 ResponseUser 에 담을 객체
@Data
public class ResponseOrder {
    private String productid;
    private int qty;
    private int unitprice;
    private int totalprice;
    private String orderid;
}
