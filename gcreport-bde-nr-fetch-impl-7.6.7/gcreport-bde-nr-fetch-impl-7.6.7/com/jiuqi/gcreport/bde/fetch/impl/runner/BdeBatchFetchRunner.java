/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
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
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryInitParam
 *  com.jiuqi.nr.dataentry.bean.FormSchemeResult
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.efdc.bean.PlanTaskEntityParam
 *  com.jiuqi.nr.efdc.bean.PlanTaskFormParam
 *  com.jiuqi.nr.efdc.bean.PlanTaskParam
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.login.constant.LoginState
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetch.impl.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.bde.fetch.impl.config.FetchTimeConfig;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLog;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLogVO;
import com.jiuqi.gcreport.bde.fetch.impl.runner.BdeBatchFetchRunnerAfterExtension;
import com.jiuqi.gcreport.bde.fetch.impl.runner.BdePlanTaskParam;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
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
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.efdc.bean.PlanTaskEntityParam;
import com.jiuqi.nr.efdc.bean.PlanTaskFormParam;
import com.jiuqi.nr.efdc.bean.PlanTaskParam;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.login.constant.LoginState;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.stereotype.Component;

@PlanTaskRunner(id="8ACC08017047C3D7F035C18126CD5E0C", name="BdeBatchFetchRunner", title="BDE\u6279\u91cf\u53d6\u6570\u8ba1\u5212\u4efb\u52a1", group="BDE/\u6279\u91cf\u63d0\u53d6", settingPage="bdeBatchFetchConfig")
@Component
public class BdeBatchFetchRunner
extends Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeBatchFetchRunner.class);
    static final String ID = "8ACC08017047C3D7F035C18126CD5E0C";
    static final String NAME = "BdeBatchFetchRunner";
    static final String TITLE = "BDE\u6279\u91cf\u53d6\u6570\u8ba1\u5212\u4efb\u52a1";
    static final String SETTING_PAGE = "bdeBatchFetchConfig";
    private static final String ALL = "all";
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private IEntityViewRunTimeController runtimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private FetchTimeConfig fetchTimeConfig;
    @Autowired
    private GcFetchService fetchService;
    @Autowired(required=false)
    private BdeBatchFetchRunnerAfterExtension extension;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean excute(JobContext jobContext) {
        boolean bl;
        EfdcInfo info;
        AsyncTaskInfo taskInfo;
        boolean success;
        block71: {
            boolean fetchFlag;
            this.appendLog("\u53c2\u6570\u4fe1\u606f" + jobContext.getJob().getExtendedConfig());
            BdePlanTaskParam params = null;
            success = false;
            taskInfo = null;
            info = new EfdcInfo();
            try {
                String runnerParameter = jobContext.getJob().getExtendedConfig();
                params = (BdePlanTaskParam)((Object)JsonUtils.readValue((String)runnerParameter, BdePlanTaskParam.class));
            }
            catch (Exception e2) {
                this.appendLog("\u89e3\u6790\u53c2\u6570\u5931\u8d25\uff0c\u7ec8\u6b62\u53d6\u6570\u3002" + e2.getMessage());
                boolean bl2 = false;
                try {
                    if (this.extension != null) {
                        this.extension.afterExecute(jobContext, info, success, taskInfo);
                    }
                }
                catch (Exception e3) {
                    LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e3);
                    this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e3.getMessage());
                }
                NpContextHolder.clearContext();
                return bl2;
            }
            String userName = jobContext.getJob().getUser();
            if (StringUtils.isEmpty((String)userName)) {
                this.appendLog("\u672a\u914d\u7f6e\u6267\u884c\u4eba");
                boolean bl3 = false;
                return bl3;
            }
            Assert.isNotEmpty((String)params.getTask(), (String)"\u4efb\u52a1\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            NpContextImpl npContext = null;
            try {
                BdeBatchFetchRunner.initNpUser(userName);
                npContext = this.buildContext(userName);
                NpContextHolder.setContext((NpContext)npContext);
                if (!StringUtils.isEmpty((String)params.getContextEntityId()) && !"#".equals(params.getContextEntityId()) && FetchTaskUtil.taskEnableMultiOrg((String)params.getTask())) {
                    FetchTaskUtil.buildNrCtxEntityId((String)params.getContextEntityId());
                }
            }
            catch (JQException var9) {
                JQException e4 = var9;
                LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + userName + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e4.getMessage()));
            }
            TaskDefine taskDefine = null;
            try {
                taskDefine = this.runtimeView.queryTaskDefine(params.getTask());
            }
            catch (Exception e5) {
                this.appendLog("\u83b7\u53d6\u53d6\u6570\u4efb\u52a1\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                boolean bl4 = false;
                try {
                    if (this.extension != null) {
                        this.extension.afterExecute(jobContext, info, success, taskInfo);
                    }
                }
                catch (Exception e6) {
                    LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e6);
                    this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e6.getMessage());
                }
                NpContextHolder.clearContext();
                return bl4;
            }
            FormSchemeDefine formSchemeDefine = null;
            try {
                formSchemeDefine = this.runtimeView.getFormScheme(params.getFormScheme());
            }
            catch (Exception e7) {
                this.appendLog("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                boolean e6 = false;
                try {
                    if (this.extension != null) {
                        this.extension.afterExecute(jobContext, info, success, taskInfo);
                    }
                }
                catch (Exception e8) {
                    LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e8);
                    this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e8.getMessage());
                }
                NpContextHolder.clearContext();
                return e6;
            }
            if (taskDefine == null) {
                this.appendLog("\u83b7\u53d6\u53d6\u6570\u4efb\u52a1\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                boolean e7 = false;
                return e7;
            }
            if (formSchemeDefine == null) {
                this.appendLog("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                boolean e7 = false;
                return e7;
            }
            StringBuilder fetchLog = new StringBuilder();
            fetchLog.append("\u53d6\u6570\u4efb\u52a1\u4e3a\uff1a").append(taskDefine.getTitle()).append("\u3010").append(taskDefine.getKey()).append("\u3011\u3002\r\n").append("\u53d6\u6570\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(formSchemeDefine.getTitle()).append("\u3010").append(formSchemeDefine.getKey()).append("\u3011\u3002\r\n");
            this.appendLog(fetchLog.toString());
            DataEntryInitParam param = new DataEntryInitParam();
            param.setTaskKey(params.getTask());
            FuncExecResult dataEntryEnv = this.iFuncExecuteService.getDataEntryEnv(param);
            BdePlanTaskParam finalParams = params;
            Optional<FormSchemeResult> formScheme = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(finalParams.getFormScheme())).findFirst();
            if (!formScheme.isPresent()) {
                this.appendLog("\u53d6\u6570\u53c2\u6570\u4e2d\u7684\u62a5\u8868\u65b9\u6848" + params.getFormScheme() + "\u4e0d\u5b58\u5728\u3002");
                boolean bl5 = false;
                return bl5;
            }
            FormSchemeResult formSchemeResult = formScheme.get();
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
            catch (Exception e9) {
                this.appendLog("\u8bfb\u53d6\u53d6\u6570\u65f6\u671f\u9519\u8bef\u3002");
                boolean bl6 = false;
                try {
                    if (this.extension != null) {
                        this.extension.afterExecute(jobContext, info, success, taskInfo);
                    }
                }
                catch (Exception e10) {
                    LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e10);
                    this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e10.getMessage());
                }
                NpContextHolder.clearContext();
                return bl6;
            }
            periodDimension.setValue(period);
            this.appendLog(String.format("\u53d6\u6570\u65f6\u671f\u4e3a\uff1a%1$s\u3002\r\n", period));
            params.getEntity().forEach((key, value) -> {
                if (!ALL.equals(value.getEntityType()) && value.getSelected() == null) {
                    ArrayList<String> selected = new ArrayList<String>();
                    selected.add(value.getEntityType());
                    value.setSelected(selected);
                }
            });
            this.buildDimensionSet(params.getEntity(), dimensionSet, keyToEntity);
            info.setDimensionSet(dimensionSet);
            this.buildFormInfo(info, params.getForms());
            if (info.getVariableMap() == null) {
                info.setVariableMap(new HashMap());
            }
            if (TaskState.PROCESSING != (taskInfo = this.fetchService.batchFetchData(info)).getState() && TaskState.WAITING != taskInfo.getState()) {
                this.appendLog("\u6279\u91cf\u63d0\u53d6\u5931\u8d25:" + taskInfo.getResult());
                boolean e9 = false;
                try {
                    if (this.extension != null) {
                        this.extension.afterExecute(jobContext, info, success, taskInfo);
                    }
                }
                catch (Exception e11) {
                    LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e11);
                    this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e11.getMessage());
                }
                NpContextHolder.clearContext();
                return e9;
            }
            Assert.isNotEmpty((String)taskInfo.getId());
            success = fetchFlag = this.executeLogsQuery(taskInfo.getId());
            bl = fetchFlag;
            if (this.extension != null) {
                this.extension.afterExecute(jobContext, info, success, taskInfo);
            }
            break block71;
            catch (Exception e12) {
                LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e12);
                this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e12.getMessage());
            }
        }
        NpContextHolder.clearContext();
        return bl;
        catch (TaskExsitsException e13) {
            LOGGER.error("\u6279\u91cf\u63d0\u53d6\u5931\u8d25,\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002", e13);
            this.appendLog("\u6279\u91cf\u63d0\u53d6\u5931\u8d25,\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
            this.appendLog(e13.getMessage());
            boolean bl7 = false;
            return bl7;
        }
        catch (OutOfQueueException e14) {
            LOGGER.error("\u6279\u91cf\u63d0\u53d6\u5931\u8d25,\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002", e14);
            this.appendLog("\u6279\u91cf\u63d0\u53d6\u5931\u8d25\u5f53\u524d\u6267\u884c{}\u4eba\u6570\u8fc7\u591a\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01");
            this.appendLog(e14.getMessage());
            boolean bl8 = false;
            return bl8;
        }
        catch (Exception e15) {
            LOGGER.error("\u6279\u91cf\u63d0\u53d6\u5931\u8d25", e15);
            this.appendLog("\u6279\u91cf\u63d0\u53d6\u5931\u8d25" + e15.getMessage());
            this.appendLog(e15.getMessage());
            boolean bl9 = false;
            return bl9;
        }
        finally {
            try {
                if (this.extension != null) {
                    this.extension.afterExecute(jobContext, info, success, taskInfo);
                }
            }
            catch (Exception e16) {
                LOGGER.error("\u6267\u884c afterExecute \u6269\u5c55\u65b9\u6cd5\u65f6\u53d1\u751f\u5f02\u5e38", e16);
                this.appendLog("\u6267\u884c\u6269\u5c55\u903b\u8f91\u65f6\u53d1\u751f\u5f02\u5e38: " + e16.getMessage());
            }
            NpContextHolder.clearContext();
        }
    }

    public String getUserName() {
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user == null) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u83b7\u53d6\u5230\u7528\u6237\u4fe1\u606f\uff0c\u8bf7\u91cd\u65b0\u767b\u5f55\u540e\u91cd\u8bd5");
        }
        return StringUtils.isEmpty((String)user.getName()) ? user.getFullname() : user.getName();
    }

    public String getOrgType(EfdcInfo efdcInfo) {
        return FetchTaskUtil.getOrgTypeByTaskAndCtx((String)efdcInfo.getTaskKey());
    }

    private void buildFormInfo(EfdcInfo info, List<PlanTaskFormParam> forms) {
        String form = "";
        String formTip = "\u5168\u8868";
        if (!CollectionUtils.isEmpty(forms)) {
            StringBuffer buffer = new StringBuffer();
            StringBuffer title = new StringBuffer();
            for (PlanTaskFormParam f : forms) {
                buffer.append(f.getFormKey()).append(";");
                FormDefine formDefine = this.runtimeView.queryFormById(f.getFormKey());
                title.append(formDefine.getTitle()).append(",");
            }
            formTip = title.substring(0, title.length() - 1);
            form = buffer.substring(0, buffer.length() - 1);
        }
        this.appendLog(String.format("\u53d6\u6570\u62a5\u8868\u4e3a:%1$s\u3002\r\n", formTip));
        info.setFormKey(form);
    }

    private boolean executeLogsQuery(String taskId) {
        boolean flag = false;
        AsyncTaskQueryInfo queryInfo = new AsyncTaskQueryInfo();
        queryInfo.setAsynTaskID(taskId);
        AsyncTaskInfo taskState = null;
        while (!flag) {
            taskState = this.fetchService.queryBatchFetchTaskInfo(queryInfo);
            if (TaskState.FINISHED == taskState.getState() || TaskState.ERROR == taskState.getState()) {
                flag = true;
            }
            try {
                Thread.sleep(this.fetchTimeConfig.getPlanTaskFetchSleepTime());
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("\u6279\u91cf\u63d0\u53d6\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
                throw new BdeRuntimeException("\u6279\u91cf\u63d0\u53d6\u5931\u8d25");
            }
        }
        String resultLog = (String)taskState.getDetail();
        String string = resultLog = StringUtils.isEmpty((String)resultLog) ? "{}" : resultLog;
        if (TaskState.FINISHED == taskState.getState()) {
            this.appendLog(resultLog);
            return true;
        }
        BatchBdeFetchLogVO batchBdeFetchLogVO = new BatchBdeFetchLogVO();
        try {
            batchBdeFetchLogVO = (BatchBdeFetchLogVO)JsonUtils.readValue((String)resultLog, (TypeReference)new TypeReference<BatchBdeFetchLogVO>(){});
        }
        catch (Exception e) {
            this.appendLog("\u89e3\u6790\u53c2\u6570\u5931\u8d25\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
            this.appendLog(e.getMessage());
            return false;
        }
        if (!StringUtils.isEmpty((String)batchBdeFetchLogVO.getErrorLog())) {
            this.appendLog(batchBdeFetchLogVO.getErrorLog());
        } else {
            Map<String, List<BatchBdeFetchLog>> batchBdeFetchLogMap = batchBdeFetchLogVO.getBatchBdeFetchLogsMap();
            for (String unitName : batchBdeFetchLogMap.keySet()) {
                List<BatchBdeFetchLog> batchBdeFetchLogs = batchBdeFetchLogMap.get(unitName);
                for (BatchBdeFetchLog batchBdeFetchLog : batchBdeFetchLogs) {
                    this.appendLog(String.format(" \u5355\u4f4d\uff1a%1$s   \u5e01\u522b\uff1a%2$s \r\n \u5931\u8d25\u539f\u56e0\uff1a%3$s \r\n", unitName, batchBdeFetchLog.getCurrency(), batchBdeFetchLog.getLog()));
                }
            }
        }
        return false;
    }

    private String getPeriod(PlanTaskParam params, FormSchemeDefine formSchemeDefine, String entityViewKey) {
        int customPeriod = 2;
        String periodValue = null;
        int offset = 0;
        if (params.getPeriodConfig() == customPeriod) {
            Date period = DateUtils.parse((String)params.getPeriod()) == null ? DateUtils.parse((String)params.getPeriod(), (String)DateCommonFormatEnum.MONTH_DIGIT_BY_DASH.getFormat()) : DateUtils.parse((String)params.getPeriod());
            return PeriodUtils.getPeriodFromDate((int)params.getPeriodType(), (Date)period);
        }
        offset = params.getPeriodConfig();
        if (params.getPeriodType() == 8) {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityViewKey);
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

    private void buildDimensionSet(Map<String, PlanTaskEntityParam> entitys, Map<String, DimensionValue> dimensionSet, Map<String, EntityViewData> keyToEntity) {
        IEntityMetaService metaService = (IEntityMetaService)ApplicationContextRegister.getBean(IEntityMetaService.class);
        entitys.forEach((key, value) -> {
            EntityViewData entityViewData = (EntityViewData)keyToEntity.get(key);
            DimensionValue dimensionValue = null;
            if (entityViewData == null) {
                String dimensionName = metaService.getDimensionName(key);
                dimensionValue = (DimensionValue)dimensionSet.get(dimensionName);
                if (dimensionValue != null && !CollectionUtils.isEmpty((Collection)value.getSelected())) {
                    dimensionValue.setValue(String.join((CharSequence)";", value.getSelected()));
                }
                return;
            }
            dimensionValue = (DimensionValue)dimensionSet.get(entityViewData.getDimensionName());
            if (dimensionValue == null) {
                return;
            }
            List selected = value.getSelected();
            String entityValue = "";
            String tip = "\u5168\u91cf";
            EntityViewDefine viewDefine = this.runtimeController.buildEntityView(entityViewData.getKey(), entityViewData.getRowFilter());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(viewDefine);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(entityViewData.getDimensionName(), (Object)selected);
            iEntityQuery.setMasterKeys(dimensionValueSet);
            List allRows = new ArrayList();
            try {
                IEntityTable iEntityTable = iEntityQuery.executeReader(null);
                allRows = iEntityTable.getAllRows();
            }
            catch (Exception e) {
                throw new BdeRuntimeException((Throwable)e);
            }
            Map<String, String> entityMap = allRows.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, IEntityItem::getTitle, (i1, i2) -> i2));
            if (!ALL.equals(value.getEntityType())) {
                StringBuffer buffer = new StringBuffer();
                StringBuffer titleBuffer = new StringBuffer();
                for (String entity : selected) {
                    buffer.append(entity).append(";");
                    String title = entityMap.get(entity);
                    if (StringUtils.isEmpty((String)title)) {
                        title = entity;
                    }
                    titleBuffer.append(title).append(",");
                }
                entityValue = buffer.substring(0, buffer.length() - 1);
                tip = titleBuffer.substring(0, titleBuffer.length() - 1);
            }
            if (!"\u5408\u5e76\u8c03\u6574\u7c7b\u578b".equals(entityViewData.getTitle())) {
                this.appendLog(String.format("\u53d6\u6570\u7ef4\u5ea6%1$s\u3010%2$s\u3011\u7684\u503c\u4e3a\uff1a%3$s\u3002\r\n", entityViewData.getTitle(), entityViewData.getDimensionName(), tip));
            }
            dimensionValue.setValue(entityValue);
        });
    }

    private PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
        }
        catch (Exception e) {
            throw new BdeRuntimeException((Throwable)e);
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
            throw new BdeRuntimeException((Throwable)e);
        }
        int fromYear = fromPeriodWrapper.getYear();
        int toYear = toPeriodWrapper.getYear();
        return PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
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
        identity.setOrgCode(contextUser.getOrgCode());
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
        userContext.setNickname(user.getFullname());
        userContext.setDescription(user.getDescription());
        userContext.setOrgCode(user.getOrgCode());
        return userContext;
    }

    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((String)userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        return sysUser.isPresent() ? (User)sysUser.get() : null;
    }

    public static void initNpUser(String userName) {
        try {
            NvwaLoginUserDTO nvwaLoginUser = new NvwaLoginUserDTO();
            nvwaLoginUser.setCheckPwd(false);
            nvwaLoginUser.setUsername(userName);
            R tryLogin = ((NvwaLoginService)ApplicationContextRegister.getBean(NvwaLoginService.class)).tryLogin(nvwaLoginUser, true);
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237", (Object)tryLogin);
            if (!BdeBatchFetchRunner.loginSuccess(tryLogin.getCode())) {
                LOGGER.error("\u7528\u6237\u6a21\u62df\u767b\u9646\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)tryLogin.getMsg());
                NpContextUser user = new NpContextUser();
                user.setName(userName);
                user.setId("");
                NpContextImpl npContext = new NpContextImpl();
                npContext.setUser((ContextUser)user);
                NpContextHolder.setContext((NpContext)npContext);
                return;
            }
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u5b8c\u6210", (Object)JsonUtils.writeValueAsString((Object)nvwaLoginUser));
        }
        catch (Exception e) {
            LOGGER.error("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private static boolean loginSuccess(int code) {
        return code == LoginState.OK.getCode() || code == LoginState.SUCCESS.getCode() || code == LoginState.NEED_MODIFY.getCode() || code == LoginState.EXPIRED_NEED_MODIFY.getCode() || code == LoginState.EXPIRED_REMINDER.getCode();
    }
}

