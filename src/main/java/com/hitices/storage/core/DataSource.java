package com.hitices.storage.core;

import java.util.Map;

public interface DataSource {
    String getType();
    Map<String, String> getConnectionDetails();
}
