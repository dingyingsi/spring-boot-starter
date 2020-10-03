package com.dys.springcloud.rocketmq.configure;

import com.dys.springcloud.rocketmq.annotation.FrameworkRocketMQMessageListener;
import com.dys.springcloud.rocketmq.properties.FrameworkRocketMQConsumerProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({FrameworkRocketMQConsumerProperties.class })
@ConditionalOnProperty(prefix = "framework.rocketmq.consumer", name = "enabled", havingValue = "true")
public class FrameworkRocketMQConsumerConfigure implements ApplicationContextAware {

    @Resource
    private FrameworkRocketMQConsumerProperties frameworkRocketMQConsumerProperties;

    private ApplicationContext applicationContext;

    @Bean
    public DefaultMQPushConsumer frameworkDefaultMQPushConsumer()  {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(this.frameworkRocketMQConsumerProperties.getNamesrvAddr());
        consumer.setConsumerGroup(this.frameworkRocketMQConsumerProperties.getConsumerGroup());
        consumer.setConsumeThreadMin(this.frameworkRocketMQConsumerProperties.getConsumeThreadMin());
        consumer.setConsumeThreadMax(this.frameworkRocketMQConsumerProperties.getConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(this.frameworkRocketMQConsumerProperties.getConsumeMessageBatchMaxSize());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        consumer.setConsumeMessageBatchMaxSize(this.frameworkRocketMQConsumerProperties.getConsumeMessageBatchMaxSize());
        try {
            String[] topicAndTags = this.frameworkRocketMQConsumerProperties.getTopicAndTags().split(";");
            for (String topicAndTag : topicAndTags) {
                String[] topicTag = topicAndTag.split(":");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }

            Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(FrameworkRocketMQMessageListener.class)
                    .entrySet().stream().filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (beans != null && beans.size() == 1) {
                MessageListener messageListener = (MessageListener)beans.values().stream().findFirst().get();
                FrameworkRocketMQMessageListener annotation = messageListener.getClass().getAnnotation(FrameworkRocketMQMessageListener.class);
                MessageModel messageModel = annotation.messageModel();

                consumer.setMessageModel(messageModel);
                consumer.setMessageListener(messageListener);
            }

            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
        return consumer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
