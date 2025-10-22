/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.bpm.ProcessEngine
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntity
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityInfo
 *  com.jiuqi.nr.bpm.common.ConcurrentTaskContext
 *  com.jiuqi.nr.bpm.common.ProcessDefinition
 *  com.jiuqi.nr.bpm.common.ProcessInstance
 *  com.jiuqi.nr.bpm.common.Task
 *  com.jiuqi.nr.bpm.common.TaskContext
 *  com.jiuqi.nr.bpm.common.UploadRecordNew
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.impl.upload.modeling.DefaultProcessBuilder
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.service.RunTimeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.nr.impl.dao.DataDeleteFlowDAO;
import com.jiuqi.gcreport.nr.impl.service.ProcessInstancesService;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.impl.upload.modeling.DefaultProcessBuilder;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstancesServiceImpl
implements ProcessInstancesService {
    @Autowired
    private ProcessEngine processEngine;
    private RunTimeService runTimeService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IBatchQueryUploadStateService iBatchQueryUploadStateService;
    @Autowired
    private DataDeleteFlowDAO dataDeleteFlowDAO;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    private static Logger logger = LoggerFactory.getLogger(ProcessInstancesServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IWorkflow iWorkflow;
    private String corporateTable;
    private String managerTable;

    @Override
    public void restoreProcessInstances(String sourceTaskKey, String targetTaskKey, String periodStr) {
        GcOrgCacheVO gcOrgCacheVOPerson;
        this.runTimeService = this.processEngine.getRunTimeService();
        FormSchemeDefine formScheme = this.getFormSchemeManage(sourceTaskKey, periodStr);
        String formSchemeKeyPerson = formScheme.getKey();
        this.corporateTable = this.getEntitieTable(formScheme.getDw());
        FormSchemeDefine formSchemeManage = this.getFormSchemeManage(targetTaskKey, periodStr);
        String formSchemeKeyManage = formSchemeManage.getKey();
        this.managerTable = this.getEntitieTable(formSchemeManage.getDw());
        YearPeriodDO coryp = GcPeriodAssistUtil.getPeriodObject(formScheme.getKey(), periodStr);
        GcOrgCenterService corporateTool = GcOrgPublicTool.getInstance((String)this.corporateTable, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)coryp);
        YearPeriodDO manyp = GcPeriodAssistUtil.getPeriodObject(formSchemeManage.getKey(), periodStr);
        GcOrgCenterService managerTool = GcOrgPublicTool.getInstance((String)this.managerTable, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)manyp);
        WorkFlowType startType = this.iWorkflow.queryStartType(formScheme.getKey());
        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
            throw new RuntimeException("\u65b9\u6848\uff1a" + formScheme.getTitle() + "\u4e0d\u652f\u6301\u5206\u7ec4\u4fee\u590d\uff01");
        }
        WorkFlowType startTypeManage = this.iWorkflow.queryStartType(formSchemeManage.getKey());
        if (!WorkFlowType.ENTITY.equals((Object)startTypeManage)) {
            throw new RuntimeException("\u65b9\u6848\uff1a" + formSchemeManage.getTitle() + "\u4e0d\u652f\u6301\u5206\u7ec4\u4fee\u590d\uff01");
        }
        String userId = NpContextHolder.getContext().getUserId();
        Map<String, BusinessKey> businessKeyPersonMap = this.getBusinessKey(formScheme.getFormSchemeCode(), periodStr, corporateTool, this.corporateTable);
        Map<String, BusinessKey> businessKeyManageMap = this.getBusinessKey(formSchemeManage.getFormSchemeCode(), periodStr, managerTool, this.managerTable);
        String processDefinitionManageKey = DefaultProcessBuilder.generateDefaultProcessId((String)formSchemeManage.getFormSchemeCode());
        for (String keyPerson : businessKeyPersonMap.keySet()) {
            BusinessKey businessKeyPerson = businessKeyPersonMap.get(keyPerson);
            BusinessKey businessKeyManage = businessKeyManageMap.get(keyPerson);
            MasterEntity masterEntity = businessKeyPerson.getMasterEntity();
            gcOrgCacheVOPerson = corporateTool.getOrgByCode(masterEntity.getMasterEntityKey(this.corporateTable));
            GcOrgCacheVO gcOrgCacheVOManage = managerTool.getOrgByCode(gcOrgCacheVOPerson.getId());
            if (businessKeyManage == null) {
                if (gcOrgCacheVOManage == null || gcOrgCacheVOManage.getOrgKind() == null || !GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVOManage.getOrgKind()) || !GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVOManage.getOrgKind()) || !"0".equals(gcOrgCacheVOManage.getBblx())) continue;
                businessKeyManage = new BusinessKeyImpl();
                MasterEntityImpl masterEntityManage = new MasterEntityImpl();
                masterEntityManage.setMasterEntityDimessionValue(this.managerTable, gcOrgCacheVOPerson.getId());
                businessKeyManage.setMasterEntity((MasterEntityInfo)masterEntityManage);
                businessKeyManage.setFormSchemeKey(formSchemeKeyManage);
                businessKeyManage.setFormKey(businessKeyPerson.getFormKey());
                businessKeyManage.setPeriod(businessKeyPerson.getPeriod());
            }
            List tasksPerson = this.runTimeService.queryTaskByBusinessKey(businessKeyPerson.toString());
            List tasksManage = this.runTimeService.queryTaskByBusinessKey(businessKeyManage.toString());
            for (Task task_p : tasksPerson) {
                String personTaskDefinitionKey = task_p.getUserTaskId();
                if (tasksManage != null && tasksManage.size() > 0) {
                    for (Task task_m : tasksManage) {
                        if (task_m.getName().equals(task_p.getName())) continue;
                        businessKeyPerson.setFormSchemeKey(formSchemeKeyManage);
                        businessKeyPerson.setMasterEntity((MasterEntityInfo)businessKeyManage.getMasterEntity());
                        this.deleteFlow(task_m.getProcessInstanceId());
                        this.startProcessByProcessDefinitionKey(processDefinitionManageKey, personTaskDefinitionKey, businessKeyPerson, userId);
                    }
                    continue;
                }
                this.startProcessByProcessDefinitionKey(processDefinitionManageKey, personTaskDefinitionKey, businessKeyManage, userId);
            }
            businessKeyManageMap.remove(keyPerson);
        }
        for (String keyManage : businessKeyManageMap.keySet()) {
            BusinessKey businessKeyManage = businessKeyManageMap.get(keyManage);
            MasterEntity masterEntity = businessKeyManage.getMasterEntity();
            GcOrgCacheVO gcOrgCacheVO = managerTool.getOrgByCode(masterEntity.getMasterEntityKey(this.managerTable));
            if (gcOrgCacheVO == null || (gcOrgCacheVOPerson = corporateTool.getOrgByCode(gcOrgCacheVO.getId())) != null && gcOrgCacheVOPerson.getOrgKind() != null && GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVOPerson.getOrgKind()) && GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVOPerson.getOrgKind()) && "0".equals(gcOrgCacheVOPerson.getBblx())) continue;
            List tasksManage = this.runTimeService.queryTaskByBusinessKey(businessKeyManage.toString());
            for (Task task : tasksManage) {
                this.deleteFlow(task.getProcessInstanceId());
            }
        }
        this.insertUploadRecordByQuery(formSchemeKeyPerson, formSchemeKeyManage, periodStr);
    }

    @Override
    public void deleteFlow(String procInstId) {
        this.dataDeleteFlowDAO.deleteFlow(procInstId);
    }

    @Override
    public void deleteUpload(BusinessKey businessKey) {
        this.iBatchQueryUploadStateService.deleteUploadState(businessKey);
        this.iBatchQueryUploadStateService.deleteUploadRecord(businessKey);
    }

    @Override
    public void insertUploadRecordByQuery(String formSchemeKeyPerson, String formSchemeKeyManage, String periodStr) {
        ExecutorContext context = new ExecutorContext(this.dataRunTimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        YearPeriodDO coryp = GcPeriodAssistUtil.getPeriodObject(formSchemeKeyPerson, periodStr);
        GcOrgCenterService corporateTool = GcOrgPublicTool.getInstance((String)this.corporateTable, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)coryp);
        YearPeriodDO manyp = GcPeriodAssistUtil.getPeriodObject(formSchemeKeyManage, periodStr);
        GcOrgCenterService managerTool = GcOrgPublicTool.getInstance((String)this.managerTable, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)manyp);
        Map<String, UploadStateNew> uploadStateEntitiesPersonMap = this.getUploadStateEntity(formSchemeKeyPerson, periodStr, corporateTool);
        Map<String, UploadStateNew> uploadStateEntitiesManageMap = this.getUploadStateEntity(formSchemeKeyManage, periodStr, managerTool);
        for (String orgIdPerson : uploadStateEntitiesPersonMap.keySet()) {
            String uploadStateManage;
            MasterEntityImpl masterEntityManage;
            UploadStateNew uploadStateEntityPerson = uploadStateEntitiesPersonMap.get(orgIdPerson);
            String uploadStatePerson = uploadStateEntityPerson.getActionStateBean().getCode();
            GcOrgCacheVO gcOrgCacheVOPerson = corporateTool.getOrgByCode(orgIdPerson);
            UploadStateNew uploadStateEntityManage = uploadStateEntitiesManageMap.get(orgIdPerson);
            BusinessKeyImpl businessKeyPerson = new BusinessKeyImpl();
            BusinessKeyImpl businessKeyManage = new BusinessKeyImpl();
            if (uploadStateEntityManage == null) {
                GcOrgCacheVO gcOrgCacheVOManage = managerTool.getOrgByCode(orgIdPerson);
                if (gcOrgCacheVOManage == null || gcOrgCacheVOManage.getOrgKind() == null || !GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVOManage.getOrgKind()) || !GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVOManage.getOrgKind()) || !"0".equals(gcOrgCacheVOManage.getBblx())) continue;
                masterEntityManage = new MasterEntityImpl();
                masterEntityManage.setMasterEntityDimessionValue(this.managerTable, orgIdPerson);
                DimensionValueSet masterKeys = uploadStateEntityPerson.getEntities();
                Collection masterEntityTableNames = masterEntityManage.getDimessionNames();
                for (String tableName : masterEntityTableNames) {
                    DimensionSet dimensionSet = dataAssist.getDimensionsByTableName(tableName);
                    if (dimensionSet == null || dimensionSet.size() <= 0) continue;
                    masterKeys.setValue(dimensionSet.get(0), (Object)masterEntityManage.getMasterEntityKey(tableName));
                }
                uploadStateEntityManage = new UploadStateNew();
                uploadStateEntityManage.setEntities(masterKeys);
            }
            String string = uploadStateManage = uploadStateEntityManage.getActionStateBean() == null ? null : uploadStateEntityManage.getActionStateBean().getCode();
            if (!uploadStatePerson.equals(uploadStateManage)) {
                logger.info("    " + gcOrgCacheVOPerson.getCode() + "     " + uploadStatePerson + " " + uploadStateManage);
                masterEntityManage = new MasterEntityImpl();
                masterEntityManage.setMasterEntityDimessionValue(this.managerTable, orgIdPerson);
                businessKeyManage.setPeriod(periodStr);
                businessKeyManage.setFormSchemeKey(formSchemeKeyManage);
                businessKeyManage.setMasterEntity((MasterEntityInfo)masterEntityManage);
                businessKeyPerson.setPeriod(periodStr);
                businessKeyPerson.setFormSchemeKey(formSchemeKeyPerson);
                MasterEntityImpl masterEntityPerson = new MasterEntityImpl();
                masterEntityPerson.setMasterEntityDimessionValue(this.corporateTable, gcOrgCacheVOPerson.getId());
                businessKeyPerson.setMasterEntity((MasterEntityInfo)masterEntityPerson);
                this.deleteUpload((BusinessKey)businessKeyManage);
                if (businessKeyPerson.getMasterEntity() != null && businessKeyPerson.getPeriod() != null) {
                    MasterEntity masterEntity = businessKeyPerson.getMasterEntity();
                    DimensionValueSet masterKeys = new DimensionValueSet();
                    Collection masterEntityTableNames = masterEntity.getDimessionNames();
                    for (String tableName : masterEntityTableNames) {
                        DimensionSet dimensionSet = dataAssist.getDimensionsByTableName(tableName);
                        if (dimensionSet == null || dimensionSet.size() <= 0) continue;
                        masterKeys.setValue(dimensionSet.get(0), (Object)masterEntity.getMasterEntityKey(tableName));
                    }
                    masterKeys.setValue("DATATIME", (Object)businessKeyPerson.getPeriod());
                    FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(businessKeyPerson.getFormSchemeKey());
                    List uploadRecordList = this.iBatchQueryUploadStateService.queryUploadActionsNew(masterKeys, null, formSchemeDefine);
                    if (uploadRecordList.size() > 0) {
                        for (UploadRecordNew uploadRecord : uploadRecordList) {
                            UploadRecordNew uploadRecordNew = new UploadRecordNew();
                            uploadRecordNew.setAction(uploadRecord.getAction());
                            uploadRecordNew.setTaskId(orgIdPerson);
                            uploadRecordNew.setCmt(uploadRecord.getCmt());
                            uploadRecordNew.setFormKey(uploadRecord.getFormKey());
                            uploadRecordNew.setOperationid(uploadRecord.getOperationid());
                            uploadRecordNew.setTime(uploadRecord.getTime());
                            uploadRecordNew.setOperator(uploadRecord.getOperator());
                            this.iBatchQueryUploadStateService.insertUploadRecord((BusinessKey)businessKeyManage, uploadRecordNew);
                        }
                    }
                }
                this.iBatchQueryUploadStateService.insertUploadState((BusinessKey)businessKeyManage, uploadStateEntityPerson);
            }
            uploadStateEntitiesManageMap.remove(orgIdPerson);
        }
        for (String orgIdManage : uploadStateEntitiesManageMap.keySet()) {
            UploadStateNew uploadStateEntityManage;
            GcOrgCacheVO gcOrgCacheVOPerson = corporateTool.getOrgByCode(orgIdManage);
            if (gcOrgCacheVOPerson != null && gcOrgCacheVOPerson.getOrgKind() != null && GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVOPerson.getOrgKind()) && GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVOPerson.getOrgKind()) && "0".equals(gcOrgCacheVOPerson.getBblx()) || (uploadStateEntityManage = uploadStateEntitiesManageMap.get(orgIdManage)) == null) continue;
            MasterEntityImpl masterEntityManage = new MasterEntityImpl();
            masterEntityManage.setMasterEntityDimessionValue(this.managerTable, orgIdManage);
            BusinessKeyImpl businessKeyManage = new BusinessKeyImpl();
            businessKeyManage.setPeriod(periodStr);
            businessKeyManage.setFormSchemeKey(formSchemeKeyManage);
            businessKeyManage.setMasterEntity((MasterEntityInfo)masterEntityManage);
            this.deleteUpload((BusinessKey)businessKeyManage);
        }
    }

    private Map<String, BusinessKey> getBusinessKey(String formSchemeCode, String periodStr, GcOrgCenterService tool, String key) {
        HashMap<String, BusinessKey> businessKeyMap = new HashMap<String, BusinessKey>();
        String processDefinitionKey = DefaultProcessBuilder.generateDefaultProcessId((String)formSchemeCode);
        List processDefinitionPersons = this.processEngine.getDeployService().getProcessDefinitionByKey(processDefinitionKey);
        for (ProcessDefinition processDefinitionPerson : processDefinitionPersons) {
            String processDefinitionPersonId = processDefinitionPerson.getId();
            List processInstancePersons = this.runTimeService.queryInstanceByProcessDefinitionId(processDefinitionPersonId, -1, -1);
            for (ProcessInstance processInstancePerson : processInstancePersons) {
                BusinessKey businessKey = processInstancePerson.getBusinessKey();
                if (businessKey == null || !periodStr.equals(businessKey.getPeriod())) continue;
                MasterEntity masterEntity = businessKey.getMasterEntity();
                GcOrgCacheVO gcOrgCacheVO = null;
                gcOrgCacheVO = tool.getOrgByCode(masterEntity.getMasterEntityKey(key));
                if (gcOrgCacheVO == null || gcOrgCacheVO.getOrgKind().getTitle() == null || !GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVO.getOrgKind()) && !GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVO.getOrgKind()) || !"0".equals(gcOrgCacheVO.getBblx())) continue;
                String orgId = gcOrgCacheVO.getId();
                businessKeyMap.put(orgId, businessKey);
            }
        }
        return businessKeyMap;
    }

    private void startProcessByProcessDefinitionKey(String processDefinitionManageKey, String personTaskDefinitionKey, BusinessKey businessKeyManage, String userId) {
        ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
        if ("tsk_submit".equals(personTaskDefinitionKey)) {
            this.runTimeService.startProcessByProcessDefinitionKey(businessKeyManage.toString(), processDefinitionManageKey, userId);
        } else if ("tsk_upload".equals(personTaskDefinitionKey)) {
            this.runTimeService.startProcessByProcessDefinitionKey(businessKeyManage.toString(), processDefinitionManageKey, userId);
            List taskList = this.runTimeService.queryTaskByBusinessKey(businessKeyManage.toString());
            this.runTimeService.completeProcessTask(businessKeyManage, ((Task)taskList.get(0)).getId(), userId, "act_submit", "", (TaskContext)taskContext);
        } else if ("tsk_audit".equals(personTaskDefinitionKey)) {
            this.runTimeService.startProcessByProcessDefinitionKey(businessKeyManage.toString(), processDefinitionManageKey, userId);
            List taskList = this.runTimeService.queryTaskByBusinessKey(businessKeyManage.toString());
            this.runTimeService.completeProcessTask(businessKeyManage, ((Task)taskList.get(0)).getId(), userId, "act_submit", "", (TaskContext)taskContext);
            List taskListT = this.runTimeService.queryTaskByBusinessKey(businessKeyManage.toString());
            this.runTimeService.completeProcessTask(businessKeyManage, ((Task)taskListT.get(0)).getId(), userId, "act_upload", "", (TaskContext)taskContext);
        }
    }

    private Map<String, UploadStateNew> getUploadStateEntity(String formSchemeKey, String periodStr, GcOrgCenterService tool) {
        HashMap<String, UploadStateNew> map = new HashMap<String, UploadStateNew>();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)periodStr);
        List uploadStateNewList = this.iBatchQueryUploadStateService.queryUploadStateNew(formSchemeDefine, masterKeys, null);
        List entityList = this.jtableParamService.getEntityList(formSchemeKey);
        for (UploadStateNew uploadStateNew : uploadStateNewList) {
            DimensionValueSet dimensionValueSet = uploadStateNew.getEntities();
            String id = (String)dimensionValueSet.getValue(((EntityViewData)entityList.get(0)).getDimensionName());
            GcOrgCacheVO gcOrgCacheVO = null;
            gcOrgCacheVO = tool.getOrgByCode(id);
            if (gcOrgCacheVO == null || gcOrgCacheVO.getOrgKind().getTitle() == null || !GcOrgKindEnum.SINGLE.equals((Object)gcOrgCacheVO.getOrgKind()) && !GcOrgKindEnum.BASE.equals((Object)gcOrgCacheVO.getOrgKind()) || !"0".equals(gcOrgCacheVO.getBblx())) continue;
            map.put(gcOrgCacheVO.getId(), uploadStateNew);
        }
        return map;
    }

    private String getEntitieTable(String orgEntityId) {
        TableModelDefine tableDefine = this.entityMetaService.getTableModel(orgEntityId);
        String tableName = tableDefine.getName();
        if (StringUtils.isEmpty((String)tableName)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u8868\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return tableName;
    }

    private FormSchemeDefine getFormSchemeManage(String taskKey, String periodStr) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskKey);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u83b7\u53d6\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0ctaskKey:" + taskKey + "  ,periodStr:" + periodStr);
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        if (null == formScheme) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0cformSchemeKey:" + schemePeriodLinkDefine.getSchemeKey());
        }
        return formScheme;
    }
}

