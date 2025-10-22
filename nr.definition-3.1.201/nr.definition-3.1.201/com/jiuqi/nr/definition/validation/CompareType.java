/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.validation;

public enum CompareType {
    BETWEEN("\u4ecb\u4e8e", ">=", "<=", 1),
    NOT_BETWEEN("\u672a\u4ecb\u4e8e", "<", ">", 2),
    EQUAL("\u7b49\u4e8e", "=", 3),
    NOT_EQUAL("\u4e0d\u7b49\u4e8e", "<>", 4),
    MORE_THAN("\u5927\u4e8e", ">", 5),
    LESS_THAN("\u5c0f\u4e8e", "<", 6),
    MORE_THAN_OR_EQUAL("\u5927\u4e8e\u6216\u7b49\u4e8e", ">=", 7),
    LESS_THAN_OR_EQUAL("\u5c0f\u4e8e\u6216\u7b49\u4e8e", "<=", 8),
    CONTAINS("\u5305\u542b", ">=", 9),
    NOT_CONTAINS("\u4e0d\u5305\u542b", "<", 10),
    IN("\u6570\u503c\u67e5\u627e", "in", 11),
    MOBILEPHONE("\u624b\u673a\u53f7", "IsMobilePhone", 12),
    NOTNULL("\u4e0d\u5141\u8bb8\u4e3a\u7a7a", "NotIsNull", 13),
    MAXLEN("\u6700\u5927\u957f\u5ea6", "MaxLen", 14);

    private final String title;
    private final int value;
    private final String sign;
    private final String sign2;
    private static CompareType[] TYPES;

    private CompareType(String title, String sign, int value) {
        this(title, sign, null, value);
    }

    private CompareType(String title, String sign, String sign2, int value) {
        this.title = title;
        this.value = value;
        this.sign = sign;
        this.sign2 = sign2;
    }

    public CompareType getCompareType(int value) {
        for (CompareType temp : CompareType.values()) {
            if (temp.getValue() != value) continue;
            return temp;
        }
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    public int getValue() {
        return this.value;
    }

    public String getSign() {
        return this.sign;
    }

    public String getSign2() {
        return this.sign2;
    }

    public static CompareType fromType(int type) {
        if (type == 0) {
            return null;
        }
        return TYPES[type - 1];
    }

    static {
        TYPES = new CompareType[]{BETWEEN, NOT_BETWEEN, EQUAL, NOT_EQUAL, MORE_THAN, LESS_THAN, MORE_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL, CONTAINS, NOT_CONTAINS, IN, MOBILEPHONE, NOTNULL, MAXLEN};
    }
}

