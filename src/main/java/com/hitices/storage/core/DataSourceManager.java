package com.hitices.storage.core;

import com.hitices.storage.bean.DataSourceBean;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

@Getter
@Component
public class DataSourceManager {
    private final Map<String, DataSource> sourceMap = new ConcurrentHashMap<>();

    // 添加数据源
    public String registerDataSource(DataSource source) {
        String identifier = String.valueOf(UUID.randomUUID());
        sourceMap.put(identifier, source);
        System.out.println("Data source " + identifier + " has been registered.");
        return identifier;
    }

    public List<DataSourceBean> getDataSource(){
        List<DataSourceBean> dataSourceBeanList = new ArrayList<>();
        for (String name: sourceMap.keySet()) {
            DataSource source = sourceMap.get(name);
            dataSourceBeanList.add(new DataSourceBean(name, source.getType(), source.getConnectionDetails()));
        }
        return dataSourceBeanList;
    }
}
