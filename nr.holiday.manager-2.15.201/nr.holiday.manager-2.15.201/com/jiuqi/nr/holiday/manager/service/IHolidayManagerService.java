/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.holiday.manager.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.facade.HolidayObj;
import java.util.List;

public interface IHolidayManagerService {
    public List<HolidayDefine> doQueryHolidayDefine(String var1);

    public List<HolidayObj> doQuery(String var1);

    public void doSave(String var1, List<HolidayObj> var2) throws JQException;

    public void doDelete(String var1) throws JQException;

    public List<HolidayObj> doReset(String var1) throws JQException;

    public void doExport();

    public void doImport();
}

