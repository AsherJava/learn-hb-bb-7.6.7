/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import java.math.BigDecimal;

public class WorkingPaperPrimaryQmsItemDTO {
    private String primaryId;
    private String orgCode;
    private BigDecimal zbValue;

    public static WorkingPaperPrimaryQmsItemDTO empty() {
        WorkingPaperPrimaryQmsItemDTO emptyItemDTO = new WorkingPaperPrimaryQmsItemDTO();
        emptyItemDTO.zbValue = BigDecimal.ZERO;
        return emptyItemDTO;
    }

    public String getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public BigDecimal getZbValue() {
        return this.zbValue;
    }

    public void setZbValue(BigDecimal zbValue) {
        this.zbValue = zbValue;
    }
}

