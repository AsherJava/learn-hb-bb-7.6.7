/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model;

import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DesignModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.RestructModel;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;

public abstract class DataModelService {
    protected final DesignDataModelService designDataModelService;
    protected final PeriodEngineService periodEngineService;
    protected final IEntityMetaService entityMetaService;
    protected final DataModelDeployService dataModelDeployService;
    protected final IRuntimeDataSchemeService dataSchemeService;

    public DataModelService(DesignDataModelService designDataModelService, PeriodEngineService periodEngineService, IEntityMetaService entityMetaService, DataModelDeployService dataModelDeployService, IRuntimeDataSchemeService dataSchemeService) {
        this.designDataModelService = designDataModelService;
        this.periodEngineService = periodEngineService;
        this.entityMetaService = entityMetaService;
        this.dataModelDeployService = dataModelDeployService;
        this.dataSchemeService = dataSchemeService;
    }

    protected abstract String getTableName(FormSchemeDefine var1);

    public boolean exists(FormSchemeDefine formScheme) {
        String tableName = this.getTableName(formScheme);
        DesignTableModelDefine tableModel = this.designDataModelService.getTableModelDefineByCode(tableName);
        return tableModel != null;
    }

    protected abstract DesignModel getDesignModel(TaskDefine var1, FormSchemeDefine var2, WorkflowSettingsDO var3);

    public void create(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
        DesignModel designModel = this.getDesignModel(task, formScheme, workflowSettings);
        try {
            this.designDataModelService.insertTableModelDefine(designModel.getTableModel());
            this.designDataModelService.insertColumnModelDefines(designModel.getColumnModelArray());
            for (DesignIndexModelDefine indexModel : designModel.getIndexModels()) {
                this.designDataModelService.addIndexModelDefine(indexModel);
            }
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u521b\u5efa\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u4efb\u52a1\uff1a" + task.getTaskCode() + "\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getKey(), (Throwable)e);
        }
        try {
            this.dataModelDeployService.deployTable(designModel.getTableId());
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u53d1\u5e03\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u4efb\u52a1\uff1a" + task.getTaskCode() + "\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getKey(), (Throwable)e);
        }
    }

    public void create(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings, String tableName) {
        DesignModel designModel = this.getDesignModel(task, formScheme, workflowSettings);
        DesignTableModelDefine tableModel = designModel.getTableModel();
        tableModel.setName(tableName);
        tableModel.setCode(tableName);
        try {
            this.designDataModelService.insertTableModelDefine(designModel.getTableModel());
            this.designDataModelService.insertColumnModelDefines(designModel.getColumnModelArray());
            for (DesignIndexModelDefine indexModel : designModel.getIndexModels()) {
                this.designDataModelService.addIndexModelDefine(indexModel);
            }
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u521b\u5efa\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u4efb\u52a1\uff1a" + task.getTaskCode() + "\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getKey(), (Throwable)e);
        }
        try {
            this.dataModelDeployService.deployTable(designModel.getTableId());
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u53d1\u5e03\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u4efb\u52a1\uff1a" + task.getTaskCode() + "\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getKey(), (Throwable)e);
        }
    }

    public void drop(FormSchemeDefine formScheme) {
        String tableName = this.getTableName(formScheme);
        DesignTableModelDefine tableModel = this.designDataModelService.getTableModelDefineByCode(tableName);
        if (tableModel != null) {
            this.designDataModelService.deleteTableModelDefine(tableModel.getID());
            try {
                this.dataModelDeployService.deployTableUnCheck(tableModel.getID());
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u53d1\u5e03\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getKey(), (Throwable)e);
            }
        }
    }

    public abstract RestructModel compare(TaskDefine var1, FormSchemeDefine var2, WorkflowSettingsDO var3);

    public void maintainBeforeRestruct(RestructModel restructModel) {
        DesignColumnModelDefine[] columnModelsNeedToAdd = restructModel.getColumnModelsToAdd();
        if (columnModelsNeedToAdd.length > 0) {
            try {
                this.designDataModelService.insertColumnModelDefines(columnModelsNeedToAdd);
            }
            catch (ModelValidateException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u7ef4\u62a4\u6d41\u7a0b\u5f02\u5e38\uff0c\u8868\u540d\uff1a" + restructModel.getTableName(), (Throwable)e);
            }
            try {
                this.dataModelDeployService.deployTable(restructModel.getTableId());
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u53d1\u5e03\u6d41\u7a0b\u5f02\u5e38\uff0c\u8868\u540d\uff1a" + restructModel.getTableName(), (Throwable)e);
            }
        }
    }

    public void maintainAfterRestruct(RestructModel restructModel) {
        DesignIndexModelDefine[] indexModelsNeedToAdd;
        DesignIndexModelDefine[] indexModelsNeedToUpdate;
        String[] indexModelsNeedtoRemove;
        DesignColumnModelDefine[] columnModelsNeedToUpdate;
        boolean tableModelChanged = false;
        DesignColumnModelDefine[] columnModelsNeedtoRemove = restructModel.getColumnModelsToRemove();
        if (columnModelsNeedtoRemove.length > 0) {
            String[] columnIds = new String[columnModelsNeedtoRemove.length];
            for (int i = 0; i < columnModelsNeedtoRemove.length; ++i) {
                columnIds[i] = columnModelsNeedtoRemove[i].getID();
            }
            this.designDataModelService.deleteColumnModelDefines(columnIds);
            tableModelChanged = true;
        }
        if ((columnModelsNeedToUpdate = restructModel.getColumnModelsToUpdate()).length > 0) {
            this.designDataModelService.updateColumnModelDefines(columnModelsNeedToUpdate);
            tableModelChanged = true;
        }
        if ((indexModelsNeedtoRemove = restructModel.getIndexModelsToRemove()).length > 0) {
            this.designDataModelService.deleteIndexModelDefines(indexModelsNeedtoRemove);
            tableModelChanged = true;
        }
        if ((indexModelsNeedToUpdate = restructModel.getIndexModelsToUpdate()).length > 0) {
            this.designDataModelService.updateIndexModelDefines(indexModelsNeedToUpdate);
            tableModelChanged = true;
        }
        for (DesignIndexModelDefine indexModel : indexModelsNeedToAdd = restructModel.getIndexModelsToAdd()) {
            this.designDataModelService.addIndexModelDefine(indexModel);
            tableModelChanged = true;
        }
        if (restructModel.getTableModelToUpdate() != null) {
            try {
                this.designDataModelService.updateTableModelDefine(restructModel.getTableModelToUpdate());
                tableModelChanged = true;
            }
            catch (ModelValidateException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u7ef4\u62a4\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u8868\u540d\uff1a" + restructModel.getTableName(), (Throwable)e);
            }
        }
        if (tableModelChanged) {
            try {
                this.dataModelDeployService.deployTable(restructModel.getTableId());
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u53d1\u5e03\u6d41\u7a0b\u8868\u5f02\u5e38\uff0c\u8868\u540d\uff1a" + restructModel.getTableName(), (Throwable)e);
            }
        }
    }

    public static class ProcessOperationModelService
    extends DataModelService {
        public ProcessOperationModelService(DesignDataModelService designDataModelService, PeriodEngineService periodEngineService, IEntityMetaService entityMetaService, DataModelDeployService dataModelDeployService, IRuntimeDataSchemeService dataSchemeService) {
            super(designDataModelService, periodEngineService, entityMetaService, dataModelDeployService, dataSchemeService);
        }

        @Override
        public RestructModel compare(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            RestructModel.ProcessOperationRestructModelBuilder builder = new RestructModel.ProcessOperationRestructModelBuilder(this.designDataModelService);
            return builder.build(task, formScheme, workflowSettings);
        }

        @Override
        protected String getTableName(FormSchemeDefine formScheme) {
            return DataModelConstant.getHistoryTableName(formScheme);
        }

        @Override
        protected DesignModel getDesignModel(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignModel.ProcessOperationDesignModelBuilder builder = new DesignModel.ProcessOperationDesignModelBuilder(this.designDataModelService);
            return builder.buildFromTask(task, formScheme, workflowSettings);
        }
    }

    public static class ProcessInstanceModelService
    extends DataModelService {
        public ProcessInstanceModelService(DesignDataModelService designDataModelService, PeriodEngineService periodEngineService, IEntityMetaService entityMetaService, DataModelDeployService dataModelDeployService, IRuntimeDataSchemeService dataSchemeService) {
            super(designDataModelService, periodEngineService, entityMetaService, dataModelDeployService, dataSchemeService);
        }

        @Override
        public RestructModel compare(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            RestructModel.ProcessInstanceRestructModelBuilder builder = new RestructModel.ProcessInstanceRestructModelBuilder(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataSchemeService);
            return builder.build(task, formScheme, workflowSettings);
        }

        @Override
        protected String getTableName(FormSchemeDefine formScheme) {
            return DataModelConstant.getInstanceTableName(formScheme);
        }

        @Override
        protected DesignModel getDesignModel(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
            DesignModel.ProcessInstanceDesignModelBuilder builder = new DesignModel.ProcessInstanceDesignModelBuilder(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataSchemeService);
            return builder.buildFromTask(task, formScheme, workflowSettings);
        }
    }
}

