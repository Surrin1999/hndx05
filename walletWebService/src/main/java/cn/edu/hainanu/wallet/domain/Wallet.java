package cn.edu.hainanu.wallet.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("wallets")
public class Wallet {
    private Integer id;
    private String userName;
    private BigDecimal amount;
}