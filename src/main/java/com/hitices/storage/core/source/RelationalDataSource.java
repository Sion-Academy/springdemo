package com.hitices.storage.core.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitices.storage.bean.DataSourceRegisterBean;
import com.hitices.storage.core.DataSource;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Getter
public class RelationalDataSource implements DataSource {
    private final String schema;
    private final String table;
    private final String[] columns;

    public RelationalDataSource(DataSourceRegisterBean dataSourceRegisterBean) {
        ObjectMapper mapper = new ObjectMapper();
        this.schema = dataSourceRegisterBean.getProperties("schema");
        this.table = dataSourceRegisterBean.getProperties("table");
        this.columns = Arrays.stream(mapper.convertValue(dataSourceRegisterBean.getDetail().get("columns"), String[].class)).toArray(String[]::new);
    }

    @Override
    public String getType() {
        return "Relational";
    }

    @Override
    public Map<String, String> getConnectionDetails() {
        Map<String, String> details = new HashMap<>();
        details.put("schema", schema);
        details.put("table", table);
        details.put("columns", String.join(", ", columns));
        return details;
    }
}
