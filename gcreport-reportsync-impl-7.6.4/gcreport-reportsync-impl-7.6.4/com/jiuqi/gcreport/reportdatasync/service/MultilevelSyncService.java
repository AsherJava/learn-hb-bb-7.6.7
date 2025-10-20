/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;

public interface MultilevelSyncService {
    public Boolean multilevelSync(MultilevelSyncContext var1);

    public Boolean multilevelImport(MultilevelImportContext var1);

    public Boolean multilevelReturn(MultilevelReturnContext var1);
}

