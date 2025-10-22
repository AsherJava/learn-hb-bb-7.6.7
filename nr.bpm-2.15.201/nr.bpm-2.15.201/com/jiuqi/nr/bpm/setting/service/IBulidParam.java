/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.service;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import java.util.List;
import java.util.Map;

public interface IBulidParam {
    public int getType();

    default public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, boolean start) {
        return null;
    }

    default public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, WorkflowStatus flowType, boolean start) {
        return null;
    }

    default public List<BusinessKey> buildBusinessKey(StateChangeObj stateChange, boolean start) {
        return null;
    }
}

