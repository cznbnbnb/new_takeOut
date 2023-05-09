package com.chenzheng.takeOut.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.Employee;
import com.chenzheng.takeOut.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /**
     * 处理员工登录请求
     * @param request HttpServletRequest对象，用于存储登录成功的员工ID
     * @param employee 包含用户名和密码的Employee对象
     * @return 如果登录成功，返回R.success，包含Employee对象；否则返回R.error，包含错误信息
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        Employee emp = employeeService.login(employee);
        if (emp != null) {
            request.getSession().setAttribute("employee", emp.getId());
            return R.success(emp);
        } else {
            return R.error("登录失败");
        }
    }
    /**
     * 处理员工登出请求
     * @param request HttpServletRequest对象，用于移除登录的员工ID
     * @return 返回R.success，包含成功信息
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 处理保存新员工信息请求
     * @param request HttpServletRequest对象，用于获取当前登录用户的ID
     * @param employee 要保存的Employee对象
     * @return 成功返回R.success，包含成功信息
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.saveEmployee(request, employee);
    }
    /**
     * 处理分页查询员工信息请求
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @param name 员工姓名（模糊查询）
     * @return 返回R.success，包含查询结果的分页对象
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Employee> pageInfo = employeeService.findEmployeesByPage(page, pageSize, name);
        return R.success(pageInfo);
    }

    /**
     * 处理更新员工信息请求
     * @param request HttpServletRequest对象，用于获取当前登录用户的ID
     * @param employee 包含更新信息的Employee对象
     * @return 成功返回R.success，包含成功信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.updateEmployee(request, employee);
    }
    /**
     * 根据员工ID查询员工信息
     * @param id 员工ID
     * @return 如果找到员工信息，返回R.success，包含Employee对象；否则返回R.error，包含错误信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (null != employee) {
            return R.success(employee);
        }
        return R.error("没有找到员工信息");
    }
}

