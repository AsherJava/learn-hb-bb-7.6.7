/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import com.jiuqi.nr.batch.summary.storage.entity.SummarySchemeDes;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class SummarySchemeImpl
implements SummarySchemeDes {
    private String key;
    private String title;
    private String group;
    private Date updateTime;
    private Date sumDataTime;
    private String ordinal;
    private SchemeRangeUnit rangeUnit;
    private SchemeRangeForm rangeForm;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Date getSumDataTime() {
        return this.sumDataTime;
    }

    public void setSumDataTime(Date sumDataTime) {
        this.sumDataTime = sumDataTime;
    }

    @Override
    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public SchemeRangeUnit getRangeUnit() {
        return this.rangeUnit;
    }

    public void setRangeUnit(SchemeRangeUnit rangeUnit) {
        this.rangeUnit = rangeUnit;
    }

    @Override
    public SchemeRangeForm getRangeForm() {
        return this.rangeForm;
    }

    public void setRangeForm(SchemeRangeForm rangeForm) {
        this.rangeForm = rangeForm;
    }

    @JsonIgnore
    public boolean isValidScheme() {
        return StringUtils.isNotEmpty((String)this.title) && this.rangeUnit != null && this.rangeForm != null;
    }
}

