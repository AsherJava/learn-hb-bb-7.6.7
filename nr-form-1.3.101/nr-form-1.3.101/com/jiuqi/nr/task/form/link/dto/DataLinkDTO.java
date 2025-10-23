/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.dto;

import com.jiuqi.nr.task.form.dto.DataCore;

public class DataLinkDTO
extends DataCore {
    private String regionKey;
    private String linkExpression;
    private Integer type = 0;
    private Integer posX = 0;
    private Integer posY = 0;
    private Integer colNum = 0;
    private Integer rowNum = 0;
    private String measureUnit;

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

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPosX() {
        return this.posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return this.posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getColNum() {
        return this.colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public Integer getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

