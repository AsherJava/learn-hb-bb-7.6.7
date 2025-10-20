/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum BalanceTable {
    PREASSBALANCE,
    CFBALANCE;


    public int getBalanceTableType() {
        return this.ordinal();
    }

    public static BalanceTable typeof(Integer type) {
        for (BalanceTable table : BalanceTable.values()) {
            if (table.getBalanceTableType() != type.intValue()) continue;
            return table;
        }
        return null;
    }
}

