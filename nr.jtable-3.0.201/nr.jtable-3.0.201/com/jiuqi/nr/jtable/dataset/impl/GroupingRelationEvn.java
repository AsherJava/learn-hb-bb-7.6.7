/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.List;

public class GroupingRelationEvn {
    private IJtableContextService jtableContextService;
    private IJtableParamService jtableParamService;
    private IJtableDataEngineService jtableDataEngineService;
    private String targetKey = "";
    private EntityViewData entityViewData = null;
    private String unitDimension = "";
    private List<Integer> unitLevels = new ArrayList<Integer>();
    private String periodCode = "";
    private String periodDimension = "";
    private List<Integer> periodLevels = new ArrayList<Integer>();
    private IPeriodEntityAdapter periodEntityAdapter;
    private IPeriodAdapter periodAdapter;

    public GroupingRelationEvn(RegionData region, RegionQueryInfo regionQueryInfo) {
        this.jtableContextService = (IJtableContextService)BeanUtil.getBean(IJtableContextService.class);
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        JtableContext jtableContext = regionQueryInfo.getContext();
        DimensionValueSet dimensionValueSet = this.jtableContextService.getDimensionValueSet(jtableContext);
        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
        if (restructureInfo == null) {
            return;
        }
        RegionGradeInfo grade = restructureInfo.getGrade();
        if (grade == null) {
            return;
        }
        List<GradeCellInfo> gradeCells = grade.getGradeCells();
        this.entityViewData = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        this.targetKey = dimensionValueSet.getValue(this.entityViewData.getDimensionName()).toString();
        this.unitDimension = this.entityViewData.getDimensionName();
        for (GradeCellInfo gradeCell : gradeCells) {
            if (!this.unitDimension.equals(gradeCell.getZbid())) continue;
            restructureInfo.setUnitAutoSum(true);
            this.unitLevels = gradeCell.getLevels();
        }
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        this.periodCode = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        this.periodDimension = dataTimeEntity.getDimensionName();
        this.periodAdapter = this.periodEntityAdapter.getPeriodProvider(dataTimeEntity.getKey());
        for (GradeCellInfo gradeCell : gradeCells) {
            if (!this.periodDimension.equals(gradeCell.getZbid())) continue;
            restructureInfo.setPeriodAutoSum(true);
            this.periodLevels = gradeCell.getLevels();
        }
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public EntityViewData getEntityViewData() {
        return this.entityViewData;
    }

    public String getUnitDimension() {
        return this.unitDimension;
    }

    public List<Integer> getUnitLevels() {
        return this.unitLevels;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public String getPeriodDimension() {
        return this.periodDimension;
    }

    public List<Integer> getPeriodLevels() {
        return this.periodLevels;
    }

    public String getUnitFieldKey(String tableName) {
        return this.jtableDataEngineService.getDimensionFieldKey(tableName, this.unitDimension);
    }

    public String getPeriodFieldKey(String tableName) {
        return this.jtableDataEngineService.getDimensionFieldKey(tableName, this.periodDimension);
    }

    public EntityData getUnitInfo(String unitId) {
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityViewKey(this.entityViewData.getKey());
        entityQueryByKeyInfo.setEntityKey(unitId);
        EntityByKeyReturnInfo entityDataByKey = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        return entityDataByKey.getEntity();
    }

    public String getPeriodTitle(String period) {
        return this.periodAdapter.getPeriodTitle(period);
    }

    public IPeriodAdapter getPeriodAdapter() {
        return this.periodAdapter;
    }
}

