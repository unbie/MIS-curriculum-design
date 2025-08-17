package com.market.controller;


import com.market.common.Result;
import com.market.controller.dto.OrderDTO;
import com.market.controller.dto.OrderPayWayDTO;
import com.market.controller.dto.OrderStatusDTO;
import com.market.controller.vo.*;
import com.market.entity.Order;
import com.market.service.OrderService;
import com.market.utils.NumberUtil;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //订单管理 初始界面 展示订单部分的详情数据
    @GetMapping("/listO")
    public Result listO(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<OrderVo> orderVoList = orderService.listO();
            return Result.success(orderVoList, (long) orderVoList.size());
        } else {
            return Result.fail();
        }

    }

    //订单详情 商品部分 展示当前订单对应商品的详情信息
    @GetMapping("/listOG")
    public Result listOG(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<OrderGoodVO> orderGoodVOList = orderService.listOG();
            return Result.success(orderGoodVOList, (long) orderGoodVOList.size());
        } else {
            return Result.fail();
        }

    }

    //订单详情 消费者部分 展示当前订单对应的消费者和收件人的详情信息
    @GetMapping("/listOC")
    public Result listOC(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<OrderCustomerVO> orderCustomerVOList = orderService.listOC();
            return Result.success(orderCustomerVOList, (long) orderCustomerVOList.size());
        } else {
            return Result.fail();
        }

    }

    //删除订单 根据订单序号删除对应的订单
    @GetMapping("/delete")
    public Result deleteOrder(@RequestParam("orderId") List<Integer> orderIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean flag = false;
        if (TokenUtil.checkToken(token)) {
            for (Integer orderId : orderIdList) {
                flag = orderService.removeById(orderId);
            }
            if (flag) {
                return Result.success();
            }
            return Result.fail();
        } else {
            return Result.fail();
        }
    }

    //管理员手动关闭订单
    @PostMapping("/closeOrder")
    public Result closeOrder(@RequestBody OrderVo orderVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return orderService.updateById(orderService.saveOrUpdate(orderVo)) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    //根据订单号查看相应的订单详情
    //此方法不可用 因为订单表表明order是SQL语句中的关键字 需要自己重写方法
    @GetMapping("/selectOrderById")
    public Result selectOrderById(@RequestParam("orderId") Integer orderId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            Order order = new Order();
            order = orderService.getById(orderId);
            return Result.success(order);
        } else {
            return Result.fail();
        }
    }

    /**
     * 获取当日交易额
     */
    @GetMapping("/dailyTransactionAmount")
    public Result getDailyTransactionAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return Result.success(orderService.calculateDailyTransactionAmount());
        } else {
            return Result.fail();
        }

    }

    /**
     * 获取本月交易额
     */
    @GetMapping("/monthlyTransactionAmount")
    public Result getMonthlyTransactionAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return Result.success(orderService.calculateMonthlyTransactionAmount());
        } else {
            return Result.fail();
        }
    }

    /**
     * 获取年度交易额
     */
    @GetMapping("/annualTransactionAmount")
    public Result getAnnualTransactionAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return Result.success(orderService.calculateAnnualTransactionAmount());
        } else {
            return Result.fail();
        }
    }

    /**
     * 获取今年每日交易额列表
     */
    @GetMapping("/dailyTransactionAmountList")
    public Result getDailyTransactionAmountList(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<DailyTransactionAmountVO> dailyTransactionAmount = orderService.getDailyTransactionAmount();
            return Result.success(dailyTransactionAmount, (long) dailyTransactionAmount.size());

        } else {
            return Result.fail();
        }
    }

    /**
     * 获取今年每月交易额列表
     */
    @GetMapping("/monthlyTransactionAmountList")
    public Result getMonthlyTransactionAmountList(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<MonthlyTransactionAmountVO> monthlyTransactionAmount = orderService.getMonthlyTransactionAmount();
            return Result.success(monthlyTransactionAmount, (long) monthlyTransactionAmount.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据当前用户的id（作为customer），获取其所有的订单
     */
    @GetMapping("/getOrdersByCustomerId")
    public Result getOrdersByCustomerId(@RequestParam("customerId") Integer customerId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<OrderInfoVO> ordersByCustomerId = orderService.getOrdersByCustomerId(customerId);
            return Result.success(ordersByCustomerId, (long) ordersByCustomerId.size());
        } else {
            return Result.fail();
        }
    }


    /**
     * 根据当前用户的id（作为seller），获取其所有的订单
     */
    @GetMapping("/getOrdersBySellerId")
    public Result getOrdersBySellerId(@RequestParam("seller") Integer seller, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<OrderInfoVO> ordersBySellerId = orderService.getOrdersBySellerId(seller);
            return Result.success(ordersBySellerId, (long) ordersBySellerId.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 获取订单详情
     * 需要有：订单编号，商品名称，商品图片，买家账号，卖家账号，
     * 商品价格，收件人电话，收件人地址，评论内容，订单状态
     */
    @GetMapping("/getOrderInfo")
    public Result getOrderInfo(@RequestParam("orderId") Integer orderId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            OrderDTO orderDTO = orderService.getOrderInfo(orderId);
            return Result.success(orderDTO, 1L);
        } else {
            return Result.fail();
        }
    }

    /**
     * 更新订单状态 传订单编号、订单状态、支付状态
     */
    @PostMapping("/updateStatus")
    public Result updateOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            try {
                orderService.updateOrderStatus(orderStatusDTO);
                return Result.success("订单状态更新成功");
            } catch (Exception e) {
                OrderStatusDTO orderStatusDTO1 = new OrderStatusDTO();
                orderStatusDTO1.setOrderId(orderStatusDTO.getOrderId());
                orderStatusDTO1.setOrderStatus(0);
                orderStatusDTO1.setPayStatus(-1);
                orderService.updateOrderStatus(orderStatusDTO1);
                return Result.fail();
            }
        } else {
            return Result.fail();
        }
    }

    /**
     * 更新支付方式 和 支付时间
     */
    @PostMapping("/updatePayWays")
    public Result updatePayWays(@RequestBody OrderPayWayDTO orderPayWayDTO, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            orderService.updatePayWays(orderPayWayDTO);
            return Result.success();
        } else {
            return Result.fail();
        }
    }


    //找到现在应该生成的订单的id
    @GetMapping("getMaxId")
    public Result getMaxId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            Integer a = orderService.getNewOrderId();
            return Result.success(a);
        } else {
            return Result.fail();
        }
    }


    @PostMapping("create")
    public Result createOrder(@RequestBody Order order, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            long orderNo = NumberUtil.genOrderNo();
            order.setOrderNo(orderNo);
            if (!orderService.insertOrder(order)) {
                return Result.fail();
            }

            return Result.success();
        } else {
            return Result.fail();
        }
    }
}
