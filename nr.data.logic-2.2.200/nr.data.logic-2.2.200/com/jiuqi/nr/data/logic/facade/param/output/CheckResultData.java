/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckResultData
implements Serializable {
    private static final long serialVersionUID = 7388522162014953193L;
    private String recordId;
    private FormulaData formulaData;
    private String left;
    private String right;
    private String difference;
    private List<FormulaNode> formulaNodeList = new ArrayList<FormulaNode>();
    private int dimensionIndex;
    private Map<String, String> dimensionTitle = new HashMap<String, String>();
    private String unitKey;
    private String unitCode;
    private String unitTitle;
    private CheckDescription checkDescription = new CheckDescription();
    private List<Integer> ckdDescIndexList = Collections.emptyList();

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public FormulaData getFormulaData() {
        return this.formulaData;
    }

    public void setFormulaData(FormulaData formulaData) {
        this.formulaData = formulaData;
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

    public String getDifference() {
        return this.difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public List<FormulaNode> getFormulaNodeList() {
        return this.formulaNodeList;
    }

    public void setFormulaNodeList(List<FormulaNode> formulaNodeList) {
        this.formulaNodeList = formulaNodeList;
    }

    public int getDimensionIndex() {
        return this.dimensionIndex;
    }

    public void setDimensionIndex(int dimensionIndex) {
        this.dimensionIndex = dimensionIndex;
    }

    public Map<String, String> getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(Map<String, String> dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public CheckDescription getCheckDescription() {
        return this.checkDescription;
    }

    public void setCheckDescription(CheckDescription checkDescription) {
        this.checkDescription = checkDescription;
    }

    public List<Integer> getCkdDescIndexList() {
        return this.ckdDescIndexList;
    }

    public void setCkdDescIndexList(List<Integer> ckdDescIndexList) {
        this.ckdDescIndexList = ckdDescIndexList;
    }
}

