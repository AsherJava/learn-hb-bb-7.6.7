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
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.multcheck2.asynctask.MultcheckItemRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteAPPSchemeService;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.service.impl.BaseMCExecuteService;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.CheckItemEnvParam;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCExecuteAPPSchemeServiceImpl
extends BaseMCExecuteService
implements IMCExecuteAPPSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteAPPSchemeServiceImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCOrgService mcOrgService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public MCExecuteResult multiExecute(JobContext jobContext, AsyncTaskMonitor asyncTaskMonitor, MCRunVO vo) {
        MCExecuteResult result = new MCExecuteResult();
        Date beginDate = new Date();
        this.progressAndMessage(asyncTaskMonitor, 0.02, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u6267\u884c\u4e2d\uff0c\u8bf7\u7a0d\u7b49\uff01");
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(vo.getScheme());
        if (scheme == null) {
            result.setError(MultcheckSchemeError.RUN_NOSCHEME);
            return result;
        }
        if (CollectionUtils.isEmpty(vo.getItems())) {
            result.setError(MultcheckSchemeError.RUN_NOITEM);
            return result;
        }
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            try {
                vo.setOrgCodes(this.mcOrgService.getOrgsBySchemePeriod(scheme, vo.getPeriod()));
            }
            catch (Exception e) {
                logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u65f6\u83b7\u53d6\u5355\u4f4d\u5f02\u5e38\uff1a\u4efb\u52a1={},\u65f6\u671f={},\u5f02\u5e38:{}", vo.getTask(), vo.getPeriod(), e.getMessage(), e);
            }
        }
        if (CollectionUtils.isEmpty(vo.getOrgCodes())) {
            result.setError(MultcheckSchemeError.RUN_UNIT);
            return result;
        }
        MCExecuteResult result1 = this.checkCompleted(asyncTaskMonitor, result);
        if (result1 != null) {
            return result1;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTask());
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List<String> dynamicFields = this.mcDimService.getDynamicFields(dataScheme.getKey());
        List<String> dynamicDimsForPage = this.mcDimService.getDynamicDimNamesForPage(dataScheme.getKey());
        try {
            if (taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO) {
                ArrayList<String> checkOrgCodes = new ArrayList<String>(vo.getOrgCodes());
                List<String> leafOrgCodes = this.mcOrgService.getLeafOrgsByTaskPeriodOrg(taskDefine.getKey(), vo.getPeriod(), vo.getOrg(), vo.getOrgCodes());
                if (CollectionUtils.isEmpty(leafOrgCodes)) {
                    String runId = this.buildGatherAutoNoOrgRecord(vo, taskDefine, dataScheme, dynamicFields, checkOrgCodes, beginDate, vo.getScheme());
                    result.setTask(vo.getTask());
                    result.setRunId(runId);
                    this.logByGather(vo, taskDefine, checkOrgCodes, asyncTaskMonitor, logger);
                    return result;
                }
                vo.setOrgCodes(leafOrgCodes);
            }
            MCSchemeDTO schemeDTO = new MCSchemeDTO(scheme, vo.getOrgCodes());
            vo.setFormScheme(schemeDTO.getFormScheme());
            schemeDTO.setVo(vo);
            MultcheckEnvContext context = this.buildContext(schemeDTO);
            String runId = jobContext.getInstanceId();
            String traceid = LogTraceIDUtil.getLogTraceId();
            LinkedHashMap<String, MultcheckItem> asyncIdToItem = new LinkedHashMap<String, MultcheckItem>();
            for (MultcheckItem item : vo.getItems()) {
                MultcheckItemRealTimeTaskExecutor subJob = new MultcheckItemRealTimeTaskExecutor();
                subJob.setTitle(schemeDTO.getCode() + "@" + item.getTitle());
                Map subParam = subJob.getParams();
                CheckItemEnvParam param = new CheckItemEnvParam();
                param.setRunId(runId);
                param.setBeforeReport(false);
                param.setEnvContext(context);
                param.setCheckItem(item);
                param.setTraceid(traceid);
                subParam.put("NR_ARGS", SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
                String asyncId = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
                asyncIdToItem.put(asyncId, item);
                logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + asyncId + "::=" + item.getTitle());
            }
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5b50\u7ebf\u7a0b\u53d1\u5e03\u5b8c\u6210$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            MCExecuteResult result2 = this.processTask(asyncTaskMonitor, result, asyncIdToItem, logger, dynamicDimsForPage, Arrays.asList(schemeDTO));
            if (result2 != null) {
                return result2;
            }
            boolean adjust = this.dataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
            boolean onlyAdjust = adjust && dynamicFields.size() == 1 && "ADJUST".equals(dynamicFields.get(0));
            ArrayList<MultcheckResItem> resItems = new ArrayList<MultcheckResItem>();
            ArrayList<MultcheckResScheme> resSchemes = new ArrayList<MultcheckResScheme>();
            SchemeExecuteResult seResult = new SchemeExecuteResult();
            MultcheckResScheme resScheme = new MultcheckResScheme();
            resScheme.setBegin(new Date());
            resScheme.setEnd(new Date());
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5ba1\u6838\u9879\u6267\u884c\u5b8c\u6bd5\uff0c\u5f85\u7ec4\u7ec7\u5ba1\u6838\u7ed3\u679c$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for (String asyncId : asyncIdToItem.keySet()) {
                MultcheckItem item = (MultcheckItem)asyncIdToItem.get(asyncId);
                CheckItemResultDate itemResult = (CheckItemResultDate)this.asyncTaskManager.queryDetail(asyncId);
                this.dealItemResult(itemResult, adjust, onlyAdjust, runId, seResult, item, resItems, resScheme);
            }
            List<String> orgs = schemeDTO.getOrgList();
            orgs.removeAll(seResult.getFailedMap().keySet());
            seResult.setSuccessList(orgs);
            seResult.setOrgDims(schemeDTO.getOrgDims());
            resScheme.setKey(UUID.randomUUID().toString());
            resScheme.setRecordKey(runId);
            resScheme.setSchemeKey(scheme.getKey());
            try {
                resScheme.setOrgs(SerializeUtil.serializeToJson(seResult));
            }
            catch (Exception e) {
                logger.error("2\u7efc\u5408\u5ba1\u6838\u6267\u884c\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
            }
            resSchemes.add(resScheme);
            this.saveResult(vo, taskDefine, dataScheme, resItems, resSchemes, runId, dynamicFields, seResult.getSuccessList().size(), seResult.getFailedMap().size(), beginDate, logger, vo.getScheme());
            this.progressAndMessage(asyncTaskMonitor, 0.96, "\u5ba1\u6838\u7ed3\u679c\u7ec4\u7ec7\u5b8c\u6bd5");
            result.setTask(vo.getTask());
            result.setRunId(runId);
        }
        catch (JobExecutionException e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setError(MultcheckSchemeError.RUN_ASYNC);
            return result;
        }
        catch (Exception e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setError(MultcheckSchemeError.RUN_ORG_AUTO);
            return result;
        }
        return result;
    }
}

