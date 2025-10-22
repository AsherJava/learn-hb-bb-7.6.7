/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class CheckDesObj {
    private String recordId;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaExpressionKey;
    private String formulaCode;
    private int globRow;
    private int globCol;
    private String floatId;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private CheckDescription checkDescription = new CheckDescription();

    public String getRecordId() {
        if (this.recordId == null) {
            this.recordId = CheckResultUtil.buildRECID(this.formulaSchemeKey, this.formKey, this.formulaExpressionKey.substring(0, 36), this.globRow, this.globCol, this.dimensionSet);
        }
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulaExpressionKey() {
        return this.formulaExpressionKey;
    }

    public void setFormulaExpressionKey(String formulaExpressionKey) {
        this.formulaExpressionKey = formulaExpressionKey;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public String getFloatId() {
        return this.floatId;
    }

    public void setFloatId(String floatId) {
        this.floatId = floatId;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public CheckDescription getCheckDescription() {
        return this.checkDescription;
    }

    public void setCheckDescription(CheckDescription checkDescription) {
        this.checkDescription = checkDescription;
    }

    public DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        HashSet<String> bnwdDimNames = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)this.floatId)) {
            String[] dims;
            for (String dim : dims = this.floatId.split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                bnwdDimNames.add(dimValues[0]);
            }
        }
        for (Map.Entry<String, DimensionValue> entry : this.dimensionSet.entrySet()) {
            if (bnwdDimNames.contains(entry.getKey())) continue;
            dimensionValueSet.setValue(entry.getKey(), (Object)entry.getValue().getValue());
        }
        return dimensionValueSet;
    }

    public DimensionValueSet getDimensionValueSet(List<String> dimNames) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (!CollectionUtils.isEmpty(dimNames)) {
            dimNames.forEach(o -> {
                if (this.dimensionSet.containsKey(o)) {
                    dimensionValueSet.setValue(o, (Object)this.dimensionSet.get(o).getValue());
                }
            });
        }
        return dimensionValueSet;
    }
}

