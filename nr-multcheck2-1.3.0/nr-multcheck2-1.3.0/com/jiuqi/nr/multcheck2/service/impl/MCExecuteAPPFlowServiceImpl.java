/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.asynctask.MultcheckItemRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteAPPFlowService;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.service.impl.BaseMCExecuteService;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.CheckItemEnvParam;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCExecuteAPPFlowServiceImpl
extends BaseMCExecuteService
implements IMCExecuteAPPFlowService {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteAPPFlowServiceImpl.class);
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private IMCOrgService mcOrgService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public MCExecuteResult multiExecute(JobContext jobContext, AsyncTaskMonitor asyncTaskMonitor, MCRunVO vo) {
        MCExecuteResult result = new MCExecuteResult();
        Date beginDate = new Date();
        this.progressAndMessage(asyncTaskMonitor, 0.02, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u6267\u884c\u4e2d\uff0c\u8bf7\u7a0d\u7b49");
        if (!StringUtils.hasText(vo.getFormScheme())) {
            try {
                SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(vo.getPeriod(), vo.getTask());
                vo.setFormScheme(scheme.getSchemeKey());
            }
            catch (Exception e) {
                logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u65f6\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff1a\u4efb\u52a1=" + vo.getTask() + ",\u65f6\u671f=" + vo.getPeriod() + ",\u5f02\u5e38:" + e.getMessage(), e);
                result.setError(MultcheckSchemeError.RUN_NOFORMSCHEME);
                return result;
            }
        }
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            try {
                vo.setOrgCodes(this.mcOrgService.getOrgsByTaskPeriodOrg(vo.getTask(), vo.getPeriod(), vo.getOrg()));
            }
            catch (Exception e) {
                logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u65f6\u83b7\u53d6\u5355\u4f4d\u5f02\u5e38\uff1a\u4efb\u52a1=" + vo.getTask() + ",\u65f6\u671f=" + vo.getPeriod() + ",\u5f02\u5e38:" + e.getMessage(), e);
                result.setError(MultcheckSchemeError.RUN_UNIT_FLOW);
                return result;
            }
        }
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            logger.error("checkBefore-\u83b7\u53d6\u5355\u4f4d\u4e3a\u7a7a");
            result.setError(MultcheckSchemeError.RUN_UNIT);
            return result;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTask());
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List<String> dynamicFields = this.mcDimService.getDynamicFields(dataScheme.getKey());
        List<String> dynamicDimsForPage = this.mcDimService.getDynamicDimNamesForPage(dataScheme.getKey());
        List<DataDimension> otherDimsForReport = this.mcDimService.getOtherDimsForReport(dataScheme.getKey());
        try {
            ArrayList<MCSchemeDTO> schemeDTOS;
            ArrayList<MCLabel> unboundList;
            MCExecuteResult result0;
            MCExecuteResult result1 = this.checkCompleted(asyncTaskMonitor, result);
            if (result1 != null) {
                return result1;
            }
            if (taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO) {
                ArrayList<String> checkOrgCodes = new ArrayList<String>(vo.getOrgCodes());
                List<String> leafOrgCodes = this.mcOrgService.getLeafOrgsByTaskPeriodOrg(taskDefine.getKey(), vo.getPeriod(), vo.getOrg(), vo.getOrgCodes());
                if (CollectionUtils.isEmpty(leafOrgCodes)) {
                    String runId = this.buildGatherAutoNoOrgRecord(vo, taskDefine, dataScheme, dynamicFields, checkOrgCodes, beginDate, vo.getOrg());
                    result.setTask(vo.getTask());
                    result.setRunId(runId);
                    this.logByGather(vo, taskDefine, checkOrgCodes, asyncTaskMonitor, logger);
                    return result;
                }
                vo.setOrgCodes(leafOrgCodes);
            }
            if ((result0 = this.buildMCSchemeDTO(vo, unboundList = new ArrayList<MCLabel>(), schemeDTOS = new ArrayList<MCSchemeDTO>(), otherDimsForReport, logger)) != null) {
                return result0;
            }
            String runId = jobContext.getInstanceId();
            String traceid = LogTraceIDUtil.getLogTraceId();
            HashMap<String, String> allDimAfterFilterMap = new HashMap<String, String>();
            HashMap<String, IEntityDefine> entityDefineMap = new HashMap<String, IEntityDefine>();
            HashMap<String, MultcheckItem> asyncIdToItem = new HashMap<String, MultcheckItem>();
            for (MCSchemeDTO schemeDTO : schemeDTOS) {
                MCRunVO voScheme = new MCRunVO(vo);
                if (!CollectionUtils.isEmpty(otherDimsForReport)) {
                    for (DataDimension dim : otherDimsForReport) {
                        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                        String dimensionName = entityDefine.getDimensionName();
                        entityDefineMap.put(dimensionName, entityDefine);
                        DimensionValue dimension = new DimensionValue();
                        dimension.setName(dimensionName);
                        String dimValue = this.buildFilterDimValue(vo, allDimAfterFilterMap, entityDefine.getId(), dimensionName, schemeDTO.getReportDimVO().getSchemeDimSet().get(dimensionName));
                        dimension.setValue(dimValue);
                        voScheme.getDimSetMap().put(dimensionName, dimension);
                    }
                }
                schemeDTO.setVo(voScheme);
                MultcheckEnvContext context = this.buildContext(schemeDTO);
                List<MultcheckItem> items = this.schemeService.getItemList(schemeDTO.getKey());
                ArrayList<String> asyncIds = new ArrayList<String>();
                schemeDTO.setAsyncIds(asyncIds);
                for (MultcheckItem item : items) {
                    MultcheckItemRealTimeTaskExecutor subJob = new MultcheckItemRealTimeTaskExecutor();
                    subJob.setTitle(schemeDTO.getCode() + item.getTitle());
                    Map subParam = subJob.getParams();
                    CheckItemEnvParam param = new CheckItemEnvParam();
                    param.setBeforeReport(true);
                    param.setRunId(runId);
                    param.setEnvContext(context);
                    param.setCheckItem(item);
                    param.setTraceid(traceid);
                    if (!CollectionUtils.isEmpty(otherDimsForReport) && !CollectionUtils.isEmpty(schemeDTO.getReportDimVO().getItemsDimSet()) && schemeDTO.getReportDimVO().getItemsDimSet().containsKey(item.getKey())) {
                        logger.info("app\u4e0a\u62a5\u524d\u5ba1\u6838\u6a21\u5f0f\uff1a\u5ba1\u6838\u9879\u5355\u72ec\u914d\u7f6e\u4e0a\u62a5\u671f\u60c5\u666f\uff1a\uff1a" + item.getTitle() + "::" + item.getKey());
                        Map<String, String> dimValueMap = schemeDTO.getReportDimVO().getItemsDimSet().get(item.getKey());
                        HashMap<String, String> dimValueMap2 = new HashMap<String, String>();
                        for (String dimensionName : dimValueMap.keySet()) {
                            String entityId = entityDefineMap.containsKey(dimensionName) ? ((IEntityDefine)entityDefineMap.get(dimensionName)).getId() : dimensionName;
                            String dimValue = this.buildFilterDimValue(vo, allDimAfterFilterMap, entityId, dimensionName, dimValueMap.get(dimensionName));
                            dimValueMap2.put(dimensionName, dimValue);
                        }
                        param.setItemDimForReport(dimValueMap2);
                    }
                    subParam.put("NR_ARGS", SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
                    String asyncId = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
                    asyncIds.add(asyncId);
                    asyncIdToItem.put(asyncId, item);
                    logger.info("\u53d1\u5e03\u5b50\u4efb\u52a1$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + asyncId + "::=" + item.getTitle());
                }
            }
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5b50\u7ebf\u7a0b\u53d1\u5e03\u5b8c\u6210$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            MCExecuteResult result2 = this.processTask(asyncTaskMonitor, result, asyncIdToItem, logger, dynamicDimsForPage, schemeDTOS);
            if (result2 != null) {
                return result2;
            }
            boolean adjust = this.dataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
            boolean onlyAdjust = adjust && dynamicFields.size() == 1 && "ADJUST".equals(dynamicFields.get(0));
            ArrayList<MultcheckResItem> resItems = new ArrayList<MultcheckResItem>();
            ArrayList<MultcheckResScheme> resSchemes = new ArrayList<MultcheckResScheme>();
            int success = 0;
            int failed = 0;
            boolean first = true;
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5ba1\u6838\u9879\u6267\u884c\u5b8c\u6bd5\uff0c\u5f85\u7ec4\u7ec7\u5ba1\u6838\u7ed3\u679c$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for (MCSchemeDTO schemeDTO : schemeDTOS) {
                SchemeExecuteResult seResult = new SchemeExecuteResult();
                if (first) {
                    first = false;
                    seResult.setUnboundList(unboundList);
                }
                MultcheckResScheme resScheme = new MultcheckResScheme();
                resScheme.setKey(UUID.randomUUID().toString());
                resScheme.setRecordKey(runId);
                resScheme.setSchemeKey(schemeDTO.getKey());
                resScheme.setBegin(new Date());
                resScheme.setEnd(new Date());
                List<String> asyncIds = schemeDTO.getAsyncIds();
                for (String asyncId : asyncIds) {
                    MultcheckItem item = (MultcheckItem)asyncIdToItem.get(asyncId);
                    CheckItemResultDate itemResult = (CheckItemResultDate)this.asyncTaskManager.queryDetail(asyncId);
                    this.dealItemResult(itemResult, adjust, onlyAdjust, runId, seResult, item, resItems, resScheme);
                }
                List<String> orgs = schemeDTO.getOrgList();
                orgs.removeAll(seResult.getFailedMap().keySet());
                seResult.setSuccessList(orgs);
                seResult.setReportDim(schemeDTO.getReportDimVO());
                seResult.setOrgDims(schemeDTO.getOrgDims());
                seResult.setItemsOrgDims(schemeDTO.getItemsOrgDims());
                try {
                    resScheme.setOrgs(SerializeUtil.serializeToJson(seResult));
                }
                catch (Exception e) {
                    logger.error("2\u7efc\u5408\u5ba1\u6838\u6267\u884c\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
                }
                resSchemes.add(resScheme);
                success += seResult.getSuccessList().size();
                failed += seResult.getFailedMap().size();
            }
            this.saveResult(vo, taskDefine, dataScheme, resItems, resSchemes, runId, dynamicFields, success, failed, beginDate, logger, vo.getOrg());
            this.progressAndMessage(asyncTaskMonitor, 0.96, "\u5ba1\u6838\u7ed3\u679c\u7ec4\u7ec7\u5b8c\u6bd5");
            result.setTask(vo.getTask());
            result.setRunId(runId);
        }
        catch (JobExecutionException e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setError(MultcheckSchemeError.RUN_ASYNC);
        }
        catch (Exception e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setError(MultcheckSchemeError.RUN_ASYNC);
        }
        return result;
    }
}

