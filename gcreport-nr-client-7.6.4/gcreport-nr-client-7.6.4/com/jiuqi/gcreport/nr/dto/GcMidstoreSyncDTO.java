/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.dto;

import com.jiuqi.gcreport.nr.dto.GcMidStoreTableDataDTO;
import java.util.List;

public class GcMidstoreSyncDTO {
    private String taskCode;
    private String midStoreSchemeCode;
    private List<GcMidStoreTableDataDTO> tableDataList;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getMidStoreSchemeCode() {
        return this.midStoreSchemeCode;
    }

    public void setMidStoreSchemeCode(String midStoreSchemeCode) {
        this.midStoreSchemeCode = midStoreSchemeCode;
    }

    public List<GcMidStoreTableDataDTO> getTableDataList() {
        return this.tableDataList;
    }

    public void setTableDataList(List<GcMidStoreTableDataDTO> tableDataList) {
        this.tableDataList = tableDataList;
    }
}

