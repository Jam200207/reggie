package com.jam.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jam.reggie.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper  extends BaseMapper<Employee> {
    //根据密码用户查找
    @Select("select * from employee where username=#{username} ")
    public Employee getByUser(Employee employee);

    //根据用户名查--模糊查询
    public List<Employee> list(String name);

    //新增
    public void add(Employee employee);

    //根据id查
    @Select("select * from employee where id=#{id}")
    public Employee getById(Integer id);

    //修改
    public void modify(Employee employee);
}
