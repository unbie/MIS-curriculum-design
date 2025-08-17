package com.market.service;

import com.market.entity.LeaveMessages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface LeaveMessagesService extends IService<LeaveMessages> {


    List<LeaveMessages> findById(Integer goodId);
}
