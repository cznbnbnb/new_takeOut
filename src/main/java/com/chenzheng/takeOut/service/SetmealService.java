package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.dto.SetmealDto;
import com.chenzheng.takeOut.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
    public SetmealDto getSetmealDtoById(Long id);
    public boolean updateStatus(List<Long> ids, Integer status);

}
