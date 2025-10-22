/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.interceptor.DefaultTransactionAttribute
 */
package com.jiuqi.np.definition.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Component
@Scope(value="prototype")
public class TransactionUtils {
    private TransactionStatus transactionStatus;
    @Autowired
    private PlatformTransactionManager dataSourceTransactionManager;

    public TransactionStatus init() {
        this.transactionStatus = this.dataSourceTransactionManager.getTransaction((TransactionDefinition)new DefaultTransactionAttribute());
        return this.transactionStatus;
    }

    public void commit(TransactionStatus transactionStatus) {
        this.dataSourceTransactionManager.commit(transactionStatus);
    }

    public void rollback() {
        this.dataSourceTransactionManager.rollback(this.transactionStatus);
    }
}

