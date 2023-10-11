package com.jam.reggie;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@Slf4j
@SpringBootApplication
//@ServletComponentScan//开启过滤器扫描
public class ReggieApplication {
    public static void main(String[] args){
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }
}
