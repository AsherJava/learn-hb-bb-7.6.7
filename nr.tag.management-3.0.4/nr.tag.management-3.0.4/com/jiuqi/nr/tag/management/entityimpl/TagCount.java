/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.entityimpl;

public class TagCount {
    private String tagKey;
    private int unitTotal;

    public TagCount(String tagKey, int unitTotal) {
        this.tagKey = tagKey;
        this.unitTotal = unitTotal;
    }

    public String getTagKey() {
        return this.tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public int getUnitTotal() {
        return this.unitTotal;
    }

    public void setUnitTotal(int unitTotal) {
        this.unitTotal = unitTotal;
    }
}

