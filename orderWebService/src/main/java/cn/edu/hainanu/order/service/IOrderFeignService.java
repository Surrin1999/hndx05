package cn.edu.hainanu.order.service;

import cn.edu.hainanu.order.domain.Ticket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("SOFTWARE-QUERYWEBSERVICE")
public interface IOrderFeignService {

    @GetMapping("/getTicketSurplus")
    Integer getTicketSurplus(@RequestParam Integer ticketId);

    @GetMapping("/getTicketById")
    Ticket getTicketById(@RequestParam Integer ticketId);
}