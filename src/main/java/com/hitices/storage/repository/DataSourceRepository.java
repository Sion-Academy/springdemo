package com.hitices.storage.repository;

import com.hitices.storage.entity.DataSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSourceEntity, String> {
}
