/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.AfterThrowing
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.springframework.transaction.TransactionStatus
 */
package com.jiuqi.np.definition.observer;

import com.jiuqi.np.definition.observer.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

@Aspect
@Component
@ConditionalOnExpression(value="!'dt'.equals('${jiuqi.product.name:null}')")
public class ObserverAOP {
    @Autowired
    TransactionUtils transactionUtils;

    @AfterThrowing(value="execution(* com.jiuqi.np.definition.observer.Observer.excute(..))")
    public void afterThrowing() {
        this.transactionUtils.rollback();
    }

    @Around(value="execution(* com.jiuqi.np.definition.observer.Observer.excute(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TransactionStatus transactionStatus = this.transactionUtils.init();
        proceedingJoinPoint.proceed();
        this.transactionUtils.commit(transactionStatus);
    }
}

