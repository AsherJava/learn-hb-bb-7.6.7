/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import java.util.Date;

public interface ProcessInstance {
    public String getId();

    public String getName();

    public String getProcessDefinitionId();

    public BusinessKey getBusinessKey();

    public ProcessInstanceState getState();

    public Date getStartTime();

    public static enum ProcessInstanceState {
        ACTIVE,
        PENDING,
        COMPLETE;

    }
}

