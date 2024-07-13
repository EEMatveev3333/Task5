package org.example.repository;

import org.example.entity.AccountEntity;
import org.example.entity.AccountPoolEntity;

import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPoolRepo  extends JpaRepository<AccountPoolEntity, Integer> {
    AccountPoolEntity getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
            String branchCode,
            String currencyCode,
            String mdmCode,
            String registerTypeCode
    );

//    @Query("select u  from TppRefProductRegisterTypeEntity u " +
//            " inner join TppRefProductClassEntity  c  on c.value=u.productClassCode " +
//            " where    c.value=:value   and u.accountType=:accountType ")
//    List<TppRefProductRegisterTypeEntity> findByValueAndAccountType(@Param("value")String value,
//                                                                    @Param("accountType") String accountType);

    @Query("select AE.accountNumber  from AccountPoolEntity APE " +
            " inner join AccountEntity AE  on APE.id = AE.accountPoolId " +
            " where c.value=:AccountPoolEntity_ID ")
    List<String> getAccountsByAccountPoolEntityID(@Param("AccountPoolEntity_ID ")Integer value);

}
