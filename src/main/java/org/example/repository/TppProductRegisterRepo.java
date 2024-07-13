package org.example.repository;

import org.example.entity.TppProductEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TppProductRegisterRepo extends JpaRepository<TppProductRegisterEntity, Integer> {

//    boolean existsByProductIdAndRegisterType(
//            TppProductEntity productId,
//            TppRefProductRegisterTypeEntity registerType
//    );
//
//    TppProductRegisterEntity getByProductIdAndRegisterType(
//            TppProductEntity productId,
//            TppRefProductRegisterTypeEntity registerType
//    );

    boolean existsByProductIdAndRegisterType(
            Long productId,
            String registerType
    );

    TppProductRegisterEntity getByProductIdAndRegisterType(
            Long productId,
            String registerType
    );

}

