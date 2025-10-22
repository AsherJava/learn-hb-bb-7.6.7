/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.ExcelException
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.bi.office.excel.ExcelException;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySetInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.CounterParamBuilder;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImplDianxin;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import com.jiuqi.nr.bpm.impl.countersign.group.IQueryGroupCount;
import com.jiuqi.nr.bpm.impl.countersign.group.QueryGroupFactory;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.dao.UnitState;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NrParameterUtils {
    private static final Logger logger = LoggerFactory.getLogger(NrParameterUtils.class);
    public static final String INIIALVERSIONID = "00000000-0000-0000-0000-000000000000";
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    CustomWorkFolwService customWorkFolwServiceImpl;
    @Autowired
    WorkflowSettingService workflowSettingService;
    @Autowired
    IRuntimeFormService iRuntimeFormService;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    CounterParamBuilder counterParamBuilder;
    @Autowired
    RoleService roleService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private IDimensionProvider iDimensionProvider;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private QueryGroupFactory queryGroupFactory;
    @Autowired
    private IBatchQueryUploadStateService uploadStateService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private MessageEventListener messageEventListener;
    @Value(value="${jiuqi.nr.workflow.todo-value:5000}")
    private int maxValue;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private BusinessGenerator businessGenerator;
    private static final ThreadLocal<SimpleDateFormat> stf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public List<String> getMasterEntityViewKeys(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
        String masterEntityViewKeys = formScheme.getMasterEntitiesKey();
        if (StringUtils.isEmpty((String)masterEntityViewKeys)) {
            TaskDefine task = this.getTaskDefine(formScheme.getTaskKey());
            masterEntityViewKeys = task.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)masterEntityViewKeys)) {
            return Collections.emptyList();
        }
        ArrayList<String> result = new ArrayList<String>();
        for (String s : masterEntityViewKeys.split(";")) {
            result.add(s);
        }
        return result;
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme;
        try {
            formScheme = this.viewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new BpmException(String.format("get form scheme %s error.", formSchemeKey), e);
        }
        if (formScheme == null) {
            throw new BpmException(String.format("form scheme %s not found.", formSchemeKey));
        }
        return formScheme;
    }

    public TaskDefine getTaskDefine(String taskKey) {
        TaskDefine task;
        try {
            task = this.viewController.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            throw new BpmException(String.format("get task %s error.", taskKey), e);
        }
        if (task == null) {
            throw new BpmException(String.format("task %s not found.", taskKey));
        }
        return task;
    }

    public FormDefine getFormDefine(String formKey) {
        FormDefine form = this.viewController.queryFormById(formKey);
        if (form == null) {
            throw new BpmException(String.format("form %s not found.", formKey));
        }
        return form;
    }

    public EntityViewDefine getEntityView(String entityViewKey) {
        EntityViewDefine entityView = this.entityViewController.buildEntityView(entityViewKey);
        if (entityView == null) {
            throw new BpmException(String.format("entity view %s not found.", entityViewKey));
        }
        return entityView;
    }

    public TableDefine getTableDefine(String tableKey) {
        TableDefine table;
        try {
            table = this.dataDefinitionController.queryTableDefineByCode(tableKey);
        }
        catch (Exception e) {
            throw new BpmException(String.format("query table %s error.", tableKey), e);
        }
        if (table == null) {
            throw new BpmException(String.format("entity table %s not found.", tableKey));
        }
        return table;
    }

    public TableDefine getTableDefineByCode(String tableCode) {
        TableDefine table;
        try {
            table = this.dataDefinitionController.queryTableDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new BpmException(String.format("query table %s error.", tableCode), e);
        }
        return table;
    }

    public TaskFlowsDefine getFlowsDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
        TaskDefine task = this.getTaskDefine(formScheme.getTaskKey());
        return task.getFlowsSetting();
    }

    public List<FieldDefine> getFieldsByTable(String tableKey) {
        ArrayList<FieldDefine> fieldDefines = new ArrayList();
        try {
            fieldDefines = this.dataDefinitionController.getAllFieldsInTable(tableKey);
        }
        catch (Exception e) {
            throw new BpmException(String.format("fields from table %s not found.", tableKey), e);
        }
        return fieldDefines;
    }

    @Deprecated
    public void commitStateQuery(IDataQuery dataQuery, List<FieldDefine> fieldDefines, DimensionValueSet masterKeys, ExecutorContext context, String prevEvent, String curNode, Boolean force) {
        this.commitStateData(dataQuery, fieldDefines, masterKeys, context, prevEvent, curNode, force);
    }

    @Deprecated
    public void commitStateQuery(IDataQuery dataQuery, List<FieldDefine> fieldDefines, DimensionValueSet masterKeys, ExecutorContext context, String prevEvent, String curNode) {
        this.commitStateData(dataQuery, fieldDefines, masterKeys, context, prevEvent, curNode, false);
    }

    @Deprecated
    private void commitStateData(IDataQuery dataQuery, List<FieldDefine> fieldDefines, DimensionValueSet masterKeys, ExecutorContext context, String prevEvent, String curNode, Boolean force) {
        try {
            IDataTable dataTable = dataQuery.executeQuery(context);
            DimensionValueSet rowKeys = new DimensionValueSet(masterKeys);
            IDataRow dataRow = dataTable.findRow(rowKeys);
            if (dataRow == null) {
                dataRow = dataTable.appendRow(rowKeys);
            }
            for (FieldDefine fieldDefine : fieldDefines) {
                if (fieldDefine.getCode().equals("PREVEVENT")) {
                    dataRow.setValue(fieldDefine, (Object)prevEvent);
                } else if (fieldDefine.getCode().equals("CURNODE")) {
                    dataRow.setValue(fieldDefine, (Object)curNode);
                } else if (fieldDefine.getCode().equals("START_TIME")) {
                    if (prevEvent.equals("start")) {
                        dataRow.setValue(fieldDefine, (Object)new Date());
                    }
                } else if (fieldDefine.getCode().equals("UPDATE_TIME") && !prevEvent.equals("start")) {
                    dataRow.setValue(fieldDefine, (Object)new Date());
                }
                if (!fieldDefine.getCode().equals("FORCE_STATE")) continue;
                if (force == null || !force.booleanValue()) {
                    dataRow.setValue(fieldDefine, (Object)0);
                    continue;
                }
                dataRow.setValue(fieldDefine, (Object)1);
            }
            dataTable.commitChanges(true);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit state data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", prevEvent:");
            logBuilder.append(prevEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(fieldDefines.get(0).getCode());
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    @Deprecated
    public void batchCommitStateData(IDataQuery dataQuery, List<FieldDefine> fieldDefines, List<DimensionValueSet> masterKeys, ExecutorContext context, String prevEvent, String curNode, Boolean force) throws Exception {
        IDataTable dataTable = dataQuery.executeQuery(context);
        for (DimensionValueSet dimensionValueSet : masterKeys) {
            DimensionValueSet rowKeys = new DimensionValueSet(dimensionValueSet);
            IDataRow dataRow = dataTable.findRow(rowKeys);
            if (dataRow == null) {
                dataRow = dataTable.appendRow(rowKeys);
            }
            for (FieldDefine fieldDefine : fieldDefines) {
                if (fieldDefine.getCode().equals("PREVEVENT")) {
                    dataRow.setValue(fieldDefine, (Object)prevEvent);
                    continue;
                }
                if (fieldDefine.getCode().equals("CURNODE")) {
                    dataRow.setValue(fieldDefine, (Object)curNode);
                    continue;
                }
                if (fieldDefine.getCode().equals("FORCE_STATE")) {
                    if (force == null || !force.booleanValue()) {
                        dataRow.setValue(fieldDefine, (Object)0);
                        continue;
                    }
                    dataRow.setValue(fieldDefine, (Object)1);
                    continue;
                }
                if (fieldDefine.getCode().equals("START_TIME")) {
                    if (!prevEvent.equals("start")) continue;
                    dataRow.setValue(fieldDefine, (Object)new Date());
                    continue;
                }
                if (!fieldDefine.getCode().equals("UPDATE_TIME") || prevEvent.equals("start")) continue;
                dataRow.setValue(fieldDefine, (Object)new Date());
            }
        }
        dataTable.commitChanges(true);
    }

    @Deprecated
    public void commitFormQuery(IDataQuery dataQuery, List<FieldDefine> fieldDefines, DimensionValueSet masterKeys, ExecutorContext context, List<FormDefine> formDefines, String actorId, String status) throws Exception {
        IDataTable dataTable = dataQuery.executeQuery(context);
        for (FormDefine formDefine : formDefines) {
            masterKeys.setValue("FORMID", (Object)formDefine.getKey());
            DimensionValueSet rowKeys = new DimensionValueSet(masterKeys);
            IDataRow dataRow = dataTable.findRow(rowKeys);
            if (dataRow == null) {
                if (status.equals("fm_upload")) continue;
                dataRow = dataTable.appendRow(rowKeys);
            }
            for (FieldDefine fieldDefine : fieldDefines) {
                if (fieldDefine.getCode().equals("REJECT_USER")) {
                    dataRow.setValue(fieldDefine, (Object)actorId);
                    continue;
                }
                if (fieldDefine.getCode().equals("REJECT_TIME")) {
                    dataRow.setValue(fieldDefine, (Object)new Date());
                    continue;
                }
                if (!fieldDefine.getCode().equals("FORM_STATE")) continue;
                dataRow.setValue(fieldDefine, (Object)status);
            }
        }
        dataTable.commitChanges(true);
    }

    public void commitFormQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, List<FormDefine> formDefines, String actorId, String status) throws Exception {
        String tableCode = TableConstant.getSysUploadFormTableName(formScheme);
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
        if (tableModelDefine == null) {
            return;
        }
        List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : columnModels) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableModelDefine.getName());
        DimensionSet dimensionSet = masterKeys.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            Object value;
            String dimensionName = dimensionSet.get(i);
            ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
            if (null == column || null == (value = masterKeys.getValue(dimensionName))) continue;
            queryModel.getColumnFilters().put(column, value);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator openForUpdate = dataAccess.openForUpdate(context);
        List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(tableModelDefine, columnModels);
        for (FormDefine formDefine : formDefines) {
            masterKeys.setValue("FORMID", (Object)formDefine.getKey());
            List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)masterKeys);
            for (DimensionValueSet dimensionValueSet : dimensionSetList) {
                ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionValueSetColumns);
                INvwaDataRow appendRow = openForUpdate.addUpdateOrInsertRow(arrayKey);
                int index = 0;
                for (ColumnModelDefine column : columnModels) {
                    if ("REJECT_USER".equals(column.getCode())) {
                        appendRow.setValue(index, (Object)actorId);
                    } else if ("REJECT_TIME".equals(column.getCode())) {
                        appendRow.setValue(index, (Object)new Date());
                    } else if ("FORM_STATE".equals(column.getCode())) {
                        appendRow.setValue(index, (Object)status);
                    }
                    ++index;
                }
            }
        }
        openForUpdate.commitChanges(context);
    }

    private Date formateDate(Date date) {
        SimpleDateFormat format = stf.get();
        Date operateTime = null;
        try {
            String dateStr = format.format(date);
            operateTime = format.parse(dateStr);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return operateTime;
    }

    @Deprecated
    public void commitHiQuery(IDataQuery dataQuery, ExecutorContext context, DimensionValueSet masterKeys, List<FieldDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Boolean force, Integer exeOrder) {
        this.commitHiQuery(dataQuery, context, masterKeys, fieldDefines, curEvent, curNode, comment, actorId, operationId, this.formateDate(new Date()), force, exeOrder);
    }

    @Deprecated
    public void commitHiQuery(IDataQuery dataQuery, ExecutorContext context, DimensionValueSet masterKeys, List<FieldDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime) {
        this.commitHiQuery(dataQuery, context, masterKeys, fieldDefines, curEvent, curNode, comment, actorId, operationId, this.formateDate(operateTime), false, 0);
    }

    @Deprecated
    public void commitHiQuery(IDataQuery dataQuery, ExecutorContext context, DimensionValueSet masterKeys, List<FieldDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime, Boolean force, Integer exeOrder) {
        try {
            IDataUpdator dataUpdator = dataQuery.openForUpdate(context);
            DimensionValueSet rowKeys = new DimensionValueSet(masterKeys);
            rowKeys.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
            IDataRow dataRow = dataUpdator.addInsertedRow(rowKeys);
            for (FieldDefine fieldDefine : fieldDefines) {
                if (fieldDefine.getCode().equals("CUREVENT")) {
                    dataRow.setValue(fieldDefine, (Object)curEvent);
                    continue;
                }
                if (fieldDefine.getCode().equals("CURNODE")) {
                    dataRow.setValue(fieldDefine, (Object)curNode);
                    continue;
                }
                if (fieldDefine.getCode().equals("CMT")) {
                    dataRow.setValue(fieldDefine, (Object)comment);
                    continue;
                }
                if (fieldDefine.getCode().equals("OPERATOR")) {
                    dataRow.setValue(fieldDefine, (Object)actorId);
                    continue;
                }
                if (fieldDefine.getCode().equals("OPERATIONID")) {
                    dataRow.setValue(fieldDefine, (Object)operationId);
                    continue;
                }
                if (fieldDefine.getCode().equals("TIME_")) {
                    dataRow.setValue(fieldDefine, (Object)operateTime);
                    continue;
                }
                if (fieldDefine.getCode().equals("EXECUTE_ORDER")) {
                    dataRow.setValue(fieldDefine, (Object)exeOrder);
                    continue;
                }
                if (!fieldDefine.getCode().equals("FORCE_STATE")) continue;
                if (force == null || !force.booleanValue()) {
                    dataRow.setValue(fieldDefine, (Object)0);
                    continue;
                }
                dataRow.setValue(fieldDefine, (Object)1);
            }
            dataUpdator.commitChanges(true);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit history data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", curEvent:");
            logBuilder.append(curEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(fieldDefines.get(0).getCode());
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    @Deprecated
    public void batchCommitHiQuery(IDataQuery dataQuery, ExecutorContext context, List<DimensionValueSet> masterKeys, List<FieldDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Boolean force, Integer exeOrder) {
        this.batchCommitHiQuery(dataQuery, context, masterKeys, fieldDefines, curEvent, curNode, comment, actorId, operationId, this.formateDate(new Date()), force, exeOrder);
    }

    @Deprecated
    public void batchCommitHiQuery(IDataQuery dataQuery, ExecutorContext context, List<DimensionValueSet> masterKeys, List<FieldDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime, Boolean force, Integer exeOrder) {
        try {
            IDataUpdator dataUpdator = dataQuery.openForUpdate(context);
            for (DimensionValueSet masterKey : masterKeys) {
                DimensionValueSet rowKeys = new DimensionValueSet(masterKey);
                rowKeys.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
                IDataRow dataRow = dataUpdator.addInsertedRow(rowKeys);
                for (FieldDefine fieldDefine : fieldDefines) {
                    if (fieldDefine.getCode().equals("CUREVENT")) {
                        dataRow.setValue(fieldDefine, (Object)curEvent);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("CURNODE")) {
                        dataRow.setValue(fieldDefine, (Object)curNode);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("CMT")) {
                        dataRow.setValue(fieldDefine, (Object)comment);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("OPERATOR")) {
                        dataRow.setValue(fieldDefine, (Object)actorId);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("OPERATIONID")) {
                        dataRow.setValue(fieldDefine, (Object)operationId);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("TIME_")) {
                        dataRow.setValue(fieldDefine, (Object)operateTime);
                        continue;
                    }
                    if (fieldDefine.getCode().equals("EXECUTE_ORDER")) {
                        dataRow.setValue(fieldDefine, (Object)exeOrder);
                        continue;
                    }
                    if (!fieldDefine.getCode().equals("FORCE_STATE")) continue;
                    if (force == null || !force.booleanValue()) {
                        dataRow.setValue(fieldDefine, (Object)0);
                        continue;
                    }
                    dataRow.setValue(fieldDefine, (Object)1);
                }
            }
            dataUpdator.commitChanges(true);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("batch commit history data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", curEvent:");
            logBuilder.append(curEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(fieldDefines.get(0).getCode());
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    public String getMapKey() {
        return "preEvent";
    }

    public String getForceMapKey() {
        return "force";
    }

    public String getExecuteOrder() {
        return "order";
    }

    public String getTaskIdMapKey() {
        return "taskId";
    }

    public String getUserMapKey() {
        return "userId";
    }

    public String getCountersignMapKey() {
        return "countersign";
    }

    public String getCountersignParamMapKey() {
        return "counterSignParam";
    }

    public String getCountersignObjKey() {
        return "counterSignObj";
    }

    public String getIConditionCache() {
        return "conditionCache";
    }

    public TableModelDefine getFirstEntityTable(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
        if (enableTwoTree) {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode("MD_ORG");
            return tableModelDefine;
        }
        String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(formScheme.getDw());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(contextMainDimId);
        return tableModel;
    }

    public EntityViewDefine getFirstEntityView(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
        if (enableTwoTree) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(taskDefine.getDw());
            return this.dimensionUtil.getEntityView(contextMainDimId);
        }
        Iterator<String> iterator = this.getMasterEntityViewKeys(businessKey.getFormSchemeKey()).iterator();
        if (iterator.hasNext()) {
            String evKey = iterator.next();
            return this.getEntityView(evKey);
        }
        return null;
    }

    public List<WorkFlowLine> getWorkFlowLine(String taskId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwServiceImpl.getWorkflowLinesByPreTask(taskId, workFlowDefine.getLinkid());
        }
        return new ArrayList<WorkFlowLine>();
    }

    public String getProcessKey(String formScheme) {
        WorkflowSettingDefine workflow;
        String result = INIIALVERSIONID;
        if (!this.isDefaultWorkflow(formScheme) && (workflow = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formScheme)) != null) {
            result = workflow.getId();
        }
        return result;
    }

    public String getFormKeyByBusinessKey(BusinessKey businessKey, String formKey) {
        if (businessKey == null) {
            return StringUtils.isNotEmpty((String)formKey) ? formKey : INIIALVERSIONID;
        }
        return businessKey.getFormKey();
    }

    public void addFormKeyToMasterKeys(DimensionValueSet masterKeys, BusinessKey businessKey, String formKey) {
        String form = this.getFormKeyByBusinessKey(businessKey, formKey);
        if (form != null) {
            masterKeys.setValue("FORMID", (Object)form);
        }
    }

    private void setProcessMastKeys(List<ColumnModelDefine> uploadFields, DimensionValueSet mastKeys, String formSchemeKey) {
        List processField = uploadFields.stream().filter(e -> e.getCode().toUpperCase().equals("PROCESSKEY")).collect(Collectors.toList());
        if (!processField.isEmpty()) {
            mastKeys.setValue("PROCESSKEY", (Object)this.getProcessKey(formSchemeKey));
        }
    }

    public boolean isDefaultWorkflow(FormSchemeDefine formSchemeDefine) {
        WorkflowStatus queryFlowType;
        boolean defaultWorkflow = false;
        FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
        if (FlowsType.DEFAULT.equals((Object)flowsType) && WorkflowStatus.DEFAULT.equals((Object)(queryFlowType = this.workflowSettingService.queryFlowType(formSchemeDefine.getKey())))) {
            defaultWorkflow = true;
        }
        return defaultWorkflow;
    }

    private boolean isDefaultWorkflow(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.getFormScheme(formSchemeKey);
            return this.isDefaultWorkflow(formScheme);
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
    }

    public WorkFlowAction getWorkflowActionById(String actionid, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwServiceImpl.getWorkflowActionById(actionid, workFlowDefine.getLinkid());
        }
        return null;
    }

    public WorkFlowNodeSet queryWorkflowNode(String nodeId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwServiceImpl.getWorkFlowNodeSetByID(nodeId, workFlowDefine.getLinkid());
        }
        return null;
    }

    public boolean canExecuteTask(String taskId, String actionId, String formSchemeKey) {
        switch (actionId) {
            case "act_upload": {
                if (!taskId.equals("tsk_upload")) break;
                return true;
            }
            case "act_submit": {
                if (!taskId.equals("tsk_submit")) break;
                return true;
            }
            case "act_confirm": {
                if (!taskId.equals("tsk_audit")) break;
                return true;
            }
            case "act_reject": {
                if (!taskId.equals("tsk_audit") && !taskId.equals("tsk_audit_after_confirm")) break;
                return true;
            }
            case "act_return": {
                if (!taskId.equals("tsk_upload")) break;
                return true;
            }
            case "act_cancel_confirm": {
                if (!taskId.equals("tsk_audit_after_confirm")) break;
                return true;
            }
            case "act_apply_return": {
                if (!taskId.equals("tsk_audit") && !taskId.equals("tsk_audit_after_confirm")) break;
                return true;
            }
        }
        return false;
    }

    public static String getStateByAction(String dimName, String unitKey, String dataTime, FormSchemeDefine formScheme) {
        IBatchQueryUploadStateService batchQueryUploadStateService = (IBatchQueryUploadStateService)BeanUtil.getBean(IBatchQueryUploadStateService.class);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        dimensionValueSet.setValue(dimName, (Object)unitKey);
        ActionStateBean state = batchQueryUploadStateService.queryUploadState(dimensionValueSet, null, null, formScheme);
        if (state != null && state.getCode() != null) {
            return state.getTitile();
        }
        return null;
    }

    public List<WorkFlowLine> queryWorkFlowLines(String businessKey) {
        try {
            WorkflowSettingDefine define = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(BusinessKeyFormatter.parsingFromString(businessKey).getFormSchemeKey());
            if (define == null) {
                return Collections.emptyList();
            }
            WorkFlowDefine defineByID = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(define.getWorkflowId(), 1);
            if (defineByID == null) {
                return Collections.emptyList();
            }
            List<WorkFlowLine> lineByID = this.customWorkFolwServiceImpl.getWorkflowLinesByRunLinkid(defineByID.getLinkid());
            if (lineByID.size() == 0) {
                throw new ExcelException("\u5de5\u4f5c\u6d41\u4e0d\u5b58\u5728,\u8bf7\u6838\u5b9e");
            }
            return lineByID.stream().filter(e -> e.getBeforeNodeID().contains("StartEvent")).collect(Collectors.toList());
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
            return new ArrayList<WorkFlowLine>();
        }
    }

    public WorkFlowType getWorkflowStartType(FormSchemeDefine formScheme) {
        DesignFlowSettingDefine flowSettings = formScheme.getFlowsSetting().getDesignFlowSettingDefine();
        WorkFlowType flowType = flowSettings.getWordFlowType();
        return flowType;
    }

    public WorkFlowType getWorkflowStartType(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
        DesignFlowSettingDefine flowSettings = formScheme.getFlowsSetting().getDesignFlowSettingDefine();
        WorkFlowType flowType = flowSettings.getWordFlowType();
        return flowType;
    }

    public Optional<ProcessEngine> getProcessEngine(String formSchemeKey) {
        boolean isDefault = this.isDefaultWorkflow(formSchemeKey);
        if (isDefault) {
            return this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
        }
        return this.processEngineProvider.getProcessEngine();
    }

    public String getDefaultFormId(String formSchemKey) {
        if (this.commonUtil.checkFormIdIsPrimaryKey(formSchemKey)) {
            return INIIALVERSIONID;
        }
        return null;
    }

    public String queryStProcessDimName(FormSchemeDefine formSchemeDefine) {
        return "PROCESSKEY";
    }

    public String queryHiProcessDimName(FormSchemeDefine formSchemeDefine) {
        return "PROCESSKEY";
    }

    public boolean isMulitiInstanceTask(String userTaskId, String formSchemeKey) {
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null && (workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            return this.counterParamBuilder.isMultiInstanceTask(userTaskId, workFlowDefine.getLinkid());
        }
        return false;
    }

    public boolean isSignNodeByRoleType(String userTaskId, String formSchemeKey) {
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null && (workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            return this.counterParamBuilder.isRole(userTaskId, workFlowDefine.getLinkid());
        }
        return false;
    }

    public boolean isSignStartMode(String userTaskId, String formSchemeKey) {
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null && (workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            return this.counterParamBuilder.isSignStartMode(userTaskId, workFlowDefine.getLinkid());
        }
        return false;
    }

    public Set<String> getRoleIdsByUserId(String userid) {
        return this.roleService.getIdByIdentity(userid);
    }

    public User getUser(String userName) {
        return this.userService.getByUsername(userName);
    }

    public List<UploadRecordNew> queryHistoryState(BusinessKey businessKey, Task task) {
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(businessKey.getFormSchemeKey());
        DimensionValueSet dimension = this.dimensionUtil.buildDimension(businessKey);
        ArrayList<String> list = new ArrayList<String>();
        return this.batchQueryUploadStateService.queryHisUploadStates(formScheme, dimension, list, list, task.getUserTaskId());
    }

    public List<UploadRecordNew> queryHistoryState(BusinessKey businessKey, String nodeCode, List<String> roleKeys) {
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(businessKey.getFormSchemeKey());
        DimensionValueSet dimension = this.dimensionUtil.buildDimension(businessKey);
        BatchUploadStateServiceImplDianxin batchUploadStateServiceImplDianxin = new BatchUploadStateServiceImplDianxin();
        List<UploadRecordNew> uploadRecordNews = batchUploadStateServiceImplDianxin.queryHisUploadStates(formScheme, dimension, nodeCode, roleKeys);
        List maxUpdateTime = uploadRecordNews.stream().sorted((m1, m2) -> {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time1 = m1.getTime();
                String time2 = m2.getTime();
                if (time1 != null && time2 != null) {
                    Date date1 = dateFormat.parse(time1);
                    Date date2 = dateFormat.parse(time2);
                    return date2.compareTo(date1);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }).collect(Collectors.toList());
        ArrayList<UploadRecordNew> temp = new ArrayList<UploadRecordNew>();
        ArrayList<String> roleKey = new ArrayList<String>();
        for (UploadRecordNew uploadRecordNew : maxUpdateTime) {
            if (roleKey.contains(uploadRecordNew.getRoleKey())) continue;
            temp.add(uploadRecordNew);
            roleKey.add(uploadRecordNew.getRoleKey());
        }
        return temp;
    }

    public TableModelDefine getTable(FormSchemeDefine formScheme, String prefixTableName) {
        TableModelDefine tableModelDefine = null;
        try {
            String tableCode = prefixTableName + formScheme.getFormSchemeCode();
            tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{prefixTableName + formScheme.getFormSchemeCode()});
        }
        return tableModelDefine;
    }

    public TableModelDefine getTableByCode(String tableCode) {
        TableModelDefine table;
        try {
            table = this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new BpmException(String.format("query table %s error.", tableCode), e);
        }
        return table;
    }

    public List<ColumnModelDefine> getAllFieldsInTable(String tableKey) {
        try {
            return this.dataModelService.getColumnModelDefinesByTable(tableKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void commitHiQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String curEvent, String curNode, String comment, String actorId, String operationId, Boolean force, Integer exeOrder, String taskNodeId, String returnType) {
        this.commitHiQuery(formScheme, masterKeys, curEvent, curNode, comment, actorId, operationId, this.formateDate(new Date()), force, exeOrder, taskNodeId, returnType);
    }

    public void commitHiQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime, String taskNodeId) {
        this.commitHiQuery(formScheme, masterKeys, curEvent, curNode, comment, actorId, operationId, this.formateDate(operateTime), false, 0, taskNodeId, null);
    }

    public void commitHiQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime, Boolean force, Integer exeOrder, String taskNodeId, String returnType) {
        try {
            WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
            String tableCode = TableConstant.getSysUploadRecordTableName(formScheme);
            TableModelDefine table = this.getTableByCode(tableCode);
            List<ColumnModelDefine> columnModels = this.getAllFieldsInTable(table.getID());
            NvwaQueryModel queryModel = new NvwaQueryModel();
            this.setProcessMastKeys(columnModels, masterKeys, formScheme.getKey());
            for (ColumnModelDefine column : columnModels) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            DimensionSet dimensionSet = masterKeys.getDimensionSet();
            for (int i = 0; i < dimensionSet.size(); ++i) {
                Object value;
                String dimensionName = dimensionSet.get(i);
                ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
                if (null == column || null == (value = masterKeys.getValue(dimensionName))) continue;
                queryModel.getColumnFilters().put(column, value);
            }
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaUpdatableDataSet executeQuery = dataAccess.executeQueryForUpdate(context);
            INvwaDataRow appendRow = executeQuery.appendRow();
            int index = 0;
            for (ColumnModelDefine column : columnModels) {
                if (column.getCode().equals("CUREVENT")) {
                    appendRow.setValue(index, (Object)curEvent);
                } else if (column.getCode().equals("CURNODE")) {
                    appendRow.setValue(index, (Object)curNode);
                } else if (column.getCode().equals("CMT")) {
                    appendRow.setValue(index, (Object)comment);
                } else if (column.getCode().equals("RETURN_TYPE")) {
                    appendRow.setValue(index, (Object)returnType);
                } else if (column.getCode().equals("OPERATOR")) {
                    appendRow.setValue(index, (Object)actorId);
                } else if (column.getCode().equals("OPERATIONID")) {
                    appendRow.setValue(index, (Object)operationId);
                } else if (column.getCode().equals("TIME_")) {
                    appendRow.setValue(index, (Object)operateTime);
                } else if (column.getCode().equals("EXECUTE_ORDER")) {
                    appendRow.setValue(index, (Object)exeOrder);
                } else if (column.getCode().equals("FORCE_STATE")) {
                    if (force == null || !force.booleanValue()) {
                        appendRow.setValue(index, (Object)0);
                    } else {
                        appendRow.setValue(index, (Object)1);
                    }
                } else if (column.getCode().equals("SERIAL_NUMBER")) {
                    Object unitObj = masterKeys.getValue(dwMainDimName);
                    Object formObj = masterKeys.getValue("FORMID");
                    String formKey = formObj != null ? formObj.toString() : "";
                    Object period = masterKeys.getValue("DATATIME");
                    Object adjust = masterKeys.getValue("ADJUST");
                    String adjustStr = adjust != null ? adjust.toString() : "";
                    String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), masterKeys);
                    String msgId = this.workflow.getMessageId(formScheme.getKey(), period.toString(), unitObj.toString(), adjustStr, formKey, formKey, startType, curNode, corporateValue);
                    appendRow.setValue(index, (Object)ProcessBuilderUtils.produceUUIDKey(msgId));
                } else if (column.getCode().equals("ROLE_KEY")) {
                    String formKey = null;
                    Object value = masterKeys.getValue("FORMID");
                    if (value != null) {
                        formKey = value.toString();
                    }
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), masterKeys, formKey, formKey);
                    String roleKey = this.getRoleKey(formScheme.getKey(), businessKey, curNode);
                    if (roleKey != null) {
                        appendRow.setValue(index, (Object)roleKey);
                    }
                } else {
                    String dimensionName = dimensionChanger.getDimensionName(column);
                    if ("RECORDKEY".equals(dimensionName)) {
                        appendRow.setValue(index, (Object)UUID.randomUUID().toString());
                    }
                }
                ++index;
            }
            executeQuery.commitChanges(context);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit history data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", curEvent:");
            logBuilder.append(curEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(TableConstant.getSysUploadRecordTableName(formScheme));
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    public void batchCommitHiQuery(NvwaQueryModel queryModel, DataAccessContext context, String tableName, List<DimensionValueSet> masterKeys, List<ColumnModelDefine> allColumns, String curEvent, String curNode, String comment, String actorId, String operationId, Boolean force, Integer exeOrder, String formSchemeKey, String taskNodeId, String returnType) {
        this.batchCommitHiQuery(queryModel, context, tableName, masterKeys, allColumns, curEvent, curNode, comment, actorId, operationId, this.formateDate(new Date()), force, exeOrder, formSchemeKey, taskNodeId, returnType);
    }

    public void batchCommitHiQuery(NvwaQueryModel queryModel, DataAccessContext context, String tableName, List<DimensionValueSet> masterKeys, List<ColumnModelDefine> allColumns, String curEvent, String curNode, String comment, String actorId, String operationId, Date operateTime, Boolean force, Integer exeOrder, String formSchemeKey, String taskNodeId, String returnType) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            for (DimensionValueSet masterKey : masterKeys) {
                INvwaDataRow appendRow = openForUpdate.addInsertRow();
                int index = 0;
                for (ColumnModelDefine column : allColumns) {
                    Object value;
                    if (column.getCode().equals("CUREVENT")) {
                        appendRow.setValue(index, (Object)curEvent);
                    } else if (column.getCode().equals("CURNODE")) {
                        appendRow.setValue(index, (Object)curNode);
                    } else if (column.getCode().equals("CMT")) {
                        appendRow.setValue(index, (Object)comment);
                    } else if (column.getCode().equals("RETURN_TYPE")) {
                        appendRow.setValue(index, (Object)returnType);
                    } else if (column.getCode().equals("OPERATOR")) {
                        appendRow.setValue(index, (Object)actorId);
                    } else if (column.getCode().equals("OPERATIONID")) {
                        appendRow.setValue(index, (Object)operationId);
                    } else if (column.getCode().equals("TIME_")) {
                        appendRow.setValue(index, (Object)operateTime);
                    } else if (column.getCode().equals("EXECUTE_ORDER")) {
                        appendRow.setValue(index, (Object)exeOrder);
                    } else if (column.getCode().equals("FORCE_STATE")) {
                        if (force == null || !force.booleanValue()) {
                            appendRow.setValue(index, (Object)0);
                        } else {
                            appendRow.setValue(index, (Object)1);
                        }
                    } else if (column.getCode().equals("SERIAL_NUMBER")) {
                        Object unitObj = masterKey.getValue(dwMainDimName);
                        Object formObj = masterKey.getValue("FORMID");
                        String formKey = formObj != null ? formObj.toString() : "";
                        Object period = masterKey.getValue("DATATIME");
                        Object adjust = masterKey.getValue("ADJUST");
                        String adjustStr = adjust != null ? adjust.toString() : "";
                        String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), masterKey);
                        String msgId = this.workflow.getMessageId(formSchemeKey, period.toString(), unitObj.toString(), adjustStr, formKey, formKey, startType, curNode, corporateValue);
                        appendRow.setValue(index, (Object)ProcessBuilderUtils.produceUUIDKey(msgId));
                    } else if (column.getCode().equals("ROLE_KEY")) {
                        String formKey = null;
                        value = masterKey.getValue("FORMID");
                        if (value != null) {
                            formKey = value.toString();
                        }
                        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), masterKey, formKey, formKey);
                        String roleKey = this.getRoleKey(formScheme.getKey(), businessKey, curNode);
                        if (roleKey != null) {
                            appendRow.setValue(index, (Object)roleKey);
                        }
                    } else {
                        String dimensionName = dimensionChanger.getDimensionName(column);
                        if (dimensionName != null) {
                            value = masterKey.getValue(dimensionName);
                            if (value != null) {
                                appendRow.setValue(index, value);
                            }
                            if ("RECORDKEY".equals(dimensionName)) {
                                appendRow.setValue(index, (Object)UUID.randomUUID().toString());
                            }
                        }
                    }
                    ++index;
                }
            }
            openForUpdate.commitChanges(context);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("batch commit history data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", curEvent:");
            logBuilder.append(curEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(tableName);
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    public void commitStateQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String prevEvent, String curNode, Boolean force, String taskId) {
        this.commitStateData(formScheme, masterKeys, prevEvent, curNode, force, taskId);
    }

    public void commitStateQuery(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String prevEvent, String curNode, String taskId) {
        this.commitStateData(formScheme, masterKeys, prevEvent, curNode, false, taskId);
    }

    private void commitStateData(FormSchemeDefine formScheme, DimensionValueSet masterKeys, String prevEvent, String curNode, Boolean force, String taskID) {
        String tableName = "";
        try {
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
            WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
            String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
            TableModelDefine table = this.getTableByCode(tableCode);
            tableName = table.getName();
            List<ColumnModelDefine> columnModels = this.getAllFieldsInTable(table.getID());
            NvwaQueryModel queryModel = new NvwaQueryModel();
            this.setProcessMastKeys(columnModels, masterKeys, formScheme.getKey());
            for (ColumnModelDefine column : columnModels) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            DimensionSet dimensionSet = masterKeys.getDimensionSet();
            for (int j = 0; j < dimensionSet.size(); ++j) {
                String dimensionName = dimensionSet.get(j);
                ColumnModelDefine column = this.getColumnDefine(dimensionName, table.getName());
                Object value = masterKeys.getValue(j);
                if (null == value) continue;
                queryModel.getColumnFilters().put(column, value);
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, columnModels);
            ArrayKey arrayKey = dimensionChanger.getArrayKey(masterKeys, new ArrayList<ColumnModelDefine>(dimensionValueSetColumns));
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator iNvwaDataUpdator = dataAccess.openForUpdate(context);
            INvwaDataRow findRow = iNvwaDataUpdator.addUpdateOrInsertRow(arrayKey);
            int index = 0;
            for (ColumnModelDefine column : columnModels) {
                Object value;
                if (column.getCode().equals("PREVEVENT")) {
                    findRow.setValue(index, (Object)prevEvent);
                } else if (column.getCode().equals("CURNODE")) {
                    if (curNode == null && (value = findRow.getValue(column)) != null) {
                        curNode = value.toString();
                    }
                    findRow.setValue(index, (Object)curNode);
                } else if (column.getCode().equals("START_TIME")) {
                    if (prevEvent.equals("start") || prevEvent.equals("act_other_start")) {
                        findRow.setValue(index, (Object)new Date());
                    }
                } else if (column.getCode().equals("UPDATE_TIME") && !prevEvent.equals("start")) {
                    findRow.setValue(index, (Object)new Date());
                }
                if (column.getCode().equals("FORCE_STATE")) {
                    if (force == null) {
                        value = findRow.getValue(column);
                        if (value != null) {
                            if (value.equals("0")) {
                                findRow.setValue(index, (Object)0);
                            } else {
                                findRow.setValue(index, (Object)1);
                            }
                        }
                    } else if (!force.booleanValue()) {
                        findRow.setValue(index, (Object)0);
                    } else {
                        findRow.setValue(index, (Object)1);
                    }
                } else if (column.getCode().equals("SERIAL_NUMBER")) {
                    Object unitObj = masterKeys.getValue(dwMainDimName);
                    Object formObj = masterKeys.getValue("FORMID");
                    String formKey = formObj != null ? formObj.toString() : "";
                    Object period = masterKeys.getValue("DATATIME");
                    Object adjust = masterKeys.getValue("ADJUST");
                    String adjustStr = adjust != null ? adjust.toString() : "";
                    String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), masterKeys);
                    String msgId = this.workflow.getMessageId(formScheme.getKey(), period.toString(), unitObj.toString(), adjustStr, formKey, formKey, startType, curNode, corporateValue);
                    findRow.setValue(index, (Object)ProcessBuilderUtils.produceUUIDKey(msgId));
                }
                ++index;
            }
            if (curNode != null) {
                iNvwaDataUpdator.commitChanges(context);
            }
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit state data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", prevEvent:");
            logBuilder.append(prevEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(tableName);
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    public void batchCommitStateData(NvwaQueryModel queryModel, DataAccessContext context, String tableName, List<ColumnModelDefine> columns, List<ColumnModelDefine> dimensionColumns, List<DimensionValueSet> masterKeys, String prevEvent, String curNode, Boolean force, String formSchemeKey, String taskID) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaUpdatableDataSet queryForUpdate = updatableDataAccess.executeQueryForUpdate(context);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
        for (DimensionValueSet dimensionValueSet : masterKeys) {
            ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionColumns);
            INvwaDataRow dataRow = queryForUpdate.findRow(arrayKey);
            if (dataRow == null) {
                int j = 0;
                dataRow = queryForUpdate.appendRow();
                for (ColumnModelDefine fieldDefine : columns) {
                    Object value;
                    String dimensionName = dimensionChanger.getDimensionName(fieldDefine);
                    if (dimensionName != null && (value = dimensionValueSet.getValue(dimensionName)) != null) {
                        dataRow.setValue(j, value);
                    }
                    ++j;
                }
            }
            int index = 0;
            for (ColumnModelDefine fieldDefine : columns) {
                if (fieldDefine.getCode().equals("PREVEVENT")) {
                    dataRow.setValue(index, (Object)prevEvent);
                } else if (fieldDefine.getCode().equals("CURNODE")) {
                    dataRow.setValue(index, (Object)curNode);
                } else if (fieldDefine.getCode().equals("FORCE_STATE")) {
                    if (force == null || !force.booleanValue()) {
                        dataRow.setValue(index, (Object)0);
                    } else {
                        dataRow.setValue(index, (Object)1);
                    }
                } else if (fieldDefine.getCode().equals("START_TIME")) {
                    if (prevEvent.equals("start")) {
                        dataRow.setValue(index, (Object)new Date());
                    }
                } else if (fieldDefine.getCode().equals("UPDATE_TIME")) {
                    if (!prevEvent.equals("start")) {
                        dataRow.setValue(index, (Object)new Date());
                    }
                } else if (fieldDefine.getCode().equals("SERIAL_NUMBER")) {
                    Object unitObj = dimensionValueSet.getValue(dwMainDimName);
                    Object formObj = dimensionValueSet.getValue("FORMID");
                    Object period = dimensionValueSet.getValue("DATATIME");
                    Object adjust = dimensionValueSet.getValue("ADJUST");
                    String formKey = formObj != null ? formObj.toString() : "";
                    String adjustStr = adjust != null ? adjust.toString() : "";
                    String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), dimensionValueSet);
                    String msgId = this.workflow.getMessageId(formSchemeKey, period.toString(), unitObj.toString(), adjustStr, formKey, formKey, startType, curNode, corporateValue);
                    dataRow.setValue(index, (Object)ProcessBuilderUtils.produceUUIDKey(msgId));
                }
                ++index;
            }
        }
        try {
            queryForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public DimensionValueSet convertDimensionName(BusinessKeyInfo businessKey) {
        MasterEntityInfo masterEntity = businessKey.getMasterEntityInfo();
        DimensionValueSet masterKeys = new DimensionValueSet();
        Collection<String> masterEntityTableNames = masterEntity.getDimessionNames();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        for (String tableName : masterEntityTableNames) {
            if ("ADJUST".equals(tableName)) {
                masterKeys.setValue(tableName, (Object)masterEntity.getMasterEntityKey(tableName));
                continue;
            }
            String dimensionName = this.iDimensionProvider.getDimensionNameByEntityTableCode(context, tableName);
            if (dimensionName == "DATATIME") continue;
            masterKeys.setValue(dimensionName, (Object)masterEntity.getMasterEntityKey(tableName));
        }
        masterKeys.setValue("DATATIME", (Object)businessKey.getPeriod());
        WorkFlowType workflowType = this.commonUtil.workflowType(businessKey.getFormSchemeKey());
        if (WorkFlowType.FORM.equals((Object)workflowType) || WorkFlowType.GROUP.equals((Object)workflowType)) {
            masterKeys.setValue("FORMID", (Object)businessKey.getFormKey());
        }
        return masterKeys;
    }

    public DimensionValueSet convertDimensionName(BusinessKeySetInfo businessKeySet) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        MasterEntitySetInfo masterEntitySetInfo = businessKeySet.getMasterEntitySetInfo();
        masterEntitySetInfo.reset();
        while (masterEntitySetInfo.next()) {
            DimensionValueSet masterKeys = new DimensionValueSet();
            MasterEntityInfo masterEntity = masterEntitySetInfo.getCurrent();
            Collection<String> masterEntityTableNames = masterEntity.getDimessionNames();
            ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
            for (String tableName : masterEntityTableNames) {
                if ("ADJUST".equals(tableName)) {
                    masterKeys.setValue(tableName, (Object)masterEntity.getMasterEntityKey(tableName));
                    continue;
                }
                String dimensionName = this.iDimensionProvider.getDimensionNameByEntityTableCode(context, tableName);
                if (dimensionName == "DATATIME") continue;
                masterKeys.setValue(dimensionName, (Object)masterEntity.getMasterEntityKey(tableName));
            }
            masterKeys.setValue("DATATIME", (Object)businessKeySet.getPeriod());
            dims.add(masterKeys);
        }
        DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        return mergeDimensionValueSet;
    }

    public DimensionValueSet convertDimensionName(MasterEntityInfo masterEntity, String period) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        Collection<String> masterEntityTableNames = masterEntity.getDimessionNames();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        for (String tableName : masterEntityTableNames) {
            if ("ADJUST".equals(tableName)) {
                masterKeys.setValue(tableName, (Object)masterEntity.getMasterEntityKey(tableName));
                continue;
            }
            String dimensionName = this.iDimensionProvider.getDimensionNameByEntityTableCode(context, tableName);
            if (dimensionName == "DATATIME") continue;
            masterKeys.setValue(dimensionName, (Object)masterEntity.getMasterEntityKey(tableName));
        }
        masterKeys.setValue("DATATIME", (Object)period);
        return masterKeys;
    }

    private ColumnModelDefine getColumnDefine(String dimensionName, String tableName) {
        ColumnModelDefine column = null;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        column = dimensionChanger.getColumn(dimensionName);
        if (column == null) {
            String dimension = this.iDimensionProvider.getEntityIdByEntityTableCode(executorContext, dimensionName);
            column = dimensionChanger.getColumn(dimension);
        }
        return column;
    }

    public List<ColumnModelDefine> getDimensionValueSetColumns(TableModelDefine table, List<ColumnModelDefine> columns) {
        ArrayList<ColumnModelDefine> dimensionColumns = new ArrayList<ColumnModelDefine>();
        String bizKeys = table.getBizKeys();
        String[] bizKeyArray = bizKeys.split(";");
        for (int i = 0; i < bizKeyArray.length; ++i) {
            String bizKey = bizKeyArray[i];
            ColumnModelDefine columnModelDefine = columns.stream().filter(e -> e.getID().equals(bizKey)).findFirst().get();
            dimensionColumns.add(columnModelDefine);
        }
        return dimensionColumns;
    }

    public Set<String> getSystemUser() {
        return this.systemIdentityService.getAllSystemIdentities();
    }

    public IQueryGroupCount getQueryGroupCount(boolean roleType) {
        return this.queryGroupFactory.getQueryGroupCount(roleType);
    }

    public Map<String, Object> buildCounterParam(BusinessKey businessKey, Task task, String actionCode) {
        Map<String, Object> counterParam = this.counterParamBuilder.buildCounterParam(businessKey, task, actionCode, false);
        return counterParam;
    }

    public void batchCommitUnitStateData(String formSchemeKey, NvwaQueryModel queryModel, DataAccessContext context, String tableName, List<ColumnModelDefine> columns, List<ColumnModelDefine> dimensionColumns, List<UnitState> unitStates) throws Exception {
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        for (UnitState unitState : unitStates) {
            DimensionValueSet dimensionValueSet = unitState.getDimensionValueSet();
            dimensionValueSet.setValue("FORMID", (Object)"11111111-1111-1111-1111-111111111111");
            dimensionValueSet.setValue("PROCESSKEY", (Object)this.getProcessKey(formSchemeKey));
            ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionColumns);
            INvwaDataRow dataRow = openForUpdate.addUpdateOrInsertRow(arrayKey);
            int index = 0;
            for (ColumnModelDefine fieldDefine : columns) {
                if (fieldDefine.getCode().equals("PREVEVENT")) {
                    dataRow.setValue(index, (Object)unitState.getActionCode());
                } else if (fieldDefine.getCode().equals("CURNODE")) {
                    dataRow.setValue(index, (Object)unitState.getTaskId());
                } else if (fieldDefine.getCode().equals("FORCE_STATE")) {
                    boolean forceUpload = unitState.isForceUpload();
                    if (!forceUpload) {
                        dataRow.setValue(index, (Object)0);
                    } else {
                        dataRow.setValue(index, (Object)1);
                    }
                } else if (fieldDefine.getCode().equals("START_TIME")) {
                    if (unitState.getActionCode().equals("start")) {
                        dataRow.setValue(index, (Object)new Date());
                    }
                } else if (fieldDefine.getCode().equals("UPDATE_TIME") && !unitState.getActionCode().equals("start")) {
                    dataRow.setValue(index, (Object)new Date());
                }
                ++index;
            }
        }
        try {
            openForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUnitState(Set<BusinessKey> businessKeys, WorkFlowType workflowStartType, String taskID) {
        try {
            if (businessKeys.isEmpty()) {
                return;
            }
            BusinessKey business = (BusinessKey)businessKeys.stream().findAny().get();
            String formSchemeKey = business.getFormSchemeKey();
            businessKeys = this.distinctUnit(businessKeys, formSchemeKey);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            IConditionCache conditionCache = this.getConditionCache(businessKeys, formSchemeKey);
            for (BusinessKey businessKey : businessKeys) {
                DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
                String actionId = this.actionId(formScheme, buildDimension, workflowStartType, conditionCache);
                this.addFormKeyToMasterKeys(buildDimension, null, "11111111-1111-1111-1111-111111111111");
                Set<String> reportKeyOrGroupKeys = this.getReportKeyOrGroupKeys(formScheme, buildDimension, conditionCache, workflowStartType);
                String taskCode = this.getTaskCode(actionId, formScheme, buildDimension, reportKeyOrGroupKeys);
                this.commitStateQuery(formScheme, buildDimension, actionId, taskCode, false, taskID);
            }
        }
        catch (Exception e) {
            throw new BpmException("batch commit state data error");
        }
    }

    private Set<BusinessKey> distinctUnit(Set<BusinessKey> businessKeys, String formSchemeKey) {
        HashSet<BusinessKey> distinctBusinessKeys = new HashSet<BusinessKey>();
        String dwTableName = this.dimensionUtil.getDwTableNameByFormSchemeKey(formSchemeKey);
        HashSet<String> unitKeys = new HashSet<String>();
        for (BusinessKey businessKey : businessKeys) {
            String unitKey = businessKey.getMasterEntity().getMasterEntityKey(dwTableName);
            if (unitKeys.contains(unitKey)) continue;
            unitKeys.add(unitKey);
            distinctBusinessKeys.add(businessKey);
        }
        return distinctBusinessKeys;
    }

    private String actionId(FormSchemeDefine formScheme, DimensionValueSet dim, WorkFlowType workflowStartType, IConditionCache conditionCache) {
        String actionId = null;
        HashSet formOrGroupKeys = new HashSet();
        DimensionValueSet defaultValue = this.setDefaultValue(formScheme, dim);
        try {
            Set<String> reportKeyOrGroupKeys = this.getReportKeyOrGroupKeys(formScheme, dim, conditionCache, workflowStartType);
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
            actionId = defaultWorkflow ? this.defaultUnitState(formScheme, dim, reportKeyOrGroupKeys) : this.unitState(formScheme, dim, reportKeyOrGroupKeys);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionId;
    }

    private String defaultUnitState(FormSchemeDefine formSchemeDefine, DimensionValueSet dim, Set<String> formOrGroupKeys) {
        List codes;
        ArrayList<String> actionList = new ArrayList<String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        Map<DimensionValueSet, ActionStateBean> queryUploadStates = this.uploadStateService.queryUploadStates(fliterDim, new ArrayList<String>(formOrGroupKeys), new ArrayList<String>(formOrGroupKeys), formSchemeDefine);
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (Map.Entry<DimensionValueSet, ActionStateBean> state : queryUploadStates.entrySet()) {
                boolean bindProcess;
                DimensionValueSet dimensionValueSet = state.getKey();
                ActionStateBean uploadState = state.getValue();
                if (uploadState == null || uploadState.getCode() == null || !(bindProcess = this.workflow.bindProcess(formSchemeDefine.getKey(), dim, dimensionValueSet.getValue("FORMID").toString()))) continue;
                actionList.add(uploadState.getCode());
            }
        }
        if (actionList.contains(UploadState.REJECTED.toString())) {
            return "act_reject";
        }
        if (actionList.contains(UploadState.RETURNED.toString())) {
            return "act_return";
        }
        if (actionList.contains(UploadState.SUBMITED.toString())) {
            List submitCodes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.SUBMITED.toString())).collect(Collectors.toList());
            if (submitCodes.size() > 0 && submitCodes.size() == formOrGroupKeys.size()) {
                return "act_submit";
            }
            return "act_other_submit";
        }
        if (actionList.contains(UploadState.CONFIRMED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.CONFIRMED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "act_confirm";
            }
            return "act_other_confirm";
        }
        if (actionList.contains(UploadState.UPLOADED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.UPLOADED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "act_upload";
            }
            return "act_other_upload";
        }
        if (actionList.contains(UploadState.ORIGINAL_SUBMIT.toString()) || actionList.contains(UploadState.ORIGINAL_UPLOAD.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.ORIGINAL_SUBMIT.toString()) || x.equals(UploadState.ORIGINAL_UPLOAD.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "start";
            }
            return "act_other_start";
        }
        return "start";
    }

    private String unitState(FormSchemeDefine formSchemeDefine, DimensionValueSet dim, Set<String> formOrGroupKeys) {
        List codes;
        ArrayList<String> actionList = new ArrayList<String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        Map<DimensionValueSet, ActionStateBean> queryUploadStates = this.uploadStateService.queryUploadStates(fliterDim, new ArrayList<String>(formOrGroupKeys), new ArrayList<String>(formOrGroupKeys), formSchemeDefine);
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (Map.Entry<DimensionValueSet, ActionStateBean> state : queryUploadStates.entrySet()) {
                boolean bindProcess;
                DimensionValueSet dimensionValueSet = state.getKey();
                ActionStateBean uploadState = state.getValue();
                if (uploadState == null || uploadState.getCode() == null || !(bindProcess = this.workflow.bindProcess(formSchemeDefine.getKey(), dim, dimensionValueSet.getValue("FORMID").toString()))) continue;
                actionList.add(uploadState.getCode());
            }
        }
        if (actionList.contains(UploadState.REJECTED.toString())) {
            return "cus_reject";
        }
        if (actionList.contains(UploadState.RETURNED.toString())) {
            return "cus_return";
        }
        if (actionList.contains(UploadState.SUBMITED.toString())) {
            List submitCodes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.SUBMITED.toString())).collect(Collectors.toList());
            if (submitCodes.size() > 0 && submitCodes.size() == formOrGroupKeys.size()) {
                return "cus_submit";
            }
            return "act_other_submit";
        }
        if (actionList.contains(UploadState.CONFIRMED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.CONFIRMED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "cus_confirm";
            }
            return "act_other_confirm";
        }
        if (actionList.contains(UploadState.UPLOADED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.UPLOADED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "cus_upload";
            }
            return "act_other_upload";
        }
        if (actionList.contains(UploadState.ORIGINAL_SUBMIT.toString()) || actionList.contains(UploadState.ORIGINAL_UPLOAD.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.ORIGINAL_SUBMIT.toString()) || x.equals(UploadState.ORIGINAL_UPLOAD.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "start";
            }
            return "act_other_start";
        }
        return "start";
    }

    private String getTaskCode(String actionCode, FormSchemeDefine formSchemeDefine, DimensionValueSet dim, Set<String> formOrGroupKeys) {
        String taskCodeTemp = "";
        HashMap<String, String> codeToTaskCode = new HashMap<String, String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        List<UploadStateNew> queryUploadStates = this.uploadStateService.queryUploadStateNew(formSchemeDefine, fliterDim, new ArrayList<String>());
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (UploadStateNew uploadStateNew : queryUploadStates) {
                String formId = uploadStateNew.getFormId();
                if (!formOrGroupKeys.contains(formId)) continue;
                codeToTaskCode.put(uploadStateNew.getPreEvent(), uploadStateNew.getTaskId());
            }
        }
        if (codeToTaskCode != null && codeToTaskCode.size() > 0) {
            taskCodeTemp = (String)codeToTaskCode.get(actionCode);
            if (taskCodeTemp != null) {
                return taskCodeTemp;
            }
            Set keySet = codeToTaskCode.keySet();
            if ("act_other_start".equals(actionCode)) {
                return (String)codeToTaskCode.get("start");
            }
            if ("act_other_submit".equals(actionCode)) {
                if (keySet.contains("act_submit") || keySet.contains("cus_submit")) {
                    taskCodeTemp = codeToTaskCode.get("act_submit") != null ? (String)codeToTaskCode.get("act_submit") : (String)codeToTaskCode.get("cus_submit");
                }
            } else if ("act_other_upload".equals(actionCode)) {
                taskCodeTemp = codeToTaskCode.get("act_upload") != null ? (String)codeToTaskCode.get("act_upload") : (String)codeToTaskCode.get("cus_upload");
            } else if ("act_other_confirm".equals(actionCode)) {
                taskCodeTemp = codeToTaskCode.get("act_confirm") != null ? (String)codeToTaskCode.get("act_confirm") : (String)codeToTaskCode.get("cus_confirm");
            }
        }
        return taskCodeTemp;
    }

    private IConditionCache getConditionCache(Set<BusinessKey> businessKeys, String formSchemeKey) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
        for (BusinessKey businessKey : businessKeys) {
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            dims.add(buildDimension);
        }
        DimensionValueSet mergeDimensionValueSet = this.dimensionUtil.mergeDimensionValueSet(dims, formSchemeKey);
        IConditionCache conditionCache = this.formConditionService.getConditionForms(mergeDimensionValueSet, formSchemeKey);
        return conditionCache;
    }

    private Set<String> getReportKeyOrGroupKeys(FormSchemeDefine fromScheme, DimensionValueSet dim, IConditionCache conditionCache, WorkFlowType workflowStartType) {
        HashSet<String> formOrGroupKeys = new HashSet<String>();
        DimensionValueSet defaultValue = this.setDefaultValue(fromScheme, dim);
        if (null == conditionCache) {
            conditionCache = this.formConditionService.getConditionForms(defaultValue, fromScheme.getKey());
        }
        formOrGroupKeys = WorkFlowType.FORM.equals((Object)workflowStartType) ? new HashSet(conditionCache.getSeeForms(defaultValue)) : new HashSet(conditionCache.getSeeFormGroups(defaultValue));
        return formOrGroupKeys;
    }

    private DimensionValueSet setDefaultValue(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet) {
        DimensionValueSet dim = new DimensionValueSet();
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimTemp = dimensionSet.get(i);
            dim.setValue(dimTemp, dimensionValueSet.getValue(dimTemp));
        }
        String dims = formScheme.getDims();
        if (com.jiuqi.util.StringUtils.isNotEmpty((String)dims)) {
            String[] dimsArray;
            for (String dimKey : dimsArray = dims.split(";")) {
                String dimensionName = this.entityMetaService.getDimensionName(dimKey);
                if ("MD_CURRENCY".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"CNY");
                }
                if ("MD_GCADJTYPE".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"BEFOREADJ");
                }
                if (!"MD_GCORGTYPE".equals(dimensionName)) continue;
                dim.setValue(dimensionName, (Object)"MD_ORG_CORPORATE");
            }
        }
        return dim;
    }

    private DimensionValueSet fliterDim(DimensionValueSet dimensionValueSet) {
        DimensionValueSet dimensionValue = new DimensionValueSet();
        if (dimensionValueSet != null) {
            DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
            int size = dimensionSet.size();
            for (int i = 0; i < size; ++i) {
                String dimenName = dimensionSet.get(i);
                Object value = dimensionValueSet.getValue(dimenName);
                if ("FORMID".equals(dimenName)) continue;
                dimensionValue.setValue(dimenName, value);
            }
        }
        return dimensionValue;
    }

    public MessageEventListener getMessageEventListener() {
        return this.messageEventListener;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public String getCorporateValue(String forSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(forSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return this.workFlowDimensionBuilder.getContextMainDimId(taskDefine.getDw());
    }

    public List<String> queryNodeActors(String formSchemeKey, WorkFlowNodeSet workFlowNodeSet) {
        return this.counterParamBuilder.queryNextNodeActors(formSchemeKey, workFlowNodeSet);
    }

    public WorkFlowNodeSet queryNodeNode(String formSchemeKey, String nodeId, String actionId) {
        return this.counterParamBuilder.getWorkflowNodeSetByID(formSchemeKey, nodeId, actionId);
    }

    public WorkFlowNodeSet queryNodeNode(String formSchemeKey, String nodeId) {
        return this.counterParamBuilder.getWorkflowNodeSetByID(formSchemeKey, nodeId);
    }

    private String getRoleKey(String formSchemeKey, BusinessKey businessKey, String curNode) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_NODE_TO_ROLE);
        Object object = extension.get(CounterSignConst.NR_WORKFLOW_SIGN_NODE_TO_ROLE_VALUE);
        if (object != null) {
            return object.toString();
        }
        return null;
    }
}

