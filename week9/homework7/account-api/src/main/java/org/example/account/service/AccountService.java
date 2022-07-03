package org.example.account.service;

import org.dromara.hmily.annotation.Hmily;
import org.example.account.entity.TransferDto;

public interface AccountService {
    @Hmily
    boolean exchange(TransferDto transfer);


}
