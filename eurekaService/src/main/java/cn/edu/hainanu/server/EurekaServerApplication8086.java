package cn.edu.hainanu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication8086 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication8086.class, args);
    }
}