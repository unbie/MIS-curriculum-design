package com.market.mapper;

import com.market.entity.LeaveMessages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
public interface LeaveMessagesMapper extends BaseMapper<LeaveMessages> {

    List<LeaveMessages> findByGoodId(Integer goodId);

    @Select("select reviewer_id,content,create_time,userName,good_name")
    List<LeaveMessages> findByPersonId(Integer personId);
}
