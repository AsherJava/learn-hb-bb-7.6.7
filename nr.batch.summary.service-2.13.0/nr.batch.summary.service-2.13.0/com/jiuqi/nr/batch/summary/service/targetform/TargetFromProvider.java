/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.List;

public interface TargetFromProvider {
    public List<FormDefine> getRangeForms(String var1);

    public List<OriTableModelInfo> getRangeFormTables(List<FormDefine> var1, String var2);

    public SumTableModelInfo getSumTableModel(OriTableModelInfo var1, String var2);
}

