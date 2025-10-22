/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investbill.cons;

public enum BillTableEnum {
    GC_COMBINEDASSETBILL,
    GC_COMBINEDASSETBILLITEM,
    GC_COMMONASSETBILL,
    GC_FVCHBILL,
    GC_FVCH_FIXEDITEM,
    GC_FVCH_OTHERITEM,
    GC_INVESTBILL,
    GC_INVESTBILLITEM;


    public static boolean contains(String value) {
        try {
            BillTableEnum.valueOf(value);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}

