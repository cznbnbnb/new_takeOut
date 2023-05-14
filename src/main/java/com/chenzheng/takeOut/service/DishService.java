package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.dto.DishDto;
import com.chenzheng.takeOut.entity.Dish;

import java.util.List;


public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
    public List<DishDto> listDishes(Dish dish);
}
