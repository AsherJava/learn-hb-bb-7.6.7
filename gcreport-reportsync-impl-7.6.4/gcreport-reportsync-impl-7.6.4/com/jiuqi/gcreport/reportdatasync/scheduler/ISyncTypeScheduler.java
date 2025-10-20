/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 */
package com.jiuqi.gcreport.reportdatasync.scheduler;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import java.util.List;

public interface ISyncTypeScheduler {
    public String code();

    public String name();

    public List<ReportDataSyncServerInfoVO> getServerInfoVOList();

    public MultilevelSyncContext buildContext(String var1);

    public ReportSyncFileDTO exportFileToOss(MultilevelSyncContext var1);

    public void afterExport(MultilevelSyncContext var1);

    public boolean importData(MultilevelImportContext var1);

    public boolean isSync();

    public boolean multilevelReturn(MultilevelReturnContext var1);
}

