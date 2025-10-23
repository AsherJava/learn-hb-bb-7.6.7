/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesignModel {
    private DesignTableModelDefine tableModel;
    private List<DesignColumnModelDefine> columnModels;
    private List<DesignIndexModelDefine> indexModels;

    public String getTableId() {
        return this.tableModel.getID();
    }

    public String getTableName() {
        return this.tableModel.getName();
    }

    public DesignTableModelDefine getTableModel() {
        return this.tableModel;
    }

    public List<DesignColumnModelDefine> getColumnModels() {
        return this.columnModels;
    }

    public DesignColumnModelDefine[] getColumnModelArray() {
        return this.columnModels.toArray(new DesignColumnModelDefine[this.columnModels.size()]);
    }

    public List<DesignIndexModelDefine> getIndexModels() {
        return this.indexModels;
    }

    public boolean exists() {
        return this.tableModel != null && !StringUtils.isEmpty(this.tableModel.getID());
    }

    public static class ProcessOperationDesignModelBuilder
    extends DesignModelBuilder {
        public ProcessOperationDesignModelBuilder(DesignDataModelService designDataModelService) {
            super(designDataModelService);
        }

        public DesignModel buildFromTask(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignTableModelDefine tableModel = this.createTableModel(task, formScheme);
            String tableId = tableModel.getID();
            ArrayList<DesignColumnModelDefine> columnModels = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefine idColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_OPT_ID, tableId);
            columnModels.add(idColumnModel);
            DesignColumnModelDefine istIdColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_OPT_ISTID, tableId);
            columnModels.add(istIdColumnModel);
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_FROMNODE, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_ACTION, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_TONODE, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_NEWSTATUS, tableId));
            DesignColumnModelDefine timeColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_OPT_OPERATETIME, tableId);
            columnModels.add(timeColumnModel);
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_OPERATEUSER, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_OPERATEIDENTITY, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_COMMENT, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_OPERATETYPE, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_OPT_FORCEREPORT, tableId));
            tableModel.setKeys(idColumnModel.getID());
            tableModel.setBizKeys(idColumnModel.getID());
            ArrayList<DesignIndexModelDefine> indexModels = new ArrayList<DesignIndexModelDefine>(2);
            DesignIndexModelDefine instanceIdIndexModel = this.designDataModelService.createIndexModel();
            instanceIdIndexModel.setTableID(tableId);
            instanceIdIndexModel.setName(DataModelConstant.getOperationInstanceIdIndexName(tableModel.getName()));
            instanceIdIndexModel.setFieldIDs(istIdColumnModel.getID());
            instanceIdIndexModel.setType(IndexModelType.NORMAL);
            indexModels.add(instanceIdIndexModel);
            DesignIndexModelDefine operateTimeIndexModel = this.designDataModelService.createIndexModel();
            operateTimeIndexModel.setTableID(tableId);
            operateTimeIndexModel.setName(DataModelConstant.getOperationOperateTimeIndexName(tableModel.getName()));
            operateTimeIndexModel.setFieldIDs(timeColumnModel.getID());
            operateTimeIndexModel.setType(IndexModelType.NORMAL);
            indexModels.add(operateTimeIndexModel);
            DesignModel designModel = new DesignModel();
            designModel.tableModel = tableModel;
            designModel.columnModels = columnModels;
            designModel.indexModels = indexModels;
            return designModel;
        }

        private DesignTableModelDefine createTableModel(TaskDefine task, FormSchemeDefine formScheme) {
            String tableName = DataModelConstant.getHistoryTableName(formScheme);
            String tableTitle = task.getTitle() + "-" + formScheme.getTitle() + "-\u9ed8\u8ba4\u6d41\u7a0b\u64cd\u4f5c\u8868";
            return super.createTableModel(tableName, tableTitle);
        }

        @Override
        protected String getTableName(FormSchemeDefine formScheme) {
            return DataModelConstant.getHistoryTableName(formScheme);
        }
    }

    public static class ProcessInstanceDesignModelBuilder
    extends DesignModelBuilder {
        protected final PeriodEngineService periodEngineService;
        protected final IEntityMetaService entityMetaService;
        protected final IRuntimeDataSchemeService dataSchemeService;

        ProcessInstanceDesignModelBuilder(DesignDataModelService designDataModelService, PeriodEngineService periodEngineService, IEntityMetaService entityMetaService, IRuntimeDataSchemeService dataSchemeService) {
            super(designDataModelService);
            this.periodEngineService = periodEngineService;
            this.entityMetaService = entityMetaService;
            this.dataSchemeService = dataSchemeService;
        }

        public DesignModel buildFromTask(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignColumnModelDefine columnModel;
            DesignTableModelDefine tableModel = this.createTableModel(task, formScheme);
            String tableId = tableModel.getID();
            ArrayList<DesignColumnModelDefine> columnModels = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefine idColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_ID, tableId);
            columnModels.add(idColumnModel);
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_DEFINITIONID, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_STARTTIME, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_STARTUSER, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_UPDATETIME, tableId));
            DesignColumnModelDefine taskColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_TASK, tableId);
            columnModels.add(taskColumnModel);
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_NODE, tableId));
            columnModels.add(this.createFixColumnModel(DataModelConstant.FIELD_IST_STATUS, tableId));
            DesignColumnModelDefine lockColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_LOCK, tableId);
            columnModels.add(lockColumnModel);
            DesignColumnModelDefine lastOperationIdColumnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_LASTOPTIONID, tableId);
            columnModels.add(lastOperationIdColumnModel);
            ArrayList<String> bizKeyFieldIds = new ArrayList<String>();
            List<DesignColumnModelDefine> bizKeyColumnModels = this.createDimensionColumnModels(task, formScheme, tableId);
            for (DesignColumnModelDefine bizKeyColumnModel : bizKeyColumnModels) {
                columnModels.add(bizKeyColumnModel);
                bizKeyFieldIds.add(bizKeyColumnModel.getID());
            }
            WorkflowObjectType workflowObjectType = workflowSettings.getWorkflowObjectType();
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                columnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_FORMKEY, tableId);
                columnModels.add(columnModel);
                bizKeyFieldIds.add(columnModel.getID());
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                columnModel = this.createFixColumnModel(DataModelConstant.FIELD_IST_FORMGROUPKEY, tableId);
                columnModels.add(columnModel);
                bizKeyFieldIds.add(columnModel.getID());
            }
            tableModel.setKeys(idColumnModel.getID());
            tableModel.setBizKeys(String.join((CharSequence)";", bizKeyFieldIds));
            ArrayList<DesignIndexModelDefine> indexModels = new ArrayList<DesignIndexModelDefine>(2);
            DesignIndexModelDefine bizKeyIndexModel = this.designDataModelService.createIndexModel();
            bizKeyIndexModel.setTableID(tableId);
            bizKeyIndexModel.setName(DataModelConstant.getInstanceBIzKeyIndexName(tableModel.getName()));
            bizKeyIndexModel.setFieldIDs(tableModel.getBizKeys());
            bizKeyIndexModel.setType(IndexModelType.UNIQUE);
            indexModels.add(bizKeyIndexModel);
            DesignIndexModelDefine taskIndexModel = this.designDataModelService.createIndexModel();
            taskIndexModel.setTableID(tableId);
            taskIndexModel.setName(DataModelConstant.getInstanceTaskIndexName(tableModel.getName()));
            taskIndexModel.setFieldIDs(taskColumnModel.getID());
            taskIndexModel.setType(IndexModelType.NORMAL);
            indexModels.add(taskIndexModel);
            DesignIndexModelDefine lockIndexModel = this.designDataModelService.createIndexModel();
            lockIndexModel.setTableID(tableId);
            lockIndexModel.setName(DataModelConstant.getInstanceLockIndexName(tableModel.getName()));
            lockIndexModel.setFieldIDs(lockColumnModel.getID());
            lockIndexModel.setType(IndexModelType.NORMAL);
            indexModels.add(lockIndexModel);
            DesignIndexModelDefine lastOperationIdIndexModel = this.designDataModelService.createIndexModel();
            lastOperationIdIndexModel.setTableID(tableId);
            lastOperationIdIndexModel.setName(DataModelConstant.getInstanceLastOperationIdIndexName(tableModel.getName()));
            lastOperationIdIndexModel.setFieldIDs(lastOperationIdColumnModel.getID());
            lastOperationIdIndexModel.setType(IndexModelType.NORMAL);
            indexModels.add(lastOperationIdIndexModel);
            DesignModel designModel = new DesignModel();
            designModel.tableModel = tableModel;
            designModel.columnModels = columnModels;
            designModel.indexModels = indexModels;
            return designModel;
        }

        private DesignTableModelDefine createTableModel(TaskDefine task, FormSchemeDefine formScheme) {
            String tableName = DataModelConstant.getInstanceTableName(formScheme.getFormSchemeCode());
            String tableTitle = task.getTitle() + "-" + formScheme.getTitle() + "-\u9ed8\u8ba4\u6d41\u7a0b\u5b9e\u4f8b\u8868";
            return super.createTableModel(tableName, tableTitle);
        }

        private List<DesignColumnModelDefine> createDimensionColumnModels(TaskDefine task, FormSchemeDefine formScheme, String tableId) {
            ArrayList<DesignColumnModelDefine> columnModels = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefine mdColumnModel = this.designDataModelService.createColumnModelDefine();
            DataModelConstant.FIELD_MDCODE.applyto(mdColumnModel);
            mdColumnModel.setTableID(tableId);
            IEntityAttribute mdBizKeyField = this.getMDEntityBizKeyField(task);
            mdColumnModel.setReferTableID(mdBizKeyField.getTableID());
            mdColumnModel.setReferColumnID(mdBizKeyField.getID());
            columnModels.add(mdColumnModel);
            DesignColumnModelDefine periodColumnModel = this.designDataModelService.createColumnModelDefine();
            DataModelConstant.FIELD_PERIOD.applyto(periodColumnModel);
            periodColumnModel.setTableID(tableId);
            TableModelDefine periodTable = this.getPeriodEntityTable(task);
            periodColumnModel.setReferTableID(periodTable.getID());
            periodColumnModel.setReferColumnID(periodTable.getBizKeys());
            columnModels.add(periodColumnModel);
            List sceneDimensions = this.dataSchemeService.getDataSchemeDimension(task.getDataScheme(), DimensionType.DIMENSION);
            for (DataDimension dimension : sceneDimensions) {
                if (!dimension.getReportDim().booleanValue()) continue;
                columnModels.add(this.createEntityColumnModel(dimension.getDimKey(), tableId));
            }
            return columnModels;
        }

        private DesignColumnModelDefine createEntityColumnModel(String entityId, String tableId) {
            String dimensionName = this.getEntityDimensionName(entityId);
            IEntityAttribute entityBizKeyField = this.getEntityBizKeyField(entityId);
            DesignColumnModelDefine columnModel = this.designDataModelService.createColumnModelDefine();
            columnModel.setTableID(tableId);
            columnModel.setCode(dimensionName);
            columnModel.setName(dimensionName);
            columnModel.setTitle(dimensionName);
            columnModel.setColumnType(entityBizKeyField.getColumnType());
            columnModel.setPrecision(entityBizKeyField.getPrecision());
            columnModel.setNullAble(false);
            return columnModel;
        }

        private IEntityAttribute getMDEntityBizKeyField(TaskDefine task) {
            return this.getEntityBizKeyField(task.getDw());
        }

        private IEntityAttribute getEntityBizKeyField(String entityId) {
            try {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
                IEntityAttribute bizkeyField = entityModel.getBizKeyField();
                if (bizkeyField == null) {
                    bizkeyField = entityModel.getRecordKeyField();
                }
                return bizkeyField;
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u5b9e\u4f53\u4e3b\u952e\u5b57\u6bb5\u65f6\u53d1\u751f\u9519\u8bef\uff0c\u5b9e\u4f53ID\uff1a" + entityId, (Throwable)e);
            }
        }

        private String getEntityDimensionName(String entityId) {
            try {
                return this.entityMetaService.getDimensionName(entityId);
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u5b9e\u4f53\u7ef4\u5ea6\u540d\u65f6\u53d1\u751f\u9519\u8bef\uff0c\u5b9e\u4f53ID\uff1a" + entityId, (Throwable)e);
            }
        }

        private TableModelDefine getPeriodEntityTable(TaskDefine task) {
            try {
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(task.getDateTime());
                return periodAdapter.getPeriodEntityTableModel(periodEntity.getKey());
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u4efb\u52a1\u65f6\u671f\u7ef4\u5ea6\u6570\u636e\u8868\u4e3b\u952e\u5b57\u6bb5\u65f6\u53d1\u751f\u9519\u8bef\u3002", (Throwable)e);
            }
        }

        @Override
        protected String getTableName(FormSchemeDefine formScheme) {
            return DataModelConstant.getInstanceTableName(formScheme);
        }
    }

    private static abstract class DesignModelBuilder {
        protected final DesignDataModelService designDataModelService;

        DesignModelBuilder(DesignDataModelService designDataModelService) {
            this.designDataModelService = designDataModelService;
        }

        protected abstract String getTableName(FormSchemeDefine var1);

        protected DesignTableModelDefine createTableModel(String tableName, String tableTitle) {
            DesignTableModelDefine tableModel = this.designDataModelService.createTableModelDefine();
            tableModel.setCode(tableName);
            tableModel.setName(tableName);
            tableModel.setType(TableModelType.DEFAULT);
            tableModel.setKind(TableModelKind.DEFAULT);
            tableModel.setOwner("NR");
            tableModel.setTitle(tableTitle);
            return tableModel;
        }

        protected DesignColumnModelDefine createFixColumnModel(DataModelConstant.ColumnModelTemplate template, String tableId) {
            DesignColumnModelDefine columnModel = this.designDataModelService.createColumnModelDefine();
            columnModel.setTableID(tableId);
            template.applyto(columnModel);
            return columnModel;
        }

        public DesignModel buildFromTable(FormSchemeDefine formScheme) {
            String tableName = this.getTableName(formScheme);
            DesignTableModelDefine tableModel = this.designDataModelService.getTableModelDefineByCode(tableName);
            DesignModel designModel = new DesignModel();
            if (tableModel == null) {
                tableModel = new DesignTableModelDefineImpl();
                tableModel.setName(tableName);
                tableModel.setCode(tableName);
                designModel.tableModel = tableModel;
                designModel.columnModels = Collections.emptyList();
                designModel.indexModels = Collections.emptyList();
            } else {
                List columnModels = this.designDataModelService.getColumnModelDefinesByTable(tableModel.getID());
                List indexModels = this.designDataModelService.getIndexsByTable(tableModel.getID());
                designModel.tableModel = tableModel;
                designModel.columnModels = columnModels;
                designModel.indexModels = indexModels;
            }
            return designModel;
        }
    }
}

