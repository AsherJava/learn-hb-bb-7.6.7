/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.gcreport.reportparam.service;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.reportparam.dto.GcReportParamInfoDTO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamProgressVO;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public interface GcReportParamInitExecuteService {
    public void fileImport(ProgressDataImpl<List<GcReportParamProgressVO>> var1, Map<String, List<GcReportParamInfoDTO>> var2, AtomicBoolean var3, double var4, Double var6, String var7);

    public void saveOrUpdate(String var1, boolean var2);

    public void movAndPublishTask(ProgressDataImpl<List<GcReportParamProgressVO>> var1, String var2, DesignTaskDefine var3);

    public void importTaskData(ProgressDataImpl<List<GcReportParamProgressVO>> var1, List<GcReportParamInfoDTO> var2, Map<String, String> var3) throws Exception;
}

