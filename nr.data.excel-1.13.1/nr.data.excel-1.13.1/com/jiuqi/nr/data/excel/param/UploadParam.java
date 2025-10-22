/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.data.excel.param.CommonInitData;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadParam
implements Serializable {
    private static final long serialVersionUID = -7657830711955992809L;
    private String formSchemeKey;
    private DimensionCombination dimensionSet;
    private boolean isAppending = false;
    private String filePath;
    private ExcelRule excelRule;
    private Map<String, CommonInitData> regionReadOnlyDataLinks;
    private boolean splitSheets;

    public ExcelRule getExcelRule() {
        return this.excelRule;
    }

    public void setExcelRule(ExcelRule excelRule) {
        this.excelRule = excelRule;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(DimensionCombination dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public boolean isAppending() {
        return this.isAppending;
    }

    public void setAppending(boolean isAppending) {
        this.isAppending = isAppending;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, CommonInitData> getRegionReadOnlyDataLinks() {
        return this.regionReadOnlyDataLinks;
    }

    public void setRegionReadOnlyDataLinks(Map<String, CommonInitData> regionReadOnlyDataLinks) {
        this.regionReadOnlyDataLinks = regionReadOnlyDataLinks;
    }

    public boolean isSplitSheets() {
        return this.splitSheets;
    }

    public void setSplitSheets(boolean splitSheets) {
        this.splitSheets = splitSheets;
    }
}

