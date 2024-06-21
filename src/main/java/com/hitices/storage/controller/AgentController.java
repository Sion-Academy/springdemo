package com.hitices.storage.controller;

import com.hitices.storage.bean.DatabaseRegisterBean;
import com.hitices.storage.entity.StorageAgent;
import com.hitices.storage.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/agent/database")
    public List listDatabase(@RequestParam(name = "id") String id) {
        RestTemplate restTemplate = new RestTemplate();
        StorageAgent storageAgent = registrationService.getAgent(id);
        return restTemplate.getForObject("http://"+storageAgent.getIp() + ":" + storageAgent.getPort() + "/getDatabases", List.class);
    }

    @PostMapping("/database/register")
    public String registerDatabase(@RequestBody DatabaseRegisterBean databaseRegisterBean) {
        RestTemplate restTemplate = new RestTemplate();
        StorageAgent storageAgent = registrationService.getAgent(databaseRegisterBean.getAgentId());
        return restTemplate.postForEntity("http://"+storageAgent.getIp() + ":" + storageAgent.getPort() + "/addDatabase",
                databaseRegisterBean, String.class).getBody();
    }
}
