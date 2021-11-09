package cn.edu.hainanu.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("cn.edu.hainanu.order.mapper")
public class OrderApplication8084 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8084.class, args);
    }
}