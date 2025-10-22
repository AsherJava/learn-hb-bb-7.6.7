/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.util.SerializeUtils
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreSchemeDO
 *  nr.midstore2.data.midstoreresult.bean.MidstoreResult
 *  nr.midstore2.data.midstoreresult.service.IBatchMidstoreResultService
 *  nr.midstore2.data.param.IReportMidstoreSchemeQueryService
 *  nr.midstore2.data.service.IReportMidstoreBatchExcuteService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.midstore2.batch.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.midstore2.batch.web.vo.MidstoreRunVO;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultBase;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultColumn;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnit;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnitInfo;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnitVO;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultPeriod;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreSchemeDO;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore2.data.midstoreresult.bean.MidstoreResult;
import nr.midstore2.data.midstoreresult.service.IBatchMidstoreResultService;
import nr.midstore2.data.param.IReportMidstoreSchemeQueryService;
import nr.midstore2.data.service.IReportMidstoreBatchExcuteService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RealTimeJob(group="ASYNCTASK_MIDSTORE_BATCH", groupTitle="\u6279\u91cf\u6267\u884c\u4e2d\u95f4\u5e93", isolate=true)
public class BatchMidstoreAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchMidstoreAsyncTaskExecutor.class);
    private static final String TASK_CANCEL_INFO = "task_cancel_info";
    private static final String TASK_ERROR_INFO = "task_error_info";
    PeriodEngineService periodEngineService;
    IReportMidstoreBatchExcuteService service;
    IReportMidstoreSchemeQueryService nrMidstoreService;
    IEntityDataService entityDataService;
    IRunTimeViewController runTimeViewController;
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    IEntityMetaService entityMetaService;
    IEntityViewRunTimeController entityViewRunTimeController;
    IBatchMidstoreResultService batchMidstoreResultService;

    public void execute(JobContext jobContext) {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_MIDSTORE_BATCH.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                MidstoreRunVO vo = (MidstoreRunVO)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                this.getBean();
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTaskKey());
                ArrayList<String> periods = new ArrayList<String>();
                HashSet<String> periodTmps = new HashSet<String>();
                String[] rangPeriodArr = vo.getPeriods().split("-");
                if (rangPeriodArr.length > 1) {
                    periods = this.periodEngineService.getPeriodAdapter().getPeriodCodeByDataRegion(taskDefine.getDateTime(), rangPeriodArr[0], rangPeriodArr[1]);
                    periodTmps.addAll(periods);
                    periodTmps.add(rangPeriodArr[0]);
                    periodTmps.add(rangPeriodArr[1]);
                    periods = new ArrayList(periodTmps);
                } else {
                    periods.add(rangPeriodArr[0]);
                }
                if (vo.getOrgCodes().size() == 1 && !StringUtils.hasText(vo.getOrgCodes().get(0))) {
                    vo.getOrgCodes().remove(0);
                }
                List<Object> results = new ArrayList<MidstoreResultObject>();
                Date beginTime = new Date();
                if ("0".equals(vo.getExchangeMode())) {
                    results = this.service.batchExcuteDataGets(vo.getMidstoreSchemeKeys(), vo.getOrgCodes(), periods, vo.getDimSetMap(), vo.isDeleteEmpty(), (AsyncTaskMonitor)asyncTaskMonitor);
                }
                if ("1".equals(vo.getExchangeMode())) {
                    results = this.service.batchExcuteDataPosts(vo.getMidstoreSchemeKeys(), vo.getOrgCodes(), periods, vo.getDimSetMap(), (AsyncTaskMonitor)asyncTaskMonitor);
                }
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(TASK_CANCEL_INFO, (Object)TASK_CANCEL_INFO);
                }
                ResultBase runResults = this.dealResults(results, vo, periods, beginTime);
                String resStr = new String(SerializeUtils.jsonSerializeToByte((Object)runResults), StandardCharsets.UTF_8);
                this.saveResult(vo, runResults, resStr, beginTime);
                asyncTaskMonitor.finish("\u6267\u884c\u5b8c\u6210", (Object)resStr);
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(TASK_ERROR_INFO, (Throwable)nrCommonException, nrCommonException.getMessage());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(TASK_ERROR_INFO, (Throwable)e, e.getMessage());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void getBean() {
        this.periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        this.service = (IReportMidstoreBatchExcuteService)BeanUtil.getBean(IReportMidstoreBatchExcuteService.class);
        this.nrMidstoreService = (IReportMidstoreSchemeQueryService)BeanUtil.getBean(IReportMidstoreSchemeQueryService.class);
        this.entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.batchMidstoreResultService = (IBatchMidstoreResultService)BeanUtil.getBean(IBatchMidstoreResultService.class);
    }

    private ResultBase dealResults(List<MidstoreResultObject> results, MidstoreRunVO vo, List<String> periods, Date beginTime) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTaskKey());
        List schemes = this.nrMidstoreService.getSchemesBySchemeKeys(vo.getMidstoreSchemeKeys());
        Map<String, MidstoreSchemeDTO> schemeMap = schemes.stream().collect(Collectors.toMap(e -> e.getKey(), e -> e));
        ResultBase base = new ResultBase();
        base.setExchangeMode("0".equals(vo.getExchangeMode()) ? "\u63d0\u53d6" : "\u63a8\u9001");
        base.setTask(taskDefine.getTitle());
        base.setSchemes(schemes.stream().map(MidstoreSchemeDO::getTitle).collect(Collectors.joining(" ")));
        base.setAllUnit(vo.getOrgCodes().size() < 1);
        base.setSingle(periods.size() <= 1);
        base.setDeleteEmpty(vo.isDeleteEmpty());
        HashMap<String, String> dimTitleMap = new HashMap<String, String>();
        for (String dimKey : vo.getDimSetMap().keySet()) {
            if (dimKey.equals("MD_ORG")) continue;
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dimKey);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
            DimensionValue dimensionValue = vo.getDimSetMap().get(dimKey);
            if (dimKey.equals("MD_CURRENCY") && "PROVIDER_BASECURRENCY".equals(dimensionValue.getValue())) {
                dimTitleMap.put(entityDefine.getTitle(), "\u672c\u4f4d\u5e01");
                continue;
            }
            if (entityViewDefine == null || entityDefine == null) continue;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(dimKey, Arrays.asList(dimensionValue.getValue().split(";")));
            IEntityQuery query = this.entityDataService.newEntityQuery();
            query.setEntityView(entityViewDefine);
            query.setMasterKeys(dimensionValueSet);
            IEntityTable entityTable = query.executeReader(null);
            dimTitleMap.put(entityDefine.getTitle(), entityTable.getAllRows().stream().map(IEntityItem::getTitle).collect(Collectors.joining(" ")));
        }
        base.setDimTitleMap(dimTitleMap);
        IPeriodProvider provider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        boolean all = CollectionUtils.isEmpty(vo.getOrgCodes());
        String lpTitle = provider.getPeriodTitle(periods.get(periods.size() - 1));
        HashMap<String, Map<String, String>> unitCodeTitle = new HashMap<String, Map<String, String>>();
        this.buildCodeTitle(vo, periods, provider, unitCodeTitle);
        HashMap<String, List<String>> ignoreUnitMap = new HashMap<String, List<String>>();
        HashMap<String, List<String>> periodFailScheme = new HashMap<String, List<String>>();
        HashMap successUnitMap = new HashMap();
        HashMap failUnitMap = new HashMap();
        StringBuffer loggerPeriod = new StringBuffer();
        for (String pCode : periods) {
            String pTitle = provider.getPeriodTitle(pCode);
            loggerPeriod.append(pTitle).append(" ");
            if (all) {
                ignoreUnitMap.put(pTitle, new ArrayList(((Map)unitCodeTitle.get(pTitle)).keySet()));
            } else {
                ignoreUnitMap.put(pTitle, new ArrayList<String>(vo.getOrgCodes()));
            }
            periodFailScheme.put(pTitle, new ArrayList());
            successUnitMap.put(pTitle, new HashMap());
            failUnitMap.put(pTitle, new HashMap());
        }
        for (MidstoreResultObject result : results) {
            MidstoreSchemeDTO scheme = schemeMap.get(result.getSchemeKey());
            if (scheme == null) continue;
            if (!result.isSuccess()) {
                base.addFailScheme(scheme.getCode() + " " + scheme.getTitle() + " \u5931\u8d25\u539f\u56e0\uff1a" + result.getMessage());
                continue;
            }
            for (MistoreWorkResultObject work : result.getWorkResults()) {
                String pTitle;
                String string = pTitle = StringUtils.hasText(work.getPeriodTitle()) ? work.getPeriodTitle() : provider.getPeriodTitle(work.getPeriodCode());
                if (!work.isSuccess()) {
                    ((List)periodFailScheme.get(pTitle)).add(scheme.getCode() + " " + scheme.getTitle() + " \u5931\u8d25\u539f\u56e0\uff1a" + work.getMessage());
                    continue;
                }
                for (String unitCode : work.getSuccessUnits().keySet()) {
                    this.buildIgnoreUnitMap(ignoreUnitMap, pTitle, unitCode);
                    MistoreWorkUnitInfo unitInfo = (MistoreWorkUnitInfo)work.getSuccessUnits().get(unitCode);
                    String unitTitle = (String)((Map)unitCodeTitle.get(all ? pTitle : lpTitle)).get(unitCode);
                    ((Map)successUnitMap.get(pTitle)).put(unitCode, new ResultColumn(unitCode, unitTitle));
                }
                for (MistoreWorkFailInfo failInfo : work.getFailInfoList()) {
                    String errorMsg = failInfo.getMessage();
                    for (String unitCode : failInfo.getUnitInfos().keySet()) {
                        this.buildIgnoreUnitMap(ignoreUnitMap, pTitle, unitCode);
                        MistoreWorkUnitInfo unitInfo = (MistoreWorkUnitInfo)failInfo.getUnitInfos().get(unitCode);
                        if (!((Map)failUnitMap.get(pTitle)).containsKey(unitCode)) {
                            String unitTitle = (String)((Map)unitCodeTitle.get(all ? pTitle : lpTitle)).get(unitCode);
                            ((Map)failUnitMap.get(pTitle)).put(unitCode, new ResultFailUnit(unitCode, unitTitle));
                        }
                        if ("\u5176\u4ed6".equals(errorMsg)) {
                            Map formInfos = unitInfo.getFormInfos();
                            for (String formKey : formInfos.keySet()) {
                                MistoreWorkFormInfo formInfo = (MistoreWorkFormInfo)formInfos.get(formKey);
                                ((ResultFailUnit)((Map)failUnitMap.get(pTitle)).get(unitCode)).add(new ResultFailUnitInfo(scheme.getTitle(), formKey, formInfo.getFormTitle(), formInfo.getMessage()));
                            }
                            continue;
                        }
                        ((ResultFailUnit)((Map)failUnitMap.get(pTitle)).get(unitCode)).add(new ResultFailUnitInfo(scheme.getTitle(), errorMsg));
                    }
                }
            }
        }
        StringBuffer loggerDetail = new StringBuffer();
        ArrayList<ResultPeriod> resultPeriodList = new ArrayList<ResultPeriod>();
        for (String pCode : periods) {
            String pTitle = provider.getPeriodTitle(pCode);
            ResultPeriod resultPeriod = new ResultPeriod();
            resultPeriod.setPeriod(pTitle);
            resultPeriod.setFailSchemes((List)periodFailScheme.get(pTitle));
            Map successUnits = (Map)successUnitMap.get(pTitle);
            Map failMap = (Map)failUnitMap.get(pTitle);
            if (!CollectionUtils.isEmpty(successUnits) && !CollectionUtils.isEmpty(failMap)) {
                for (String fUnitCode : failMap.keySet()) {
                    if (!successUnits.containsKey(fUnitCode)) continue;
                    successUnits.remove(fUnitCode);
                }
            }
            resultPeriod.setSuccessUnits(successUnits.values().stream().collect(Collectors.toList()));
            resultPeriod.setSuccessCount(successUnits.size());
            resultPeriod.setFailCount(failMap.size());
            ArrayList<ResultFailUnitVO> failUnits = new ArrayList<ResultFailUnitVO>();
            int index = 1;
            for (Map.Entry entry : ((Map)failUnitMap.get(pTitle)).entrySet()) {
                ResultFailUnit failUnit = (ResultFailUnit)entry.getValue();
                List<ResultFailUnitInfo> failDetails = failUnit.getFailDetails();
                Optional<ResultFailUnitInfo> first = failDetails.stream().filter(e -> "\u5355\u4f4d\u5339\u914d\u5931\u8d25".equals(e.getMessage())).findFirst();
                if (first.isPresent()) {
                    failDetails = new ArrayList<ResultFailUnitInfo>();
                    failDetails.add(first.get());
                }
                if (failDetails.size() == 1) {
                    ResultFailUnitInfo info = failDetails.get(0);
                    ResultFailUnitVO newFailUnit = new ResultFailUnitVO();
                    BeanUtils.copyProperties(info, newFailUnit);
                    newFailUnit.setCode(failUnit.getCode());
                    newFailUnit.setTitle(failUnit.getTitle());
                    newFailUnit.setIndex(String.valueOf(index));
                    newFailUnit.setUnitErrorCount(1);
                    failUnits.add(newFailUnit);
                } else {
                    ResultFailUnitVO newFailUnit = new ResultFailUnitVO();
                    BeanUtils.copyProperties(failUnit, newFailUnit);
                    newFailUnit.setIndex(String.valueOf(index));
                    newFailUnit.setUnitErrorCount(newFailUnit.getUnitErrorCount());
                    failUnits.add(newFailUnit);
                    int pos = 1;
                    for (ResultFailUnitInfo unitInfo : failDetails) {
                        unitInfo.setIndex(index + "-" + pos++);
                        resultPeriod.addFailUnitInfo(failUnit.getCode(), unitInfo);
                    }
                }
                ++index;
            }
            resultPeriod.setFailUnits(failUnits);
            resultPeriod.setIgnoreCount(((List)ignoreUnitMap.get(pTitle)).size());
            resultPeriod.setIgnoreUnits(this.getIgnoreUnits(all, lpTitle, unitCodeTitle, ignoreUnitMap, pTitle));
            resultPeriod.setSelectUnitsCount(resultPeriod.getSuccessCount() + resultPeriod.getFailCount() + resultPeriod.getIgnoreCount());
            this.buildLoggerDetail(periodFailScheme, loggerDetail, pTitle, resultPeriod);
            resultPeriodList.add(resultPeriod);
        }
        base.setResultPeriods(resultPeriodList);
        this.buildLogger(vo, base, all, loggerPeriod, loggerDetail);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        base.setBeginTime(sf.format(beginTime));
        base.setEndTime(sf.format(new Date()));
        return base;
    }

    private void saveResult(MidstoreRunVO vo, ResultBase runResults, String resStr, Date beginTime) throws Exception {
        MidstoreResult res = new MidstoreResult();
        List<ResultPeriod> periods = runResults.getResultPeriods();
        res.setKey(UUID.randomUUID().toString());
        res.setUser(NpContextHolder.getContext().getUserId());
        res.setTask(vo.getTaskKey());
        res.setMidstore(runResults.getSchemes());
        res.setExchangeMode(vo.getExchangeMode());
        res.setPeriod(runResults.isSingle() ? periods.get(0).getPeriod() : periods.get(0).getPeriod() + "-" + periods.get(periods.size() - 1).getPeriod());
        res.setUnit(vo.getOrgCodes().size());
        res.setExecuteTime(beginTime);
        res.setSource("BATCH");
        res.setDetail(resStr);
        this.batchMidstoreResultService.addResult(res);
    }

    private void buildLoggerDetail(Map<String, List<String>> periodFailScheme, StringBuffer loggerDetail, String pTitle, ResultPeriod resultPeriod) {
        loggerDetail.append("\u65f6\u671f\uff1a").append(pTitle).append("\n");
        if (periodFailScheme.get(pTitle).size() > 0) {
            loggerDetail.append("\u5931\u8d25\u65b9\u6848\uff1a").append(periodFailScheme.get(pTitle).stream().collect(Collectors.joining("\uff0c"))).append("\n");
        }
        loggerDetail.append("\u6210\u529f\u5355\u4f4d\uff1a").append(resultPeriod.getSuccessCount()).append("\u5bb6\uff0c");
        loggerDetail.append("\u5931\u8d25\u5355\u4f4d\uff1a").append(resultPeriod.getFailCount()).append("\u5bb6\uff0c");
        loggerDetail.append("\u672a\u6267\u884c\u5355\u4f4d\uff1a").append(resultPeriod.getIgnoreCount()).append("\u5bb6").append("\n");
    }

    private void buildLogger(MidstoreRunVO vo, ResultBase base, boolean all, StringBuffer loggerPeriod, StringBuffer loggerDetail) {
        StringBuffer sb = new StringBuffer("\u6279\u91cf\u6267\u884c\u4e2d\u95f4\u5e93");
        sb.append("0".equals(vo.getExchangeMode()) ? "\u53d6\u6570" : "\u63a8\u6570").append("\n");
        sb.append("\u4efb\u52a1\uff1a").append(base.getTask()).append("\n");
        sb.append("\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a").append(base.getSchemes()).append("\n");
        sb.append("\u65f6\u671f\uff1a").append(loggerPeriod).append("\n");
        sb.append("\u9009\u62e9\u5355\u4f4d\uff1a").append(all ? "\u5168\u90e8" : Integer.valueOf(vo.getOrgCodes().size())).append("\n");
        if (!CollectionUtils.isEmpty(base.getFailSchemes())) {
            sb.append(base.getFailSchemes().stream().collect(Collectors.joining("\n")));
        }
        sb.append(loggerDetail).append("\n");
        LogHelper.info((String)"\u6279\u91cf\u6267\u884c\u4e2d\u95f4\u5e93", (String)"\u6279\u91cf\u6267\u884c\u4e2d\u95f4\u5e93", (String)sb.toString());
    }

    @NotNull
    private List<ResultColumn> getIgnoreUnits(boolean all, String lpTitle, Map<String, Map<String, String>> unitCodeTitle, Map<String, List<String>> ignoreUnitMap, String pTitle) {
        List<String> ignoreUnits = ignoreUnitMap.get(pTitle);
        ArrayList<ResultColumn> list = new ArrayList<ResultColumn>();
        for (String unitCode : ignoreUnits) {
            list.add(new ResultColumn(unitCode, unitCodeTitle.get(all ? pTitle : lpTitle).get(unitCode)));
        }
        return list;
    }

    private void buildIgnoreUnitMap(Map<String, List<String>> ignoreUnitMap, String pTitle, String unitCode) {
        if (ignoreUnitMap.get(pTitle).contains(unitCode)) {
            ignoreUnitMap.get(pTitle).remove(unitCode);
        }
    }

    private void buildCodeTitle(MidstoreRunVO vo, List<String> periods, IPeriodProvider provider, Map<String, Map<String, String>> unitCodeTitle) throws Exception {
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            for (String pCode : periods) {
                String pTitle = provider.getPeriodTitle(pCode);
                IEntityTable entityTable = this.getEntityTable(vo.getTaskKey(), pCode);
                List rows = entityTable.getAllRows();
                unitCodeTitle.put(pTitle, rows.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, IEntityItem::getTitle)));
            }
        } else {
            IEntityTable entityTable = this.getEntityTable(vo.getTaskKey(), periods.get(periods.size() - 1));
            Map rowMap = entityTable.findByEntityKeys(new HashSet<String>(vo.getOrgCodes()));
            unitCodeTitle.put(provider.getPeriodTitle(periods.get(periods.size() - 1)), rowMap.values().stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, IEntityItem::getTitle)));
        }
    }

    private IEntityTable getEntityTable(String taskKey, String period) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        IEntityTable entityTable = query.executeReader((IContext)context);
        return entityTable;
    }
}

