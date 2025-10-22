/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import java.sql.Timestamp;

public class MetaDataEntity {
    private String id;
    private String code;
    private String title;
    private String descr;
    private Integer dataType;
    private Integer accuracy;
    private Integer decimal;
    private String floatArea;
    private Timestamp createTime;
    private Timestamp upTimeStamp;
    private String belongForm;

    public String getBelongForm() {
        return this.belongForm;
    }

    public void setBelongForm(String belongForm) {
        this.belongForm = belongForm;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getFloatArea() {
        return this.floatArea;
    }

    public void setFloatArea(String floatArea) {
        this.floatArea = floatArea;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpTimeStamp() {
        return this.upTimeStamp;
    }

    public void setUpTimeStamp(Timestamp upTimeStamp) {
        this.upTimeStamp = upTimeStamp;
    }
}

