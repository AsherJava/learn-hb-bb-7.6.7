/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.TransactionCallback
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.common.base.datasource;

import com.jiuqi.common.base.datasource.OuterDataSourceContextHolder;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import javax.sql.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Aspect
public class OuterTransactionAspect {
    @Pointcut(value="@annotation(com.jiuqi.common.base.datasource.OuterTransaction)")
    public void outerDataSourcePointCut() {
    }

    @Around(value="outerDataSourcePointCut()")
    public Object switchDataSource(final ProceedingJoinPoint joinPoint) throws Throwable {
        String dataSourceCode = this.getDataSourceCodeFromAnnotation((JoinPoint)joinPoint);
        if (!StringUtils.isEmpty(dataSourceCode)) {
            OuterDataSourceContextHolder.setDataSourceCode(dataSourceCode);
        }
        DataSource dataSource = OuterDataSourceUtils.getDataSource();
        DataSourceTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate((PlatformTransactionManager)platformTransactionManager);
        OuterTransaction annotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(OuterTransaction.class);
        transactionTemplate.setPropagationBehavior(annotation.propagation().value());
        try {
            Object object = transactionTemplate.execute((TransactionCallback)new TransactionCallback<Object>(){

                public Object doInTransaction(TransactionStatus transactionStatus) {
                    try {
                        Object result = joinPoint.proceed();
                        return result;
                    }
                    catch (Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            throw (RuntimeException)throwable;
                        }
                        throw new RuntimeException(throwable);
                    }
                }
            });
            return object;
        }
        catch (RuntimeException ex) {
            if (ex.getCause() instanceof Exception) {
                throw (Exception)ex.getCause();
            }
            throw ex;
        }
        finally {
            OuterDataSourceContextHolder.clearDataSourceCode();
        }
    }

    private String getDataSourceCodeFromAnnotation(JoinPoint joinPoint) {
        OuterTransaction annotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(OuterTransaction.class);
        return annotation.dataSource();
    }
}

