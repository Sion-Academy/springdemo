package com.hitices.storage.controller;

import com.hitices.storage.bean.DataSourceBean;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.bean.StorageBean;
import com.hitices.storage.core.DataSourceFactory;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.entity.StorageRouteEntity;
import com.hitices.storage.repository.StorageRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/source/register")
    public String registerDataSource(@RequestBody DataSourceRegisterBean dataSourceRegisterBean) {
        return dataSourceManager.registerDataSource(dataSourceFactory.getSource(dataSourceRegisterBean));
    }

    @GetMapping("/source/list")
    public List<DataSourceBean> getDataSource() {
        return dataSourceManager.getDataSource();
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
