/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.modal.impl;

import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.Date;

public class I18nPeriodRow
implements IPeriodRow {
    private final IPeriodRow iPeriodRow;
    private String title;

    public I18nPeriodRow(IPeriodRow iPeriodRow) {
        this.iPeriodRow = iPeriodRow;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getKey() {
        return this.iPeriodRow.getKey();
    }

    @Override
    public String getCode() {
        return this.iPeriodRow.getCode();
    }

    @Override
    public String getTitle() {
        return StringUtils.isEmpty(this.title) ? this.iPeriodRow.getTitle() : this.title;
    }

    @Override
    public String getAlias() {
        return this.iPeriodRow.getAlias();
    }

    @Override
    public Date getStartDate() {
        return this.iPeriodRow.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return this.iPeriodRow.getEndDate();
    }

    @Override
    public Date getCreateTime() {
        return this.iPeriodRow.getCreateTime();
    }

    @Override
    public Date getUpdateTime() {
        return this.iPeriodRow.getUpdateTime();
    }

    @Override
    public String getCreateUser() {
        return this.iPeriodRow.getCreateUser();
    }

    @Override
    public String getUpdateUser() {
        return this.iPeriodRow.getUpdateUser();
    }

    @Override
    public int getYear() {
        return this.iPeriodRow.getYear();
    }

    @Override
    public int getQuarter() {
        return this.iPeriodRow.getQuarter();
    }

    @Override
    public int getMonth() {
        return this.iPeriodRow.getMonth();
    }

    @Override
    public int getDay() {
        return this.iPeriodRow.getDay();
    }

    @Override
    public String getTimeKey() {
        return this.iPeriodRow.getTimeKey();
    }

    @Override
    public int getDays() {
        return this.iPeriodRow.getDays();
    }

    @Override
    public String getOrder() {
        return this.iPeriodRow.getOrder();
    }

    @Override
    public String getVersion() {
        return this.iPeriodRow.getVersion();
    }

    @Override
    public String getOwnerLevelAndId() {
        return this.iPeriodRow.getOwnerLevelAndId();
    }

    public IPeriodRow getiPeriodRow() {
        return this.iPeriodRow;
    }

    @Override
    public String getSimpleTitle() {
        return this.iPeriodRow.getSimpleTitle();
    }

    public String getID() {
        return this.iPeriodRow.getKey();
    }
}

