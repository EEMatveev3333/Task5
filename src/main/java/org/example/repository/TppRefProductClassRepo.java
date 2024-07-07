package org.example.repository;

import org.example.entity.TppRefProductClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TppRefProductClassRepo extends JpaRepository<TppRefProductClassEntity, Integer> {
}
