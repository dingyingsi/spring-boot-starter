package com.dys.springcloud.rocketmq.annotation;

import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FrameworkRocketMQMessageListener {

    /**
     *
     * @return
     */
    MessageModel messageModel() default MessageModel.CLUSTERING;

}
