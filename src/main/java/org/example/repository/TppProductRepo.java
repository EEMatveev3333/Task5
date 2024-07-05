package org.example.repository;

import org.example.entity.TppProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppProductRepo extends JpaRepository<TppProductEntity, Integer> {
    TppProductEntity getByNumber(String number);
}