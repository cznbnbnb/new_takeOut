package com.chenzheng.takeOut.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.dto.SetmealDto;
import com.chenzheng.takeOut.entity.Category;
import com.chenzheng.takeOut.entity.Setmeal;
import com.chenzheng.takeOut.service.CategoryService;
import com.chenzheng.takeOut.service.SetmealDishService;
import com.chenzheng.takeOut.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("setmealDto：{}",setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("新建套餐成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        //构造分页对象
        Page<Setmeal> setmealPage = new Page(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page(page,pageSize);
        //构造查询条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,queryWrapper);
        //复制基本信息
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            //复制内核信息
            BeanUtils.copyProperties(item,setmealDto);

            Category category = categoryService.getById(item.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;

        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("setmealIds：{}",ids.toString());
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }





}
