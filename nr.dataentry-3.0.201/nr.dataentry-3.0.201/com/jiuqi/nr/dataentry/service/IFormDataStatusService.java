/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public interface IFormDataStatusService {
    public List<String> getFilledPeriod(JtableContext var1);

    public List<String> getFilledFormkey(JtableContext var1);

    public Map<String, String> getFilledPeriodBySql(List<String> var1, List<String> var2);
}

