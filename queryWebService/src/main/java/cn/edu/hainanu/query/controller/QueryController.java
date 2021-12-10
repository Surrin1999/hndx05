package cn.edu.hainanu.query.controller;

import cn.edu.hainanu.query.domain.Ticket;
import cn.edu.hainanu.query.service.ITicketFeignService;
import cn.edu.hainanu.query.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private ITicketService ticketService;

    @Resource
    private ITicketFeignService ticketFeignService;

    @GetMapping("/getWeather")
    public Map<String, String> getWeather(@RequestParam("city") String city) {
        return ticketService.getWeather(city);
    }

    @GetMapping("/getTicketsInformation")
    public Map<String, List<Ticket>> getTicketsInformation(@RequestParam("departureCity") String departureCity,
                                                           @RequestParam("destinationCity") String destinationCity) {
        return Collections.singletonMap("data", ticketService.getTicketsInformation(departureCity, destinationCity));
    }

    @GetMapping("/getTicketById")
    public Ticket getTicketById(@RequestParam("ticketId") Integer ticketId) {
        if (ticketId == null) {
            return null;
        }
        return ticketService.getById(ticketId);
    }

    @GetMapping("/getTicketSurplus")
    public Integer getTicketSurplus(@RequestParam("ticketId") Integer ticketId) {
        if (ticketId == null) {
            return null;
        }
        Ticket ticket = ticketService.getById(ticketId);
        return ticket != null ? ticket.getTicketSurplus() : -1;
    }

    @GetMapping("/getPrediction")
    public Map<String, String> getPrediction(@RequestParam("date") String date) {
        HashMap<String, String> resultMap = new HashMap<>(2);
        resultMap.put("message", "预测完毕");
        resultMap.put("prediction", ticketFeignService.predictFlow(date));
        return resultMap;
    }
}