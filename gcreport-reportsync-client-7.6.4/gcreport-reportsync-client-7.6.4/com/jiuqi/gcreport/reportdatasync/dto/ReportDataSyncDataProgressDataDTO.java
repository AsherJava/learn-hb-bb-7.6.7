/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 */
package com.jiuqi.gcreport.reportdatasync.dto;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReportDataSyncDataProgressDataDTO
extends ProgressDataImpl<List<String>> {
    public ReportDataSyncDataProgressDataDTO() {
        this(UUID.randomUUID().toString());
    }

    public ReportDataSyncDataProgressDataDTO(String sn) {
        super(sn, new CopyOnWriteArrayList(), "ReportDataSyncDataProgressDataDTO");
    }
}

