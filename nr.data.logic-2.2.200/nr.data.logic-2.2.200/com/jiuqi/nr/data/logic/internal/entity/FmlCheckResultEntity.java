/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.logic.internal.entity;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FmlCheckResultEntity {
    private DimensionValueSet masterKey;
    private String actionID;
    private final Map<String, String> dimMap = new HashMap<String, String>();
    private String recId;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaExpressionKey;
    private int globRow;
    private int globCol;
    private String formulaKey;
    private int formulaCheckType;
    private String dimStr;
    private String errorDesc;
    private String left;
    private String right;
    private String balance;
    private String formOrder;
    private BigDecimal unitOrder;
    private String formulaOrder;

    public String getActionID() {
        return this.actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public Map<String, String> getDimMap() {
        return this.dimMap;
    }

    public String getRecId() {
        return this.recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
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

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public int getFormulaCheckType() {
        return this.formulaCheckType;
    }

    public void setFormulaCheckType(int formulaCheckType) {
        this.formulaCheckType = formulaCheckType;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
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

    public BigDecimal getUnitOrder() {
        return this.unitOrder;
    }

    public void setUnitOrder(BigDecimal unitOrder) {
        this.unitOrder = unitOrder;
    }

    public String getFormulaOrder() {
        return this.formulaOrder;
    }

    public void setFormulaOrder(String formulaOrder) {
        this.formulaOrder = formulaOrder;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }
}

