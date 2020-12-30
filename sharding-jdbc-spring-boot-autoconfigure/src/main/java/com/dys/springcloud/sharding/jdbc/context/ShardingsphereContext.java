package com.dys.springcloud.sharding.jdbc.context;

import com.dys.springcloud.sharding.jdbc.properites.ShardingsphereContextProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShardingsphereContext {

    private static Map<String, ShardingsphereContextProperty> context = new LinkedHashMap();

    public static void setShardingsphereContextProperty(String logicTableName, ShardingsphereContextProperty shardingsphereContextProperty) {
        context.put(logicTableName, shardingsphereContextProperty);
    }

    public static ShardingsphereContextProperty getShardingsphereContextProperty(String logicTableName) {
        return context.get(logicTableName);
    }


}
