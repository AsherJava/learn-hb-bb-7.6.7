/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import java.io.Serializable;

public class RefChangeDTO
implements Serializable {
    public static final String REFCHANGEHANDLETASKNAME = "RefChangeHandle";
    private static final long serialVersionUID = -1702971019027879252L;
    private String dataSchemeCode;
    private String tableName;
    private DataRefDTO refData;
    private DataRefDTO oldRefData;
    private Boolean customFlag;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataRefDTO getRefData() {
        return this.refData;
    }

    public void setRefData(DataRefDTO refData) {
        this.refData = refData;
    }

    public DataRefDTO getOldRefData() {
        return this.oldRefData;
    }

    public void setOldRefData(DataRefDTO oldRefData) {
        this.oldRefData = oldRefData;
    }

    public Boolean getCustomFlag() {
        return this.customFlag;
    }

    public void setCustomFlag(Boolean customFlag) {
        this.customFlag = customFlag;
    }
}

