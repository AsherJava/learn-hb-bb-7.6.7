/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.OrderFieldInfo;
import java.io.Serializable;
import java.util.List;

public class FloatTableGatherSetting
implements Serializable {
    private String regionKey;
    private boolean isListGather = false;
    private List<String> unClassifyFields;
    private List<OrderFieldInfo> orderFields;
    private Integer ReservedRows;
    private boolean isSingleFloatTableGather = false;
    private boolean isAllNumFieldsSum = false;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public boolean isListGather() {
        return this.isListGather;
    }

    public void setListGather(boolean listGather) {
        this.isListGather = listGather;
    }

    public List<String> getUnClassifyFields() {
        return this.unClassifyFields;
    }

    public void setUnClassifyFields(List<String> unClassifyFields) {
        this.unClassifyFields = unClassifyFields;
    }

    public List<OrderFieldInfo> getOrderFields() {
        return this.orderFields;
    }

    public void setOrderFields(List<OrderFieldInfo> orderFields) {
        this.orderFields = orderFields;
    }

    public Integer getReservedRows() {
        return this.ReservedRows;
    }

    public void setReservedRows(Integer reservedRows) {
        this.ReservedRows = reservedRows;
    }

    public boolean isSingleFloatTableGather() {
        return this.isSingleFloatTableGather;
    }

    public void setSingleFloatTableGather(boolean singleFloatTableGather) {
        this.isSingleFloatTableGather = singleFloatTableGather;
    }

    public boolean isAllNumFieldsSum() {
        return this.isAllNumFieldsSum;
    }

    public void setAllNumFieldsSum(boolean allNumFieldsSum) {
        this.isAllNumFieldsSum = allNumFieldsSum;
    }
}

