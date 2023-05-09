package com.chenzheng.takeOut.dto;

import com.chenzheng.takeOut.entity.Setmeal;
import com.chenzheng.takeOut.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
