/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.jtable.common.BatchSummaryConst
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.ICheckResultExtraService
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.CheckTransformUtil
 *  com.jiuqi.nr.parallel.IParallelRunner
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.internal.service.util.CheckResultParamForReportUtil;
import com.jiuqi.nr.dataentry.internal.service.util.QueryLastOperateUtil;
import com.jiuqi.nr.dataentry.internal.service.util.UploadCheckFliterUtil;
import com.jiuqi.nr.dataentry.monitor.LogicProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.dataentry.service.IAllCheckService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.ICheckResultExtraService;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.CheckTransformUtil;
import com.jiuqi.nr.parallel.IParallelRunner;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllCheckServiceImpl
implements IAllCheckService {
    private static final Logger logger = LoggerFactory.getLogger(AllCheckServiceImpl.class);
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    IJtableDataEngineService jtableDataEngineService;
    @Autowired
    IParallelRunner parallelRunner;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    IFormulaCheckDesService formulaCheckDesService;
    @Autowired
    IJtableEntityService jtableEntityService;
    @Autowired(required=false)
    ICheckResultExtraService checkResultExtraService;
    @Autowired
    IDataentryFlowService dataentryFlowService;
    @Autowired
    AsyncTaskManager asyncTaskManager;
    @Autowired
    IFMDMAttributeService fMDMAttributeService;
    @Autowired
    private ICheckService checkService;
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private CheckTransformUtil checkTransformUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CheckResultParamForReportUtil checkResultParamForReportUtil;
    @Autowired
    private UploadCheckFliterUtil uploadCheckFliterUtil;
    @Autowired
    private QueryLastOperateUtil queryLastOperateUtil;
    @Autowired
    private IRunTimeViewController runtimeView;

    @Override
    public void allCheckForm(AllCheckInfo allCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        String getParam = "get_param";
        asyncTaskMonitor.progressAndMessage(0.07, getParam);
        JtableContext jtableContext = new JtableContext(allCheckInfo.getContext());
        CheckParam checkParam = new CheckParam();
        checkParam.setActionId(asyncTaskMonitor.getTaskId());
        checkParam.setMode(Mode.FORM);
        checkParam.setRangeKeys(new ArrayList());
        checkParam.setVariableMap(jtableContext.getVariableMap());
        checkParam.setFormulaSchemeKey(allCheckInfo.getFormulaSchemeKeys());
        checkParam.setDimensionCollection(this.getDimensionCollection(jtableContext));
        checkParam.setIgnoreItems(this.getIgnoreItems(jtableContext, checkParam));
        String batchChecking = "batch_check_ing";
        LogicProgressMonitor progressMonitor = new LogicProgressMonitor(asyncTaskMonitor, 0.07, batchChecking, 0.9199999999999999);
        this.checkService.allCheck(checkParam, (IFmlMonitor)progressMonitor);
        if (asyncTaskMonitor.isCancel()) {
            asyncTaskMonitor.canceled("stop_execute", (Object)"");
            LogHelper.info((String)"\u6279\u91cf\u5ba1\u6838", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        String checkSuccessInfo = "all_check_success_info";
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish(checkSuccessInfo, (Object)(checkSuccessInfo + "\u3002"));
        }
    }

    @Override
    public FormulaCheckReturnInfo allCheckResult(AllCheckInfo allCheckInfo) {
        boolean judgeCurrentcyType;
        List<Integer> checkTypes1 = allCheckInfo.getCheckTypes();
        if (checkTypes1 == null || checkTypes1.isEmpty()) {
            return new FormulaCheckReturnInfo();
        }
        String asyncTaskKey = allCheckInfo.getAsyncTaskKey();
        this.getFormulaSchemeKeys(allCheckInfo);
        List<String> formulaSchemeKeys = Arrays.asList(allCheckInfo.getFormulaSchemeKeys().split(";"));
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        if (allCheckInfo.isWorkFlowCheck()) {
            DUserActionParam dUserActionParam = allCheckInfo.getdUserActionParam();
            checkResultQueryParam.setFilterCondition(dUserActionParam.getCheckFilter());
            String checkFormulaValue = dUserActionParam.getCheckFormulaValue();
            String[] split = checkFormulaValue.split(";");
            checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(split));
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(allCheckInfo.getContext().getTaskKey());
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            boolean specialAudit = flowsSetting.getSpecialAudit();
            String string = allCheckInfo.getActionCode();
        } else {
            checkResultQueryParam.setRangeKeys(new ArrayList());
            checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeKeys);
        }
        checkResultQueryParam.setMode(Mode.FORM);
        if (!allCheckInfo.getFormulas().isEmpty()) {
            ArrayList<String> formKeys = new ArrayList<String>();
            HashSet formulaIds = new HashSet();
            for (String key : allCheckInfo.getFormulas().keySet()) {
                formKeys.add(key);
                formulaIds.addAll(allCheckInfo.getFormulas().get(key));
            }
            checkResultQueryParam.setRangeKeys(formKeys);
            if (allCheckInfo.isSearchByFormula()) {
                checkResultQueryParam.setMode(Mode.FORMULA);
                checkResultQueryParam.setRangeKeys(new ArrayList(formulaIds));
            }
        }
        checkResultQueryParam.setVariableMap(allCheckInfo.getContext().getVariableMap());
        JtableContext jtableContext = allCheckInfo.getContext();
        Map dimensionSet = jtableContext.getDimensionSet();
        if (allCheckInfo.isWorkFlowCheck() && !(judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(jtableContext.getFormSchemeKey(), dimensionSet))) {
            DUserActionParam dUserActionParam = allCheckInfo.getdUserActionParam();
            int checkCurrencyType = dUserActionParam.getCheckCurrencyType();
            String checkCurrencyValue = dUserActionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(jtableContext.getTaskKey(), dimensionSet, checkCurrencyType, checkCurrencyValue);
        }
        checkResultQueryParam.setDimensionCollection(this.getDimensionCollection(allCheckInfo.getContext()));
        checkResultQueryParam.setPagerInfo(allCheckInfo.getPagerInfo());
        Map<Object, Object> checkTypes = new HashMap<Integer, Boolean>();
        boolean checkDesNull = allCheckInfo.isCheckDesNull();
        List<Integer> uploadCheckTypes = allCheckInfo.getUploadCheckTypes();
        if (allCheckInfo.isWorkFlowCheck() && allCheckInfo.isAffectCommit()) {
            Iterator<Integer> noFillErrorExplain = uploadCheckTypes == null || uploadCheckTypes.size() == 0 ? null : Boolean.valueOf(checkDesNull);
            checkTypes = this.checkResultParamForReportUtil.getCheckTypesMapForReport(allCheckInfo.getContext(), allCheckInfo.getChooseTypes(), (Boolean)((Object)noFillErrorExplain), allCheckInfo.isAffectCommit(), allCheckInfo.getdUserActionParam());
            if (checkTypes.isEmpty()) {
                return new FormulaCheckReturnInfo();
            }
        } else if (uploadCheckTypes.isEmpty()) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, null);
            }
        } else if (checkDesNull) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, false);
            }
        } else {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, true);
            }
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        CheckResult checkResult = this.checkResultService.queryAllCheckResult(checkResultQueryParam, asyncTaskKey);
        List allFormKeys = allCheckInfo.getFormulas().keySet().stream().collect(Collectors.toList());
        return this.checkTransformUtil.transformCheckResult(checkResult, allCheckInfo.getContext(), allFormKeys);
    }

    private void getFormulaSchemeKeys(AllCheckInfo allCheckInfo) {
        WorkflowConfig workflowConfig;
        String checkFormulaSchemeKey;
        AsyncTask asyncTask;
        String asyncTaskKey = allCheckInfo.getAsyncTaskKey();
        String taskPoolType = "";
        if (StringUtils.isNotEmpty((String)asyncTaskKey) && (asyncTask = this.asyncTaskManager.queryTask(asyncTaskKey)) != null) {
            taskPoolType = asyncTask.getTaskPoolType();
        }
        if ((allCheckInfo.isWorkFlowCheck() || StringUtils.isNotEmpty((String)taskPoolType) && AsynctaskPoolType.ASYNCTASK_WORKFLOWEXE.getName().equals(taskPoolType)) && StringUtils.isNotEmpty((String)(checkFormulaSchemeKey = (workflowConfig = this.dataentryFlowService.queryWorkflowConfig(allCheckInfo.getContext().getFormSchemeKey())).getCheckFormulaSchemeKey()))) {
            allCheckInfo.setFormulaSchemeKeys(checkFormulaSchemeKey);
        }
    }

    private DimensionCollection getDimensionCollection(JtableContext jtableContext) {
        if (BatchSummaryConst.isBatchSummaryEntry((Map)jtableContext.getVariableMap())) {
            DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
            for (Map.Entry entry : jtableContext.getDimensionSet().entrySet()) {
                builder.setValue((String)entry.getKey(), new Object[]{((DimensionValue)entry.getValue()).getValue()});
            }
            return builder.getCollection();
        }
        return this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
    }

    private Set<String> getIgnoreItems(JtableContext jtableContext, CheckParam batchCalculateInfo) {
        HashSet<String> ignoreItems = new HashSet<String>();
        if (BatchSummaryConst.isBatchSummaryEntry((Map)jtableContext.getVariableMap())) {
            ignoreItems.add("ALL");
        }
        return ignoreItems;
    }
}

