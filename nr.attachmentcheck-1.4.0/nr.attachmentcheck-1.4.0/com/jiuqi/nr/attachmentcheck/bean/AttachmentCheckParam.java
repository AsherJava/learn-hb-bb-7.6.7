/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class AttachmentCheckParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private DimensionCollection dims;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private List<AttachmentFormStruct> selBlobItem;
    private double minValue;
    private double maxValue;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<AttachmentFormStruct> getSelBlobItem() {
        return this.selBlobItem;
    }

    public void setSelBlobItem(List<AttachmentFormStruct> selBlobItem) {
        this.selBlobItem = selBlobItem;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}

