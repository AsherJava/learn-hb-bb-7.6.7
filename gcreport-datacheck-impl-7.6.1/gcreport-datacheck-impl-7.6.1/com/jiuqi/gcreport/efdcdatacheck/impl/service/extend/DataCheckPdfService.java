/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.extend;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;

public interface DataCheckPdfService {
    public String getDataCheckPdfHeaderMsg(String var1, GcBatchEfdcCheckInfo var2, EFDCDataCheckImpl var3);
}

