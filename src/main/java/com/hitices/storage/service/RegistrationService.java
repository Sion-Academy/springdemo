package com.hitices.storage.service;

import com.hitices.storage.bean.Heartbeat;
import com.hitices.storage.bean.RegistrationRequest;
import com.hitices.storage.entity.StorageAgent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class RegistrationService {
    private final ConcurrentHashMap<String, StorageAgent> registrations = new ConcurrentHashMap<>();

    public String register(RegistrationRequest request) {
        String uniqueId = UUID.randomUUID().toString();
        for (String storageId : registrations.keySet()){
            if (registrations.get(storageId).getName().equals(request.getName())){
                return storageId;
            }
        }
        registrations.put(uniqueId, new StorageAgent(request, "active"));
        return uniqueId;
    }

    public void update(Heartbeat heartbeat) {
        String uniqueId = heartbeat.getAgentId();
        if (uniqueId!=null){
            System.out.println(heartbeat.getRegistration().getDatabases());
            registrations.put(uniqueId, new StorageAgent(heartbeat.getRegistration(), "active"));
        }
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
}

