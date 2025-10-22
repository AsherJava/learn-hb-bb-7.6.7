/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.Map;

public class IAttachmentContext
extends NRContext {
    private String task;
    private String formscheme;
    private Map<String, DimensionValue> dimensionSet;
    private String groupKey;
    private String from;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

