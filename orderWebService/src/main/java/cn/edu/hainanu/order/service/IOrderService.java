package cn.edu.hainanu.order.service;

import cn.edu.hainanu.order.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface IOrderService extends IService<Order> {

    Map<String, String> generateOrder(Integer userId, Integer ticketId, String date);

    Map<String, Object> queryOrder(String orderId);

    String orderPayment(String orderId);
}