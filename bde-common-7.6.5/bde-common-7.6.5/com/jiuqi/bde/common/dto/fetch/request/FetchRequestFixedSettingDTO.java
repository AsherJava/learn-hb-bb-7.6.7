/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto.fetch.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchRequestFixedSettingDTO {
    private String fieldDefineId;
    private String fieldDefineTitle;
    private Integer fieldDefineType;
    private Integer fieldDefineSize;
    private Integer fieldDefineFractionDigits;
    private String logicFormula;
    private Map<String, List<FixedFetchSourceRowSettingVO>> bizModelFormula;

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public String getFieldDefineTitle() {
        return this.fieldDefineTitle;
    }

    public void setFieldDefineTitle(String fieldDefineTitle) {
        this.fieldDefineTitle = fieldDefineTitle;
    }

    public Integer getFieldDefineType() {
        return this.fieldDefineType;
    }

    public void setFieldDefineType(Integer fieldDefineType) {
        this.fieldDefineType = fieldDefineType;
    }

    public Integer getFieldDefineSize() {
        return this.fieldDefineSize;
    }

    public void setFieldDefineSize(Integer fieldDefineSize) {
        this.fieldDefineSize = fieldDefineSize;
    }

    public Integer getFieldDefineFractionDigits() {
        return this.fieldDefineFractionDigits;
    }

    public void setFieldDefineFractionDigits(Integer fieldDefineFractionDigits) {
        this.fieldDefineFractionDigits = fieldDefineFractionDigits;
    }

    public String getLogicFormula() {
        return this.logicFormula;
    }

    public void setLogicFormula(String logicFormula) {
        this.logicFormula = logicFormula;
    }

    public Map<String, List<FixedFetchSourceRowSettingVO>> getBizModelFormula() {
        return this.bizModelFormula;
    }

    public void setBizModelFormula(Map<String, List<FixedFetchSourceRowSettingVO>> bizModelFormula) {
        this.bizModelFormula = bizModelFormula;
    }

    public String toString() {
        return "FetchRequestFixedSettingDTO [fieldDefineId=" + this.fieldDefineId + ", fieldDefineTitle=" + this.fieldDefineTitle + ", fieldDefineType=" + this.fieldDefineType + ", fieldDefineSize=" + this.fieldDefineSize + ", fieldDefineFractionDigits=" + this.fieldDefineFractionDigits + ", logicFormula=" + this.logicFormula + ", bizModelFormula=" + this.bizModelFormula + "]";
    }
}

