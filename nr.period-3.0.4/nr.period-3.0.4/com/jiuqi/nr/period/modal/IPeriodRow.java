/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.period.modal;

import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.Date;

public interface IPeriodRow
extends IModelDefineItem {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getAlias();

    public Date getStartDate();

    public Date getEndDate();

    public Date getCreateTime();

    public Date getUpdateTime();

    public String getCreateUser();

    public String getUpdateUser();

    public int getYear();

    public int getQuarter();

    public int getMonth();

    public int getDay();

    public String getTimeKey();

    public int getDays();

    public String getSimpleTitle();

    public String getOrder();

    public String getVersion();

    public String getOwnerLevelAndId();
}

