package com.jam.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jam.reggie.common.PageBean;
import com.jam.reggie.entity.Employee;
import com.jam.reggie.mapper.EmployeeMapper;
import com.jam.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     */
    public Employee login(Employee employee){
        return employeeMapper.getByUser(employee);
    }

    /**
     * 员工列表
     */
    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行分页查询
        List<Employee> employeeList=employeeMapper.list(name);
        //获取分页结果
        Page<Employee> p=(Page<Employee>) employeeList;
        //封装PageBean
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 添加员工
     */
    public void add(Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(1);
        employee.setCreateUser(Long.valueOf (1));
        employee.setUpdateUser(Long.valueOf (1));
        employeeMapper.add(employee);
    }

    /**
     * 修改员工
     */
    public void modify(Employee employee){
        employee.setUpdateTime(LocalDateTime.now());
        log.info("员工禁用，员工信息：{}",employee.toString());
        employeeMapper.modify(employee);
    }

    /**
     * 根据id
     */
    public Employee getById(Integer id){
        return employeeMapper.getById(id);
    }
}
