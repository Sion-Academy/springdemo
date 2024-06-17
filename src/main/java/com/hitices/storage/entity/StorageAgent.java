package com.hitices.storage.entity;

import com.hitices.storage.bean.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StorageAgent {
    private String ip;
    private String port;
    // todo: add more fields
    private String status;
    private long lastHeartbeat;
    private int missedHeartbeats = 0;

    public void incrementMissedHeartbeats() {
        this.missedHeartbeats++;
    }

    public StorageAgent(RegistrationRequest registrationRequest, String status){
        this.ip = registrationRequest.getIp();
        this.port = registrationRequest.getPort();
        this.status = status;
        this.lastHeartbeat = System.currentTimeMillis();
    }
}
