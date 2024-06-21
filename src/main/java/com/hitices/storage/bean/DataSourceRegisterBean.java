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
public class DataSourceRegisterBean {
    private String type;
    private JsonNode detail;

    public String getProperties(String name){
        return detail.get(name).asText();
    }
}
