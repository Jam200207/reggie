package com.jam.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jam.reggie.common.PageBean;
import com.jam.reggie.entity.Employee;
import com.jam.reggie.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;


public interface EmployeeService extends IService<Employee> {


    /**
     * 员工登录
     */
    public Employee login(Employee employee);

    /**
     * 员工列表
     */
    public PageBean page(Integer page,Integer pageSize,String name);

    /**
     * 员工新增
     */
    public void add(Employee employee);

    /**
     * 员工禁用
     */
    public void modify(Employee employee);

    /**
     * 根据Id
     */
    public Employee getById(Integer id);

}
