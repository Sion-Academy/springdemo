package com.hitices.storage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitices.storage.bean.DataSourceBean;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.bean.StorageBean;
import com.hitices.storage.core.DataSource;
import com.hitices.storage.core.DataSourceFactory;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.entity.DataSourceEntity;
import com.hitices.storage.entity.StorageRouteEntity;
import com.hitices.storage.repository.DataSourceRepository;
import com.hitices.storage.repository.StorageRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class DataSourceController {

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private StorageRouteRepository storageRouteRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @PostMapping("/source/register")
    public String registerDataSource(@RequestBody DataSourceRegisterBean dataSourceRegisterBean) {
        ObjectMapper mapper = new ObjectMapper();
        String id = dataSourceManager.registerDataSource(dataSourceFactory.getSource(dataSourceRegisterBean));
        try {
            dataSourceRepository.save(new DataSourceEntity(id,
                    dataSourceRegisterBean.getName(),
                    "activate",
                    dataSourceRegisterBean.getType(),
                    mapper.writeValueAsString(dataSourceRegisterBean.getDetail()),
                    dataSourceRegisterBean.getDescription(),
                    new Date(), new Date(), null));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @GetMapping("/source/list")
    public List<DataSourceBean> getDataSource() {
        return dataSourceManager.getDataSource();
    }

    @GetMapping("/source/detail")
    public DataSourceEntity getDataSourceDetail(@RequestParam(name = "id") String id) {
        return dataSourceRepository.findById(id).get();
    }

    @PostMapping("/source/route")
    public void getDataSource(@RequestBody StorageBean storageBean) {
        storageRouteRepository.save(new StorageRouteEntity(storageBean.getSourceId(),storageBean.getAgentId(),storageBean.getStorage()));
        dataSourceManager.registerAgentMap(storageBean.getSourceId(),storageBean.getAgentId());
        dataSourceManager.registerStorageMap(storageBean.getSourceId(),storageBean.getStorage());
    }

    @GetMapping("/route/list")
    public List<StorageBean> getDataSourceRoute() {
        return dataSourceManager.getDataSourceRoute();
    }
}
