package org.example.services.implementations;


import org.example.entity.AccountPoolEntity;
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

    public String getAccountNum(String branchCode, String currencyCode, String mdmCode, TppRefProductRegisterTypeEntity registerType) {
        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
                branchCode,
                currencyCode,
                mdmCode,
                registerType.getValue()
        );

//        if (accountPool.getAccounts().isEmpty()) {
//            throw new NoResultException("В пуле счетов закончились счета");
//        }

        if (accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId()).isEmpty()) {
            throw new NoResultException("В пуле счетов закончились счета");
        }

//        List<String> accounts = accountPool.getAccounts();
        List<String> accounts = accountPoolRepo.getAccountsByAccountPoolEntityID(accountPool.getId());
        String retAccountNum = accounts.get(0);

        // Удаляем счёт из пула
        //accounts111.remove(0);
        accountRepo.deleteAllByAccountPoolIdAndAccountNumber(accountPool.getId(),retAccountNum);


        return retAccountNum;
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
