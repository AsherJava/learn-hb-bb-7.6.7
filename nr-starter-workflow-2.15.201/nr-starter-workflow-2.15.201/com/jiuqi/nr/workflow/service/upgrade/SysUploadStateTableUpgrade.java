/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface
 *  com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow.service.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import javax.sql.DataSource;

public class SysUploadStateTableUpgrade
implements CustomClassExecutor {
    private final IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
    private TodoExtendInterface todoExtendInterface = (TodoExtendInterface)SpringBeanUtils.getBean(TodoExtendInterface.class);
    private DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
    private DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
    private TodoUtil todoUtil = (TodoUtil)SpringBeanUtils.getBean(TodoUtil.class);

    public void execute(DataSource dataSource) throws Exception {
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                DesignTableModelDefine stateHistoryTableModelDefine;
                WorkFlowStateTableModel stateTableModel = this.todoExtendInterface.getStateTableModel(formSchemeDefine.getKey());
                DesignTableModelDefine stateTableModelDefine = this.designDataModelService.getTableModelDefineByCode(stateTableModel.getTableName());
                if (stateTableModelDefine != null) {
                    this.insertSerialNumberColumn(stateTableModelDefine, stateTableModel);
                }
                if ((stateHistoryTableModelDefine = this.designDataModelService.getTableModelDefineByCode(stateTableModel.getHistoryTableName())) == null) continue;
                this.insertSerialNumberColumn(stateHistoryTableModelDefine, stateTableModel);
            }
        }
    }

    private void insertSerialNumberColumn(DesignTableModelDefine tableModelDefine, WorkFlowStateTableModel stateTableModel) throws Exception {
        DesignColumnModelDefine idColumn;
        List stateTableColumns = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        if (!stateTableColumns.contains(idColumn = this.designDataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), stateTableModel.getWorkflowInstanceColumn()))) {
            this.designDataModelService.insertColumnModelDefine(this.buildDesignColumnModelDefine(tableModelDefine, stateTableModel));
            this.designDataModelService.updateTableModelDefine(tableModelDefine);
            this.dataModelDeployService.deployTableUnCheck(tableModelDefine.getID());
        }
    }

    private DesignColumnModelDefine buildDesignColumnModelDefine(DesignTableModelDefine tableModelDefine, WorkFlowStateTableModel stateTableModel) {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(stateTableModel.getWorkflowInstanceColumn());
        fieldDefine.setName(stateTableModel.getWorkflowInstanceColumn());
        fieldDefine.setColumnType(ColumnModelType.STRING);
        fieldDefine.setTableID(tableModelDefine.getID());
        fieldDefine.setPrecision(50);
        fieldDefine.setReferColumnID("");
        fieldDefine.setNullAble(true);
        return fieldDefine;
    }
}

