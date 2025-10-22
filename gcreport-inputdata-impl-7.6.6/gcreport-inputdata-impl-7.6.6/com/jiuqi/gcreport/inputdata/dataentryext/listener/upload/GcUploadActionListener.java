/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.bpm.Actor.Actor
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl
 *  com.jiuqi.nr.bpm.businesskey.BusinessKeySet
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityInfo
 *  com.jiuqi.nr.bpm.common.ConcurrentTaskContext
 *  com.jiuqi.nr.bpm.common.Task
 *  com.jiuqi.nr.bpm.common.TaskContext
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult
 *  com.jiuqi.nr.bpm.de.dataflow.step.datang.StepByStep2
 *  com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent
 *  com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent
 *  com.jiuqi.nr.bpm.event.UserActionCompleteEvent
 *  com.jiuqi.nr.bpm.event.UserActionEventListener
 *  com.jiuqi.nr.bpm.event.UserActionPrepareEvent
 *  com.jiuqi.nr.bpm.event.UserActionProgressEvent
 *  com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter
 *  com.jiuqi.nr.bpm.service.RunTimeService
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.listener.upload;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.listener.upload.impl.GcUploaderImpl;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;
import com.jiuqi.nr.bpm.de.dataflow.step.datang.StepByStep2;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class GcUploadActionListener
implements UserActionEventListener {
    private static final String EXECUTE_FLAG_NAME = "EXECUTE_FLAG_NAME";
    private static final String EXECUTE_MANAGE_PARAM = "GC_EXECUTE_MANAGE_PARAM";
    private static final String EXECUTE_MANAGE_PARAM_FORMK = "EXECUTE_MANAGE_PARAM_FORMK";
    private Logger logger = LoggerFactory.getLogger(GcUploadActionListener.class);
    @Lazy
    @Autowired
    private NpReportQueryProvider provider;
    @Lazy
    @Autowired
    private IWorkflow iWorkflow;
    @Lazy
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Lazy
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Lazy
    @Autowired
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Lazy
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Lazy
    @Autowired
    private IDataentryFlowService iDataentryFlowService;
    @Lazy
    @Autowired
    private CommonUtil commonUtil;
    @Lazy
    @Autowired
    private IEntityMetaService entityMetaService;
    @Lazy
    @Autowired
    private BusinessGenerator businessGenerator;
    @Lazy
    @Autowired
    private StepByStep2 stepByStep;
    @Lazy
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    static final Set<String> LISTENINGACTIONS = new HashSet<String>();

    public void onPrepare(UserActionPrepareEvent event) throws Exception {
        try {
            this.doExecute(event);
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u540c\u6b65\u5f02\u5e38\u62a5\u9519:", e);
            throw e;
        }
    }

    private void doExecute(UserActionPrepareEvent event) throws Exception {
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString((String)event.getBusinessKey());
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(businessKey.getFormSchemeKey(), businessKey.getPeriod());
        Boolean exeFlag = (Boolean)event.getContext().get(EXECUTE_FLAG_NAME);
        if (StringUtils.isEmpty((String)systemId)) {
            return;
        }
        if (exeFlag == null || !exeFlag.booleanValue()) {
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)businessKey.getFormSchemeKey(), (String)businessKey.getPeriod());
            GcDataEntryContext context = new GcDataEntryContext(businessKey.getFormSchemeKey(), this.provider);
            if (!StringUtils.isEmpty((String)context.getOrgTableName())) {
                String entityName = this.getEntityName(businessKey.getFormSchemeKey());
                String orgId = businessKey.getMasterEntityInfo().getMasterEntityKey(entityName);
                GcOrgCacheVO cacheVO = this.getOrgByScheme(context.getOrgTableName(), yp, orgId);
                boolean isDefault = this.iWorkflow.isDefaultWorkflow(businessKey.getFormSchemeKey());
                if (Objects.isNull(cacheVO)) {
                    this.logger.error("\u83b7\u53d6\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0ccode=" + orgId + " mad_org=" + context.getOrgTableName() + ",\u65f6\u671f=" + businessKey.getPeriod());
                    return;
                }
                boolean isCorporate = this.consolidatedTaskService.isCorporate(this.getTaskId(businessKey.getFormSchemeKey()), businessKey.getPeriod(), this.getOrgTableName(businessKey.getFormSchemeKey()));
                WorkFlowType startType = this.iWorkflow.queryStartType(businessKey.getFormSchemeKey());
                FormSchemeDefine formSchemeDefine = context.getFormScheme();
                MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
                Map<String, String> masterEntity = this.getMasterEntity(masterEntityInfo, context);
                HashMap<String, DimensionValueSet> dimensionValueSetMap = new HashMap<String, DimensionValueSet>();
                if (isDefault) {
                    if (isCorporate) {
                        List<BusinessKey> businessKeys = this.listBusinessKey(formSchemeDefine.getKey(), businessKey.getPeriod(), masterEntity, cacheVO.getOrgTypeId(), isCorporate, entityName, dimensionValueSetMap);
                        if (WorkFlowType.ENTITY.equals((Object)startType)) {
                            this.orgUnitUploadActionDefault(businessKey, context, event, startType, true, businessKeys, dimensionValueSetMap);
                        } else {
                            this.uploadActionGroup(businessKey, context, event, true, startType, dimensionValueSetMap, businessKeys);
                        }
                    } else {
                        boolean isUpload = this.isUploadByDefaultWorkflow(businessKey.getFormSchemeKey(), businessKey.getPeriod(), true);
                        if (!StringUtils.isEmpty((String)systemId) && isUpload) {
                            List<BusinessKey> businessKeys = this.listBusinessKeyByManageKey(businessKey.getFormSchemeKey(), businessKey.getPeriod(), cacheVO, masterEntity, entityName, dimensionValueSetMap);
                            if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
                                if (WorkFlowType.ENTITY.equals((Object)startType)) {
                                    this.orgUnitUploadActionDefault(businessKey, context, event, startType, true, businessKeys, dimensionValueSetMap);
                                } else {
                                    this.uploadActionGroup(businessKey, context, event, true, startType, dimensionValueSetMap, businessKeys);
                                }
                                return;
                            }
                            this.checkUploadManageScheme(cacheVO, businessKey.getFormSchemeKey(), businessKey.getPeriod(), businessKey.getFormKey(), startType, yp, event, null, orgId);
                        }
                    }
                } else if (isCorporate) {
                    List<BusinessKey> businessKeys = this.listBusinessKey(formSchemeDefine.getKey(), businessKey.getPeriod(), masterEntity, cacheVO.getOrgTypeId(), isCorporate, entityName, dimensionValueSetMap);
                    if (WorkFlowType.ENTITY.equals((Object)startType)) {
                        this.orgUnitUploadActionDefault(businessKey, context, event, startType, false, businessKeys, dimensionValueSetMap);
                        return;
                    }
                    this.uploadActionGroup(businessKey, context, event, false, startType, dimensionValueSetMap, businessKeys);
                } else {
                    boolean isUpload = this.isUploadByDefaultWorkflow(businessKey.getFormSchemeKey(), businessKey.getPeriod(), false);
                    if (!StringUtils.isEmpty((String)systemId) && isUpload) {
                        List<BusinessKey> businessKeys = this.listBusinessKeyByManageKey(businessKey.getFormSchemeKey(), businessKey.getPeriod(), cacheVO, masterEntity, context.getOrgTableName(), dimensionValueSetMap);
                        if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
                            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                                this.orgUnitUploadActionDefault(businessKey, context, event, startType, false, businessKeys, dimensionValueSetMap);
                            } else {
                                this.uploadActionGroup(businessKey, context, event, false, startType, dimensionValueSetMap, businessKeys);
                            }
                            return;
                        }
                        this.checkUploadManageScheme(cacheVO, businessKey.getFormSchemeKey(), businessKey.getPeriod(), businessKey.getFormKey(), startType, yp, event, null, orgId);
                    }
                }
            }
        }
    }

    public void onComplete(UserActionCompleteEvent event) throws Exception {
        if (this.getGcUploadStateOption()) {
            return;
        }
        List impls = (List)event.getContext().get(EXECUTE_MANAGE_PARAM);
        if (impls != null && impls.size() > 0) {
            for (GcUploaderImpl gcUploader : impls) {
                BusinessKey businessKey = BusinessKeyFormatter.parsingFromString((String)event.getBusinessKey());
                if (businessKey.getFormSchemeKey().equals(gcUploader.getBusinessKey().getFormSchemeKey())) continue;
                if ("act_retrieve".equals(event.getActionId())) {
                    this.iDataentryFlowService.executeRevert(gcUploader.getBusinessKey(), gcUploader.getTaskContext());
                    continue;
                }
                String taskID = gcUploader.getTasks().get(0).getId();
                Actor candicateActor = Actor.fromNpContext();
                gcUploader.getRunTimeService().completeProcessTask(gcUploader.getBusinessKey(), taskID, candicateActor.getUserId(), event.getActionId(), event.getComment(), gcUploader.getTaskContext());
            }
        }
    }

    private List<BusinessKey> listBusinessKey(String schemeID, String period, Map<String, String> masterEntitys, String orgTypeId, boolean isCorporate, String inputEntityName, Map<String, DimensionValueSet> dimensionValueSetMap) {
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(schemeID, period);
        Set<Object> schemeIds = new HashSet();
        HashSet<String> orgEntityList = new HashSet<String>();
        if (isCorporate) {
            schemeIds = ConsolidatedSystemUtils.listSchemeIdSetByTaskIdListAndPeriod((List)consolidatedTaskVO.getManageTaskKeys(), (String)period);
            if (org.springframework.util.CollectionUtils.isEmpty(schemeIds)) {
                schemeIds = new HashSet();
            }
            if (!org.springframework.util.CollectionUtils.isEmpty(consolidatedTaskVO.getManageEntityList())) {
                orgEntityList.addAll(consolidatedTaskVO.getManageEntityList());
            }
        } else {
            Set manageSchemeIds = ConsolidatedSystemUtils.listSchemeIdSetByTaskIdListAndPeriod((List)consolidatedTaskVO.getManageTaskKeys(), (String)period);
            String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)period);
            HashSet<String> allSchemeIds = new HashSet<String>();
            if (!org.springframework.util.CollectionUtils.isEmpty(manageSchemeIds)) {
                allSchemeIds.addAll(manageSchemeIds);
            }
            allSchemeIds.add(inputSchemeId);
            if (!StringUtils.isEmpty((String)consolidatedTaskVO.getCorporateEntity())) {
                orgEntityList.add(consolidatedTaskVO.getCorporateEntity());
            }
            if (!org.springframework.util.CollectionUtils.isEmpty(consolidatedTaskVO.getManageEntityList())) {
                String currOrgTableName = this.getOrgTableName(schemeID);
                for (String orgEntity : consolidatedTaskVO.getManageEntityList()) {
                    if (orgEntity.equals(currOrgTableName)) continue;
                    orgEntityList.add(orgEntity);
                }
            }
            schemeIds = allSchemeIds.stream().filter(item -> !schemeID.equals(item)).collect(Collectors.toSet());
        }
        if (consolidatedTaskVO == null || org.springframework.util.CollectionUtils.isEmpty(schemeIds) && org.springframework.util.CollectionUtils.isEmpty(orgEntityList)) {
            return businessKeys;
        }
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)schemeID, (String)period);
        for (String schemeId : schemeIds) {
            if (StringUtils.isEmpty((String)schemeId)) continue;
            FormSchemeDefine formSchemeDefine = this.commonUtil.getFormScheme(schemeId);
            if (formSchemeDefine == null) {
                Object[] args = new String[]{schemeId};
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.fromschemedeniedmsg", (Object[])args));
            }
            String orgTableName = DimensionUtils.getDwEntitieTableByTaskKey((String)this.getTaskId(schemeId));
            GcOrgCacheVO cacheVO = this.getOrgByScheme(orgTableName, yp, masterEntitys.get(inputEntityName));
            if (cacheVO == null || !orgTypeId.equals(cacheVO.getOrgTypeId())) continue;
            DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)cacheVO.getCode(), (Object)period, (Object)masterEntitys.get("MD_CURRENCY"), (Object)cacheVO.getOrgTypeId(), (String)masterEntitys.get("ADJUST"), (String)formSchemeDefine.getTaskKey());
            dimensionValueSetMap.put(formSchemeDefine.getKey(), dimensionValueSet);
            String entityName = this.getManageEntityName(formSchemeDefine.getKey());
            BusinessKey define = this.buildBusinessKey(schemeId, period, entityName, masterEntitys, inputEntityName);
            businessKeys.add(define);
        }
        return businessKeys;
    }

    private BusinessKey buildBusinessKey(String formSchemeKey, String period, String manageEntityName, Map<String, String> masterEntitys, String inputEntityName) {
        BusinessKeyImpl businessKey = new BusinessKeyImpl();
        businessKey.setFormSchemeKey(formSchemeKey);
        businessKey.setPeriod(period);
        MasterEntityImpl masterEntity = new MasterEntityImpl();
        businessKey.setMasterEntity((MasterEntityInfo)masterEntity);
        for (Map.Entry<String, String> entry : masterEntitys.entrySet()) {
            if (inputEntityName.equals(entry.getKey())) {
                masterEntity.setMasterEntityDimessionValue(manageEntityName, entry.getValue());
                continue;
            }
            masterEntity.setMasterEntityDimessionValue(entry.getKey(), entry.getValue());
        }
        return businessKey;
    }

    private GcOrgCacheVO getOrgByScheme(String orgTableName, YearPeriodDO yp, String orgCode) {
        if (StringUtils.isEmpty((String)orgTableName)) {
            return null;
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        GcOrgCacheVO cacheVO = tool.getOrgByCode(orgCode);
        return cacheVO;
    }

    public void onBatchPrepare(UserActionBatchPrepareEvent event) throws Exception {
        ExecutorContext executorContext = new ExecutorContext(this.dataRunTimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        BusinessKeySet businessKeySet = event.getBusinessKeySet();
        WorkFlowType startType = this.iWorkflow.queryStartType(businessKeySet.getFormSchemeKey());
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod());
        if (StringUtils.isEmpty((String)systemId)) {
            return;
        }
        Boolean exeFlag = (Boolean)event.getContext().get(EXECUTE_FLAG_NAME);
        if (exeFlag == null || !exeFlag.booleanValue()) {
            GcDataEntryContext context = new GcDataEntryContext(businessKeySet.getFormSchemeKey(), this.provider);
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)businessKeySet.getFormSchemeKey(), (String)businessKeySet.getPeriod());
            if (!StringUtils.isEmpty((String)context.getOrgTableName())) {
                MasterEntityImpl masterEntityInfo = new MasterEntityImpl();
                if (businessKeySet.getMasterEntitySet().next()) {
                    masterEntityInfo = businessKeySet.getMasterEntitySet().getCurrent();
                }
                businessKeySet.getMasterEntitySet().reset();
                String entityName = this.getEntityName(businessKeySet.getFormSchemeKey());
                String orgId = masterEntityInfo.getMasterEntityKey(entityName);
                GcOrgCacheVO cacheVO = this.getOrgByScheme(context.getOrgTableName(), yp, orgId);
                if (Objects.isNull(cacheVO)) {
                    this.logger.error("\u83b7\u53d6\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0ccode=" + orgId + " mad_org=" + context.getOrgTableName() + ",\u65f6\u671f=" + businessKeySet.getPeriod());
                    return;
                }
                boolean isPersonDefault = this.iWorkflow.isDefaultWorkflow(businessKeySet.getFormSchemeKey());
                boolean isCorporate = this.consolidatedTaskService.isCorporate(this.getTaskId(businessKeySet.getFormSchemeKey()), businessKeySet.getPeriod(), this.getOrgTableName(businessKeySet.getFormSchemeKey()));
                Map<String, String> masterEntity = this.getMasterEntity((MasterEntityInfo)masterEntityInfo, context);
                HashMap<String, DimensionValueSet> dimensionValueSetMap = new HashMap<String, DimensionValueSet>();
                if (isCorporate) {
                    List<BusinessKey> businessKeys = this.listBusinessKey(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), masterEntity, cacheVO.getOrgTypeId(), isCorporate, entityName, dimensionValueSetMap);
                    this.onBatchUploadPrepare(businessKeySet, masterEntity, context, isPersonDefault, startType, dimensionValueSetMap, event, dataAssist, businessKeys);
                } else {
                    boolean isUpload = this.isUploadByDefaultWorkflow(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), isPersonDefault);
                    if (!StringUtils.isEmpty((String)systemId) && isUpload) {
                        List<BusinessKey> businessKeys = this.listBusinessKeyByManageKey(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), cacheVO, masterEntity, context.getOrgTableName(), dimensionValueSetMap);
                        if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
                            this.onBatchUploadPrepare(businessKeySet, masterEntity, context, isPersonDefault, startType, dimensionValueSetMap, event, dataAssist, businessKeys);
                            return;
                        }
                        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                            for (String formKey : businessKeySet.getFormKey()) {
                                this.checkUploadManageScheme(cacheVO, businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), formKey, startType, yp, null, event, orgId);
                            }
                        } else {
                            this.checkUploadManageScheme(cacheVO, businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), null, startType, yp, null, event, orgId);
                        }
                    }
                }
            }
        }
    }

    public void onProgrocessChanged(UserActionProgressEvent event) throws Exception {
    }

    public void onBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
        if (this.getGcUploadStateOption()) {
            return;
        }
        List impls = (List)event.getContext().get(EXECUTE_MANAGE_PARAM_FORMK);
        if (impls != null && impls.size() > 0) {
            this.logger.info("onBatchComplete\u4e0a\u62a5\u6761\u6570\uff1a" + impls.size());
            for (GcUploaderImpl gcUploader : impls) {
                String taskID = gcUploader.getTasks().get(0).getId();
                Actor candicateActor = Actor.fromNpContext();
                if (event.getBusinessKeySet().getFormSchemeKey().equals(gcUploader.getBusinessKeySet().getFormSchemeKey())) continue;
                gcUploader.getRunTimeService().batchCompleteProcessTasks(gcUploader.getBusinessKeySet(), candicateActor, event.getActionId(), taskID, event.getComment(), gcUploader.getTaskContext());
            }
        }
    }

    public Set<String> getListeningActionId() {
        return LISTENINGACTIONS;
    }

    public Integer getSequence() {
        return Integer.MAX_VALUE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void orgUnitUploadActionDefault(BusinessKey businessKey, GcDataEntryContext context, UserActionPrepareEvent event, WorkFlowType startType, boolean isPersonDefault, List<BusinessKey> businessKeys, Map<String, DimensionValueSet> dimensionValueSetMap) {
        if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
            ArrayList<GcUploaderImpl> gcUploaders = new ArrayList<GcUploaderImpl>();
            ArrayList<GcUploaderImpl> gcUploaderTo = new ArrayList<GcUploaderImpl>();
            for (BusinessKey scheme : businessKeys) {
                List<Task> schemeTasks;
                boolean isDefault;
                if (scheme.getFormSchemeKey().equals(businessKey.getFormSchemeKey()) && !org.springframework.util.CollectionUtils.isEmpty(gcUploaderTo) || (isDefault = this.iWorkflow.isDefaultWorkflow(scheme.getFormSchemeKey())) != isPersonDefault) continue;
                WorkFlowType startTypeManage = this.iWorkflow.queryStartType(scheme.getFormSchemeKey());
                if (!startType.equals((Object)startTypeManage)) {
                    this.logger.info("\u5f53\u524d\u5355\u4f4d\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u4e0e\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u6d41\u7a0b\u5bf9\u8c61\u8bbe\u7f6e\u4e0d\u4e00\u81f4\u3002");
                    continue;
                }
                String formSchemeKey = scheme.getFormSchemeKey();
                ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
                Optional processEngine = this.iWorkflow.getProcessEngine(formSchemeKey);
                RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
                List<Task> tasks = this.listTaskByBusinessKey(businessKey, isDefault, event.getActionId(), runTimeService, event.getUserTaskId());
                if (org.springframework.util.CollectionUtils.isEmpty(tasks) || org.springframework.util.CollectionUtils.isEmpty(schemeTasks = this.listTaskByBusinessKey(scheme, isDefault, event.getActionId(), runTimeService, event.getUserTaskId())) || !Objects.nonNull(tasks.get(0)) || !Objects.nonNull(schemeTasks.get(0)) || StringUtils.isEmpty((String)schemeTasks.get(0).getUserTaskId()) || StringUtils.isEmpty((String)tasks.get(0).getUserTaskId()) || !schemeTasks.get(0).getUserTaskId().equals(tasks.get(0).getUserTaskId())) continue;
                if (context.getTask() != null && context.getTask().getKey() != null) {
                    taskContext.put("taskKey", (Object)context.getTask().getKey());
                }
                if (formSchemeKey != null) {
                    taskContext.put("formSchemeKey", (Object)formSchemeKey);
                }
                if (event.getContext().get("formulaSchemeKey") != null) {
                    taskContext.put("formulaSchemeKey", event.getContext().get("formulaSchemeKey"));
                }
                taskContext.put(EXECUTE_FLAG_NAME, (Object)true);
                GcUploaderImpl impl = new GcUploaderImpl((TaskContext)taskContext, runTimeService, schemeTasks, scheme, null, dimensionValueSetMap.get(formSchemeKey));
                gcUploaderTo.add(impl);
                gcUploaders.add(impl);
            }
            DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
            String currentEntityId = dsContext.getContextEntityId();
            for (BusinessKey manageBusinessKey : businessKeys) {
                try {
                    String checkUploadError;
                    FormSchemeDefine manageFormScheme = this.iRunTimeViewController.getFormScheme(manageBusinessKey.getFormSchemeKey());
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(manageFormScheme.getTaskKey());
                    dsContext.setEntityId(taskDefine.getDw());
                    List tasks = this.iWorkflow.queryTasks(businessKey.getFormSchemeKey(), businessKey);
                    if (Objects.isNull(tasks)) {
                        this.logger.error("\u6d41\u7a0b\u8282\u70b9\u4efb\u52a1\u4e0d\u5b58\u5728,businessKey:{}", (Object)businessKey);
                    }
                    if (StringUtils.isEmpty((String)(checkUploadError = this.checkStepUpload(gcUploaders, event.getActionId(), ((Task)tasks.get(0)).getId(), false, null)))) continue;
                    event.setBreak(checkUploadError);
                    return;
                }
                finally {
                    dsContext.setEntityId(currentEntityId);
                }
            }
            event.getContext().put(EXECUTE_MANAGE_PARAM, gcUploaders);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void uploadActionGroup(BusinessKey businessKey, GcDataEntryContext context, UserActionPrepareEvent event, boolean isPersonDefault, WorkFlowType startType, Map<String, DimensionValueSet> dimensionValueSetMap, List<BusinessKey> businessKeys) throws Exception {
        if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
            ArrayList<GcUploaderImpl> gcUploaders = new ArrayList<GcUploaderImpl>();
            ArrayList<GcUploaderImpl> gcUploaderTo = new ArrayList<GcUploaderImpl>();
            for (BusinessKey scheme : businessKeys) {
                List<Task> tasks;
                boolean isDefault;
                if (scheme.getFormSchemeKey().equals(businessKey.getFormSchemeKey()) && !org.springframework.util.CollectionUtils.isEmpty(gcUploaderTo) || (isDefault = this.iWorkflow.isDefaultWorkflow(scheme.getFormSchemeKey())) != isPersonDefault) continue;
                WorkFlowType startTypeManage = this.iWorkflow.queryStartType(scheme.getFormSchemeKey());
                if (!startType.equals((Object)startTypeManage)) {
                    this.logger.info("\u5f53\u524d\u5355\u4f4d\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u4e0e\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u6d41\u7a0b\u5bf9\u8c61\u8bbe\u7f6e\u4e0d\u4e00\u81f4\u3002");
                    continue;
                }
                Map<Object, Object> mangeFormOrFormGroupKeysGroupByTitle = new HashMap();
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    mangeFormOrFormGroupKeysGroupByTitle = this.getFormKeysGroupByTitle(scheme.getFormSchemeKey());
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    mangeFormOrFormGroupKeysGroupByTitle = this.getFormGroupKeysGroupByTitle(scheme.getFormSchemeKey());
                }
                ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
                Optional processEngine = this.iWorkflow.getProcessEngine(scheme.getFormSchemeKey());
                RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
                List<Task> schemeTasks = null;
                List formKeys = new ArrayList();
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    FormDefine formDefine = this.iRunTimeViewController.queryFormById(businessKey.getFormKey());
                    if (!mangeFormOrFormGroupKeysGroupByTitle.keySet().contains(formDefine.getTitle())) continue;
                    formKeys = (List)mangeFormOrFormGroupKeysGroupByTitle.get(formDefine.getTitle());
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    FormGroupDefine groupDefine = this.iRunTimeViewController.queryFormGroup(businessKey.getFormKey());
                    if (!mangeFormOrFormGroupKeysGroupByTitle.keySet().contains(groupDefine.getTitle())) continue;
                    formKeys = (List)mangeFormOrFormGroupKeysGroupByTitle.get(groupDefine.getTitle());
                }
                if (org.springframework.util.CollectionUtils.isEmpty(tasks = this.listTaskByBusinessKey(businessKey, isDefault, event.getActionId(), runTimeService, event.getUserTaskId()))) continue;
                for (String manageFormKey : formKeys) {
                    BusinessKeyImpl formKeyBusinessKey = new BusinessKeyImpl();
                    BeanUtils.copyProperties(scheme, formKeyBusinessKey);
                    formKeyBusinessKey.setFormKey(manageFormKey);
                    schemeTasks = this.listTaskByBusinessKey((BusinessKey)formKeyBusinessKey, isDefault, event.getActionId(), runTimeService, event.getUserTaskId());
                    if (org.springframework.util.CollectionUtils.isEmpty(schemeTasks) || org.springframework.util.CollectionUtils.isEmpty(tasks) || !Objects.nonNull(tasks.get(0)) || !Objects.nonNull(schemeTasks.get(0)) || StringUtils.isEmpty((String)schemeTasks.get(0).getUserTaskId()) || StringUtils.isEmpty((String)tasks.get(0).getUserTaskId()) || !schemeTasks.get(0).getUserTaskId().equals(tasks.get(0).getUserTaskId())) continue;
                    if (context.getTask() != null && context.getTask().getKey() != null) {
                        taskContext.put("taskKey", (Object)context.getTask().getKey());
                    }
                    if (formKeyBusinessKey.getFormSchemeKey() != null) {
                        taskContext.put("formSchemeKey", (Object)formKeyBusinessKey.getFormSchemeKey());
                    }
                    if (event.getContext().get("formulaSchemeKey") != null) {
                        taskContext.put("formulaSchemeKey", event.getContext().get("formulaSchemeKey"));
                    }
                    taskContext.put(EXECUTE_FLAG_NAME, (Object)true);
                    this.logger.info("\u62a5\u8868\uff1a" + formKeyBusinessKey.toString());
                    DimensionValueSet dimensionValueSet = dimensionValueSetMap.get(scheme.getFormSchemeKey());
                    GcUploaderImpl impl = new GcUploaderImpl((TaskContext)taskContext, runTimeService, schemeTasks, (BusinessKey)formKeyBusinessKey, null, dimensionValueSet);
                    gcUploaderTo.add(impl);
                    gcUploaders.add(impl);
                }
            }
            DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
            String currentEntityId = dsContext.getContextEntityId();
            for (BusinessKey manageBusinessKey : businessKeys) {
                try {
                    String checkUploadError;
                    FormSchemeDefine manageFormScheme = this.iRunTimeViewController.getFormScheme(manageBusinessKey.getFormSchemeKey());
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(manageFormScheme.getTaskKey());
                    dsContext.setEntityId(taskDefine.getDw());
                    List tasks = this.iWorkflow.queryTasks(businessKey.getFormSchemeKey(), businessKey);
                    if (Objects.isNull(tasks)) {
                        this.logger.error("\u6d41\u7a0b\u8282\u70b9\u4efb\u52a1\u4e0d\u5b58\u5728,businessKey:{}", (Object)businessKey);
                    }
                    if (StringUtils.isEmpty((String)(checkUploadError = this.checkStepUpload(gcUploaders, event.getActionId(), ((Task)tasks.get(0)).getId(), false, null)))) continue;
                    event.setBreak(checkUploadError);
                    return;
                }
                finally {
                    dsContext.setEntityId(currentEntityId);
                }
            }
            event.getContext().put(EXECUTE_MANAGE_PARAM, gcUploaders);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onBatchUploadPrepare(BusinessKeySet businessKeySet, Map<String, String> masterEntitys, GcDataEntryContext context, boolean isPersonDefault, WorkFlowType startType, Map<String, DimensionValueSet> dimensionValueSetMap, UserActionBatchPrepareEvent event, IDataAssist dataAssist, List<BusinessKey> businessKeys) {
        if (!org.springframework.util.CollectionUtils.isEmpty(businessKeys)) {
            boolean isNeedSync = false;
            ArrayList<GcUploaderImpl> gcUploaders = new ArrayList<GcUploaderImpl>();
            String entityName = this.getEntityName(businessKeySet.getFormSchemeKey());
            ArrayList<GcUploaderImpl> gcUploaderTo = new ArrayList<GcUploaderImpl>();
            for (BusinessKey scheme : businessKeys) {
                boolean isDefault;
                if (scheme.getFormSchemeKey().equals(businessKeySet.getFormSchemeKey()) && !org.springframework.util.CollectionUtils.isEmpty(gcUploaderTo) || (isDefault = this.iWorkflow.isDefaultWorkflow(scheme.getFormSchemeKey())) != isPersonDefault) continue;
                WorkFlowType startTypeManage = this.iWorkflow.queryStartType(scheme.getFormSchemeKey());
                if (!startType.equals((Object)startTypeManage)) {
                    this.logger.info("\u5f53\u524d\u5355\u4f4d\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u4e0e\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u6d41\u7a0b\u5bf9\u8c61\u8bbe\u7f6e\u4e0d\u4e00\u81f4\u3002");
                    continue;
                }
                ArrayList<DimensionValueSet> dimSets = new ArrayList<DimensionValueSet>();
                HashSet<String> formKeySet = new HashSet<String>();
                ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
                Optional processEngine = this.iWorkflow.getProcessEngine(scheme.getFormSchemeKey());
                RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
                List<Object> schemeTasks = new ArrayList();
                MasterEntityInfo schemeInfo = scheme.getMasterEntityInfo();
                DimensionValueSet dimensionValueSetScheme = new DimensionValueSet();
                Collection masterTableNames = schemeInfo.getDimessionNames();
                for (Object tableName : masterTableNames) {
                    DimensionSet dimension = dataAssist.getDimensionsByTableName((String)tableName);
                    if (dimension != null && dimension.size() > 0) {
                        dimensionValueSetScheme.setValue(dimension.get(0), (Object)schemeInfo.getMasterEntityKey((String)tableName));
                        continue;
                    }
                    dimensionValueSetScheme.setValue((String)tableName, (Object)schemeInfo.getMasterEntityKey((String)tableName));
                }
                dimensionValueSetScheme.setValue("DATATIME", (Object)scheme.getPeriod());
                dimSets.add(dimensionValueSetScheme);
                if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                    Object tableName;
                    Map<Object, Object> mangeFormOrFormGroupKeysGroupByTitle = new HashMap();
                    if (WorkFlowType.FORM.equals((Object)startType)) {
                        mangeFormOrFormGroupKeysGroupByTitle = this.getFormKeysGroupByTitle(scheme.getFormSchemeKey());
                    }
                    if (WorkFlowType.GROUP.equals((Object)startType)) {
                        mangeFormOrFormGroupKeysGroupByTitle = this.getFormGroupKeysGroupByTitle(scheme.getFormSchemeKey());
                    }
                    tableName = businessKeySet.getFormKey().iterator();
                    while (tableName.hasNext()) {
                        Set<String> formKeys;
                        String formKey = (String)tableName.next();
                        List<String> manageFormKeys = new ArrayList<String>();
                        if (WorkFlowType.FORM.equals((Object)startType)) {
                            FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
                            if (!mangeFormOrFormGroupKeysGroupByTitle.keySet().contains(formDefine.getTitle())) continue;
                            manageFormKeys = (List)mangeFormOrFormGroupKeysGroupByTitle.get(formDefine.getTitle());
                        }
                        if (WorkFlowType.GROUP.equals((Object)startType)) {
                            FormGroupDefine groupDefine = this.iRunTimeViewController.queryFormGroup(formKey);
                            if (!mangeFormOrFormGroupKeysGroupByTitle.keySet().contains(groupDefine.getTitle())) continue;
                            manageFormKeys = (List)mangeFormOrFormGroupKeysGroupByTitle.get(groupDefine.getTitle());
                        }
                        if (org.springframework.util.CollectionUtils.isEmpty(manageFormKeys) || org.springframework.util.CollectionUtils.isEmpty(formKeys = this.getFormKeyOrFormGroupKey(manageFormKeys, businessKeySet, masterEntitys, (TaskContext)taskContext, context, isDefault, formKey, event, scheme, runTimeService, schemeTasks, entityName))) continue;
                        formKeySet.addAll(formKeys);
                        isNeedSync = true;
                    }
                } else {
                    BusinessKey define = this.buildBusinessKey(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), context.getOrgTableName(), masterEntitys, entityName);
                    List<Task> tasks = this.listTaskByBusinessKey(define, isDefault, event.getActionId(), runTimeService, event.getUserTaskId());
                    if (!org.springframework.util.CollectionUtils.isEmpty(tasks) && !org.springframework.util.CollectionUtils.isEmpty(schemeTasks = this.listTaskByBusinessKey(scheme, isDefault, event.getActionId(), runTimeService, event.getUserTaskId())) && Objects.nonNull(tasks.get(0)) && Objects.nonNull(schemeTasks.get(0)) && !StringUtils.isEmpty((String)((Task)schemeTasks.get(0)).getUserTaskId()) && !StringUtils.isEmpty((String)tasks.get(0).getUserTaskId()) && ((Task)schemeTasks.get(0)).getUserTaskId().equals(tasks.get(0).getUserTaskId())) {
                        if (context.getTask() != null && context.getTask().getKey() != null) {
                            taskContext.put("taskKey", (Object)context.getTask().getKey());
                        }
                        if (scheme.getFormSchemeKey() != null) {
                            taskContext.put("formSchemeKey", (Object)scheme.getFormSchemeKey());
                        }
                        if (event.getContext().get("formulaSchemeKey") != null) {
                            taskContext.put("formulaSchemeKey", event.getContext().get("formulaSchemeKey"));
                        }
                        isNeedSync = true;
                    }
                }
                if (org.springframework.util.CollectionUtils.isEmpty(dimSets) || org.springframework.util.CollectionUtils.isEmpty(schemeTasks) || !isNeedSync) continue;
                this.logger.info("\u62a5\u8868\uff1a" + ((Object)formKeySet).toString());
                taskContext.put(EXECUTE_FLAG_NAME, (Object)true);
                BusinessKeySet buildBusinessKeySet = this.businessGenerator.buildBusinessKeySet(scheme.getFormSchemeKey(), dimSets, formKeySet, formKeySet);
                DimensionValueSet dimensionValueSet = dimensionValueSetMap.get(buildBusinessKeySet.getFormSchemeKey());
                GcUploaderImpl impl = new GcUploaderImpl((TaskContext)taskContext, runTimeService, schemeTasks, null, buildBusinessKeySet, dimensionValueSet);
                gcUploaderTo.add(impl);
                gcUploaders.add(impl);
            }
            DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
            String currentEntityId = dsContext.getContextEntityId();
            for (BusinessKey manageBusinessKeySet : businessKeys) {
                try {
                    String checkUploadError;
                    FormSchemeDefine manageFormScheme = this.iRunTimeViewController.getFormScheme(manageBusinessKeySet.getFormSchemeKey());
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(manageFormScheme.getTaskKey());
                    dsContext.setEntityId(taskDefine.getDw());
                    List tasks = this.iWorkflow.queryTasks(businessKeySet.getFormSchemeKey(), manageBusinessKeySet);
                    if (Objects.isNull(tasks)) {
                        this.logger.error("\u6d41\u7a0b\u8282\u70b9\u4efb\u52a1\u4e0d\u5b58\u5728,businessKey:{}", (Object)businessKeySet);
                    }
                    if (StringUtils.isEmpty((String)(checkUploadError = this.checkStepUpload(gcUploaders, event.getActionId(), ((Task)tasks.get(0)).getId(), true, null)))) continue;
                    event.setBreak(checkUploadError);
                    return;
                }
                finally {
                    dsContext.setEntityId(currentEntityId);
                }
            }
            event.getContext().put(EXECUTE_MANAGE_PARAM_FORMK, gcUploaders);
        }
    }

    private boolean isUploadByDefaultWorkflow(String formSchemeKey, String periodStr, boolean isDefault) {
        boolean isCorporateDefault;
        String inputSchemeId;
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(formSchemeKey, periodStr);
        return consolidatedTaskVO != null && !StringUtils.isEmpty((String)(inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)periodStr))) && isDefault == (isCorporateDefault = this.iWorkflow.isDefaultWorkflow(inputSchemeId));
    }

    private List<Task> listTaskByBusinessKey(BusinessKey businessKey, boolean startIfInstanceNotExist, String actionId, RunTimeService runTimeService, String userTaskId) {
        List tasks = null;
        if ("act_retrieve".equals(actionId)) {
            Optional processInstanceOptional = runTimeService.queryInstanceByBusinessKey(businessKey.toString());
            if (processInstanceOptional.isPresent()) {
                tasks = runTimeService.queryTaskByBusinessKey(businessKey.toString(), startIfInstanceNotExist);
            }
        } else {
            tasks = runTimeService.queryTaskByBusinessKey(businessKey.toString(), startIfInstanceNotExist);
        }
        return tasks;
    }

    private Map<String, String> getMasterEntity(MasterEntityInfo masterEntity, GcDataEntryContext context) {
        HashMap<String, String> masterEntitys = org.springframework.util.CollectionUtils.newHashMap(masterEntity.dimessionSize());
        String entityName = this.getEntityName(context.getFormSchemeKey());
        for (String name : masterEntity.getDimessionNames()) {
            if (name.equalsIgnoreCase(entityName)) {
                masterEntitys.put(entityName, masterEntity.getMasterEntityKey(name));
                continue;
            }
            masterEntitys.put(name, masterEntity.getMasterEntityKey(name));
        }
        return masterEntitys;
    }

    private List<BusinessKey> listBusinessKeyByManageKey(String formSchemeKey, String periodStr, GcOrgCacheVO cacheVO, Map<String, String> masterEntitys, String entityName, Map<String, DimensionValueSet> dimensionValueSetMap) {
        ConsolidatedTaskVO consolidatedTask = this.consolidatedTaskService.getTaskBySchemeId(formSchemeKey, periodStr);
        LinkedHashSet manageCodes = consolidatedTask.getManageCalcUnitCodes();
        if (!consolidatedTask.getManageCalcUnitCodes().isEmpty() && manageCodes.contains(cacheVO.getCode())) {
            return this.listBusinessKey(formSchemeKey, periodStr, masterEntitys, cacheVO.getOrgTypeId(), false, entityName, dimensionValueSetMap);
        }
        return Collections.emptyList();
    }

    private void checkUploadManageScheme(GcOrgCacheVO cacheVO, String manageFormSchemeKey, String periodStr, String formKey, WorkFlowType startType, YearPeriodDO yp, UserActionPrepareEvent event, UserActionBatchPrepareEvent batchEvent, String orgId) {
        if (cacheVO != null) {
            ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(manageFormSchemeKey, periodStr);
            if (consolidatedTaskVO == null || consolidatedTaskVO.getInputTaskInfo() == null) {
                return;
            }
            String inputTaskTitle = consolidatedTaskVO.getInputTaskInfo().getTaskTitle();
            String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)periodStr);
            if (StringUtils.isEmpty((String)inputSchemeId)) {
                return;
            }
            if (manageFormSchemeKey.equals(inputSchemeId)) {
                if (org.springframework.util.CollectionUtils.isEmpty(consolidatedTaskVO.getManageEntityList())) {
                    return;
                }
                String manageOrgTable = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)consolidatedTaskVO.getTaskKey());
                if (consolidatedTaskVO.getManageEntityList().contains(manageOrgTable)) {
                    this.checkUpload(consolidatedTaskVO.getCorporateEntity(), cacheVO, inputSchemeId, formKey, startType, yp, event, batchEvent, inputTaskTitle + GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmultiorgmsg"));
                }
            } else {
                String orgTableName = this.getOrgTableCodeByFormSchemeKey(inputSchemeId);
                this.checkUpload(orgTableName, cacheVO, inputSchemeId, formKey, startType, yp, event, batchEvent, inputTaskTitle);
            }
        } else {
            this.logger.info("\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u4e0b\uff0c\u5f53\u524d\u5355\u4f4d\u7684\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a\u3002");
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.manageorgtypeemptymsg"));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.manageorgtypeemptymsg"));
            }
        }
    }

    private void checkFormUpload(GcOrgCacheVO cacheVO, String orgTypeId, String inputSchemeId, String formKey, String inputTaskTitle, UserActionPrepareEvent event, UserActionBatchPrepareEvent batchEvent) {
        FormDefine formDefine;
        List formTitles;
        if (cacheVO != null && orgTypeId.equals(cacheVO.getOrgTypeId()) && (formTitles = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(inputSchemeId).stream().map(IBaseMetaItem::getTitle).collect(Collectors.toList())).contains((formDefine = this.iRunTimeViewController.queryFormById(formKey)).getTitle())) {
            this.logger.info("\u5f53\u524d\u8868\u5355\u3010" + formDefine.getTitle() + "\u3011\uff0c\u5355\u4f4d\u3010" + cacheVO.getCode() + "\u3011");
            Object[] args = new String[]{inputTaskTitle};
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            }
        }
    }

    private void checkFormGroupUpload(GcOrgCacheVO cacheVO, String orgTypeId, String inputSchemeId, String formKey, String inputTaskTitle, UserActionPrepareEvent event, UserActionBatchPrepareEvent batchEvent) {
        FormGroupDefine groupDefine;
        List rootGroupFormKeys = this.iRunTimeViewController.queryRootGroupsByFormScheme(inputSchemeId).stream().map(IMetaGroup::getTitle).collect(Collectors.toList());
        if (rootGroupFormKeys.contains((groupDefine = this.iRunTimeViewController.queryFormGroup(formKey)).getTitle()) && cacheVO != null && orgTypeId.equals(cacheVO.getOrgTypeId())) {
            this.logger.info("\u5f53\u524d\u62a5\u8868\u5206\u7ec4\u3010" + groupDefine.getTitle() + "\u3011\uff0c\u5355\u4f4d\u3010" + cacheVO.getCode() + "\u3011");
            Object[] args = new String[]{inputTaskTitle};
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            }
        }
    }

    private void checkUpload(String orgTableName, GcOrgCacheVO cacheVO, String inputSchemeId, String formKey, WorkFlowType startType, YearPeriodDO yp, UserActionPrepareEvent event, UserActionBatchPrepareEvent batchEvent, String inputTaskTitle) {
        GcOrgCacheVO inputSchemeOrg = this.getOrgByScheme(orgTableName, yp, cacheVO.getCode());
        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
            if (WorkFlowType.FORM.equals((Object)startType)) {
                this.checkFormUpload(inputSchemeOrg, cacheVO.getOrgTypeId(), inputSchemeId, formKey, inputTaskTitle, event, batchEvent);
            }
            if (WorkFlowType.GROUP.equals((Object)startType)) {
                this.checkFormGroupUpload(inputSchemeOrg, cacheVO.getOrgTypeId(), inputSchemeId, formKey, inputTaskTitle, event, batchEvent);
            }
        } else if (inputSchemeOrg != null && cacheVO.getOrgTypeId().equals(inputSchemeOrg.getOrgTypeId())) {
            this.logger.info("\u4e3b\u4f53\u4e0a\u62a5\uff0c\u5355\u4f4d\u3010" + cacheVO.getCode() + "\u3011");
            Object[] args = new String[]{inputTaskTitle};
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            }
        }
    }

    private String getOrgTableCodeByFormSchemeKey(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        TableModelDefine tableDefine = this.entityMetaService.getTableModel(formSchemeDefine.getDw());
        return tableDefine.getName();
    }

    private Map<String, List<String>> getFormKeysGroupByTitle(String formSchemeKey) {
        HashMap<String, List<String>> formKeysGroupByTitle = new HashMap<String, List<String>>();
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefines) {
            String title = formDefine.getTitle();
            if (StringUtils.isEmpty((String)title)) continue;
            List fromKeys = formKeysGroupByTitle.computeIfAbsent(title, item -> new ArrayList());
            fromKeys.add(formDefine.getKey());
        }
        return formKeysGroupByTitle;
    }

    private Map<String, List<String>> getFormGroupKeysGroupByTitle(String formSchemeKey) {
        HashMap<String, List<String>> formGroupKeysGroupByTitle = new HashMap<String, List<String>>();
        List formGroupDefines = this.iRunTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        for (FormGroupDefine formGroupDefine : formGroupDefines) {
            String title = formGroupDefine.getTitle();
            if (StringUtils.isEmpty((String)title)) continue;
            List fromKeys = formGroupKeysGroupByTitle.computeIfAbsent(title, item -> new ArrayList());
            fromKeys.add(formGroupDefine.getKey());
        }
        return formGroupKeysGroupByTitle;
    }

    private Set<String> getFormKeyOrFormGroupKey(List<String> manageFormKeys, BusinessKeySet businessKeySet, Map<String, String> masterEntitys, TaskContext taskContext, GcDataEntryContext context, boolean isDefault, String formKey, UserActionBatchPrepareEvent event, BusinessKey scheme, RunTimeService runTimeService, List<Task> schemeTasks, String entityName) {
        HashSet<String> formKeySet = new HashSet<String>();
        String orgTableName = this.getOrgTableCodeByFormSchemeKey(businessKeySet.getFormSchemeKey());
        BusinessKey define = this.buildBusinessKey(businessKeySet.getFormSchemeKey(), businessKeySet.getPeriod(), orgTableName, masterEntitys, context.getOrgTableName());
        define.setFormKey(formKey);
        List<Task> tasks = this.listTaskByBusinessKey(define, isDefault, event.getActionId(), runTimeService, event.getUserTaskId());
        if (org.springframework.util.CollectionUtils.isEmpty(tasks)) {
            return formKeySet;
        }
        for (String manageFormKey : manageFormKeys) {
            BusinessKeyImpl formKeyBusinessKey = new BusinessKeyImpl();
            BeanUtils.copyProperties(scheme, formKeyBusinessKey);
            formKeyBusinessKey.setFormKey(manageFormKey);
            List<Task> manageSchemeTasks = this.listTaskByBusinessKey((BusinessKey)formKeyBusinessKey, isDefault, event.getActionId(), runTimeService, event.getUserTaskId());
            if (org.springframework.util.CollectionUtils.isEmpty(manageSchemeTasks) || !Objects.nonNull(tasks.get(0)) || !Objects.nonNull(manageSchemeTasks.get(0)) || StringUtils.isEmpty((String)manageSchemeTasks.get(0).getUserTaskId()) || StringUtils.isEmpty((String)tasks.get(0).getUserTaskId()) || !manageSchemeTasks.get(0).getUserTaskId().equals(tasks.get(0).getUserTaskId())) continue;
            if (context.getTask() != null && context.getTask().getKey() != null) {
                taskContext.put("taskKey", (Object)context.getTask().getKey());
            }
            if (formKeyBusinessKey.getFormSchemeKey() != null) {
                taskContext.put("formSchemeKey", (Object)formKeyBusinessKey.getFormSchemeKey());
            }
            if (event.getContext().get("formulaSchemeKey") != null) {
                taskContext.put("formulaSchemeKey", event.getContext().get("formulaSchemeKey"));
            }
            schemeTasks.addAll(manageSchemeTasks);
            formKeySet.add(manageFormKey);
        }
        return formKeySet;
    }

    private String checkStepUpload(List<GcUploaderImpl> gcUploaders, String actionId, String userTaskId, boolean isBatch, String entityId) {
        if (!this.getGcUploadStateOption()) {
            return null;
        }
        String message = null;
        try {
            message = this.stepByOpt(gcUploaders, actionId, userTaskId, isBatch, entityId);
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u540c\u6b65\u53d1\u751f\u5f02\u5e38", e);
            throw new RuntimeException("\u72b6\u6001\u540c\u6b65\u53d1\u751f\u5f02\u5e38", e);
        }
        return message;
    }

    private String stepByOpt(List<GcUploaderImpl> gcUploaders, String actionId, String userTaskId, boolean isBatch, String entityId) {
        StringBuilder stringBuilder = new StringBuilder();
        String entityTitle = null;
        if (!StringUtils.isEmpty((String)entityId)) {
            entityTitle = this.entityMetaService.queryEntity(entityId).getTitle();
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(gcUploaders)) {
            for (GcUploaderImpl gcUploader : gcUploaders) {
                BatchStepByStepParam stepByOptParam;
                if (isBatch) {
                    BatchStepByStepResult stepByStepCheckResult;
                    BusinessKeySet businessKeySet = gcUploader.getBusinessKeySet();
                    stepByOptParam = new BatchStepByStepParam();
                    stepByOptParam.setContext(gcUploader.getTaskContext());
                    stepByOptParam.setActionId(actionId);
                    Set formKeys = businessKeySet.getFormKey();
                    if (!org.springframework.util.CollectionUtils.isEmpty(formKeys)) {
                        LinkedHashSet formKeySets = formKeys.stream().collect(Collectors.toCollection(LinkedHashSet::new));
                        HashMap<String, LinkedHashSet> forms = new HashMap<String, LinkedHashSet>();
                        forms.put(String.valueOf(gcUploader.getDimensionValue().getValue("MD_ORG")), formKeySets);
                        stepByOptParam.setFormKeys(forms);
                        stepByOptParam.setGroupKeys(forms);
                    }
                    stepByOptParam.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                    if (StringUtils.isEmpty((String)userTaskId)) {
                        userTaskId = gcUploader.getTasks().get(0).getUserTaskId();
                    }
                    stepByOptParam.setTaskId(userTaskId);
                    stepByOptParam.setPeriod(businessKeySet.getPeriod());
                    stepByOptParam.setForceUpload(true);
                    ArrayList<DimensionValueSet> dimensionSets = new ArrayList<DimensionValueSet>();
                    dimensionSets.add(gcUploader.getDimensionValue());
                    stepByOptParam.setStepUnit(dimensionSets);
                    stepByOptParam.setNodeId(userTaskId);
                    FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(businessKeySet.getFormSchemeKey());
                    WorkFlowType startType = this.iWorkflow.queryStartType(formSchemeDefine.getKey());
                    String formOrGroupTip = WorkFlowType.FORM.equals((Object)startType) ? "\u62a5\u8868" : (WorkFlowType.GROUP.equals((Object)startType) ? "\u62a5\u8868\u5206\u7ec4" : "\u5355\u4f4d");
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
                    if ("act_retrieve".equals(actionId) || !Objects.nonNull(stepByStepCheckResult = this.stepByStep.batchStepByOpt(stepByOptParam)) || org.springframework.util.CollectionUtils.isEmpty(stepByStepCheckResult.getNoOperateUnitMap()) && org.springframework.util.CollectionUtils.isEmpty(stepByStepCheckResult.getNoOperateGroupOrFormMap())) continue;
                    stringBuilder.append("\u4efb\u52a1\u3010").append(taskDefine.getTitle()).append("\u3011");
                    if (!StringUtils.isEmpty((String)entityTitle)) {
                        stringBuilder.append("-\u3010").append(entityTitle).append("\u3011");
                    }
                    HashMap<String, Set> formOrGroupTitleGroupByOrgName = new HashMap<String, Set>();
                    if (!org.springframework.util.CollectionUtils.isEmpty(stepByStepCheckResult.getNoOperateGroupOrFormMap())) {
                        for (Map items : stepByStepCheckResult.getNoOperateGroupOrFormMap().values()) {
                            for (BatchNoOperate batchNoOperate : items.keySet()) {
                                List batchNoOperates = (List)items.get(batchNoOperate);
                                for (BatchNoOperate noOperate : batchNoOperates) {
                                    Set formOrGroupTitles = formOrGroupTitleGroupByOrgName.computeIfAbsent(noOperate.getName(), orgTitle -> new HashSet());
                                    formOrGroupTitles.add(batchNoOperate.getName());
                                }
                            }
                        }
                        for (String orgName : formOrGroupTitleGroupByOrgName.keySet()) {
                            String formOrGroupTitles = CollectionUtils.toString((Object[])((Set)formOrGroupTitleGroupByOrgName.get(orgName)).toArray(), (String)"\uff0c");
                            stringBuilder.append("\u5355\u4f4d\u3010").append(orgName).append("\u3011").append(formOrGroupTip).append("\u3010").append(formOrGroupTitles).append("\u3011\u672a\u8fdb\u884c\u9000\u56de\uff1b");
                        }
                    }
                    if (org.springframework.util.CollectionUtils.isEmpty(stepByStepCheckResult.getNoOperateUnitMap())) continue;
                    Map items = stepByStepCheckResult.getNoOperateUnitMap();
                    for (BatchNoOperate batchNoOperate : items.keySet()) {
                        List batchNoOperates = (List)items.get(batchNoOperate);
                        for (BatchNoOperate noOperate : batchNoOperates) {
                            Set formOrGroupTitles = formOrGroupTitleGroupByOrgName.computeIfAbsent(batchNoOperate.getName(), orgTitle -> new HashSet());
                            formOrGroupTitles.add(noOperate.getName());
                        }
                    }
                    for (String orgName : formOrGroupTitleGroupByOrgName.keySet()) {
                        String formOrGroupTitles = CollectionUtils.toString((Object[])((Set)formOrGroupTitleGroupByOrgName.get(orgName)).toArray(), (String)"\uff0c");
                        stringBuilder.append(formOrGroupTip).append("\u3010").append(formOrGroupTitles).append("\u3011\uff0c\u5355\u4f4d\u3010").append(orgName).append("\u3011").append("\u672a\u8fdb\u884c\u9000\u56de\uff1b");
                    }
                    continue;
                }
                BusinessKey businessKey = gcUploader.getBusinessKey();
                stepByOptParam = new StepByOptParam();
                stepByOptParam.setContext(gcUploader.getTaskContext());
                stepByOptParam.setActionId(actionId);
                stepByOptParam.setFormKey(businessKey.getFormKey());
                stepByOptParam.setGroupKey(businessKey.getFormKey());
                stepByOptParam.setFormSchemeKey(businessKey.getFormSchemeKey());
                if (StringUtils.isEmpty((String)userTaskId)) {
                    userTaskId = gcUploader.getTasks().get(0).getUserTaskId();
                }
                stepByOptParam.setTaskId(userTaskId);
                stepByOptParam.setPeriod(businessKey.getPeriod());
                stepByOptParam.setForceUpload(true);
                stepByOptParam.setDimensionValue(gcUploader.getDimensionValue());
                stepByOptParam.setNodeId(userTaskId);
                FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
                if ("act_retrieve".equals(actionId)) {
                    CompleteMsg completeMsg = this.stepByStep.executeTask((StepByOptParam)stepByOptParam);
                    if (completeMsg.isSucceed()) continue;
                    YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)businessKey.getFormSchemeKey(), (String)businessKey.getPeriod());
                    String orgTableName = this.getOrgTableCodeByFormSchemeKey(formSchemeDefine.getKey());
                    GcOrgCacheVO gcOrgCache = this.getOrgByScheme(orgTableName, yp, String.valueOf(gcUploader.getDimensionValue().getValue("MD_ORG")));
                    stringBuilder.append("\u4efb\u52a1\u3010").append(taskDefine.getTitle()).append("\u3011");
                    if (!StringUtils.isEmpty((String)entityTitle)) {
                        stringBuilder.append("-\u3010").append(entityTitle).append("\u3011");
                    }
                    stringBuilder.append("\uff0c\u5355\u4f4d\u3010").append(gcOrgCache.getTitle()).append("\u3011\u7531\u4e8e\u4e0a\u7ea7\u5355\u4f4d\u5df2\u4e0a\u62a5\uff0c\u4e0d\u80fd\u8fdb\u884c\u53d6\u56de\u64cd\u4f5c\uff1b");
                    continue;
                }
                StepByStepCheckResult stepByStepCheckResult = this.stepByStep.stepByOpt((StepByOptParam)stepByOptParam);
                if (!Objects.nonNull(stepByStepCheckResult) || org.springframework.util.CollectionUtils.isEmpty(stepByStepCheckResult.getItems())) continue;
                stringBuilder.append("\u4efb\u52a1\u3010").append(taskDefine.getTitle()).append("\u3011");
                if (!StringUtils.isEmpty((String)entityTitle)) {
                    stringBuilder.append("-\u3010").append(entityTitle).append("\u3011\uff0c");
                }
                for (StepByStepCheckItem item : stepByStepCheckResult.getItems()) {
                    stringBuilder.append("\u5355\u4f4d\u3010").append(item.getUnitTitle()).append("\u3011\u672a\u8fdb\u884c").append(stepByStepCheckResult.getActionStateTitle()).append("\uff1b");
                }
            }
        }
        return stringBuilder.toString();
    }

    private boolean getGcUploadStateOption() {
        String uploadStateFlag = this.iNvwaSystemOptionService.findValueById("GCUPLOAD_STATE_OPTION");
        return !StringUtils.isEmpty((String)uploadStateFlag) && !"0".equals(uploadStateFlag);
    }

    private String getOrgTableName(String formSchemeKey) {
        return GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.getTaskId(formSchemeKey));
    }

    private String getTaskId(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        return formSchemeDefine.getTaskKey();
    }

    private String getEntityName(String formSchemeKey) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(formScheme.getDw());
        String dimensionName = iEntityDefine.getDimensionName();
        return this.businessGenerator.getTableName(formSchemeKey, dimensionName);
    }

    private String getManageEntityName(String formSchemeKey) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(taskDefine.getDw());
        return tableModel.getCode();
    }

    static {
        LISTENINGACTIONS.add("act_submit");
        LISTENINGACTIONS.add("act_return");
        LISTENINGACTIONS.add("act_upload");
        LISTENINGACTIONS.add("act_reject");
        LISTENINGACTIONS.add("act_confirm");
        LISTENINGACTIONS.add("act_retrieve");
        LISTENINGACTIONS.add("act_cancel_confirm");
        LISTENINGACTIONS.add("cus_submit");
        LISTENINGACTIONS.add("cus_upload");
        LISTENINGACTIONS.add("cus_reject");
        LISTENINGACTIONS.add("cus_return");
        LISTENINGACTIONS.add("cus_confirm");
    }
}

