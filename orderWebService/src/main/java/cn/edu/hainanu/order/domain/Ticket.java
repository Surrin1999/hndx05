package cn.edu.hainanu.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tickets")
public class Ticket {
    private Integer id;
    private String classes;
    private String departureCity;
    private String destinationCity;
    private String departureTime;
    private Integer ticketSurplus;
    private BigDecimal money;
}