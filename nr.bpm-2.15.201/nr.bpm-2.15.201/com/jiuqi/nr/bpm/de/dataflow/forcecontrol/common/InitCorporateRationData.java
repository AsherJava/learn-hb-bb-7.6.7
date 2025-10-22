/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import java.util.List;

public class InitCorporateRationData {
    private DimensionUtil dimensionUtil;
    private IEntityTable currentEntityTable;
    private IEntityTable parentEntityTable;
    private String corporateId;
    private String corporateDimName;

    public InitCorporateRationData(String period, String corporateValue, String formSchemeKey) {
        DeEntityHelper entityHelper = (DeEntityHelper)SpringBeanUtils.getBean(DeEntityHelper.class);
        IWorkFlowDimensionBuilder workFlowDimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanUtils.getBean(IWorkFlowDimensionBuilder.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.dimensionUtil = (DimensionUtil)SpringBeanUtils.getBean(DimensionUtil.class);
        EntityViewDefine entityView = this.dimensionUtil.getEntityView(corporateValue);
        this.parentEntityTable = entityHelper.getEntityTableByEntityView(entityView, formSchemeKey, period, AuthorityType.None);
        this.currentEntityTable = entityHelper.getEntityTable(corporateValue, formSchemeKey, period);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine task = runTimeViewController.getTask(formScheme.getTaskKey());
        this.corporateId = workFlowDimensionBuilder.getCorporateEntityId(task);
        this.corporateDimName = workFlowDimensionBuilder.getCorporateEntityCode(task);
    }

    public void initData(ForceControlInfo forceControlInfo) {
        DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
        FixedDimensionValue dwDimensionValue = dimensionCombination.getDWDimensionValue();
        String unitKey = dwDimensionValue.getValue().toString();
        String dimName = dwDimensionValue.getName();
        FixedDimensionValue periodDimensionValue = dimensionCombination.getPeriodDimensionValue();
        String period = periodDimensionValue.getValue().toString();
        IEntityRow specialCorporateTable = this.parentEntityTable.findByEntityKey(unitKey);
        if (specialCorporateTable != null) {
            String parentCorporateValue = null;
            if (this.corporateId != null) {
                IEntityRow parentEntity = this.parentEntityTable.findByEntityKey(specialCorporateTable.getParentEntityKey());
                if (parentEntity != null) {
                    parentCorporateValue = parentEntity.getAsString(this.corporateId);
                    forceControlInfo.setParentKeyWithCorporateValue(specialCorporateTable.getParentEntityKey() + ";" + parentCorporateValue);
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    dimensionValueSet.setValue(dimName, (Object)specialCorporateTable.getParentEntityKey());
                    dimensionValueSet.setValue(this.corporateDimName, (Object)parentCorporateValue);
                    forceControlInfo.setParentDimensionValue(dimensionValueSet);
                } else {
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    dimensionValueSet.setValue(dimName, (Object)specialCorporateTable.getParentEntityKey());
                    forceControlInfo.setParentDimensionValue(dimensionValueSet);
                }
            } else {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue("DATATIME", (Object)period);
                dimensionValueSet.setValue(dimName, (Object)specialCorporateTable.getParentEntityKey());
                forceControlInfo.setParentDimensionValue(dimensionValueSet);
            }
            forceControlInfo.setParentKey(specialCorporateTable.getParentEntityKey());
            forceControlInfo.setParentEntityRow(this.parentEntityTable.findByEntityKey(specialCorporateTable.getParentEntityKey()));
        } else {
            forceControlInfo.setParentKeyWithCorporateValue(null);
            forceControlInfo.setParentDimensionValue(null);
            forceControlInfo.setParentEntityRow(null);
            forceControlInfo.setParentKey(null);
        }
        int maxDepthByEntityKey = this.parentEntityTable.getMaxDepthByEntityKey(unitKey);
        forceControlInfo.setLevel(maxDepthByEntityKey);
        IEntityRow currentCorporateTable = this.currentEntityTable.findByEntityKey(unitKey);
        if (currentCorporateTable != null) {
            forceControlInfo.setUnitCode(currentCorporateTable.getCode());
            forceControlInfo.setUnitName(currentCorporateTable.getTitle());
        }
    }

    public void batchInitData(List<ForceControlInfo> forceControlInfos) {
        for (ForceControlInfo forceControlInfo : forceControlInfos) {
            this.initData(forceControlInfo);
        }
    }
}

