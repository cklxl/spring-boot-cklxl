package com.cklxl.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cklxl.**.mapper")
@SpringBootApplication(scanBasePackages = {"com.cklxl"})
public class DemoSrv {
    public static void main(String[] args) {
        SpringApplication.run(DemoSrv.class, args);
    }
}
