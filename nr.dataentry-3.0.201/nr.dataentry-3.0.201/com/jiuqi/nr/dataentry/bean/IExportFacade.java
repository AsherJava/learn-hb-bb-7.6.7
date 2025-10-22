/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.CellQueryInfo
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IExportFacade {
    public UUID getSyncTaskID();

    public JtableContext getContext();

    public boolean isLabel();

    public boolean isSumData();

    public boolean isBackground();

    public boolean isOnlyStyle();

    public String getSheetName();

    public boolean isPrintCatalog();

    public List<String> getTabs();

    public String getPrintSchemeKey();

    public boolean isArithmeticBackground();

    public boolean isArithmeticFormula();

    public boolean isExportAllLable();

    default public Map<String, List<CellQueryInfo>> getConditions() {
        return null;
    }

    default public boolean isExportEmptyTable() {
        return false;
    }

    public List<RegionFilterListInfo> getRegionFilterListInfo();
}

