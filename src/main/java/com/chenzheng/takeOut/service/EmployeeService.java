package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    Employee login(Employee employee);

    R<String> saveEmployee(HttpServletRequest request, Employee employee);

    Page<Employee> findEmployeesByPage(int page, int pageSize, String name);

    R<String> updateEmployee(HttpServletRequest request, Employee employee);
}

