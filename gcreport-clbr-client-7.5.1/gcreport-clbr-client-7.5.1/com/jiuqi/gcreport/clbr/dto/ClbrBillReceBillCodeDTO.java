/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Set;

public class ClbrBillReceBillCodeDTO {
    private String sysCode;
    private Set<String> sendBillIdList;
    private String receBillCode;
    private boolean deleteFlag;

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Set<String> getSendBillIdList() {
        return this.sendBillIdList;
    }

    public void setSendBillIdList(Set<String> sendBillIdList) {
        this.sendBillIdList = sendBillIdList;
    }

    public String getReceBillCode() {
        return this.receBillCode;
    }

    public void setReceBillCode(String receBillCode) {
        this.receBillCode = receBillCode;
    }

    public boolean isDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}

