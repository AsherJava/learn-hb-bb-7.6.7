/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;

public class BatchCopyInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String sourcePeriod;
    private Map<String, DimensionValue> sourcePeriodDim;
    private boolean dbTask = true;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSourcePeriod() {
        return this.sourcePeriod;
    }

    public void setSourcePeriod(String sourcePeriod) {
        this.sourcePeriod = sourcePeriod;
    }

    public boolean isDbTask() {
        return this.dbTask;
    }

    public void setDbTask(boolean dbTask) {
        this.dbTask = dbTask;
    }

    public String getFormKeys() {
        return this.context.getFormKey();
    }

    public Map<String, DimensionValue> getSourcePeriodDim() {
        return this.sourcePeriodDim;
    }

    public void setSourcePeriodDim(Map<String, DimensionValue> sourcePeriodDim) {
        this.sourcePeriodDim = sourcePeriodDim;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

