package com.chenzheng.takeOut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.dto.DishDto;
import com.chenzheng.takeOut.entity.Category;
import com.chenzheng.takeOut.entity.Dish;
import com.chenzheng.takeOut.entity.DishFlavor;
import com.chenzheng.takeOut.service.CategoryService;
import com.chenzheng.takeOut.service.DishFlavorService;
import com.chenzheng.takeOut.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("category：{}",dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新建菜品成功");
    }
//菜品分类管理
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        //构造分页对象
        Page<Dish> pageInfo = new Page(page,pageSize);
        Page<DishDto> dishDtoPage = new Page(page,pageSize);
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishService.page(pageInfo,queryWrapper);
        //复制基本信息
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            //复制内核信息
            BeanUtils.copyProperties(item,dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;

        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }



    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        log.info("根据id查询菜品信息");
        DishDto dishdto = dishService.getByIdWithFlavor(id);
        if (null==dishdto){
            return R.error("没有找到菜品信息");
        }
        return R.success(dishdto);

    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info("dishdto：{}",dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("更新菜品成功");
    }


    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = dishService.listDishes(dish);
        return R.success(dishDtoList);
    }





}
