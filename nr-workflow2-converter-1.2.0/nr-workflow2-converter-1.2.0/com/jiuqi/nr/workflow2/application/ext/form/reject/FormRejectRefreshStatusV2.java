/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.service.IRefreshStatus
 *  com.jiuqi.nr.dataentry.util.Consts$RefreshStatusType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity
 *  com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper
 *  com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRunPara
 */
package com.jiuqi.nr.workflow2.application.ext.form.reject;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FormRejectRefreshStatusV2
implements IRefreshStatus<Map<String, String>> {
    public static final String NAME = "formReject-V2";
    private static final String icon_key_tui_hui = "icon-_Tyituihui";
    private static final String icon_key_suo_ding = "icon-18_SHU_A_NR_jiaobiaosuoding";
    private static final String icon_key_group_tui_hui = "icon-16_SHU_A_NR_yituihui";
    @Value(value="${jiuqi.nr.workflow.version:1.0}")
    private String workflowVersion;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessRuntimeParamHelper runtimeParamHelper;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IFormRejectQueryService formRejectQueryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected IFormRejectJudgeHelper judgeHelper;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean getEnable(String taskKey, String formSchemeKey) {
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskKey);
        boolean taskAndEngineVersion10 = this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey);
        return !taskAndEngineVersion10 && WorkflowObjectType.MD_WITH_SFR == workflowObjectType;
    }

    public String getName() {
        return NAME;
    }

    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.FORM;
    }

    public Map<String, String> getStatus(JtableContext context) throws Exception {
        String taskKey = context.getTaskKey();
        Map dimensionValueMap = context.getDimensionSet();
        DimensionCombination dimensionCombination = this.getDimensionCombination(taskKey, dimensionValueMap);
        FixedDimensionValue periodDimensionValue = dimensionCombination.getPeriodDimensionValue();
        FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(taskKey, periodDimensionValue.getValue().toString());
        ProcessRunPara processRunPara = new ProcessRunPara();
        processRunPara.setTaskKey(taskKey);
        processRunPara.setPeriod(periodDimensionValue.getValue().toString());
        HashMap<String, String> map = new HashMap<String, String>();
        BusinessKey businessKey = new BusinessKey(formScheme.getTaskKey(), (IBusinessObject)new DimensionObject(dimensionCombination));
        if (this.judgeHelper.canShowFormRejectIcon((IProcessRunPara)processRunPara, (IBusinessKey)businessKey)) {
            HashMap<String, String> formGroupMap = new HashMap<String, String>();
            List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(formScheme.getKey());
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                List formDefines = this.runTimeViewController.listFormByGroup(formGroupDefine.getKey(), formScheme.getKey());
                for (FormDefine formDefine : formDefines) {
                    formGroupMap.put(formDefine.getKey(), formGroupDefine.getKey());
                }
            }
            List formRejectRecords = this.formRejectQueryService.queryAllFormRecordsInUnit(taskKey, periodDimensionValue.getValue().toString(), dimensionCombination);
            for (IRejectFormRecordEntity record : formRejectRecords) {
                String formKey = record.getFormObject().getFormKey();
                if (FormRejectStatus.rejected == record.getStatus()) {
                    map.put(formKey, icon_key_tui_hui);
                    map.put((String)formGroupMap.get(formKey), icon_key_group_tui_hui);
                    continue;
                }
                if (FormRejectStatus.locked != record.getStatus()) continue;
            }
        }
        return map;
    }

    private DimensionCombination getDimensionCombination(String taskKey, Map<String, DimensionValue> dimensionValueMap) {
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List reportDimensions = this.reportDimensionHelper.getAllReportDimensions(taskKey);
        for (DataDimension dimension : reportDimensions) {
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            DimensionValue dimensionValue = dimensionValueMap.get(dimensionName);
            if (dimensionValue == null) continue;
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                IEntityDefine entityDefine = this.runtimeParamHelper.getProcessEntityDefinition(taskKey);
                combination.setDWValue(dimensionName, entityDefine.getId(), (Object)dimensionValue.getValue());
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                combination.setValue(dimensionName, dimension.getDimKey(), (Object)dimensionValue.getValue());
                continue;
            }
            combination.setValue(dimensionName, dimension.getDimKey(), (Object)dimensionValue.getValue());
        }
        return combination;
    }
}

