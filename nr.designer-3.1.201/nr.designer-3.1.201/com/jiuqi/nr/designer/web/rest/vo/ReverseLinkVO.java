/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.util.IReverseState;

public class ReverseLinkVO
implements IReverseState {
    private String regionKey;
    private String linkExpression;
    private int posX;
    private int posY;
    private int colNum;
    private int rowNum;
    private String captionFieldsString;
    private String dropDownFieldsString;
    private boolean allowMultipleSelect;
    private boolean allowNotLeafNodeRefer;
    private FormatProperties formatProperties;
    private ReverseItemState state;
    @JsonProperty(value="IsFormulaOrField")
    private Integer isFormulaOrField;

    @Override
    public ReverseItemState getState() {
        return this.state;
    }

    @Override
    public void setState(ReverseItemState state) {
        this.state = state;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
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

    public String getCaptionFieldsString() {
        return this.captionFieldsString;
    }

    public void setCaptionFieldsString(String captionFieldsString) {
        this.captionFieldsString = captionFieldsString;
    }

    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    public void setDropDownFieldsString(String dropDownFieldsString) {
        this.dropDownFieldsString = dropDownFieldsString;
    }

    public boolean isAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public Integer getIsFormulaOrField() {
        return this.isFormulaOrField;
    }

    public void setIsFormulaOrField(Integer isFormulaOrField) {
        this.isFormulaOrField = isFormulaOrField;
    }
}

