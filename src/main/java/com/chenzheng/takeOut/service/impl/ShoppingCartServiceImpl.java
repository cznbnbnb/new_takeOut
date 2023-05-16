package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.common.BaseContext;
import com.chenzheng.takeOut.entity.ShoppingCart;
import com.chenzheng.takeOut.mapper.ShoppingCartMapper;
import com.chenzheng.takeOut.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    private ShoppingCart findCartItem(ShoppingCart shoppingCart, Long userId) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        if(shoppingCart.getDishId() != null){
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }else{
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        return this.getOne(queryWrapper);
    }

    @Override
    public ShoppingCart addCartItem(ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        ShoppingCart existingCartItem = findCartItem(shoppingCart, currentId);

        if(existingCartItem != null){
            existingCartItem.setNumber(existingCartItem.getNumber() + 1);
            this.updateById(existingCartItem);
        }else{
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
            existingCartItem = shoppingCart;
        }

        return existingCartItem;
    }

    @Override
    public ShoppingCart subCartItem(ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        ShoppingCart existingCartItem = findCartItem(shoppingCart, currentId);

        if(existingCartItem != null){
            Integer number = existingCartItem.getNumber();
            if(number > 1) {
                existingCartItem.setNumber(number - 1);
                this.updateById(existingCartItem);
            } else {
                this.removeById(existingCartItem.getId());
                existingCartItem = null;
            }
        }

        return existingCartItem;
    }
}


