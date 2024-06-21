package com.hitices.storage.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBean {
    private String id;
    private String type;
    private Map<String, String> detail;
}
