package com.hitices.storage.repository;

import com.hitices.storage.entity.StorageAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageAgentRepository extends JpaRepository<StorageAgentEntity, String>{
    StorageAgentEntity findByName(String name);
}
