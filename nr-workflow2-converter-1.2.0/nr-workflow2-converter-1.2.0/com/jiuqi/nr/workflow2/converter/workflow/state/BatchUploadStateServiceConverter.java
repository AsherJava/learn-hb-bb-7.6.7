/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImpl
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 */
package com.jiuqi.nr.workflow2.converter.workflow.state;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImpl;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class BatchUploadStateServiceConverter
extends BatchUploadStateServiceImpl {
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;

    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKey, List<String> groupkeys) {
        ProcessRangeDims formRangeDims;
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formScheme.getTaskKey())) {
            return super.queryUploadStateNew(formScheme, dimensionValueSet, formKey, groupkeys);
        }
        Optional firstFormKey = formKey.stream().findFirst();
        if (firstFormKey.isPresent() && ((String)firstFormKey.get()).equals("00000000-0000-0000-0000-000000000000")) {
            return new ArrayList<UploadStateNew>();
        }
        TaskDefine taskDefine = this.runTimeViewController.getTask(formScheme.getTaskKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : taskDefine.getDw();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        Object unitInfoObject = dimensionValueSet.getValue(dimensionName);
        String period = dimensionValueSet.getValue("DATATIME").toString();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formScheme.getTaskKey());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        if (unitInfoObject instanceof List) {
            entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            entityRangeDims.setRangeDims((List)unitInfoObject);
        } else if (unitInfoObject instanceof String) {
            entityRangeDims.setRangeType(EProcessRangeDimType.ONE);
            entityRangeDims.setDimensionValue(unitInfoObject.toString());
        }
        rangeDims.add(entityRangeDims);
        Object adjustDim = dimensionValueSet.getValue("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.toString());
            rangeDims.add(adjustOneDims);
        }
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formRangeDims.setRangeDims(formKey);
            rangeDims.add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formRangeDims.setRangeDims(groupkeys);
            rangeDims.add(formRangeDims);
        }
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(formScheme.getTaskKey());
        batchRunPara.setPeriod(period);
        batchRunPara.setReportDimensions(rangeDims);
        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
        IBizObjectOperateResult operateResult = this.processQueryService.queryInstanceState((IProcessRunPara)batchRunPara, businessKeyCollection);
        Iterable businessObjects = operateResult.getBusinessObjects();
        ArrayList<UploadStateNew> result = new ArrayList<UploadStateNew>();
        for (IBusinessObject businessObject : businessObjects) {
            IOperateResult statusResult = operateResult.getResult((Object)businessObject);
            if (statusResult == null || !statusResult.isSuccessful()) continue;
            IProcessStatus processStatus = (IProcessStatus)statusResult.getResult();
            UploadStateNew uploadState = new UploadStateNew();
            DimensionValueSet dim = businessObject.getDimensions().toDimensionValueSet();
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                FormObject formObject = (FormObject)businessObject;
                uploadState.setFormId(formObject.getFormKey());
                dim.setValue("FORMID", (Object)formObject.getFormKey());
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                uploadState.setFormId(formGroupObject.getFormGroupKey());
                dim.setValue("FORMID", (Object)formGroupObject.getFormGroupKey());
            }
            uploadState.setEntities(dim);
            ActionStateBean actionStateBean = new ActionStateBean();
            actionStateBean.setCode(this.transferToUploadStateEnum(processStatus.getCode()));
            actionStateBean.setTitile(processStatus.getTitle());
            actionStateBean.setTaskKey(formScheme.getTaskKey());
            uploadState.setActionStateBean(actionStateBean);
            result.add(uploadState);
        }
        return result;
    }

    private String transferToUploadStateEnum(String processStatus) {
        switch (processStatus) {
            case "unsubmited": {
                return UploadStateEnum.ORIGINAL_SUBMIT.getCode();
            }
            case "submited": {
                return UploadStateEnum.SUBMITED.getCode();
            }
            case "backed": {
                return UploadStateEnum.RETURNED.getCode();
            }
            case "unreported": {
                return UploadStateEnum.ORIGINAL_UPLOAD.getCode();
            }
            case "reported": {
                return UploadStateEnum.UPLOADED.getCode();
            }
            case "rejected": {
                return UploadStateEnum.REJECTED.getCode();
            }
            case "confirmed": {
                return UploadStateEnum.CONFIRMED.getCode();
            }
        }
        return UploadStateEnum.ORIGINAL.getCode();
    }
}

