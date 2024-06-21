package com.hitices.storage.controller;

import com.hitices.storage.core.DataSource;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.entity.StorageAgent;
import com.hitices.storage.service.RegistrationService;
import com.hitices.storage.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private DataSourceManager dataSourceManager;

    @PostMapping("/heartbeat")
    public List<DataSourceBean> receiveHeartbeat(@RequestBody Heartbeat heartbeat) {
        registrationService.update(heartbeat);
        System.out.println("Received heartbeat from " + heartbeat.getAgentId());
        // todo: 返回整体的源库映射, 以及路由表
        return dataSourceManager.getAgentDataSource(heartbeat.getAgentId());
    }

    @PostMapping("/register")
    public String registerAgent(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
