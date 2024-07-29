package com.hitices.storage.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBean {
    private String id;
    private String name;
    private String status;
    private String type;
    private Map<String, String> detail;
    private String description;
    private Date createTime;
    private Date modifyTime;
    private Date dataUpdateTime;
}
