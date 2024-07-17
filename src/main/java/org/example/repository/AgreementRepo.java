package org.example.repository;

import org.example.entity.AgreementEntity;
import org.example.entity.TppProductEntity;
import org.example.entity.TppRefProductClassEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepo extends JpaRepository<AgreementEntity, Integer> {
    //private TppProductEntity productId;
    //private String number;
    List<AgreementEntity> findAllByProductIdAndNumber(
            TppProductEntity productId,
            String number
    );
}

