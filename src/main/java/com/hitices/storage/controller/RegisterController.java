package com.hitices.storage.controller;

import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.service.DatabaseService;
import com.hitices.storage.service.RegistrationService;
import com.hitices.storage.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/heartbeat")
    public HeartbeatCallback receiveHeartbeat(@RequestBody Heartbeat heartbeat) {
        registrationService.update(heartbeat);
        log.info("Received heartbeat from " + heartbeat.getAgentId());
        return new HeartbeatCallback(
                dataSourceManager.getAgentMap(),
                dataSourceManager.getStorageMap(),
                dataSourceManager.getAgentDataSource(heartbeat.getAgentId()),
                databaseService.getAgentDatabase(heartbeat.getAgentId()),
                registrationService.getAgentRoute());

    }

    @PostMapping("/register")
    public String registerAgent(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
