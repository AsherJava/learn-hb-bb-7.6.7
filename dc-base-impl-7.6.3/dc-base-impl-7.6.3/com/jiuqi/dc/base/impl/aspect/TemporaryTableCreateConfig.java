/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.dc.base.impl.aspect;

import com.jiuqi.dc.base.impl.annotation.TemporaryTableCreate;
import com.jiuqi.dc.base.impl.definition.DcTempTableDefinitionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
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
public class TemporaryTableCreateConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value="@annotation(com.jiuqi.dc.base.impl.annotation.TemporaryTableCreate)")
    public void temporaryTableCreatePoint() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Around(value="temporaryTableCreatePoint()")
    public Object temporaryTableCreateAop(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        TemporaryTableCreate annotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(TemporaryTableCreate.class);
        String tableName = annotation.name();
        String[] tableNameSplit = tableName.split(";");
        DcTempTableDefinitionService tempTableDefinitionService = (DcTempTableDefinitionService)ApplicationContextRegister.getBean(DcTempTableDefinitionService.class);
        try {
            for (int index = 0; index < tableNameSplit.length; ++index) {
                tempTableDefinitionService.createTemporaryTable(tableNameSplit[index]);
            }
            proceed = joinPoint.proceed();
        }
        finally {
            for (int i = index; i > 0; --i) {
                try {
                    tempTableDefinitionService.dropTemporaryTable(tableNameSplit[i - 1]);
                    continue;
                }
                catch (Exception e) {
                    this.logger.error("\u5220\u9664\u4e34\u65f6\u8868" + tableNameSplit[i - 1] + "\u5931\u8d25", e);
                }
            }
        }
        return proceed;
    }
}

