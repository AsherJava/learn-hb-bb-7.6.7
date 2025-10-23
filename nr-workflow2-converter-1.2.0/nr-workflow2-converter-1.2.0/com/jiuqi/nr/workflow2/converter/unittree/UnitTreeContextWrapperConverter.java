/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.UnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.workflow2.converter.unittree;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.UnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class UnitTreeContextWrapperConverter
extends UnitTreeContextWrapper {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean isOpenWorkFlow(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine == null) {
            return false;
        }
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.isOpenWorkFlow(formSchemeDefine);
        }
        return this.workflowSettingsService.queryTaskWorkflowEnable(formSchemeDefine.getTaskKey());
    }

    public boolean isOpenTerminal(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine == null) {
            return false;
        }
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.isOpenTerminal(formSchemeDefine);
        }
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(formSchemeDefine.getTaskKey());
        ManualTerminationConfig manualTerminationConfig = workflowOtherSettings.getManualTerminationConfig();
        return manualTerminationConfig.isEnable();
    }

    public boolean isOpenFillReport(FormSchemeDefine formSchemeDefine) {
        return super.isOpenFillReport(formSchemeDefine);
    }

    public boolean isShowTimeDeadline(FormSchemeDefine formSchemeDefine) {
        return super.isShowTimeDeadline(formSchemeDefine);
    }

    public boolean canAddDimension(TaskDefine taskDefine) {
        return super.canAddDimension(taskDefine);
    }

    public boolean hasDimGroupConfig(TaskDefine taskDefine) {
        return super.hasDimGroupConfig(taskDefine);
    }

    public boolean canDisplayFMDMAttributes(FormSchemeDefine formSchemeDefine, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        return super.canDisplayFMDMAttributes(formSchemeDefine, entityDefine, entityQueryPloy);
    }

    public List<IFMDMAttribute> getCationFields(FormSchemeDefine formScheme, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        return super.getCationFields(formScheme, entityDefine, entityQueryPloy);
    }

    public List<IFMDMAttribute> getFMDMShowAttribute(FormSchemeDefine formScheme, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        return super.getFMDMShowAttribute(formScheme, entityDefine, entityQueryPloy);
    }

    public boolean hasFMDMFormDefine(FormSchemeDefine formScheme) {
        return super.hasFMDMFormDefine(formScheme);
    }

    public boolean canShowNodeTags(UnitTreeShowTagsOptions showTagsOptions) {
        return super.canShowNodeTags(showTagsOptions);
    }

    public boolean hasUnitAuditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        return super.hasUnitAuditOperation(entityId, rowKey, periodEntityId, periodString);
    }

    public boolean hasUnitEditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        return super.hasUnitEditOperation(entityId, rowKey, periodEntityId, periodString);
    }

    public String openTerminalRole(TaskDefine taskDefine) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskDefine.getKey())) {
            return super.openTerminalRole(taskDefine);
        }
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskDefine.getKey());
        ManualTerminationConfig manualTerminationConfig = workflowOtherSettings.getManualTerminationConfig();
        return manualTerminationConfig.isEnable() ? manualTerminationConfig.getRole() : null;
    }

    public UnitTreeGroupField getDimGroupFieldConfig(TaskDefine taskDefine) {
        return super.getDimGroupFieldConfig(taskDefine);
    }

    public IEntityRefer getBBLXEntityRefer(IEntityDefine entityDefine) {
        return super.getBBLXEntityRefer(entityDefine);
    }

    public IconSourceScheme getBBLXIConSourceScheme(IEntityDefine entityDefine) {
        return super.getBBLXIConSourceScheme(entityDefine);
    }

    public List<String> getReportEntityDimensionName(FormSchemeDefine formSchemeDefine) {
        return super.getReportEntityDimensionName(formSchemeDefine);
    }

    public String getDimensionName(String entityId) {
        return super.getDimensionName(entityId);
    }

    public DimensionValueSet buildDimensionValueSet(IUnitTreeContext ctx) {
        return super.buildDimensionValueSet(ctx);
    }

    public boolean isOpenADJUST(TaskDefine taskDefine) {
        return super.isOpenADJUST(taskDefine);
    }

    public boolean canLoadWorkFlowState(IUnitTreeContext ctx) {
        return super.canLoadWorkFlowState(ctx);
    }

    public boolean isTreeExpandAllLevel(IUnitTreeContext context) {
        return super.isTreeExpandAllLevel(context);
    }

    public String[] queryDimAttributeCode(IUnitTreeContext context) {
        return super.queryDimAttributeCode(context);
    }

    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, List<DataDimension> dataSchemeDimension) {
        return super.isCorporate(taskDefine, dimension, dataSchemeDimension);
    }
}

