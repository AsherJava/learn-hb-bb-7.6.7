/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.multcheck2.asynctask.MCRealTimeTaskMonitor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.collector.MultcheckCollector;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteEntrySingle;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.service.impl.BaseMCExecuteService;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCExecuteEntrySingleImpl
extends BaseMCExecuteService
implements IMCExecuteEntrySingle {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteEntrySingleImpl.class);
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private MultcheckCollector collector;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private IMCOrgService mcOrgService;

    @Override
    public MCExecuteResult singleExecute(JobContext jobContext, AsyncTaskMonitor asyncTaskMonitor, MCRunVO vo) {
        Date beginDate = new Date();
        MCExecuteResult result = new MCExecuteResult();
        this.progressAndMessage(asyncTaskMonitor, 0.02, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u6267\u884c\u4e2d\uff0c\u8bf7\u7a0d\u7b49\uff01");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTask());
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List<String> dynamicFields = this.mcDimService.getDynamicFields(dataScheme.getKey());
        if (!CollectionUtils.isEmpty(vo.getDimSetMap()) && !CollectionUtils.isEmpty(dynamicFields)) {
            HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
            for (String dim : vo.getDimSetMap().keySet()) {
                if (!dynamicFields.contains(dim)) continue;
                dimSetMap.put(dim, vo.getDimSetMap().get(dim));
            }
            vo.setDimSetMap(dimSetMap);
        }
        try {
            ArrayList<MCSchemeDTO> schemeDTOS;
            ArrayList<MCLabel> unboundList;
            MCExecuteResult result0;
            if (taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO) {
                ArrayList<String> checkOrgCodes = new ArrayList<String>(vo.getOrgCodes());
                List<String> leafOrgCodes = this.mcOrgService.getLeafOrgsByTaskPeriodOrg(taskDefine.getKey(), vo.getPeriod(), vo.getOrg(), vo.getOrgCodes());
                if (CollectionUtils.isEmpty(leafOrgCodes)) {
                    String runId = this.buildGatherAutoNoOrgRecord(vo, taskDefine, dataScheme, dynamicFields, checkOrgCodes, beginDate, vo.getOrg());
                    result.setTask(vo.getTask());
                    result.setRunId(runId);
                    result.setSingleOrgRes(true);
                    this.logByGather(vo, taskDefine, checkOrgCodes, asyncTaskMonitor, logger);
                    return result;
                }
                vo.setOrgCodes(leafOrgCodes);
            }
            if ((result0 = this.buildMCSchemeDTOForEntry(vo, unboundList = new ArrayList<MCLabel>(), schemeDTOS = new ArrayList<MCSchemeDTO>(), logger)) != null) {
                return result0;
            }
            MCExecuteResult result1 = this.checkCompleted(asyncTaskMonitor, result);
            if (result1 != null) {
                return result1;
            }
            String runId = jobContext.getInstanceId();
            MCSchemeDTO schemeDTO = (MCSchemeDTO)schemeDTOS.get(0);
            schemeDTO.setVo(vo);
            MultcheckEnvContext context = this.buildContext(schemeDTO);
            context.setDims(DimensionValueSetUtil.buildDimensionCollection(vo.getDimSetMap(), (String)vo.getFormScheme()));
            List<MultcheckItem> items = this.schemeService.getItemList(schemeDTO.getKey());
            int itemCount = items.size();
            double currTaskProgress = 0.1;
            double weight = 0.7 / (double)itemCount;
            int totalProgress = 0;
            LinkedHashMap<String, CheckItemResultDate> mcItemResultMap = new LinkedHashMap<String, CheckItemResultDate>();
            this.progressAndMessage(asyncTaskMonitor, currTaskProgress, "\u5f00\u59cb\u6267\u884c\u5ba1\u6838\u5171" + items.size() + "\u4e2a\u5ba1\u6838\u9879");
            MCRealTimeTaskMonitor mcRealTimeTaskMonitor = new MCRealTimeTaskMonitor(jobContext.getInstanceId(), "ASYNCTASK_DATAENTRY_MULTCHECK_SINGLE", null);
            for (MultcheckItem item : items) {
                CheckItemParam param = new CheckItemParam();
                param.setRunId(runId);
                param.setBeforeReport(false);
                param.setContext(context);
                param.setCheckItem(item);
                param.setAsyncTaskMonitor((AsyncTaskMonitor)mcRealTimeTaskMonitor);
                Date begin = new Date();
                this.progressAndMessage(asyncTaskMonitor, currTaskProgress, String.format("\u987b\u6267\u884c%s\u4e2a\u5ba1\u6838\u9879\uff0c\u6b63\u5728\u6267\u884c\u7b2c%s\u4e2a\u5ba1\u6838\u9879\u3010%s\u3011", itemCount, ++totalProgress, item.getTitle()));
                logger.info(String.format("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u7b2c%s/%s\u5ba1\u6838\u9879\u3010%s\u3011\u5f00\u59cb\u6267\u884c", totalProgress, itemCount, item.getTitle()));
                CheckItemResult itemResult = this.collector.getProvider(item.getType()).runCheck(param);
                if (itemResult == null) {
                    result.setError(MultcheckSchemeError.RUN_ITEM);
                    result.setErrorMsg("\u3010" + item.getTitle() + "\u3011\u6267\u884c\u5931\u8d25\uff01");
                    return result;
                }
                currTaskProgress += weight;
                CheckItemResultDate resultDate = new CheckItemResultDate();
                BeanUtils.copyProperties(itemResult, resultDate);
                resultDate.setBegin(begin);
                resultDate.setEnd(new Date());
                mcItemResultMap.put(item.getKey(), resultDate);
            }
            List<String> dynamicDimsForPage = this.mcDimService.getDynamicDimNamesForPage(dataScheme.getKey());
            if (!CollectionUtils.isEmpty(dynamicDimsForPage)) {
                HashMap<String, DimSetDTO> orgDimMap = new HashMap<String, DimSetDTO>();
                schemeDTO.setOrgDims(orgDimMap);
                this.buildOrgDims(dynamicDimsForPage, DimensionValueSetUtil.buildDimensionCollection(vo.getDimSetMap(), (String)vo.getFormScheme()), orgDimMap, null);
            }
            this.progressAndMessage(asyncTaskMonitor, 0.85, "\u5df2\u5ba1\u6838\u5b8c\u6bd5" + itemCount + "\u4e2a\u5ba1\u6838\u9879");
            boolean adjust = this.dataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
            boolean onlyAdjust = adjust && dynamicFields.size() == 1 && "ADJUST".equals(dynamicFields.get(0));
            ArrayList<MultcheckResItem> resItems = new ArrayList<MultcheckResItem>();
            ArrayList<MultcheckResScheme> resSchemes = new ArrayList<MultcheckResScheme>();
            SchemeExecuteResult seResult = new SchemeExecuteResult();
            MultcheckResScheme resScheme = new MultcheckResScheme();
            resScheme.setKey(UUID.randomUUID().toString());
            resScheme.setRecordKey(runId);
            resScheme.setSchemeKey(schemeDTO.getKey());
            resScheme.setBegin(new Date());
            resScheme.setEnd(new Date());
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5ba1\u6838\u9879\u6267\u884c\u5b8c\u6bd5\uff0c\u5f85\u7ec4\u7ec7\u5ba1\u6838\u7ed3\u679c$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for (MultcheckItem item : items) {
                CheckItemResultDate itemResult = (CheckItemResultDate)mcItemResultMap.get(item.getKey());
                this.dealItemResult(itemResult, adjust, onlyAdjust, runId, seResult, item, resItems, resScheme);
            }
            List<String> orgs = schemeDTO.getOrgList();
            orgs.removeAll(seResult.getFailedMap().keySet());
            seResult.setSuccessList(orgs);
            seResult.setOrgDims(schemeDTO.getOrgDims());
            try {
                resScheme.setOrgs(SerializeUtil.serializeToJson(seResult));
            }
            catch (Exception e) {
                logger.error("2\u7efc\u5408\u5ba1\u6838\u6267\u884c\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
            }
            resSchemes.add(resScheme);
            this.saveResult(vo, taskDefine, dataScheme, resItems, resSchemes, runId, dynamicFields, seResult.getSuccessList().size(), seResult.getFailedMap().size(), beginDate, logger, vo.getOrg());
            this.progressAndMessage(asyncTaskMonitor, 0.96, "\u5ba1\u6838\u7ed3\u679c\u7ec4\u7ec7\u5b8c\u6bd5");
            result.setTask(vo.getTask());
            result.setRunId(runId);
            result.setSingleOrgRes(seResult.getSuccessList().size() == 1);
            return result;
        }
        catch (Exception e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setError(MultcheckSchemeError.RUN_ASYNC);
            return result;
        }
    }
}

