/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import java.io.Serializable;
import java.util.List;

public class FloatCompareDifference
implements Serializable {
    private static final long serialVersionUID = -4103572174777153733L;
    private List<AbstractData> naturalDatas;
    private AbstractData bizKeyOrder;
    private AbstractData groupTreeDeep;
    private AbstractData groupKey;
    private List<DifferenceDataItem> differenceDataItems;

    public List<AbstractData> getNaturalDatas() {
        return this.naturalDatas;
    }

    public void setNaturalDatas(List<AbstractData> naturalDatas) {
        this.naturalDatas = naturalDatas;
    }

    public AbstractData getBizKeyOrder() {
        return this.bizKeyOrder;
    }

    public void setBizKeyOrder(AbstractData bizKeyOrder) {
        this.bizKeyOrder = bizKeyOrder;
    }

    public AbstractData getGroupTreeDeep() {
        return this.groupTreeDeep;
    }

    public void setGroupTreeDeep(AbstractData groupTreeDeep) {
        this.groupTreeDeep = groupTreeDeep;
    }

    public AbstractData getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(AbstractData groupKey) {
        this.groupKey = groupKey;
    }

    public List<DifferenceDataItem> getDifferenceDataItems() {
        return this.differenceDataItems;
    }

    public void setDifferenceDataItems(List<DifferenceDataItem> differenceDataItems) {
        this.differenceDataItems = differenceDataItems;
    }
}

