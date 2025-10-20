/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.archive.plugin;

import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.List;

public interface ArchivePlugin {
    public String getPluginName();

    public String getPluginCode();

    default public void afterArchiveProcessComplete(ArchiveLogEO logEO, StringBuffer logInfo) {
    }

    default public void beforeSingleArchive(ArchiveContext context, PeriodWrapper startPeriodWrapper, ArchiveParamData archiveParamData) {
    }

    default public void afterSingleArchive(String archiveResult, ArchiveParamData archiveParamData) {
    }

    public String doAction(ArchiveContext var1, ArchiveLogEO var2, List<ArchiveConfigEO> var3);

    public boolean reuploadArchive(ArchiveContext var1, String var2, StringBuffer var3);

    default public void beforeArchive(PeriodWrapper startPeriodWrapper) {
    }
}

