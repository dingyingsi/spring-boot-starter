package com.dys.springcloud.sharding.jdbc.properites;

import com.dys.springcloud.sharding.jdbc.properites.ShardingProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "framework.shardingsphere")
public class ShardingsphereProperties {

    private Map<String, ShardingProperty> table = new LinkedHashMap();
    private String bindingTableGroups;

    public Map<String, ShardingProperty> getTable() {
        return table;
    }

    public void setTable(Map<String, ShardingProperty> table) {
        this.table = table;
    }

    public String getBindingTableGroups() {
        return bindingTableGroups;
    }

    public void setBindingTableGroups(String bindingTableGroups) {
        this.bindingTableGroups = bindingTableGroups;
    }
}
