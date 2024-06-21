package com.hitices.storage.core;

import com.hitices.storage.bean.DataSourceBean;
import com.hitices.storage.service.RegistrationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class DataSourceManager {
    private final Map<String, DataSource> sourceMap = new ConcurrentHashMap<>();
    // 数据源-数据代理映射关系
    private final Map<String, String> agentMap = new ConcurrentHashMap<>();
    // 数据源-数据库映射关系
    private final Map<String, String> storageMap = new ConcurrentHashMap<>();


    // 添加数据源
    public String registerDataSource(DataSource source) {
        String identifier = String.valueOf(UUID.randomUUID());
        sourceMap.put(identifier, source);
        source.setId(identifier);
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

    // 添加数据源-数据代理映射
    public void registerAgentMap(String sourceId, String agentId) {
        // todo: 判断代理的可用性
        agentMap.put(sourceId, agentId);
    }

    public List<DataSourceBean> getAgentDataSource(String agentId){
        List<DataSourceBean> dataSourceBeanList = new ArrayList<>();
        for (String name: sourceMap.keySet()) {
            DataSource source = sourceMap.get(name);
            dataSourceBeanList.add(new DataSourceBean(name, source.getType(), source.getConnectionDetails()));
        }
        return dataSourceBeanList;
    }

    //添加数据源-数据库映射
    public void registerStorageMap(String sourceId, String databaseId) {
        // todo: 判断数据库的可用性
        storageMap.put(sourceId, databaseId);
    }
}
