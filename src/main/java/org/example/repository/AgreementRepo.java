package org.example.repository;

import org.example.entity.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepo extends JpaRepository<AgreementEntity, Integer> {
}

