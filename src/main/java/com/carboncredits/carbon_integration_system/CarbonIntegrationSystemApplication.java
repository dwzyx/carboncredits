package com.carboncredits.carbon_integration_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.carboncredits.carbon_integration_system.mapper")
@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class CarbonIntegrationSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarbonIntegrationSystemApplication.class, args);

    }

}
