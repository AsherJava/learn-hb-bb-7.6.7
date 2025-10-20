/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.entity.ArchiveParamData
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.archive.plugin.inspur.service;

import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.period.PeriodWrapper;

public interface InspurArchiveService {
    public void beforeSingleArchive(ArchiveContext var1, PeriodWrapper var2, ArchiveParamData var3);

    public void afterSingleArchive(String var1, ArchiveParamData var2);

    public void getArchiveParamData(ArchiveParamData var1, ArchiveContext var2);

    public boolean getUnitEndFillState(ArchiveContext var1, ContextUser var2);
}

