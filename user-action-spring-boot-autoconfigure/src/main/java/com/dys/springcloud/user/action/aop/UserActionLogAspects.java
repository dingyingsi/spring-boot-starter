package com.dys.springcloud.user.action.aop;

import com.dys.springcloud.user.action.annotation.UserActionLog;
import com.dys.springcloud.user.action.dto.UserActionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dingyingsi
 */
@Aspect
@Slf4j
public class UserActionLogAspects {

    private ObjectMapper objectMapper = null;

    @Resource
    private KafkaTemplate<String, String> frameworkKafkaTemplate;

    @Value("${framework.user.action.kafka.producer.topic}")
    private String frameworkUserActionKafkaProducerTopic;


    @PostConstruct
    public void postConstruct() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Pointcut(value = "@annotation(com.dys.springcloud.user.action.annotation.UserActionLog)")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void logStart(JoinPoint joinPoint) {
        // TODO
    }

    @AfterReturning(value = "pointCut()", returning = "object")
    public void logReturn(JoinPoint joinPoint, Object object) {
        try {
            UserActionDto userActionDto = this.processJoinPoint(joinPoint, object, null);

            this.frameworkKafkaTemplate.send(this.frameworkUserActionKafkaProducerTopic, "framework-user-action", this.objectMapper.writeValueAsString(userActionDto));
        } catch (Exception ex) {
            log.error("UserActionLogAspects.logReturn()发生异常, ex = {}", ex);
        }
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void logException(JoinPoint joinPoint, Throwable e) {
        try {
            UserActionDto userActionDto = this.processJoinPoint(joinPoint, null, e);

            this.frameworkKafkaTemplate.send(this.frameworkUserActionKafkaProducerTopic, "framework-user-action", this.objectMapper.writeValueAsString(userActionDto));
        } catch (Exception ex) {
            log.error("UserActionLogAspects.logException()发生异常, ex = {}", ex);
        }
    }

    @After(value = "pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        // TODO
    }

    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer exceptionMsg = new StringBuffer();
        for (StackTraceElement stet : elements) {
            exceptionMsg.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + exceptionMsg.toString();
        return message;
    }

    /**
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private UserActionDto processJoinPoint(JoinPoint joinPoint, Object returnObject, Throwable e) throws Exception {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String contextPath = httpRequest.getContextPath();
        String servletPath = httpRequest.getServletPath();
        String httpMethod = httpRequest.getMethod();
        String contentType = httpRequest.getHeader(HttpHeaders.CONTENT_TYPE);
        String ip = httpRequest.getHeader("X-Forwarded-For");

        Long userId = null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String classMethod = joinPoint.getTarget().getClass().getName() + "." + method.getName();
        List<Object> args = Arrays.stream(joinPoint.getArgs()).filter(arg -> !(arg instanceof ServletRequest)).filter(arg -> !(arg instanceof ServletResponse)).collect(Collectors.toList());

        UserActionLog userActionLog = method.getAnnotation(UserActionLog.class);
        String actionExpression = userActionLog.value();
        String module = userActionLog.module();
        boolean ignoreParam = userActionLog.ignoreParam();
        boolean ignoreReturn = userActionLog.ignoreReturn();

        Map<String, Object> argNameAndValue = this.getArgNameAndValue(method, joinPoint.getArgs());
        argNameAndValue.put("loginUser", null);

        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariables(argNameAndValue);
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        String action = spelExpressionParser.parseExpression(actionExpression, new TemplateParserContext()).getValue(standardEvaluationContext).toString();

        UserActionDto userActionDto = new UserActionDto();
        userActionDto.setUserId(null);
        userActionDto.setMobilePhone(null);
        userActionDto.setUsername(null);
        userActionDto.setRealName(null);
        userActionDto.setClientId(null);
        userActionDto.setIp(ip);
        userActionDto.setHttpMethod(httpMethod);
        userActionDto.setContentType(contentType);
        userActionDto.setUri(servletPath);
        userActionDto.setHashCode(servletPath.hashCode());
        userActionDto.setContextPath(contextPath);
        userActionDto.setAction(action);
        userActionDto.setActionTime(new Date());
        userActionDto.setClassMethod(classMethod);
        userActionDto.setRequestParam(ignoreParam ? "" : this.objectMapper.writeValueAsString(args));
        userActionDto.setModule(module);

        if(e != null) {
            userActionDto.setExceptionName(e.getClass().getName());
            userActionDto.setExceptionMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        }
        if (returnObject != null) {
            userActionDto.setResponseParam(ignoreReturn ? "" : this.objectMapper.writeValueAsString(returnObject));
        }

        return userActionDto;
    }

    /**
     * @param args
     * @return
     * @throws NotFoundException
     */
    private Map<String, Object> getArgNameAndValue(Method method, Object[] args) {

        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            Object value = args[i];
            if (!(value instanceof ServletRequest) || !(value instanceof ServletResponse)) {
                map.put(parameterNames[i], value);
            }
        }
        return map;
    }
}