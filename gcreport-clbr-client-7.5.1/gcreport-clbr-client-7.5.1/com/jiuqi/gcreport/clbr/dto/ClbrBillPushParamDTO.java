/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO;
import java.util.List;

public class ClbrBillPushParamDTO {
    private String sn;
    private String sysCode;
    private List<ClbrBillPushItemParamDTO> items;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public List<ClbrBillPushItemParamDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ClbrBillPushItemParamDTO> items) {
        this.items = items;
    }
}

