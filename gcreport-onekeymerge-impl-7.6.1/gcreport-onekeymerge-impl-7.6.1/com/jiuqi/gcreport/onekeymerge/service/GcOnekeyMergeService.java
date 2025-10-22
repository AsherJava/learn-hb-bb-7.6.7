/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;

public interface GcOnekeyMergeService {
    @Async
    public void doMergeAsync(GcActionParamsVO var1);

    public void doMerge(GcActionParamsVO var1);

    public Boolean checkDiffCurrency(GcActionParamsVO var1, GcOrgCacheVO var2);

    public ReturnObject checkCurrencyDataAndUploadState(GcActionParamsVO var1, GcOrgCacheVO var2);

    public ReturnObject checkTarCurrencyData(GcActionParamsVO var1, GcOrgCacheVO var2);

    public ReturnObject checkCurCurrencyData(GcActionParamsVO var1, GcOrgCacheVO var2);

    public GcConversionContextEnv buildConversionEnv(GcActionParamsVO var1, GcOrgCacheVO var2);

    public TaskLog getTaskLog(String var1);

    public Map<String, DimensionValue> buildDimensionMap(String var1, String var2, String var3, String var4, String var5, String var6);

    public boolean getFinishCalcState(GcActionParamsVO var1, String var2);

    public UploadState getUploadSate(GcActionParamsVO var1, String var2);

    public GcTaskResultVO convertTaskResultEO2VO(GcTaskResultEO var1);

    public ReturnObject checkFinishCalcState(GcActionParamsVO var1);

    public ReturnObject checkUpLoadAndFinishCalState(GcActionParamsVO var1, String var2);

    public ReturnObject checkUploadState(GcActionParamsVO var1, String var2);

    public void saveTaskResult(GcActionParamsVO var1, GcTaskResultEO var2, List<GcBaseTaskStateVO> var3, String var4);

    public TaskLog getTaskLogWithCode(String var1, String var2);

    public void stopMerge(GcActionParamsVO var1);

    public boolean getStopOrNot(String var1);

    public DebugZbInfoVO debugZbReWrite(GcActionParamsVO var1, String var2);
}

