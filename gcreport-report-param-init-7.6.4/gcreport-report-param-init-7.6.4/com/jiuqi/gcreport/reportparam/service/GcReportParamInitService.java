/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportparam.service;

import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitExecuteVO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitVO;
import java.util.List;

public interface GcReportParamInitService {
    public List<GcReportParamInitVO> listReportParamPackage();

    public void startInit(GcReportParamInitExecuteVO var1);
}

