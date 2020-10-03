package com.dys.springcloud.redisson.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dingyingsi
 */


@ConfigurationProperties(prefix = "spring.redis.redisson", ignoreUnknownFields = false)
public class RedisProperties {

    /**
     *
     */
    private int nettyThreads;

    /**
     *
     */
    private int threads;

    /**
     *
     */
    private int database;

    /**
     * 服务端影响超时时间
     */
    private int timeout;

    /**
     * 服务端等待客户端连接超时时间
     */
    private int soTimeout;

    /**
     * redis 连接密码
     */
    private String password;

    /**
     * 服务端运行模式
     */
    private String mode;

    /**
     * 连接池配置项
     */
    private RedisPoolProperties pool;

    /**
     *  单机模式
     */
    private RedisSingleProperties single;

    /**
     * 集群模式
     */
    private RedisClusterProperties cluster;

    /**
     * 哨兵模式
     */
    private RedisSentinelProperties sentinel;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public RedisPoolProperties getPool() {
        return pool;
    }

    public void setPool(RedisPoolProperties pool) {
        this.pool = pool;
    }

    public RedisSingleProperties getSingle() {
        return single;
    }

    public void setSingle(RedisSingleProperties single) {
        this.single = single;
    }

    public RedisClusterProperties getCluster() {
        return cluster;
    }

    public void setCluster(RedisClusterProperties cluster) {
        this.cluster = cluster;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public RedisSentinelProperties getSentinel() {
        return sentinel;
    }

    public void setSentinel(RedisSentinelProperties sentinel) {
        this.sentinel = sentinel;
    }
}