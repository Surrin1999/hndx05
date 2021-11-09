package cn.edu.hainanu.order.util;

import java.text.SimpleDateFormat;

/**
 * 获取20位随机数
 * 4位年份+13位时间戳+3位随机数
 *
 * @author yuyu
 */
public class RandomUtil {

    /**
     * 20位末尾的数字id
     */
    private static int Guid = 100;

    public static String getOrderId() {

        RandomUtil.Guid += 1;

        long now = System.currentTimeMillis();
        //获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //获取时间戳
        String time = dateFormat.format(now);
        String info = now + "";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        if (RandomUtil.Guid > 999) {
            RandomUtil.Guid = 100;
        }
        int ran = RandomUtil.Guid;
        return time + info.substring(2) + ran;
    }
}
