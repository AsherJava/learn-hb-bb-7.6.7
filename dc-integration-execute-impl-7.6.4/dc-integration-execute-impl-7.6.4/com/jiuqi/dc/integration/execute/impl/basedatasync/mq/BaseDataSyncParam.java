/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.mq;

import java.io.Serializable;
import java.util.List;

public class BaseDataSyncParam
implements Serializable {
    private static final long serialVersionUID = 1784676910878642390L;
    private String baseDataCode;
    private String syncType;
    private List<String> baseDataCodeList;
    private List<String> orgTypeList;
    private String baseDataType;

    public BaseDataSyncParam(String baseDataCode, String syncType, String baseDataType) {
        this.baseDataCode = baseDataCode;
        this.syncType = syncType;
        this.baseDataType = baseDataType;
    }

    public BaseDataSyncParam() {
    }

    public String getSyncType() {
        return this.syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getBaseDataCode() {
        return this.baseDataCode;
    }

    public void setBaseDataCode(String baseDataCode) {
        this.baseDataCode = baseDataCode;
    }

    public List<String> getBaseDataCodeList() {
        return this.baseDataCodeList;
    }

    public void setBaseDataCodeList(List<String> baseDataCodeList) {
        this.baseDataCodeList = baseDataCodeList;
    }

    public List<String> getOrgTypeList() {
        return this.orgTypeList;
    }

    public void setOrgTypeList(List<String> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public String getBaseDataType() {
        return this.baseDataType;
    }

    public void setBaseDataType(String baseDataType) {
        this.baseDataType = baseDataType;
    }
}

