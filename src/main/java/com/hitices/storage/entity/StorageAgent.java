package com.hitices.storage.entity;

import com.hitices.storage.bean.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class StorageAgent {
    private String id;
    private String ip;
    private String port;
    private String name;
    // todo: add more fields
    private Set<String> databases;
    private String status;
    private long lastHeartbeat;
    private int missedHeartbeats = 0;

    public void incrementMissedHeartbeats() {
        this.missedHeartbeats++;
    }

    public StorageAgent(RegistrationRequest registrationRequest, String status){
        this.ip = registrationRequest.getIp();
        this.port = registrationRequest.getPort();
        this.name = registrationRequest.getName();
        this.databases = registrationRequest.getDatabases();
        this.status = status;
        this.lastHeartbeat = System.currentTimeMillis();
    }
}
