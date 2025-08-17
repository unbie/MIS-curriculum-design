package com.market.mapper;

import com.market.entity.ImgAndGood;
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
public interface ImgAndGoodMapper extends BaseMapper<ImgAndGood> {
    List<String> getImgUrl(@Param("sid") int id);

    ImgAndGood selectByGoodId(Integer goodId);

    @Select("<script>" +
            "SELECT * FROM img_and_good WHERE good_id IN" +
            "<foreach collection='goodIds' item='goodId' open='(' separator=',' close=')'>" +
            "#{goodId}" +
            "</foreach>" +
            "</script>")
    List<ImgAndGood> getImgAndGoodByGoodIds(@Param("goodIds") List<Integer> goodIds);
}
