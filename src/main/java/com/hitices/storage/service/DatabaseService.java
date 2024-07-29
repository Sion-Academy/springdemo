package com.hitices.storage.service;

import antlr.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitices.storage.bean.DatabaseBean;
import com.hitices.storage.bean.DatabaseRegisterBean;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.entity.DatabaseEntity;
import com.hitices.storage.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DatabaseService {
    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private RegistrationService registrationService;

    public List<DatabaseEntity> getAgentDatabase(String agentId) {
        return databaseRepository.findAllByAgentId(agentId);
    }

    public List<DatabaseBean> getDatabase(String agentId) {
        List<DatabaseBean> databaseBeans = new ArrayList<>();
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        if (agentId == null || agentId.isEmpty()){
            databaseEntities = databaseRepository.findAll();
        }else {
            databaseEntities = databaseRepository.findAllByAgentId(agentId);
        }
        for (DatabaseEntity databaseEntity: databaseEntities){
            DatabaseBean databaseBean = new DatabaseBean();
            databaseBean.setId(databaseEntity.getId());
            databaseBean.setAgentName(registrationService.getAgent(databaseEntity.getAgentId()).getName());
            databaseBean.setName(databaseEntity.getName());
            databaseBean.setDatabaseType(databaseEntity.getDatabaseType());
            databaseBean.setStatus(databaseEntity.getStatus());
            databaseBean.setSourceNum(dataSourceManager.getDataSourceCountByDatabase(databaseEntity.getId()));
            databaseBean.setHost(databaseEntity.getHost());
            databaseBean.setPort(databaseEntity.getPort());
            databaseBean.setDetail(databaseEntity.getDetail());
            databaseBean.setDescription(databaseEntity.getDescription());
            databaseBean.setCreateTime(databaseEntity.getCreateTime());
            databaseBean.setUpdateTime(databaseEntity.getUpdateTime());
            databaseBeans.add(databaseBean);
        }
        return databaseBeans;
    }

    public String addDatabase(DatabaseRegisterBean databaseRegisterBean) {
        ObjectMapper mapper = new ObjectMapper();
        String identifier = String.valueOf(UUID.randomUUID());
        String id = databaseRegisterBean.getDatabaseType()+"-"+identifier;
        try {
            databaseRepository.save(
                    new DatabaseEntity(id,
                            databaseRegisterBean.getAgentId(),
                            databaseRegisterBean.getName(),
                            databaseRegisterBean.getDatabaseType(),
                            "activate",
                            databaseRegisterBean.getHost(),
                            databaseRegisterBean.getPort(),
                            mapper.writeValueAsString(databaseRegisterBean.getDetail()),
                            databaseRegisterBean.getDescription(),
                            new Date(),new Date()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public DatabaseEntity getDatabaseDetail(String storageId){
        return databaseRepository.findById(storageId).orElse(null);
    }
}
