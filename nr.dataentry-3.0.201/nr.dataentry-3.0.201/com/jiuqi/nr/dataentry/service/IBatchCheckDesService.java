/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.DesCheckResult;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;

@Deprecated
public interface IBatchCheckDesService {
    @Deprecated
    public DesCheckResult desCheckResult(BatchCheckInfo var1);

    @Deprecated
    public ExportData desCheckResultExport(BatchCheckInfo var1);
}

