/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.JtableData
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;
import java.util.List;

public interface IGetReportFormDataHook {
    public boolean enable(JtableContext var1);

    public JtableData execute(JtableData var1, JtableContext var2);

    public List<String> execute(JtableContext var1);
}

