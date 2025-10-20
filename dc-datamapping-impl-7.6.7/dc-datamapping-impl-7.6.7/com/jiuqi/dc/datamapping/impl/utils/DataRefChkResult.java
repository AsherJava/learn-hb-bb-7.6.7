/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import java.util.List;
import java.util.Map;

public class DataRefChkResult {
    private Map<Integer, String> errorMessage;
    private List<DataRefDTO> createData;
    private List<DataRefDTO> modifyData;
    private List<DataRefDTO> deleteData;
    private List<DataRefDTO> savedDataList;

    public Map<Integer, String> getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(Map<Integer, String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DataRefDTO> getCreateData() {
        return this.createData;
    }

    public void setCreateData(List<DataRefDTO> createData) {
        this.createData = createData;
    }

    public List<DataRefDTO> getModifyData() {
        return this.modifyData;
    }

    public void setModifyData(List<DataRefDTO> modifyData) {
        this.modifyData = modifyData;
    }

    public List<DataRefDTO> getDeleteData() {
        return this.deleteData;
    }

    public void setDeleteData(List<DataRefDTO> deleteData) {
        this.deleteData = deleteData;
    }

    public List<DataRefDTO> getSavedDataList() {
        return this.savedDataList;
    }

    public void setSavedDataList(List<DataRefDTO> savedDataList) {
        this.savedDataList = savedDataList;
    }
}

