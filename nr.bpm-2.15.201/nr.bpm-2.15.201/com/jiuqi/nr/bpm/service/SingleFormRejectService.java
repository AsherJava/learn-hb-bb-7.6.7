/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import java.util.Map;
import java.util.Set;

public interface SingleFormRejectService {
    public CompleteMsg execute(Set<String> var1, DimensionValueSet var2, String var3, String var4);

    public CompleteMsg execute(Set<String> var1, DimensionValueSet var2, String var3, String var4, TaskContext var5);

    public Set<String> getFormKeysByAction(DimensionValueSet var1, String var2, String var3);

    public boolean isRejectOrReturnForm(DimensionValueSet var1, String var2, String var3, String var4);

    public boolean isCanWrite(DimensionValueSet var1, String var2, String var3);

    public Map<String, Boolean> queryRejectFromDatas(DimensionValueSet var1, String var2);
}

