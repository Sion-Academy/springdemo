package com.hitices.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data_base")
public class DatabaseEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "agent_id")
    private String agentId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String databaseType;

    @Column(name = "status")
    private String status;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private int port;

    @Column(name = "detail")
    private String detail;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
