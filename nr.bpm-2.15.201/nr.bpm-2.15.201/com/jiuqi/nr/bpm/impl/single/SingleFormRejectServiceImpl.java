/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.impl.single;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectEvent;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.single.listener.ISingleFormRejectListener;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleFormRejectServiceImpl
implements SingleFormRejectService {
    private static final Logger logger = LoggerFactory.getLogger(SingleFormRejectServiceImpl.class);
    public static final String ACTION_FORM_REJECT_INFO = "action_form_reject_info";
    public static final String ACTION_FORM_UPLOAD_INFO = "action_form_upload_info";
    public static final String ACTION_FORM_REJECT = "action_form_reject_info_fail";
    public static final String ACTION_FORM_UPLOAD = "action_form_upload_info_fail";
    public static final String DW_FIELD = "MDCODE";
    @Autowired
    IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    IRuntimeFormService iRuntimeFormService;
    @Autowired
    IRuntimeFormSchemeService iRuntimeFormSchemeService;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private IRunTimeViewController runtimeviewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired(required=false)
    List<ISingleFormRejectListener> iSingleFormRejectListeners;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private DeEntityHelper deEntityHelper;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private Optional<EventDispatcher> dispatcher;

    @Override
    @Transactional(rollbackFor={BpmException.class, RuntimeException.class, Exception.class})
    public CompleteMsg execute(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode) {
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
            FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
            if (formScheme.getFlowsSetting() != null) {
                boolean stepByStepReport = formScheme.getFlowsSetting().getStepByStepReport();
                boolean stepByStepBack = formScheme.getFlowsSetting().getStepByStepBack();
                completeMsg = stepByStepReport || stepByStepBack ? this.stepByStep(formKeys, dimensionSet, formSchemeKey, actionCode, stepByStepReport, stepByStepBack, taskContext) : this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, (TaskContext)taskContext);
            } else {
                completeMsg = this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, (TaskContext)taskContext);
            }
        }
        catch (UserActionException e2) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e2.getMessage());
            logger.error(e2.getMessage(), e2);
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e.getMessage());
            completeMsg.setMsg(e.getLocalizedMessage());
            logger.error(e.getMessage(), e);
        }
        return completeMsg;
    }

    @Override
    @Transactional(rollbackFor={BpmException.class, RuntimeException.class, Exception.class})
    public CompleteMsg execute(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, TaskContext context) {
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
            if (formScheme.getFlowsSetting() != null) {
                boolean stepByStepReport = formScheme.getFlowsSetting().getStepByStepReport();
                boolean stepByStepBack = formScheme.getFlowsSetting().getStepByStepBack();
                completeMsg = stepByStepReport || stepByStepBack ? this.stepByStep(formKeys, dimensionSet, formSchemeKey, actionCode, stepByStepReport, stepByStepBack, context) : this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, context);
            } else {
                completeMsg = this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, context);
            }
        }
        catch (UserActionException e2) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e2.getMessage());
            logger.error(e2.getMessage(), e2);
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e.getMessage());
            completeMsg.setMsg(e.getLocalizedMessage());
            logger.error(e.getMessage(), e);
        }
        return completeMsg;
    }

    private CompleteMsg stepByStep(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, boolean stepByStepReport, boolean stepByStepBack, TaskContext context) {
        if ("single_form_upload".equals(actionCode) || "single_form_submit".equals(actionCode)) {
            if (stepByStepReport) {
                return this.uploadStepByStep(formKeys, dimensionSet, formSchemeKey, actionCode, context);
            }
            return this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, context);
        }
        if (stepByStepBack) {
            return this.rejectStepByStep(formKeys, dimensionSet, formSchemeKey, actionCode, context);
        }
        return this.executeTask(formKeys, dimensionSet, formSchemeKey, actionCode, context);
    }

    private CompleteMsg rejectStepByStep(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, TaskContext context) {
        String period;
        CompleteMsg returnMsg = new CompleteMsg();
        Map<Integer, List<String>> unitsGroup = this.unitsGroup(dimensionSet, formSchemeKey);
        List<String> stepBack = this.stepBack(unitsGroup, formKeys, period = dimensionSet.getValue("DATATIME").toString(), formSchemeKey, actionCode, dimensionSet, context);
        if (stepBack != null && stepBack.size() > 0) {
            returnMsg.setSucceed(false);
            returnMsg.setMsg(ACTION_FORM_REJECT);
        } else {
            returnMsg.setSucceed(true);
            returnMsg.setMsg(ACTION_FORM_REJECT_INFO);
        }
        return returnMsg;
    }

    private CompleteMsg uploadStepByStep(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, TaskContext context) {
        String period;
        CompleteMsg returnMsg = new CompleteMsg();
        Map<Integer, List<String>> unitsGroup = this.unitsGroup(dimensionSet, formSchemeKey);
        List<String> stepBack = this.stepUpload(unitsGroup, formKeys, period = dimensionSet.getValue("DATATIME").toString(), formSchemeKey, actionCode, dimensionSet, context);
        if (stepBack != null && stepBack.size() > 0) {
            returnMsg.setSucceed(false);
            returnMsg.setMsg(ACTION_FORM_UPLOAD);
        } else {
            returnMsg.setSucceed(true);
            returnMsg.setMsg(ACTION_FORM_UPLOAD_INFO);
        }
        return returnMsg;
    }

    @Override
    public Set<String> getFormKeysByAction(DimensionValueSet dimensionSet, String formSchemeKey, String actionCode) {
        HashSet<String> formKeys = new HashSet();
        MemoryDataSet<NvwaQueryColumn> dataSet = this.queryDataTable(dimensionSet, formSchemeKey, actionCode);
        if (null == dataSet || dataSet.size() <= 0) {
            return Collections.emptySet();
        }
        try {
            formKeys = this.convrtDataTable(dataSet, formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return formKeys;
    }

    @Override
    public boolean isRejectOrReturnForm(DimensionValueSet dimensionSet, String formSchemeKey, String formKey, String actionCode) {
        MemoryDataSet<NvwaQueryColumn> dataSet = this.queryDataTable(dimensionSet, formSchemeKey, actionCode);
        try {
            return this.containsFormKey(dataSet, formSchemeKey, formKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isCanWrite(DimensionValueSet dimensionSet, String formSchemeKey, String formKey) {
        MemoryDataSet<NvwaQueryColumn> dataSet = this.queryTable(dimensionSet, formSchemeKey, "single_form_reject", "single_form_return");
        try {
            return this.containsFormKey(dataSet, formSchemeKey, formKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private MemoryDataSet<NvwaQueryColumn> queryDataTable(DimensionValueSet dimensionSet, String formSchemeKey, String actionCode) {
        MemoryDataSet<NvwaQueryColumn> dataSet = null;
        dataSet = StringUtils.isEmpty((String)actionCode) ? this.queryTable(dimensionSet, formSchemeKey, "single_form_reject", "single_form_return") : this.queryTable(dimensionSet, formSchemeKey, actionCode);
        return dataSet;
    }

    private boolean containsFormKey(MemoryDataSet<NvwaQueryColumn> dataSet, String formSchemeKey, String formKey) throws Exception {
        if (null == dataSet || dataSet.size() <= 0) {
            return false;
        }
        if (StringUtils.isEmpty((String)formKey)) {
            return true;
        }
        Set<String> formKeys = this.convrtDataTable(dataSet, formSchemeKey);
        return !formKeys.isEmpty() && formKeys.contains(formKey);
    }

    private Set<String> convrtDataTable(MemoryDataSet<NvwaQueryColumn> dataSet, String formSchemeKey) throws Exception {
        HashSet<String> formKeys = new HashSet<String>();
        FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
        TableModelDefine table = this.nrParameterUtils.getTableByCode(TableConstant.getSysUploadFormTableName(formScheme));
        if (table == null) {
            return formKeys;
        }
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        for (DataRow dataRow : dataSet) {
            for (int j = 0; j < allColumns.size(); ++j) {
                ColumnModelDefine columnModel = allColumns.get(j);
                if (!"FORMID".equals(columnModel.getCode())) continue;
                formKeys.add(dataRow.getString(j));
            }
        }
        return formKeys;
    }

    private CompleteMsg executeTask(Set<String> formKeys, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, TaskContext context) {
        CompleteMsg returnMsg = new CompleteMsg();
        if (null == formKeys || formKeys.isEmpty()) {
            returnMsg.setMsg("formKeys is null");
            return returnMsg;
        }
        FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            returnMsg.setMsg("formScheme is can not be null");
            return returnMsg;
        }
        List<FormDefine> formDefines = this.iRuntimeFormService.queryFormDefinesByFormScheme(formScheme.getKey());
        formDefines = formDefines.stream().filter(e -> formKeys.contains(e.getKey())).collect(Collectors.toList());
        String actorId = NpContextHolder.getContext().getIdentityId();
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionSet, formScheme);
        String action = this.getStatusByActionCode(actionCode);
        SingleFormRejectPrepareEventImpl singleFormRejectPreEvent = new SingleFormRejectPrepareEventImpl();
        singleFormRejectPreEvent.setFormSchemeKey(formSchemeKey);
        singleFormRejectPreEvent.setDimensionValueSet(dimensionSet);
        singleFormRejectPreEvent.setFormKeys(formKeys);
        singleFormRejectPreEvent.setActionId(actionCode);
        singleFormRejectPreEvent.setActorId(actorId);
        singleFormRejectPreEvent.setContext(context);
        try {
            this.dispatcher.get().onSingleFormRejectPrepare(singleFormRejectPreEvent);
        }
        catch (Exception e2) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e2);
        }
        if (singleFormRejectPreEvent.isSetBreak()) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", singleFormRejectPreEvent.getBreakMessage());
        }
        try {
            this.nrParameterUtils.commitFormQuery(formScheme, fliterDimensionValueSet, formDefines, actorId, action);
            returnMsg.setSucceed(true);
            if (this.iSingleFormRejectListeners != null) {
                SingleFormRejectEvent event = new SingleFormRejectEvent(formSchemeKey, dimensionSet, formKeys);
                for (ISingleFormRejectListener iSingleFormRejectListener : this.matchAction(this.iSingleFormRejectListeners, actionCode)) {
                    iSingleFormRejectListener.execute(event);
                }
            }
        }
        catch (Exception e3) {
            returnMsg.setSucceed(false);
            throw new BpmException(String.format("commit form record error.", new Object[0]), e3);
        }
        SingleFormRejectCompleteEventImpl singleFormRejectCompleteEvent = new SingleFormRejectCompleteEventImpl();
        singleFormRejectCompleteEvent.setFormSchemeKey(formSchemeKey);
        singleFormRejectCompleteEvent.setDimensionValueSet(dimensionSet);
        singleFormRejectCompleteEvent.setFormKeys(formKeys);
        singleFormRejectCompleteEvent.setActionId(actionCode);
        singleFormRejectCompleteEvent.setActorId(actorId);
        singleFormRejectCompleteEvent.setContext(context);
        try {
            this.dispatcher.get().onSingleFormRejectComplete(singleFormRejectCompleteEvent);
        }
        catch (Exception e4) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e4);
        }
        return returnMsg;
    }

    private CompleteMsg executeTask(String formKey, DimensionValueSet dimensionSet, String formSchemeKey, String actionCode, TaskContext context) {
        CompleteMsg returnMsg = new CompleteMsg();
        HashSet<String> formKeys = new HashSet<String>();
        if (null == formKey || formKey.isEmpty()) {
            returnMsg.setMsg("formKeys is null");
            return returnMsg;
        }
        formKeys.add(formKey);
        FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            returnMsg.setMsg("formScheme is can not be null");
            return returnMsg;
        }
        List<FormDefine> formDefines = this.iRuntimeFormService.queryFormDefinesByFormScheme(formScheme.getKey());
        formDefines = formDefines.stream().filter(e -> formKeys.contains(e.getKey())).collect(Collectors.toList());
        String actorId = NpContextHolder.getContext().getIdentityId();
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionSet, formScheme);
        String action = this.getStatusByActionCode(actionCode);
        SingleFormRejectPrepareEventImpl singleFormRejectPreEvent = new SingleFormRejectPrepareEventImpl();
        singleFormRejectPreEvent.setFormSchemeKey(formSchemeKey);
        singleFormRejectPreEvent.setDimensionValueSet(dimensionSet);
        singleFormRejectPreEvent.setFormKeys(formKeys);
        singleFormRejectPreEvent.setActionId(actionCode);
        singleFormRejectPreEvent.setActorId(actorId);
        singleFormRejectPreEvent.setContext(context);
        try {
            this.dispatcher.get().onSingleFormRejectPrepare(singleFormRejectPreEvent);
        }
        catch (Exception e2) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e2);
        }
        if (singleFormRejectPreEvent.isSetBreak()) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", singleFormRejectPreEvent.getBreakMessage());
        }
        try {
            this.nrParameterUtils.commitFormQuery(formScheme, fliterDimensionValueSet, formDefines, actorId, action);
            returnMsg.setSucceed(true);
            if (this.iSingleFormRejectListeners != null) {
                SingleFormRejectEvent event = new SingleFormRejectEvent(formSchemeKey, dimensionSet, formKeys);
                for (ISingleFormRejectListener iSingleFormRejectListener : this.matchAction(this.iSingleFormRejectListeners, actionCode)) {
                    iSingleFormRejectListener.execute(event);
                }
            }
        }
        catch (Exception e3) {
            returnMsg.setSucceed(false);
            throw new BpmException(String.format("commit form record error.", new Object[0]), e3);
        }
        SingleFormRejectCompleteEventImpl singleFormRejectCompleteEvent = new SingleFormRejectCompleteEventImpl();
        singleFormRejectCompleteEvent.setFormSchemeKey(formSchemeKey);
        singleFormRejectCompleteEvent.setDimensionValueSet(dimensionSet);
        singleFormRejectCompleteEvent.setFormKeys(formKeys);
        singleFormRejectCompleteEvent.setActionId(actionCode);
        singleFormRejectCompleteEvent.setActorId(actorId);
        singleFormRejectCompleteEvent.setContext(context);
        try {
            this.dispatcher.get().onSingleFormRejectComplete(singleFormRejectCompleteEvent);
        }
        catch (Exception e4) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e4);
        }
        return returnMsg;
    }

    private List<ISingleFormRejectListener> matchAction(List<ISingleFormRejectListener> iSingleFormRejectListeners, String actionCode) {
        return iSingleFormRejectListeners.stream().filter(e -> e.taskActionIds().contains(actionCode)).collect(Collectors.toList());
    }

    private MemoryDataSet<NvwaQueryColumn> queryTable(DimensionValueSet dimensionSet, String formSchemeKey, String ... actionCode) {
        FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            throw new BpmException("formScheme is can not be null");
        }
        String tableCode = TableConstant.getSysUploadFormTableName(formScheme);
        TableModelDefine tableModelDefine = this.nrParameterUtils.getTableByCode(tableCode);
        if (tableModelDefine == null) {
            return null;
        }
        List<ColumnModelDefine> columnModels = this.nrParameterUtils.getAllFieldsInTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionSet, formScheme);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableModelDefine.getName());
        for (ColumnModelDefine columnModel : columnModels) {
            Object value;
            String dimensionName;
            int length;
            if ("FORM_STATE".equals(columnModel.getCode()) && (length = actionCode.length) > 0) {
                List<String> actions = Arrays.asList(actionCode);
                List status = actions.stream().map(e -> this.getStatusByActionCode((String)e)).collect(Collectors.toList());
                ArrayList value2 = new ArrayList();
                value2.addAll(status);
                queryModel.getColumnFilters().put(columnModel, value2);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModel)) != null && (value = fliterDimensionValueSet.getValue(dimensionName)) != null) {
                queryModel.getColumnFilters().put(columnModel, value);
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModel));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet executeQuery = null;
        try {
            executeQuery = readOnlyDataAccess.executeQuery(context);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return executeQuery;
    }

    private FormSchemeDefine getFormScheme(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.iRuntimeFormSchemeService.getFormScheme(formSchemeKey);
            return formScheme;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private String getStatusByActionCode(String action) {
        String status = null;
        switch (action) {
            case "single_form_reject": {
                status = "fm_reject";
                break;
            }
            case "single_form_return": {
                status = "fm_return";
                break;
            }
            case "single_form_upload": {
                status = "fm_upload";
                break;
            }
            case "single_form_submit": {
                status = "fm_submit";
            }
        }
        return status;
    }

    public List<String> stepBack(Map<Integer, List<String>> levels, Set<String> formKeys, String period, String formSchemeKey, String actionCode, DimensionValueSet dimensionSet, TaskContext context) {
        ArrayList<String> noRejectForms = new ArrayList<String>();
        if (levels == null || levels.size() == 0) {
            return Collections.emptyList();
        }
        Set<Integer> keySet = levels.keySet();
        Integer max = Collections.max(keySet);
        Integer min = Collections.min(keySet);
        DimensionValueSet dimension = new DimensionValueSet();
        dimension.setValue("DATATIME", (Object)period);
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityTable = this.deEntityHelper.getFullEntityTable(entityViewDefine, formSchemeKey, dimension);
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        for (int i = min.intValue(); i <= max; ++i) {
            List<String> list = levels.get(i);
            if (list == null) continue;
            for (String unitKey : list) {
                for (String formkey : formKeys) {
                    dimensionSet.setValue(mainDimName, (Object)unitKey);
                    boolean returned = this.isReturn(unitKey, period, formSchemeKey, formkey, entityTable, actionCode, dimensionSet, context);
                    if (returned) continue;
                    noRejectForms.add(formkey);
                }
            }
        }
        return noRejectForms;
    }

    public List<String> stepUpload(Map<Integer, List<String>> levels, Set<String> formKeys, String period, String formSchemeKey, String actionCode, DimensionValueSet dimensionSet, TaskContext context) {
        ArrayList<String> noRejectForms = new ArrayList<String>();
        if (levels == null || levels.size() == 0) {
            return Collections.emptyList();
        }
        Set<Integer> keySet = levels.keySet();
        Integer max = Collections.max(keySet);
        Integer min = Collections.min(keySet);
        DimensionValueSet dimension = new DimensionValueSet();
        dimension.setValue("DATATIME", (Object)period);
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityTable = this.deEntityHelper.getFullEntityTable(entityViewDefine, formSchemeKey, dimension);
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        for (int i = max.intValue(); i >= min; --i) {
            List<String> list = levels.get(i);
            if (list == null) continue;
            for (String unitKey : list) {
                for (String formkey : formKeys) {
                    dimensionSet.setValue(mainDimName, (Object)unitKey);
                    boolean returned = this.isUplaod(unitKey, period, formSchemeKey, formkey, entityTable, actionCode, dimensionSet, context);
                    if (returned) continue;
                    noRejectForms.add(formkey);
                }
            }
        }
        return noRejectForms;
    }

    public Map<Integer, List<String>> unitsGroup(DimensionValueSet dimensionSet, String formSchemeKey) {
        Map<String, Integer> unitMap = this.splitUnits(dimensionSet, formSchemeKey);
        HashMap<Integer, List<String>> levels = new HashMap<Integer, List<String>>();
        if (unitMap != null && unitMap.size() > 0) {
            for (Map.Entry<String, Integer> unit : unitMap.entrySet()) {
                String unitKey = unit.getKey();
                Integer level = unit.getValue();
                if (levels.get(level) != null) {
                    List list = (List)levels.get(level);
                    list.add(unitKey);
                    continue;
                }
                ArrayList<String> units = new ArrayList<String>();
                units.add(unitKey);
                levels.put(level, units);
            }
        }
        return levels;
    }

    public Map<String, Integer> splitUnits(DimensionValueSet dimensionSet, String formSchemeKey) {
        HashMap<String, Integer> unitToLevel = new HashMap<String, Integer>();
        List<IEntityRow> allRows = this.deEntityHelper.getEntityRow(formSchemeKey, dimensionSet);
        if (allRows != null && allRows.size() > 0) {
            for (IEntityRow iEntityRow : allRows) {
                String[] parentsEntityKeyDataPath = iEntityRow.getParentsEntityKeyDataPath();
                unitToLevel.put(iEntityRow.getEntityKeyData(), parentsEntityKeyDataPath.length);
            }
        }
        return unitToLevel;
    }

    public boolean isReturn(String unitKey, String period, String formSchemeKey, String formKey, IEntityTable entityTable, String actionCode, DimensionValueSet currentDim, TaskContext context) {
        List<UploadStateNew> uploadStateNew;
        FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        DimensionValueSet dimensionSet = new DimensionValueSet();
        dimensionSet.setValue("DATATIME", (Object)period);
        String parentEntityKey = this.deEntityHelper.getParent(entityTable, unitKey);
        dimensionSet.setValue(mainDimName, (Object)parentEntityKey);
        boolean stepByStepBack = formScheme.getFlowsSetting().getStepByStepBack();
        if (stepByStepBack && (uploadStateNew = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionSet, null)) != null && uploadStateNew.size() > 0) {
            for (UploadStateNew uploadState : uploadStateNew) {
                ActionStateBean actionStateBean = uploadState.getActionStateBean();
                if (actionStateBean != null) {
                    String code = actionStateBean.getCode();
                    if (UploadState.REJECTED.toString().equals(code)) {
                        this.executeTask(formKey, currentDim, formSchemeKey, actionCode, context);
                        return true;
                    }
                    if (!UploadState.UPLOADED.toString().equals(code)) continue;
                    boolean canWrite = this.isCanWrite(dimensionSet, formSchemeKey, formKey);
                    if (canWrite) {
                        this.executeTask(formKey, currentDim, formSchemeKey, actionCode, context);
                        return true;
                    }
                    return false;
                }
                this.executeTask(formKey, currentDim, formSchemeKey, actionCode, context);
                return true;
            }
        }
        this.executeTask(formKey, currentDim, formSchemeKey, actionCode, context);
        return true;
    }

    public boolean isUplaod(String unitKey, String period, String formSchemeKey, String formKey, IEntityTable entityTable, String actionCode, DimensionValueSet currentDim, TaskContext context) {
        Boolean childerUpload = this.isChilderUpload(formSchemeKey, period, formKey, currentDim, entityTable);
        if (childerUpload.booleanValue()) {
            this.executeTask(formKey, currentDim, formSchemeKey, actionCode, context);
        }
        return childerUpload;
    }

    private Boolean isChilderUpload(String formSchemeKey, String period, String formKey, DimensionValueSet currentDim, IEntityTable entityTable) {
        FormSchemeDefine formScheme = this.runtimeviewController.getFormScheme(formSchemeKey);
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        DimensionValueSet dimensionSet = new DimensionValueSet();
        dimensionSet.setValue("DATATIME", (Object)period);
        List<String> directChildrenId = this.deEntityHelper.getDirectChildrenId(currentDim, formSchemeKey, entityTable);
        dimensionSet.setValue(mainDimName, directChildrenId);
        List<UploadStateNew> uploadStateNew = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionSet, null);
        HashMap<String, String> unitToActionCode = new HashMap<String, String>();
        if (uploadStateNew != null && uploadStateNew.size() > 0) {
            for (UploadStateNew uploadState : uploadStateNew) {
                DimensionValueSet entities = uploadState.getEntities();
                String unitKey = entities.getValue(mainDimName).toString();
                ActionStateBean actionStateBean = uploadState.getActionStateBean();
                unitToActionCode.put(unitKey, actionStateBean.getCode());
            }
        } else {
            return true;
        }
        for (String childrenId : directChildrenId) {
            String actionCode = (String)unitToActionCode.get(childrenId);
            if (actionCode == null) continue;
            if (UploadState.REJECTED.toString().equals(actionCode)) {
                return false;
            }
            if (!UploadState.UPLOADED.toString().equals(actionCode)) continue;
            boolean canWrite = this.isCanWrite(dimensionSet, formSchemeKey, formKey);
            if (canWrite) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public Map<String, Boolean> queryRejectFromDatas(DimensionValueSet dimensionSet, String formSchemeKey) {
        HashMap<String, Boolean> unitToReject = new HashMap<String, Boolean>();
        MemoryDataSet<NvwaQueryColumn> dataSet = this.queryTable(dimensionSet, formSchemeKey, "single_form_reject", "single_form_return");
        if (null == dataSet || dataSet.size() <= 0) {
            return new HashMap<String, Boolean>();
        }
        try {
            FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
            TableModelDefine table = this.nrParameterUtils.getTableByCode(TableConstant.getSysUploadFormTableName(formScheme));
            if (table == null) {
                return new HashMap<String, Boolean>();
            }
            List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
            for (DataRow dataRow : dataSet) {
                for (int j = 0; j < allColumns.size(); ++j) {
                    ColumnModelDefine columnModel = allColumns.get(j);
                    if (!DW_FIELD.equals(columnModel.getCode())) continue;
                    String unitKey = dataRow.getString(j);
                    unitToReject.put(unitKey, true);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return unitToReject;
    }
}

