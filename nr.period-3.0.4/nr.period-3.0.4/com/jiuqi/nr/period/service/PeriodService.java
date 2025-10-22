/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.service;

import com.jiuqi.nr.period.common.rest.PeriodY13Obj;
import com.jiuqi.nr.period.common.utils.EntityInfo;
import com.jiuqi.nr.period.common.utils.SearchParam;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import java.util.List;

public interface PeriodService {
    public List<IPeriodEntity> getPeriodList() throws Exception;

    public IPeriodEntity queryPeriodByKey(String var1) throws Exception;

    public void extensionDefaultPeriod(IPeriodEntity var1, String var2, String var3) throws Exception;

    public void insertCustomPeriod(IPeriodEntity var1) throws Exception;

    public void updateCustomPeriod(IPeriodEntity var1) throws Exception;

    public void updatePeriodDate(IPeriodEntity var1) throws Exception;

    public void deleteCustomPeriod(String var1) throws Exception;

    public List<IPeriodEntity> queryDefaultPeriodList() throws Exception;

    public List<IPeriodEntity> queryCustomPeriodList() throws Exception;

    public List<IPeriodEntity> queryPeriodList(SearchParam var1) throws Exception;

    public IPeriodEntity queryPeriodByKeyLanguage(String var1, String var2) throws Exception;

    public void insertCustomPeriodLanguage(IPeriodEntity var1, String var2) throws Exception;

    public void updateCustomPeriodLanguage(IPeriodEntity var1, String var2) throws Exception;

    public void insertPeriodY13(IPeriodEntity var1, String var2) throws Exception;

    public void updatePeriodY13(IPeriodEntity var1, String var2) throws Exception;

    public List<PeriodDataDefineImpl> createPeriodY13Data(IPeriodEntity var1, PeriodY13Obj var2, boolean var3) throws Exception;

    public List<PeriodDataDefineImpl> extendPeriodY13Data(IPeriodEntity var1, PeriodY13Obj var2, boolean var3) throws Exception;

    public void insertPeriodY13Datas(String var1, List<PeriodDataDefineImpl> var2) throws Exception;

    public EntityInfo createEntityInfo(String var1) throws Exception;

    public void updateEntityInfo(String var1) throws Exception;

    public void updateDataType(IPeriodEntity var1, int var2) throws Exception;
}

