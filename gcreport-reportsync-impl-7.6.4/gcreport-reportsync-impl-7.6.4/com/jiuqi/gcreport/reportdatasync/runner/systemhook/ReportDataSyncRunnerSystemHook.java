/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.runner.systemhook;

import com.jiuqi.gcreport.reportdatasync.runner.context.ReportDataSyncRunnerContext;
import java.io.File;
import java.io.IOException;

public interface ReportDataSyncRunnerSystemHook {
    public void pushHook(ReportDataSyncRunnerContext var1, File var2) throws IOException;

    public String getHookName();

    public String getHookTitle();

    public String getHookDescription();
}

