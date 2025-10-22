/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumCheckErrorKind;
import java.io.Serializable;
import java.util.Map;

public class EnumDataCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String dataValue;
    private String enumCode;
    private EnumCheckErrorKind errorKind;
    private String masterEntityKey;
    private int entityOrder;
    private String entityTitle;
    private Map<String, String> dimNameValueMap;
    private Map<String, String> dimNameCodeMap;
    private String enumTitle;
    private String bbfz;
    private String field;
    private String formKey;
    private String dataLinkKey;
    private String regionId;
    private String dataId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataValue() {
        return this.dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
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

    public String getMasterEntityKey() {
        return this.masterEntityKey;
    }

    public void setMasterEntityKey(String masterEntityKey) {
        this.masterEntityKey = masterEntityKey;
    }

    public int getEntityOrder() {
        return this.entityOrder;
    }

    public void setEntityOrder(int entityOrder) {
        this.entityOrder = entityOrder;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public Map<String, String> getDimNameValueMap() {
        return this.dimNameValueMap;
    }

    public void setDimNameValueMap(Map<String, String> dimNameValueMap) {
        this.dimNameValueMap = dimNameValueMap;
    }

    public Map<String, String> getDimNameCodeMap() {
        return this.dimNameCodeMap;
    }

    public void setDimNameCodeMap(Map<String, String> dimNameCodeMap) {
        this.dimNameCodeMap = dimNameCodeMap;
    }

    public String getEnumTitle() {
        return this.enumTitle;
    }

    public void setEnumTitle(String enumTitle) {
        this.enumTitle = enumTitle;
    }

    public String getBbfz() {
        return this.bbfz;
    }

    public void setBbfz(String bbfz) {
        this.bbfz = bbfz;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
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
}

