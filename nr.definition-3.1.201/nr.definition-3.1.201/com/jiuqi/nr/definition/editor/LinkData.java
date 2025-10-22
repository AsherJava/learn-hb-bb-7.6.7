/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.nr.definition.editor;

import com.jiuqi.np.definition.common.FieldType;

public class LinkData {
    private int col;
    private int row;
    private int x;
    private int y;
    private String linkExpression;
    private String fieldcode;
    private String fieldtitle;
    private FieldType datatype;
    private String tableCode;
    private String referField;
    private int linkType;

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
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

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public String getFieldcode() {
        return this.fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getFieldtitle() {
        return this.fieldtitle;
    }

    public void setFieldtitle(String fieldtitle) {
        this.fieldtitle = fieldtitle;
    }

    public FieldType getDatatype() {
        return this.datatype;
    }

    public void setDatatype(FieldType datatype) {
        this.datatype = datatype;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public int getLinkType() {
        return this.linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }
}

