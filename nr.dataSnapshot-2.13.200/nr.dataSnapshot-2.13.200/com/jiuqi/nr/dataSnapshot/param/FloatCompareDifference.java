/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.snapshot.message.DifferenceDataItem
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import java.util.List;

public class FloatCompareDifference {
    private List<String> naturalDatas;
    private String bizKeyOrder;
    private List<DifferenceDataItem> differenceDataItems;

    public List<String> getNaturalDatas() {
        return this.naturalDatas;
    }

    public void setNaturalDatas(List<String> naturalDatas) {
        this.naturalDatas = naturalDatas;
    }

    public String getBizKeyOrder() {
        return this.bizKeyOrder;
    }

    public void setBizKeyOrder(String bizKeyOrder) {
        this.bizKeyOrder = bizKeyOrder;
    }

    public List<DifferenceDataItem> getDifferenceDataItems() {
        return this.differenceDataItems;
    }

    public void setDifferenceDataItems(List<DifferenceDataItem> differenceDataItems) {
        this.differenceDataItems = differenceDataItems;
    }
}

