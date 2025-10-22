/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.util.Date;

public interface TaskComment {
    public String getId();

    public String getMessage();

    public String getUserId();

    public Date getTime();

    public String getTaskId();

    public String getProcessInstanceId();
}

