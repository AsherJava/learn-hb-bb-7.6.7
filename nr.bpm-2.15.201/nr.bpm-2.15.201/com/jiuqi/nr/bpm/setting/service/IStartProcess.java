/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.service;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import java.util.Map;

public interface IStartProcess {
    public boolean startProcess(String var1, Map<BusinessKey, String> var2, WorkflowStatus var3);
}

