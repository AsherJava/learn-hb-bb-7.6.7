/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.bean;

public class FilterCacheSetItem
implements Cloneable {
    private double order;
    private String entityDataKey;

    public String getEntityDataKey() {
        return this.entityDataKey;
    }

    public void setEntityDataKey(String entityDataKey) {
        this.entityDataKey = entityDataKey;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static FilterCacheSetItem clone(FilterCacheSetItem item) throws CloneNotSupportedException {
        return (FilterCacheSetItem)item.clone();
    }
}

