/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrgAsyncTask {
    @Async(value="vaOrgDataAsyncTaskExecutor")
    public void execute(Runnable runnable) {
        runnable.run();
    }
}

