package cn.edu.hainanu.query.controller;

import cn.edu.hainanu.query.domain.Ticket;
import cn.edu.hainanu.query.service.ITicketFeignService;
import cn.edu.hainanu.query.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private ITicketService ticketService;

    @Resource
    private ITicketFeignService ticketFeignService;

    @GetMapping("/getWeather")
    public Map<String, String> getWeather(String city) {
        return ticketService.getWeather(city);
    }

    @GetMapping("/getTicketsInformation")
    public Map<String, List<Ticket>> getTicketsInformation(String departureCity, String destinationCity) {
        return Collections.singletonMap("data", ticketService.getTicketsInformation(departureCity, destinationCity));
    }

    @GetMapping("/getTicketById")
    public Ticket getTicketById(Integer ticketId) {
        if (ticketId == null) {
            return null;
        }
        return ticketService.getById(ticketId);
    }

    @GetMapping("/getTicketSurplus")
    public Integer getTicketSurplus(Integer tickedId) {
        if (tickedId == null) {
            return null;
        }
        Ticket ticket = ticketService.getById(tickedId);
        return ticket != null ? ticket.getTicketSurplus() : -1;
    }

    @GetMapping("/getPrediction")
    public Map<String, String> getPrediction(String data) {
        HashMap<String, String> resultMap = new HashMap<>(2);
        resultMap.put("message", "预测完毕");
        resultMap.put("prediction", ticketFeignService.predict(data));
        return resultMap;
    }
}