/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum SignTypeEnum {
    PLUS("+", 1),
    SUBTRACT("-", -1);

    private String sign;
    private int orient;

    private SignTypeEnum(String sign, int orient) {
        this.sign = sign;
        this.orient = orient;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getOrient() {
        return this.orient;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public static SignTypeEnum typeof(String sign) {
        for (SignTypeEnum signType : SignTypeEnum.values()) {
            if (!signType.getSign().equals(sign)) continue;
            return signType;
        }
        throw new IllegalArgumentException("No enum constant SignTypeEnum." + sign);
    }
}

