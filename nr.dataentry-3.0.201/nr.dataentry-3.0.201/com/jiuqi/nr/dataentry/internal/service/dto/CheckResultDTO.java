/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.jtable.util.CheckResultUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.internal.service.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.jtable.util.CheckResultUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.Map;

@Deprecated
public class CheckResultDTO {
    private DimensionValueSet dimensionValueSet;
    private String version;
    private String recid;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaCode;
    private int globRow;
    private int globCol;
    private String formula;
    private String formulaDescription;
    private String dimStr;
    private String errorDescription;
    private String left;
    private String right;
    private String balance;
    private String formOrder;
    private double unitOrder;
    private String formulaOrder;
    private int formulaCheckType;
    private String formulaId;

    public CheckResultDTO() {
    }

    public CheckResultDTO(CheckResultDTO checkResultDTO, DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
        this.version = checkResultDTO.getVersion();
        this.formSchemeKey = checkResultDTO.getFormSchemeKey();
        this.formulaSchemeKey = checkResultDTO.getFormulaSchemeKey();
        this.formKey = checkResultDTO.getFormKey();
        this.formulaCode = checkResultDTO.getFormulaCode();
        this.globRow = checkResultDTO.getGlobRow();
        this.globCol = checkResultDTO.getGlobCol();
        this.formula = checkResultDTO.getFormula();
        this.formulaDescription = checkResultDTO.getFormulaDescription();
        this.dimStr = checkResultDTO.getDimStr();
        this.errorDescription = checkResultDTO.getErrorDescription();
        this.left = checkResultDTO.getLeft();
        this.right = checkResultDTO.getRight();
        this.balance = checkResultDTO.getBalance();
        this.formOrder = checkResultDTO.getFormOrder();
        this.unitOrder = checkResultDTO.getUnitOrder();
        this.formulaOrder = checkResultDTO.getFormulaOrder();
        this.formulaCheckType = checkResultDTO.getFormulaCheckType();
        this.formulaId = checkResultDTO.getFormulaId();
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        this.recid = CheckResultUtil.buildDesKey((String)this.formSchemeKey, (String)this.formulaSchemeKey, (String)this.formKey, (String)this.formulaCode, (int)this.globRow, (int)this.globCol, (Map)dimensionSet);
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRecid() {
        if (this.recid == null) {
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)this.dimensionValueSet);
            this.recid = CheckResultUtil.buildDesKey((String)this.formSchemeKey, (String)this.formulaSchemeKey, (String)this.formKey, (String)this.formulaCode, (int)this.globRow, (int)this.globCol, (Map)dimensionSet);
        }
        return this.recid;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
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

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaDescription() {
        return this.formulaDescription;
    }

    public void setFormulaDescription(String formulaDescription) {
        this.formulaDescription = formulaDescription;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getLeft() {
        return this.left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFormOrder() {
        return this.formOrder;
    }

    public void setFormOrder(String formOrder) {
        this.formOrder = formOrder;
    }

    public double getUnitOrder() {
        return this.unitOrder;
    }

    public void setUnitOrder(double unitOrder) {
        this.unitOrder = unitOrder;
    }

    public String getFormulaOrder() {
        return this.formulaOrder;
    }

    public void setFormulaOrder(String formulaOrder) {
        this.formulaOrder = formulaOrder;
    }

    public int getFormulaCheckType() {
        return this.formulaCheckType;
    }

    public void setFormulaCheckType(int formulaCheckType) {
        this.formulaCheckType = formulaCheckType;
    }

    public String getFormulaId() {
        return this.formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }
}

