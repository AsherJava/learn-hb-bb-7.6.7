/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.DUserActionParam
 *  com.jiuqi.nr.dataentry.bean.LogInfo
 *  com.jiuqi.nr.dataentry.service.IBatchWorkflowService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncEntityLastHistoryDTO;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IImportAfterService;
import com.jiuqi.nr.transmission.data.service.ISyncEntityLastHistoryService;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ImportAfterDoUpload
implements IImportAfterService {
    private static final Logger logger = LoggerFactory.getLogger(ImportAfterDoUpload.class);
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IBatchWorkflowService batchWorkflowService;
    @Autowired
    private ISyncEntityLastHistoryService syncEntityLastHistoryService;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private ActionMethod actionMethod;

    @Override
    public Double getOrder() {
        return 1.0;
    }

    @Override
    public String getTitle() {
        return MultilingualLog.doUploadMessage(1, "");
    }

    @Override
    public Object afterImport(ITransmissionContext defaultTransmissionContext) throws Exception {
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        FlowsType flowsType = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey()).getFlowsSetting().getFlowsType();
        if (executeParam.getDoUpload() == 1 && !FlowsType.NOSTARTUP.equals((Object)flowsType)) {
            logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5f00\u59cb\u51c6\u5907\u4e0a\u62a5\u6d41\u7a0b\u5bf9\u8c61");
            boolean uploadResult = this.doUploaded(defaultTransmissionContext);
            if (uploadResult) {
                this.afterUpload(defaultTransmissionContext);
            } else {
                this.notUploadMessage(defaultTransmissionContext);
            }
            logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u4e0a\u62a5\u6d41\u7a0b\u5bf9\u8c61\u5b8c\u6210");
        }
        if (executeParam.getDoUpload() == 1 && FlowsType.NOSTARTUP.equals((Object)flowsType)) {
            logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u6d41\u7a0b\u5bf9\u8c61\u6ca1\u6709\u5f00\u542f\uff0c\u65e0\u6cd5\u8fdb\u884c\u4e0a\u62a5");
            ILogHelper logHelper = defaultTransmissionContext.getLogHelper();
            logHelper.appendLog("\r\n" + MultilingualLog.doUploadMessage(10, ""));
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean doUploaded(ITransmissionContext defaultTransmissionContext) throws Exception {
        List<String> formGroupLists = defaultTransmissionContext.getContextExpendParam().getFormGroupLists();
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        String uploadActionCode = this.workflow.getUploadActionCode(executeParam.getFormSchemeKey());
        String taskCode = this.workflow.getTaskCode(executeParam.getFormSchemeKey(), uploadActionCode);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formScheme.getKey());
        List<String> forms = executeParam.getForms();
        BatchExecuteTaskParam executeTaskparam = new BatchExecuteTaskParam();
        JtableContext jtableContext = new JtableContext();
        executeTaskparam.setContext(jtableContext);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (executeParam.getAllowForceUpload() == 1 && flowsSetting.isUnitSubmitForForce()) {
            executeTaskparam.setForceCommit(true);
        }
        executeTaskparam.setActionId(uploadActionCode);
        executeTaskparam.setTaskCode(taskCode);
        executeTaskparam.setComment(defaultTransmissionContext.getExecuteParam().getUploadDesc());
        WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
        executeTaskparam.setWorkFlowType(wordFlowType);
        executeTaskparam.setFormulaSchemeKeys(defaultFormulaSchemeInFormScheme.getKey());
        ActionParam actionParam = this.actionMethod.actionParam(executeParam.getFormSchemeKey(), uploadActionCode);
        DUserActionParam userActionParam = new DUserActionParam();
        userActionParam.setCheckFormulaValue(actionParam.getCheckFormulaValue());
        userActionParam.setCalculateFormulaValue(actionParam.getCalcuteFormulaValue());
        userActionParam.setCheckCurrencyType(actionParam.getCheckCurrencyType());
        userActionParam.setCheckCurrencyValue(actionParam.getCheckCurrencyValue());
        userActionParam.setNodeCheckCurrencyType(actionParam.getNodeCheckCurrencyType());
        userActionParam.setNodeCheckCurrencyValue(actionParam.getNodeCheckCurrencyValue());
        executeTaskparam.setUserActionParam(userActionParam);
        jtableContext.setTaskKey(executeParam.getTaskKey());
        jtableContext.setFormSchemeKey(formScheme.getKey());
        jtableContext.setFormGroupKey(formGroupLists.get(0));
        jtableContext.setFormulaSchemeKey(defaultFormulaSchemeInFormScheme.getKey());
        jtableContext.setFormKey("");
        TransmissionMonitor monitor = new TransmissionMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
        try {
            DimensionValueSet dimensionValueSet;
            LogInfo logInfo = new LogInfo();
            if (WorkFlowType.ENTITY.equals((Object)wordFlowType)) {
                if (syncResult.getFailForms().size() != 0) return false;
                DimensionValueSet uploadDimensionSet = this.getUploadDimensionSet(executeParam);
                jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimensionSet));
                logInfo = this.batchWorkflowService.batchExecuteTask(executeTaskparam, (AsyncTaskMonitor)monitor);
            } else if (WorkFlowType.FORM.equals((Object)wordFlowType)) {
                Set<String> unUploadForms;
                dimensionValueSet = this.getUploadDimensionSet(executeParam);
                ArrayList<String> uploadForms = new ArrayList<String>(forms);
                if (syncResult.getFailForms().size() > 0 && !CollectionUtils.isEmpty(unUploadForms = syncResult.getFailForms().keySet())) {
                    uploadForms.removeAll(unUploadForms);
                }
                if (uploadForms.size() <= 0) return false;
                executeTaskparam.setFormKeys(uploadForms);
                jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
                logInfo = this.batchWorkflowService.batchExecuteTask(executeTaskparam, (AsyncTaskMonitor)monitor);
            } else {
                dimensionValueSet = this.getUploadDimensionSet(executeParam);
                ArrayList<String> formGroupListsForUpload = new ArrayList<String>(formGroupLists);
                if (syncResult.getFailForms().size() > 0) {
                    Set<String> exportForms = syncResult.getFailForms().keySet();
                    ArrayList groups = new ArrayList();
                    for (String form : exportForms) {
                        groups.addAll(this.runTimeViewController.getFormGroupsByFormKey(form));
                    }
                    List formGroupsByFormKey = groups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(formGroupsByFormKey)) {
                        HashSet exportFormGroups = new HashSet(formGroupsByFormKey);
                        formGroupListsForUpload.removeAll(exportFormGroups);
                    }
                }
                if (formGroupListsForUpload.size() <= 0) return false;
                executeTaskparam.setFormGroupKeys(formGroupListsForUpload);
                jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
                logInfo = this.batchWorkflowService.batchExecuteTask(executeTaskparam, (AsyncTaskMonitor)monitor);
            }
            String actionNameMessage = "\u591a\u7ea7\u90e8\u7f72\u4e0a\u62a5\u6267\u884c\u7ed3\u679cactionName\uff1a" + logInfo.getActionName();
            String logInfoMessage = "\u591a\u7ea7\u90e8\u7f72\u4e0a\u62a5\u6267\u884c\u7ed3\u679clogInfo\uff1a" + logInfo.getLogInfo();
            defaultTransmissionContext.getLogHelper().appendLog(actionNameMessage);
            defaultTransmissionContext.getLogHelper().appendLog(logInfoMessage);
            logger.info("\u591a\u7ea7\u90e8\u7f72\u4e0a\u62a5\u6267\u884c\u7ed3\u679cactionName\uff1a" + actionNameMessage);
            logger.info("\u591a\u7ea7\u90e8\u7f72\u4e0a\u62a5\u6267\u884c\u7ed3\u679clogInfo\uff1a" + logInfoMessage);
            return true;
        }
        catch (Exception e) {
            throw new Exception(MultilingualLog.doUploadMessage(2, ""), e);
        }
    }

    private DimensionValueSet getUploadDimensionSet(IExecuteParam executeParam) {
        Map<String, String> uploadDimMap = executeParam.getUploadDimMap();
        DimensionValueSet uploadDimensionValueSet = new DimensionValueSet();
        uploadDimensionValueSet.assign(executeParam.getDimensionValueSet());
        for (Map.Entry<String, String> stringStringEntry : uploadDimMap.entrySet()) {
            if (StringUtils.hasText(stringStringEntry.getValue()) || uploadDimensionValueSet.getValue(stringStringEntry.getKey()) != null) continue;
            uploadDimensionValueSet.setValue(stringStringEntry.getKey(), (Object)"");
        }
        return uploadDimensionValueSet;
    }

    private void afterUpload(ITransmissionContext defaultTransmissionContext) throws Exception {
        String userId = defaultTransmissionContext.getContextExpendParam().getUserInfoParam().getSyncUserId();
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        List<UploadStateNew> uploadStateNews = this.queryUpload(defaultTransmissionContext);
        ArrayList<UploadStateNew> upload = new ArrayList<UploadStateNew>();
        ArrayList<UploadStateNew> unUpload = new ArrayList<UploadStateNew>();
        if (!CollectionUtils.isEmpty(uploadStateNews)) {
            for (UploadStateNew uploadStateNew : uploadStateNews) {
                String code = uploadStateNew.getActionStateBean().getCode();
                if (UploadState.UPLOADED.toString().equals(code)) {
                    upload.add(uploadStateNew);
                    continue;
                }
                unUpload.add(uploadStateNew);
            }
        }
        HashMap<String, Set<String>> uploadEntityToForm = new HashMap<String, Set<String>>();
        HashMap<String, Set<String>> unUploadEntityToForm = new HashMap<String, Set<String>>();
        if (upload.size() > 0) {
            this.getUploadMap(defaultTransmissionContext, upload, uploadEntityToForm);
        }
        if (unUpload.size() > 0) {
            this.getUploadMap(defaultTransmissionContext, unUpload, unUploadEntityToForm);
        }
        if (unUploadEntityToForm.size() > 0) {
            int syncErrorNum = syncResult.getSyncErrorNum();
            syncResult.setSyncErrorNum(syncErrorNum + unUploadEntityToForm.size());
        }
        this.logEntityInfoForUpload(defaultTransmissionContext, uploadEntityToForm, unUploadEntityToForm);
        if (uploadEntityToForm.size() > 0 && StringUtils.hasText(userId)) {
            this.doUploadUser(defaultTransmissionContext, uploadEntityToForm);
        }
    }

    private void getUploadMap(ITransmissionContext defaultTransmissionContext, List<UploadStateNew> uploadStateNews, Map<String, Set<String>> entityToForm) {
        WorkFlowType workFlowType = defaultTransmissionContext.getContextExpendParam().getWorkFlowType();
        List<String> formKeys = defaultTransmissionContext.getExecuteParam().getForms();
        String dimensionName = defaultTransmissionContext.getContextExpendParam().getDimensionName();
        if (!WorkFlowType.ENTITY.equals((Object)workFlowType)) {
            entityToForm = uploadStateNews.stream().collect(Collectors.groupingBy(uploadStateNew -> uploadStateNew.getEntities().getValue(dimensionName).toString(), Collectors.mapping(UploadStateNew::getFormId, Collectors.toSet())));
        } else {
            for (UploadStateNew uploadStateNew2 : uploadStateNews) {
                entityToForm.put(uploadStateNew2.getEntities().getValue(dimensionName).toString(), new HashSet<String>(formKeys));
            }
        }
    }

    private List<UploadStateNew> queryUpload(ITransmissionContext defaultTransmissionContext) {
        List<String> formGroupLists = defaultTransmissionContext.getContextExpendParam().getFormGroupLists();
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        return this.queryUploadStateService.queryUploadStates(executeParam.getFormSchemeKey(), executeParam.getDimensionValueSet(), executeParam.getForms(), formGroupLists);
    }

    private void logEntityInfoForUpload(ITransmissionContext defaultTransmissionContext, Map<String, Set<String>> uploadEntityToForm, Map<String, Set<String>> unUploadEntityToForm) {
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        ContextExpendParam contextExpendParam = defaultTransmissionContext.getContextExpendParam();
        Map<String, EntityInfoParam> entityRowMap = contextExpendParam.getUnits();
        WorkFlowType wordFlowType = contextExpendParam.getWorkFlowType();
        ILogHelper logHelper = defaultTransmissionContext.getLogHelper();
        StringBuilder sbs = new StringBuilder();
        if (uploadEntityToForm.size() > 0) {
            sbs.append(MultilingualLog.doUploadMessage(3, ""));
            Set<String> uploadEntity = uploadEntityToForm.keySet();
            Utils.setMessage(uploadEntity, entityRowMap, sbs);
        }
        if (unUploadEntityToForm.size() > 0) {
            sbs.append("\r\n").append(MultilingualLog.doUploadMessage(4, ""));
            int i = 0;
            if (WorkFlowType.ENTITY.equals((Object)wordFlowType)) {
                Set<String> unUploadEntity = unUploadEntityToForm.keySet();
                String uploadFailMessage = MultilingualLog.doUploadMessage(5, "");
                for (String s : unUploadEntity) {
                    EntityInfoParam entityRow = entityRowMap.get(s);
                    sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]");
                    if (i < unUploadEntity.size() - 1) {
                        sbs.append("\uff0c");
                    } else {
                        sbs.append("\u3002");
                    }
                    syncResult.addFailUploadEntity(entityRow.getTitle(), entityRow.getEntityKeyData(), uploadFailMessage);
                    ++i;
                }
            } else if (WorkFlowType.FORM.equals((Object)wordFlowType)) {
                List formDefines = this.runTimeViewController.queryFormsById(executeParam.getForms());
                Map<String, FormDefine> formKeyToDefine = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
                String errorMessage = MultilingualLog.doUploadMessage(6, "");
                for (Map.Entry<String, Set<String>> unUploadEntity : unUploadEntityToForm.entrySet()) {
                    EntityInfoParam entityRow = entityRowMap.get(unUploadEntity.getKey());
                    sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]\uff1a");
                    int j = 0;
                    StringBuilder errorFormMessage = new StringBuilder();
                    for (String formKey : unUploadEntity.getValue()) {
                        FormDefine formDefine = formKeyToDefine.get(formKey);
                        sbs.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
                        errorFormMessage.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
                        if (j < unUploadEntity.getValue().size() - 1) {
                            sbs.append("\u3001");
                            errorFormMessage.append("\u3001");
                        }
                        ++j;
                    }
                    this.setMessage(unUploadEntityToForm, i, sbs);
                    String message = String.format(errorMessage, errorFormMessage.toString());
                    syncResult.addFailUploadEntity(entityRow.getTitle(), entityRow.getEntityKeyData(), message.toString());
                    ++i;
                }
            } else {
                List<String> allForms = executeParam.getForms();
                ArrayList formGroupDefines = new ArrayList();
                for (String form : allForms) {
                    List groups = this.runTimeViewController.getFormGroupsByFormKey(form);
                    formGroupDefines.addAll(groups);
                }
                Map<String, FormGroupDefine> groupKeyToDefine = formGroupDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
                String errorMessage = MultilingualLog.doUploadMessage(7, "");
                for (Map.Entry<String, Set<String>> unUploadEntity : unUploadEntityToForm.entrySet()) {
                    EntityInfoParam entityRow = entityRowMap.get(unUploadEntity.getKey());
                    sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]\uff1a");
                    int j = 0;
                    StringBuilder errorFormGroupMessage = new StringBuilder();
                    for (String groupkey : unUploadEntity.getValue()) {
                        FormGroupDefine formGroupDefine = groupKeyToDefine.get(groupkey);
                        sbs.append(formGroupDefine.getTitle());
                        errorFormGroupMessage.append(formGroupDefine.getTitle()).append("[").append(formGroupDefine.getCode()).append("]");
                        if (j < unUploadEntity.getValue().size() - 1) {
                            sbs.append("\u3001");
                            errorFormGroupMessage.append("\u3001");
                        }
                        ++j;
                    }
                    this.setMessage(unUploadEntityToForm, i, sbs);
                    String message = String.format(errorMessage, errorFormGroupMessage.toString());
                    syncResult.addFailUploadEntity(entityRow.getTitle(), entityRow.getEntityKeyData(), message.toString());
                    ++i;
                }
            }
        }
        logHelper.appendLog(sbs.toString());
    }

    private void setMessage(Map<String, Set<String>> unUploadEntityToForm, int i, StringBuilder sbs) {
        if (i < unUploadEntityToForm.entrySet().size() - 1) {
            sbs.append("\uff0c");
        } else {
            sbs.append("\u3002");
        }
    }

    private void doUploadUser(ITransmissionContext defaultTransmissionContext, Map<String, Set<String>> uploadEntityToForm) throws Exception {
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        String userId = defaultTransmissionContext.getContextExpendParam().getUserInfoParam().getSyncUserId();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        String taskKey = executeParam.getTaskKey();
        String periodValue = executeParam.getDimensionValueSet().getValue("DATATIME").toString();
        SyncEntityLastHistoryDTO syncEntityLastHistoryDTO = new SyncEntityLastHistoryDTO();
        syncEntityLastHistoryDTO.setTaskKey(taskKey);
        syncEntityLastHistoryDTO.setPeriod(periodValue);
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            List<SyncEntityLastHistoryDTO> list = this.syncEntityLastHistoryService.lists(syncEntityLastHistoryDTO, new ArrayList<String>(uploadEntityToForm.keySet()));
            if (!CollectionUtils.isEmpty(list)) {
                List<SyncEntityLastHistoryDO> syncEntityLastHistoryDos = SyncEntityLastHistoryDTO.calibreDataDTOssToDOs(list);
                this.syncEntityLastHistoryService.betchdeletes(syncEntityLastHistoryDos);
            }
            ArrayList<SyncEntityLastHistoryDO> addParam = new ArrayList<SyncEntityLastHistoryDO>();
            for (Map.Entry<String, Set<String>> stringListEntry : uploadEntityToForm.entrySet()) {
                String entity = stringListEntry.getKey();
                SyncEntityLastHistoryDO syncEntityLastHistoryDO = this.setSyncEntityLastHistoryDO(taskKey, periodValue, entity, entity, userId);
                addParam.add(syncEntityLastHistoryDO);
            }
            this.syncEntityLastHistoryService.betchAdd(addParam);
        } else {
            ArrayList<SyncEntityLastHistoryDO> addParam = new ArrayList<SyncEntityLastHistoryDO>();
            ArrayList<SyncEntityLastHistoryDO> deleteParam = new ArrayList<SyncEntityLastHistoryDO>();
            for (Map.Entry<String, Set<String>> stringListEntry : uploadEntityToForm.entrySet()) {
                String entity = stringListEntry.getKey();
                syncEntityLastHistoryDTO.setEntity(entity);
                List<SyncEntityLastHistoryDTO> list = this.syncEntityLastHistoryService.list(syncEntityLastHistoryDTO);
                Set<String> uploadFormOrGroupkeys = stringListEntry.getValue();
                for (SyncEntityLastHistoryDTO syncEntityLastHistoryDTO1 : list) {
                    if (!uploadFormOrGroupkeys.contains(syncEntityLastHistoryDTO1.getFormKey())) continue;
                    deleteParam.add(syncEntityLastHistoryDTO1);
                }
                List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOs = this.setSyncEntityLastHistoryDOS(taskKey, periodValue, stringListEntry.getValue(), entity, userId);
                addParam.addAll(syncEntityLastHistoryDOs);
            }
            if (deleteParam.size() > 0) {
                this.syncEntityLastHistoryService.betchdeletes(deleteParam);
            }
            if (addParam.size() > 0) {
                this.syncEntityLastHistoryService.betchAdd(addParam);
            }
        }
    }

    private SyncEntityLastHistoryDO setSyncEntityLastHistoryDO(String taskKey, String period, String formKey, String entity, String userId) {
        SyncEntityLastHistoryDTO syncEntityLastHistoryDTO = new SyncEntityLastHistoryDTO();
        syncEntityLastHistoryDTO.setKey(UUIDUtils.getKey());
        syncEntityLastHistoryDTO.setTaskKey(taskKey);
        syncEntityLastHistoryDTO.setPeriod(period);
        syncEntityLastHistoryDTO.setFormKey(formKey);
        syncEntityLastHistoryDTO.setEntity(entity);
        syncEntityLastHistoryDTO.setUserId(userId);
        syncEntityLastHistoryDTO.setTime(new Date());
        return syncEntityLastHistoryDTO;
    }

    private List<SyncEntityLastHistoryDO> setSyncEntityLastHistoryDOS(String taskKey, String period, Set<String> formKeys, String entity, String userId) {
        ArrayList<SyncEntityLastHistoryDO> syncEntityLastHistoryDTO = new ArrayList<SyncEntityLastHistoryDO>();
        for (String formKey : formKeys) {
            SyncEntityLastHistoryDO syncEntityLastHistoryDO = this.setSyncEntityLastHistoryDO(taskKey, period, formKey, entity, userId);
            syncEntityLastHistoryDTO.add(syncEntityLastHistoryDO);
        }
        return syncEntityLastHistoryDTO;
    }

    private void notUploadMessage(ITransmissionContext defaultTransmissionContext) {
        DataImportResult syncResult = defaultTransmissionContext.getDataImportResult();
        ILogHelper logHelper = defaultTransmissionContext.getLogHelper();
        logHelper.appendLog(MultilingualLog.doUploadMessage(8, ""));
        String unitUploadFailMessage = MultilingualLog.doUploadMessage(9, "");
        for (EntityInfoParam unit : defaultTransmissionContext.getContextExpendParam().getUnits().values()) {
            syncResult.addFailUploadEntity(unit.getTitle(), unit.getEntityKeyData(), unitUploadFailMessage);
        }
    }
}

