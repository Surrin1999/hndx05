package cn.edu.hainanu.query.service;

import cn.edu.hainanu.query.domain.Ticket;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ITicketService extends IService<Ticket> {

    Map<String, String> getWeather(String city);

    List<Ticket> getTicketsInformation(String departureCity, String destinationCity);
}