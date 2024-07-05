package org.example.repository;

import org.example.entity.AccountPoolEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPoolRepo  extends JpaRepository<AccountPoolEntity, Integer> {
    AccountPoolEntity getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
            String branchCode,
            String currencyCode,
            String mdmCode,
            String registerTypeCode
    );
}
