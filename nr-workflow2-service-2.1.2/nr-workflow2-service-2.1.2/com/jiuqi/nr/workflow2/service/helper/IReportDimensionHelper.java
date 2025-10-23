/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;

public interface IReportDimensionHelper {
    public String getUnitDimensionName(String var1);

    public String getDimensionName(DataDimension var1);

    public DataDimension getReportUnitDimension(String var1);

    public DataDimension getReportPeriodDimension(String var1);

    public DataDimension getCurrencyDataDimension(String var1);

    public DataDimension findDataDimensionByName(String var1, String var2);

    public List<DataDimension> getReportDimensionsExceptUnitAndPeriod(String var1);

    public List<DataDimension> getAllReportDimensions(String var1);

    public List<DataDimension> getAllDataDimension(String var1);

    public boolean isCorporateDimension(TaskDefine var1, DataDimension var2);

    public boolean isMdCurrencyDimension(TaskDefine var1, DataDimension var2);

    public boolean isMdCurrencyReferEntity(String var1, String var2);
}

