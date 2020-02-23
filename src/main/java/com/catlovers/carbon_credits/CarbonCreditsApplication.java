package com.catlovers.carbon_credits;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@MapperScan("com.catlovers.carbon_credits.dao")
@EnableCaching
@ServletComponentScan
@SpringBootApplication
@RequestMapping("/carbon_credits_system")
public class CarbonCreditsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonCreditsApplication.class, args);
    }


}
