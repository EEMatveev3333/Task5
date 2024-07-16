package org.example.repository;

import org.example.entity.AccountEntity;
import org.example.entity.AccountPoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPoolRepo  extends JpaRepository<AccountPoolEntity, Integer> {
    AccountPoolEntity getByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegisterTypeCode(
            String branchCode,
            String currencyCode,
            String mdmCode,
            String priorityCode,
            String registerTypeCode
    );

//    @Query("select u  from TppRefProductRegisterTypeEntity u " +
//            " inner join TppRefProductClassEntity  c  on c.value=u.productClassCode " +
//            " where    c.value=:value   and u.accountType=:accountType ")
//    List<TppRefProductRegisterTypeEntity> findByValueAndAccountType(@Param("value")String value,
//                                                                    @Param("accountType") String accountType);

//    @Query("select AE.accountNumber  from account_pool APE " +
//            " inner join AccountEntity AE  on APE.id = AE.accountPoolId " +
//            " where APE.id = :AccountPoolEntity_ID ")

//    @Query("select AE.account_number from account AE where AE.id =:AccountPoolEntity_ID")
//    List<String> getAccountsByAccountPoolEntityID(@Param("AccountPoolEntity_ID")Integer value);

//    @Query("select APE.branchCode from AccountPoolEntity APE")
//    List<String> getAccountsByAccountPoolEntityID();

//
//   @Query("select AE.accountNumber from AccountPoolEntity APE inner join AccountEntity AE  on APE = AE.accountPoolId where APE.id = :AccountPoolEntity_ID")
//    List<String> getAccountsByAccountPoolEntityID(@Param("AccountPoolEntity_ID")Integer value);

//    @Query("select o as one, t as two from One o, Two t where exists (select t from Two t where substring(t.name, 4, 1) = substring(o.name, 4, 1)) or not exists (select t from Two t where substring(t.name, 4, 1) = substring(o.name, 4, 1))")
//    List<OneTwo> getOnesWithUnrelatedJoin();

    @Query("select AE from AccountPoolEntity APE inner join AccountEntity AE  on APE = AE.accountPoolId  " +
            " where APE.id = :AccountPoolEntity_ID and not exists ( select TPRE from TppProductRegisterEntity TPRE where TPRE.account = AE.id)")
    List<AccountEntity> getAccountsByAccountPoolEntityID(@Param("AccountPoolEntity_ID")Integer value);


}
