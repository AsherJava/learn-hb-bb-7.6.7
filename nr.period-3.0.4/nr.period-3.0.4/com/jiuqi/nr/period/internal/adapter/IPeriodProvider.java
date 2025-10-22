/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.IPeriodAdapter
 */
package com.jiuqi.nr.period.internal.adapter;

import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.Date;
import java.util.List;

public interface IPeriodProvider
extends IPeriodAdapter {
    public List<IPeriodRow> getPeriodItems();

    public Date[] getPeriodDateRegion();

    public String[] getPeriodCodeRegion();

    public int comparePeriod(String var1, String var2);

    public String nextPeriod(String var1);

    public String priorPeriod(String var1);

    public IPeriodRow getCurPeriod();

    public IPeriodEntity getPeriodEntity();

    public List<PeriodDataSelectObject> getPeriodDataByModifyTitle();
}

