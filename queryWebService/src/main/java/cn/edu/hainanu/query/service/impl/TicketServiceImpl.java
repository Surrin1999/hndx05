package cn.edu.hainanu.query.service.impl;

import cn.edu.hainanu.query.domain.Ticket;
import cn.edu.hainanu.query.mapper.TicketMapper;
import cn.edu.hainanu.query.service.ITicketService;
import cn.edu.hainanu.query.util.AdCodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements ITicketService {

    @Resource
    private TicketMapper ticketMapper;

    @Value("${amap.url}")
    private String url;

    @Value("${amap.key}")
    private String key;

    @Value("${amap.extensions}")
    private String extensions;

    @Override
    public Map<String, String> getWeather(String city) {
        String adCode = AdCodeUtil.getAdCode(city);
        if (adCode == null) {
            return Collections.singletonMap("message", "请输入正确的城市名");
        }
        HashMap<String, String> resultMap = new HashMap<>(8);
        HashMap<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("key", key);
        paramMap.put("extensions", extensions);
        paramMap.put("city", city);
        String result = HttpUtil.get(url, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray lives = jsonObject.getJSONArray("lives");
        if (lives.size() > 0) {
            JSONObject weather = JSONUtil.parseObj(lives.get(0));
            resultMap.put("province", weather.getStr("province"));
            resultMap.put("city", weather.getStr("city"));
            resultMap.put("weather", weather.getStr("weather"));
            resultMap.put("temperature", weather.getStr("temperature"));
            resultMap.put("winddirection", weather.getStr("winddirection"));
            resultMap.put("windpower", weather.getStr("windpower"));
            resultMap.put("humidity", weather.getStr("humidity"));
            resultMap.put("reporttime", weather.getStr("reporttime"));
            return resultMap;
        }
        return Collections.singletonMap("message", "第三方平台异常，暂未找到相关天气数据");
    }

    @Override
    public List<Ticket> getTicketsInformation(String departureCity, String destinationCity) {
        if (departureCity == null || destinationCity == null) {
            return null;
        }
        return ChainWrappers.lambdaQueryChain(ticketMapper)
                .eq(Ticket::getDepartureCity, departureCity.trim())
                .eq(Ticket::getDestinationCity, destinationCity.trim()).list();
    }
}