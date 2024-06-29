package com.hitices.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "storage_route")
public class StorageRouteEntity {
    @Id
    @Column(name = "source_id")
    private String sourceId;
    @Column(name = "agent_id")
    private String agentId;
    @Column(name = "storage")
    private String storage;
}
