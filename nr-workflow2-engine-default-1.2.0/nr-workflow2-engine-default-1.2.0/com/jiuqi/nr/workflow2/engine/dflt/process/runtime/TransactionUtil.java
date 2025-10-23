/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Lazy(value=false)
public class TransactionUtil {
    @Transactional
    public void runWithinTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void runWithinNewTransaction(Runnable runnable) {
        runnable.run();
    }
}

