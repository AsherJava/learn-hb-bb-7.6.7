/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.dc.base.common.aspect;

import com.jiuqi.dc.base.common.annotation.CustDSTransaction;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import javax.sql.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Configuration
public class CustDSTransactionConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value="@annotation(com.jiuqi.dc.base.common.annotation.CustDSTransaction)")
    public void custTransactPoint() {
    }

    @Around(value="custTransactPoint()")
    public Object custTranAop(ProceedingJoinPoint joinPoint) throws Throwable {
        String dataSourceCode = this.getDataSourceCode(joinPoint);
        DataSource dataSource = ((DynamicDataSourceService)ApplicationContextRegister.getBean(DynamicDataSourceService.class)).getJdbcTemplate(dataSourceCode).getDataSource();
        DataSourceTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction((TransactionDefinition)transactionDefinition);
        try {
            Object proceed = joinPoint.proceed();
            platformTransactionManager.commit(transactionStatus);
            return proceed;
        }
        catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    private String getDataSourceCode(ProceedingJoinPoint joinPoint) {
        CustDSTransaction annotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(CustDSTransaction.class);
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if (String.class.equals((Object)annotation.type())) {
            for (int i = 0; i < argNames.length; ++i) {
                if (!annotation.name().equals(argNames[i])) continue;
                return String.valueOf(args[i]);
            }
        }
        for (Object arg : args) {
            if (!arg.getClass().equals(annotation.type())) continue;
            try {
                Field field = annotation.type().getDeclaredField(annotation.name());
                String name = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(name, arg.getClass());
                return (String)pd.getReadMethod().invoke(arg, new Object[0]);
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u6570\u636e\u6e90\u4ee3\u7801\u5931\u8d25", e);
            }
        }
        return null;
    }
}

