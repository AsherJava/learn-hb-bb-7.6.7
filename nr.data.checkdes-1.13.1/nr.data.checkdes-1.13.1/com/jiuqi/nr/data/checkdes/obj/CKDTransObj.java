/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CKDTransObj
implements Serializable {
    private static final long serialVersionUID = 39210224665767680L;
    private String formulaSchemeTitle;
    private String formulaSchemeKey;
    private String formCode;
    private String formKey;
    private String formulaCode;
    private Map<String, String> dimMap = new HashMap<String, String>();
    private String formulaExpressionKey;
    private String globRow;
    private String globCol;
    private String dimStr;
    private String userId;
    private String userNickName;
    private long updateTime;
    private String description;
    private String formSchemeKey;
    private String formTitle;
    private String formulaKey;

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public Map<String, String> getDimMap() {
        return this.dimMap;
    }

    public void setDimMap(Map<String, String> dimMap) {
        this.dimMap = dimMap;
    }

    public String getFormulaExpressionKey() {
        return this.formulaExpressionKey;
    }

    public void setFormulaExpressionKey(String formulaExpressionKey) {
        this.formulaExpressionKey = formulaExpressionKey;
    }

    public String getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(String globRow) {
        this.globRow = globRow;
    }

    public String getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(String globCol) {
        this.globCol = globCol;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public DimensionValueSet getDimensionValueSet() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, String> entry : this.dimMap.entrySet()) {
            dimensionValueSet.setValue(entry.getKey(), (Object)entry.getValue());
        }
        return dimensionValueSet;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }
}

