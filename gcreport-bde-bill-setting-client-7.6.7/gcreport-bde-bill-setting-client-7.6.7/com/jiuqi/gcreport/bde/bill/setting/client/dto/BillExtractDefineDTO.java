/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BillExtractDefineDTO {
    private String id;
    private String name;
    private String billSettingType;
    private BigDecimal ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillSettingType() {
        return this.billSettingType;
    }

    public void setBillSettingType(String billSettingType) {
        this.billSettingType = billSettingType;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String toString() {
        return "BillExtractGroupDTO [id=" + this.id + ", billSettingType=" + this.billSettingType + ", ordinal=" + this.ordinal + "]";
    }
}

