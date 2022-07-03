package org.example.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.account.entity.TransferDto;

@Mapper
public interface AccountMapper {
    @Update("update t_account set amount = amount - #{fromAmount} ,frozen = frozen+#{fromAmount}" +
            "where user_code = #{userCode} " +
            "and currency_type = #{fromCurrencyType} " +
            "and amount >= #{fromAmount}")
    int frozen(TransferDto transfer);

    @Update("update t_account set frozen = frozen-#{fromAmount}" +
            "where user_code = #{userCode} " +
            "and currency_type = #{fromCurrencyType} " +
            "and frozen >= #{fromAmount}")
    int useFrozen(TransferDto transfer);

    @Update("update t_account set amount = amount + #{toAmount} " +
            "where user_code = #{userCode} " +
            "and currency_type = #{toCurrencyType} ")
    int add(TransferDto transfer);

    @Update("update t_account set amount = amount + #{fromAmount} ,frozen = frozen-#{fromAmount}" +
            "where user_code = #{userCode} " +
            "and currency_type = #{fromCurrencyType} " +
            "and frozen >= #{fromAmount}")
    int releaseFrozen(TransferDto transfer);
}
