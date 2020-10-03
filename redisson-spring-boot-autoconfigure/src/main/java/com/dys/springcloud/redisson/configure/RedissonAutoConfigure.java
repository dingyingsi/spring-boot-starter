package com.dys.springcloud.redisson.configure;

import com.dys.springcloud.redisson.consts.RedissonConsts;
import com.dys.springcloud.redisson.propeties.RedisProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dingyingsi
 */

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonAutoConfigure {

    @Autowired
    private RedisProperties redisProperties;

    @Configuration
    @ConditionalOnClass({Redisson.class})
    @ConditionalOnExpression("'${spring.redis.redisson.mode}'=='single' or '${spring.redis.redisson.mode}'=='cluster' or '${spring.redis.redisson.mode}'=='sentinel'")
    protected class RedissonSingleClientConfiguration {

        /**
         * single mode redisson client
         *
         * @return RedissonClient
         */

        @Bean
        @ConditionalOnProperty(name = "spring.redis.redisson.mode", havingValue = RedissonConsts.REDIS_SINGLE)
        public RedissonClient redissonSingle() {

            String address = redisProperties.getSingle().getAddress();
            address = address.startsWith(RedissonConsts.REDIS_PROTOCOL) ? address : RedissonConsts.REDIS_PROTOCOL + address;

            Config config = new Config();
            config.setNettyThreads(redisProperties.getNettyThreads());
            config.setThreads(redisProperties.getThreads());
            config.setNettyThreads(redisProperties.getNettyThreads());
            config.setThreads(redisProperties.getThreads());
            SingleServerConfig serverConfig = config.useSingleServer()
                    .setAddress(address)
                    .setDatabase(redisProperties.getDatabase())
                    .setTimeout(redisProperties.getTimeout())

                    .setConnectTimeout(redisProperties.getPool().getConnectTimeout())
                    .setConnectionPoolSize(redisProperties.getPool().getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(redisProperties.getPool().getConnectionMinimumIdleSize());

            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
                serverConfig.setPassword(redisProperties.getPassword());
            }
            return Redisson.create(config);
        }


        /**
         * cluster mode redisson client
         *
         * @return RedissonClient
         */
        @Bean
        @ConditionalOnProperty(name = "spring.redis.redisson.mode", havingValue = RedissonConsts.REDIS_CLUSTER)
        public RedissonClient redissonCluster() {

            Config config = new Config();
            config.setNettyThreads(redisProperties.getNettyThreads());
            config.setThreads(redisProperties.getThreads());
            String[] nodeAddress = redisProperties.getCluster().getAddress().split(",");
            List<String> newNodes = new ArrayList<>(nodeAddress.length);
            Arrays.stream(nodeAddress).forEach((address) -> newNodes.add(
                    address.startsWith(RedissonConsts.REDIS_PROTOCOL) ? address : RedissonConsts.REDIS_PROTOCOL + address));

            ClusterServersConfig serverConfig = config.useClusterServers()
                    .addNodeAddress(newNodes.toArray(new String[0]))

                    .setTimeout(redisProperties.getTimeout())
                    .setReadMode(ReadMode.SLAVE)

                    .setIdleConnectionTimeout(redisProperties.getPool().getIdleConnectionTimeout())
                    .setConnectTimeout(redisProperties.getPool().getConnectTimeout())

                    .setScanInterval(redisProperties.getCluster().getScanInterval())
                    .setFailedSlaveCheckInterval(redisProperties.getCluster().getFailedSlaveCheckInterval())
                    .setRetryAttempts(redisProperties.getCluster().getRetryAttempts())
                    .setRetryInterval(redisProperties.getCluster().getRetryInterval())
                    .setMasterConnectionPoolSize(redisProperties.getCluster().getMasterConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(redisProperties.getCluster().getMasterConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(redisProperties.getCluster().getSlaveConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(redisProperties.getCluster().getSlaveConnectionMinimumIdleSize());

            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
                serverConfig.setPassword(redisProperties.getPassword());
            }
            return Redisson.create(config);
        }

        /**
         * sentinel mode redisson client
         *
         * @return RedissonClient
         */

        @Bean
        @ConditionalOnProperty(name = "spring.redis.redisson.mode", havingValue = RedissonConsts.REDIS_SENTINEL)
        public RedissonClient redissonSentinel() {

            Config config = new Config();
            config.setNettyThreads(redisProperties.getNettyThreads());
            config.setThreads(redisProperties.getThreads());
            String[] sentinelAddress = redisProperties.getSentinel().getAddress().split(",");
            List<String> newNodes = new ArrayList<>(sentinelAddress.length);
            Arrays.stream(sentinelAddress).forEach((address) -> newNodes.add(
                    address.startsWith(RedissonConsts.REDIS_PROTOCOL) ? address : RedissonConsts.REDIS_PROTOCOL + address));

            SentinelServersConfig serverConfig = config.useSentinelServers()

                    .addSentinelAddress(newNodes.toArray(new String[0]))
                    .setDatabase(redisProperties.getDatabase())
                    .setTimeout(redisProperties.getTimeout())
                    .setReadMode(ReadMode.SLAVE)

                    .setMasterConnectionPoolSize(redisProperties.getSentinel().getMasterConnectionPoolSize())
                    .setSlaveConnectionPoolSize(redisProperties.getSentinel().getSlaveConnectionPoolSize())

                    .setMasterName(redisProperties.getSentinel().getMasterName())
                    .setFailedSlaveCheckInterval(redisProperties.getSentinel().getFailedSlaveCheckInterval())
                    .setConnectTimeout(redisProperties.getPool().getConnectTimeout());

            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
                serverConfig.setPassword(redisProperties.getPassword());
            }

            return Redisson.create(config);
        }
    }
}