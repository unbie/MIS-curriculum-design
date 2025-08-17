package com.market.service;

import com.market.controller.dto.OrderDTO;
import com.market.controller.dto.OrderPayWayDTO;
import com.market.controller.dto.OrderStatusDTO;
import com.market.controller.vo.*;
import com.market.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>

 */
public interface OrderService extends IService<Order> {

    //展示订单数据部分
    Integer getNewOrderId();

    boolean insertOrder(Order order);

    List<OrderVo> listO();

    List<OrderGoodVO> listOG();

    List<OrderCustomerVO> listOC();

    //重写删除订单方法
    Boolean removeById(Integer orderId);

    //管理手动关闭订单 管理员关闭
    Order saveOrUpdate(OrderVo orderVo);

    //重写更新订单方法
    boolean updateById(Order order);

    // 获取本日交易额
    BigDecimal calculateDailyTransactionAmount();

    // 获取本月交易额
    BigDecimal calculateMonthlyTransactionAmount();

    // 获取年度交易额
    BigDecimal calculateAnnualTransactionAmount();

    // 获取今年每日交易额列表
    List<DailyTransactionAmountVO> getDailyTransactionAmount();

    // 获取今年每月交易额列表
    List<MonthlyTransactionAmountVO> getMonthlyTransactionAmount();

    List<OrderInfoVO> getOrdersByCustomerId(Integer customerId);

    List<OrderInfoVO> getOrdersBySellerId(Integer sellerId);

    OrderDTO getOrderInfo(Integer orderId);

    void updateOrderStatus(OrderStatusDTO orderStatusDTO);

    void updatePayWays(OrderPayWayDTO orderPayWayDTO);
}
