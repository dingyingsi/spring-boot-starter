package com.dys.springcloud.redisson.propeties;

/**
 * @author dingyingsi
 */

public class RedisSentinelProperties {

    /**
     * 哨兵master 名称
     */
    private String masterName;

    /**
     * 哨兵节点
     */
    private String address;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failedSlaveCheckInterval;

    /**
     * 连接池中主节点的连接数量
     */
    private int masterConnectionPoolSize;

    /**
     * 连接池中从节点的连接数量
     */
    private int slaveConnectionPoolSize;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMasterOnlyWrite() {
        return masterOnlyWrite;
    }

    public void setMasterOnlyWrite(boolean masterOnlyWrite) {
        this.masterOnlyWrite = masterOnlyWrite;
    }

    public int getFailedSlaveCheckInterval() {
        return failedSlaveCheckInterval;
    }

    public void setFailedSlaveCheckInterval(int failedSlaveCheckInterval) {
        this.failedSlaveCheckInterval = failedSlaveCheckInterval;
    }

    public int getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public int getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }
}