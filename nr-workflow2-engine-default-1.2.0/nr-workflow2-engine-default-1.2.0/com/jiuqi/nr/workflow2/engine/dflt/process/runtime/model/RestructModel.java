/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model;

import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DesignModel;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RestructModel {
    private DesignModel targetModel;
    private DesignModel originalModel;
    private RestructMode restructMode;
    private DesignTableModelDefine tableModelToUpdate;
    private List<DesignColumnModelDefine> columnModelsToAdd;
    private List<DesignColumnModelDefine> columnModelsToUpdate;
    private List<DesignColumnModelDefine> columnModelsToRemove;
    private List<DesignIndexModelDefine> indexModelsToAdd;
    private List<DesignIndexModelDefine> indexModelsToUpdate;
    private List<String> indexModelsToRemove;

    RestructModel(DesignModel targetModel, DesignModel originalModel) {
        if (originalModel.exists()) {
            this.targetModel = targetModel;
            this.originalModel = originalModel;
            this.columnModelsToAdd = new ArrayList<DesignColumnModelDefine>();
            this.columnModelsToUpdate = new ArrayList<DesignColumnModelDefine>();
            this.columnModelsToRemove = new ArrayList<DesignColumnModelDefine>();
            this.indexModelsToAdd = new ArrayList<DesignIndexModelDefine>();
            this.indexModelsToUpdate = new ArrayList<DesignIndexModelDefine>();
            this.indexModelsToRemove = new ArrayList<String>();
            Comparison comparison = new Comparison();
            comparison.compare();
            this.restructMode = RestructMode.MODIFY;
        } else {
            this.targetModel = targetModel;
            this.originalModel = null;
            this.restructMode = RestructMode.CREATE;
        }
    }

    RestructModel(DesignModel targetModel) {
        this.targetModel = targetModel;
        this.originalModel = null;
        this.restructMode = RestructMode.CREATE;
    }

    private void assertModifyMode() {
        if (this.restructMode != RestructMode.MODIFY) {
            throw new RuntimeException("method call fail, this method only run in MODIFY mode. ");
        }
    }

    private void assertCreateMode() {
        if (this.restructMode != RestructMode.CREATE) {
            throw new RuntimeException("method call fail, this method only run in CREATE mode. ");
        }
    }

    public RestructMode getRestructMode() {
        return this.restructMode;
    }

    public String getTableId() {
        this.assertModifyMode();
        return this.originalModel.getTableId();
    }

    public String getTableName() {
        if (this.restructMode == RestructMode.MODIFY) {
            return this.originalModel.getTableName();
        }
        return this.targetModel.getTableName();
    }

    public DesignModel getTargetDesignModel() {
        this.assertCreateMode();
        return this.targetModel;
    }

    public DesignTableModelDefine getTableModelToUpdate() {
        this.assertModifyMode();
        return this.tableModelToUpdate;
    }

    public DesignColumnModelDefine[] getColumnModelsToAdd() {
        this.assertModifyMode();
        return this.columnModelsToAdd.toArray(new DesignColumnModelDefine[this.columnModelsToAdd.size()]);
    }

    public DesignColumnModelDefine[] getColumnModelsToUpdate() {
        this.assertModifyMode();
        return this.columnModelsToUpdate.toArray(new DesignColumnModelDefine[this.columnModelsToUpdate.size()]);
    }

    public DesignColumnModelDefine[] getColumnModelsToRemove() {
        this.assertModifyMode();
        return this.columnModelsToRemove.toArray(new DesignColumnModelDefine[this.columnModelsToRemove.size()]);
    }

    public DesignIndexModelDefine[] getIndexModelsToAdd() {
        this.assertModifyMode();
        return this.indexModelsToAdd.toArray(new DesignIndexModelDefine[this.indexModelsToAdd.size()]);
    }

    public DesignIndexModelDefine[] getIndexModelsToUpdate() {
        this.assertModifyMode();
        return this.indexModelsToUpdate.toArray(new DesignIndexModelDefine[this.indexModelsToUpdate.size()]);
    }

    public String[] getIndexModelsToRemove() {
        this.assertModifyMode();
        return this.indexModelsToRemove.toArray(new String[this.indexModelsToRemove.size()]);
    }

    public static class ProcessOperationRestructModelBuilder {
        private final DesignDataModelService designDataModelService;

        public ProcessOperationRestructModelBuilder(DesignDataModelService designDataModelService) {
            this.designDataModelService = designDataModelService;
        }

        public RestructModel build(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignModel.ProcessOperationDesignModelBuilder designModelbuilder = new DesignModel.ProcessOperationDesignModelBuilder(this.designDataModelService);
            return new RestructModel(designModelbuilder.buildFromTask(task, formScheme, workflowSettings), designModelbuilder.buildFromTable(formScheme));
        }
    }

    public static class ProcessInstanceRestructModelBuilder {
        private final DesignDataModelService designDataModelService;
        private final PeriodEngineService periodEngineService;
        private final IEntityMetaService entityMetaService;
        private final IRuntimeDataSchemeService dataSchemeService;

        public ProcessInstanceRestructModelBuilder(DesignDataModelService designDataModelService, PeriodEngineService periodEngineService, IEntityMetaService entityMetaService, IRuntimeDataSchemeService dataSchemeService) {
            this.designDataModelService = designDataModelService;
            this.periodEngineService = periodEngineService;
            this.entityMetaService = entityMetaService;
            this.dataSchemeService = dataSchemeService;
        }

        public RestructModel build(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignModel.ProcessInstanceDesignModelBuilder designModelbuilder = new DesignModel.ProcessInstanceDesignModelBuilder(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataSchemeService);
            return new RestructModel(designModelbuilder.buildFromTask(task, formScheme, workflowSettings), designModelbuilder.buildFromTable(formScheme));
        }
    }

    private class Comparison {
        Map<String, DesignColumnModelDefine> originalColIdMap = new HashMap<String, DesignColumnModelDefine>();
        Map<String, DesignColumnModelDefine> originalColNameMap = new HashMap<String, DesignColumnModelDefine>();
        Map<String, DesignColumnModelDefine> targetColIdMap = new HashMap<String, DesignColumnModelDefine>();
        Map<String, DesignColumnModelDefine> targetColNameMap = new HashMap<String, DesignColumnModelDefine>();
        Map<String, DesignIndexModelDefine> originalIdxNameMap = new HashMap<String, DesignIndexModelDefine>();
        Map<String, DesignIndexModelDefine> targetIdxNameMap = new HashMap<String, DesignIndexModelDefine>();

        Comparison() {
            for (DesignColumnModelDefine column : RestructModel.this.originalModel.getColumnModels()) {
                this.originalColIdMap.put(column.getID(), column);
                this.originalColNameMap.put(column.getName(), column);
            }
            for (DesignColumnModelDefine column : RestructModel.this.targetModel.getColumnModels()) {
                this.targetColIdMap.put(column.getID(), column);
                this.targetColNameMap.put(column.getName(), column);
            }
            for (DesignIndexModelDefine index : RestructModel.this.originalModel.getIndexModels()) {
                this.originalIdxNameMap.put(index.getName(), index);
            }
            for (DesignIndexModelDefine index : RestructModel.this.targetModel.getIndexModels()) {
                this.targetIdxNameMap.put(index.getName(), index);
            }
        }

        public void compare() {
            this.compareColumn();
            this.compareIndex();
            this.compareTable();
        }

        private void compareColumn() {
            for (DesignColumnModelDefine originalCol : RestructModel.this.originalModel.getColumnModels()) {
                DesignColumnModelDefine targetCol = this.targetColNameMap.get(originalCol.getName());
                if (targetCol == null) {
                    RestructModel.this.columnModelsToRemove.add(originalCol);
                    continue;
                }
                if (!this.columnIsChanged(originalCol, targetCol)) continue;
                RestructModel.this.columnModelsToUpdate.add(this.changeColumn(originalCol, targetCol));
            }
            for (DesignColumnModelDefine targetCol : RestructModel.this.targetModel.getColumnModels()) {
                if (this.originalColNameMap.containsKey(targetCol.getName())) continue;
                RestructModel.this.columnModelsToAdd.add(targetCol);
            }
        }

        private boolean columnIsChanged(DesignColumnModelDefine originalCol, DesignColumnModelDefine targetCol) {
            return !originalCol.getColumnType().equals((Object)targetCol.getColumnType()) || originalCol.getPrecision() != targetCol.getPrecision() || originalCol.isNullAble() != targetCol.isNullAble();
        }

        private DesignColumnModelDefine changeColumn(DesignColumnModelDefine originalCol, DesignColumnModelDefine targetCol) {
            originalCol.setColumnType(targetCol.getColumnType());
            originalCol.setPrecision(targetCol.getPrecision());
            originalCol.setNullAble(targetCol.isNullAble());
            return originalCol;
        }

        private void compareIndex() {
            for (DesignIndexModelDefine originalIdx : RestructModel.this.originalModel.getIndexModels()) {
                DesignIndexModelDefine targetIdx = this.targetIdxNameMap.get(originalIdx.getName());
                if (targetIdx == null) {
                    RestructModel.this.indexModelsToRemove.add(originalIdx.getID());
                    continue;
                }
                if (!this.indexIsChanged(originalIdx, targetIdx)) continue;
                RestructModel.this.indexModelsToUpdate.add(this.changeIndex(originalIdx, targetIdx));
            }
            for (DesignIndexModelDefine targetIdx : RestructModel.this.targetModel.getIndexModels()) {
                if (this.originalIdxNameMap.containsKey(targetIdx.getName())) continue;
                RestructModel.this.indexModelsToAdd.add(targetIdx);
            }
        }

        private boolean indexIsChanged(DesignIndexModelDefine originalIdx, DesignIndexModelDefine targetIdx) {
            return !originalIdx.getType().equals((Object)targetIdx.getType()) || this.columnIdArrayIsChanged(originalIdx.getFieldIDs(), targetIdx.getFieldIDs());
        }

        private DesignIndexModelDefine changeIndex(DesignIndexModelDefine originalIdx, DesignIndexModelDefine targetIdx) {
            originalIdx.setType(targetIdx.getType());
            originalIdx.setFieldIDs(this.trunColumnIdArrayToOriginal(targetIdx.getFieldIDs()));
            return originalIdx;
        }

        private void compareTable() {
            DesignTableModelDefine originalTable = RestructModel.this.originalModel.getTableModel();
            DesignTableModelDefine targetTable = RestructModel.this.targetModel.getTableModel();
            boolean changed = false;
            if (this.columnIdArrayIsChanged(originalTable.getKeys(), targetTable.getKeys())) {
                originalTable.setKeys(this.trunColumnIdArrayToOriginal(targetTable.getKeys()));
                changed = true;
            }
            if (this.columnIdArrayIsChanged(originalTable.getBizKeys(), targetTable.getBizKeys())) {
                originalTable.setBizKeys(this.trunColumnIdArrayToOriginal(targetTable.getBizKeys()));
                changed = true;
            }
            if (changed) {
                RestructModel.this.tableModelToUpdate = originalTable;
            }
        }

        private boolean columnIdArrayIsChanged(String originalColumnIdArray, String targetColumnIdArray) {
            String[] originalFieldIds = originalColumnIdArray.split(";");
            HashSet<String> originalFieldNameSet = new HashSet<String>();
            for (String originalFieldId : originalFieldIds) {
                DesignColumnModelDefine col = this.originalColIdMap.get(originalFieldId);
                if (col == null) continue;
                originalFieldNameSet.add(col.getName());
            }
            String[] targetFieldIds = targetColumnIdArray.split(";");
            HashSet<String> targetFieldNameSet = new HashSet<String>();
            for (String targetFieldId : targetFieldIds) {
                DesignColumnModelDefine col = this.targetColIdMap.get(targetFieldId);
                if (col == null) continue;
                targetFieldNameSet.add(col.getName());
            }
            return !originalFieldNameSet.containsAll(targetFieldNameSet) || !targetFieldNameSet.containsAll(originalFieldNameSet);
        }

        private String trunColumnIdArrayToOriginal(String targetColumnIdArray) {
            String[] targetFieldIds = targetColumnIdArray.split(";");
            ArrayList<String> turnToOriginalFieldIds = new ArrayList<String>(targetFieldIds.length);
            for (String targetFieldId : targetFieldIds) {
                DesignColumnModelDefine targetField = this.targetColIdMap.get(targetFieldId);
                if (targetField == null) continue;
                String turnToFieldId = targetFieldId;
                String fieldName = targetField.getName();
                DesignColumnModelDefine originalField = this.originalColNameMap.get(fieldName);
                if (originalField != null) {
                    turnToFieldId = originalField.getID();
                }
                turnToOriginalFieldIds.add(turnToFieldId);
            }
            return String.join((CharSequence)";", turnToOriginalFieldIds);
        }
    }

    public static enum RestructMode {
        CREATE,
        MODIFY;

    }
}

