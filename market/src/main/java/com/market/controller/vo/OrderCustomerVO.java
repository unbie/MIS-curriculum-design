package com.market.controller.vo;

import lombok.Data;

/**
 * <p>
 * 订单管理中的小界面
 * </p>
 * <p>
 * 相关联的两个表格
 * person_shipping_address
 * person
 * </p>
 */
@Data
public class OrderCustomerVO {

    private Integer customerId;
    private String customerName;
    private String shippingPerson;
    private String shippingAddress;
    private Long shippingPhone;
}
