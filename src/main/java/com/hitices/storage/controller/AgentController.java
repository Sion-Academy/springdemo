package com.hitices.storage.controller;

import com.hitices.storage.entity.StorageAgent;
import com.hitices.storage.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AgentController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/agent/list")
    public List<StorageAgent> listAgents() {
        return registrationService.listAgents();
    }
}
