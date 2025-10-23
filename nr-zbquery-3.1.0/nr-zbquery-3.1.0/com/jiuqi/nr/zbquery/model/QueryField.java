/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAlias
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.zbquery.model.DisplayContent;
import com.jiuqi.nr.zbquery.model.MagnitudeType;
import com.jiuqi.nr.zbquery.model.MagnitudeValue;
import com.jiuqi.nr.zbquery.model.QueryObject;

public abstract class QueryField
extends QueryObject {
    public static final int COL_WIDTH_DEFAULT = -1;
    public static final int COL_WIDTH_AUTO = -2;
    private int dataType = 0;
    private DataFieldApplyType applyType = DataFieldApplyType.NONE;
    private int colWidth = -1;
    private String showFormat;
    @JsonAlias(value={"magnitudeValue"})
    private MagnitudeValue queryMagnitude = MagnitudeValue.NONE;
    private MagnitudeValue fieldMagnitude = MagnitudeValue.NONE;
    private MagnitudeType fieldMagnitudeType;
    private boolean displaySum;
    private String relatedDimension;
    private DisplayContent displayContent = DisplayContent.TITLE;

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public DataFieldApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(DataFieldApplyType applyType) {
        this.applyType = applyType;
    }

    public int getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public MagnitudeValue getQueryMagnitude() {
        return this.queryMagnitude;
    }

    public void setQueryMagnitude(MagnitudeValue queryMagnitude) {
        this.queryMagnitude = queryMagnitude;
    }

    public MagnitudeValue getFieldMagnitude() {
        return this.fieldMagnitude;
    }

    public MagnitudeType getFieldMagnitudeType() {
        return this.fieldMagnitudeType;
    }

    public void setFieldMagnitudeType(MagnitudeType fieldMagnitudeType) {
        this.fieldMagnitudeType = fieldMagnitudeType;
    }

    public void setFieldMagnitude(MagnitudeValue fieldMagnitude) {
        this.fieldMagnitude = fieldMagnitude;
    }

    public boolean isDisplaySum() {
        return this.displaySum;
    }

    public void setDisplaySum(boolean displaySum) {
        this.displaySum = displaySum;
    }

    public String getRelatedDimension() {
        return this.relatedDimension;
    }

    public void setRelatedDimension(String relatedDimension) {
        this.relatedDimension = relatedDimension;
    }

    public DisplayContent getDisplayContent() {
        return this.displayContent;
    }

    public void setDisplayContent(DisplayContent displayContent) {
        this.displayContent = displayContent;
    }
}

