/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Map;

public class EntityCheckVersionObjectInfo {
    String taskKey;
    String fromSchemeKey;
    String period;
    EntityViewData masterEntityView;
    EntityViewData periodEntityView;
    TableModelDefine tableModelDefine;
    IEntityModel entityModel;
    EntityViewDefine entityView;
    IEntityTable entityTable;
    Map<String, IFMDMData> fmdmDataMap;
    String expressionFormula;
    Map<String, Object[]> expressionFormulaMap;
    int matchingType;

    public IEntityTable getEntityTable() {
        return this.entityTable;
    }

    public void setEntityTable(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public int getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(int matchingType) {
        this.matchingType = matchingType;
    }

    public Map<String, Object[]> getExpressionFormulaMap() {
        return this.expressionFormulaMap;
    }

    public void setExpressionFormulaMap(Map<String, Object[]> expressionFormulaMap) {
        this.expressionFormulaMap = expressionFormulaMap;
    }

    public String getExpressionFormula() {
        return this.expressionFormula;
    }

    public void setExpressionFormula(String expressionFormula) {
        this.expressionFormula = expressionFormula;
    }

    public Map<String, IFMDMData> getFmdmDataMap() {
        return this.fmdmDataMap;
    }

    public void setFmdmDataMap(Map<String, IFMDMData> fmdmDataMap) {
        this.fmdmDataMap = fmdmDataMap;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFromSchemeKey() {
        return this.fromSchemeKey;
    }

    public void setFromSchemeKey(String fromSchemeKey) {
        this.fromSchemeKey = fromSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public EntityViewData getMasterEntityView() {
        return this.masterEntityView;
    }

    public void setMasterEntityView(EntityViewData masterEntityView) {
        this.masterEntityView = masterEntityView;
    }

    public EntityViewData getPeriodEntityView() {
        return this.periodEntityView;
    }

    public void setPeriodEntityView(EntityViewData periodEntityView) {
        this.periodEntityView = periodEntityView;
    }

    public IEntityModel getEntityModel() {
        return this.entityModel;
    }

    public void setEntityModel(IEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public EntityViewDefine getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityViewDefine entityView) {
        this.entityView = entityView;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }
}

