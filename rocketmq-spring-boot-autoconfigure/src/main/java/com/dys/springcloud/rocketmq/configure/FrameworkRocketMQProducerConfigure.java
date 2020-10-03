package com.dys.springcloud.rocketmq.configure;

import com.dys.springcloud.rocketmq.properties.FrameworkRocketMQProducerProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({ FrameworkRocketMQProducerProperties.class })
@ConditionalOnProperty(prefix = "framework.rocketmq.producer", name = "enabled", havingValue = "true")
public class FrameworkRocketMQProducerConfigure {

    @Resource
    private FrameworkRocketMQProducerProperties frameworkRocketMQProducerProperties;

    @Bean
    public DefaultMQProducer frameworkRocketMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(this.frameworkRocketMQProducerProperties.getNamesrvAddr());
        producer.setProducerGroup(this.frameworkRocketMQProducerProperties.getProducerGroup());
        producer.setInstanceName("frameworkRocketMQProducer");
        producer.setMaxMessageSize(this.frameworkRocketMQProducerProperties.getMaxMessageSize());
        producer.setSendMsgTimeout(this.frameworkRocketMQProducerProperties.getSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(this.frameworkRocketMQProducerProperties.getRetryTimesWhenSendFailed());

        try {
            producer.start();
        } catch (MQClientException e) {
            throw e;
        }
        return producer;
    }

}
