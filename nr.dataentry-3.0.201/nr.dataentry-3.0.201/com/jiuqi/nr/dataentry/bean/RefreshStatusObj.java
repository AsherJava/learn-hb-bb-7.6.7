/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.List;

public class RefreshStatusObj
implements Serializable {
    private static final long serialVersionUID = -6163361370985805590L;
    private String task;
    private boolean defaultPeriod = true;
    private List<String> periods;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(boolean defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    public boolean isNotEmpty() {
        boolean taskKeyNotEmpty = StringUtils.isNotEmpty((String)this.task);
        return taskKeyNotEmpty && !this.periods.isEmpty();
    }
}

