package com.hitices.storage.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/6/9 10:52
 */
@Getter
@Setter
@AllArgsConstructor
public class AgentDatabaseBean {
    private String name;
    private String type;
    private String url;
    private String status;
}
