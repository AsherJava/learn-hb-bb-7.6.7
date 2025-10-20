/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.common;

import java.util.HashMap;
import java.util.Map;

public enum SubjectAttributeEnum {
    ASSET(0, "\u8d44\u4ea7\u7c7b"),
    DEBT(1, "\u8d1f\u503a\u7c7b"),
    RIGHT(2, "\u6743\u76ca\u7c7b"),
    PROFITLOSS(3, "\u635f\u76ca\u7c7b"),
    CASH(4, "\u73b0\u91d1\u6d41\u91cf\u7c7b"),
    OTHER(5, "\u5176\u4ed6");

    private int value;
    private String label;

    private SubjectAttributeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static SubjectAttributeEnum getEnumByValue(Integer value) {
        SubjectAttributeEnum[] attributeEnums;
        for (SubjectAttributeEnum attributeEnum : attributeEnums = SubjectAttributeEnum.values()) {
            if (attributeEnum.getValue() != value.intValue()) continue;
            return attributeEnum;
        }
        return null;
    }

    public static String getEnumLabel(Integer value) {
        for (Map.Entry<Integer, String> entry : SubjectAttributeEnum.getMap().entrySet()) {
            if (!entry.getKey().equals(value)) continue;
            return entry.getValue();
        }
        return null;
    }

    public static Integer getEnumValue(String label) {
        for (Map.Entry<Integer, String> entry : SubjectAttributeEnum.getMap().entrySet()) {
            if (!entry.getValue().equals(label)) continue;
            return entry.getKey();
        }
        return null;
    }

    public static Map<Integer, String> getMap() {
        SubjectAttributeEnum[] values;
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (SubjectAttributeEnum value : values = SubjectAttributeEnum.values()) {
            map.put(value.getValue(), value.getLabel());
        }
        return map;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

