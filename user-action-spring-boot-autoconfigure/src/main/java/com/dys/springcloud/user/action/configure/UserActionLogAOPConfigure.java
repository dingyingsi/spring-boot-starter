package com.dys.springcloud.user.action.configure;

import com.dys.springcloud.user.action.aop.UserActionLogAspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dingyingsi
 */
@Configuration
@EnableAspectJAutoProxy
public class UserActionLogAOPConfigure {

    @Bean
    public UserActionLogAspects operationLogAspects() {
        UserActionLogAspects operationLogAspects = new UserActionLogAspects();
        return operationLogAspects;
    }
}
