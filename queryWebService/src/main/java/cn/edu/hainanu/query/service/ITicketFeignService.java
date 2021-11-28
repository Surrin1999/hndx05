package cn.edu.hainanu.query.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("SOFTWARE-FORECASTSERVICE")
public interface ITicketFeignService {

    @GetMapping(value = "/predict")
    String predict(@RequestParam String data);
}