/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.client.dto;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdjustVchrQueryResultDTO
implements Serializable {
    private static final long serialVersionUID = -1799766447999748429L;
    private List<AdjustVoucherVO> vouchers;
    private Integer total;

    public AdjustVchrQueryResultDTO() {
        this.vouchers = new ArrayList<AdjustVoucherVO>();
        this.total = 0;
    }

    public AdjustVchrQueryResultDTO(List<AdjustVoucherVO> vouchers, Integer total) {
        this.vouchers = vouchers;
        this.total = total;
    }

    public List<AdjustVoucherVO> getVouchers() {
        return this.vouchers;
    }

    public void setVouchers(List<AdjustVoucherVO> vouchers) {
        this.vouchers = vouchers;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

