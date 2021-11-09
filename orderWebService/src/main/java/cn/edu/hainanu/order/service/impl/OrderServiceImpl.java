package cn.edu.hainanu.order.service.impl;

import cn.edu.hainanu.order.domain.Order;
import cn.edu.hainanu.order.domain.Ticket;
import cn.edu.hainanu.order.mapper.OrderMapper;
import cn.edu.hainanu.order.mapper.TicketMapper;
import cn.edu.hainanu.order.service.IOrderFeignService;
import cn.edu.hainanu.order.service.IOrderService;
import cn.edu.hainanu.order.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private static final String PASS = "Check Passed";

    private static final int UN_PAID = 0;

    private static final int PAID = 1;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private IOrderFeignService orderFeignService;

    @Override
    public Map<String, String> generateOrder(Integer userId, Integer ticketId, String date) {
        if (userId <= 0) {
            return Collections.singletonMap("message", "用户ID必须大于0");
        }
        Map<String, String> checkTicketResult = checkTicket(ticketId);
        if (!PASS.equals(checkTicketResult.get("message"))) {
            return checkTicketResult;
        }
        Map<String, String> checkDateResult = checkDate(date);
        if (!PASS.equals(checkDateResult.get("message"))) {
            return checkDateResult;
        }
        try {
            return doGenerateOrder(userId, ticketId,
                    Integer.parseInt(checkDateResult.get("year")),
                    Integer.parseInt(checkDateResult.get("month")),
                    Integer.parseInt(checkDateResult.get("day")));
        } catch (Exception e) {
            return Collections.singletonMap("message", e.getMessage());
        }
    }

    private Map<String, String> checkDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT);
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.singletonMap("message", "请输入正确的日期，输入格式为uuuu-M-d");
        }
        LocalDate now = LocalDate.now();
        if (now.compareTo(localDate) > 0) {
            return Collections.singletonMap("message", "不能订过去时间的票");
        }
        HashMap<String, String> checkDateResult = new HashMap<>(4);
        checkDateResult.put("year", String.valueOf(localDate.getYear()));
        checkDateResult.put("month", String.valueOf(localDate.getMonthValue()));
        checkDateResult.put("day", String.valueOf(localDate.getDayOfMonth()));
        checkDateResult.put("message", PASS);
        return checkDateResult;
    }

    private Map<String, String> checkTicket(Integer ticketId) {
        int ticketSurplus = orderFeignService.getTicketSurplus(ticketId);
        if (ticketSurplus == -1) {
            return Collections.singletonMap("message", "未找到该TickedID的数据");
        }
        if (ticketSurplus == 0) {
            return Collections.singletonMap("message", "抱歉，该票次已售罄");
        }
        return Collections.singletonMap("message", PASS);
    }

    @Transactional
    public Map<String, String> doGenerateOrder(int userId, int ticketId, int year, int month, int day) throws Exception {
        Ticket ticket = orderFeignService.getTicketById(ticketId);
        Order order = new Order();
        order.setOrderId(RandomUtil.getOrderId());
        order.setStatus(UN_PAID);
        order.setTicketsClasses(ticket.getClasses());
        order.setUserId(userId);
        order.setDate(LocalDate.of(year, month, day));
        boolean saveSuccess = save(order);
        if (!saveSuccess) {
            throw new Exception("订单生成失败");
        }
        boolean updateSuccess = ChainWrappers.lambdaUpdateChain(ticketMapper).eq(Ticket::getId, ticketId).set(Ticket::getTicketSurplus, ticket.getTicketSurplus() - 1).update();
        if (!updateSuccess) {
            throw new Exception("票余额更新失败");
        }
        HashMap<String, String> resultMap = new HashMap<>(2);
        resultMap.put("message", "下单成功");
        resultMap.put("orderId", order.getOrderId());
        return resultMap;
    }

    @Override
    public Map<String, Object> queryOrder(String orderId) {
        if (StrUtil.isBlank(orderId)) {
            return Collections.singletonMap("message", "请输入订单ID");
        }
        HashMap<String, Object> resultMap = new HashMap<>(2);
        List<Order> orderList = ChainWrappers.lambdaQueryChain(orderMapper).eq(Order::getOrderId, orderId).list();
        if (orderList.size() > 0) {
            resultMap.put("message", "查询成功");
            resultMap.put("data", orderList);
        } else {
            resultMap.put("message", "未找到相关数据");
        }
        return resultMap;
    }

    @Override
    public String orderPayment(String orderId) {
        if (StrUtil.isBlank(orderId)) {
            return "请输入订单ID";
        }
        List<Order> orderList = ChainWrappers.lambdaQueryChain(orderMapper).eq(Order::getOrderId, orderId).list();
        if (orderList.size() == 0) {
            return "订单号错误，未找到该订单数据";
        }
        List<Integer> idList = new ArrayList<>();
        orderList.forEach(order -> {
            if (order.getStatus() == UN_PAID) {
                idList.add(order.getId());
            }
        });
        if (idList.size() == 0) {
            return "该订单已付款，不能重复付款";
        }
        boolean isSuccess = executeBatch(idList, ((sqlSession, id) -> {
            HashMap<String, Integer> paramsMap = new HashMap<>(2);
            paramsMap.put("id", id);
            paramsMap.put("status", PAID);
            sqlSession.update("cn.edu.hainanu.order.mapper.OrderMapper.updateStatusById", paramsMap);
        }));
        return isSuccess ? "付款成功" : "付款失败";
    }
}