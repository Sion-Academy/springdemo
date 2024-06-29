package com.hitices.storage.service;

import com.hitices.storage.bean.Heartbeat;
import com.hitices.storage.bean.RegistrationRequest;
import com.hitices.storage.core.StorageAgent;
import com.hitices.storage.entity.StorageAgentEntity;
import com.hitices.storage.repository.StorageAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class RegistrationService implements CommandLineRunner {
    private final ConcurrentHashMap<String, StorageAgent> registrations = new ConcurrentHashMap<>();

    @Autowired
    private StorageAgentRepository storageAgentRepository;

    public String register(RegistrationRequest request) {
        StorageAgentEntity agentEntity = storageAgentRepository.findByName(request.getName());
        String uniqueId = UUID.randomUUID().toString();
        if (agentEntity != null) {
            uniqueId = agentEntity.getId();
        }
        for (String storageId : registrations.keySet()){
            if (registrations.get(storageId).getName().equals(request.getName())){
                return storageId;
            }
        }
        registrations.put(uniqueId, new StorageAgent(request, "active"));
        storageAgentRepository.save(new StorageAgentEntity(uniqueId, request.getName(), request.getIp(), request.getPort()));
        return uniqueId;
    }

    public void update(Heartbeat heartbeat) {
        String uniqueId = heartbeat.getAgentId();
        if (uniqueId!=null){
            RegistrationRequest request = heartbeat.getRegistration();
            registrations.put(uniqueId, new StorageAgent(request, "active"));
            storageAgentRepository.save(new StorageAgentEntity(uniqueId, request.getName(), request.getIp(), request.getPort()));
        }
    }

    public Map<String, String> getAgentRoute(){
        Map<String, String> agentRoute = new ConcurrentHashMap<>();
        for (String id: registrations.keySet()){
            agentRoute.put(id, registrations.get(id).getIp()+":"+registrations.get(id).getPort());
        }
        return agentRoute;
    }

    @Scheduled(fixedRate = 10000)
    public void checkAgentStatuses() {
        long currentTime = System.currentTimeMillis();
        registrations.forEach((id, info) -> {
            if (currentTime - info.getLastHeartbeat() > 5000) {
                info.incrementMissedHeartbeats();
                if (info.getMissedHeartbeats() >= 3) {
                    System.out.println("Agent " + id + " is considered offline.");
                    info.setStatus("offline");
                    registrations.put(id, info);
                    // todo: remove all mappings related to this agent
                }
            }
        });
    }


    public List<StorageAgent> listAgents() {
        List<StorageAgent> databases = new ArrayList<>();
        for (String id: registrations.keySet()) {
            StorageAgent storageAgent = registrations.get(id);
            storageAgent.setId(id);
            databases.add(storageAgent);
        }
        return databases;
    }

    public StorageAgent getAgent(String id) {
        return registrations.get(id);
    }

    @Override
    public void run(String... args) throws Exception {
        List<StorageAgentEntity> storageAgentEntities = storageAgentRepository.findAll();
        for (StorageAgentEntity entity : storageAgentEntities) {
            registrations.put(entity.getId(),
                    new StorageAgent(
                            entity.getId(),
                            entity.getName(),
                            entity.getIp(),
                            entity.getPort(), "offline"));
        }
    }
}

