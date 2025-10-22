/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.bde.common.dto.QueryConfigInfo;

public class BillFloatRegionConfigDTO {
    private String billType;
    private String billTable;
    private String queryType;
    private QueryConfigInfo queryConfigInfo;

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillTable() {
        return this.billTable;
    }

    public void setBillTable(String billTable) {
        this.billTable = billTable;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public QueryConfigInfo getQueryConfigInfo() {
        return this.queryConfigInfo;
    }

    public void setQueryConfigInfo(QueryConfigInfo queryConfigInfo) {
        this.queryConfigInfo = queryConfigInfo;
    }

    public String toString() {
        return "BillFloatRegionConfigDTO [billType=" + this.billType + ", billTable=" + this.billTable + ", queryType=" + this.queryType + ", queryConfigInfo=" + this.queryConfigInfo + "]";
    }
}

