/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.nr.designer.web.facade.formuladesigner;

import com.jiuqi.np.definition.common.FieldType;

public class FormulaDesignLinksData {
    private int colNum;
    private int rowNum;
    private int x;
    private int y;
    private String linkExpression;
    private String fieldcode;
    private String fieldtitle;
    private FieldType datatype;
    private String tableCode;
    private String referField;
    private int linkType;

    public int getLinkType() {
        return this.linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCol(int col) {
        this.colNum = col;
    }

    public int getCol() {
        return this.colNum;
    }

    public void setRow(int row) {
        this.rowNum = row;
    }

    public int getRow() {
        return this.rowNum;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public String getFieldTitle() {
        return this.fieldtitle;
    }

    public void setFieldTitle(String fieldtitle) {
        this.fieldtitle = fieldtitle;
    }

    public String getFieldCode() {
        return this.fieldcode;
    }

    public void setFieldCode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public void setDataType(FieldType datatype) {
        this.datatype = datatype;
    }

    public FieldType getDataType() {
        return this.datatype;
    }
}

