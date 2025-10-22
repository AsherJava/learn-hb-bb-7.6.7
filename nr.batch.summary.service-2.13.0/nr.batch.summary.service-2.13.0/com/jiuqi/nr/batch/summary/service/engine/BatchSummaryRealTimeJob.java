/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.internal.service.UserServieImpl
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
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
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.internal.service.UserServieImpl;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.engine.BatchCalcProgressMonitor;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryDataEngine;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver;
import com.jiuqi.nr.batch.summary.service.engine.JobContextLogger;
import com.jiuqi.nr.batch.summary.service.engine.TempTableProvider;
import com.jiuqi.nr.batch.summary.service.enumeration.SumPeriodPloy;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactory;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactory;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RealTimeJob(group="BATCH_SUMMARY_REALTIME_JOB", groupTitle="\u6279\u91cf\u6c47\u603b\u5373\u65f6\u4efb\u52a1")
public class BatchSummaryRealTimeJob
extends AbstractRealTimeJob {
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";

    public void execute(JobContext jobContext) {
        BSSchemeService schemeService = (BSSchemeService)ApplicationContextRegister.getBean(BSSchemeService.class);
        TempTableProvider tempTableFactory = (TempTableProvider)ApplicationContextRegister.getBean(TempTableProvider.class);
        TargetDimProviderFactory targetDimFactory = (TargetDimProviderFactory)ApplicationContextRegister.getBean(TargetDimProviderFactory.class);
        TargetFromProviderFactory targetFromFactory = (TargetFromProviderFactory)ApplicationContextRegister.getBean(TargetFromProviderFactory.class);
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        ITableDBUtil tableDBUtil = (ITableDBUtil)ApplicationContextRegister.getBean(ITableDBUtil.class);
        NrdbHelper nrdbHelper = (NrdbHelper)ApplicationContextRegister.getBean(NrdbHelper.class);
        DataModelService dataModelService = (DataModelService)ApplicationContextRegister.getBean(DataModelService.class);
        INvwaDataAccessProvider dataAccessProvider = (INvwaDataAccessProvider)ApplicationContextRegister.getBean(INvwaDataAccessProvider.class);
        JobContextLogger logger = new JobContextLogger(jobContext);
        NpContextHolder.setContext((NpContext)this.getNpContext(this.getUserName()));
        Map params = this.getParams();
        String taskKey = (String)params.get("taskId");
        String summarySchemeKey = (String)params.get("summarySchemeKey");
        TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskKey);
        SummaryScheme summaryScheme = schemeService.findScheme(summarySchemeKey);
        TargetDimProvider targetDimProvider = targetDimFactory.getTargetDimProvider(summaryScheme);
        TargetFromProvider targetFromProvider = targetFromFactory.getTargetFromProvider(summaryScheme);
        BatchSummaryNrDBSaver nrDBSaver = new BatchSummaryNrDBSaver(logger, nrdbHelper, summaryScheme, tableDBUtil, dataModelService, dataAccessProvider);
        BatchSummaryDataEngine summaryDataEngine = new BatchSummaryDataEngine(summaryScheme, targetFromProvider, targetDimProvider, tableDBUtil, nrDBSaver, logger);
        List<String> periodRange = this.getPeriodRange(params, taskDefine);
        summaryDataEngine.execute(tempTableFactory, periodRange);
        schemeService.updateSumDataDate(summaryScheme.getKey(), new Date());
        logger.logInfo("\u6c47\u603b\u4efb\u52a1\u6267\u884c\u5b8c\u6bd5\uff01\uff01");
        BatchCalcProgressMonitor fmlMonitor = new BatchCalcProgressMonitor();
        fmlMonitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\uff1a\u6279\u91cf\u8fd0\u7b97");
        this.executeCalc(taskDefine, summaryScheme, targetDimProvider, targetFromProvider, periodRange, fmlMonitor);
    }

    private List<String> getPeriodRange(Map<String, String> params, TaskDefine taskDefine) {
        List<String> periods = new ArrayList<String>();
        String period = params.get("periods");
        String periodPloy = params.get("periodPloy");
        SumPeriodPloy ploy = SumPeriodPloy.translate(periodPloy);
        switch (ploy) {
            case CURRENT_PERIOD: {
                periods.add(period);
                break;
            }
            case RANGE_PERIOD: {
                periods = this.getRangePeriods(period, taskDefine);
                break;
            }
            case ALL_PERIOD: {
                periods = this.getAllPeriods(taskDefine);
                break;
            }
            case SELECT_PERIOD: {
                periods = BatchSummaryUtils.toJavaArray((String)period, String.class);
            }
        }
        return periods;
    }

    private List<String> getRangePeriods(String periodRange, TaskDefine taskDefine) {
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)ApplicationContextRegister.getBean(IPeriodEntityAdapter.class);
        ArrayList<String> rangePeriods = new ArrayList<String>();
        String startDate = periodRange.split("-")[0];
        String endDate = periodRange.split("-")[1];
        List periodListOfRegion = periodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startDate, endDate);
        List<String> allPeriods = this.getAllPeriods(taskDefine);
        for (String period : allPeriods) {
            if (!periodListOfRegion.contains(period)) continue;
            rangePeriods.add(period);
        }
        return rangePeriods;
    }

    private List<String> getAllPeriods(TaskDefine taskDefine) {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        ArrayList<String> allPeriods = new ArrayList<String>();
        try {
            List schemePeriodLinkDefineList = iRunTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
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
        context.setIp(BatchSummaryRealTimeJob.getClientIpAddress(RequestContextHolder.getRequestAttributes()));
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
        if (orgDO == null || orgDO.isEmpty()) {
            return nvwaContextOrg;
        }
        nvwaContextOrg.setCode(orgDO.getCode());
        nvwaContextOrg.setName(orgDO.getName());
        nvwaContextOrg.setId(orgDO.getId().toString());
        return nvwaContextOrg;
    }

    private static String getClientIpAddress(RequestAttributes requestAttr) {
        if (!(requestAttr instanceof ServletRequestAttributes)) {
            return DistributionManager.getInstance().getIp().replace("-", "@");
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

    private void executeCalc(TaskDefine taskDefine, SummaryScheme summaryScheme, TargetDimProvider targetDimProvider, TargetFromProvider targetFromProvider, List<String> periodRange, IFmlMonitor fmlMonitor) {
        ICalculateService calculateService = (ICalculateService)ApplicationContextRegister.getBean(ICalculateService.class);
        for (String period : periodRange) {
            FormulaSchemeDefine formulaScheme = this.getDefaultFormulaSchemeInFormScheme(taskDefine, period);
            if (formulaScheme == null) {
                fmlMonitor.progressAndMessage(0.0, "\u65f6\u671f\uff1a\u3010" + period + "\u3011\u4e0b\u6ca1\u6709\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848\uff0c\u6216\u8005\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848\u4e2d\u6ca1\u6709\u516c\u5f0f\uff0c\u8df3\u8fc7\u8fd0\u7b97");
                continue;
            }
            List<FormDefine> rangeForms = targetFromProvider.getRangeForms(period);
            if (rangeForms == null || rangeForms.isEmpty()) {
                fmlMonitor.progressAndMessage(0.0, "\u65f6\u671f\uff1a\u3010" + period + "\u3011\u4e0b\u6ca1\u6709\u53ef\u8fd0\u7b97\u7684\u8868\u5355\uff0c\u8df3\u8fc7\u8fd0\u7b97");
                continue;
            }
            List rangeFormKeys = rangeForms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            rangeFormKeys.add("00000000-0000-0000-0000-000000000000");
            Map<String, Object> variableMap = this.getVariableMap(summaryScheme);
            DimensionCollection dimensionCollection = this.getDimensionCollection(summaryScheme, taskDefine, targetDimProvider, period);
            CalculateParam calculateParam = new CalculateParam();
            calculateParam.setMode(Mode.FORM);
            calculateParam.setRangeKeys(new ArrayList());
            calculateParam.setVariableMap(variableMap);
            calculateParam.setDimensionCollection(dimensionCollection);
            calculateParam.setIgnoreItems(this.getIgnoreItems());
            calculateParam.setFormulaSchemeKey(formulaScheme.getKey());
            calculateParam.setRangeKeys(rangeFormKeys);
            calculateService.batchCalculate(calculateParam, fmlMonitor);
        }
        fmlMonitor.finish("\u8fd0\u7b97\u5b8c\u6210", (Object)"");
    }

    private FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(TaskDefine taskDefine, String period) {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)ApplicationContextRegister.getBean(IFormulaRunTimeController.class);
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
            if (schemePeriodLinkDefine != null) {
                FormSchemeDefine formScheme = iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                FormulaSchemeDefine defaultFormulaScheme = formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formScheme.getKey());
                if (defaultFormulaScheme != null) {
                    List allFormulasInScheme = formulaRunTimeController.getAllFormulasInScheme(defaultFormulaScheme.getKey());
                    return allFormulasInScheme != null && allFormulasInScheme.isEmpty() ? null : defaultFormulaScheme;
                }
                return null;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Map<String, Object> getVariableMap(SummaryScheme summaryScheme) {
        HashMap<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("batchGatherSchemeCode", summaryScheme.getKey());
        variableMap.put("dimType", summaryScheme.getTargetDim().getTargetDimType().value + "");
        variableMap.put("dimValue", summaryScheme.getTargetDim().getDimValue());
        return variableMap;
    }

    private DimensionCollection getDimensionCollection(SummaryScheme summaryScheme, TaskDefine taskDefine, TargetDimProvider targetDimProvider, String period) {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)ApplicationContextRegister.getBean(IRuntimeDataSchemeService.class);
        DimensionProviderFactory dimensionProviderFactory = (DimensionProviderFactory)ApplicationContextRegister.getBean(DimensionProviderFactory.class);
        EntityFrameExtendHelper entityFrameExtendHelper = (EntityFrameExtendHelper)ApplicationContextRegister.getBean(EntityFrameExtendHelper.class);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        List dataSchemeDimension = dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                builder.setDWValue(dimensionName, dimension.getDimKey(), targetDimProvider.getTargetDims(period).toArray());
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                builder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{period});
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
            if (entityFrameExtendHelper.isCorporate(taskDefine, dimension)) {
                CorporateEntityData corporateEntityData = entityFrameExtendHelper.getCorporateEntityData(summaryScheme, dimension);
                builder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{corporateEntityData.getEntityCode()});
                continue;
            }
            DimensionProviderData dimensionProviderData = new DimensionProviderData();
            dimensionProviderData.setDataSchemeKey(taskDefine.getDataScheme());
            VariableDimensionValueProvider dimensionProvider = dimensionProviderFactory.getDimensionProvider("PROVIDER_ALLNODE", dimensionProviderData);
            builder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
        }
        return builder.getCollection();
    }

    public String getDimensionName(String entityId) {
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)ApplicationContextRegister.getBean(IPeriodEntityAdapter.class);
        IEntityMetaService entityMetaService = (IEntityMetaService)ApplicationContextRegister.getBean(IEntityMetaService.class);
        if (periodEntityAdapter.isPeriodEntity(entityId)) {
            return periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        if (entityId.equals("ADJUST")) {
            return entityId;
        }
        return entityMetaService.queryEntity(entityId).getDimensionName();
    }

    private Set<String> getIgnoreItems() {
        HashSet<String> ignoreItems = new HashSet<String>();
        ignoreItems.add("ALL");
        return ignoreItems;
    }
}

