/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.efdc.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.pojo.EfdcReturnInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;

public interface IEFDCService {
    public Map<String, EfdcReturnInfo> efdcService(EfdcInfo var1, AsyncTaskMonitor var2, Map<String, String> var3);

    public FormulaSchemeDefine getEfdcFormula(JtableContext var1);
}

