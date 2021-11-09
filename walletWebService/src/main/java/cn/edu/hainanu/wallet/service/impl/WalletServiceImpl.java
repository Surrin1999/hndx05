package cn.edu.hainanu.wallet.service.impl;

import cn.edu.hainanu.wallet.domain.Wallet;
import cn.edu.hainanu.wallet.mapper.WalletMapper;
import cn.edu.hainanu.wallet.service.IWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements IWalletService {

    @Resource
    private WalletMapper walletMapper;

    private static final String AMOUNT_REGEX = "^\\d+(\\.\\d+)?$";

    @Override
    public Map<String, String> rechargeBalance(Integer id, String amount) {
        Map<String, String> resultMap = new HashMap<>(2);
        if (!amount.matches(AMOUNT_REGEX)) {
            resultMap.put("message", "请正确输入充值金额");
            return resultMap;
        }
        BigDecimal amountValue = new BigDecimal(amount);
        if (amountValue.compareTo(BigDecimal.ZERO) == 0) {
            resultMap.put("message", "充值金额不能为0");
            return resultMap;
        }
        Wallet wallet = getById(id);
        if (wallet == null) {
            resultMap.put("message", "ID无效，请输入正确的ID");
            return resultMap;
        }
        wallet.setAmount(amountValue.add(wallet.getAmount()));
        boolean isSuccess = ChainWrappers.lambdaUpdateChain(walletMapper).update(wallet);
        resultMap.put("message", isSuccess ? "余额充值成功" : "余额充值失败");
        resultMap.put("amount", wallet.getAmount().toString());
        return resultMap;
    }
}