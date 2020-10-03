package com.dys.springcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
@ComponentScan(value = {"com.dys"})
public class SpringCloudTest implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
