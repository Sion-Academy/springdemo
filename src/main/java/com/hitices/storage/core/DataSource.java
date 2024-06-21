package com.hitices.storage.core;

import java.util.Map;

public interface DataSource {
    String getId();
    void setId(String id);
    String getType();
    Map<String, String> getConnectionDetails();
}
