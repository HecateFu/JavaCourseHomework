package learn.java.dbop.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class Order {
    private Long id;
    private String orderCode;
    private String orderStatus;
    private BigDecimal amount;
    private BigDecimal postFee;
    private BigDecimal payment;
    private String orderUser;

    public Order(String orderCode, BigDecimal amount, BigDecimal postFee, String orderUser) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.postFee = postFee;
        this.orderUser = orderUser;
        this.payment = this.amount.add(this.postFee);
        this.orderStatus="00";
    }
}
