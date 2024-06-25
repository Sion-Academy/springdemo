package com.hitices.storage.core;

import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.core.source.LogDataSource;
import com.hitices.storage.core.source.RelationalDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {
    public DataSource getSource(DataSourceRegisterBean dataSourceRegisterBean) {
        switch (dataSourceRegisterBean.getType().toLowerCase()) {
            case "relational":
                return new RelationalDataSource(dataSourceRegisterBean);
            case "log":
                return new LogDataSource(dataSourceRegisterBean);
            default:
                throw new IllegalArgumentException("Unsupported data source type: " + dataSourceRegisterBean.getType());
        }
    }
}
