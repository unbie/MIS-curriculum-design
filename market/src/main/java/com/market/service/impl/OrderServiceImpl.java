package com.market.service.impl;

import com.market.controller.dto.GoodDTO;
import com.market.controller.dto.OrderDTO;
import com.market.controller.dto.OrderPayWayDTO;
import com.market.controller.dto.OrderStatusDTO;
import com.market.controller.vo.*;
import com.market.entity.Comment;
import com.market.entity.Order;
import com.market.entity.PersonShoppingAddress;
import com.market.mapper.GoodMapper;
import com.market.mapper.OrderMapper;
import com.market.mapper.PersonMapper;
import com.market.mapper.PersonShoppingAddressMapper;
import com.market.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodMapper goodMapper;
    @Resource
    private PersonMapper personMapper;
    @Resource
    private PersonShoppingAddressMapper personShoppingAddressMapper;

    @Override
    public Integer getNewOrderId() {
        return orderMapper.getMaxId();
    }

    @Override
    public boolean insertOrder(Order order) {

        if (orderMapper.insertOrder(order))
            return true;
        else return false;
    }

    @Override
    public List<OrderVo> listO() {
        return orderMapper.listOrderVo();
    }

    @Override
    public List<OrderGoodVO> listOG() {
        return goodMapper.listOrderGoodVo();
    }

    @Override
    public List<OrderCustomerVO> listOC() {
        return personShoppingAddressMapper.listOrderCustomerVo();
    }

    @Override
    public Boolean removeById(Integer orderId) {
        if (orderMapper.deleteByOrderId(orderId) != 0) {
            return true;
        } else {
            return false;
        }
    }

    //管理员手动关闭
    @Override
    public Order saveOrUpdate(OrderVo orderVo) {
        Order order = new Order();

        // 将 orderVo 对象中的属性值赋值给 order 对象
        order.setOrderId(orderVo.getOrderId());
        order.setOrderNo(orderVo.getOrderNo());
        order.setGoodId(orderVo.getGoodId());
        order.setAddressId(orderVo.getAddressId());
        order.setCustomerId(orderVo.getCustomerId());
        order.setTotalPrice(orderVo.getTotalPrice());
        order.setOrderStatus(orderVo.getOrderStatus());
        order.setPayTime(orderVo.getPayTime());
        order.setPayWays(orderVo.getPayWays());
        order.setPayStatus(orderVo.getPayStatus());
        order.setCount(orderVo.getCount());
        order.setCreateTime(orderVo.getCreateTime());
        return order;
    }

    @Override
    public boolean updateById(Order order) {
        Integer orderId = order.getOrderId();
        if (orderMapper.updateByOrderId(orderId) != 0) {
            return true;
        }
        return false;
    }

    // 获取本日交易额
    @Override
    public BigDecimal calculateDailyTransactionAmount() {
        return orderMapper.calculateTransactionAmountByDate(LocalDate.now());
    }

    // 获取本月交易额
    @Override
    public BigDecimal calculateMonthlyTransactionAmount() {
        YearMonth currentYearMonth = YearMonth.now();
        return orderMapper.calculateTransactionAmountByMonth(currentYearMonth.getYear(), currentYearMonth.getMonthValue());
    }

    // 获取年度交易额
    @Override
    public BigDecimal calculateAnnualTransactionAmount() {
        return orderMapper.calculateTransactionAmountByYear(Year.now());
    }

    // 获取今年每日交易额列表
    @Override
    public List<DailyTransactionAmountVO> getDailyTransactionAmount() {
        return orderMapper.getDailyTransactionAmount();
    }

    // 获取今年每月交易额列表
    @Override
    public List<MonthlyTransactionAmountVO> getMonthlyTransactionAmount() {
        return orderMapper.getMonthlyTransactionAmount();
    }

    @Override
    public List<OrderInfoVO> getOrdersByCustomerId(Integer customerId) {
        // 调用Mapper层的方法查询数据库并返回订单信息
        return orderMapper.getOrdersByCustomerId(customerId);
    }

    @Override
    public List<OrderInfoVO> getOrdersBySellerId(Integer sellerId) {
        return orderMapper.getOrdersBySellerId(sellerId);
    }

    @Override
    public OrderDTO getOrderInfo(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo(order.getOrderNo());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderStatus(order.getOrderStatus());

        // 获取商品信息
        GoodDTO goodDTO = orderMapper.findGoodByOrderId(orderId);
        orderDTO.setGoodName(goodDTO.getGoodName());
        orderDTO.setGoodImg(goodDTO.getImgUrl());

        // 获取买家信息
        String customerAccount = orderMapper.findCustomerAccountByOrderId(orderId);
        orderDTO.setCustomerAccount(customerAccount);

        // 获取卖家信息
        String sellerAccount = orderMapper.findSellerAccountByOrderId(orderId);
        orderDTO.setSellerAccount(sellerAccount);

        // 获取收件人信息
        PersonShoppingAddress address = orderMapper.findAddressByOrderId(orderId);
        if (address != null) {
            orderDTO.setShoppingPhone(address.getShoppingPhone());
            orderDTO.setShippingAddress(address.getShippingAddress());
        }

        // 获取评论信息
        Comment comment = orderMapper.findCommentByOrderId(orderId);
        if (comment != null) {
            orderDTO.setInitialComments(comment.getInitialComments());
            orderDTO.setModifiedComments(comment.getModifiedComments());
            orderDTO.setStars(comment.getStars());
        } else {
            orderDTO.setInitialComments("");
            orderDTO.setModifiedComments("");
            orderDTO.setStars(0);
        }

        return orderDTO;
    }

    @Override
    public void updateOrderStatus(OrderStatusDTO orderStatusDTO) {
        orderMapper.updateOrderStatus(orderStatusDTO.getOrderId(),
                orderStatusDTO.getOrderStatus(),
                orderStatusDTO.getPayStatus());
    }

    @Override
    public void updatePayWays(OrderPayWayDTO orderPayWayDTO) {
        Date payTime = new Date();  // 使用当前时间作为支付时间
        orderMapper.updatePayWays(orderPayWayDTO.getOrderId(),
                orderPayWayDTO.getPayWay(), payTime);
    }
}
