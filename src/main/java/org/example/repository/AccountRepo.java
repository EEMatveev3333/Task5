package org.example.repository;

import org.example.entity.AccountEntity;

import org.example.entity.AccountPoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Integer> {

    void deleteAllByAccountPoolIdAndAccountNumber(AccountPoolEntity accountPoolId, String accountNumber);

}

