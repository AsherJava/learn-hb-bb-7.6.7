/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.ReportState.WorkflowJoinProvider;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorporateUtil {
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    IdentityService identityService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    TreeWorkflow treeWorkFlow;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;

    public DataAccessContext buildDataAccessContext(String taskKey, String sourceTableCode) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(taskDefine.getDw());
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(contextMainDimId);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        if (corporate) {
            WorkflowJoinProvider workflowJoinProvider = new WorkflowJoinProvider(taskDefine);
            workflowJoinProvider.getSqlJoinItem(sourceTableCode, tableModel.getCode());
            context.setSqlJoinProvider((ISqlJoinProvider)workflowJoinProvider);
        }
        return context;
    }

    public DataAccessContext buildDataAccessContext(String taskKey, String sourceTableCode, String corporateValue) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(corporateValue);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        if (corporate) {
            WorkflowJoinProvider workflowJoinProvider = new WorkflowJoinProvider(taskDefine);
            workflowJoinProvider.getSqlJoinItem(sourceTableCode, tableModel.getCode());
            context.setSqlJoinProvider((ISqlJoinProvider)workflowJoinProvider);
        }
        return context;
    }

    public void getAllColumnModels(TaskDefine taskDefine, NvwaQueryModel nvwaQueryModel) {
        String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(taskDefine.getDw());
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(contextMainDimId);
        IEntityModel dwEntityModel = this.iEntityMetaService.getEntityModel(contextMainDimId);
        IEntityAttribute bizKeyField = dwEntityModel.getBizKeyField();
        String corporateEntityId = this.workFlowDimensionBuilder.getCorporateEntityId(taskDefine);
        List<ColumnModelDefine> columnModels = this.nrParameterUtils.getAllFieldsInTable(tableModel.getID());
        for (ColumnModelDefine columnModel : columnModels) {
            if (bizKeyField.getCode().equals(columnModel.getCode())) {
                nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModel));
            }
            if (corporateEntityId == null || !corporateEntityId.equals(columnModel.getCode())) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModel));
        }
    }

    public void getAllColumnModels(TaskDefine taskDefine, NvwaQueryModel nvwaQueryModel, String corporateValue) {
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(corporateValue);
        IEntityModel dwEntityModel = this.iEntityMetaService.getEntityModel(corporateValue);
        IEntityAttribute bizKeyField = dwEntityModel.getBizKeyField();
        String corporateEntityId = this.workFlowDimensionBuilder.getCorporateEntityId(taskDefine);
        List<ColumnModelDefine> columnModels = this.nrParameterUtils.getAllFieldsInTable(tableModel.getID());
        for (ColumnModelDefine columnModel : columnModels) {
            if (bizKeyField.getCode().equals(columnModel.getCode())) {
                nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModel));
            }
            if (corporateEntityId == null || !corporateEntityId.equals(columnModel.getCode())) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModel));
        }
    }

    public NvwaQueryModel buildNvwaQueryModel(String periodString, TaskDefine taskDefine, List<ColumnModelDefine> columnModelDefines, boolean corporate) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        try {
            if (corporate) {
                for (ColumnModelDefine columnModelDefine : columnModelDefines) {
                    nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                }
                Date[] dates = this.parseFromPeriod(periodString, taskDefine.getDateTime());
                nvwaQueryModel.setQueryVersionDate(dates[1]);
                this.getAllColumnModels(taskDefine, nvwaQueryModel);
            }
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return nvwaQueryModel;
    }

    public NvwaQueryModel buildNvwaQueryModel(String periodString, TaskDefine taskDefine, List<ColumnModelDefine> columnModelDefines, String corporateValue, boolean corporate) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        try {
            if (corporate) {
                for (ColumnModelDefine columnModelDefine : columnModelDefines) {
                    nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                }
                Date[] dates = this.parseFromPeriod(periodString, taskDefine.getDateTime());
                nvwaQueryModel.setQueryVersionDate(dates[1]);
                this.getAllColumnModels(taskDefine, nvwaQueryModel, corporateValue);
            }
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return nvwaQueryModel;
    }

    public Date[] parseFromPeriod(String periodString, String periodEntityId) throws ParseException {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (StringUtils.isEmpty((String)periodString) || StringUtils.isEmpty((String)periodEntityId)) {
            return dateRegion;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        return periodProvider.getPeriodDateRegion(periodString);
    }
}

