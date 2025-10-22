/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.anal;

public class AnalCellFormulaItem {
    private int posX;
    private int posY;
    private int colNum;
    private int rowNum;
    private String fieldCode;
    private String formula;
    private String oldFormula;
    private String title;
    private String netSourceFlag;
    private String netFlag;

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getOldFormula() {
        return this.oldFormula;
    }

    public void setOldFormula(String oldFormula) {
        this.oldFormula = oldFormula;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNetSourceFlag() {
        return this.netSourceFlag;
    }

    public void setNetSourceFlag(String netSourceFlag) {
        this.netSourceFlag = netSourceFlag;
    }

    public String getNetFlag() {
        return this.netFlag;
    }

    public void setNetFlag(String netFlag) {
        this.netFlag = netFlag;
    }
}

