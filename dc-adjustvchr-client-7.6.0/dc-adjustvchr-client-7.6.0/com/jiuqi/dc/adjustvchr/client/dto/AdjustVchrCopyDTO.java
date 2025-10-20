/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.adjustvchr.client.dto;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import java.util.List;
import javax.validation.constraints.NotNull;

public class AdjustVchrCopyDTO {
    @NotNull(message="\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a") int acctYear;
    @NotNull(message="\u671f\u95f4\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u671f\u95f4\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String periodType;
    @NotNull(message="\u8d77\u59cb\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8d77\u59cb\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a") String affectPeriodStart;
    @NotNull(message="\u622a\u6b62\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u622a\u6b62\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a") String affectPeriodEnd;
    private List<AdjustVoucherVO> srcVouchers;
    @NotNull(message="\u51ed\u8bc1\u6240\u5c5e\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u51ed\u8bc1\u6240\u5c5e\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a") int acctPeriod;

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getAffectPeriodStart() {
        return this.affectPeriodStart;
    }

    public void setAffectPeriodStart(String affectPeriodStart) {
        this.affectPeriodStart = affectPeriodStart;
    }

    public String getAffectPeriodEnd() {
        return this.affectPeriodEnd;
    }

    public void setAffectPeriodEnd(String affectPeriodEnd) {
        this.affectPeriodEnd = affectPeriodEnd;
    }

    public List<AdjustVoucherVO> getSrcVouchers() {
        return this.srcVouchers;
    }

    public void setSrcVouchers(List<AdjustVoucherVO> srcVouchers) {
        this.srcVouchers = srcVouchers;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }
}

