/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import java.util.List;
import java.util.Set;

public class CommitFlowResult {
    private List<DWorkflowData> actions;
    private ActionState uploadState;
    private boolean writeable;
    private String unWriteMsg;
    private Set<String> unWriteFormKeys;

    public List<DWorkflowData> getActions() {
        return this.actions;
    }

    public void setActions(List<DWorkflowData> actions) {
        this.actions = actions;
    }

    public ActionState getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(ActionState uploadState) {
        this.uploadState = uploadState;
    }

    public boolean isWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    public Set<String> getUnWriteFormKeys() {
        return this.unWriteFormKeys;
    }

    public void setUnWriteFormKeys(Set<String> unWriteFormKeys) {
        this.unWriteFormKeys = unWriteFormKeys;
    }

    public String getUnWriteMsg() {
        return this.unWriteMsg;
    }

    public void setUnWriteMsg(String unWriteMsg) {
        this.unWriteMsg = unWriteMsg;
    }
}

