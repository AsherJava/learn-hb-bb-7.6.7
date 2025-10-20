/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.client.dto;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import java.util.List;

public class AdjustVoucherSaveDTO {
    private List<AdjustVoucherVO> vouchers;
    private List<String> deletedVchrs;
    private String influenceLaterFlag;

    public List<AdjustVoucherVO> getVouchers() {
        return this.vouchers;
    }

    public void setVouchers(List<AdjustVoucherVO> vouchers) {
        this.vouchers = vouchers;
    }

    public List<String> getDeletedVchrs() {
        return this.deletedVchrs;
    }

    public void setDeletedVchrs(List<String> deletedVchrs) {
        this.deletedVchrs = deletedVchrs;
    }

    public String getInfluenceLaterFlag() {
        return this.influenceLaterFlag;
    }

    public void setInfluenceLaterFlag(String influenceLaterFlag) {
        this.influenceLaterFlag = influenceLaterFlag;
    }
}

