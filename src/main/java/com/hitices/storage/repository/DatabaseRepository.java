package com.hitices.storage.repository;

import com.hitices.storage.entity.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseRepository extends JpaRepository<DatabaseEntity, String> {
    @Override
    List<DatabaseEntity> findAll();

    List<DatabaseEntity> findAllByAgentId(String agentId);
}
