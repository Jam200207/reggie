package com.jam.reggie.controller;

import com.jam.reggie.common.PageBean;
import com.jam.reggie.common.R;
import com.jam.reggie.entity.Employee;
import com.jam.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * 员工登录
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //        //根据页面提交的用户名username查询数据库
//        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(Employee::getUsername,employee.getUsername());
//        Employee emp=employeeService.getOne(queryWrapper);

        //1.调用业务层  根据username查询
        Employee emp=employeeService.login(employee);
        //2.判断用户是否存在
        if(emp==null){
            return R.error("用户不存在");
        }
        //3.对比密码
        //将页面提交的密码password进行md5加密处理
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //4.查看员工的工作状态--是否禁用
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        //5.如果登陆成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 员工列表 分页
     */
    @GetMapping("/page")
    public R page(Integer page,Integer pageSize,String name){
        log.info("分页查询,{},{},{}",page,pageSize,name);
        //分页查询
        PageBean pageBean=employeeService.page(page,pageSize,name);
        //响应
        return R.success(pageBean);
    }

    /**
     * 员工添加
     */
    @PostMapping("")
    public R addEmployee(@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        employeeService.add(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工禁用、更改
     */
    @PutMapping("")
    public R editEmployee(@RequestBody Employee employee){
        log.info("员工更改，员工信息：{}",employee.toString());
        employeeService.modify(employee);
        return R.success("员工更改成功");
    }

    /***
     *根据员工id查询
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id){
        Employee employee=employeeService.getById(id);
        return R.success(employee);
    }

}
