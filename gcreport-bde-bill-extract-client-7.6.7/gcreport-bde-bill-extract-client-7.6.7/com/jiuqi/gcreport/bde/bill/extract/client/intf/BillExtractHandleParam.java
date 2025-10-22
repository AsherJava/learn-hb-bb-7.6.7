/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

import java.util.List;
import java.util.Map;

public class BillExtractHandleParam {
    private List<String> unitCodes;
    private String startDateStr;
    private String endDateStr;
    private Map<String, String> extInfo;

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getStartDateStr() {
        return this.startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return this.endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Map<String, String> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }
}

