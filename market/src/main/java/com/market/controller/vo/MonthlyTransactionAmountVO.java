package com.market.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 每月交易额
 */
@Data
public class MonthlyTransactionAmountVO {

    private String month;

    private BigDecimal amount;
}