/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.enums;

public enum WorkingPaperType {
    SIMPLE("SIMPLE", 1),
    CUBES("CUBES", 2),
    MERGE("ArbitrarilyMerge", 3);

    private String code;
    private Integer type;

    private WorkingPaperType(String code, Integer type) {
        this.code = code;
        this.type = type;
    }

    public static WorkingPaperType getEnumByCode(String code) {
        for (WorkingPaperType typeEnum : WorkingPaperType.values()) {
            if (!typeEnum.getCode().equals(code)) continue;
            return typeEnum;
        }
        return null;
    }

    public static WorkingPaperType getEnumByType(Integer type) {
        for (WorkingPaperType typeEnum : WorkingPaperType.values()) {
            if (!typeEnum.getType().equals(type)) continue;
            return typeEnum;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

