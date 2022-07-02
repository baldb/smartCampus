package com.linyi.zhcompus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linyi.zhcompus.mapper")
public class Smartcompus01Application {

    public static void main(String[] args) {
        SpringApplication.run(Smartcompus01Application.class, args);
    }

}
