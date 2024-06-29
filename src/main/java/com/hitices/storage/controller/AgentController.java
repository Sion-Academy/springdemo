package com.hitices.storage.controller;

import com.hitices.storage.bean.DatabaseRegisterBean;
import com.hitices.storage.bean.SearchBean;
import com.hitices.storage.core.DataSourceManager;
import com.hitices.storage.core.StorageAgent;
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

    @Autowired
    private DataSourceManager dataSourceManager;

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

    @GetMapping("/agent/detail")
    public StorageAgent getAgentDetail(@RequestParam(name = "id") String id) {
        return registrationService.getAgent(id);
    }

    @GetMapping("/database/detail")
    public Object getDatabaseDetail(@RequestParam(name = "agentId") String agentId, @RequestParam(name = "storageId") String storageId) {
        RestTemplate restTemplate = new RestTemplate();
        StorageAgent storageAgent = registrationService.getAgent(agentId);
        return restTemplate.getForObject(
                "http://"+storageAgent.getIp() + ":" + storageAgent.getPort() + "/database/detail?storageId="+storageId,
                Object.class);
    }

    @PostMapping("/database/register")
    public String registerDatabase(@RequestBody DatabaseRegisterBean databaseRegisterBean) {
        RestTemplate restTemplate = new RestTemplate();
        StorageAgent storageAgent = registrationService.getAgent(databaseRegisterBean.getAgentId());
        return restTemplate.postForEntity("http://"+storageAgent.getIp() + ":" + storageAgent.getPort() + "/addDatabase",
                databaseRegisterBean, String.class).getBody();
    }

    @PostMapping("/searchData")
    public String searchData(@RequestBody SearchBean searchBean) {
        RestTemplate restTemplate = new RestTemplate();
        StorageAgent storageAgent = registrationService.getAgent(dataSourceManager.getAgentMap().get(searchBean.getSourceId()));
        return restTemplate.postForEntity("http://"+storageAgent.getIp() + ":" + storageAgent.getPort() + "/searchData",
                searchBean, String.class).getBody();
    }
}
