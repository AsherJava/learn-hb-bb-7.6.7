/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.coop.feign.config.domain;

import com.jiuqi.coop.feign.config.domain.ClbrItemDTO;
import java.util.List;

public class ClbrOperateDTO {
    private static final long serialVersionUID = 1L;
    List<ClbrItemDTO> clbrItems;
    private String clbrCode;
    private String clbrState;
    private String clbrTime;
    private String clbrReason;
    private String unitCode;

    public List<ClbrItemDTO> getClbrItems() {
        return this.clbrItems;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
}

