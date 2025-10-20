/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;

public class SubjectBaseDataParam
extends BaseDataParam {
    private String systemId;
    private boolean filterStopNode;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public boolean isFilterStopNode() {
        return this.filterStopNode;
    }

    public void setFilterStopNode(boolean filterStopNode) {
        this.filterStopNode = filterStopNode;
    }
}

