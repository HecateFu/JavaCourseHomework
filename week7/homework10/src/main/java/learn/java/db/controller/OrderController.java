package learn.java.db.controller;

import learn.java.db.dao.mapper.TBizOrderMapper;
import learn.java.db.model.TBizOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private TBizOrderMapper tBizOrderMapper;

    @GetMapping("{id}")
    public TBizOrder getById(@PathVariable Long id){
        return tBizOrderMapper.selectByPrimaryKey(id);
    }

    @PostMapping("add")
    public String insert(@RequestBody TBizOrder order){
        tBizOrderMapper.insert(order);
        return String.valueOf(order.getId());
    }
}
