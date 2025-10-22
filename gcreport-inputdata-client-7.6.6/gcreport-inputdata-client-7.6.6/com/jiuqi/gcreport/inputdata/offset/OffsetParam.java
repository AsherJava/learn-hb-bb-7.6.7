/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.offset;

import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.io.Serializable;
import java.util.List;

public class OffsetParam
implements Serializable {
    private DataEntryContext dataEntryContext;
    private boolean allFormOffset;
    private List<String> formIds;

    public OffsetParam(DataEntryContext dataEntryContext, boolean allFormOffset, List<String> formIds) {
        this.dataEntryContext = dataEntryContext;
        this.allFormOffset = allFormOffset;
        this.formIds = formIds;
    }

    public DataEntryContext getDataEntryContext() {
        return this.dataEntryContext;
    }

    public void setDataEntryContext(DataEntryContext dataEntryContext) {
        this.dataEntryContext = dataEntryContext;
    }

    public boolean getAllFormOffset() {
        return this.allFormOffset;
    }

    public void setAllFormOffset(boolean allFormOffset) {
        this.allFormOffset = allFormOffset;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }
}

