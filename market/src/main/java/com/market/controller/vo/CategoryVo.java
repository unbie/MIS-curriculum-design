package com.market.controller.vo;

import com.market.entity.GoodCategory;
import lombok.Data;


@Data
public class CategoryVo {

    private Integer categoryId;

    private String categoryName;


    private String categoryRank;


    private Integer isDeleted;

    //private LocalDateTime createTime;

    public CategoryVo(GoodCategory category) {
        categoryId = category.getCategoryId();
        categoryName = category.getCategoryName();
        categoryRank = category.getCategoryRank();
        isDeleted = category.getIsDeleted();
        //createTime = category.getCreateTime();
    }
}
