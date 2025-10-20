/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto;

import com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto.ClbrItemDTO;
import java.util.List;

public class ClbrOperateDTO {
    private static final long serialVersionUID = 1L;
    List<ClbrItemDTO> clbrItems;
    private String clbrCode;
    private String clbrState;
    private String clbrTime;
    private String clbrReason;
    private String operateUser;

    public List<ClbrItemDTO> getClbrItems() {
        return this.clbrItems;
    }

    public void setClbrItems(List<ClbrItemDTO> clbrItems) {
        this.clbrItems = clbrItems;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrState() {
        return this.clbrState;
    }

    public void setClbrState(String clbrState) {
        this.clbrState = clbrState;
    }

    public String getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(String clbrTime) {
        this.clbrTime = clbrTime;
    }

    public String getClbrReason() {
        return this.clbrReason;
    }

    public void setClbrReason(String clbrReason) {
        this.clbrReason = clbrReason;
    }

    public String getOperateUser() {
        return this.operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }
}

