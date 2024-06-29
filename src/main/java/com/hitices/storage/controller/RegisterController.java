package com.hitices.storage.controller;

import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.service.RegistrationService;
import com.hitices.storage.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private DataSourceManager dataSourceManager;

    @PostMapping("/heartbeat")
    public HeartbeatCallback receiveHeartbeat(@RequestBody Heartbeat heartbeat) {
        registrationService.update(heartbeat);
        System.out.println("Received heartbeat from " + heartbeat.getAgentId());
        // todo: 返回整体的源库映射, 以及路由表
        return new HeartbeatCallback(
                dataSourceManager.getAgentMap(),
                dataSourceManager.getStorageMap(),
                dataSourceManager.getAgentDataSource(heartbeat.getAgentId()),
                registrationService.getAgentRoute());

    }

    @PostMapping("/register")
    public String registerAgent(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
