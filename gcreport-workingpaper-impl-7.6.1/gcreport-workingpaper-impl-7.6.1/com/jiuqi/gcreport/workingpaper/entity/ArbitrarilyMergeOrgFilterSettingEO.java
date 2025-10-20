/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  javax.persistence.Transient
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterCustomFormulaSettingEO;
import java.util.List;
import java.util.Map;
import javax.persistence.Transient;

@DBTable(name="GC_ORGFILTER_SETTING", title="\u4efb\u610f\u5408\u5e76\u5355\u4f4d\u7b5b\u9009\u914d\u7f6e\u8868", inStorage=true)
public class ArbitrarilyMergeOrgFilterSettingEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ORGFILTER_SETTING";
    @DBColumn(nameInDB="DATATYPE", title="\u6570\u636e\u7c7b\u522b", dbType=DBColumn.DBType.Varchar)
    private String dataType;
    @DBColumn(nameInDB="DATANAME", title="\u6570\u636e\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    private String dataName;
    @DBColumn(nameInDB="DATAALIAS", title="\u6570\u636e\u522b\u540d", dbType=DBColumn.DBType.Varchar)
    private String dataAlias;
    @DBColumn(nameInDB="CONTROLTYPE", title="\u63a7\u4ef6\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String controlType;
    @DBColumn(nameInDB="SOURCETYPE", title="\u6570\u636e\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String sourceType;
    @DBColumn(nameInDB="FIELDTAG", title="\u67e5\u8be2\u5b57\u6bb5\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String fieldTag;
    @DBColumn(nameInDB="ENABLEFLAG", title="\u662f\u5426\u663e\u793a\u63a7\u4ef6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer enableFlag;
    @DBColumn(nameInDB="ORDERNUM", title="\u6392\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric)
    private Double orderNum;
    @Transient
    private String dataType_;
    @Transient
    private String controlType_;
    @Transient
    private String sourceType_;
    @Transient
    private String baseDataDefine;
    @Transient
    private Map<String, List<String>> valueMap;
    @Transient
    private String toExecuteFormula;
    @Transient
    private List<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO> formulaList;

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

    public Integer getEnableFlag() {
        return this.enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
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

    public List<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO> getFormulaList() {
        return this.formulaList;
    }

    public void setFormulaList(List<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO> formulaList) {
        this.formulaList = formulaList;
    }
}

