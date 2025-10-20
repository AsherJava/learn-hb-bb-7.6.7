/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class OrderVO {
    private String groupKey;
    private String[] keys;
    private String[] orders;

    public String[] getOrders() {
        return this.orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String[] getKeys() {
        return this.keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }
}

