package com.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.market.entity.Views;
import com.market.mapper.GoodMapper;
import com.market.mapper.ViewsMapper;
import com.market.service.ViewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class ViewsServiceImpl extends ServiceImpl<ViewsMapper, Views> implements ViewsService {

    @Autowired
    private ViewsMapper viewsMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public void addView(Integer goodId, Integer personId) {
        // 查询对应的views记录
        Views views = viewsMapper.selectOne(
                new QueryWrapper<Views>()
                        .eq("category_id", goodMapper.selectCategoryIdByGoodId(goodId))
                        .eq("person_id", personId)
        );

        if (views == null) {
            // 如果没有对应的views记录，则新增一条记录
            views = new Views();
            views.setCategoryId(goodMapper.selectCategoryIdByGoodId(goodId));
            views.setPersonId(personId);
            views.setViews(1);
            views.setCreateTime(new Date());
            viewsMapper.insert(views);
        } else {
            // 如果找到了对应的views记录，则将views加一
            views.setViews(views.getViews() + 1);
            viewsMapper.updateById(views);
        }
    }

}
