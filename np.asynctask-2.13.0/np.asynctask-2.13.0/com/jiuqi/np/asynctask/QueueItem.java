/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import java.time.Instant;

public interface QueueItem {
    public String getId();

    public String getTaskId();

    public Instant getJoinTime();

    public Integer getPriority();
}

