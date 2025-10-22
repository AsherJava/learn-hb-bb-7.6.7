/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.util.List;

public class SchemeRangeFormInfo
implements SchemeRangeForm {
    private RangeFormType rangeFormType = RangeFormType.ALL;
    private List<String> formList;

    @Override
    public RangeFormType getRangeFormType() {
        return this.rangeFormType;
    }

    public void setRangeFormType(RangeFormType rangeFormType) {
        this.rangeFormType = rangeFormType;
    }

    @Override
    public List<String> getFormList() {
        return this.formList;
    }

    public void setFormList(List<String> formList) {
        this.formList = formList;
    }

    @Override
    @JsonIgnore
    public String valueToClob() {
        switch (this.rangeFormType) {
            case CUSTOM: {
                return BatchSummaryUtils.toJSONStr(this.formList);
            }
        }
        return null;
    }

    @JsonIgnore
    public void transformAndSetFromList(String jsonStr) {
        switch (this.rangeFormType) {
            case CUSTOM: {
                this.formList = BatchSummaryUtils.toJavaArray(jsonStr, String.class);
            }
        }
    }
}

