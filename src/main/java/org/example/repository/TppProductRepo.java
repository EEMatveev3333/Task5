package org.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.entity.TppProductEntity;

@Repository
public interface TppProductRepo extends JpaRepository<TppProductEntity, Integer> {
    TppProductEntity getByNumber(String number);
    boolean existsById(Integer id);
}