/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveObj;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class CheckErrorObj {
    private DimensionCombination masterKey;
    private DimensionCombination rowKey;
    private String recid;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaKey;
    private String formulaExpressionKey;
    private int formulaCheckType;
    private int globeRow;
    private int globeCol;
    private String left;
    private String right;
    private String balance;
    private List<FormulaNodeSaveObj> nodes;

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getFormulaCheckType() {
        return this.formulaCheckType;
    }

    public void setFormulaCheckType(int formulaCheckType) {
        this.formulaCheckType = formulaCheckType;
    }

    public String getFormulaExpressionKey() {
        return this.formulaExpressionKey;
    }

    public void setFormulaExpressionKey(String formulaExpressionKey) {
        this.formulaExpressionKey = formulaExpressionKey;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public int getGlobeCol() {
        return this.globeCol;
    }

    public void setGlobeCol(int globeCol) {
        this.globeCol = globeCol;
    }

    public int getGlobeRow() {
        return this.globeRow;
    }

    public void setGlobeRow(int globeRow) {
        this.globeRow = globeRow;
    }

    public String getLeft() {
        return this.left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public DimensionCombination getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCombination masterKey) {
        this.masterKey = masterKey;
    }

    public List<FormulaNodeSaveObj> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<FormulaNodeSaveObj> nodes) {
        this.nodes = nodes;
    }

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public DimensionCombination getRowKey() {
        return this.rowKey;
    }

    public void setRowKey(DimensionCombination rowKey) {
        this.rowKey = rowKey;
    }
}

