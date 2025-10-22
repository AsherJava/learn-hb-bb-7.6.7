/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.internal.service.UserServieImpl
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.batch.summary.common.StringLogger
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil
 *  com.jiuqi.nr.batch.summary.service.engine.JobContextLogger
 *  com.jiuqi.nr.batch.summary.service.engine.TempTableProvider
 *  com.jiuqi.nr.batch.summary.service.enumeration.SumPeriodPloy
 *  com.jiuqi.nr.batch.summary.service.targetdim.RangeOfAllEntities
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nr.batch.gather.gzw.service.engine;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.internal.service.UserServieImpl;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao;
import com.jiuqi.nr.batch.gather.gzw.service.engine.BatchGatherGZWDataEngine;
import com.jiuqi.nr.batch.gather.gzw.service.engine.BatchGatherGZWNrDBSaver;
import com.jiuqi.nr.batch.gather.gzw.service.executor.EntityExecutor;
import com.jiuqi.nr.batch.gather.gzw.service.provider.ConditionTargetDimGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.service.provider.TargetFormGZWProviderImpl;
import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.engine.JobContextLogger;
import com.jiuqi.nr.batch.summary.service.engine.TempTableProvider;
import com.jiuqi.nr.batch.summary.service.enumeration.SumPeriodPloy;
import com.jiuqi.nr.batch.summary.service.targetdim.RangeOfAllEntities;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RealTimeJob(group="BATCH_GATHER_GZW_REALTIME_JOB", groupTitle="\u56fd\u8d44\u59d4\u6279\u91cf\u6c47\u603b\u5373\u65f6\u4efb\u52a1")
public class BatchGatherGZWRealTimeJob
extends AbstractRealTimeJob {
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";
    private static final String TASK_ID_KEY = "taskId";

    public void execute(JobContext jobContext) {
        BSSchemeService schemeService = (BSSchemeService)ApplicationContextRegister.getBean(BSSchemeService.class);
        TempTableProvider tempTableFactory = (TempTableProvider)ApplicationContextRegister.getBean(TempTableProvider.class);
        TargetDimProviderFactoryImpl targetDimFactory = (TargetDimProviderFactoryImpl)ApplicationContextRegister.getBean(TargetDimProviderFactoryImpl.class);
        TargetFromProviderFactoryImpl targetFromFactory = (TargetFromProviderFactoryImpl)ApplicationContextRegister.getBean(TargetFromProviderFactoryImpl.class);
        ITableDBUtil tableDBUtil = (ITableDBUtil)ApplicationContextRegister.getBean(ITableDBUtil.class);
        CustomConditionHelper customConditionHelper = (CustomConditionHelper)ApplicationContextRegister.getBean(CustomConditionHelper.class);
        EntityExecutor entityExecutor = (EntityExecutor)ApplicationContextRegister.getBean(EntityExecutor.class);
        IGatherEntityCodeMappingDao gatherEntityCodeMappingDao = (IGatherEntityCodeMappingDao)ApplicationContextRegister.getBean(IGatherEntityCodeMappingDao.class);
        IDataBaseTableProvider iDataBaseTableProvider = (IDataBaseTableProvider)ApplicationContextRegister.getBean(IDataBaseTableProvider.class);
        NrdbHelper nrdbHelper = (NrdbHelper)ApplicationContextRegister.getBean(NrdbHelper.class);
        DataModelService dataModelService = (DataModelService)ApplicationContextRegister.getBean(DataModelService.class);
        INvwaDataAccessProvider dataAccessProvider = (INvwaDataAccessProvider)ApplicationContextRegister.getBean(INvwaDataAccessProvider.class);
        JobContextLogger logger = new JobContextLogger(jobContext);
        Map params = this.getParams();
        List<String> periodRange = this.getPeriodRange(params);
        String summarySchemeKey = (String)params.get("summarySchemeKey");
        SummaryScheme summaryScheme = schemeService.findScheme(summarySchemeKey);
        String entityId = summaryScheme.getEntityId();
        this.buildDsContext(entityId);
        entityExecutor.updateCustomizedConditionToEntity(summaryScheme, (String)params.get(TASK_ID_KEY), periodRange);
        ConditionTargetDimGZWProvider targetDimProvider = new ConditionTargetDimGZWProvider(summaryScheme, customConditionHelper.getTreeProvider(summaryScheme.getKey()), (TargetRangeUnitProvider)new RangeOfAllEntities(), targetDimFactory, gatherEntityCodeMappingDao);
        TargetFormGZWProviderImpl targetFromProvider = new TargetFormGZWProviderImpl(targetFromFactory, summaryScheme, iDataBaseTableProvider);
        BatchGatherGZWNrDBSaver nrDBSaver = new BatchGatherGZWNrDBSaver((StringLogger)logger, nrdbHelper, summaryScheme, tableDBUtil, dataModelService, dataAccessProvider, (TargetDimProvider)targetDimProvider);
        BatchGatherGZWDataEngine summaryDataEngine = new BatchGatherGZWDataEngine(summaryScheme, (TargetFromProvider)targetFromProvider, (TargetDimProvider)targetDimProvider, tableDBUtil, nrDBSaver, (StringLogger)logger);
        summaryDataEngine.execute(tempTableFactory, periodRange);
        schemeService.updateSumDataDate(summaryScheme.getKey(), new Date());
        logger.logInfo("\u6c47\u603b\u4efb\u52a1\u6267\u884c\u5b8c\u6bd5\uff01\uff01");
    }

    private void buildDsContext(String entityId) {
        if (StringUtils.isNotEmpty((String)entityId)) {
            DsContextImpl dsContext = new DsContextImpl();
            dsContext.setEntityId(entityId);
            DsContextHolder.setDsContext((DsContext)dsContext);
        }
    }

    private List<String> getPeriodRange(Map<String, String> params) {
        List<String> periods = new ArrayList<String>();
        String period = params.get("periods");
        String taskKey = params.get(TASK_ID_KEY);
        String periodPloy = params.get("periodPloy");
        SumPeriodPloy ploy = SumPeriodPloy.translate((String)periodPloy);
        switch (ploy) {
            case CURRENT_PERIOD: {
                periods.add(period);
                break;
            }
            case RANGE_PERIOD: {
                periods = this.getRangePeriods(period, taskKey);
                break;
            }
            case ALL_PERIOD: {
                periods = this.getAllPeriods(taskKey);
                break;
            }
            case SELECT_PERIOD: {
                periods = BatchSummaryUtils.toJavaArray((String)period, String.class);
            }
        }
        return periods;
    }

    private List<String> getRangePeriods(String periodRange, String taskKey) {
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)ApplicationContextRegister.getBean(IPeriodEntityAdapter.class);
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        ArrayList<String> rangePeriods = new ArrayList<String>();
        String startDate = periodRange.split("-")[0];
        String endDate = periodRange.split("-")[1];
        TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskKey);
        List periodListOfRegion = periodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startDate, endDate);
        List<String> allPeriods = this.getAllPeriods(taskKey);
        for (String period : allPeriods) {
            if (!periodListOfRegion.contains(period)) continue;
            rangePeriods.add(period);
        }
        return rangePeriods;
    }

    private List<String> getAllPeriods(String taskKey) {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        ArrayList<String> allPeriods = new ArrayList<String>();
        try {
            List schemePeriodLinkDefineList = iRunTimeViewController.querySchemePeriodLinkByTask(taskKey);
            for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                allPeriods.add(schemePeriodLinkDefine.getPeriodKey());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return allPeriods;
    }

    private NpContext getNpContext(String userName) {
        User user = this.getUserByUserName(userName);
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        context.setTenant("__default_tenant__");
        context.setIdentity(this.getContextIdentity(user));
        context.setUser(this.getContextUser(user));
        context.setIp(BatchGatherGZWRealTimeJob.getClientIpAddress(RequestContextHolder.getRequestAttributes()));
        context.setLocale(LocaleContextHolder.getLocale());
        context.setOrganization(this.getContextOrganization(user));
        return context;
    }

    private ContextIdentity getContextIdentity(User user) {
        NpContextIdentity idEntity = new NpContextIdentity();
        idEntity.setId(user.getId());
        idEntity.setTitle(user.getFullname());
        idEntity.setOrgCode(user.getOrgCode());
        return idEntity;
    }

    private ContextUser getContextUser(User user) {
        NpContextUser userContext = new NpContextUser();
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private ContextOrganization getContextOrganization(User user) {
        OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(user.getOrgCode());
        OrgDO orgDO = orgDataClient.get(orgDTO);
        NvwaContextOrg nvwaContextOrg = new NvwaContextOrg();
        nvwaContextOrg.setCode(orgDO.getCode());
        nvwaContextOrg.setName(orgDO.getName());
        nvwaContextOrg.setId(orgDO.getId().toString());
        return nvwaContextOrg;
    }

    private static String getClientIpAddress(RequestAttributes requestAttr) {
        if (!(requestAttr instanceof ServletRequestAttributes)) {
            InetAddress address = null;
            try {
                address = InetAddress.getLocalHost();
            }
            catch (UnknownHostException e) {
                return null;
            }
            return address.toString().replace("-", "@");
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttr;
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader(IP_HEADER_FORWARDED_FOR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            int index = requestHeader.indexOf(",");
            if (index != -1) {
                return requestHeader.substring(0, index);
            }
            return requestHeader;
        }
        requestHeader = request.getHeader(IP_HEADER_REMOTE_ADDR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            return requestHeader;
        }
        return request.getRemoteAddr();
    }

    private User getUserByUserName(String userName) {
        UserService userService = (UserService)ApplicationContextRegister.getBean(UserServieImpl.class);
        SystemUserService systemUserService = (SystemUserService)ApplicationContextRegister.getBean(SystemUserService.class);
        Optional user = userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }
}

