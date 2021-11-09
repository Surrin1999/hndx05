package cn.edu.hainanu.query;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.edu.hainanu.query.mapper")
@EnableEurekaClient
@EnableFeignClients
public class QueryApplication8083 {
    public static void main(String[] args) {
        SpringApplication.run(QueryApplication8083.class, args);
    }
}