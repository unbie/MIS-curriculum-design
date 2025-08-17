package com.market.service.impl;

import com.market.entity.LeaveMessages;
import com.market.mapper.LeaveMessagesMapper;
import com.market.service.LeaveMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-07-04
 */
@Service
public class LeaveMessagesServiceImpl extends ServiceImpl<LeaveMessagesMapper, LeaveMessages> implements LeaveMessagesService {

    @Resource
    LeaveMessagesMapper leaveMessagesMapper;

    @Override
    public List<LeaveMessages> findById(Integer goodId) {
        return leaveMessagesMapper.findByGoodId(goodId);
    }
}
