/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.SpringTransactionRunnable;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SpringTransactionUtil {
    public static void afterCommit(final Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void afterCommittedCompletion(final Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCompletion(int status) {
                    if (status == 0) {
                        runnable.run();
                    }
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void afterCompletion(final SpringTransactionRunnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCompletion(int status) {
                    if (status == 0) {
                        runnable.run(Status.COMMITTED);
                    } else if (status == 1) {
                        runnable.run(Status.ROLLED_BACK);
                    } else {
                        runnable.run(Status.UNKNOWN);
                    }
                }
            });
        } else {
            runnable.run(Status.NOT_ACTIVE);
        }
    }

    public static enum Status {
        COMMITTED,
        ROLLED_BACK,
        UNKNOWN,
        NOT_ACTIVE;

    }
}

