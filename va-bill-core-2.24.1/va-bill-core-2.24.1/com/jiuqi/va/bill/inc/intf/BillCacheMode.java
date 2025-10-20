/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.value.EnumValue
 */
package com.jiuqi.va.bill.inc.intf;

import com.jiuqi.va.biz.intf.value.EnumValue;

public enum BillCacheMode implements EnumValue
{
    LOCAL(1, "caffeine"),
    REDIS(2, "redis");

    private int value;
    private String title;

    private BillCacheMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

