/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

import com.jiuqi.nr.single.core.task.model.SingleEntityDataItem;
import java.util.ArrayList;
import java.util.List;

public class SinglePeriodDataItem {
    private String period;
    private List<SingleEntityDataItem> entitys;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<SingleEntityDataItem> getEntitys() {
        if (this.entitys == null) {
            this.entitys = new ArrayList<SingleEntityDataItem>();
        }
        return this.entitys;
    }

    public void setEntitys(List<SingleEntityDataItem> entitys) {
        this.entitys = entitys;
    }
}

