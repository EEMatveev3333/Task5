package org.example.repository;

import org.example.entity.AccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity, Integer> {
}

