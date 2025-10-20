/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.dto;

import com.jiuqi.gcreport.rate.impl.enums.AmountTypeEnum;
import com.jiuqi.gcreport.rate.impl.enums.ConversionTypeEnum;
import java.io.Serializable;
import java.math.BigDecimal;

public class ConvertParam
implements Serializable {
    private AmountTypeEnum amountType;
    private String rateCode;
    private ConversionTypeEnum conversionTypeEnum;
    private BigDecimal rateValue;

    public AmountTypeEnum getAmountType() {
        return this.amountType;
    }

    public void setAmountType(AmountTypeEnum amountType) {
        this.amountType = amountType;
    }

    public String getRateCode() {
        return this.rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public ConversionTypeEnum getConversionTypeEnum() {
        return this.conversionTypeEnum;
    }

    public void setConversionTypeEnum(ConversionTypeEnum conversionTypeEnum) {
        this.conversionTypeEnum = conversionTypeEnum;
    }

    public BigDecimal getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(BigDecimal rateValue) {
        this.rateValue = rateValue;
    }
}

