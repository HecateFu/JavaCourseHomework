package org.example.account.service;

import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.example.account.entity.TransferDto;
import org.example.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @HmilyTCC(confirmMethod = "exchangeConfirm",cancelMethod = "exchangeCancel")
    public boolean exchange(TransferDto transfer) {
        int count = accountMapper.frozen(transfer);
        if (count > 0) {
            return true;
        } else {
            throw new HmilyRuntimeException("账户扣减异常！");
        }
    }

    @Transactional
    public boolean exchangeConfirm(TransferDto transfer) {
        accountMapper.useFrozen(transfer);
        accountMapper.add(transfer);
        return true;
    }

    @Transactional
    public boolean exchangeCancel(TransferDto transfer) {
        accountMapper.releaseFrozen(transfer);
        return true;
    }


}
