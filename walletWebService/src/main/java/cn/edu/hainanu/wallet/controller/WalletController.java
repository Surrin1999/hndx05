package cn.edu.hainanu.wallet.controller;

import cn.edu.hainanu.wallet.domain.Wallet;
import cn.edu.hainanu.wallet.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @GetMapping("/queryBalance")
    public Map<String, Object> queryBalance(@RequestParam("id") Integer id) {
        HashMap<String, Object> resultMap = new HashMap<>(2);
        Wallet wallet = walletService.getById(id);
        if (wallet != null) {
            resultMap.put("message", "查询成功");
            resultMap.put("amount", wallet.getAmount().toString());
        } else {
            resultMap.put("message", "查询失败，未找到此钱包数据");
        }
        return resultMap;
    }

    @GetMapping("/rechargeBalance")
    public Object rechargeBalance(@RequestParam("id") Integer id, @RequestParam("amount") String amount) {
        return walletService.rechargeBalance(id, amount);
    }

}