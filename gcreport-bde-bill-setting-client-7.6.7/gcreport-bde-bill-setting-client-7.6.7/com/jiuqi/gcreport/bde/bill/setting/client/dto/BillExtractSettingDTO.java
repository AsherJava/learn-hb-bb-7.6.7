/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import java.util.Map;

public class BillExtractSettingDTO {
    private String billType;
    private String billTable;
    private BillFloatRegionConfigDTO floatSetting;
    private Map<String, BillFixedSettingDTO> fixedSetting;
    private Map<String, BillExtractSettingDTO> itemTableSetting;

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

    public BillFloatRegionConfigDTO getFloatSetting() {
        return this.floatSetting;
    }

    public void setFloatSetting(BillFloatRegionConfigDTO floatSetting) {
        this.floatSetting = floatSetting;
    }

    public Map<String, BillFixedSettingDTO> getFixedSetting() {
        return this.fixedSetting;
    }

    public void setFixedSetting(Map<String, BillFixedSettingDTO> fixedSetting) {
        this.fixedSetting = fixedSetting;
    }

    public Map<String, BillExtractSettingDTO> getItemTableSetting() {
        return this.itemTableSetting;
    }

    public void setItemTableSetting(Map<String, BillExtractSettingDTO> itemTableSetting) {
        this.itemTableSetting = itemTableSetting;
    }

    public String toString() {
        return "BillExtractSettingDTO [billType=" + this.billType + ", billTable=" + this.billTable + ", floatSetting=" + this.floatSetting + ", fixedSetting=" + this.fixedSetting + ", itemTableSetting=" + this.itemTableSetting + "]";
    }
}

