/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class VaWorkflowAsyncTask {
    @Async
    public void execute(Runnable runnable) {
        runnable.run();
    }
}

