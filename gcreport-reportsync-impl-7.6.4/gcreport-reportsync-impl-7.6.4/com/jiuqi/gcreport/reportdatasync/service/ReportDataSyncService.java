/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import java.io.ByteArrayOutputStream;

public interface ReportDataSyncService {
    public ByteArrayOutputStream export(ReportDataSyncParams var1, String var2);

    public String upload(String var1);
}

