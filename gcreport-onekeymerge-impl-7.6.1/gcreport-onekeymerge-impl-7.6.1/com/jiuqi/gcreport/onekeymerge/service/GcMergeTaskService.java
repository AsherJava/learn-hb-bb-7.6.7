/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO;
import com.jiuqi.gcreport.onekeymerge.executor.model.MergeTaskLogsExcelModel;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;

public interface GcMergeTaskService {
    public void mergeTask(List<GcOrgCacheVO> var1, GcActionParamsVO var2);

    public MergeTaskProcessDTO getProcess(String var1);

    public void stopMergeTask(String var1, String var2);

    public MergeTaskResultLogDTO getMergeTaskLogs(String var1);

    public void createTaskTree(List<GcOrgCacheVO> var1, GcActionParamsVO var2);

    public List<MergeTaskRecordDTO> getTaskRecord(GcActionParamsVO var1);

    public List<MergeTaskLogsExcelModel> exportLogs(String var1);

    public String checkOrgInfo(GcActionParamsVO var1);
}

