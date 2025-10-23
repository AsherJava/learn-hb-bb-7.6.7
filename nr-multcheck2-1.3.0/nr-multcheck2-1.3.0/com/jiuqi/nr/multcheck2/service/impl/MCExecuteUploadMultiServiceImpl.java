/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.Guid
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.asynctask.MultcheckItemRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadMultiService;
import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.service.impl.BaseMCExecuteService;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.CheckItemEnvParam;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import com.jiuqi.util.Guid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCExecuteUploadMultiServiceImpl
extends BaseMCExecuteService
implements IMCExecuteUploadMultiService {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteUploadMultiServiceImpl.class);
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMCOrgService mcOrgService;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public MCUploadResult uploadMultiExecute(IMCMonitor mcMonitor, MCRunVO vo) {
        DimensionValue dimensionValue;
        MCUploadResult result = new MCUploadResult();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTask());
        boolean adjust = this.dataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
        if (adjust && !CollectionUtils.isEmpty(vo.getDimSetMap()) && vo.getDimSetMap().containsKey("ADJUST") && (dimensionValue = vo.getDimSetMap().get("ADJUST")) != null && !"0".equals(dimensionValue.getValue())) {
            try {
                logger.info("\u8c03\u6574\u671f\u4e0b\u6267\u884c\u4e0a\u62a5\uff1a\uff1a\u5168\u90e8\u5ba1\u6838\u901a\u8fc7=" + dimensionValue.getValue());
                result.setSuccessList(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), vo.getOrgCodes()));
            }
            catch (Exception e) {
                String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
                logger.error(title + "\uff1a" + e.getMessage(), e);
                LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            }
            return result;
        }
        Date beginDate = new Date();
        vo.setSource(CheckSource.FLOW);
        DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
        newContext.setEntityId(vo.getOrg());
        DsContextHolder.setDsContext((DsContext)newContext);
        this.uploadProgressAndMessage(mcMonitor, 0.02, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u6267\u884c\u4e2d\uff0c\u8bf7\u7a0d\u7b49");
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            result.setErrorMsg(MultcheckSchemeError.RUN_UNIT.getMessage());
            logger.error("uploadSingleExecute-\u83b7\u53d6\u5355\u4f4d\u4e3a\u7a7a");
            return result;
        }
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByFSAndOrg(vo.getFormScheme(), vo.getOrg());
        if (CollectionUtils.isEmpty(schemes)) {
            result.setErrorMsg(MultcheckSchemeError.RUN_NOSCHEME.getMessage());
            return result;
        }
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List<DataDimension> otherDimsForReport = this.mcDimService.getOtherDimsForReport(dataScheme.getKey());
        List<String> dynamicFields = this.mcDimService.getDynamicFields(dataScheme.getKey());
        List<String> dynamicDimsForPage = this.mcDimService.getDynamicDimNamesForPage(dataScheme.getKey());
        try {
            ArrayList<MCSchemeDTO> schemeDTOS;
            ArrayList<MCLabel> unboundList;
            MCExecuteResult result0;
            ArrayList<String> successList = new ArrayList<String>();
            ArrayList<String> failedList = new ArrayList<String>();
            if (taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO) {
                ArrayList<String> checkOrgCodes = new ArrayList<String>(vo.getOrgCodes());
                List<String> leafOrgCodes = this.mcOrgService.getLeafOrgsByTaskPeriodOrg(taskDefine.getKey(), vo.getPeriod(), vo.getOrg(), vo.getOrgCodes());
                if (CollectionUtils.isEmpty(leafOrgCodes)) {
                    String runId = this.buildGatherAutoNoOrgRecord(vo, taskDefine, dataScheme, dynamicFields, checkOrgCodes, beginDate, vo.getOrg());
                    result.setSuccessList(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), checkOrgCodes));
                    result.setTask(vo.getTask());
                    result.setRunId(runId);
                    this.logByGather(vo, taskDefine, checkOrgCodes, null, logger);
                    this.uploadProgressAndMessage(mcMonitor, 0.96, "\u5ba1\u6838\u5355\u4f4d\u5747\u4e3a\u5408\u5e76\u8282\u70b9\uff0c\u5ba1\u6838\u5b8c\u6bd5\uff01");
                    return result;
                }
                checkOrgCodes.removeAll(leafOrgCodes);
                successList.addAll(checkOrgCodes);
                vo.setOrgCodes(leafOrgCodes);
            }
            if ((result0 = this.buildMCSchemeDTO(vo, unboundList = new ArrayList<MCLabel>(), schemeDTOS = new ArrayList<MCSchemeDTO>(), otherDimsForReport, logger)) != null) {
                result.setErrorMsg(result0.getErrorMsg());
                return result;
            }
            if (mcMonitor != null) {
                this.uploadProgressAndMessage(mcMonitor, 0.05, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u5df2\u5b8c\u6210\uff0c\u8bf7\u7a0d\u7b49");
                if (mcMonitor.isCancel()) {
                    result.setErrorMsg("\u5ba1\u6838\u9879\u672a\u6267\u884c");
                    return result;
                }
            }
            String runId = Guid.newGuid();
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
                        for (String string : dimValueMap.keySet()) {
                            String entityId = entityDefineMap.containsKey(string) ? ((IEntityDefine)entityDefineMap.get(string)).getId() : string;
                            String dimValue = this.buildFilterDimValue(vo, allDimAfterFilterMap, entityId, string, dimValueMap.get(string));
                            dimValueMap2.put(string, dimValue);
                        }
                        param.setItemDimForReport(dimValueMap2);
                    }
                    NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                    npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
                    npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MultcheckItemRealTimeTaskExecutor());
                    String asyncId = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
                    asyncIds.add(asyncId);
                    asyncIdToItem.put(asyncId, item);
                    logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + asyncId + "::=" + item.getTitle());
                }
            }
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5b50\u7ebf\u7a0b\u53d1\u5e03\u5b8c\u6210$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            MCExecuteResult result2 = this.uploadProcessTask(mcMonitor, new MCExecuteResult(), asyncIdToItem, logger, dynamicDimsForPage, schemeDTOS);
            if (result2 != null) {
                result.setErrorMsg(result2.getErrorMsg());
                return result;
            }
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
                resScheme.setBegin(new Date());
                resScheme.setEnd(new Date());
                List<String> asyncIds = schemeDTO.getAsyncIds();
                for (String asyncId : asyncIds) {
                    MultcheckItem item = (MultcheckItem)asyncIdToItem.get(asyncId);
                    CheckItemResultDate itemResult = (CheckItemResultDate)this.asyncTaskManager.queryDetail(asyncId);
                    this.dealItemResult(itemResult, adjust, onlyAdjust, runId, seResult, item, resItems, resScheme);
                }
                List<String> list = schemeDTO.getOrgList();
                list.removeAll(seResult.getFailedMap().keySet());
                seResult.setSuccessList(list);
                seResult.setReportDim(schemeDTO.getReportDimVO());
                seResult.setOrgDims(schemeDTO.getOrgDims());
                seResult.setItemsOrgDims(schemeDTO.getItemsOrgDims());
                resScheme.setKey(UUID.randomUUID().toString());
                resScheme.setRecordKey(runId);
                resScheme.setSchemeKey(schemeDTO.getKey());
                try {
                    resScheme.setOrgs(SerializeUtil.serializeToJson(seResult));
                }
                catch (Exception e) {
                    logger.error("2\u7efc\u5408\u5ba1\u6838\u6267\u884c\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
                }
                resSchemes.add(resScheme);
                success += seResult.getSuccessList().size();
                failed += seResult.getFailedMap().size();
                HashSet<String> failedSet = new HashSet<String>(seResult.getFailedMap().keySet());
                if (!CollectionUtils.isEmpty(seResult.getSuccessList())) {
                    successList.addAll(seResult.getSuccessList());
                }
                if (CollectionUtils.isEmpty(failedSet)) continue;
                failedList.addAll(failedSet);
            }
            this.saveResult(vo, taskDefine, dataScheme, resItems, resSchemes, runId, dynamicFields, success, failed, beginDate, logger, vo.getOrg());
            this.uploadProgressAndMessage(mcMonitor, 0.96, "\u5ba1\u6838\u7ed3\u679c\u7ec4\u7ec7\u5b8c\u6bd5");
            if (!CollectionUtils.isEmpty(successList)) {
                result.setSuccessList(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), successList));
            }
            if (!CollectionUtils.isEmpty(failedList)) {
                result.setFailedList(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), failedList));
            }
            if (!CollectionUtils.isEmpty(unboundList)) {
                result.getFailedList().addAll(unboundList);
            }
            result.setTask(vo.getTask());
            result.setRunId(runId);
        }
        catch (JobExecutionException e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setErrorMsg("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage());
        }
        catch (Exception e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setErrorMsg("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return result;
    }
}

