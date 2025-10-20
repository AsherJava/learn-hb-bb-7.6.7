/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class QuerySqlTransactionUtils {
    private static final Logger logger = LoggerFactory.getLogger(QuerySqlTransactionUtils.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void executeWithTransaction(Runnable runnable) {
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            runnable.run();
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <T> T executeWithTransaction(Supplier<T> supplier) {
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            T t = supplier.get();
            return t;
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

