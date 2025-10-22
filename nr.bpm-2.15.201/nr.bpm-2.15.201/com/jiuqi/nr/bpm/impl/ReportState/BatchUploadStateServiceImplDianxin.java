/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.Identity
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.exception.GatherException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.Identity;
import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.exception.GatherException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecord;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UploadStateVO;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImpl;
import com.jiuqi.nr.bpm.impl.ReportState.BuildUploadCountResultSet;
import com.jiuqi.nr.bpm.impl.ReportState.CorporateUtil;
import com.jiuqi.nr.bpm.impl.ReportState.OverviewStatisticsDianxin;
import com.jiuqi.nr.bpm.impl.ReportState.UploadUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.service.impl.ShowProcess;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionStateEnum;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class BatchUploadStateServiceImplDianxin
extends BatchUploadStateServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(BatchUploadStateServiceImplDianxin.class);
    private static final int MAXIDS = 900;
    private IRunTimeViewController runTimeViewController;
    private NrParameterUtils nrParameterUtils;
    private UserService<User> userService;
    private IdentityService identityService;
    private CustomWorkFolwService customWorkFolwService;
    private IWorkflow workflow;
    private TreeWorkflow treeWorkFlow;
    private CommonUtil commonUtil;
    private DataModelService dataModelService;
    private DataEngineAdapter dataEngineAdapter;
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    private IEntityMetaService iEntityMetaService;
    private INvwaSystemOptionService systemOptionOperator;
    private SystemIdentityService systemIdentityService;
    private WorkflowSettingService settingService;
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    private ShowProcess showProcess;
    private CorporateUtil corporateUtil;
    private OverviewStatisticsDianxin overviewStatisticsDianxin;
    private DimensionUtil dimensionUtil;
    Function<UploadStateNew, Boolean> filterUploadNews = e -> e.getActionStateBean() != null && UploadState.UPLOADED.toString().equals(e.getActionStateBean().getCode()) || UploadState.CONFIRMED.toString().equals(e.getActionStateBean().getCode());

    public BatchUploadStateServiceImplDianxin() {
        this.runTimeViewController = (IRunTimeViewController)SpringBeanProvider.getBean(IRunTimeViewController.class);
        this.nrParameterUtils = (NrParameterUtils)SpringBeanProvider.getBean(NrParameterUtils.class);
        this.userService = (UserService)SpringBeanProvider.getBean(UserService.class);
        this.identityService = (IdentityService)SpringBeanProvider.getBean(IdentityService.class);
        this.customWorkFolwService = (CustomWorkFolwService)SpringBeanProvider.getBean(CustomWorkFolwService.class);
        this.workflow = (IWorkflow)SpringBeanProvider.getBean(IWorkflow.class);
        this.treeWorkFlow = (TreeWorkflow)SpringBeanProvider.getBean(TreeWorkflow.class);
        this.commonUtil = (CommonUtil)SpringBeanProvider.getBean(CommonUtil.class);
        this.dataModelService = (DataModelService)SpringBeanProvider.getBean(DataModelService.class);
        this.dataEngineAdapter = (DataEngineAdapter)SpringBeanProvider.getBean(DataEngineAdapter.class);
        this.iNvwaDataAccessProvider = (INvwaDataAccessProvider)SpringBeanProvider.getBean(INvwaDataAccessProvider.class);
        this.iEntityMetaService = (IEntityMetaService)SpringBeanProvider.getBean(IEntityMetaService.class);
        this.systemOptionOperator = (INvwaSystemOptionService)SpringBeanProvider.getBean(INvwaSystemOptionService.class);
        this.systemIdentityService = (SystemIdentityService)SpringBeanProvider.getBean(SystemIdentityService.class);
        this.settingService = (WorkflowSettingService)SpringBeanProvider.getBean(WorkflowSettingService.class);
        this.workFlowDimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanProvider.getBean(IWorkFlowDimensionBuilder.class);
        this.showProcess = (ShowProcess)SpringBeanProvider.getBean(ShowProcess.class);
        this.corporateUtil = (CorporateUtil)SpringBeanProvider.getBean(CorporateUtil.class);
        this.overviewStatisticsDianxin = (OverviewStatisticsDianxin)SpringBeanProvider.getBean(OverviewStatisticsDianxin.class);
        this.dimensionUtil = (DimensionUtil)SpringBeanProvider.getBean(DimensionUtil.class);
    }

    @Override
    public List<UploadRecord> queryUploadActions(String entityId, String period, String formKey, FormSchemeDefine formScheme) {
        ArrayList<UploadRecord> uploadActions = new ArrayList<UploadRecord>();
        String tableCode = "UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumns) {
            switch (columnModelDefine.getCode()) {
                case "PERIOD": {
                    if (period == null) break;
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, period);
                    break;
                }
                case "ENTITIES": {
                    if (entityId == null) break;
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, entityId);
                    break;
                }
                case "FORMID": {
                    if (formKey == null) break;
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKey);
                    break;
                }
                case "TIME_": {
                    OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                    nvwaQueryModel.getOrderByItems().add(orderByItem);
                    break;
                }
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            UploadRecord uploadAction = null;
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            for (DataRow dataRow : executeQuery) {
                block38: for (int i = 0; i < allColumns.size(); ++i) {
                    uploadAction = new UploadRecord();
                    Object value = null;
                    String fieldValue = "";
                    try {
                        value = dataRow.getValue(i);
                        if (value != null) {
                            fieldValue = value.toString();
                        }
                    }
                    catch (RuntimeException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (allColumns.get(i).getCode()) {
                        case "PERIOD": {
                            uploadAction.setPeriod(fieldValue);
                            continue block38;
                        }
                        case "ENTITIES": {
                            uploadAction.setEntiryId(fieldValue);
                            continue block38;
                        }
                        case "FORMID": {
                            uploadAction.setFormKey(fieldValue);
                            continue block38;
                        }
                        case "ACTION": {
                            uploadAction.setAction(fieldValue);
                            continue block38;
                        }
                        case "CMT": {
                            Object comment = dataRow.getValue(i);
                            if (comment == null) continue block38;
                            if (comment instanceof byte[]) {
                                byte[] bt = (byte[])comment;
                                String s = new String(bt);
                                uploadAction.setCmt(s);
                                continue block38;
                            }
                            uploadAction.setCmt(comment.toString());
                            continue block38;
                        }
                        case "TIME_": {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value == null || !(value instanceof GregorianCalendar)) continue block38;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            String format = formatter.format(time);
                            uploadAction.setTime(format);
                            continue block38;
                        }
                        case "OPERATOR": {
                            uploadAction.setOperator(fieldValue);
                            continue block38;
                        }
                        case "OPERATIONID": {
                            uploadAction.setOperationid(fieldValue);
                            continue block38;
                        }
                    }
                }
                uploadActions.add(uploadAction);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadActions;
    }

    @Override
    public List<UploadStateVO> queryUploadStates(FormSchemeDefine formScheme) {
        ArrayList<UploadStateVO> uploadStates = new ArrayList<UploadStateVO>();
        String tableCode = TableConstant.getUploadStateTableName(formScheme);
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumns) {
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            UploadStateVO uploadState = null;
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            for (DataRow dataRow : executeQuery) {
                block18: for (int i = 0; i < allColumns.size(); ++i) {
                    uploadState = new UploadStateVO();
                    ColumnModelDefine columnModelDefine = allColumns.get(i);
                    String fieldValue = "";
                    try {
                        fieldValue = dataRow.getString(i);
                    }
                    catch (RuntimeException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (columnModelDefine.getCode()) {
                        case "PERIOD": {
                            uploadState.setPeriod(fieldValue);
                            continue block18;
                        }
                        case "ENTITIES": {
                            uploadState.setEntiryId(fieldValue);
                            continue block18;
                        }
                        case "FORMID": {
                            if (null == fieldValue) continue block18;
                            uploadState.setFormKey(fieldValue);
                            continue block18;
                        }
                        case "STATE": {
                            uploadState.setState(UploadState.valueOf(fieldValue));
                            continue block18;
                        }
                    }
                }
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    @Override
    public List<UploadRecordNew> queryUploadActionsNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        return this.queryUploadActionsNew(dimensionValueSet, formKey, formScheme, true);
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        return this.queryLatestUploadAction(dimensionValueSet, formKey, groupKey, formScheme, true);
    }

    @Override
    public ActionStateBean queryUploadState(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String formOrGroupKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && (formOrGroupKey = this.getDbFormGroupKey(startType, formKey, groupKey, formScheme)) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKey);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && null != (value = dimensionValueSet.getValue(dimensionName)) && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        String action = "";
        String node = "";
        String forceUpload = "";
        try {
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            Iterator iterator = executeQuery.iterator();
            if (iterator.hasNext()) {
                DataRow dataRow = (DataRow)iterator.next();
                for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModelDefine = allColumnModels.get(j);
                    if ("PREVEVENT".equals(columnModelDefine.getCode())) {
                        action = dataRow.getString(j);
                        continue;
                    }
                    if ("CURNODE".equals(columnModelDefine.getCode())) {
                        node = dataRow.getString(j);
                        continue;
                    }
                    if (!"FORCE_STATE".equals(columnModelDefine.getCode())) continue;
                    forceUpload = dataRow.getString(j);
                }
                return this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValueSet);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private String getDbFormGroupKey(WorkFlowType startType, String formKey, String groupKey, FormSchemeDefine formScheme) {
        if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            return startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : this.nrParameterUtils.getDefaultFormId(formScheme.getKey()));
        }
        return this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
    }

    private List<String> getDbFormGroupKeys(WorkFlowType startType, List<String> formKeys, List<String> groupKeys, FormSchemeDefine formScheme) {
        ArrayList<String> formKeysOrGroupKeys = new ArrayList<String>();
        if (WorkFlowType.FORM.equals((Object)startType) && !CollectionUtils.isEmpty(formKeys)) {
            formKeysOrGroupKeys.addAll(formKeys);
        } else if (WorkFlowType.GROUP.equals((Object)startType) && !CollectionUtils.isEmpty(groupKeys)) {
            formKeysOrGroupKeys.addAll(groupKeys);
        } else {
            String defaultFormId = this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
            if (defaultFormId != null) {
                formKeysOrGroupKeys.add(defaultFormId);
            }
        }
        return formKeysOrGroupKeys;
    }

    private ActionStateBean getState(String action, String node, String forceUpload, FormSchemeDefine formScheme, boolean defaultWorkflow, WorkFlowType workflowType, DimensionValueSet dimensionValueSet) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskKey(node);
        switch (action) {
            case "act_other_start": {
                actionState.setCode(UploadState.PART_START.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_START.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_submit": {
                actionState.setCode(UploadState.PART_SUBMITED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_SUBMITED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_upload": {
                actionState.setCode(UploadState.PART_UPLOADED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_UPLOADED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            case "act_other_confirm": {
                actionState.setCode(UploadState.PART_CONFIRMED.toString());
                actionState.setTitile(formScheme.getKey(), ActionStateEnum.PART_CONFIRMED.getStateName(workflowType));
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        }
        if (defaultWorkflow) {
            return this.defaultActionState(action, node, formScheme, forceUpload);
        }
        return this.customActionState(action, node, formScheme, forceUpload, dimensionValueSet);
    }

    private ActionStateBean defaultActionState(String action, String node, FormSchemeDefine formScheme, String forceUpload) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskKey(node);
        if ("start".equals(action)) {
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if (node.equals("tsk_upload")) {
                if (flowsSetting.isUnitSubmitForCensorship()) {
                    actionState.setCode(UploadState.SUBMITED.toString());
                    actionState.setTitile(formScheme.getKey(), "\u5df2\u9001\u5ba1");
                    actionState.setForceUpload(forceUpload.equals("1"));
                    return actionState;
                }
                actionState.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                actionState.setTitile(formScheme.getKey(), "\u672a\u4e0a\u62a5");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if (node.equals("tsk_submit")) {
                actionState.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                actionState.setTitile(formScheme.getKey(), "\u672a\u9001\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        } else {
            if ("act_submit".equals(action) || "cus_submit".equals(action)) {
                actionState.setCode(UploadState.SUBMITED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9001\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_return".equals(action) || "cus_return".equals(action)) {
                actionState.setCode(UploadState.RETURNED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9000\u5ba1");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_upload".equals(action) || "act_cancel_confirm".equals(action) || "cus_upload".equals(action)) {
                actionState.setCode(UploadState.UPLOADED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u4e0a\u62a5");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_confirm".equals(action) || "cus_confirm".equals(action)) {
                actionState.setCode(UploadState.CONFIRMED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u786e\u8ba4");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
            if ("act_reject".equals(action) || "cus_reject".equals(action)) {
                actionState.setCode(UploadState.REJECTED.toString());
                actionState.setTitile(formScheme.getKey(), "\u5df2\u9000\u56de");
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        }
        actionState.setCode(UploadState.ORIGINAL.toString());
        actionState.setTitile(formScheme.getKey(), ActionStateEnum.ORIGINAL.getStateName());
        actionState.setForceUpload(forceUpload.equals("1"));
        return actionState;
    }

    private ActionStateBean customActionState(String action, String node, FormSchemeDefine formScheme, String forceUpload, DimensionValueSet dimensionValue) {
        ActionStateBean actionState = new ActionStateBean();
        actionState.setTaskKey(node);
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        if ("start".equals(action)) {
            Iterator<WorkFlowAction> iterator;
            List<WorkFlowAction> workFlowNodeAction = this.customWorkFolwService.getRunWorkFlowNodeAction(node, workFlowDefine.getLinkid());
            if (workFlowNodeAction != null && workFlowNodeAction.size() > 0 && (iterator = workFlowNodeAction.iterator()).hasNext()) {
                WorkFlowAction workFlowAction = iterator.next();
                if ("cus_submit".equals(workFlowAction.getActionCode())) {
                    actionState.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                } else if ("cus_upload".equals(workFlowAction.getActionCode())) {
                    actionState.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                }
                actionState.setTitile("\u672a" + workFlowAction.getActionTitle());
                actionState.setForceUpload(forceUpload.equals("1"));
                return actionState;
            }
        } else {
            List<WorkFlowLine> workflowLineByEndNode = this.customWorkFolwService.getWorkflowLineByEndNode(node, workFlowDefine.getLinkid());
            for (WorkFlowLine wkl : workflowLineByEndNode) {
                String beforeNodeID = wkl.getBeforeNodeID();
                String actionId = wkl.getActionid();
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionId, workFlowDefine.getLinkid());
                boolean executeFormula = this.executeFormula(wkl.getFormula(), dimensionValue, formScheme.getKey());
                if (action == null || workflowAction == null || !action.equals(workflowAction.getActionCode()) || !executeFormula) continue;
                WorkFlowNodeSet nodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(beforeNodeID, workFlowDefine.getLinkid());
                if (nodeSetByID != null && nodeSetByID.isStatisticalNode()) {
                    JSONObject actionConfig;
                    actionState.setCode(nodeSetByID.getId() + "@" + workflowAction.getStateCode());
                    actionState.setTitile(workflowAction.getStateName());
                    actionState.setForceUpload(forceUpload.equals("1"));
                    String exset = workflowAction.getExset();
                    if (exset == null || (actionConfig = new JSONObject(workflowAction.getExset())) == null || !actionConfig.has("stateColor")) continue;
                    String color = actionConfig.getString("stateColor");
                    actionState.setColor(color);
                    continue;
                }
                actionState.setCode(workflowAction.getStateCode());
                actionState.setTitile(workflowAction.getStateName());
                actionState.setForceUpload(forceUpload.equals("1"));
            }
            if (actionState == null || actionState.getCode() == null) {
                actionState = this.defaultActionState(action, node, formScheme, forceUpload);
            }
        }
        return actionState;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> queryUploadStates(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        HashMap<DimensionValueSet, ActionStateBean> uploadStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = value1.toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            String processKey;
            if ("FORMID".equals(columnModelDefine.getCode())) {
                String formOrGroupKey = null;
                formOrGroupKey = WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType) ? (startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : null)) : this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
                if (formOrGroupKey != null) {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKey);
                }
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            Object value;
            String dimensionName = dimensionSet.get(i);
            ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
            if (null == column || null == (value = dimensionValueSet.getValue(dimensionName)) || "".equals(value)) continue;
            nvwaQueryModel.getColumnFilters().put(column, value);
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                block15: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            continue block15;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            continue block15;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block15;
                        }
                    }
                }
                uploadStateMap.put(dimensionValue, this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStateMap;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> queryUploadStates(DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, FormSchemeDefine formScheme) {
        HashMap<DimensionValueSet, ActionStateBean> uploadStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String processKey;
            if ("FORMID".equals(columnModelDefine.getCode())) {
                List<Object> formOrGroupKeys = new ArrayList();
                if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
                    formOrGroupKeys = startType == WorkFlowType.FORM ? formKeys : (startType == WorkFlowType.GROUP ? groupKeys : null);
                } else {
                    String defaultFormId = this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
                    if (null != defaultFormId) {
                        formOrGroupKeys.add(defaultFormId);
                    }
                }
                if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKeys);
                }
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && null != (value = dimensionValueSet.getValue(dimensionName)) && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            DimensionValueSet dimensionValue = null;
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                block14: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            continue block14;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            continue block14;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block14;
                        }
                    }
                }
                uploadStateMap.put(dimensionValue, this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStateMap;
    }

    @Override
    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine formScheme) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        UploadStateNew uploadState = null;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            int size = executeQuery.size();
            String action = "";
            String node = "";
            String forceUpload = "";
            for (int i = 0; i < size; ++i) {
                for (INvwaDataRow iNvwaDataRow : executeQuery) {
                    uploadState = new UploadStateNew();
                    ArrayKey rowKey = iNvwaDataRow.getRowKey();
                    DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, new ArrayList());
                    uploadState.setEntities(dimensionValue);
                    block21: for (int j = 0; j < allColumnModels.size(); ++j) {
                        ColumnModelDefine columnModel = allColumnModels.get(j);
                        Object value = iNvwaDataRow.getValue(columnModel);
                        if (value == null || "".equals(value)) continue;
                        switch (columnModel.getCode()) {
                            case "PREVEVENT": {
                                action = value.toString();
                                uploadState.setPreEvent(action);
                                continue block21;
                            }
                            case "CURNODE": {
                                node = value.toString();
                                uploadState.setTaskId(node);
                                continue block21;
                            }
                            case "FORMID": {
                                if (null == value) continue block21;
                                uploadState.setFormId(value.toString());
                                continue block21;
                            }
                            case "START_TIME": {
                                if (value == null || !(value instanceof GregorianCalendar)) continue block21;
                                GregorianCalendar gc = (GregorianCalendar)value;
                                Date time = gc.getTime();
                                uploadState.setStartTime(time);
                                continue block21;
                            }
                            case "UPDATE_TIME": {
                                if (value == null || !(value instanceof GregorianCalendar)) continue block21;
                                GregorianCalendar gc = (GregorianCalendar)value;
                                Date time = gc.getTime();
                                uploadState.setUpdateTime(time);
                                continue block21;
                            }
                            case "FORCE_STATE": {
                                forceUpload = value.toString();
                                continue block21;
                            }
                        }
                    }
                    uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
                }
                if (uploadState == null || uploadState.getTaskId() == null || "".equals(uploadState.getTaskId())) continue;
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    @Override
    public void deleteUploadRecord(BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        String tableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        this.delete(tableCode, businessKey);
    }

    private void delete(String tableCode, BusinessKey businessKey) {
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFieldsInTable = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allFieldsInTable) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allFieldsInTable);
        for (ColumnModelDefine column : dimensionValueSetColumns) {
            Object value;
            String dimensionName;
            if ("PROCESSKEY".equals(column.getCode())) {
                nvwaQueryModel.getColumnFilters().put(column, this.nrParameterUtils.getProcessKey(businessKey.getFormSchemeKey()));
                continue;
            }
            if ("BIZKEYORDER".equals(column.getCode()) || (dimensionName = dimensionChanger.getDimensionName(column)) == null || null == (value = masterKeys.getValue(dimensionName)) || "".equals(value)) continue;
            nvwaQueryModel.getColumnFilters().put(column, value);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(nvwaQueryModel);
        try {
            INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
            openForUpdate.deleteAll();
            openForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUploadRecord(BusinessKey businessKey, UploadRecordNew uploadRecord) {
        Optional userInfo;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, businessKey, businessKey.getFormKey());
        String actorId = uploadRecord.getOperator();
        if (!StringUtils.isEmpty((String)uploadRecord.getOperator()) && (userInfo = this.userService.findByUsername(uploadRecord.getOperator())) != null && userInfo.isPresent()) {
            actorId = ((User)userInfo.get()).getId();
        }
        Date dateTime = new Date();
        if (!StringUtils.isEmpty((String)uploadRecord.getTime())) {
            long timeStamp = Convert.toDate((String)uploadRecord.getTime());
            dateTime = new Date(timeStamp);
        }
        this.nrParameterUtils.commitHiQuery(formScheme, masterKeys, uploadRecord.getAction(), uploadRecord.getTaskId(), uploadRecord.getCmt(), actorId, uploadRecord.getOperationid(), dateTime, "import");
    }

    @Override
    public void deleteUploadState(BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
        this.delete(tableCode, businessKey);
    }

    @Override
    public void insertUploadState(BusinessKey businessKey, UploadStateNew uploadState) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, businessKey, businessKey.getFormKey());
        this.nrParameterUtils.commitStateQuery(formScheme, masterKeys, uploadState.getPreEvent(), uploadState.getTaskId(), null);
    }

    @Override
    public UploadStateNew queryUploadStateNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            if ("FORMID".equals(columnModelDefine.getCode()) && formKey != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        UploadStateNew uploadState = new UploadStateNew();
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (value == null) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadState;
    }

    @Override
    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKey) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        UploadStateNew uploadState = null;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String processKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && !CollectionUtils.isEmpty(formKey)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKey);
            }
            if ("UPDATE_TIME".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, processKey);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (!Objects.nonNull(value)) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
                if (uploadState == null || uploadState.getTaskId() == null || "".equals(uploadState.getTaskId())) continue;
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    private void initUserTitles(List<UploadRecordNew> uploadActions) {
        if (uploadActions.size() <= 0) {
            return;
        }
        Set<String> userIds = uploadActions.stream().filter(t -> !StringUtils.isEmpty((String)t.getOperator())).map(t -> t.getOperator()).collect(Collectors.toSet());
        List<User> userDefines = this.getUsers(userIds);
        Map<String, User> userMap = userDefines.stream().collect(Collectors.toMap(User::getId, t -> t, (oldValue, newValue) -> oldValue));
        for (UploadRecordNew desInfo : uploadActions) {
            String userName;
            String userId = desInfo.getOperator();
            if (StringUtils.isEmpty((String)userId)) continue;
            boolean enableUserIdentity = this.enableUserIdentity();
            if (enableUserIdentity) {
                userName = this.getUserName(userMap, userId);
                desInfo.setOperator(userName);
                continue;
            }
            userName = this.getUserNameIgnoreIdentity(userMap, userId);
            desInfo.setOperator(userName);
        }
    }

    private boolean enableUserIdentity() {
        String value = this.systemOptionOperator.findValueById("NVWA_USER_IDENTITY");
        return "1".equals(value);
    }

    @Override
    public String getUserNameIgnoreIdentity(Map<String, User> userMap, String userId) {
        String userName = "";
        User user = userMap.get(userId);
        if (user == null) {
            boolean systemIdentity = this.systemIdentityService.isSystemIdentity(userId);
            boolean system = false;
            if ("00000000-0000-0000-0000-000000000000".equals(userId)) {
                system = true;
            }
            userName = systemIdentity || system ? "\u7cfb\u7edf\u7ba1\u7406\u5458" : "\u7528\u6237\u5df2\u5220\u9664";
        } else {
            userName = user.getNickname();
        }
        return userName;
    }

    @Override
    public String getUserName(Map<String, User> userMap, String userId) {
        String userName = "";
        User user = userMap.get(userId);
        if (user == null) {
            boolean systemIdentity = this.systemIdentityService.isSystemIdentity(userId);
            boolean system = false;
            if ("00000000-0000-0000-0000-000000000000".equals(userId)) {
                system = true;
            }
            if (systemIdentity || system) {
                userName = "\u7cfb\u7edf\u7ba1\u7406\u5458";
            } else {
                Optional identity = this.identityService.get(userId);
                if (identity.isPresent()) {
                    List userIdByIdentity = this.identityService.getUserIdByIdentity(userId);
                    if (userIdByIdentity != null && userIdByIdentity.size() > 0) {
                        String userid = (String)userIdByIdentity.get(0);
                        User user2 = this.userService.get(userid);
                        if (user2 != null) {
                            String name = ((Identity)identity.get()).getName();
                            userName = user2.getNickname() + "(" + name + ")";
                        } else {
                            userName = "\u7528\u6237\u5df2\u5220\u9664(" + ((Identity)identity.get()).getName() + ")";
                        }
                    }
                } else {
                    User user2 = this.userService.get(userId);
                    userName = user2 == null ? "\u7528\u6237\u5df2\u5220\u9664" : "\u8eab\u4efd\u5df2\u5220\u9664";
                }
            }
        } else {
            Optional identity = this.identityService.get(userId);
            if (identity.isPresent()) {
                String name = ((Identity)identity.get()).getName();
                userName = user.getNickname() + "(" + name + ")";
            } else {
                userName = user.getNickname();
            }
        }
        return userName;
    }

    private List<User> getUsers(Set<String> userIds) {
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<String> userList = new ArrayList<String>(userIds);
        int userCount = userList.size();
        int count = userCount % 900 == 0 ? userCount / 900 : userCount / 900 + 1;
        for (int index = 0; index < count; ++index) {
            int fromIndex = index * 900;
            int toIndex = Math.min(fromIndex + 900, userCount);
            List tempIds = userList.subList(fromIndex, toIndex);
            List userDefines = this.userService.get(tempIds.toArray(new String[0]));
            users.addAll(userDefines);
        }
        return users;
    }

    @Override
    public List<UploadRecordNew> queryUploadActionsNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean getUser) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        ArrayList<String> fromKeyOrGroupKeys = new ArrayList<String>();
        fromKeyOrGroupKeys.add(formKey);
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, dimensionValueSet, fromKeyOrGroupKeys, fromKeyOrGroupKeys, null, false);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        uploadActions.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<UploadRecordNew>(Comparator.comparing(e -> e.getTaskId()))), ArrayList::new));
        if (getUser) {
            this.initUserTitles(uploadActions);
        }
        return uploadActions;
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme, boolean getUser) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        ArrayList<String> fromKeys = new ArrayList<String>();
        fromKeys.add(formKey);
        ArrayList<String> groupKeys = new ArrayList<String>();
        groupKeys.add(groupKey);
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, dimensionValueSet, fromKeys, groupKeys, null, true);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        if (getUser) {
            this.initUserTitles(uploadActions);
        }
        return uploadActions.size() <= 0 ? null : uploadActions.get(0);
    }

    @Override
    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String formKey, String groupKey, String nodeId) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        ArrayList<String> fromKeys = new ArrayList<String>();
        fromKeys.add(formKey);
        ArrayList<String> groupKeys = new ArrayList<String>();
        groupKeys.add(groupKey);
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, dimensionValueSet, fromKeys, groupKeys, nodeId, false);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        this.initUserTitles(uploadActions);
        return uploadActions;
    }

    @Override
    public List<UploadRecordNew> queryUploadActionsNew(FormSchemeDefine formScheme) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        ArrayList<String> fromKeyOrGroupKeys = new ArrayList<String>();
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, new DimensionValueSet(), fromKeyOrGroupKeys, fromKeyOrGroupKeys, null, false);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        this.initUserTitles(uploadActions);
        return uploadActions;
    }

    @Override
    public List<UploadRecordNew> queryUploadHisState(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, dimensionValueSet, formKeys, formKeys, null, false);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        return uploadActions;
    }

    @Override
    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, String nodeId) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        INvwaDataSet dataSetWithRowKey = this.queryDataSetWithRowKey(table.getName(), allColumns, formScheme, dimensionValueSet, formKeys, groupKeys, nodeId, false);
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(dataSetWithRowKey, allColumns, dimensionColumns, table.getName());
        return uploadActions;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> queryUploadAfterState(DimensionValueSet dimensionValueSet, String period, FormSchemeDefine formScheme) {
        HashMap<DimensionValueSet, ActionStateBean> uploadStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        StringBuilder peridoFilter = new StringBuilder();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            String dimensionName;
            String processKey;
            if ("FORMID".equals(columnModelDefine.getCode())) {
                ArrayList<String> formOrGroupKeys = new ArrayList<String>();
                String defaultFormId = this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
                if (null != defaultFormId) {
                    formOrGroupKeys.add(defaultFormId);
                }
                if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKeys);
                }
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null) {
                Object value = dimensionValueSet.getValue(dimensionName);
                if (null != value && !"".equals(value) && dimensionName != "DATATIME") {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
                }
                if ("DATATIME".equals(dimensionName)) {
                    peridoFilter.append(tableCode).append("[").append(columnModelDefine.getCode()).append("] > '").append(period).append("'");
                    nvwaQueryModel.setFilter(peridoFilter.toString());
                }
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            DimensionValueSet dimensionValue = null;
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                block14: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            continue block14;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            continue block14;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block14;
                        }
                    }
                }
                uploadStateMap.put(dimensionValue, this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        HashMap<DimensionValueSet, ActionStateBean> newUploadStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        uploadStateMap.forEach((k, v) -> {
            String upload = v.getCode();
            if (UploadState.SUBMITED.toString().equals(upload) || UploadState.UPLOADED.toString().equals(upload) || UploadState.CONFIRMED.toString().equals(upload)) {
                newUploadStateMap.put((DimensionValueSet)k, (ActionStateBean)v);
            }
        });
        return newUploadStateMap;
    }

    private INvwaDataSet queryDataSetWithRowKey(String tableName, List<ColumnModelDefine> allColumns, FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, String nodeId, boolean paged) {
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel queryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumns, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        block14: for (ColumnModelDefine columnModelDefine : allColumns) {
            Object value;
            if (!corporate) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            switch (columnModelDefine.getCode()) {
                case "FORMID": {
                    if (WorkFlowType.FORM.equals((Object)startType) && formKeys != null && formKeys.size() > 0) {
                        queryModel.getColumnFilters().put(columnModelDefine, formKeys);
                    }
                    if (!WorkFlowType.GROUP.equals((Object)startType) || groupKeys == null || groupKeys.size() <= 0) continue block14;
                    queryModel.getColumnFilters().put(columnModelDefine, groupKeys);
                    continue block14;
                }
                case "TIME_": {
                    OrderByItem orderItemTime = new OrderByItem(columnModelDefine, true);
                    queryModel.getOrderByItems().add(orderItemTime);
                    continue block14;
                }
                case "EXECUTE_ORDER": {
                    OrderByItem orderItemOrder = new OrderByItem(columnModelDefine, false);
                    queryModel.getOrderByItems().add(orderItemOrder);
                    continue block14;
                }
                case "PROCESSKEY": {
                    queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                    continue block14;
                }
            }
            String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
            if (dimensionName == null || (value = dimensionValueSet.getValue(dimensionName)) == null || "".equals(value)) continue;
            queryModel.getColumnFilters().put(columnModelDefine, value);
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableName);
        INvwaDataSet executeQueryWithRowKey = null;
        try {
            if (paged) {
                INvwaPageDataAccess dataAccess = this.iNvwaDataAccessProvider.createPageDataAccess(queryModel);
                executeQueryWithRowKey = dataAccess.executeQueryWithRowKey(context, 0, 1);
            } else {
                INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return executeQueryWithRowKey;
    }

    private MemoryDataSet<NvwaQueryColumn> queryDataSet(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, boolean paged) {
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        block14: for (ColumnModelDefine columnModelDefine : allColumns) {
            Object value;
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            switch (columnModelDefine.getCode()) {
                case "FORMID": {
                    if (WorkFlowType.FORM.equals((Object)startType) && formKeys != null && formKeys.size() > 0) {
                        queryModel.getColumnFilters().put(columnModelDefine, formKeys);
                    }
                    if (!WorkFlowType.GROUP.equals((Object)startType) || groupKeys == null || groupKeys.size() <= 0) continue block14;
                    queryModel.getColumnFilters().put(columnModelDefine, groupKeys);
                    continue block14;
                }
                case "TIME_": {
                    OrderByItem orderItemTime = new OrderByItem(columnModelDefine, true);
                    queryModel.getOrderByItems().add(orderItemTime);
                    continue block14;
                }
                case "EXECUTE_ORDER": {
                    OrderByItem orderItemOrder = new OrderByItem(columnModelDefine, false);
                    queryModel.getOrderByItems().add(orderItemOrder);
                    continue block14;
                }
                case "PROCESSKEY": {
                    String processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey());
                    if (processKey == null) continue block14;
                    queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                    continue block14;
                }
            }
            String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
            if (dimensionName == null || (value = dimensionValueSet.getValue(dimensionName)) == null || "".equals(value)) continue;
            queryModel.getColumnFilters().put(columnModelDefine, value);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        MemoryDataSet executeQuery = null;
        try {
            if (paged) {
                INvwaPageDataAccess pageDataAccess = this.iNvwaDataAccessProvider.createPageDataAccess(queryModel);
                executeQuery = pageDataAccess.executeQuery(context, 0, 1);
            } else {
                INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                executeQuery = readOnlyDataAccess.executeQuery(context);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return executeQuery;
    }

    private List<UploadRecordNew> buildUploadRecordNew(INvwaDataSet dataSetWithRowKey, List<ColumnModelDefine> allColumns, List<ColumnModelDefine> dimensionColumns, String tableName) {
        ArrayList<UploadRecordNew> uploadRecordList = new ArrayList<UploadRecordNew>();
        UploadRecordNew uploadRecordNew = null;
        if (dataSetWithRowKey == null || dataSetWithRowKey.size() == 0) {
            return uploadRecordList;
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        for (INvwaDataRow dataSet : dataSetWithRowKey) {
            uploadRecordNew = new UploadRecordNew();
            ArrayKey rowKey = dataSet.getRowKey();
            DimensionValueSet dimensionValueSet = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
            uploadRecordNew.setEntities(dimensionValueSet);
            block23: for (int j = 0; j < allColumns.size(); ++j) {
                ColumnModelDefine columnModelDefine = allColumns.get(j);
                if (columnModelDefine == null) continue;
                String value = "";
                Object obj = dataSet.getValue(columnModelDefine);
                if (obj != null) {
                    value = obj.toString();
                }
                switch (columnModelDefine.getCode()) {
                    case "FORMID": {
                        uploadRecordNew.setFormKey(value);
                        continue block23;
                    }
                    case "CMT": {
                        Object commet = dataSet.getValue(columnModelDefine);
                        if (commet == null) continue block23;
                        if (commet instanceof byte[]) {
                            byte[] bt = (byte[])commet;
                            String s = new String(bt);
                            uploadRecordNew.setCmt(s);
                            continue block23;
                        }
                        uploadRecordNew.setCmt(commet.toString());
                        continue block23;
                    }
                    case "RETURN_TYPE": {
                        uploadRecordNew.setReturnType(value);
                        continue block23;
                    }
                    case "TIME_": {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (obj == null || !(obj instanceof GregorianCalendar)) continue block23;
                        GregorianCalendar gc = (GregorianCalendar)obj;
                        Date time = gc.getTime();
                        String format = formatter.format(time);
                        uploadRecordNew.setTime(format);
                        continue block23;
                    }
                    case "OPERATOR": {
                        if (StringUtils.isEmpty((String)value)) continue block23;
                        uploadRecordNew.setOperator(value);
                        continue block23;
                    }
                    case "OPERATIONID": {
                        uploadRecordNew.setOperationid(value);
                        continue block23;
                    }
                    case "CUREVENT": {
                        uploadRecordNew.setAction(value);
                        continue block23;
                    }
                    case "CURNODE": {
                        uploadRecordNew.setTaskId(value);
                        continue block23;
                    }
                    case "ROLE_KEY": {
                        uploadRecordNew.setRoleKey(value);
                        continue block23;
                    }
                }
            }
            if (uploadRecordNew == null || uploadRecordNew.getRoleKey() == null || "".equals(uploadRecordNew.getRoleKey())) continue;
            uploadRecordList.add(uploadRecordNew);
        }
        return uploadRecordList;
    }

    @Override
    public UploadSumNew queryUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime) throws Exception {
        Integer originalNum;
        IEntityModel entityModel;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        UploadSumNew uploadSum = new UploadSumNew();
        uploadSum.setFormKey(formKey);
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFields = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        Map<String, DimensionValue> dimensionSetMap = UploadUtil.getDimensionSet(dimensionValueSet);
        String value = dimensionSetMap.get(mainDim).getValue();
        try {
            entityModel = this.iEntityMetaService.getEntityModel(unitView.getEntityId());
        }
        catch (Exception e) {
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002", (Throwable)e);
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        ArrayList<String> codeList = new ArrayList<String>(Arrays.asList(value.split(";")));
        HashSet<String> minusSumSet = new HashSet<String>();
        HashSet<String> isLeafSet = new HashSet<String>();
        HashSet<String> notDirectChildSet = new HashSet<String>();
        if (codeList.size() > 1) {
            for (String code : codeList) {
                IEntityRow entityRow = iEntityTable.findByEntityKey(code);
                if (entityRow == null) continue;
                if (iEntityTable.getChildRows(entityRow.getEntityKeyData()).size() > 0) {
                    isLeafSet.add(code);
                }
                if (!entityRow.getParentEntityKey().equals(entitySelf)) {
                    notDirectChildSet.add(code);
                }
                if (bblxField == null || !"1".equals(entityRow.getAsString(bblxField.getCode()))) continue;
                minusSumSet.add(code);
            }
        }
        if (filterDiffUnit && minusSumSet.size() > 0) {
            codeList.removeAll(minusSumSet);
        }
        if (leafEntity && isLeafSet.size() > 0) {
            codeList.removeAll(isLeafSet);
        }
        if (onlyDirectChild && notDirectChildSet.size() > 0) {
            codeList.removeAll(notDirectChildSet);
        }
        uploadSum.setMasterSum(codeList.size());
        dimensionValueSet.setValue(mainDim, codeList);
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allFields, corporate);
        int countIndex = 0;
        int stateIndex = 0;
        NvwaQueryColumn nvwaQueryColumn = null;
        for (int i = 0; i < allFields.size(); ++i) {
            ColumnModelDefine columnModelDefine = allFields.get(i);
            nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            if ("FORMID".equals(columnModelDefine.getCode())) {
                if (formKey != null) {
                    String[] split = formKey.split(";");
                    ArrayList<String> formKeyList = new ArrayList<String>();
                    formKeyList.addAll(Arrays.asList(split));
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKeyList);
                }
                nvwaQueryColumn.setAggrType(AggrType.NONE);
            } else if ("PREVEVENT".equals(columnModelDefine.getCode())) {
                stateIndex = i;
                nvwaQueryColumn.setAggrType(AggrType.NONE);
                if (!corporate) {
                    nvwaQueryModel.getGroupByColumns().add(i);
                }
            } else if ("CURNODE".equals(columnModelDefine.getCode())) {
                countIndex = i;
                nvwaQueryColumn.setAggrType(AggrType.COUNT);
            } else if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(nvwaQueryColumn);
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int j = 0; j < dimensionSet.size(); ++j) {
            String dimensionName = dimensionSet.get(j);
            ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
            if (null == column) continue;
            Object values = dimensionValueSet.getValue(dimensionName);
            if (null == value || "".equals(values)) continue;
            nvwaQueryModel.getColumnFilters().put(column, values);
        }
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), tableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            if (corporate) {
                BuildUploadCountResultSet buildUploadCountResultSet = new BuildUploadCountResultSet();
                buildUploadCountResultSet.getUploadCountResult(uploadSum, stateIndex, countIndex, (MemoryDataSet<NvwaQueryColumn>)executeQuery);
            } else {
                int size = executeQuery.size();
                String state = "";
                int stateCount = 0;
                block47: for (int k = 0; k < size; ++k) {
                    DataRow dataRow = executeQuery.get(k);
                    state = dataRow.getString(stateIndex);
                    String count = dataRow.getString(countIndex);
                    Double f = Double.valueOf(count);
                    stateCount = (int)Math.ceil(f);
                    switch (state) {
                        case "start": {
                            continue block47;
                        }
                        case "act_submit": 
                        case "cus_submit": {
                            uploadSum.setSubmitedNum(stateCount);
                            continue block47;
                        }
                        case "act_return": 
                        case "cus_return": {
                            uploadSum.setReturnedNum(stateCount);
                            continue block47;
                        }
                        case "act_upload": 
                        case "cus_upload": 
                        case "act_cancel_confirm": {
                            uploadSum.setUploadedNum(stateCount);
                            continue block47;
                        }
                        case "act_confirm": 
                        case "cus_confirm": {
                            uploadSum.setConfirmedNum(stateCount);
                            continue block47;
                        }
                        case "act_reject": 
                        case "cus_reject": {
                            uploadSum.setRejectedNum(stateCount);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Map<String, String> actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formScheme.getKey());
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                Integer unSubmitedNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setUnSubmitedNum(unSubmitedNum > 0 ? unSubmitedNum : 0);
            } else {
                originalNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
            }
        } else {
            originalNum = uploadSum.getMasterSum() - uploadSum.getRejectedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
            uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
        }
        ArrayList<UploadRecordNew> uploadActions = new ArrayList<UploadRecordNew>();
        String hiTableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine hiTable = this.nrParameterUtils.getTableByCode(hiTableCode);
        List<ColumnModelDefine> allFieldsOfHiTable = this.nrParameterUtils.getAllFieldsInTable(hiTable.getID());
        DimensionValueSet hisDimension = new DimensionValueSet(dimensionValueSet);
        Object value3 = hisDimension.getValue(mainDim);
        if (value3 instanceof List) {
            List unitKeys = (List)value3;
            unitKeys.add(entitySelf);
            unitKeys = unitKeys.stream().distinct().collect(Collectors.toList());
            hisDimension.setValue(mainDim, unitKeys);
        }
        NvwaQueryModel queryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allFieldsOfHiTable, corporate);
        for (ColumnModelDefine columnModelDefine : allFieldsOfHiTable) {
            if ("FORMID".equals(columnModelDefine.getCode()) && formKey != null) {
                queryModel.getColumnFilters().put(columnModelDefine, formKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem item = new OrderByItem(columnModelDefine, false);
                queryModel.getOrderByItems().add(item);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger1 = this.dataEngineAdapter.getDimensionChanger(hiTable.getName());
        DimensionSet dimensionSet1 = hisDimension.getDimensionSet();
        for (int j = 0; j < dimensionSet1.size(); ++j) {
            String dimensionName = dimensionSet1.get(j);
            ColumnModelDefine column = dimensionChanger1.getColumn(dimensionName);
            if (null == column) continue;
            Object values = hisDimension.getValue(dimensionName);
            if (null == value || "".equals(value)) continue;
            queryModel.getColumnFilters().put(column, values);
        }
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(hiTable, allFieldsOfHiTable);
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), hiTableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow dataRow : executeQueryWithRowKey) {
                UploadRecordNew uploadAction = new UploadRecordNew();
                ArrayKey rowKey = dataRow.getRowKey();
                DimensionValueSet dimension = dimensionChanger1.getDimensionValueSet(rowKey, dimensionValueSetColumns);
                uploadAction.setEntities(dimension);
                block51: for (int m = 0; m < allFieldsOfHiTable.size(); ++m) {
                    ColumnModelDefine columnModelDefine = allFieldsOfHiTable.get(m);
                    Object fieldValue = dataRow.getValue(m);
                    switch (columnModelDefine.getCode()) {
                        case "CURNODE": {
                            if (fieldValue == null) continue block51;
                            uploadAction.setTaskId(fieldValue.toString());
                            continue block51;
                        }
                        case "TIME_": {
                            Object value2 = dataRow.getValue(m);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value2 == null || !(value2 instanceof GregorianCalendar)) continue block51;
                            GregorianCalendar gc = (GregorianCalendar)value2;
                            Date time = gc.getTime();
                            String format = formatter.format(time);
                            uploadAction.setTime(format);
                            continue block51;
                        }
                        case "CUREVENT": {
                            if (fieldValue == null) continue block51;
                            uploadAction.setAction(fieldValue.toString());
                            continue block51;
                        }
                        case "OPERATOR": {
                            if (fieldValue == null) continue block51;
                            uploadAction.setOperator(fieldValue.toString());
                            continue block51;
                        }
                        case "RETURN_TYPE": {
                            if (fieldValue == null) continue block51;
                            uploadAction.setReturnType(fieldValue.toString());
                            continue block51;
                        }
                        case "CMT": {
                            Object comment = dataRow.getValue(m);
                            if (comment == null) continue block51;
                            if (comment instanceof byte[]) {
                                byte[] bt = (byte[])comment;
                                String s = new String(bt);
                                uploadAction.setCmt(s);
                                continue block51;
                            }
                            uploadAction.setCmt(comment.toString());
                            continue block51;
                        }
                    }
                }
                if (uploadAction == null || uploadAction.getTaskId() == null || "".equals(uploadAction.getTaskId())) continue;
                uploadActions.add(uploadAction);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.initUserTitles(uploadActions);
        int num = 0;
        for (UploadRecordNew upload : uploadActions) {
            DimensionValueSet entities = upload.getEntities();
            String unitKey = entities.getValue(mainDim).toString();
            if ("act_reject".equals(upload.getAction()) || "cus_reject".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setRejectTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setReturnType(this.showProcess.getReturnTypeTitle(formScheme.getKey(), upload.getReturnType()));
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
                uploadSum.setRejectedExplain(upload.getCmt());
            }
            if ("act_submit".equals(upload.getAction()) || "cus_submit".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setSubmitedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_return".equals(upload.getAction()) || "cus_return".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setReturnedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_upload".equals(upload.getAction()) || "cus_upload".equals(upload.getAction())) {
                if (StringUtils.isEmpty((String)uploadSum.getEndUploadTime())) {
                    if (entitySelf.equals(unitKey)) {
                        uploadSum.setFirstUploadTime(upload.getTime());
                    }
                    uploadSum.setFirstUploadExplain(upload.getCmt());
                    uploadSum.setOperator(upload.getOperator());
                    uploadSum.setTime(upload.getTime());
                }
                ++num;
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setEndUploadTime(upload.getTime());
                }
                uploadSum.setUploadExplain(upload.getCmt());
            }
            if ("act_confirm".equals(upload.getAction()) || "cus_confirm".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setComfirmedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if (!"act_cancel_confirm".equals(upload.getAction())) continue;
            if (entitySelf.equals(unitKey)) {
                uploadSum.setCancelConfirmTime(upload.getTime());
            }
            uploadSum.setOperator(upload.getOperator());
            uploadSum.setTime(upload.getTime());
        }
        uploadSum.setUploadNums(num);
        if (abortTime != null) {
            int delayCount = this.queryUploadDelay(formScheme, dimensionValueSet, formKey, abortTime).size();
            if (this.isDelay(abortTime)) {
                delayCount += uploadSum.getOriginalNum();
            }
            uploadSum.setDelayNum(delayCount);
        }
        return uploadSum;
    }

    @Override
    public List<UploadAllFormSumInfo> queryAllFormState(DimensionValueSet dimensionValueSet, String formKeys, FormSchemeDefine formScheme, List<String> entityIds, WorkFlowType queryStartType, Map<String, UploadAllFormSumInfo> formToSum) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        LinkedList<UploadAllFormSumInfo> uploadSumList = new LinkedList<UploadAllFormSumInfo>();
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFields = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allFields, corporate);
        NvwaQueryColumn nvwaQueryColumn = null;
        int actionIndex = 0;
        int nodeIndex = 0;
        int formIndex = 0;
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (int i = 0; i < allFields.size(); ++i) {
            ColumnModelDefine columnModelDefine = allFields.get(i);
            switch (columnModelDefine.getCode()) {
                case "PREVEVENT": {
                    actionIndex = i;
                    nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
                    nvwaQueryColumn.setAggrType(AggrType.NONE);
                    if (corporate) break;
                    nvwaQueryModel.getGroupByColumns().add(i);
                    break;
                }
                case "CURNODE": {
                    nodeIndex = i;
                    nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
                    nvwaQueryColumn.setAggrType(AggrType.COUNT);
                    break;
                }
                case "FORMID": {
                    formIndex = i;
                    nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
                    if (formKeys != null && !formKeys.equals("allForm")) {
                        ArrayList<String> formKeyList = new ArrayList<String>();
                        formKeyList.addAll(Arrays.asList(formKeys.split(";")));
                        nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKeyList);
                    }
                    if (corporate) break;
                    nvwaQueryModel.getGroupByColumns().add(i);
                    break;
                }
                case "PROCESSKEY": {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                    break;
                }
                default: {
                    Object value;
                    String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
                    if (dimensionName != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                        nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                    nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
                }
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(nvwaQueryColumn);
        }
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            if (corporate) {
                BuildUploadCountResultSet buildUploadCountResultSet = new BuildUploadCountResultSet();
                buildUploadCountResultSet.getFormUploadCountResult(actionIndex, nodeIndex, formIndex, (MemoryDataSet<NvwaQueryColumn>)executeQuery, formToSum);
            } else {
                int size = executeQuery.size();
                String action = "";
                int stateCount = 0;
                String formKey = "";
                block35: for (int j = 0; j < size; ++j) {
                    DataRow dataRow = executeQuery.get(j);
                    action = dataRow.getString(actionIndex);
                    formKey = dataRow.getString(formIndex);
                    stateCount = dataRow.getInt(nodeIndex);
                    switch (action) {
                        case "act_submit": 
                        case "cus_submit": {
                            if (!formToSum.containsKey(formKey)) continue block35;
                            formToSum.get(formKey).setSubmitedNum(formToSum.get(formKey).getSubmitedNum() + stateCount);
                            continue block35;
                        }
                        case "act_return": 
                        case "cus_return": {
                            if (!formToSum.containsKey(formKey)) continue block35;
                            formToSum.get(formKey).setReturnedNum(formToSum.get(formKey).getReturnedNum() + stateCount);
                            continue block35;
                        }
                        case "act_upload": 
                        case "cus_upload": 
                        case "act_cancel_confirm": {
                            if (!formToSum.containsKey(formKey)) continue block35;
                            formToSum.get(formKey).setUploadedNum(stateCount);
                            continue block35;
                        }
                        case "act_confirm": 
                        case "cus_confirm": {
                            if (!formToSum.containsKey(formKey)) continue block35;
                            formToSum.get(formKey).setConfirmedNum(formToSum.get(formKey).getConfirmedNum() + stateCount);
                            continue block35;
                        }
                        case "act_reject": 
                        case "cus_reject": {
                            if (!formToSum.containsKey(formKey)) continue block35;
                            formToSum.get(formKey).setRejectedNum(formToSum.get(formKey).getRejectedNum() + stateCount);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (String key : formToSum.keySet()) {
            List currFormSchemeFormGroup;
            List formGroupsByFormKey;
            UploadAllFormSumInfo uploadAllFormSumInfo2 = formToSum.get(key);
            Map<String, String> actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formScheme.getKey());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
            if (defaultWorkflow) {
                if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                    formToSum.get(key).setUnSubmitedNum(uploadAllFormSumInfo2.getMasterSum() - uploadAllFormSumInfo2.getSubmitedNum() - uploadAllFormSumInfo2.getRejectedNum() - uploadAllFormSumInfo2.getReturnedNum() - uploadAllFormSumInfo2.getUploadedNum() - uploadAllFormSumInfo2.getConfirmedNum());
                } else {
                    formToSum.get(key).setOriginalNum(uploadAllFormSumInfo2.getMasterSum() - uploadAllFormSumInfo2.getSubmitedNum() - uploadAllFormSumInfo2.getRejectedNum() - uploadAllFormSumInfo2.getReturnedNum() - uploadAllFormSumInfo2.getUploadedNum() - uploadAllFormSumInfo2.getConfirmedNum());
                }
            } else {
                formToSum.get(key).setOriginalNum(uploadAllFormSumInfo2.getMasterSum() - uploadAllFormSumInfo2.getRejectedNum() - uploadAllFormSumInfo2.getUploadedNum() - uploadAllFormSumInfo2.getConfirmedNum());
            }
            FormGroupDefine queryFormGroup = null;
            FormDefine queryFormById = null;
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                queryFormGroup = this.runTimeViewController.queryFormGroup(key);
            } else if (WorkFlowType.FORM.equals((Object)queryStartType) && (formGroupsByFormKey = this.runTimeViewController.getFormGroupsByFormKey((queryFormById = this.runTimeViewController.queryFormById(key)).getKey())) != null && formGroupsByFormKey.size() > 0 && (currFormSchemeFormGroup = formGroupsByFormKey.stream().filter(item -> item.getFormSchemeKey().equals(formScheme.getKey())).collect(Collectors.toList())) != null && currFormSchemeFormGroup.size() > 0) {
                queryFormGroup = (FormGroupDefine)currFormSchemeFormGroup.get(0);
            }
            UploadAllFormSumInfo uploadAllFormSumInfo = formToSum.get(key);
            uploadAllFormSumInfo.setFormId(key);
            uploadAllFormSumInfo.setFormTitle(queryFormById == null ? "" : queryFormById.getTitle());
            if (queryFormGroup != null) {
                uploadAllFormSumInfo.setFormGroupId(queryFormGroup.getKey());
                uploadAllFormSumInfo.setFormGroupTitle(queryFormGroup.getTitle());
            }
            uploadSumList.add(uploadAllFormSumInfo);
        }
        return uploadSumList;
    }

    @Override
    public List<UploadStateNew> queryUploadDelay(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String formKey, Calendar abortTime) {
        ArrayList<String> formKeys = new ArrayList<String>();
        if (org.springframework.util.StringUtils.hasLength(formKey)) {
            formKeys.add(formKey);
        }
        List<UploadStateNew> uploadStates = this.queryUploadStateNew(formScheme, dimensionValueSet, formKeys);
        Date delayTime = new Date();
        List alreadyUpload = uploadStates.stream().filter(e -> e.getUpdateTime() != null && this.filterUploadNews.apply((UploadStateNew)e) != false).collect(Collectors.toList());
        if (abortTime != null) {
            delayTime = abortTime.getTime();
        }
        Date finalDelayTime = delayTime;
        List<UploadStateNew> delayUpload = alreadyUpload.stream().filter(e -> e.getUpdateTime().compareTo(finalDelayTime) > 0).collect(Collectors.toList());
        return delayUpload;
    }

    private boolean isDelay(Calendar abortTime) {
        return abortTime.getTime().compareTo(new Date()) < 0;
    }

    @Override
    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupkeys) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        UploadStateNew uploadState = null;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String processKey;
            List<String> formGroupKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && (formGroupKey = this.getDbFormGroupKeys(startType, formKeys, groupkeys, formScheme)) != null && formGroupKey.size() > 0) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formGroupKey);
            }
            if ("UPDATE_TIME".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, processKey);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (!Objects.nonNull(value)) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
                if (uploadState == null || uploadState.getTaskId() == null) continue;
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    @Override
    public UploadStateNew queryUploadStateNew(DimensionValueSet dimensionValueSet, String formKey, String fromGroupKey, FormSchemeDefine formScheme) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String dbFormGroupKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && (dbFormGroupKey = this.getDbFormGroupKey(startType, formKey, fromGroupKey, formScheme)) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, dbFormGroupKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        UploadStateNew uploadState = new UploadStateNew();
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (value == null) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadState;
    }

    @Override
    public void deleteHistoryStateData(FormSchemeDefine formScheme) {
        String tableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFieldsInTable = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allFieldsInTable) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(nvwaQueryModel);
        try {
            INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
            openForUpdate.deleteAll();
            openForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStateData(FormSchemeDefine formScheme) {
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFieldsInTable = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allFieldsInTable) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(nvwaQueryModel);
        try {
            INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
            openForUpdate.deleteAll();
            openForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int queryStateData(FormSchemeDefine formScheme) {
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        int size = 0;
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            size = executeQuery.size();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return size;
    }

    @Override
    public int queryHistoryStateData(FormSchemeDefine formScheme) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        int size = 0;
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            size = executeQuery.size();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return size;
    }

    @Override
    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine formScheme, String period, String nodeCode) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        UploadStateNew uploadState = null;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            String dimensionName;
            if ("CURNODE".equals(columnModelDefine.getCode()) && nodeCode != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, nodeCode);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && "DATATIME".equals(dimensionName) && period != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, period);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                String action = "";
                String node = "";
                String forceUpload = "";
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (!Objects.nonNull(value)) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            uploadState.setForce(forceUpload);
                            continue block20;
                        }
                    }
                }
                if ("act_other_start".equals(action) || "act_other_submit".equals(action) || "act_other_upload".equals(action) || "act_other_confirm".equals(action)) continue;
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
                if (uploadState == null || uploadState.getTaskId() == null) continue;
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    @Override
    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime) throws Exception {
        Integer originalNum;
        IEntityModel entityModel;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        UploadSumNew uploadSum = new UploadSumNew();
        uploadSum.setFormKey(formKey);
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFields = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        Map<String, DimensionValue> dimensionSetMap = UploadUtil.getDimensionSet(dimensionValueSet);
        String value = dimensionSetMap.get(mainDim).getValue();
        try {
            entityModel = this.iEntityMetaService.getEntityModel(unitView.getEntityId());
        }
        catch (Exception e) {
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002", (Throwable)e);
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        ArrayList<String> codeList = new ArrayList<String>(Arrays.asList(value.split(";")));
        HashSet<String> minusSumSet = new HashSet<String>();
        HashSet<String> isLeafSet = new HashSet<String>();
        HashSet notDirectChildSet = new HashSet();
        if (codeList.size() > 1) {
            for (String code : codeList) {
                IEntityRow entityRow = iEntityTable.findByEntityKey(code);
                if (entityRow == null) continue;
                if (iEntityTable.getChildRows(entityRow.getEntityKeyData()).size() > 0) {
                    isLeafSet.add(code);
                }
                if (bblxField == null || !"1".equals(entityRow.getAsString(bblxField.getCode()))) continue;
                minusSumSet.add(code);
            }
        }
        if (filterDiffUnit && minusSumSet.size() > 0) {
            codeList.removeAll(minusSumSet);
        }
        if (leafEntity && isLeafSet.size() > 0) {
            codeList.removeAll(isLeafSet);
        }
        uploadSum.setMasterSum(codeList.size());
        dimensionValueSet.setValue(mainDim, codeList);
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(dimensionValueSet.getValue("DATATIME").toString(), taskDefine, allFields, corporate);
        int countIndex = 0;
        int stateIndex = 0;
        NvwaQueryColumn nvwaQueryColumn = null;
        for (int i = 0; i < allFields.size(); ++i) {
            ColumnModelDefine columnModelDefine = allFields.get(i);
            nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            if ("FORMID".equals(columnModelDefine.getCode())) {
                if (formKey != null) {
                    String[] split = formKey.split(";");
                    ArrayList<String> formKeyList = new ArrayList<String>();
                    formKeyList.addAll(Arrays.asList(split));
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKeyList);
                }
                nvwaQueryColumn.setAggrType(AggrType.NONE);
            } else if ("PREVEVENT".equals(columnModelDefine.getCode())) {
                stateIndex = i;
                nvwaQueryColumn.setAggrType(AggrType.NONE);
                if (!corporate) {
                    nvwaQueryModel.getGroupByColumns().add(i);
                }
            } else if ("CURNODE".equals(columnModelDefine.getCode())) {
                countIndex = i;
                nvwaQueryColumn.setAggrType(AggrType.COUNT);
            } else if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(nvwaQueryColumn);
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int j = 0; j < dimensionSet.size(); ++j) {
            String dimensionName = dimensionSet.get(j);
            ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
            if (null == column) continue;
            Object values = dimensionValueSet.getValue(dimensionName);
            if (null == value || "".equals(values)) continue;
            nvwaQueryModel.getColumnFilters().put(column, values);
        }
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), tableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            if (corporate) {
                BuildUploadCountResultSet buildUploadCountResultSet = new BuildUploadCountResultSet();
                buildUploadCountResultSet.getUploadCountResult(uploadSum, stateIndex, countIndex, (MemoryDataSet<NvwaQueryColumn>)executeQuery);
            } else {
                int size = executeQuery.size();
                String state = "";
                int stateCount = 0;
                block43: for (int k = 0; k < size; ++k) {
                    DataRow dataRow = executeQuery.get(k);
                    state = dataRow.getString(stateIndex);
                    String count = dataRow.getString(countIndex);
                    Double f = Double.valueOf(count);
                    stateCount = (int)Math.ceil(f);
                    switch (state) {
                        case "start": {
                            continue block43;
                        }
                        case "act_submit": 
                        case "cus_submit": {
                            uploadSum.setSubmitedNum(stateCount);
                            continue block43;
                        }
                        case "act_return": 
                        case "cus_return": {
                            uploadSum.setReturnedNum(stateCount);
                            continue block43;
                        }
                        case "act_upload": 
                        case "cus_upload": 
                        case "act_cancel_confirm": {
                            uploadSum.setUploadedNum(stateCount);
                            continue block43;
                        }
                        case "act_confirm": 
                        case "cus_confirm": {
                            uploadSum.setConfirmedNum(stateCount);
                            continue block43;
                        }
                        case "act_reject": 
                        case "cus_reject": {
                            uploadSum.setRejectedNum(stateCount);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Map<String, String> actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formScheme.getKey());
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                Integer unSubmitedNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setUnSubmitedNum(unSubmitedNum > 0 ? unSubmitedNum : 0);
            } else {
                originalNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
            }
        } else {
            originalNum = uploadSum.getMasterSum() - uploadSum.getRejectedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
            uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
        }
        ArrayList<UploadRecordNew> uploadActions = new ArrayList<UploadRecordNew>();
        String hiTableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine hiTable = this.nrParameterUtils.getTableByCode(hiTableCode);
        List<ColumnModelDefine> allFieldsOfHiTable = this.nrParameterUtils.getAllFieldsInTable(hiTable.getID());
        DimensionValueSet hisDimension = new DimensionValueSet(dimensionValueSet);
        Object value3 = hisDimension.getValue(mainDim);
        if (value3 instanceof List) {
            List unitKeys = (List)value3;
            unitKeys.add(entitySelf);
            unitKeys = unitKeys.stream().distinct().collect(Collectors.toList());
            hisDimension.setValue(mainDim, unitKeys);
        }
        NvwaQueryModel queryModel = this.corporateUtil.buildNvwaQueryModel(dimensionValueSet.getValue("DATATIME").toString(), taskDefine, allFieldsOfHiTable, corporate);
        for (ColumnModelDefine columnModelDefine : allFieldsOfHiTable) {
            if ("FORMID".equals(columnModelDefine.getCode()) && formKey != null) {
                queryModel.getColumnFilters().put(columnModelDefine, formKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem item = new OrderByItem(columnModelDefine, false);
                queryModel.getOrderByItems().add(item);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger1 = this.dataEngineAdapter.getDimensionChanger(hiTable.getName());
        DimensionSet dimensionSet1 = hisDimension.getDimensionSet();
        for (int j = 0; j < dimensionSet1.size(); ++j) {
            String dimensionName = dimensionSet1.get(j);
            ColumnModelDefine column = dimensionChanger1.getColumn(dimensionName);
            if (null == column) continue;
            Object values = hisDimension.getValue(dimensionName);
            if (null == value) continue;
            queryModel.getColumnFilters().put(column, values);
        }
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(hiTable, allFieldsOfHiTable);
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), hiTableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow dataRow : executeQueryWithRowKey) {
                UploadRecordNew uploadAction = new UploadRecordNew();
                ArrayKey rowKey = dataRow.getRowKey();
                DimensionValueSet dimension = dimensionChanger1.getDimensionValueSet(rowKey, dimensionValueSetColumns);
                uploadAction.setEntities(dimension);
                block47: for (int m = 0; m < allFieldsOfHiTable.size(); ++m) {
                    ColumnModelDefine columnModelDefine = allFieldsOfHiTable.get(m);
                    Object fieldValue = dataRow.getValue(m);
                    switch (columnModelDefine.getCode()) {
                        case "TIME_": {
                            Object value2 = dataRow.getValue(m);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value2 == null || !(value2 instanceof GregorianCalendar)) continue block47;
                            GregorianCalendar gc = (GregorianCalendar)value2;
                            Date time = gc.getTime();
                            String format = formatter.format(time);
                            uploadAction.setTime(format);
                            continue block47;
                        }
                        case "CUREVENT": {
                            if (fieldValue == null) continue block47;
                            uploadAction.setAction(fieldValue.toString());
                            continue block47;
                        }
                        case "OPERATOR": {
                            if (fieldValue == null) continue block47;
                            uploadAction.setOperator(fieldValue.toString());
                            continue block47;
                        }
                        case "CMT": {
                            Object comment = dataRow.getValue(m);
                            if (comment == null) continue block47;
                            if (comment instanceof byte[]) {
                                byte[] bt = (byte[])comment;
                                String s = new String(bt);
                                uploadAction.setCmt(s);
                                continue block47;
                            }
                            uploadAction.setCmt(comment.toString());
                            continue block47;
                        }
                    }
                }
                uploadActions.add(uploadAction);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.initUserTitles(uploadActions);
        int num = 0;
        for (UploadRecordNew upload : uploadActions) {
            DimensionValueSet entities = upload.getEntities();
            String unitKey = entities.getValue(mainDim).toString();
            if ("act_reject".equals(upload.getAction()) || "cus_reject".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setRejectTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
                uploadSum.setRejectedExplain(upload.getCmt());
            }
            if ("act_submit".equals(upload.getAction()) || "cus_submit".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setSubmitedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_return".equals(upload.getAction()) || "cus_return".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setReturnedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_upload".equals(upload.getAction()) || "cus_upload".equals(upload.getAction())) {
                if (StringUtils.isEmpty((String)uploadSum.getEndUploadTime())) {
                    if (entitySelf.equals(unitKey)) {
                        uploadSum.setFirstUploadTime(upload.getTime());
                    }
                    uploadSum.setFirstUploadExplain(upload.getCmt());
                    uploadSum.setOperator(upload.getOperator());
                    uploadSum.setTime(upload.getTime());
                }
                ++num;
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setEndUploadTime(upload.getTime());
                }
                uploadSum.setUploadExplain(upload.getCmt());
            }
            if ("act_confirm".equals(upload.getAction()) || "cus_confirm".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setComfirmedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if (!"act_cancel_confirm".equals(upload.getAction())) continue;
            if (entitySelf.equals(unitKey)) {
                uploadSum.setCancelConfirmTime(upload.getTime());
            }
            uploadSum.setOperator(upload.getOperator());
            uploadSum.setTime(upload.getTime());
        }
        uploadSum.setUploadNums(num);
        if (abortTime != null) {
            int delayCount = this.queryUploadDelay(formScheme, dimensionValueSet, formKey, abortTime).size();
            if (this.isDelay(abortTime)) {
                delayCount += uploadSum.getOriginalNum();
            }
            uploadSum.setDelayNum(delayCount);
        }
        return uploadSum;
    }

    @Override
    public UploadStateNew queryUploadState(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey, String corporateValue) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporateValue, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String dbFormGroupKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && (dbFormGroupKey = this.getDbFormGroupKey(startType, formKey, formGroupKey, formScheme)) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, dbFormGroupKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode, corporateValue);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        UploadStateNew uploadState = new UploadStateNew();
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (value == null) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (null == value || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadState;
    }

    @Override
    public List<UploadStateNew> queryUploadState(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupkeys, String corporateValue) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        UploadStateNew uploadState = null;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumnModels = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumnModels, corporateValue, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            String processKey;
            List<String> formGroupKey;
            if ("FORMID".equals(columnModelDefine.getCode()) && (formGroupKey = this.getDbFormGroupKeys(startType, formKeys, groupkeys, formScheme)) != null && formGroupKey.size() > 0) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formGroupKey);
            }
            if ("UPDATE_TIME".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode()) && (processKey = this.nrParameterUtils.getProcessKey(formScheme.getKey())) != null) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, processKey);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dimensionValueSet.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumnModels);
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode, corporateValue);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            String action = "";
            String node = "";
            String forceUpload = "";
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            action = value.toString();
                            uploadState.setPreEvent(action);
                            continue block20;
                        }
                        case "CURNODE": {
                            node = value.toString();
                            uploadState.setTaskId(node);
                            continue block20;
                        }
                        case "FORMID": {
                            if (!Objects.nonNull(value)) continue block20;
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (value == null || !(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            forceUpload = value.toString();
                            continue block20;
                        }
                    }
                }
                uploadState.setActionStateBean(this.getState(action, node, forceUpload, formScheme, defaultWorkflow, startType, dimensionValue));
                if (uploadState == null || uploadState.getTaskId() == null) continue;
                uploadStates.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadStates;
    }

    @Override
    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String nodeCode, List<String> roleKeys) {
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String period = "";
        Object value1 = dimensionValueSet.getValue("DATATIME");
        if (value1 != null) {
            period = dimensionValueSet.getValue("DATATIME").toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        NvwaQueryModel queryModel = this.corporateUtil.buildNvwaQueryModel(period, taskDefine, allColumns, corporate);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableCode);
        block16: for (int i = 0; i < allColumns.size(); ++i) {
            ColumnModelDefine columnModelDefine = allColumns.get(i);
            if (!corporate) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            switch (columnModelDefine.getCode()) {
                case "CURNODE": {
                    if (null == nodeCode) continue block16;
                    queryModel.getColumnFilters().put(columnModelDefine, nodeCode);
                    continue block16;
                }
                case "ROLE_KEY": {
                    if (roleKeys == null || roleKeys.size() <= 0) continue block16;
                    queryModel.getColumnFilters().put(columnModelDefine, roleKeys);
                    continue block16;
                }
                case "TIME_": {
                    OrderByItem orderItemTime = new OrderByItem(columnModelDefine, true);
                    queryModel.getOrderByItems().add(orderItemTime);
                    continue block16;
                }
                case "EXECUTE_ORDER": {
                    OrderByItem orderItemOrder = new OrderByItem(columnModelDefine, false);
                    queryModel.getOrderByItems().add(orderItemOrder);
                    continue block16;
                }
                case "PROCESSKEY": {
                    queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                    continue block16;
                }
                default: {
                    Object value;
                    String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
                    if (dimensionName == null || (value = dimensionValueSet.getValue(dimensionName)) == null || "".equals(value)) continue block16;
                    queryModel.getColumnFilters().put(columnModelDefine, value);
                }
            }
        }
        DataAccessContext context = this.corporateUtil.buildDataAccessContext(formScheme.getTaskKey(), tableCode);
        INvwaDataSet executeQueryWithRowKey = null;
        try {
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        List<UploadRecordNew> uploadActions = this.buildUploadRecordNew(executeQueryWithRowKey, allColumns, dimensionColumns, table.getName());
        return uploadActions;
    }

    @Override
    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime, Map<String, List<String>> statisticalStates) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        UploadSumNew uploadSum = new UploadSumNew();
        uploadSum.setFormKey(formKey);
        String period = "";
        Object periodObj = dimensionValueSet.getValue("DATATIME");
        if (periodObj != null) {
            period = periodObj.toString();
        }
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        this.queryState(uploadSum, formScheme, dimensionValueSet, period, taskDefine, entitySelf, formKey, corporate, mainDim, unitView, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, flowsType, statisticalStates);
        this.queryHisState(uploadSum, formScheme, dimensionValueSet, period, taskDefine, entitySelf, formKey, corporate, mainDim);
        if (abortTime != null) {
            int delayCount = this.queryUploadDelay(formScheme, dimensionValueSet, formKey, abortTime).size();
            if (this.isDelay(abortTime)) {
                delayCount += uploadSum.getOriginalNum();
            }
            uploadSum.setDelayNum(delayCount);
        }
        return uploadSum;
    }

    private void queryState(UploadSumNew uploadSum, FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String period, TaskDefine taskDefine, String entitySelf, String formKey, boolean corporate, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, boolean flowsType, Map<String, List<String>> statisticalStates) throws Exception {
        Integer originalNum;
        IEntityModel entityModel;
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allFields = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        Map<String, DimensionValue> dimensionSetMap = UploadUtil.getDimensionSet(dimensionValueSet);
        String unitKey = dimensionSetMap.get(mainDim).getValue();
        try {
            entityModel = this.iEntityMetaService.getEntityModel(unitView.getEntityId());
        }
        catch (Exception e) {
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002", (Throwable)e);
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        ArrayList<String> codeList = new ArrayList<String>(Arrays.asList(unitKey.split(";")));
        HashSet<String> minusSumSet = new HashSet<String>();
        HashSet<String> isLeafSet = new HashSet<String>();
        HashSet notDirectChildSet = new HashSet();
        if (codeList.size() > 1) {
            for (String code : codeList) {
                IEntityRow entityRow = iEntityTable.findByEntityKey(code);
                if (entityRow == null) continue;
                if (iEntityTable.getChildRows(entityRow.getEntityKeyData()).size() > 0) {
                    isLeafSet.add(code);
                }
                if (bblxField == null || !"1".equals(entityRow.getAsString(bblxField.getCode()))) continue;
                minusSumSet.add(code);
            }
        }
        if (filterDiffUnit && minusSumSet.size() > 0) {
            codeList.removeAll(minusSumSet);
        }
        if (leafEntity && isLeafSet.size() > 0) {
            codeList.removeAll(isLeafSet);
        }
        uploadSum.setMasterSum(codeList.size());
        dimensionValueSet.setValue(mainDim, codeList);
        NvwaQueryModel nvwaQueryModel = this.corporateUtil.buildNvwaQueryModel(dimensionValueSet.getValue("DATATIME").toString(), taskDefine, allFields, corporate);
        int countIndex = 0;
        int stateIndex = 0;
        int preveventIndex = 0;
        int curNodeIndex = 0;
        NvwaQueryColumn nvwaQueryColumn = null;
        for (int i = 0; i < allFields.size(); ++i) {
            ColumnModelDefine columnModelDefine = allFields.get(i);
            nvwaQueryColumn = new NvwaQueryColumn(columnModelDefine);
            if ("FORMID".equals(columnModelDefine.getCode())) {
                if (formKey != null) {
                    String[] split = formKey.split(";");
                    ArrayList<String> formKeyList = new ArrayList<String>();
                    formKeyList.addAll(Arrays.asList(split));
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, formKeyList);
                }
            } else if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            } else if ("PREVEVENT".equals(columnModelDefine.getCode())) {
                preveventIndex = i;
            } else if ("CURNODE".equals(columnModelDefine.getCode())) {
                curNodeIndex = i;
            }
            if (corporate) continue;
            nvwaQueryModel.getColumns().add(nvwaQueryColumn);
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int j = 0; j < dimensionSet.size(); ++j) {
            Object values;
            String dimensionName = dimensionSet.get(j);
            ColumnModelDefine column = dimensionChanger.getColumn(dimensionName);
            if (null == column || null == (values = dimensionValueSet.getValue(dimensionName)) || "".equals(values)) continue;
            nvwaQueryModel.getColumnFilters().put(column, values);
        }
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), tableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            MemoryDataSet executeQuery = readOnlyDataAccess.executeQuery(context);
            if (corporate) {
                BuildUploadCountResultSet buildUploadCountResultSet = new BuildUploadCountResultSet();
                buildUploadCountResultSet.getUploadCountResult(uploadSum, stateIndex, countIndex, (MemoryDataSet<NvwaQueryColumn>)executeQuery);
            } else {
                HashMap<String, Integer> countMap = new HashMap<String, Integer>();
                for (Map.Entry<String, List<String>> stateMap : statisticalStates.entrySet()) {
                    countMap.put("custom@" + stateMap.getKey(), 0);
                }
                int size = executeQuery.size();
                String state = "";
                boolean stateCount = false;
                for (int k = 0; k < size; ++k) {
                    DataRow dataRow = executeQuery.get(k);
                    String preActionCode = dataRow.getString(preveventIndex);
                    String curNodeCode = dataRow.getString(curNodeIndex);
                    String stateCode = this.convertStateCode(preActionCode);
                    String customCode = curNodeCode + "@" + stateCode;
                    for (Map.Entry<String, List<String>> stateMap : statisticalStates.entrySet()) {
                        String key = stateMap.getKey();
                        List<String> value = stateMap.getValue();
                        if (!value.contains(customCode)) continue;
                        Integer num = (Integer)countMap.get("custom@" + key);
                        num = num + 1;
                        countMap.put("custom@" + key, num);
                    }
                    if (preActionCode.equals("act_upload") || preActionCode.equals("cus_upload") || preActionCode.equals("act_cancel_confirm")) {
                        uploadSum.addUploadNum();
                    }
                    if (!"act_confirm".equals(preActionCode) && !"cus_confirm".equals(preActionCode)) continue;
                    uploadSum.addConfirmNum();
                }
                uploadSum.setCustomStateMap(countMap);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Map<String, String> actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formScheme.getKey());
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                Integer unSubmitedNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setUnSubmitedNum(unSubmitedNum > 0 ? unSubmitedNum : 0);
            } else {
                originalNum = uploadSum.getMasterSum() - uploadSum.getSubmitedNum() - uploadSum.getRejectedNum() - uploadSum.getReturnedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
                uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
            }
        } else {
            originalNum = uploadSum.getMasterSum() - uploadSum.getRejectedNum() - uploadSum.getUploadedNum() - uploadSum.getConfirmedNum();
            uploadSum.setOriginalNum(originalNum > 0 ? originalNum : 0);
        }
    }

    private void queryHisState(UploadSumNew uploadSum, FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String period, TaskDefine taskDefine, String entitySelf, String formKey, boolean corporate, String mainDim) {
        ArrayList<UploadRecordNew> uploadActions = new ArrayList<UploadRecordNew>();
        String hiTableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine hiTable = this.nrParameterUtils.getTableByCode(hiTableCode);
        List<ColumnModelDefine> allFieldsOfHiTable = this.nrParameterUtils.getAllFieldsInTable(hiTable.getID());
        DimensionValueSet hisDimension = new DimensionValueSet(dimensionValueSet);
        Object value3 = hisDimension.getValue(mainDim);
        if (value3 instanceof List) {
            List unitKeys = (List)value3;
            unitKeys.add(entitySelf);
            unitKeys = unitKeys.stream().distinct().collect(Collectors.toList());
            hisDimension.setValue(mainDim, unitKeys);
        }
        NvwaQueryModel queryModel = this.corporateUtil.buildNvwaQueryModel(dimensionValueSet.getValue("DATATIME").toString(), taskDefine, allFieldsOfHiTable, corporate);
        for (ColumnModelDefine columnModelDefine : allFieldsOfHiTable) {
            if ("FORMID".equals(columnModelDefine.getCode()) && formKey != null) {
                queryModel.getColumnFilters().put(columnModelDefine, formKey);
            }
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem item = new OrderByItem(columnModelDefine, false);
                queryModel.getOrderByItems().add(item);
            }
            if ("PROCESSKEY".equals(columnModelDefine.getCode())) {
                queryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            if (corporate) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger1 = this.dataEngineAdapter.getDimensionChanger(hiTable.getName());
        DimensionSet dimensionSet1 = hisDimension.getDimensionSet();
        for (int j = 0; j < dimensionSet1.size(); ++j) {
            Object values;
            String dimensionName = dimensionSet1.get(j);
            ColumnModelDefine column = dimensionChanger1.getColumn(dimensionName);
            if (null == column || null == (values = hisDimension.getValue(dimensionName))) continue;
            queryModel.getColumnFilters().put(column, values);
        }
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(hiTable, allFieldsOfHiTable);
        try {
            DataAccessContext context = this.corporateUtil.buildDataAccessContext(taskDefine.getKey(), hiTableCode);
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow dataRow : executeQueryWithRowKey) {
                UploadRecordNew uploadAction = new UploadRecordNew();
                ArrayKey rowKey = dataRow.getRowKey();
                DimensionValueSet dimension = dimensionChanger1.getDimensionValueSet(rowKey, dimensionValueSetColumns);
                uploadAction.setEntities(dimension);
                block17: for (int m = 0; m < allFieldsOfHiTable.size(); ++m) {
                    ColumnModelDefine columnModelDefine = allFieldsOfHiTable.get(m);
                    Object fieldValue = dataRow.getValue(m);
                    switch (columnModelDefine.getCode()) {
                        case "TIME_": {
                            Object value2 = dataRow.getValue(m);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value2 == null || !(value2 instanceof GregorianCalendar)) continue block17;
                            GregorianCalendar gc = (GregorianCalendar)value2;
                            Date time = gc.getTime();
                            String format = formatter.format(time);
                            uploadAction.setTime(format);
                            continue block17;
                        }
                        case "CUREVENT": {
                            if (fieldValue == null) continue block17;
                            uploadAction.setAction(fieldValue.toString());
                            continue block17;
                        }
                        case "OPERATOR": {
                            if (fieldValue == null) continue block17;
                            uploadAction.setOperator(fieldValue.toString());
                            continue block17;
                        }
                        case "CMT": {
                            Object comment = dataRow.getValue(m);
                            if (comment == null) continue block17;
                            if (comment instanceof byte[]) {
                                byte[] bt = (byte[])comment;
                                String s = new String(bt);
                                uploadAction.setCmt(s);
                                continue block17;
                            }
                            uploadAction.setCmt(comment.toString());
                            continue block17;
                        }
                    }
                }
                uploadActions.add(uploadAction);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.initUserTitles(uploadActions);
        int num = 0;
        for (UploadRecordNew upload : uploadActions) {
            DimensionValueSet entities = upload.getEntities();
            String unitKey = entities.getValue(mainDim).toString();
            if ("act_reject".equals(upload.getAction()) || "cus_reject".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setRejectTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
                uploadSum.setRejectedExplain(upload.getCmt());
            }
            if ("act_submit".equals(upload.getAction()) || "cus_submit".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setSubmitedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_return".equals(upload.getAction()) || "cus_return".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setReturnedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setCmt(upload.getCmt());
                uploadSum.setTime(upload.getTime());
            }
            if ("act_upload".equals(upload.getAction()) || "cus_upload".equals(upload.getAction())) {
                if (StringUtils.isEmpty((String)uploadSum.getEndUploadTime())) {
                    if (entitySelf.equals(unitKey)) {
                        uploadSum.setFirstUploadTime(upload.getTime());
                    }
                    uploadSum.setFirstUploadExplain(upload.getCmt());
                    uploadSum.setOperator(upload.getOperator());
                    uploadSum.setTime(upload.getTime());
                }
                ++num;
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setEndUploadTime(upload.getTime());
                }
                uploadSum.setUploadExplain(upload.getCmt());
            }
            if ("act_confirm".equals(upload.getAction()) || "cus_confirm".equals(upload.getAction())) {
                if (entitySelf.equals(unitKey)) {
                    uploadSum.setComfirmedTime(upload.getTime());
                }
                uploadSum.setOperator(upload.getOperator());
                uploadSum.setTime(upload.getTime());
            }
            if (!"act_cancel_confirm".equals(upload.getAction())) continue;
            if (entitySelf.equals(unitKey)) {
                uploadSum.setCancelConfirmTime(upload.getTime());
            }
            uploadSum.setOperator(upload.getOperator());
            uploadSum.setTime(upload.getTime());
        }
        uploadSum.setUploadNums(num);
    }

    private String convertStateCode(String actionCode) {
        String stateCode = null;
        if ("start".equals(actionCode)) {
            return "start";
        }
        if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            return UploadState.SUBMITED.toString();
        }
        if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            return UploadState.RETURNED.toString();
        }
        if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode) || "act_cancel_confirm".equals(actionCode)) {
            return UploadState.UPLOADED.toString();
        }
        if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
            return UploadState.CONFIRMED.toString();
        }
        if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
            return UploadState.REJECTED.toString();
        }
        return stateCode;
    }

    private boolean executeFormula(String formulaExpression, DimensionValueSet dimensionValueSet, String formSchemeKey) {
        if (formulaExpression == null || formulaExpression.isEmpty()) {
            return true;
        }
        ExecutorContext context = this.executorContext(formSchemeKey);
        ExpressionEvaluatorImpl expressionEvaluator = new ExpressionEvaluatorImpl(new QueryParam(this.connectionProvider, this.dataRunTimeController));
        try {
            AbstractData result = expressionEvaluator.eval(formulaExpression, context, dimensionValueSet);
            return result.getAsBool();
        }
        catch (ExpressionException e) {
            logger.error("\u6267\u884c\u516c\u5f0f\u51fa\u9519");
            return false;
        }
        catch (DataTypeException e) {
            logger.error("\u6267\u884c\u516c\u5f0f\u51fa\u9519");
            return false;
        }
    }

    private ExecutorContext executorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataRunTimeController);
        context.setVarDimensionValueSet(new DimensionValueSet());
        if (!com.jiuqi.util.StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataRunTimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        context.setOrgEntityId(this.getContextMainDimId(formScheme.getDw()));
        return context;
    }

    private String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return com.jiuqi.util.StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    public List<ActionStateBean> queryState(FormSchemeDefine formSchemeDefine, DimensionValueSet masterKey) {
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(masterKey, formSchemeDefine);
        return this.overviewStatisticsDianxin.queryState(formSchemeDefine, dimensionValueSet);
    }
}

