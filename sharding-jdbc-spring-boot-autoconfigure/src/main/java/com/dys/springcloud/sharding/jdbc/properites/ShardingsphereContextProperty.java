package com.dys.springcloud.sharding.jdbc.properites;

import java.util.List;

public class ShardingsphereContextProperty {
    private String shardingColumn;
    private List<String> actualDataNodes;

    public String getShardingColumn() {
        return shardingColumn;
    }

    public void setShardingColumn(String shardingColumn) {
        this.shardingColumn = shardingColumn;
    }

    public List<String> getActualDataNodes() {
        return actualDataNodes;
    }

    public void setActualDataNodes(List<String> actualDataNodes) {
        this.actualDataNodes = actualDataNodes;
    }
}
