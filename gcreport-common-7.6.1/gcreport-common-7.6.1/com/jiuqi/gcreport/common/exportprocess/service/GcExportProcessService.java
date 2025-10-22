/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.exportprocess.service;

import com.jiuqi.gcreport.common.exportprocess.dto.ExportProcess;

public interface GcExportProcessService {
    public void setExportProcess(String var1, ExportProcess var2);

    public ExportProcess getExportProcess(String var1);

    public void removeExportProcess(String var1);

    public ExportProcess getProcess(String var1);
}

