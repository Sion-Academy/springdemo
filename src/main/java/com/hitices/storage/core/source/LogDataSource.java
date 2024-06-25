package com.hitices.storage.core.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.core.DataSource;

import java.util.Collections;
import java.util.Map;

public class LogDataSource implements DataSource {
    private String id;
    private Map<String, String> metrics = Collections.emptyMap();

    public LogDataSource(DataSourceRegisterBean dataSourceRegisterBean) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.metrics = objectMapper.convertValue(dataSourceRegisterBean.getDetail(), Map.class);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return "log";
    }

    @Override
    public Map<String, String> getConnectionDetails() {
        return metrics;
    }
}
