/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.List;

public class DataentryEntityParam
extends NRContext {
    private List<String> taskId;

    public List<String> getTaskId() {
        return this.taskId;
    }

    public void setTaskId(List<String> taskId) {
        this.taskId = taskId;
    }
}

