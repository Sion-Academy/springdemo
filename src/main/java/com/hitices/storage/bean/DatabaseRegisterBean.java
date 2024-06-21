package com.hitices.storage.bean;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseRegisterBean {
    private String agentId;
    private String databaseType;
    private String host;
    private int port;
    private JsonNode detail;
}
