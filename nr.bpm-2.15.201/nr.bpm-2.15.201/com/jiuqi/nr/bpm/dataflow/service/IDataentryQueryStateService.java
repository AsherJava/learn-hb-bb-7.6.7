/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.dataflow.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IDataentryQueryStateService {
    public List<WorkflowDataInfo> queryWorkflowDataInfo(WorkflowDataBean var1);

    public Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo(BatchWorkflowDataBean var1);

    public ActionStateBean queryResourceState(DataEntryParam var1);

    public ActionStateBean queryUnitState(DataEntryParam var1);

    public UploadStateNew queryUnitStateInfo(DataEntryParam var1);

    public ActionStateBean queryUploadState(String var1, DimensionValueSet var2, String var3, String var4);

    public ReadOnlyBean readOnly(DataEntryParam var1);

    public Map<DimensionValueSet, ActionStateBean> getWorkflowUploadState(DimensionValueSet var1, String var2, String var3, String var4);

    public UploadStateNew queryUploadStateList(String var1, DimensionValueSet var2, String var3, String var4);

    public ReadOnlyBean readOnly(DataEntryParam var1, UploadStateNew var2);
}

