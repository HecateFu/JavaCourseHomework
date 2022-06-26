package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.entity.Inventory;
import org.example.entity.InventoryDTO;

@Mapper
public interface InventoryMapper {
    @Update("update t_inventory " +
            "set total_inventory = total_inventory - #{count}," +
            "lock_inventory = lock_inventory + #{count} " +
            "where product_id = #{productId} and total_inventory>=#{count}")
    int reduceTry(InventoryDTO inventoryDTO);

    @Update("update t_inventory set lock_inventory = lock_inventory - #{count} " +
            "where product_id = #{productId} and lock_inventory>=#{count}")
    int reduceConfirm(InventoryDTO inventoryDTO);

    @Update("update t_inventory " +
            "set total_inventory = total_inventory + #{count}," +
            "lock_inventory = lock_inventory - #{count} " +
            "where product_id = #{productId} and lock_inventory>=#{count}")
    int reduceCancel(InventoryDTO inventoryDTO);

    @Select("select id, product_id as productId, " +
            "total_inventory as totalInventory, " +
            "lock_inventory as lockInventory " +
            "from t_inventory where product_id = #{productId}")
    Inventory selectById(String productId);
}
