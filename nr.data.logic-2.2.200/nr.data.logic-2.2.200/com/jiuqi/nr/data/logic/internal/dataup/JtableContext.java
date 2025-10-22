/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.summary.SummaryScheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="JtableContext", description="jtable\u73af\u5883\u4fe1\u606f")
public class JtableContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868Key", name="formKey", required=true)
    private String formKey;
    @ApiModelProperty(value="\u62a5\u8868\u5206\u7ec4Key", name="formGroupKey", required=true)
    private String formGroupKey;
    @ApiModelProperty(value="\u516c\u5f0f\u65b9\u6848Key", name="formulaSchemeKey", required=true)
    private String formulaSchemeKey;
    @ApiModelProperty(value="\u5f53\u524d\u7ef4\u5ea6", name="dimensionSet", required=true)
    private Map<String, DimensionValue> dimensionSet;
    @ApiModelProperty(value="\u81ea\u5b9a\u4e49\u73af\u5883\u53d8\u91cf", name="variableMap", required=false, example="DATA_STATE_CHECK_EDIT: \"123\"")
    private Map<String, Object> variableMap = new HashMap<String, Object>();
    @ApiModelProperty(value="\u5f53\u524d\u91cf\u7eb2(\u6ca1\u6539\u53d8\u91cf\u7eb2\u4e0d\u7528\u4f20)", name="measureMap", required=false, example="419c0472-2ca8-4c00-b284-79075de90dd7: \"WANYUAN\"")
    private Map<String, String> measureMap = new HashMap<String, String>();
    @ApiModelProperty(value="\u5355\u4f4d\u6811\u81ea\u5b9a\u4e49\u6c47\u603b\u65b9\u6848", name="sumScheme", required=false)
    private SummaryScheme sumScheme;
    @ApiModelProperty(value="\u5bfc\u51fa\u6570\u636e\u5c0f\u6570\u4f4d\u6570", name="decimal", required=false)
    private String decimal;
    @ApiModelProperty(value="\u5355\u4f4d\u4e3b\u4f53\u7684\u89c6\u56fekey", name="unitViewKey", required=false)
    private String unitViewKey;

    public JtableContext() {
    }

    public JtableContext(JtableContext context) {
        if (context == null) {
            return;
        }
        this.taskKey = context.taskKey;
        this.formSchemeKey = context.formSchemeKey;
        this.formKey = context.formKey;
        this.formGroupKey = context.formGroupKey;
        this.formulaSchemeKey = context.formulaSchemeKey;
        this.dimensionSet = new HashMap<String, DimensionValue>();
        if (context.dimensionSet != null) {
            for (String dimensionName : context.dimensionSet.keySet()) {
                DimensionValue dimensionValue = context.dimensionSet.get(dimensionName);
                this.dimensionSet.put(dimensionName, new DimensionValue(dimensionValue));
            }
        }
        this.variableMap = context.variableMap;
        this.sumScheme = context.sumScheme;
        this.measureMap = context.measureMap;
        this.decimal = context.decimal;
        this.unitViewKey = context.unitViewKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public Map<String, String> getMeasureMap() {
        return this.measureMap;
    }

    public void setMeasureMap(Map<String, String> measureMap) {
        this.measureMap = measureMap;
    }

    public SummaryScheme getSumScheme() {
        return this.sumScheme;
    }

    public void setSumScheme(SummaryScheme sumScheme) {
        this.sumScheme = sumScheme;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getUnitViewKey() {
        return this.unitViewKey;
    }

    public void setUnitViewKey(String unitViewKey) {
        this.unitViewKey = unitViewKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dimensionSet == null ? 0 : this.dimensionSet.hashCode());
        result = 31 * result + (this.formKey == null ? 0 : this.formKey.hashCode());
        result = 31 * result + (this.formGroupKey == null ? 0 : this.formGroupKey.hashCode());
        result = 31 * result + (this.formSchemeKey == null ? 0 : this.formSchemeKey.hashCode());
        result = 31 * result + (this.formulaSchemeKey == null ? 0 : this.formulaSchemeKey.hashCode());
        result = 31 * result + (this.taskKey == null ? 0 : this.taskKey.hashCode());
        result = 31 * result + (this.variableMap == null ? 0 : this.variableMap.hashCode());
        result = 31 * result + (this.measureMap == null ? 0 : this.measureMap.hashCode());
        result = 31 * result + (this.unitViewKey == null ? 0 : this.unitViewKey.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        JtableContext other = (JtableContext)obj;
        if (this.dimensionSet == null ? other.dimensionSet != null : !this.dimensionSet.equals(other.dimensionSet)) {
            return false;
        }
        if (this.formKey == null ? other.formKey != null : !this.formKey.equals(other.formKey)) {
            return false;
        }
        if (this.formGroupKey == null ? other.formGroupKey != null : !this.formGroupKey.equals(other.formGroupKey)) {
            return false;
        }
        if (this.formSchemeKey == null ? other.formSchemeKey != null : !this.formSchemeKey.equals(other.formSchemeKey)) {
            return false;
        }
        if (this.formulaSchemeKey == null ? other.formulaSchemeKey != null : !this.formulaSchemeKey.equals(other.formulaSchemeKey)) {
            return false;
        }
        if (this.taskKey == null ? other.taskKey != null : !this.taskKey.equals(other.taskKey)) {
            return false;
        }
        if (this.variableMap == null ? other.variableMap != null : !this.variableMap.equals(other.variableMap)) {
            return false;
        }
        if (this.measureMap == null ? other.measureMap != null : !this.measureMap.equals(other.measureMap)) {
            return false;
        }
        return !(this.unitViewKey == null ? other.unitViewKey != null : !this.unitViewKey.equals(other.unitViewKey));
    }
}

