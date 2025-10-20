/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.jiuqi.dc.datamapping.client.dto.RefChangePairDTO;
import java.io.Serializable;
import java.util.List;

public class RefChangeHandleParamDTO
implements Serializable {
    private static final long serialVersionUID = 8067169005129485494L;
    private String dataSchemeCode;
    private String tableName;
    private Boolean customFlag;
    private String unitCode;
    private List<Integer> isolateYears;
    private List<RefChangePairDTO> refChangePairDatas;

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

    public Boolean getCustomFlag() {
        return this.customFlag;
    }

    public void setCustomFlag(Boolean customFlag) {
        this.customFlag = customFlag;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<Integer> getIsolateYears() {
        return this.isolateYears;
    }

    public void setIsolateYears(List<Integer> isolateYears) {
        this.isolateYears = isolateYears;
    }

    public List<RefChangePairDTO> getRefChangePairDatas() {
        return this.refChangePairDatas;
    }

    public void setRefChangePairDatas(List<RefChangePairDTO> refChangePairDatas) {
        this.refChangePairDatas = refChangePairDatas;
    }
}

