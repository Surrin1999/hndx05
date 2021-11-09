package cn.edu.hainanu.wallet.service;

import cn.edu.hainanu.wallet.domain.Wallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface IWalletService extends IService<Wallet> {

    Map<String, String> rechargeBalance(Integer id, String amount);
}
