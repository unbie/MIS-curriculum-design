package com.market.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前端的结果
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    //编码 200/400
    private int resultCode;
    // 成功/失败
    private String message;
    // 总记录条数
    private Long total;
    // 数据
    private Object data;

    public static Result fail() {
        return result(400, "失败", 0L, null);
    }

    public static Result fail(String message) {
        return result(400, message, 0L, null);
    }

    public static Result success() {
        return result(200, "成功", 0L, null);
    }

    public static Result success(Object data) {
        return result(200, "成功", 0L, data);
    }

    public static Result success(Object data, Long total) {
        return result(200, "成功", total, data);
    }

    // 基本结果生成方法
    private static Result result(int resultCode, String msg, Long total, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setMessage(msg);
        result.setTotal(total);
        result.setData(data);
        return result;
    }

}
