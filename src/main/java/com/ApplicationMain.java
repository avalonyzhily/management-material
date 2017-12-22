package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *启动类
 */
@SpringBootApplication
@EnableTransactionManagement
public class ApplicationMain {

    public static void main(String[] args){
        SpringApplication.run(ApplicationMain.class,args);
    }
}
