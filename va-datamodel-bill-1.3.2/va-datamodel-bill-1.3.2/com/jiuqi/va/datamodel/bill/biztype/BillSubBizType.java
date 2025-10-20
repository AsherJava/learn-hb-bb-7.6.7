/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.bill.biztype;

public enum BillSubBizType {
    BILLMaster(1),
    BILLSUB(2),
    BILLSLAVE(4),
    MULTIPLESUB(9);

    private Integer index;

    private BillSubBizType(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return this.index;
    }
}

