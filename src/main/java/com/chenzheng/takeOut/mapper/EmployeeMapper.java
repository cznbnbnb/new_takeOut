package com.chenzheng.takeOut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenzheng.takeOut.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
