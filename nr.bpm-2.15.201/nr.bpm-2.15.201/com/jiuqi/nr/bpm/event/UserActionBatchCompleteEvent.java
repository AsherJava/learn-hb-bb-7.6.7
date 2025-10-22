/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.businesskey.BusinessKeySetInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.event.UserActionBatchEvent;
import java.util.Collection;

public interface UserActionBatchCompleteEvent
extends UserActionBatchEvent {
    public BusinessKeySetInfo getBusinessKeySet();

    public Exception getException();

    public Collection<MasterEntityInfo> getCompletedMasterEntities();
}

