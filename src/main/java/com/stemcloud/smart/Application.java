package com.stemcloud.smart;

import com.stemcloud.smart.web.service.MySecurityFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/15
 * Description: Server entrance
 */
@ServletComponentScan
@SpringBootApplication
/*@EnableAutoConfiguration(exclude = MySecurityFilter.class)*/
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
