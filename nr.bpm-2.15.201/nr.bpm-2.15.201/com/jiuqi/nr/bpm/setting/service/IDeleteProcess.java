/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.service;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import java.util.Map;

public interface IDeleteProcess {
    public boolean deleteProcess(String var1, Map<BusinessKey, String> var2, WorkflowStatus var3, boolean var4, boolean var5, boolean var6, String var7);
}

