/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.fmdm.internal.check;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.fmdm.common.FMDMModifyTypeEnum;
import java.util.Map;

public class CheckParam {
    private String formulaSchemeKey;
    private String formKey;
    private Map<String, Object> data;
    private DimensionValueSet masterKeys;
    private FMDMModifyTypeEnum modifyType;

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

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }

    public FMDMModifyTypeEnum getModifyType() {
        return this.modifyType;
    }

    public void setModifyType(FMDMModifyTypeEnum modifyType) {
        this.modifyType = modifyType;
    }
}

