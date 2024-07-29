package com.hitices.storage.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseBean {
    private String id;
    private String agentName;
    private String name;
    private String databaseType;
    private String status;
    private Long sourceNum;
    private String host;
    private int port;
    private String detail;
    private String description;
    private Date createTime;
    private Date updateTime;
}
