/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.upload.domain.CommonParamDTO;

public class DataLinkDTO
extends CommonParamDTO {
    private String formKey;
    private String regionKey;
    private int regionIndex;
    private int FloatingId;
    private int colNum;
    private int rowNum;
    private int posX;
    private int posY;
    private String fieldKey;
    private String fieldName;
    private String fieldTitle;
    private String expression;
    private String ownTableCode;
    private String ownTableTitle;
    private String ownTableKey;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public int getRegionIndex() {
        return this.regionIndex;
    }

    public void setRegionIndex(int regionIndex) {
        this.regionIndex = regionIndex;
    }

    public int getFloatingId() {
        return this.FloatingId;
    }

    public void setFloatingId(int floatingId) {
        this.FloatingId = floatingId;
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

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getOwnTableCode() {
        return this.ownTableCode;
    }

    public void setOwnTableCode(String ownTableCode) {
        this.ownTableCode = ownTableCode;
    }

    public String getOwnTableTitle() {
        return this.ownTableTitle;
    }

    public void setOwnTableTitle(String ownTableTitle) {
        this.ownTableTitle = ownTableTitle;
    }

    public String getOwnTableKey() {
        return this.ownTableKey;
    }

    public void setOwnTableKey(String ownTableKey) {
        this.ownTableKey = ownTableKey;
    }
}

