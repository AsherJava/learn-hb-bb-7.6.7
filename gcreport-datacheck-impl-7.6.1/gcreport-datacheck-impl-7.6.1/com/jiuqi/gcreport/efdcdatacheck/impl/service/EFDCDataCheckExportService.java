/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.np.core.context.NpContext
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.np.core.context.NpContext;
import javax.servlet.http.HttpServletResponse;

public interface EFDCDataCheckExportService {
    public StringBuffer planTaskCheckResultPdf(NpContext var1, String var2, EFDCDataCheckImpl var3, GcBatchEfdcCheckInfo var4) throws Exception;

    public void exportPdf(GcFormOperationInfo var1, HttpServletResponse var2) throws Exception;

    public StringBuffer planTaskCheckResultExcel(String var1, EFDCDataCheckImpl var2, GcBatchEfdcCheckInfo var3) throws Exception;
}

