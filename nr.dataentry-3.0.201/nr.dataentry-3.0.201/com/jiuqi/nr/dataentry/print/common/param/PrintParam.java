/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.print.common.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintParam
implements IPrintParamBase {
    private JtableContext context;
    private boolean printPageNum;
    private List<String> tabs;
    private Map<String, String> measureMap = new HashMap<String, String>();
    private boolean label;
    private String decimal;
    private boolean exportEmptyTable;
    private boolean emptyTable;
    private String secretLevelTitle;
    private boolean exportZero;
    private List<RegionFilterListInfo> regionFilterListInfo;

    public boolean isExportZero() {
        return this.exportZero;
    }

    public void setExportZero(boolean exportZero) {
        this.exportZero = exportZero;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public boolean isPrintPageNum() {
        return this.printPageNum;
    }

    public void setPrintPageNum(boolean printPageNum) {
        this.printPageNum = printPageNum;
    }

    public List<String> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    public boolean isLabel() {
        return this.label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

    public String getFormSchemeKey() {
        return this.context.getFormSchemeKey();
    }

    public String getFormKey() {
        return this.context.getFormKey();
    }

    public DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)this.context);
        return dimensionValueSet;
    }

    public String getFormulaSchemeKey() {
        return this.context.getFormulaSchemeKey();
    }

    public Map<String, String> getMeasureMap() {
        return this.context.getMeasureMap();
    }

    public void setMeasureMap(Map<String, String> measureMap) {
        this.measureMap = measureMap;
    }

    public String getDecimal() {
        return this.context.getDecimal();
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getTaskKey() {
        return this.context.getTaskKey();
    }

    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }

    public boolean isEmptyTable() {
        return this.emptyTable;
    }

    public void setEmptyTable(boolean emptyTable) {
        this.emptyTable = emptyTable;
    }

    public String getSecretLevelTitle() {
        return this.secretLevelTitle;
    }

    public void setSecretLevelTitle(String secretLevelTitle) {
        this.secretLevelTitle = secretLevelTitle;
    }

    public List<RegionFilterListInfo> getRegionFilterListInfo() {
        return this.regionFilterListInfo;
    }

    public void setRegionFilterListInfo(List<RegionFilterListInfo> regionFilterListInfo) {
        this.regionFilterListInfo = regionFilterListInfo;
    }
}

