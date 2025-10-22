/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemResultDTO;
import java.util.List;

public class ClbrBillPushResultDTO {
    private String sn;
    private String message;
    private Boolean status = Boolean.FALSE;
    private List<ClbrBillPushItemResultDTO> items;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ClbrBillPushItemResultDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ClbrBillPushItemResultDTO> items) {
        this.items = items;
    }
}

