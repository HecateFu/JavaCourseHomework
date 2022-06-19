package learn.java.db.controller;

import learn.java.db.annotation.Readonly;
import learn.java.db.dao.mapper.TBizOrderMapper;
import learn.java.db.model.TBizOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private TBizOrderMapper tBizOrderMapper;

    /**
     * 只读方法加注解走从库
     * @param id 订单id
     * @return 订单记录
     */
    @Readonly
    @GetMapping("{id}")
    public TBizOrder getById(@PathVariable Long id){
        return tBizOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 插入方法默认走主库
     * @param order 订单数据
     * @return 新订单id
     */
    @PostMapping("add")
    public String insert(@RequestBody TBizOrder order){
        tBizOrderMapper.insert(order);
        return String.valueOf(order.getId());
    }
}
