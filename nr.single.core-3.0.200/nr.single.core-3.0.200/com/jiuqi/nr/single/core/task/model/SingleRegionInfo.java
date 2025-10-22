/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

import com.jiuqi.nr.single.core.task.model.SingleFieldInfo;
import java.util.ArrayList;
import java.util.List;

public class SingleRegionInfo {
    private int floatingId;
    private List<SingleFieldInfo> fields;

    public int getFloatingId() {
        return this.floatingId;
    }

    public void setFloatingId(int floatingId) {
        this.floatingId = floatingId;
    }

    public List<SingleFieldInfo> getFields() {
        if (this.fields == null) {
            this.fields = new ArrayList<SingleFieldInfo>();
        }
        return this.fields;
    }

    public void setFields(List<SingleFieldInfo> fields) {
        this.fields = fields;
    }
}

