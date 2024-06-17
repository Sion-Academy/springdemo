package com.hitices.storage.controller;

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

    @PostMapping("/heartbeat")
    public void receiveHeartbeat(@RequestBody Heartbeat heartbeat) {
        registrationService.update(heartbeat);
        System.out.println("Received heartbeat from " + heartbeat.getAgentId());
    }

    @PostMapping("/register")
    public String registerAgent(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
