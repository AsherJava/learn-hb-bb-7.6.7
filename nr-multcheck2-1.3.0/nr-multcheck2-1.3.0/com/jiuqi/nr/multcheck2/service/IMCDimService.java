/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IMCDimService {
    public List<DataDimension> getDynamicDimsNoCache(String var1);

    public List<String> getDynamicFields(String var1);

    public List<String> getDynamicFieldsByTask(String var1);

    public List<DataDimension> getDynamicDimsForPage(String var1);

    public List<String> getDynamicDimNamesForPage(String var1);

    public List<DataDimension> getOtherDimsForReport(String var1);

    public List<DataDimension> getReportDims(String var1);

    public List<IEntityRow> getEntityAllRow(String var1, String var2, String var3, String var4) throws Exception;

    public List<IEntityRow> filterEntityValue(String var1, String var2, String var3, String var4, String var5) throws Exception;
}

