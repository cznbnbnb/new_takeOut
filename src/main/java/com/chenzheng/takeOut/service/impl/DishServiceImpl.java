package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.dto.DishDto;
import com.chenzheng.takeOut.entity.Category;
import com.chenzheng.takeOut.entity.Dish;
import com.chenzheng.takeOut.entity.DishFlavor;
import com.chenzheng.takeOut.mapper.DishMapper;
import com.chenzheng.takeOut.service.CategoryService;
import com.chenzheng.takeOut.service.DishFlavorService;
import com.chenzheng.takeOut.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;


    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavor = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(dishFlavor);
        return dishDto;

    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);


    }
    @Override
    public List<DishDto> listDishes(Dish dish) {
        // 构造查询条件
        // LambdaQueryWrapper 是一个条件构造器，用于构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // 如果dish对象的categoryId属性不为null，添加一个查询条件：菜品的categoryId等于dish对象的categoryId
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

        // 添加一个查询条件：菜品的状态（status）为1（起售状态）
        queryWrapper.eq(Dish::getStatus, 1);

        // 添加排序条件：首先按照菜品的sort属性升序排序，如果sort相同，则按照更新时间（updateTime）降序排序
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        // 使用构造好的查询条件进行查询，获取满足条件的菜品列表
        List<Dish> list = this.list(queryWrapper);

        // 使用Java 8的Stream API将菜品列表转换为DishDto对象的列表
        return list.stream().map(item -> {
            // 创建一个新的DishDto对象
            DishDto dishDto = new DishDto();

            // 使用Spring的BeanUtils工具类，将item（Dish对象）的属性复制到dishDto对象
            BeanUtils.copyProperties(item, dishDto);

            // 获取菜品的分类id
            Long categoryId = item.getCategoryId();

            // 根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);

            // 如果分类对象不为null，将分类的名称设置到dishDto对象的categoryName属性
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 获取当前菜品的id
            Long dishId = item.getId();

            // 构造查询口味信息的查询条件
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);

            // 使用构造好的查询条件查询口味信息列表
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);

            // 将口味信息列表设置到dishDto对象的flavors属性
            dishDto.setFlavors(dishFlavorList);

            // 返回dishDto对象，这将作为stream的新元素
            return dishDto;
        }).collect(Collectors.toList()); // 将stream转换为列表
    }




}
