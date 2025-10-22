/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 */
package com.jiuqi.nr.definition.service;

import com.jiuqi.nr.datascheme.api.DataDimension;
import java.util.List;

public interface ITaskService {
    public List<DataDimension> getDataDimension(String var1);

    public List<DataDimension> getReportDimension(String var1);

    public String getCurrentPeriod(String var1);
}

