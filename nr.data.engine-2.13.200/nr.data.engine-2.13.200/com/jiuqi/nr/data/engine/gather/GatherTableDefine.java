/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import java.util.ArrayList;
import java.util.List;

public class GatherTableDefine {
    private final DataTable tableDefine;
    private List<DataField> gatherFields;
    private final boolean isFixed;
    private String filterCondition;
    private String bingingExpression;
    private String regionKey;
    private String formId;
    private List<DataField> classifyFields;
    private DataField unitField;
    private DataField periodField;
    private DataField orderField;
    private DataField bizOrderField;
    private List<DataField> unClassifyFields;
    private FloatTableGatherSetting floatTableGatherSetting;

    public GatherTableDefine(DataTable tableDefine, boolean isFixed) {
        this.tableDefine = tableDefine;
        this.isFixed = isFixed;
        this.gatherFields = new ArrayList<DataField>();
    }

    public DataTable getTableDefine() {
        return this.tableDefine;
    }

    public List<DataField> getGatherFields() {
        return this.gatherFields;
    }

    public void setGatherFields(List<DataField> gatherFields) {
        this.gatherFields = gatherFields;
    }

    public boolean isFixed() {
        return this.isFixed;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getBingingExpression() {
        return this.bingingExpression;
    }

    public void setBingingExpression(String bingingExpression) {
        this.bingingExpression = bingingExpression;
    }

    List<DataField> getClassifyFields() {
        return this.classifyFields;
    }

    void setClassifyFields(List<DataField> classifyFields) {
        this.classifyFields = classifyFields;
    }

    DataField getUnitField() {
        return this.unitField;
    }

    void setUnitField(DataField unitField) {
        this.unitField = unitField;
    }

    DataField getPeriodField() {
        return this.periodField;
    }

    void setPeriodField(DataField periodField) {
        this.periodField = periodField;
    }

    DataField getOrderField() {
        return this.orderField;
    }

    void setOrderField(DataField orderField) {
        this.orderField = orderField;
    }

    void setBizOrderField(DataField bizOrderField) {
        this.bizOrderField = bizOrderField;
    }

    DataField getBizOrderField() {
        return this.bizOrderField;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<DataField> getUnClassifyFields() {
        return this.unClassifyFields;
    }

    void setUnClassifyFields(List<DataField> unClassifyFields) {
        this.unClassifyFields = unClassifyFields;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public FloatTableGatherSetting getFloatTableGatherSetting() {
        return this.floatTableGatherSetting;
    }

    public void setFloatTableGatherSetting(FloatTableGatherSetting singleFloatTableGatherSetting) {
        this.floatTableGatherSetting = singleFloatTableGatherSetting;
    }
}

