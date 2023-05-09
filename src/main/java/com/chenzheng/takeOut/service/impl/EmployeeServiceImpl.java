package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.Employee;
import com.chenzheng.takeOut.mapper.EmployeeMapper;
import com.chenzheng.takeOut.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    /**
     * 登录验证
     * @param employee 包含用户名和密码的Employee对象
     * @return 如果验证成功，返回Employee对象；否则返回null
     */
    @Override
    public Employee login(Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = this.getOne(queryWrapper);
        if (null == emp || !emp.getPassword().equals(password) || emp.getStatus() == 0) {
            return null;
        }
        return emp;
    }

    /**
     * 保存新员工信息
     * @param request HttpServletRequest对象，用于获取当前登录用户的ID
     * @param employee 要保存的Employee对象
     * @return 成功返回R.success，包含成功信息
     */
    @Override
    public R<String> saveEmployee(HttpServletRequest request, Employee employee) {
        log.info("新增员工信息：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empID = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empID);
        employee.setUpdateUser(empID);
        this.save(employee);
        return R.success("创建新员工成功");
    }

    /**
     * 分页查询员工信息
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @param name 员工姓名（模糊查询）
     * @return 包含查询结果的分页对象
     */
    @Override
    public Page<Employee> findEmployeesByPage(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        this.page(pageInfo, queryWrapper);
        return pageInfo;
    }

    /**
     * 更新员工信息
     * @param request HttpServletRequest对象，用于获取当前登录用户的ID
     * @param employee 包含更新信息的Employee对象
     * @return 成功返回R.success，包含成功信息
     */
    @Override
    public R<String> updateEmployee(HttpServletRequest request, Employee employee) {
        log.info(employee.toString());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        this.updateById(employee);
        return R.success("员工信息修改成功");
    }
}



