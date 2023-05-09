package com.chenzheng.takeOut.dto;

import com.chenzheng.takeOut.entity.Dish;
import com.chenzheng.takeOut.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
