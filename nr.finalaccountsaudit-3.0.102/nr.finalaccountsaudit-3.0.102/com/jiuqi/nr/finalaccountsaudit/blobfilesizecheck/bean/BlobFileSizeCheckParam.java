/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlobFileSizeCheckParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private List<String> unitKeys = new ArrayList<String>();
    private List<BlobFormStruct> selBlobItem;
    private double minValue;
    private double maxValue;

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public List<BlobFormStruct> getSelBlobItem() {
        return this.selBlobItem;
    }

    public void setSelBlobItem(List<BlobFormStruct> selBlobItem) {
        this.selBlobItem = selBlobItem;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getUnitKeys() {
        return this.unitKeys;
    }

    public void setUnitKeys(List<String> unitKeys) {
        this.unitKeys = unitKeys;
    }
}

