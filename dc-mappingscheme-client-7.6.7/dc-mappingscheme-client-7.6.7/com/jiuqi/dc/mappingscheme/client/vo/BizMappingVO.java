/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public class BizMappingVO
extends HashMap<String, String> {
    private String BizVoucherConvert;
    private String BizBalanceInitConvert;
    private String BizAgingUnClearConvert;

    @JsonProperty(value="BizVoucherConvert")
    public String getBizVoucherConvert() {
        return this.BizVoucherConvert;
    }

    public void setBizVoucherConvert(String bizVoucherConvert) {
        this.BizVoucherConvert = bizVoucherConvert;
    }

    @JsonProperty(value="BizBalanceInitConvert")
    public String getBizBalanceInitConvert() {
        return this.BizBalanceInitConvert;
    }

    public void setBizBalanceInitConvert(String bizBalanceInitConvert) {
        this.BizBalanceInitConvert = bizBalanceInitConvert;
    }

    @JsonProperty(value="BizAgingUnClearConvert")
    public String getBizAgingUnClearConvert() {
        return this.BizAgingUnClearConvert;
    }

    public void setBizAgingUnClearConvert(String bizAgingUnClearConvert) {
        this.BizAgingUnClearConvert = bizAgingUnClearConvert;
    }
}

