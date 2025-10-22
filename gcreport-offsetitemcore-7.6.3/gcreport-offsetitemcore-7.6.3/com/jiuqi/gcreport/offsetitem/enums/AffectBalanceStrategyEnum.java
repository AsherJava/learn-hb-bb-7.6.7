/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum AffectBalanceStrategyEnum {
    DEFAUT_AFFECT_DETAIL(0),
    AFFECT_RESET(1),
    NO_AFFECT(2);

    private int strategyValue;

    private AffectBalanceStrategyEnum(int strategyValue) {
        this.strategyValue = strategyValue;
    }

    public int getStrategyValue() {
        return this.strategyValue;
    }

    public void setStrategyValue(int strategyValue) {
        this.strategyValue = strategyValue;
    }

    public static AffectBalanceStrategyEnum getStrategyByValue(int strategyValue) {
        for (AffectBalanceStrategyEnum balanceStrategyEnum : AffectBalanceStrategyEnum.values()) {
            if (balanceStrategyEnum.getStrategyValue() != strategyValue) continue;
            return balanceStrategyEnum;
        }
        return DEFAUT_AFFECT_DETAIL;
    }

    public static int getStrategyValue(AffectBalanceStrategyEnum balanceStrategyEnum) {
        return balanceStrategyEnum == null ? AffectBalanceStrategyEnum.DEFAUT_AFFECT_DETAIL.strategyValue : balanceStrategyEnum.getStrategyValue();
    }
}

