/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.common;

import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckErrorKind;

public class EnumDataCheckResultItem {
    private String id;
    private String dataValue;
    private String enumCode;
    private EnumCheckErrorKind errorKind;
    private String masterEntityKey;
    private int entityOrder;
    private String entityTitle;
    private String enumTitle;
    private String bbfz;
    private String field;
    private String formKey;
    private String dataLinkKey;
    private String regionId;
    private String dataId;

    public String getFormKey() {
        return this.formKey;
    }

    public String getId() {
        return this.id;
    }

    public String getMasterEntityKey() {
        return this.masterEntityKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDataValue(String string) {
        this.dataValue = string;
    }

    public String getDataValue() {
        return this.dataValue;
    }

    public String getEnumCode() {
        return this.enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public EnumCheckErrorKind getErrorKind() {
        return this.errorKind;
    }

    public void setErrorKind(EnumCheckErrorKind errorKind) {
        this.errorKind = errorKind;
    }

    public void setMasterEntityKey(String masterEntityKey) {
        this.masterEntityKey = masterEntityKey;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getBbfz() {
        return this.bbfz;
    }

    public void setBbfz(String bbfz) {
        this.bbfz = bbfz;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public String getEnumTitle() {
        return this.enumTitle;
    }

    public void setEnumTitle(String enumTitle) {
        this.enumTitle = enumTitle;
    }

    public void setDataLinkKey(String key) {
        this.dataLinkKey = key;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getEntityOrder() {
        return this.entityOrder;
    }

    public void setEntityOrder(int entityOrder) {
        this.entityOrder = entityOrder;
    }
}

