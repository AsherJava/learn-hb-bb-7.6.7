/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BaseDataAsyncTask {
    @Async(value="vaBaseDataAsyncTaskExecutor")
    public void execute(Runnable runnable) {
        runnable.run();
    }
}

