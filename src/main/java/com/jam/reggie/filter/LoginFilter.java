package com.jam.reggie.filter;




import com.jam.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")//拦截所有请求
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //前置：强制转换为http协议的请求对象、响应对象（原因：要使用子类特有的方法
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //1.获取请求的url
        String url=request.getRequestURI().toString();
        log.info("url: {}", url);
        //2.判断请求url是否含有login，若是登录操作。则放行，，若不是，则拦截
        if(url.contains("login")){
            //log.info("登录，不录拦截");
            filterChain.doFilter(request,response);//放行请求
            return;
        }
        // 3. 排除特定请求，如 JavaScript 文件
        if (url.endsWith(".js") ) {
            //log.info("不拦截 JavaScript 文件");
            filterChain.doFilter(request, response); // 放行请求
            return;
        }
        //3.判断是否已经登录，若已经登录，则放行  若未登录则返回登录页面
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") != null) {
            // 用户已登录，放行
           //log.info("登录过");
            filterChain.doFilter(request, response);
            return;
        } else {
            // 用户未登录，可以重定向到登录页面或返回错误信息
            //log.info("未登录拦截");
            response.sendRedirect("/backend/page/login/login.html");
        }
    }
}
