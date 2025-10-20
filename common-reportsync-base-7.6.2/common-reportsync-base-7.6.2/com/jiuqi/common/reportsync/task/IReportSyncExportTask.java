/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.reportsync.task;

import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import java.util.Collection;

public interface IReportSyncExportTask {
    public boolean match(ReportDataSyncParams var1);

    public ReportDataSyncTypeEnum syncType();

    public Collection<String> exec(ReportSyncExportTaskContext var1);

    public String funcTitle();
}

