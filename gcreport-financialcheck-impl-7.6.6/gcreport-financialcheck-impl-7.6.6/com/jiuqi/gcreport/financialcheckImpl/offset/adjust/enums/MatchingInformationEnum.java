/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.enums;

public enum MatchingInformationEnum {
    NO_FOUND_RULE(0, "\u6ca1\u6709\u5339\u914d\u5230\u5408\u5e76\u89c4\u5219");

    private int value;
    private String message;

    private MatchingInformationEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public MatchingInformationEnum getMatchingInformation(int value) {
        for (MatchingInformationEnum matchingInformationEnum : MatchingInformationEnum.values()) {
            if (value != matchingInformationEnum.value) continue;
            return matchingInformationEnum;
        }
        return null;
    }
}

