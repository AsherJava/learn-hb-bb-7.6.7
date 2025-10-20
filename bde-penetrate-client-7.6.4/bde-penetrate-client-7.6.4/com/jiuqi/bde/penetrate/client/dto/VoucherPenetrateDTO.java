/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.client.dto;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;

public class VoucherPenetrateDTO
extends PenetrateBaseDTO {
    private static final long serialVersionUID = -8999446679622237990L;
    private String vchrId;
    private String vchrItemId;

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public String getVchrItemId() {
        return this.vchrItemId;
    }

    public void setVchrItemId(String vchrItemId) {
        this.vchrItemId = vchrItemId;
    }

    @Override
    public String toString() {
        return "VoucherPenetrateDTO [vchrId=" + this.vchrId + ", vchrItemId=" + this.vchrItemId + ", toString()=" + super.toString() + "]";
    }
}

