package com.dys.springcloud.sharding.jdbc.service;


public interface PreciseShardingAlgorithmService {

    PreciseShardingAlgorithm getTablePreciseShardingAlgorithm(String shardingColumn);

    Map<String, PreciseShardingAlgorithm> getTablePreciseShardingAlgorithms();

    PreciseShardingAlgorithm getDatabasePreciseShardingAlgorithm(String shardingColumn);

    Map<String, PreciseShardingAlgorithm> getDatabasePreciseShardingAlgorithms();

}