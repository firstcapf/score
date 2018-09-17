package com.example.demosb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.example.demosb.Mapper")
@EnableSwagger2
public class DemoSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSbApplication.class, args);
    }
}
