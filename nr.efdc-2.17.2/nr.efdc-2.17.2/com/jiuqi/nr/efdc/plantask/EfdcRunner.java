/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$ClassifierContext
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$Path
 *  com.jiuqi.bi.core.jobs.extension.IJobListener
 *  com.jiuqi.bi.core.jobs.extension.JobListenerContext
 *  com.jiuqi.bi.core.jobs.extension.item.FolderItem
 *  com.jiuqi.bi.core.jobs.extension.item.JobItem
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.dataentry.bean.DataEntryInitParam
 *  com.jiuqi.nr.dataentry.bean.FormSchemeResult
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.efdc.plantask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.efdc.asynctask.BatchEfdcAsyncTaskExecutor;
import com.jiuqi.nr.efdc.bean.PlanTaskEntityParam;
import com.jiuqi.nr.efdc.bean.PlanTaskFormParam;
import com.jiuqi.nr.efdc.bean.PlanTaskParam;
import com.jiuqi.nr.efdc.param.TaskOrgData;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.pojo.EfdcReturnInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EfdcRunner
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "3ebd5ca5-58e0-4cf6-abf0-a97156223063";
    private static final String JOB_TITLE = "EFDC\u53d6\u6570";
    private static final String JOB_CONFIG_PAGE = "job-efdc";
    private static final String ALL = "all";
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runtimeView;
    @Autowired
    private IEntityViewRunTimeController runtimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new EfdcExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new EfdcRunnerJobClassifier();
    }

    public IJobListener getJobListener() {
        return new EfdcRunnerJobListener();
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    class EfdcRunnerJobListener
    implements IJobListener {
        EfdcRunnerJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class EfdcRunnerJobClassifier
    implements IJobClassifier {
        EfdcRunnerJobClassifier() {
        }

        public List<FolderItem> getFolders(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public List<JobItem> getItems(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public JobItem getJobItem(String s) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(FolderItem folderItem) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(JobItem jobItem) throws Exception {
            return null;
        }
    }

    class EfdcExecutor
    extends JobExecutor {
        private final Logger log = LoggerFactory.getLogger(EfdcExecutor.class);
        private StringBuffer logs = null;
        private JobContext jobContext;
        private ObjectMapper mapper = new ObjectMapper();

        EfdcExecutor() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void execute(JobContext jobContext) throws JobExecutionException {
            this.logs = new StringBuffer();
            String user = jobContext.getJob().getUser();
            NpContextImpl npContext = null;
            if (!org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)user)) {
                try {
                    npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
                }
            } else {
                jobContext.getDefaultLogger().error("\u672a\u6307\u5b9a\u6267\u884c\u7528\u6237");
                return;
            }
            this.jobContext = jobContext;
            try {
                this.excute();
            }
            finally {
                NpContextHolder.clearContext();
            }
        }

        public void excute() {
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u6267\u884cEFDC\u6279\u91cf\u53d6\u6570:\r\n");
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            PlanTaskParam params = null;
            try {
                params = (PlanTaskParam)this.mapper.readValue(runnerParameter, PlanTaskParam.class);
            }
            catch (JsonProcessingException e2) {
                this.jobContext.getDefaultLogger().error("\u89e3\u6790\u53c2\u6570\u5931\u8d25\uff0c\u7ec8\u6b62\u53d6\u6570\u3002", (Throwable)e2);
                return;
            }
            DsContextImpl dsContext = new DsContextImpl();
            dsContext.setEntityId(params.getUnitCorporate());
            if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)params.getUnitCorporate())) {
                List unitCorporate;
                TaskOrgLinkListStream taskOrgLinkListStream = EfdcRunner.this.iRunTimeViewController.listTaskOrgLinkStreamByTask(params.getTask());
                ArrayList<TaskOrgData> taskOrgDataList = new ArrayList<TaskOrgData>();
                IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
                for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
                    TaskOrgData taskOrgData = new TaskOrgData();
                    taskOrgData.setId(taskOrgLinkDefine.getEntity());
                    if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                        taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
                    } else {
                        taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
                    }
                    taskOrgDataList.add(taskOrgData);
                }
                PlanTaskParam finalParams1 = params;
                if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(finalParams1.getUnitCorporate())).collect(Collectors.toList())).size() > 0) {
                    this.jobContext.getDefaultLogger().info("\u6279\u91cf\u5bfc\u51fa\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                }
            }
            DsContextHolder.setDsContext((DsContext)dsContext);
            TaskDefine taskDefine = null;
            try {
                taskDefine = EfdcRunner.this.runtimeView.queryTaskDefine(params.getTask());
            }
            catch (Exception e3) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u4efb\u52a1\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                return;
            }
            FormSchemeDefine formSchemeDefine = null;
            try {
                formSchemeDefine = EfdcRunner.this.runtimeView.getFormScheme(params.getFormScheme());
            }
            catch (Exception e4) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                return;
            }
            if (taskDefine == null) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u4efb\u52a1\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                return;
            }
            if (formSchemeDefine == null) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                return;
            }
            this.logs.append("\u53d6\u6570\u4efb\u52a1\u4e3a\uff1a").append(taskDefine.getTitle()).append("\u3010").append(taskDefine.getKey()).append("\u3011\u3002\r\n").append("\u53d6\u6570\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(formSchemeDefine.getTitle()).append("\u3010").append(formSchemeDefine.getKey()).append("\u3011\u3002\r\n");
            DataEntryInitParam param = new DataEntryInitParam();
            param.setTaskKey(params.getTask());
            FuncExecResult dataEntryEnv = null;
            try {
                dataEntryEnv = EfdcRunner.this.iFuncExecuteService.getDataEntryEnv(param);
            }
            catch (Exception e5) {
                this.log.error(e5.getMessage(), e5);
            }
            PlanTaskParam finalParams = params;
            Optional<FormSchemeResult> formScheme = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(finalParams.getFormScheme())).findFirst();
            if (!formScheme.isPresent()) {
                this.jobContext.getDefaultLogger().error("\u53d6\u6570\u53c2\u6570\u4e2d\u7684\u62a5\u8868\u65b9\u6848" + params.getFormScheme() + "\u4e0d\u5b58\u5728\u3002\r\n");
                return;
            }
            FormSchemeResult formSchemeResult = formScheme.get();
            EfdcInfo info = new EfdcInfo();
            info.setTaskKey(params.getTask());
            info.setFormSchemeKey(params.getFormScheme());
            info.setContainsUnbVou(params.isCertificate());
            Map dimensionSet = formSchemeResult.getDimensionSet();
            DimensionValue periodDimension = (DimensionValue)dimensionSet.get(params.getPeriodDimension());
            Map<String, EntityViewData> keyToEntity = formSchemeResult.getScheme().getEntitys().stream().collect(Collectors.toMap(EntityViewData::getKey, e -> e, (key1, key2) -> key2));
            String entityViewKey = null;
            for (String key3 : keyToEntity.keySet()) {
                if (!keyToEntity.get(key3).getDimensionName().equals(periodDimension.getName())) continue;
                entityViewKey = keyToEntity.get(key3).getKey();
            }
            String period = null;
            try {
                period = this.getPeriod(params, formSchemeDefine, entityViewKey);
            }
            catch (Exception e6) {
                this.jobContext.getDefaultLogger().error("\u8bfb\u53d6\u53d6\u6570\u65f6\u671f\u9519\u8bef\u3002\r\n");
            }
            periodDimension.setValue(period);
            this.logs.append("\u53d6\u6570\u65f6\u671f\u4e3a\uff1a").append(period).append("\u3002\r\n");
            if (formSchemeResult.isOpenAdJustPeriod()) {
                DimensionValue dimensionValue = new DimensionValue();
                if (dimensionSet.containsKey("ADJUST")) {
                    dimensionValue = (DimensionValue)dimensionSet.get("ADJUST");
                } else {
                    dimensionValue.setName("ADJUST");
                    dimensionValue.setType(0);
                }
                if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)params.getAdjustDate())) {
                    dimensionValue.setValue(params.getAdjustDate());
                } else {
                    dimensionValue.setValue("0");
                }
                dimensionSet.put("ADJUST", dimensionValue);
            }
            params.getEntity().forEach((key, value) -> {
                if (!EfdcRunner.ALL.equals(value.getEntityType()) && value.getSelected() == null) {
                    ArrayList<String> selected = new ArrayList<String>();
                    selected.add(value.getEntityType());
                    value.setSelected(selected);
                }
            });
            this.buildDimensionSet(params.getEntity(), dimensionSet, keyToEntity);
            info.setDimensionSet(dimensionSet);
            this.buildFormInfo(info, params.getForms());
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setTaskKey(info.getContext().getTaskKey());
            npRealTimeTaskInfo.setFormSchemeKey(info.getContext().getFormSchemeKey());
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)info)));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchEfdcAsyncTaskExecutor());
            String taskId = EfdcRunner.this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            this.executeLogsQuery(taskId);
            this.jobContext.getDefaultLogger().info(this.logs.toString());
            this.jobContext.getDefaultLogger().info("EFDC\u6279\u91cf\u53d6\u6570\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
        }

        private void buildFormInfo(EfdcInfo info, List<PlanTaskFormParam> forms) {
            String form = "";
            String formTip = "\u5168\u8868";
            if (!CollectionUtils.isEmpty(forms)) {
                StringBuffer buffer = new StringBuffer();
                StringBuffer title = new StringBuffer();
                for (PlanTaskFormParam f : forms) {
                    buffer.append(f.getFormKey()).append(";");
                    FormDefine formDefine = EfdcRunner.this.runtimeView.queryFormById(f.getFormKey());
                    title.append(formDefine.getTitle()).append(",");
                }
                formTip = title.substring(0, title.length() - 1);
                form = buffer.substring(0, buffer.length() - 1);
            }
            this.logs.append("\u53d6\u6570\u62a5\u8868\u4e3a:").append(formTip).append("\u3002\r\n");
            info.setFormKey(form);
        }

        private void buildDimensionSet(Map<String, PlanTaskEntityParam> entitys, Map<String, DimensionValue> dimensionSet, Map<String, EntityViewData> keyToEntity) {
            entitys.forEach((key, value) -> {
                EntityViewData entityViewData = (EntityViewData)keyToEntity.get(key);
                DimensionValue dimensionValue = (DimensionValue)dimensionSet.get(entityViewData.getDimensionName());
                List<String> selected = value.getSelected();
                String entityValue = "";
                String tip = "\u5168\u91cf";
                EntityViewDefine viewDefine = EfdcRunner.this.iEntityViewRunTimeController.buildEntityView(entityViewData.getKey(), entityViewData.getRowFilter());
                IEntityQuery iEntityQuery = EfdcRunner.this.entityDataService.newEntityQuery();
                iEntityQuery.setEntityView(viewDefine);
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(entityViewData.getDimensionName(), selected);
                iEntityQuery.setMasterKeys(dimensionValueSet);
                List allRows = new ArrayList();
                try {
                    IEntityTable iEntityTable = iEntityQuery.executeReader(null);
                    allRows = iEntityTable.getAllRows();
                }
                catch (Exception e) {
                    this.log.error(e.getMessage(), e);
                }
                Map<String, String> entityMap = allRows.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, IEntityItem::getTitle, (i1, i2) -> i2));
                if (!EfdcRunner.ALL.equals(value.getEntityType())) {
                    StringBuffer buffer = new StringBuffer();
                    StringBuffer titleBuffer = new StringBuffer();
                    for (String entity : selected) {
                        buffer.append(entity).append(";");
                        String title = entityMap.get(entity);
                        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)title)) {
                            title = entity;
                        }
                        titleBuffer.append(title).append(",");
                    }
                    entityValue = buffer.substring(0, buffer.length() - 1);
                    tip = titleBuffer.substring(0, titleBuffer.length() - 1);
                }
                this.logs.append("\u53d6\u6570\u7ef4\u5ea6").append(entityViewData.getTitle()).append("\u3010").append(entityViewData.getDimensionName()).append("\u3011\u7684\u503c\u4e3a\uff1a").append(tip).append("\u3002\r\n");
                dimensionValue.setValue(entityValue);
            });
        }

        private boolean executeLogsQuery(String taskId) {
            Object obj;
            boolean executeResult = true;
            boolean flag = false;
            while (!flag) {
                TaskState taskState = EfdcRunner.this.asyncTaskManager.queryTaskState(taskId);
                if (TaskState.FINISHED.equals((Object)taskState) || TaskState.ERROR.equals((Object)taskState)) {
                    flag = true;
                }
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException e) {
                    this.log.error(e.getMessage(), e);
                    break;
                }
            }
            if (null != (obj = EfdcRunner.this.asyncTaskManager.queryDetail(taskId)) && !"".equals(obj)) {
                HashMap EfdcReturnInfo2 = null;
                try {
                    EfdcReturnInfo2 = (HashMap)this.mapper.readValue(obj.toString(), (TypeReference)new TypeReference<HashMap<String, EfdcReturnInfo>>(){});
                }
                catch (JsonProcessingException e) {
                    this.jobContext.getDefaultLogger().error("\u89e3\u6790\u53c2\u6570\u5931\u8d25\uff0c\u7ec8\u6b62\u53d6\u6570\u3002", (Throwable)e);
                    return false;
                }
                EfdcReturnInfo2.forEach((key, info) -> {
                    StringBuffer sbs = new StringBuffer("\u5355\u4f4d\u3010" + info.getEntity().getTitle() + "\u3011\u4e2d\u7684\u95ee\u9898:\r\n");
                    Map<String, String> formMessage = info.getFormMessage();
                    formMessage.forEach((form, msg) -> {
                        sbs.append((String)form).append(":").append((String)msg).append("\r\n");
                        this.jobContext.getDefaultLogger().info(sbs.toString());
                    });
                });
            }
            return executeResult;
        }

        private String getPeriod(PlanTaskParam params, FormSchemeDefine formSchemeDefine, String entityViewKey) {
            int customPeriod = 2;
            String periodValue = null;
            int offset = 0;
            if (params.getPeriodConfig() == customPeriod) {
                return params.getPeriod();
            }
            offset = params.getPeriodConfig();
            if (params.getPeriodType() == 8) {
                IPeriodProvider periodProvider = EfdcRunner.this.periodEntityAdapter.getPeriodProvider(entityViewKey);
                List periodItems = periodProvider.getPeriodItems();
                Date nowDate = new Date();
                for (int i = 0; i < periodItems.size(); ++i) {
                    if (!nowDate.after(((IPeriodRow)periodItems.get(i)).getStartDate()) || !nowDate.before(((IPeriodRow)periodItems.get(i)).getEndDate())) continue;
                    if (offset + i < 0 || offset + i > periodItems.size() - 1) {
                        StringBuffer buffer = new StringBuffer("\u81ea\u5b9a\u4e49\u65f6\u671f\u504f\u79fb\u91cf\u8bbe\u7f6e\u9519\u8bef\uff1a").append("\u5f53\u524d\u671f\u7684\u5f00\u59cb\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getStartDate()).append("\u7ed3\u675f\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getEndDate()).append(",").append("\u53d6\u6570\u671f'").append(offset == -1 ? "\u4e0a\u4e00\u671f'," : "\u4e0b\u4e00\u671f',").append(offset == -1 ? "\u5c0f\u4e8e\u6700\u5c0f\u81ea\u5b9a\u4e49\u671f" : "\u8d85\u51fa\u6700\u5927\u81ea\u5b9a\u671f");
                        throw new IllegalArgumentException(buffer.toString());
                    }
                    periodValue = ((IPeriodRow)periodItems.get(offset + i)).getCode();
                    break;
                }
            } else {
                PeriodWrapper periodWrapper = this.getCurrPeriod(formSchemeDefine);
                periodValue = PeriodUtil.currentPeriod((GregorianCalendar)PeriodUtil.getCurrentCalendar(), (int)periodWrapper.getType(), (int)offset).toString();
            }
            return periodValue;
        }

        private PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
            TaskDefine taskDefine = null;
            try {
                taskDefine = EfdcRunner.this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
            }
            catch (Exception e) {
                this.log.error(e.getMessage(), e);
            }
            PeriodType periodType = taskDefine.getPeriodType();
            int periodOffset = taskDefine.getTaskPeriodOffset();
            String fromPeriod = taskDefine.getFromPeriod();
            String toPeriod = taskDefine.getToPeriod();
            if (null == fromPeriod || null == toPeriod) {
                char typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
                fromPeriod = "1970" + typeToCode + "0001";
                toPeriod = "9999" + typeToCode + "0001";
            }
            return this.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
        }

        private PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
            PeriodWrapper fromPeriodWrapper = null;
            PeriodWrapper toPeriodWrapper = null;
            try {
                fromPeriodWrapper = new PeriodWrapper(fromPeriod);
                toPeriodWrapper = new PeriodWrapper(toPeriod);
            }
            catch (Exception e) {
                this.log.error(e.getMessage(), e);
            }
            int fromYear = fromPeriodWrapper.getYear();
            int toYear = toPeriodWrapper.getYear();
            return PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
        }

        private User getUserByUserName(String userName) {
            if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = EfdcRunner.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = EfdcRunner.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }

        private NpContextImpl buildContext(String userName) throws JQException {
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            npContext.setTenant("__default_tenant__");
            NpContextUser contextUser = this.buildUserContext(userName);
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            return npContext;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setTitle(contextUser.getFullname());
            return identity;
        }

        private NpContextUser buildUserContext(String userName) throws JQException {
            NpContextUser userContext = new NpContextUser();
            User user = this.getUserByUserName(userName);
            if (user == null) {
                throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
            }
            userContext.setId(user.getId());
            userContext.setName(user.getName());
            userContext.setNickname(user.getNickname());
            userContext.setDescription(user.getDescription());
            return userContext;
        }
    }
}

