/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultData
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.dataentry.bean.RefreshStatusParam;
import com.jiuqi.nr.dataentry.service.IRefreshStatusService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchCheckResultFilter
implements ICKDQueryCKRFilter {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckResultFilter.class);
    private JtableContext context;
    private IRunTimeViewController runTimeViewController;

    public BatchCheckResultFilter(JtableContext context, IRunTimeViewController runTimeViewController) {
        this.context = context;
        this.runTimeViewController = runTimeViewController;
    }

    public CheckResult filter(CheckResult source) {
        CheckResult result = new CheckResult();
        List dimensionList = source.getDimensionList();
        result.setMessage(source.getMessage());
        result.setTotalCount(source.getTotalCount());
        result.setCheckTypeCountMap(source.getCheckTypeCountMap());
        result.setDimensionList(dimensionList);
        ArrayList<CheckResultData> resultData = new ArrayList<CheckResultData>();
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        List checkResultData = source.getResultData();
        IWorkflow workflow = (IWorkflow)BeanUtil.getBean(IWorkflow.class);
        WorkFlowType workFlowType = workflow.queryStartType(this.context.getFormSchemeKey());
        boolean entityFlowType = workFlowType == WorkFlowType.ENTITY || workFlowType == WorkFlowType.GROUP || workFlowType == WorkFlowType.FORM;
        WorkflowConfig workflowConfig = dataFlowService.queryWorkflowConfig(this.context.getFormSchemeKey());
        if (entityFlowType && workflowConfig.isFlowStarted()) {
            IQueryUploadStateService queryUploadStateService = (IQueryUploadStateService)BeanUtil.getBean(IQueryUploadStateService.class);
            HashMap<DimensionValueSet, UploadStateNew> UploadStateMap = new HashMap<DimensionValueSet, UploadStateNew>();
            ArrayList<String> dimNames = new ArrayList<String>();
            DimensionValueSet mergeDim = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.mergeDimension((List)dimensionList));
            if (workFlowType == WorkFlowType.ENTITY) {
                List uploadStateNews = queryUploadStateService.queryUploadStates(this.context.getFormSchemeKey(), mergeDim, new ArrayList(), new ArrayList());
                for (UploadStateNew uploadStateNew : uploadStateNews) {
                    UploadStateMap.put(uploadStateNew.getEntities(), uploadStateNew);
                    if (dimNames.size() != 0) continue;
                    for (String string : DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadStateNew.getEntities()).keySet()) {
                        dimNames.add(string);
                    }
                }
            } else {
                List uploadStateNews;
                HashSet<String> formSet = new HashSet<String>();
                for (CheckResultData data : checkResultData) {
                    formSet.add(data.getFormulaData().getFormKey());
                }
                if (workFlowType == WorkFlowType.FORM) {
                    uploadStateNews = queryUploadStateService.queryUploadStates(this.context.getFormSchemeKey(), mergeDim, new ArrayList(formSet), new ArrayList());
                } else {
                    HashSet groupSet = new HashSet();
                    for (String string : formSet) {
                        List formGroups = this.runTimeViewController.getFormGroupsByFormKey(string);
                        if (formGroups.size() <= 0) continue;
                        groupSet.add(((FormGroupDefine)formGroups.get(0)).getKey());
                    }
                    uploadStateNews = queryUploadStateService.queryUploadStates(this.context.getFormSchemeKey(), mergeDim, new ArrayList(), new ArrayList(groupSet));
                }
                for (UploadStateNew uploadStateNew : uploadStateNews) {
                    UploadStateMap.put(uploadStateNew.getEntities(), uploadStateNew);
                    if (dimNames.size() != 0) continue;
                    for (String key : DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadStateNew.getEntities()).keySet()) {
                        dimNames.add(key);
                    }
                }
            }
            IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
            EntityViewData dwEntity = jtableParamService.getDwEntity(this.context.getFormSchemeKey());
            String mainDim = dwEntity.getDimensionName();
            JtableContext jtableContext = new JtableContext(this.context);
            ((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).setValue("");
            RefreshStatusParam refreshStatusParam = new RefreshStatusParam();
            refreshStatusParam.setForm(false);
            Map<Object, Object> status = new HashMap();
            IRefreshStatusService refreshStatusService = (IRefreshStatusService)BeanUtil.getBean(IRefreshStatusService.class);
            for (CheckResultData data : checkResultData) {
                if (!((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).equals((Object)data.getUnitCode())) {
                    ((DimensionValue)jtableContext.getDimensionSet().get(mainDim)).setValue(data.getUnitCode());
                    refreshStatusParam.setContext(jtableContext);
                    status = refreshStatusService.getStatus(refreshStatusParam);
                }
                boolean writable = true;
                UploadStateNew uploadStateNew = null;
                ActionStateBean actionState = null;
                Map sourceDimMap = (Map)dimensionList.get(data.getDimensionIndex());
                HashMap dimMap = new HashMap();
                for (String key : dimNames) {
                    dimMap.put(key, sourceDimMap.get(key));
                }
                if (workFlowType == WorkFlowType.ENTITY) {
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimMap);
                    uploadStateNew = (UploadStateNew)UploadStateMap.get(dimensionValueSet);
                } else if (workFlowType == WorkFlowType.FORM) {
                    DimensionValue formID = new DimensionValue();
                    formID.setValue(data.getFormulaData().getFormKey());
                    formID.setName("FORMID");
                    dimMap.put("FORMID", formID);
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimMap);
                    uploadStateNew = (UploadStateNew)UploadStateMap.get(dimensionValueSet);
                } else {
                    if ("00000000-0000-0000-0000-000000000000".equals(data.getFormulaData().getFormKey())) {
                        resultData.add(data);
                        continue;
                    }
                    String groupKey = null;
                    List formGroups = this.runTimeViewController.getFormGroupsByFormKey(data.getFormulaData().getFormKey());
                    if (formGroups.size() > 0) {
                        groupKey = ((FormGroupDefine)formGroups.get(0)).getKey();
                    }
                    if (groupKey == null) {
                        logger.error("\u5206\u7ec4\u4e0a\u62a5\u6d41\u7a0b\uff0c\u4f46\u8868\u5355" + data.getFormulaData().getFormKey() + "\u65e0\u5206\u7ec4\u3002\u8be5\u8868\u5355\u4e0b\u7684\u5ba1\u6838\u9519\u8bef\u5c06\u65e0\u6cd5\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e");
                        continue;
                    }
                    DimensionValue formID = new DimensionValue();
                    formID.setValue(groupKey);
                    formID.setName("FORMID");
                    dimMap.put("FORMID", formID);
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimMap);
                    uploadStateNew = (UploadStateNew)UploadStateMap.get(dimensionValueSet);
                }
                if (uploadStateNew != null) {
                    actionState = uploadStateNew.getActionStateBean();
                }
                if (actionState != null && (UploadState.UPLOADED.name().equals(actionState.getCode()) || UploadState.CONFIRMED.name().equals(actionState.getCode()) || UploadState.SUBMITED.name().equals(actionState.getCode()))) {
                    writable = false;
                }
                if (status.containsKey("formReject") && !((Set)status.get("formReject")).isEmpty()) {
                    writable = ((Set)status.get("formReject")).contains(data.getFormulaData().getFormKey());
                }
                if (status.containsKey("formReject-V2") && !((Map)status.get("formReject-V2")).isEmpty()) {
                    writable = ((Map)status.get("formReject-V2")).containsKey(data.getFormulaData().getFormKey());
                }
                if (!writable) continue;
                resultData.add(data);
            }
            result.setResultData(resultData);
        } else {
            result.setResultData(checkResultData);
        }
        return result;
    }
}

