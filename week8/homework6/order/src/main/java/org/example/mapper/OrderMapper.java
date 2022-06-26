package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Order;

@Mapper
public interface OrderMapper {
    @Insert("insert t_order(create_time,status,product_id,total_amount,count,user_id) " +
            "value(#{createTime},#{status},#{productId},#{totalAmount},#{count},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Update("update t_order set status=1 where id=#{id} and status=0")
    int update(Order order);

    @Delete("delete from t_order where id=#{id}")
    int deleteById(Order order);
}
