package com.hitices.storage.core;

import com.hitices.storage.bean.AgentDatabaseBean;
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
    private List<AgentDatabaseBean> databases;
    private String status;
    private long lastHeartbeat;
    private int missedHeartbeats = 0;

    public StorageAgent(String id, String name, String ip, String port, String offline) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.status = offline;
    }

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
