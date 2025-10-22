/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import java.util.List;
import java.util.Map;

public class ArbitrarilyMergeOrgFilterSettingVO {
    private String id;
    private String dataType;
    private String dataName;
    private String dataAlias;
    private String controlType;
    private String sourceType;
    private String fieldTag;
    private Boolean enableFlag;
    private Double orderNum;
    private String dataType_;
    private String controlType_;
    private String sourceType_;
    private String baseDataDefine;
    private String orgType;
    private Map<String, List<String>> valueMap;
    private String toExecuteFormula;
    private String dataSourceId;
    private List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> formulaList;

    public String getDataSourceId() {
        return this.dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataAlias() {
        return this.dataAlias;
    }

    public void setDataAlias(String dataAlias) {
        this.dataAlias = dataAlias;
    }

    public String getControlType() {
        return this.controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFieldTag() {
        return this.fieldTag;
    }

    public void setFieldTag(String fieldTag) {
        this.fieldTag = fieldTag;
    }

    public Boolean getEnableFlag() {
        return this.enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Double getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Double orderNum) {
        this.orderNum = orderNum;
    }

    public String getDataType_() {
        return this.dataType_;
    }

    public void setDataType_(String dataType_) {
        this.dataType_ = dataType_;
    }

    public String getControlType_() {
        return this.controlType_;
    }

    public void setControlType_(String controlType_) {
        this.controlType_ = controlType_;
    }

    public String getSourceType_() {
        return this.sourceType_;
    }

    public void setSourceType_(String sourceType_) {
        this.sourceType_ = sourceType_;
    }

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public Map<String, List<String>> getValueMap() {
        return this.valueMap;
    }

    public void setValueMap(Map<String, List<String>> valueMap) {
        this.valueMap = valueMap;
    }

    public String getToExecuteFormula() {
        return this.toExecuteFormula;
    }

    public void setToExecuteFormula(String toExecuteFormula) {
        this.toExecuteFormula = toExecuteFormula;
    }

    public List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> getFormulaList() {
        return this.formulaList;
    }

    public void setFormulaList(List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> formulaList) {
        this.formulaList = formulaList;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

