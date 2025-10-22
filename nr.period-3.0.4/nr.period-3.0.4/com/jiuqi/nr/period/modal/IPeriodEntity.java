/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.period.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.util.TitleState;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.Date;

public interface IPeriodEntity
extends IModelDefineItem {
    public String getKey();

    public String getCode();

    public String getTitle();

    public PeriodType getPeriodType();

    public PeriodType getType();

    public Date getCreateTime();

    public Date getUpdateTime();

    public String getCreateUser();

    public String getUpdateUser();

    public String getDimensionName();

    public PeriodPropertyGroup getPeriodPropertyGroup();

    @JsonIgnoreProperties
    default public int getMaxFiscalMonth() {
        return -1;
    }

    @JsonIgnoreProperties
    default public int getMinFiscalMonth() {
        return -1;
    }

    @JsonIgnoreProperties
    default public int getMinYear() {
        return -1;
    }

    @JsonIgnoreProperties
    default public int getMaxYear() {
        return -1;
    }

    @JsonIgnoreProperties
    default public int getDataType() {
        return TitleState.NONE.getValue();
    }

    public String getOrder();

    public String getVersion();

    public String getOwnerLevelAndId();
}

