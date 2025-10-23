/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.log.LogHelper
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.nr.task.api.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.task.api.aop.TaskLog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class TaskLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogAspect.class);
    private final Map<String, String> optionCache;
    private static final String FLAG = "#";
    private static final String DEFAULT_VALUE = "\u672a\u77e5\u64cd\u4f5c";
    private ObjectMapper mapper = new ObjectMapper();

    public TaskLogAspect() {
        this.optionCache = new ConcurrentHashMap<String, String>();
    }

    @Pointcut(value="@annotation(com.jiuqi.nr.task.api.aop.TaskLog)")
    public void pointcut() {
    }

    @Around(value="pointcut()")
    public Object doLog(ProceedingJoinPoint joinPoint) {
        Object proceed;
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        TaskLog annotation = signature.getMethod().getAnnotation(TaskLog.class);
        String operation = this.getOperation(this.getOperationKey(signature), annotation);
        String param = null;
        if (annotation.recordParam()) {
            param = this.buildArgs(joinPoint.getArgs());
        }
        try {
            proceed = joinPoint.proceed();
        }
        catch (Throwable e) {
            LogHelper.error((String)"\u4efb\u52a1\u8bbe\u8ba12.0", (String)operation, (String)String.format("\u6267\u884c\u5931\u8d25\u3002\r\n \u53c2\u6570\uff1a%s\r\n\u5f02\u5e38\uff1a%s", param, e.getMessage()));
            throw new RuntimeException(e);
        }
        String result = null;
        if (annotation.recordResult()) {
            result = this.buildResult(proceed);
        }
        LogHelper.info((String)"\u4efb\u52a1\u8bbe\u8ba12.0", (String)operation, (String)String.format("\u53c2\u6570\uff1a%s\r\n\u7ed3\u679c\uff1a%s", param, result));
        return proceed;
    }

    private String getOperation(String key, TaskLog annotation) {
        if (this.optionCache.containsKey(key)) {
            return this.optionCache.getOrDefault(key, DEFAULT_VALUE);
        }
        String operation = annotation.operation();
        this.optionCache.put(key, operation);
        return operation;
    }

    private String getOperationKey(MethodSignature signature) {
        return signature.getDeclaringTypeName() + FLAG + signature.getName();
    }

    private String buildArgs(Object[] args) {
        try {
            return this.mapper.writeValueAsString((Object)args);
        }
        catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return "\u5e8f\u5217\u5316\u9519\u8bef\uff0c\u65e0\u6cd5\u8bb0\u5f55";
        }
    }

    private String buildResult(Object result) {
        try {
            return this.mapper.writeValueAsString(result);
        }
        catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return "\u5e8f\u5217\u5316\u9519\u8bef\uff0c\u65e0\u6cd5\u8bb0\u5f55";
        }
    }
}

