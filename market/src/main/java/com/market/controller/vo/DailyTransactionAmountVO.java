package com.market.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 每日交易额
 */
@Data
public class DailyTransactionAmountVO {

    private String date;

    private BigDecimal amount;

}