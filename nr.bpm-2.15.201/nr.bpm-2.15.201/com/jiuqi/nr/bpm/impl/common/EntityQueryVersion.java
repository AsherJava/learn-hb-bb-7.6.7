/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.text.ParseException;
import java.util.Date;

public class EntityQueryVersion {
    private Date queryVersionStartTime = Consts.DATE_VERSION_INVALID_VALUE;
    private Date queryVersionTime = Consts.DATE_VERSION_FOR_ALL;
    static final EntityQueryVersion DEFAULT = new EntityQueryVersion();

    public Date getQueryVersionStartDate() {
        return this.queryVersionStartTime;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionTime;
    }

    public static EntityQueryVersion parseFromPeriod(String periodString, String formSchemeKey, String entityId) {
        EntityQueryVersion queryPeriod = new EntityQueryVersion();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
        IPeriodEntityAdapter periodAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        Date[] dateRegion = null;
        try {
            dateRegion = periodAdapter.getPeriodProvider(entityId).getPeriodDateRegion(periodWrapper);
        }
        catch (ParseException e) {
            throw new BpmException(String.format("parse period %s error.", periodString), e);
        }
        if (dateRegion == null) {
            throw new BpmException(String.format("parse period %s error.", periodString));
        }
        queryPeriod.queryVersionStartTime = dateRegion[0];
        queryPeriod.queryVersionTime = dateRegion[1];
        return queryPeriod;
    }
}

