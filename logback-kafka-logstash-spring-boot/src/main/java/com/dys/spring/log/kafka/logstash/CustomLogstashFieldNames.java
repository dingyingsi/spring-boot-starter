package com.dys.spring.log.kafka.logstash;

import net.logstash.logback.fieldnames.LogstashCommonFieldNames;
import net.logstash.logback.fieldnames.LogstashFieldNames;

/**
 * @author dingyingsi
 */
public class CustomLogstashFieldNames extends LogstashFieldNames {

    public static final String FIELD_LOGGER = "logger";
    public static final String FIELD_THREAD = "thread";
    public static final String FIELD_LEVEL_VAL = LogstashCommonFieldNames.IGNORE_FIELD_INDICATOR;
    public static final String FIELD_CALLER = "caller";
    public static final String FIELD_CLASS = "class";
    public static final String FIELD_METHOD = "method";
    public static final String FIELD_FILE = LogstashCommonFieldNames.IGNORE_FIELD_INDICATOR;
    public static final String FIELD_LINE = LogstashCommonFieldNames.IGNORE_FIELD_INDICATOR;
    public static final String FIELD_STACKTRACE = "stacktrace";

    public CustomLogstashFieldNames() {
        setLogger(FIELD_LOGGER);
        setThread(FIELD_THREAD);
        setCaller(FIELD_CALLER);
        setCallerClass(FIELD_CLASS);
        setCallerMethod(FIELD_METHOD);
        setCallerFile(FIELD_FILE);
        setCallerLine(FIELD_LINE);
        setStackTrace(FIELD_STACKTRACE);
        setLevelValue(FIELD_LEVEL_VAL);
    }
}