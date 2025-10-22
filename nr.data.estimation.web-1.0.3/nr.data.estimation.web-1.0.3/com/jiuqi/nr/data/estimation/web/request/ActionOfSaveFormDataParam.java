/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.RegionDataCommitSet
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import java.util.HashMap;
import java.util.Map;

public class ActionOfSaveFormDataParam {
    private JtableContext context;
    private Map<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, RegionDataCommitSet> getCommitData() {
        return this.commitData;
    }

    public void setCommitData(Map<String, RegionDataCommitSet> commitData) {
        this.commitData = commitData;
    }
}

