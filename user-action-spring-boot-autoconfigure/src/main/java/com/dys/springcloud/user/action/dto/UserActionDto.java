package com.dys.springcloud.user.action.dto;

import java.io.Serializable;
import java.util.Date;

public class UserActionDto implements Serializable {
    private Long userId;
    private String username;
    private String realName;
    private String mobilePhone;
    private String action;
    private String uri;
    private Integer hashCode;
    private Date actionTime;
    private String clientId;
    private String ip;
    private String contextPath;
    private String classMethod;
    private String httpMethod;
    private String exceptionName;
    private String exceptionMsg;
    private String requestParam;
    private String responseParam;
    private String module;
    private String contentType;

    public UserActionDto() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRealName() {
        return this.realName;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public String getAction() {
        return this.action;
    }

    public String getUri() {
        return this.uri;
    }

    public Integer getHashCode() {
        return this.hashCode;
    }

    public Date getActionTime() {
        return this.actionTime;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getIp() {
        return this.ip;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getClassMethod() {
        return this.classMethod;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public String getExceptionName() {
        return this.exceptionName;
    }

    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    public String getRequestParam() {
        return this.requestParam;
    }

    public String getResponseParam() {
        return this.responseParam;
    }

    public String getModule() {
        return this.module;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
    }

    public void setMobilePhone(final String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public void setHashCode(final Integer hashCode) {
        this.hashCode = hashCode;
    }

    public void setActionTime(final Date actionTime) {
        this.actionTime = actionTime;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public void setContextPath(final String contextPath) {
        this.contextPath = contextPath;
    }

    public void setClassMethod(final String classMethod) {
        this.classMethod = classMethod;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setExceptionName(final String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public void setExceptionMsg(final String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public void setRequestParam(final String requestParam) {
        this.requestParam = requestParam;
    }

    public void setResponseParam(final String responseParam) {
        this.responseParam = responseParam;
    }

    public void setModule(final String module) {
        this.module = module;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }
}