package org.example.services.implementations;


import org.example.entity.AccountEntity;
import org.example.entity.AccountPoolEntity;
import org.example.entity.TppProductEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.example.repository.AccountPoolRepo;
import org.example.repository.AccountRepo;
import org.example.services.interfaces.AccountNumServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
public class AccountNumService implements AccountNumServiceIntf {

    private AccountPoolRepo accountPoolRepo;

    private AccountRepo accountRepo;

//    public String getAccountNum(String branchCode, String currencyCode, String mdmCode, String priorityCode, TppRefProductRegisterTypeEntity registerType) {
//        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegisterTypeCode(
//                branchCode,
//                currencyCode,
//                mdmCode,
//                priorityCode,
//                registerType.getValue()
//        );
//
//
////        if (accountPool.getAccounts().isEmpty()) {
////            throw new NoResultException("В пуле счетов закончились счета");
////        }
//
//        if (accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId()).isEmpty()) {
////        if (accountPoolRepo.getAccountsByAccountPoolEntityID().isEmpty()) {
//            throw new NoResultException("В пуле счетов закончились счета");
//        }
//
////        List<String> accounts = accountPool.getAccounts();
//        List<String> accounts = accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId());
////        List<String> accounts = accountPoolRepo.getAccountsByAccountPoolEntityID();
//       String retAccountNum = accounts.get(0);
//
//        // Удаляем счёт из пула
//        //accounts111.remove(0);
//        accountRepo.deleteAllByAccountPoolIdAndAccountNumber(accountPool,retAccountNum);
//        //accountRepo.sa
//
//        return retAccountNum;
////    return mdmCode;
//    }

    public AccountEntity getAccount(String branchCode, String currencyCode, String mdmCode, String priorityCode, TppRefProductRegisterTypeEntity registerType) {
        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegisterTypeCode(
                branchCode,
                currencyCode,
                mdmCode,
                priorityCode,
                registerType.getValue()
        );


        if (accountPool == null) {
            throw new NoResultException("По переданным параметрам " +
                    "branchCode =\""+branchCode+"\" " +
                    "currencyCode =\""+currencyCode+"\" " +
                    "mdmCode =\""+mdmCode+"\" " +
                    "priorityCode =\""+priorityCode+"\" " +
                    "registerType.getValue() =\""+registerType.getValue()+"\" " +
                    " не найден пул счетов");
        }

//        if (accountPool.getAccounts().isEmpty()) {
//            throw new NoResultException("В пуле счетов закончились счета");
//        }

        if (accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId()).isEmpty()) {
//        if (accountPoolRepo.getAccountsByAccountPoolEntityID().isEmpty()) {
            throw new NoResultException("В пуле счетов закончились счета");
        }

//        List<String> accounts = accountPool.getAccounts();
        List<AccountEntity> accounts = accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId());
//        List<String> accounts = accountPoolRepo.getAccountsByAccountPoolEntityID();
        AccountEntity retAccountNum = accounts.get(0);

        // Удаляем счёт из пула
        //accounts111.remove(0);
        //accountRepo.deleteAllByAccountPoolIdAndAccountNumber(accountPool,retAccountNum);
        //accountRepo.sa

        return retAccountNum;
//    return mdmCode;
    }


    @Autowired
    public void setAccountPoolRepo(AccountPoolRepo accountPoolRepo) {
        this.accountPoolRepo = accountPoolRepo;
    }

    @Autowired
    public void setAccountRepo(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

}
