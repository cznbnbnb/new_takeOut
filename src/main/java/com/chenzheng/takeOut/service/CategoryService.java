package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.Category;

import java.util.List;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);
    public List<Category> listCategories(Category category);
}
