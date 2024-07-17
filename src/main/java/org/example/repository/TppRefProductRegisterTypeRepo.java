package org.example.repository;

import org.example.entity.TppRefAccountTypeEntity;
import org.example.entity.TppRefProductClassEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TppRefProductRegisterTypeRepo extends JpaRepository<TppRefProductRegisterTypeEntity, Integer> {

//    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndAccountType(String productClassCode, String accountType);
//    TppRefAccountTypeEntity
//    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndAccountType(String productClassCode, String accountType);
    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndAccountType(
            TppRefProductClassEntity productClassCode,
            TppRefAccountTypeEntity accountType
    );

    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndValue(
            TppRefProductClassEntity productClassCode,
            String value
    );

    TppRefProductRegisterTypeEntity getByValue(
            String value
    );

    List<TppRefProductRegisterTypeEntity> findAllByValue(
             String value
    );
}
