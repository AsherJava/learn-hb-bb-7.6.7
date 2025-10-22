/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.modal.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;

public class I18nPeriodEntity
implements IPeriodEntity {
    private final IPeriodEntity iPeriodEntity;
    private String title;

    public I18nPeriodEntity(IPeriodEntity iPeriodEntity) {
        this.iPeriodEntity = iPeriodEntity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getKey() {
        return this.iPeriodEntity.getKey();
    }

    @Override
    public String getCode() {
        return this.iPeriodEntity.getCode();
    }

    @Override
    public String getTitle() {
        return StringUtils.isEmpty(this.title) ? this.iPeriodEntity.getTitle() : this.title;
    }

    @Override
    public PeriodType getPeriodType() {
        return this.iPeriodEntity.getPeriodType();
    }

    @Override
    public PeriodType getType() {
        return this.iPeriodEntity.getType();
    }

    @Override
    public Date getCreateTime() {
        return this.iPeriodEntity.getCreateTime();
    }

    @Override
    public Date getUpdateTime() {
        return this.iPeriodEntity.getUpdateTime();
    }

    @Override
    public String getCreateUser() {
        return this.iPeriodEntity.getCreateUser();
    }

    @Override
    public String getUpdateUser() {
        return this.iPeriodEntity.getUpdateUser();
    }

    @Override
    public String getDimensionName() {
        return this.iPeriodEntity.getDimensionName();
    }

    @Override
    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.iPeriodEntity.getPeriodPropertyGroup();
    }

    @Override
    public String getOrder() {
        return this.iPeriodEntity.getOrder();
    }

    @Override
    public String getVersion() {
        return this.iPeriodEntity.getVersion();
    }

    @Override
    public String getOwnerLevelAndId() {
        return this.iPeriodEntity.getOwnerLevelAndId();
    }

    public IPeriodEntity getiPeriodEntity() {
        return this.iPeriodEntity;
    }

    @Override
    public int getMaxFiscalMonth() {
        return this.iPeriodEntity.getMaxFiscalMonth();
    }

    @Override
    public int getMinFiscalMonth() {
        return this.iPeriodEntity.getMinFiscalMonth();
    }

    @Override
    public int getMinYear() {
        return this.iPeriodEntity.getMinYear();
    }

    @Override
    public int getMaxYear() {
        return this.iPeriodEntity.getMaxYear();
    }

    @Override
    public int getDataType() {
        return this.iPeriodEntity.getDataType();
    }

    public String getID() {
        return this.iPeriodEntity.getKey();
    }
}

