/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.Date;
import java.util.List;

public interface PeriodAdapterService {
    public List<IPeriodEntity> getPeriodList();

    public IPeriodEntity getPeriodByKey(String var1);

    public IPeriodEntity getPeriodByCode(String var1);

    public List<IPeriodEntity> getPeriodByType(PeriodType var1);

    public List<IPeriodRow> getDataListByCode(String var1);

    public List<IPeriodRow> getDataListByKey(String var1);

    public Date[] getPeriodDateRegion(String var1);

    public String[] getPeriodDateStrRegion(String var1);

    public int getPeriodType(String var1);

    public IPeriodRow getPeriodData(String var1, String var2);

    public int getPeriodDataType(String var1) throws JQException;

    public IPeriodRow modify(String var1, String var2, int var3) throws Exception;

    public IPeriodRow modify(String var1, String var2, PeriodModifier var3) throws Exception;

    public IPeriodRow customModify(String var1, String var2, PeriodModifier var3) throws Exception;

    public IPeriodRow getCurPeriod(String var1);
}

