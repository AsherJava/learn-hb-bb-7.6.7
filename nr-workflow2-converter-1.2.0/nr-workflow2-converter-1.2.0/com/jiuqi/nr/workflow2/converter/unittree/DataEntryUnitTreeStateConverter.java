/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 *  com.jiuqi.nr.workflow2.service.para.ProcessRunPara
 */
package com.jiuqi.nr.workflow2.converter.unittree;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DataEntryUnitTreeStateConverter
extends TreeState {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public Map<DimensionValueSet, ActionStateBean> getWorkflowUploadState(DimensionValueSet dimSet, String formKey, String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.getWorkflowUploadState(dimSet, formKey, formSchemeKey);
        }
        ProcessRunPara processRunPara = new ProcessRunPara();
        processRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        processRunPara.setPeriod(dimSet.getValue("DATATIME").toString());
        IBizObjectOperateResult operateResult = this.processQueryService.queryUnitState((IProcessRunPara)processRunPara, this.buildBusinessCollection(formSchemeDefine, dimSet));
        if (operateResult == null) {
            return new HashMap<DimensionValueSet, ActionStateBean>();
        }
        Iterable businessObjects = operateResult.getBusinessObjects();
        HashMap<DimensionValueSet, ActionStateBean> result = new HashMap<DimensionValueSet, ActionStateBean>();
        for (IBusinessObject businessObject : businessObjects) {
            IOperateResult stateResult = operateResult.getResult((Object)businessObject);
            if (!stateResult.isSuccessful()) continue;
            IProcessStatus processStatus = (IProcessStatus)stateResult.getResult();
            ActionStateBean actionStateBean = new ActionStateBean();
            actionStateBean.setCode(DataEntryUnitTreeStateConverter.transferToOldStateCode(processStatus.getCode()));
            actionStateBean.setTitile(processStatus.getAlias());
            result.put(businessObject.getDimensions().toDimensionValueSet(), actionStateBean);
        }
        return result;
    }

    public List<WorkFlowTreeState> getWorkFlowActions(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.getWorkFlowActions(formSchemeKey);
        }
        List processStatuses = this.processMetaDataService.queryAllStatus(formSchemeDefine.getTaskKey());
        return processStatuses.stream().map(status -> {
            WorkFlowTreeState workFlowTreeState = new WorkFlowTreeState();
            workFlowTreeState.setCode(DataEntryUnitTreeStateConverter.transferToOldStateCode(status.getCode()));
            workFlowTreeState.setTitle(status.getAlias());
            return workFlowTreeState;
        }).collect(Collectors.toList());
    }

    private IBusinessKeyCollection buildBusinessCollection(FormSchemeDefine formSchemeDefine, DimensionValueSet dimSet) {
        DataDimension periodDimension = this.reportDimensionHelper.getReportPeriodDimension(formSchemeDefine.getTaskKey());
        String periodDimensionName = this.reportDimensionHelper.getDimensionName(periodDimension);
        ProcessBatchRunPara runPara = new ProcessBatchRunPara();
        runPara.setTaskKey(formSchemeDefine.getTaskKey());
        runPara.setPeriod(dimSet.getValue("DATATIME").toString());
        runPara.setReportDimensions(new HashSet());
        DataDimension unitDimension = this.reportDimensionHelper.getReportUnitDimension(formSchemeDefine.getTaskKey());
        String unitDimensionName = this.reportDimensionHelper.getDimensionName(unitDimension);
        ProcessRangeDims unitDims = new ProcessRangeDims();
        unitDims.setDimensionName(unitDimensionName);
        unitDims.setRangeDims((List)dimSet.getValue(unitDimensionName));
        unitDims.setRangeType(EProcessRangeDimType.RANGE);
        unitDims.setDimensionKey(unitDimension.getDimKey());
        runPara.getReportDimensions().add(unitDims);
        ProcessRangeDims periodDim = new ProcessRangeDims();
        periodDim.setDimensionKey(periodDimension.getDimKey());
        periodDim.setRangeType(EProcessRangeDimType.ONE);
        periodDim.setDimensionValue(dimSet.getValue(periodDimensionName).toString());
        periodDim.setDimensionName(periodDimensionName);
        runPara.getReportDimensions().add(periodDim);
        return this.processDimensionsBuilder.buildUnitDimensionCollection(runPara);
    }

    public static String transferToOldStateCode(String unitStateCode) {
        if (unitStateCode.equals(FormRejectStatus.rejected.value)) {
            return UploadState.REJECTED.toString();
        }
        switch (unitStateCode) {
            case "unsubmited": {
                return UploadState.ORIGINAL_SUBMIT.toString();
            }
            case "unreported": {
                return UploadState.ORIGINAL_UPLOAD.toString();
            }
            case "submited": {
                return UploadState.SUBMITED.toString();
            }
            case "part-submited": {
                return UploadState.PART_SUBMITED.toString();
            }
            case "reported": {
                return UploadState.UPLOADED.toString();
            }
            case "part-reported": {
                return UploadState.PART_UPLOADED.toString();
            }
            case "backed": {
                return UploadState.RETURNED.toString();
            }
            case "rejected": {
                return UploadState.REJECTED.toString();
            }
            case "confirmed": {
                return UploadState.CONFIRMED.toString();
            }
            case "part-confirmed": {
                return UploadState.PART_CONFIRMED.toString();
            }
        }
        return UploadState.ORIGINAL.toString();
    }
}

