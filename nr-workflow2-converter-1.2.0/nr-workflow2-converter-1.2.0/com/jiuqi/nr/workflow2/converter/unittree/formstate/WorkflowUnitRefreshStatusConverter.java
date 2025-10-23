/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeIconAndColor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.paramInfo.FormUploadState
 *  com.jiuqi.nr.dataentry.service.IRefreshStatus
 *  com.jiuqi.nr.dataentry.util.Consts$RefreshStatusType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
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
 *  com.jiuqi.xlib.runtime.Assert
 */
package com.jiuqi.nr.workflow2.converter.unittree.formstate;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeIconAndColor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.paramInfo.FormUploadState;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.workflow2.converter.unittree.DataEntryUnitTreeStateConverter;
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
import com.jiuqi.xlib.runtime.Assert;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowUnitRefreshStatusConverter
implements IRefreshStatus<FormUploadState> {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private TreeIconAndColor treeIconAndColor;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean getEnable(String taskKey, String formSchemeKey) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return false;
        }
        Assert.notNull((Object)taskKey, (String)"taskKey must not be null!");
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskKey);
        return !workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION) && !workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR);
    }

    public String getName() {
        return "formUpload";
    }

    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.UNIT;
    }

    public FormUploadState getStatus(JtableContext context) {
        boolean isIconEnable;
        IBusinessKeyCollection businessKeyCollection;
        ProcessBatchRunPara batchRunPara;
        Map dimensionSet = context.getDimensionSet();
        DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
        String period = dimensionValue.getValue();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        HashMap<String, String> formOrFormGroupState = new HashMap<String, String>();
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(context.getTaskKey());
            batchRunPara.setPeriod(period);
            List formDefines = this.runTimeViewController.listFormByFormScheme(context.getFormSchemeKey());
            batchRunPara.setReportDimensions(this.buildFormReportDimension(context, formDefines));
            businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            IBizObjectOperateResult operateResult = this.processQueryService.queryInstanceState((IProcessRunPara)batchRunPara, businessKeyCollection);
            Iterable businessObjects = operateResult.getBusinessObjects();
            for (IBusinessObject businessObject : businessObjects) {
                FormObject formObject = (FormObject)businessObject;
                IOperateResult stateResult = operateResult.getResult((Object)businessObject);
                if (!stateResult.isSuccessful()) continue;
                IProcessStatus processStatus = (IProcessStatus)stateResult.getResult();
                formOrFormGroupState.put(formObject.getFormKey(), DataEntryUnitTreeStateConverter.transferToOldStateCode(processStatus.getCode()));
            }
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(context.getTaskKey());
            batchRunPara.setPeriod(period);
            List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(context.getFormSchemeKey());
            batchRunPara.setReportDimensions(this.buildFormGroupReportDimension(context, formGroupDefines));
            businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            IBizObjectOperateResult operateResult = this.processQueryService.queryInstanceState((IProcessRunPara)batchRunPara, businessKeyCollection);
            Iterable businessObjects = operateResult.getBusinessObjects();
            for (IBusinessObject businessObject : businessObjects) {
                FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                IOperateResult stateResult = operateResult.getResult((Object)businessObject);
                if (!stateResult.isSuccessful()) continue;
                IProcessStatus processStatus = (IProcessStatus)stateResult.getResult();
                formOrFormGroupState.put(formGroupObject.getFormGroupKey(), DataEntryUnitTreeStateConverter.transferToOldStateCode(processStatus.getCode()));
            }
        }
        Map stateDisMap = (isIconEnable = this.treeIconAndColor.isIcon()) ? this.treeIconAndColor.getBase64IconMap() : this.treeIconAndColor.getColorMap();
        FormUploadState formUploadState = new FormUploadState();
        formUploadState.setFormsState(formOrFormGroupState);
        formUploadState.setIsIcon(isIconEnable);
        formUploadState.setStateDisMap(stateDisMap);
        return formUploadState;
    }

    private Set<ProcessRangeDims> buildFormReportDimension(JtableContext context, List<FormDefine> formDefines) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : taskDefine.getDw();
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        Map dimensionSet = context.getDimensionSet();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        String unitKey = ((DimensionValue)dimensionSet.get(dimensionName)).getValue();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.ONE);
        entityRangeDims.setDimensionValue(unitKey);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        rangeDims.add(formRangeDims);
        DimensionValue adjustDim = (DimensionValue)dimensionSet.get("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.getValue());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormGroupReportDimension(JtableContext context, List<FormGroupDefine> formGroupDefines) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : taskDefine.getDw();
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        Map dimensionSet = context.getDimensionSet();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        String unitKey = ((DimensionValue)dimensionSet.get(dimensionName)).getValue();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.ONE);
        entityRangeDims.setDimensionValue(unitKey);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        rangeDims.add(formRangeDims);
        DimensionValue adjustDim = (DimensionValue)dimensionSet.get("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.getValue());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }
}

