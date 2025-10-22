/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.definition.facade.print.common.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;

public interface IPrintParamBase {
    public String getTaskKey();

    public String getFormSchemeKey();

    public String getFormKey();

    public DimensionValueSet getDimensionValueSet();

    public String getFormulaSchemeKey();

    public boolean isPrintPageNum();

    public List<String> getTabs();

    public boolean isLabel();

    public void setLabel(boolean var1);

    public Map<String, String> getMeasureMap();

    public String getDecimal();

    public boolean isExportEmptyTable();

    public void setEmptyTable(boolean var1);

    public boolean isEmptyTable();

    public String getSecretLevelTitle();
}

