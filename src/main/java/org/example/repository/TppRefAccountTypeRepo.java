package org.example.repository;

import org.example.entity.TppRefAccountTypeEntity;
import org.example.entity.TppRefProductClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TppRefAccountTypeRepo extends JpaRepository<TppRefAccountTypeEntity, Integer> {
    TppRefAccountTypeEntity getByValue(String productCode);
}
