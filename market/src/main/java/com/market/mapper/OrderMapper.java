package com.market.mapper;

import com.market.controller.dto.GoodDTO;
import com.market.controller.vo.*;
import com.market.entity.Comment;
import com.market.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.market.entity.PersonShoppingAddress;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    //通过查找封装成OrderVO类
    List<OrderVo> listOrderVo();

    //重写删除评论方法，根据orderId删除对应的评论 返回删除的行数
    int deleteByOrderId(Integer orderId);

    //重写管理员更新order方法 返回更新的行数
    int updateByOrderId(Integer orderId);

    @Select("Select Max(order_id) From `order`")
    Integer getMaxId();

    @Insert("INSERT INTO `order` (order_id, order_no,good_id, address_id, seller_id , customer_id, total_price, order_status, pay_time, pay_ways, pay_status, count, create_time) " +
            "VALUES (#{orderId}, #{orderNo}, #{goodId}, #{addressId}, #{sellerId} , #{customerId}, #{totalPrice}, #{orderStatus}, #{payTime}, #{payWays}, #{payStatus}, #{count}, #{createTime})")
    boolean insertOrder(Order order);


    // 获取本日交易额
    @Select("SELECT SUM(total_price) FROM `order` WHERE DATE(pay_time) = #{date}")
    BigDecimal calculateTransactionAmountByDate(@Param("date") LocalDate date);

    // 获取本月交易额
    @Select("SELECT SUM(total_price) FROM `order` WHERE YEAR(pay_time) = #{year} AND MONTH(pay_time) = #{month}")
    BigDecimal calculateTransactionAmountByMonth(@Param("year") int year, @Param("month") int month);

    // 获取年度交易额
    @Select("SELECT SUM(total_price) FROM `order` WHERE YEAR(pay_time) = #{year}")
    BigDecimal calculateTransactionAmountByYear(@Param("year") Year year);

    // 获取今年每日交易额列表
    @Select("SELECT DATE_FORMAT(pay_time, '%Y-%m-%d') AS date, SUM(total_price) AS amount " +
            "FROM `order` " +
            "WHERE YEAR(pay_time) = YEAR(CURDATE()) AND MONTH(pay_time) = MONTH(CURDATE()) " +
            "GROUP BY DATE_FORMAT(pay_time, '%Y-%m-%d')")
    List<DailyTransactionAmountVO> getDailyTransactionAmount();

    // 获取今年每月交易额列表
    @Select("SELECT DATE_FORMAT(pay_time, '%c') AS month, SUM(total_price) AS amount " +
            "FROM `order` " +
            "WHERE YEAR(pay_time) = YEAR(CURDATE()) " +
            "GROUP BY DATE_FORMAT(pay_time, '%c')")
    List<MonthlyTransactionAmountVO> getMonthlyTransactionAmount();

    @Select("SELECT o.order_id, o.order_no, (SELECT i.img_url FROM img_and_good i WHERE i.good_id = o.good_id LIMIT 1) AS img_url, g.description, o.total_price, o.order_status " +
            "FROM `order` o " +
            "LEFT JOIN good g ON o.good_id = g.good_id " +
            "WHERE o.customer_id = #{customerId}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "imgUrl", column = "img_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "orderStatus", column = "order_status")
    })
    List<OrderInfoVO> getOrdersByCustomerId(@Param("customerId") Integer customerId);

    @Select("SELECT o.order_id, o.order_no, (SELECT i.img_url FROM img_and_good i WHERE i.good_id = o.good_id LIMIT 1) AS img_url, g.description, o.total_price, o.order_status " +
            "FROM `order` o " +
            "LEFT JOIN good g ON o.good_id = g.good_id " +
            "WHERE o.seller_id = #{sellerId}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "imgUrl", column = "img_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "orderStatus", column = "order_status")
    })
    List<OrderInfoVO> getOrdersBySellerId(@Param("sellerId") Integer sellerId);


    @Select("SELECT g.good_id, g.good_name, i.img_url " +
            "FROM `order` o " +
            "JOIN good g ON o.good_id = g.good_id " +
            "JOIN img_and_good i ON g.good_id = i.good_id " +
            "WHERE o.order_id = #{orderId} " +
            "limit 1")
    GoodDTO findGoodByOrderId(Integer orderId);

    @Select("SELECT p.shipping_address, p.shopping_phone " +
            "FROM `order` o " +
            "JOIN person_shopping_address p ON o.address_id = p.shopping_address_id " +
            "WHERE o.order_id = #{orderId}")
    PersonShoppingAddress findAddressByOrderId(Integer orderId);

    @Select("SELECT c.initial_comments, c.stars, c.modified_comments " +
            "FROM `order` o " +
            "JOIN comment c ON o.seller_id = c.reviewed_id and o.customer_id = c.reviewer_id " +
            "WHERE o.order_id = #{orderId}")
    Comment findCommentByOrderId(Integer orderId);

    @Select("SELECT p.account " +
            "FROM `order` o " +
            "JOIN person p ON o.customer_id = p.person_id " +
            "WHERE o.order_id = #{orderId}")
    String findCustomerAccountByOrderId(Integer orderId);

    @Select("SELECT p.account " +
            "FROM `order` o " +
            "JOIN person p ON o.seller_id = p.person_id " +
            "WHERE o.order_id = #{orderId}")
    String findSellerAccountByOrderId(Integer orderId);

    @Select("SELECT * FROM `order` WHERE order_id = #{orderId}")
    Order selectById(Integer orderId);

    @Update("UPDATE `order` SET order_status=#{orderStatus}, pay_status=#{payStatus} WHERE order_id=#{orderId}")
    void updateOrderStatus(@Param("orderId") Integer orderId, @Param("orderStatus") Integer orderStatus, @Param("payStatus") Integer payStatus);

    @Update("UPDATE `order` SET pay_ways=#{payWays}, pay_time=#{payTime} WHERE order_id=#{orderId}")
    void updatePayWays(Integer orderId, Integer payWays, Date payTime);
}
