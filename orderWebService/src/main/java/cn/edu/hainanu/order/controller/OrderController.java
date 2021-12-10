package cn.edu.hainanu.order.controller;

import cn.edu.hainanu.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/generateOrder")
    public Map<String, String> generateOrder(@RequestParam("userId") Integer userId, @RequestParam("ticketId") Integer ticketId,
                                             @RequestParam("date") String date) {
        return orderService.generateOrder(userId, ticketId, date);
    }

    @GetMapping("/queryOrder")
    public Map<String, Object> queryOrder(@RequestParam("orderId") String orderId) {
        return orderService.queryOrder(orderId);
    }

    @GetMapping("/orderPayment")
    public String orderPayment(@RequestParam("orderId") String orderId) {
        return orderService.orderPayment(orderId);
    }
}