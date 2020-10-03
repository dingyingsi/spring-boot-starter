package com.dys.springcloud.kafka.configure;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingyingsi
 */
@Configuration
@EnableKafka
@ConditionalOnProperty(name = "framework.kafka.consumer.enabled", havingValue = "true")
public class KafkaConsumerConfigure {

    @Value("${framework.kafka.consumer.servers}")
    private String servers;
    @Value("${framework.kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;
    @Value("${framework.kafka.consumer.session.timeout}")
    private String sessionTimeout;
    @Value("${framework.kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;
    @Value("${framework.kafka.consumer.group.id}")
    private String groupId;
    @Value("${framework.kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${framework.kafka.consumer.concurrency}")
    private int concurrency;
    @Value("#{framework.kafka.consumer.fetch.max.bytes.config")
    private int fetchMaxBytesConfig;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> frameworkKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        propsMap.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, fetchMaxBytesConfig);
        return propsMap;
    }
    
}