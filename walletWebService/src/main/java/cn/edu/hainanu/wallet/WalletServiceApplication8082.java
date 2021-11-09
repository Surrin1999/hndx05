package cn.edu.hainanu.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.edu.hainanu.wallet.mapper")
public class WalletServiceApplication8082 {
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication8082.class, args);
    }
}