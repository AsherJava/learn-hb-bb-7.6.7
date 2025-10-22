/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting;
import java.util.List;
import java.util.Map;

public interface SameCtrlExtractDataService {
    public void initReportSameCtrlChgEnvContextImpl(SameCtrlExtractDataVO var1, SameCtrlChgEnvContextImpl var2);

    public void initOffsetSameCtrlChgEnvContextImpl(SameCtrlExtractDataVO var1, SameCtrlChgEnvContextImpl var2);

    public void extractReportData(SameCtrlChgEnvContextImpl var1);

    public void saveExtractReportData(SameCtrlExtractTableZbSetting var1, List<List<Map<String, Object>>> var2, SameCtrlExtractReportCond var3);
}

