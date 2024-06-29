package com.hitices.storage.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatCallback {
    /**
     * 数据源-数据代理映射
     */
    Map<String, String> agentMap;
    /**
     * 数据源-数据库映射
     */
    Map<String, String> storageMap;
    /**
     * 数据源
     */
    List<DataSourceBean> dataSourceBeans;
    /**
     * 数据代理路由表
     */
    Map<String, String> agentRouteMap;
}
