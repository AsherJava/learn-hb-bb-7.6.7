/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;

class DataLinkDO {
    private String key;
    private String uniqueCode;
    private String regionKey;
    private String linkExpression;
    private int posX;
    private int posY;
    private int colNum;
    private int rowNum;
    private String formKey;
    private int type;

    DataLinkDO() {
    }

    public static DataLinkDefine toDataLinkDefine(DataLinkDO dataLinkDO) {
        RunTimeDataLinkDefineImpl dataLinkDefine = new RunTimeDataLinkDefineImpl();
        dataLinkDefine.setKey(dataLinkDO.getKey());
        dataLinkDefine.setUniqueCode(dataLinkDO.getUniqueCode());
        dataLinkDefine.setRegionKey(dataLinkDO.getRegionKey());
        dataLinkDefine.setLinkExpression(dataLinkDO.getLinkExpression());
        dataLinkDefine.setPosX(dataLinkDO.getPosX());
        dataLinkDefine.setPosY(dataLinkDO.getPosY());
        dataLinkDefine.setColNum(dataLinkDO.getColNum());
        dataLinkDefine.setRowNum(dataLinkDO.getRowNum());
        dataLinkDefine.setType(DataLinkType.forValue(dataLinkDO.getType()));
        return dataLinkDefine;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

