package com.hitices.storage.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hitices.storage.bean.DataSourceBean;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.bean.StorageBean;
import com.hitices.storage.entity.DataSourceEntity;
import com.hitices.storage.entity.StorageRouteEntity;
import com.hitices.storage.repository.DataSourceRepository;
import com.hitices.storage.repository.StorageRouteRepository;
import com.hitices.storage.service.RegistrationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
@Slf4j
public class DataSourceManager implements CommandLineRunner {
    private final Map<String, DataSource> sourceMap = new ConcurrentHashMap<>();
    // 数据源-数据代理映射关系
    private final Map<String, String> agentMap = new ConcurrentHashMap<>();
    // 数据源-数据库映射关系
    private final Map<String, String> storageMap = new ConcurrentHashMap<>();

    @Autowired
    private DataSourceRepository dataSourceRepository;
    @Autowired
    private StorageRouteRepository storageRouteRepository;
    @Autowired
    private DataSourceFactory dataSourceFactory;

    // 添加数据源
    public String registerDataSource(DataSource source) {
        String identifier = String.valueOf(UUID.randomUUID());
        sourceMap.put(identifier, source);
        source.setId(identifier);
        log.info("Data source " + identifier + " has been registered.");
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

    public List<StorageBean> getDataSourceRoute(){
        List<StorageBean> storageBeans = new ArrayList<>();
        for (String name: sourceMap.keySet()) {
            storageBeans.add(new StorageBean(name, agentMap.get(name), storageMap.get(name)));
        }
        return storageBeans;
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

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<DataSourceEntity> dataSourceEntities = dataSourceRepository.findAll();
        for (DataSourceEntity entity : dataSourceEntities) {
            DataSource source = dataSourceFactory.getSource(
                    new DataSourceRegisterBean(entity.getType(), mapper.readTree(entity.getDetail())));
            sourceMap.put(entity.getId(), source);
        }
        List<StorageRouteEntity> storageRouteEntities = storageRouteRepository.findAll();
        for (StorageRouteEntity entity : storageRouteEntities) {
            agentMap.put(entity.getSourceId(), entity.getAgentId());
            storageMap.put(entity.getSourceId(), entity.getStorage());
        }
    }
}
