/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.integritycheck.common.ErrorDesShowInfo;
import com.jiuqi.nr.integritycheck.common.ErrorDesSysOptInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityDataInfo;
import java.io.Serializable;
import java.util.Map;

public class ResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String entityId;
    private IntegrityDataInfo integrityDataInfo;
    private Map<String, ErrorDesShowInfo> tableICDInfos;
    private String batchId;
    private ErrorDesSysOptInfo errorDesSysOptInfo;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public IntegrityDataInfo getIntegrityDataInfo() {
        return this.integrityDataInfo;
    }

    public void setIntegrityDataInfo(IntegrityDataInfo integrityDataInfo) {
        this.integrityDataInfo = integrityDataInfo;
    }

    public Map<String, ErrorDesShowInfo> getTableICDInfos() {
        return this.tableICDInfos;
    }

    public void setTableICDInfos(Map<String, ErrorDesShowInfo> tableICDInfos) {
        this.tableICDInfos = tableICDInfos;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public ErrorDesSysOptInfo getErrorDesSysOptInfo() {
        return this.errorDesSysOptInfo;
    }

    public void setErrorDesSysOptInfo(ErrorDesSysOptInfo errorDesSysOptInfo) {
        this.errorDesSysOptInfo = errorDesSysOptInfo;
    }
}

