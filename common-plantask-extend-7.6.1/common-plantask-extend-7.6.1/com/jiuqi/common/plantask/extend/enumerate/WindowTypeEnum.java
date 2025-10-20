/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.enumerate;

public enum WindowTypeEnum {
    MINI("mini", "\u5bf9\u5e94520\u5bbd\u5ea6"),
    MEDIUM("medium", "\u5bf9\u5e94660\u5bbd\u5ea6"),
    MAX("max", "\u5bf9\u5e94960\u5bbd\u5ea6");

    String type;
    String describe;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    private WindowTypeEnum(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }
}

