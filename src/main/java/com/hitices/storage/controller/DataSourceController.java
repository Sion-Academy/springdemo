package com.hitices.storage.controller;

import com.hitices.storage.bean.DataSourceBean;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.bean.DatabaseRegisterBean;
import com.hitices.storage.core.DataSourceFactory;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.entity.StorageAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
public class DataSourceController {

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @PostMapping("/source/register")
    public String registerDataSource(@RequestBody DataSourceRegisterBean dataSourceRegisterBean) {
        return dataSourceManager.registerDataSource(dataSourceFactory.getSource(dataSourceRegisterBean));
    }

    @GetMapping("/source/list")
    public List<DataSourceBean> getDataSource() {
        return dataSourceManager.getDataSource();
    }
}
