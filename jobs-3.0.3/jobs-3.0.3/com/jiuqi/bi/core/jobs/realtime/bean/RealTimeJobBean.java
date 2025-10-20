/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.bean;

import java.io.Serializable;

public class RealTimeJobBean
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String group;
    private String groupTitle;
    private boolean cancellable;
    private boolean rollback;

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public boolean isCancellable() {
        return this.cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean isRollback() {
        return this.rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
}

