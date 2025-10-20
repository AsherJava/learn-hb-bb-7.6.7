/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import java.io.Serializable;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataRefSaveDTO
implements Serializable {
    private static final long serialVersionUID = 9074423465687316063L;
    private String dataSchemeCode;
    private String tableName;
    private Boolean customFlag;
    private List<DataRefDTO> data;

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

    public List<DataRefDTO> getData() {
        return this.data;
    }

    public void setData(List<DataRefDTO> data) {
        this.data = data;
    }
}

