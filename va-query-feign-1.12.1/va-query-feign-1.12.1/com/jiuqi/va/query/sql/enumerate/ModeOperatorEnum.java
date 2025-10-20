/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.enumerate;

public enum ModeOperatorEnum {
    EQ("EQ", "\u7b49\u4e8e"),
    NE("NE", "\u4e0d\u7b49\u4e8e"),
    LT("LT", "\u5c0f\u4e8e"),
    LE("LE", "\u5c0f\u4e8e\u7b49\u4e8e"),
    GT("GT", "\u5927\u4e8e"),
    GE("GE", "\u5927\u4e8e\u7b49\u4e8e"),
    in("in", "\u5305\u542b"),
    notIn("notIn", "\u4e0d\u5305\u542b"),
    like("like", "\u6a21\u7cca\u5339\u914d"),
    leftLike("leftLike", "\u5de6\u5339\u914d"),
    notLike("notLike", "\u975e\u6a21\u7cca\u5339\u914d"),
    notLeftLike("notLeftLike", "\u975e\u5de6\u5339\u914d");

    private String operatorCode;
    private String operatorName;

    private ModeOperatorEnum(String operatorCode, String operatorName) {
        this.operatorCode = operatorCode;
        this.operatorName = operatorName;
    }

    public static ModeOperatorEnum getByCode(String modeOperatorCode) {
        for (ModeOperatorEnum modeOperatorEnum : ModeOperatorEnum.values()) {
            if (!modeOperatorEnum.getOperatorCode().equals(modeOperatorCode)) continue;
            return modeOperatorEnum;
        }
        return null;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public String getOperatorCode() {
        return this.operatorCode;
    }
}

