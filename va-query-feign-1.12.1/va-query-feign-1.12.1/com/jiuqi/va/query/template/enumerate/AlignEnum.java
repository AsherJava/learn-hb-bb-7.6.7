/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.enumerate;

public enum AlignEnum {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    private String name;

    private AlignEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static AlignEnum val(String name) {
        for (AlignEnum align : AlignEnum.values()) {
            if (!align.getName().equals(name)) continue;
            return align;
        }
        return null;
    }
}

