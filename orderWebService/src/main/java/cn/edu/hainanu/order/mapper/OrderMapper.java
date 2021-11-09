package cn.edu.hainanu.order.mapper;

import cn.edu.hainanu.order.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends BaseMapper<Order> {

    int updateStatusById(@Param("id") Integer id, @Param("status") Integer status);
}