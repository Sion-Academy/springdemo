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
        registrations.put(uniqueId, new StorageAgent(request, "active"));
        return uniqueId;
    }

    public void update(Heartbeat heartbeat) {
        String uniqueId = heartbeat.getAgentId();
        if (uniqueId!=null){
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
                }
            }
        });
    }


    public List<StorageAgent> listAgents() {
        return new ArrayList<>(registrations.values());
    }
}

