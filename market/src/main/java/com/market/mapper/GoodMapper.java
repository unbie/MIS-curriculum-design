package com.market.mapper;

import com.market.controller.dto.GoodDTO;
import com.market.controller.vo.OrderGoodVO;
import com.market.entity.Good;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface GoodMapper extends BaseMapper<Good> {

    //查找封装形成OrderGoodVO类
    List<OrderGoodVO> listOrderGoodVo();

    @Select("SELECT MAX(good_id) AS max_good_id FROM good")
    Integer getMaxId();

    @Select("SELECT good_id FROM good WHERE publisher_id = #{personId}")
    List<Integer> selectGoodIdsByPersonId(Integer personId);

    @Select("SELECT g.good_id AS goodId, g.good_name AS goodName, img.img_url AS imgUrl, g.price " +
            "FROM good g " +
            "INNER JOIN img_and_good img ON g.good_id = img.good_id " +
            "INNER JOIN (" +
            "    SELECT v.category_id, SUM(v.views) AS totalViews " +
            "    FROM views v " +
            "    WHERE v.person_id = #{personId} " +
            "    GROUP BY v.category_id " +
            "    ORDER BY totalViews DESC " +
            "    LIMIT 2" +
            ") top ON g.category_id = top.category_id " +
            "WHERE g.is_sale = 1 " +
            "ORDER BY RAND() " +
            "LIMIT 8")
    List<GoodDTO> getTopCategoryGoods(@Param("personId") Integer personId);

    @Select("SELECT g.*, MAX(i.img_url) AS img_url " +
            "FROM good g " +
            "JOIN img_and_good i ON g.good_id = i.good_id " +
            "WHERE g.is_sale = #{isSale} and g.good_name LIKE CONCAT('%', #{keyword}, '%')" +
            "GROUP BY g.good_id")
    List<GoodDTO> searchGoodsByKeyword(@Param("keyword") String keyword, Integer isSale);

    @Select("SELECT g.*, MAX(i.img_url) AS img_url " +
            "FROM good g " +
            "JOIN img_and_good i ON g.good_id = i.good_id " +
            "WHERE g.is_sale = #{isSale} AND g.category_id = #{categoryId} " +
            "GROUP BY g.good_id")
    List<GoodDTO> findByCategoryId(Integer categoryId, Integer isSale);

    @Select("SELECT category_id FROM good WHERE good_id = #{goodId}")
    Integer selectCategoryIdByGoodId(@Param("goodId") Integer goodId);

    @Select("SELECT g.good_id, g.good_name, max(i.img_url) as img_url , g.price FROM good g INNER JOIN img_and_good i ON g.good_id = i.good_id WHERE g.is_sale = 0 GROUP BY g.good_id")
    List<GoodDTO> getShopForGoods();
}
