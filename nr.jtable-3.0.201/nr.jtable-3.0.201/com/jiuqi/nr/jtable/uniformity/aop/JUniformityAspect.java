/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.ExceptionResult
 *  com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.nr.jtable.uniformity.aop;

import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService;
import com.jiuqi.nr.jtable.exception.DataStatueCheckException;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="JUniformityAspect")
public class JUniformityAspect {
    private static final Logger logger = LoggerFactory.getLogger(JUniformityAspect.class);
    @Autowired
    private IDataStateCheckService dataStateCheckService;
    @Autowired
    private DesignTimePlanPublishService designTimePlanPublishService;

    @Pointcut(value="@annotation(com.jiuqi.nr.jtable.uniformity.annotation.JUniformity)")
    public void checkMethod() {
    }

    @Around(value="checkMethod()")
    public Object wrapMethod(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        JtableContext jtableContext = null;
        Object[] args = point.getArgs();
        if (null != args && args.length > 0) {
            for (Object object : args) {
                if (null == object) continue;
                if (object instanceof JtableContext) {
                    jtableContext = (JtableContext)object;
                    break;
                }
                if (!(object instanceof JUniformityService)) continue;
                jtableContext = ((JUniformityService)object).getContext();
                break;
            }
            if (null != jtableContext) {
                ExceptionResult exceptionResult = this.dataStateCheckService.checkDataState(jtableContext);
                if (null != exceptionResult) {
                    throw new DataStatueCheckException(exceptionResult.getErrorCode(), exceptionResult.getData());
                }
                boolean isProtectIng = false;
                try {
                    isProtectIng = this.designTimePlanPublishService.taskIsProtectIng(jtableContext.getTaskKey());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (isProtectIng) {
                    String[] errors = new String[]{"\u4efb\u52a1\u7ef4\u62a4\u4e2d\uff0c\u4e0d\u53ef\u5199"};
                    throw new SaveDataException(errors);
                }
            }
        }
        result = point.proceed();
        return result;
    }
}

