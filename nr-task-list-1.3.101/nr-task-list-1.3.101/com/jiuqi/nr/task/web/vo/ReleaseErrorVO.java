/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

public class ReleaseErrorVO {
    private final String type;
    private final String desc;

    public ReleaseErrorVO(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }
}

