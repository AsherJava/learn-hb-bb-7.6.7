/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import java.util.List;
import java.util.Map;

public class FixedAdaptSettingExcelDTO {
    private String adaptFormula;
    private String wildcardFormula;
    private String logicFormula;
    private String description;
    private String sheetName;
    private Map<String, List<ExcelRowFetchSettingVO>> bizModelFormula;

    public FixedAdaptSettingExcelDTO() {
    }

    public FixedAdaptSettingExcelDTO(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getAdaptFormula() {
        return this.adaptFormula;
    }

    public void setAdaptFormula(String adaptFormula) {
        this.adaptFormula = adaptFormula;
    }

    public String getWildcardFormula() {
        return this.wildcardFormula;
    }

    public void setWildcardFormula(String wildcardFormula) {
        this.wildcardFormula = wildcardFormula;
    }

    public String getLogicFormula() {
        return this.logicFormula;
    }

    public void setLogicFormula(String logicFormula) {
        this.logicFormula = logicFormula;
    }

    public Map<String, List<ExcelRowFetchSettingVO>> getBizModelFormula() {
        return this.bizModelFormula;
    }

    public void setBizModelFormula(Map<String, List<ExcelRowFetchSettingVO>> bizModelFormula) {
        this.bizModelFormula = bizModelFormula;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}

