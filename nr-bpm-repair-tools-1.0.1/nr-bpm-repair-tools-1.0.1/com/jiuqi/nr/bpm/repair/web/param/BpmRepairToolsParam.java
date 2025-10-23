/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.repair.web.param;

import com.jiuqi.util.StringUtils;
import java.io.Serializable;

public class BpmRepairToolsParam
implements Serializable {
    private String taskId;
    private String period;
    private String scheme;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public boolean isValidEnv() {
        return StringUtils.isNotEmpty((String)this.taskId) && StringUtils.isNotEmpty((String)this.period) && StringUtils.isNotEmpty((String)this.scheme);
    }
}

