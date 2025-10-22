/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UploadStatusDetail
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.bpm.common.UploadStatusDetail;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;

public interface BatchExportUploadService {
    public UploadStatusDetail getUploadDeatils(ExportExcelState var1) throws Exception;
}

