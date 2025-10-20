/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;

public class VaParamSyncParamDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private Integer exportRefData;
    private VaParamSyncMainfestDO mainfest;
    private VaParamSyncModuleEnum paramType;
    private Integer publish;
    private String requestID;
    private String fileID;

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Integer getPublish() {
        return this.publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getFileID() {
        return this.fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public VaParamSyncModuleEnum getParamType() {
        return this.paramType;
    }

    public void setParamType(VaParamSyncModuleEnum paramType) {
        this.paramType = paramType;
    }

    public Integer getExportRefData() {
        return this.exportRefData;
    }

    public void setExportRefData(Integer exportRefData) {
        this.exportRefData = exportRefData;
    }

    public VaParamSyncMainfestDO getMainfest() {
        return this.mainfest;
    }

    public void setMainfest(VaParamSyncMainfestDO mainfest) {
        this.mainfest = mainfest;
    }
}

