/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO;
import java.util.List;

public class BillFixedSettingDTO {
    private String billType;
    private String billTable;
    private String dataField;
    private String regionType;
    private List<FieldAdaptSettingDTO> fieldAdaptSettings;

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

    public String getDataField() {
        return this.dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public String getRegionType() {
        return this.regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public List<FieldAdaptSettingDTO> getFieldAdaptSettings() {
        return this.fieldAdaptSettings;
    }

    public void setFieldAdaptSettings(List<FieldAdaptSettingDTO> fieldAdaptSettings) {
        this.fieldAdaptSettings = fieldAdaptSettings;
    }

    public String toString() {
        return "BillFixedSettingDTO [billType=" + this.billType + ", billTable=" + this.billTable + ", dataField=" + this.dataField + ", regionType=" + this.regionType + ", fieldAdaptSettings=" + this.fieldAdaptSettings + "]";
    }
}

