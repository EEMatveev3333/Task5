package org.example.repository;

import org.example.entity.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepo extends JpaRepository<AgreementEntity, Integer> {
}

