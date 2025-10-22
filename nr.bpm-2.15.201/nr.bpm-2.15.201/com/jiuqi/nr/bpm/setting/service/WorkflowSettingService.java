/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.bpm.setting.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowParam;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.pojo.BaseData;
import com.jiuqi.nr.bpm.setting.pojo.DataParam;
import com.jiuqi.nr.bpm.setting.pojo.EntitryCount;
import com.jiuqi.nr.bpm.setting.pojo.ITreeNode;
import com.jiuqi.nr.bpm.setting.pojo.ProcessExcelParam;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackPrintData;
import com.jiuqi.nr.bpm.setting.pojo.ReportData;
import com.jiuqi.nr.bpm.setting.pojo.ReportParam;
import com.jiuqi.nr.bpm.setting.pojo.SearchResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.pojo.StartState;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.GridDataResult;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.IGridParam;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowData;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowTree;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface WorkflowSettingService {
    public String saveWorkFlowSettingData(WorkflowSettingPojo var1);

    public List<ITreeNode> getTaskByCondition() throws Exception;

    public List<WorkflowSettingDefine> queryWorkflowSettings();

    public String deleteWorkflowSetting(String var1, String var2, String var3);

    public void updateSettingState(String var1, boolean var2);

    public void refreshStrategicPartici(String var1, String var2);

    public StartState startDataObjs(StateChangeObj var1);

    public boolean clearDataObjs(StateChangeObj var1);

    public BaseData queryBaseParam(DataParam var1);

    public List<GridDataResult> queryGridData(IGridParam var1);

    public int entityQuerySetCount(EntitryCount var1);

    public ShowResult showWorkflow(ShowNodeParam var1);

    public WorkflowSettingDefine getWorkflowDefineByFormSchemeKey(String var1);

    public List<SearchResult> searchByInput(String var1);

    public int getFormSchemeSizeByWorkflow(String var1);

    public List<WorkflowParam> getAllWorkflowList();

    public void exportExcel(HttpServletResponse var1, ProcessExcelParam var2);

    public ProcessTrackPrintData printProcessTrack(List<ProcessTrackExcelInfo> var1);

    public void deleteWorkflowProcess(String var1, BusinessKey var2);

    public void getStrategicParticiLog(String var1);

    public void autoStartLog(BusinessKey var1, List<IEntityRow> var2);

    public WorkFlowDefine getWorkflowDefine(String var1, String var2);

    public List<ReportData> queryReportGroupData(ReportParam var1);

    public void unBindProcess(StateChangeObj var1);

    public WorkFlowType queryStartType(String var1);

    public WorkflowStatus queryFlowType(String var1);

    public WorkflowStatus queryFlowType(String var1, String var2, String var3, String var4);

    public boolean queryStartType(String var1, String var2, String var3, String var4);

    public Map<String, Map<String, Boolean>> isProcessStop(String var1, String var2, List<String> var3, List<String> var4);

    public void deleteBindData(String var1, String var2, String var3, String var4);

    public StartState startDataObjs(StateChangeObj var1, AsyncTaskMonitor var2);

    public boolean clearDataObjs(StateChangeObj var1, AsyncTaskMonitor var2);

    public boolean unBindProcess(StateChangeObj var1, AsyncTaskMonitor var2);

    public List<WorkflowTree<WorkflowData>> getAllTask(String var1, String var2);
}

