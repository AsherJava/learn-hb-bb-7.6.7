/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.AfterReturning
 *  org.aspectj.lang.annotation.AfterThrowing
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.nr.data.access.aop;

import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Deprecated
public class DataAccessAspect {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessAspect.class);
    private ThreadLocal<Set<String>> ignoreItemsRequest = ThreadLocal.withInitial(() -> new HashSet());

    @Pointcut(value="execution(* com.jiuqi.nr.data.access.service.IDataAccessFormService.getBatchAccessForms(..))")
    public void formBatchAccessPoint() {
        if (logger.isDebugEnabled()) {
            logger.debug("\u8fdb\u53bb\u6279\u91cf\u83b7\u53d6\u62a5\u8868\u6743\u9650\u7684\u5207\u9762...");
        }
    }

    @Pointcut(value="execution(* com.jiuqi.nr.data.access.service.IDataAccessItemBaseService.isEnable(..))")
    public void ignorePoint() {
        if (logger.isDebugEnabled()) {
            logger.debug("\u8fdb\u53bb\u6279\u91cf\u83b7\u53d6\u62a5\u8868\u6743\u9650-\u5ffd\u7565\u6743\u9650\u9879\u7684\u5207\u9762...");
        }
    }

    @Around(value="formBatchAccessPoint()")
    public DimensionAccessFormInfo executeAroundFormBatchAccess(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        if (Objects.isNull(args)) {
            return new DimensionAccessFormInfo();
        }
        HashSet<String> ignoreItems = new HashSet<String>();
        if (args[0] instanceof AccessFormParam) {
            AccessFormParam accessFormParam = (AccessFormParam)args[0];
            ignoreItems.addAll(accessFormParam.getIgnoreAccessItems());
        }
        this.ignoreItemsRequest.set(ignoreItems);
        try {
            return (DimensionAccessFormInfo)proceedingJoinPoint.proceed();
        }
        catch (Throwable e) {
            logger.error("\u8bfb\u5199\u6743\u9650\u73af\u7ed5\u901a\u77e5\u5f02\u5e38", e);
            return new DimensionAccessFormInfo();
        }
    }

    @Around(value="ignorePoint()")
    public boolean accessIgnore(ProceedingJoinPoint proceedingJoinPoint) {
        Object target = proceedingJoinPoint.getTarget();
        try {
            Set<String> ignoreItems = this.ignoreItemsRequest.get();
            Method nameMethod = proceedingJoinPoint.getSignature().getDeclaringType().getMethod("group", new Class[0]);
            if (!CollectionUtils.isEmpty(ignoreItems) && ignoreItems.contains("ALL")) {
                return false;
            }
            if (!CollectionUtils.isEmpty(ignoreItems) && ignoreItems.contains(nameMethod.invoke(target, new Object[0]))) {
                return false;
            }
            return (Boolean)proceedingJoinPoint.proceed();
        }
        catch (Throwable e) {
            logger.error("\u8bfb\u5199\u6743\u9650\u73af\u7ed5\u901a\u77e5\u5f02\u5e38", e);
            return false;
        }
    }

    @AfterReturning(value="formBatchAccessPoint()", returning="returnObj")
    public void afterFormBatchAccessReturn(DimensionAccessFormInfo returnObj) {
        if (logger.isDebugEnabled()) {
            logger.debug("\u8fdb\u53bb\u6279\u91cf\u83b7\u53d6\u62a5\u8868\u6743\u9650-\u6e05\u9664\u5207\u9762\u8bf7\u6c42\u53c2\u6570...");
        }
        this.ignoreItemsRequest.remove();
    }

    @AfterThrowing(value="formBatchAccessPoint()", throwing="exception")
    public void afterFormBatchAccessReturn(Exception exception) {
        if (logger.isDebugEnabled()) {
            logger.debug("\u8bfb\u5199\u6743\u9650\u5207\u9762\u5f02\u5e38-\u6e05\u9664\u8bf7\u6c42\u53c2\u6570\uff0c\u5f02\u5e38\u4fe1\u606f\uff1a" + exception.getMessage());
        }
        this.ignoreItemsRequest.remove();
    }
}

