/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchEventImpl;
import java.util.Collections;
import java.util.List;

public class UserActionBatchCompleteEventImpl
extends UserActionBatchEventImpl
implements UserActionBatchCompleteEvent {
    private List<MasterEntityInfo> completedMasterEntities;
    private Exception exception;

    public List<MasterEntityInfo> getCompletedMasterEntities() {
        return Collections.unmodifiableList(this.completedMasterEntities);
    }

    public void setCompletedMasterEntities(List<MasterEntityInfo> completedMasterEntities) {
        this.completedMasterEntities = completedMasterEntities;
    }

    @Override
    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}

