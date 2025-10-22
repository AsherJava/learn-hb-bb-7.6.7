/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.intf.IFormulaCheckEvent;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FormulaCheckEventImpl
implements IFormulaCheckEvent {
    private Formula formulaObj;
    private String formulaExpression;
    private String formulaMeaning;
    private UUID floatId;
    private List<NodeAdapter> nodes = new ArrayList<NodeAdapter>();
    private AbstractData leftValue;
    private AbstractData rightValue;
    private AbstractData differenceValue;
    private String compliedFormulaExpression;
    private UUID entity;
    private DimensionValueSet rowkey;
    private int wildcardCol;
    private int wildcardRow;
    private String key;
    private Exception checkException;

    @Override
    public DimensionValueSet getRowkey() {
        return this.rowkey;
    }

    public void setRowkey(DimensionValueSet rowkey) {
        this.rowkey = rowkey;
    }

    @Override
    public final AbstractData getLeftValue() {
        return this.leftValue;
    }

    public final void setLeftValue(AbstractData leftValue) {
        this.leftValue = leftValue;
    }

    @Override
    public final AbstractData getRightValue() {
        return this.rightValue;
    }

    public final void setRightValue(AbstractData rightValue) {
        this.rightValue = rightValue;
    }

    @Override
    public final Formula getFormulaObj() {
        return this.formulaObj;
    }

    public final void setFormulaObj(Formula formulaObj) {
        this.formulaObj = formulaObj;
    }

    @Override
    public final String getFormulaExpression() {
        return this.formulaExpression;
    }

    public final void setFormulaExpression(String formulaExpression) {
        this.formulaExpression = formulaExpression;
    }

    @Override
    public final String getFormulaMeaning() {
        return this.formulaMeaning;
    }

    public final void setFormulaMeaning(String formulaMeaning) {
        this.formulaMeaning = formulaMeaning;
    }

    @Override
    public final UUID getFloatId() {
        return this.floatId;
    }

    public final void setFloatId(UUID floatId) {
        this.floatId = floatId;
    }

    @Override
    public final AbstractData getDifferenceValue() {
        return this.differenceValue;
    }

    public final void setDifferenceValue(AbstractData differenceValue) {
        this.differenceValue = differenceValue;
    }

    @Override
    public String getCompliedFormulaExpression() {
        return this.compliedFormulaExpression;
    }

    public void setCompliedFormulaExpression(String compliedFormulaExpression) {
        this.compliedFormulaExpression = compliedFormulaExpression;
    }

    public void setEntityId(UUID entity) {
        this.entity = entity;
    }

    @Override
    public UUID getEntityId() {
        return this.entity;
    }

    @Override
    public List<NodeAdapter> getNodes() {
        return this.nodes;
    }

    @Override
    public int getWildcardCol() {
        return this.wildcardCol;
    }

    @Override
    public int getWildcardRow() {
        return this.wildcardRow;
    }

    public void setWildcardCol(int wildcardCol) {
        this.wildcardCol = wildcardCol;
    }

    public void setWildcardRow(int wildcardRow) {
        this.wildcardRow = wildcardRow;
    }

    public String toString() {
        return "FormulaCheckEventImpl [formulaObj=" + this.formulaObj + ", formulaExpression=" + this.formulaExpression + ", formulaMeaning=" + this.formulaMeaning + ", floatId=" + this.floatId + ", nodes=" + this.nodes + ", leftValue=" + this.leftValue + ", rightValue=" + this.rightValue + ", differenceValue=" + this.differenceValue + ", compliedFormulaExpression=" + this.compliedFormulaExpression + ", entity=" + this.entity + ", rowkey=" + this.rowkey + "]";
    }

    @Override
    public String getParsedExpresionKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Exception getCheckException() {
        return this.checkException;
    }

    public void setCheckException(Exception checkException) {
        this.checkException = checkException;
    }
}

