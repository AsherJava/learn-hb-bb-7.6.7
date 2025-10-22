/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 */
package com.jiuqi.gcreport.samecontrol.env;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import java.util.List;

public interface SameCtrlChgEnvContext
extends ProgressData<List<String>> {
    public SameCtrlExtractReportCond getSameCtrlExtractReportCond();

    public SameCtrlOffsetCond getSameCtrlOffsetCond();

    public SameCtrlExtractManageCond getSameCtrlExtractManageCond();

    public List<String> getResult();

    public void addResultItem(String var1);
}

