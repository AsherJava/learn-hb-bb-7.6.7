/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.Guid
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.asynctask.MCRealTimeTaskMonitor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.collector.MultcheckCollector;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadSingleService;
import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.service.impl.BaseMCExecuteService;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import com.jiuqi.util.Guid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCExecuteUploadSingleServiceImpl
extends BaseMCExecuteService
implements IMCExecuteUploadSingleService {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteUploadSingleServiceImpl.class);
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private MultcheckCollector collector;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMCOrgService mcOrgService;

    @Override
    public MCUploadResult uploadSingleExecute(IMCMonitor mcMonitor, MCRunVO vo) {
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
            logger.warn("uploadSingleExecute-\u83b7\u53d6\u5355\u4f4d\u4e3a\u7a7a");
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
            MCExecuteResult result0;
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
            }
            if ((result0 = this.buildMCSchemeDTO(vo, new ArrayList<MCLabel>(), schemeDTOS = new ArrayList<MCSchemeDTO>(), otherDimsForReport, logger)) != null) {
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
            HashMap<String, MultcheckItem> mcItemMap = new HashMap<String, MultcheckItem>();
            HashMap<String, CheckItemResultDate> mcItemResultMap = new HashMap<String, CheckItemResultDate>();
            MCSchemeDTO schemeDTO = (MCSchemeDTO)schemeDTOS.get(0);
            ReportDimVO reportDimVO = schemeDTO.getReportDimVO();
            MCRunVO voScheme = new MCRunVO(vo);
            HashMap<String, String> allDimAfterFilterMap = new HashMap<String, String>();
            HashMap<String, IEntityDefine> entityDefineMap = new HashMap<String, IEntityDefine>();
            if (!CollectionUtils.isEmpty(otherDimsForReport)) {
                for (DataDimension dim : otherDimsForReport) {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                    String dimensionName = entityDefine.getDimensionName();
                    entityDefineMap.put(dimensionName, entityDefine);
                    DimensionValue dimension = new DimensionValue();
                    dimension.setName(dimensionName);
                    String dimValue = this.buildFilterDimValue(vo, allDimAfterFilterMap, entityDefine.getId(), dimensionName, reportDimVO.getSchemeDimSet().get(dimensionName));
                    dimension.setValue(dimValue);
                    voScheme.getDimSetMap().put(dimensionName, dimension);
                }
            }
            schemeDTO.setVo(voScheme);
            List<MultcheckItem> items = this.schemeService.getItemList(schemeDTO.getKey());
            int itemCount = items.size();
            double currTaskProgress = 0.1;
            this.uploadProgressAndMessage(mcMonitor, currTaskProgress, "\u5f00\u59cb\u6267\u884c\u5ba1\u6838\u5171" + itemCount + "\u4e2a\u5ba1\u6838\u9879");
            double weight = 0.8 / (double)itemCount;
            int totalProgress = 0;
            String runId = Guid.newGuid();
            MultcheckEnvContext context = this.buildContext(schemeDTO);
            context.setDims(DimensionValueSetUtil.buildDimensionCollection(voScheme.getDimSetMap(), (String)voScheme.getFormScheme()));
            HashMap<String, MultcheckEnvContext> itemContextMap = new HashMap<String, MultcheckEnvContext>();
            MCRealTimeTaskMonitor mcRealTimeTaskMonitor = new MCRealTimeTaskMonitor(Guid.newGuid(), "", null);
            for (MultcheckItem item : items) {
                if (mcMonitor != null && mcMonitor.isCancel()) {
                    result.setErrorMsg(item.getTitle() + "\u5ba1\u6838\u9879\u53d6\u6d88\u6267\u884c");
                    return result;
                }
                MultcheckEnvContext itemContext = context;
                if (!CollectionUtils.isEmpty(otherDimsForReport) && !CollectionUtils.isEmpty(reportDimVO.getItemsDimSet()) && reportDimVO.getItemsDimSet().containsKey(item.getKey())) {
                    logger.info("app\u4e0a\u62a5\u524d\u5ba1\u6838\u6a21\u5f0f\uff1a\u5ba1\u6838\u9879\u5355\u72ec\u914d\u7f6e\u4e0a\u62a5\u671f\u60c5\u666f\uff1a\uff1a" + item.getTitle() + "::" + item.getKey());
                    itemContext = new MultcheckEnvContext(context);
                    itemContextMap.put(item.getKey(), itemContext);
                    Map<String, String> itemDim = reportDimVO.getItemsDimSet().get(item.getKey());
                    for (String dimensionName : itemDim.keySet()) {
                        DimensionValue dimensionValue2 = itemContext.getDimSetMap().get(dimensionName);
                        String entityId = entityDefineMap.containsKey(dimensionName) ? ((IEntityDefine)entityDefineMap.get(dimensionName)).getId() : dimensionName;
                        String dimValue = this.buildFilterDimValue(vo, allDimAfterFilterMap, entityId, dimensionName, itemDim.get(dimensionName));
                        dimensionValue2.setValue(dimValue);
                    }
                    itemContext.setDims(DimensionValueSetUtil.buildDimensionCollection(itemContext.getDimSetMap(), (String)itemContext.getFormSchemeKey()));
                }
                Iterator param = new CheckItemParam();
                ((CheckItemParam)((Object)param)).setBeforeReport(true);
                ((CheckItemParam)((Object)param)).setRunId(runId);
                ((CheckItemParam)((Object)param)).setContext(itemContext);
                ((CheckItemParam)((Object)param)).setCheckItem(item);
                ((CheckItemParam)((Object)param)).setAsyncTaskMonitor((AsyncTaskMonitor)mcRealTimeTaskMonitor);
                Date begin = new Date();
                this.uploadProgressAndMessage(mcMonitor, currTaskProgress, String.format("\u987b\u6267\u884c%s\u4e2a\u5ba1\u6838\u9879\uff0c\u6b63\u5728\u6267\u884c\u7b2c%s\u4e2a\u5ba1\u6838\u9879\u3010%s\u3011", itemCount, ++totalProgress, item.getTitle()));
                logger.info(String.format("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u7b2c%s/%s\u5ba1\u6838\u9879\u3010%s\u3011\u5f00\u59cb\u6267\u884c", totalProgress, itemCount, item.getTitle()));
                CheckItemResult res = this.collector.getProvider(item.getType()).runCheck((CheckItemParam)((Object)param));
                if (res == null) {
                    result.setErrorMsg("\u3010" + item.getTitle() + "\u3011\u6267\u884c\u5931\u8d25\uff01");
                    return result;
                }
                currTaskProgress += weight;
                CheckItemResultDate resultDate = new CheckItemResultDate();
                BeanUtils.copyProperties(res, resultDate);
                resultDate.setBegin(begin);
                resultDate.setEnd(new Date());
                mcItemMap.put(item.getKey(), item);
                mcItemResultMap.put(item.getKey(), resultDate);
            }
            if (!CollectionUtils.isEmpty(dynamicDimsForPage)) {
                HashMap<String, DimSetDTO> orgDimMap = new HashMap<String, DimSetDTO>();
                schemeDTO.setOrgDims(orgDimMap);
                HashMap<String, Set<String>> schemeDimSet = null;
                if (reportDimVO != null) {
                    schemeDimSet = new HashMap<String, Set<String>>();
                }
                this.buildOrgDims(dynamicDimsForPage, DimensionValueSetUtil.buildDimensionCollection(voScheme.getDimSetMap(), (String)voScheme.getFormScheme()), orgDimMap, schemeDimSet);
                if (reportDimVO != null) {
                    Map<String, String> schemeDimSet1 = reportDimVO.getSchemeDimSet();
                    for (String dim : schemeDimSet1.keySet()) {
                        String value = schemeDimSet1.get(dim);
                        if (StringUtils.hasText(value) && value.split(";").length <= 1) continue;
                        schemeDimSet1.put(dim, String.join((CharSequence)";", (Iterable)schemeDimSet.get(dim)));
                    }
                }
                if (!CollectionUtils.isEmpty(itemContextMap)) {
                    HashMap<String, Map<String, DimSetDTO>> itemOrgDimMap = new HashMap<String, Map<String, DimSetDTO>>();
                    schemeDTO.setItemsOrgDims(itemOrgDimMap);
                    for (Object itemKey : itemContextMap.keySet()) {
                        HashMap<String, Set<String>> itemSchemeDimSet = new HashMap<String, Set<String>>();
                        HashMap<String, DimSetDTO> orgDimMapItem = new HashMap<String, DimSetDTO>();
                        itemOrgDimMap.put((String)itemKey, orgDimMapItem);
                        this.buildOrgDims(dynamicDimsForPage, ((MultcheckEnvContext)itemContextMap.get(itemKey)).getDims(), orgDimMapItem, itemSchemeDimSet);
                        Map<String, String> dimMap = reportDimVO.getItemsDimSet().get(itemKey);
                        for (String dim : dimMap.keySet()) {
                            String value = dimMap.get(dim);
                            if (StringUtils.hasText(value) && value.split(";").length <= 1) continue;
                            dimMap.put(dim, String.join((CharSequence)";", (Iterable)itemSchemeDimSet.get(dim)));
                        }
                    }
                }
            }
            this.uploadProgressAndMessage(mcMonitor, 0.85, "\u5df2\u5ba1\u6838\u5b8c\u6bd5" + itemCount + "\u4e2a\u5ba1\u6838\u9879");
            boolean onlyAdjust = adjust && dynamicFields.size() == 1 && "ADJUST".equals(dynamicFields.get(0));
            MultcheckResScheme resScheme = new MultcheckResScheme();
            resScheme.setKey(UUID.randomUUID().toString());
            resScheme.setRecordKey(runId);
            resScheme.setSchemeKey(schemeDTO.getKey());
            resScheme.setBegin(new Date());
            resScheme.setEnd(new Date());
            ArrayList<MultcheckResItem> resItems = new ArrayList<MultcheckResItem>();
            SchemeExecuteResult seResult = new SchemeExecuteResult();
            logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\u5ba1\u6838\u9879\u6267\u884c\u5b8c\u6bd5\uff0c\u5f85\u7ec4\u7ec7\u5ba1\u6838\u7ed3\u679c$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for (String itemKey : mcItemResultMap.keySet()) {
                MultcheckItem item = (MultcheckItem)mcItemMap.get(itemKey);
                CheckItemResultDate itemResult = (CheckItemResultDate)mcItemResultMap.get(itemKey);
                this.dealItemResult(itemResult, adjust, onlyAdjust, runId, seResult, item, resItems, resScheme);
            }
            List<String> orgs = schemeDTO.getOrgList();
            String org = orgs.get(0);
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
            try {
                List<MCLabel> orgLabels = this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), Arrays.asList(org));
                if (seResult.getSuccessList().size() == 1) {
                    result.setSuccessList(orgLabels);
                } else {
                    result.setFailedList(orgLabels);
                }
            }
            catch (Exception e) {
                logger.error("\u7efc\u5408\u5ba1\u6838\u6267\u884c\u83b7\u53d6\u5355\u4f4d\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            ArrayList<MultcheckResScheme> resSchemes = new ArrayList<MultcheckResScheme>();
            resSchemes.add(resScheme);
            this.saveResult(vo, taskDefine, dataScheme, resItems, resSchemes, runId, dynamicFields, seResult.getSuccessList().size(), seResult.getFailedMap().size(), beginDate, logger, vo.getOrg());
            this.uploadProgressAndMessage(mcMonitor, 0.96, "\u5ba1\u6838\u7ed3\u679c\u7ec4\u7ec7\u5b8c\u6bd5");
            result.setTask(vo.getTask());
            result.setRunId(runId);
            return result;
        }
        catch (Exception e) {
            String title = this.getLoggerTitle(logger) + "\u6267\u884c\u5f02\u5e38";
            logger.error(title + "\uff1a" + e.getMessage(), e);
            LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)title, (String)("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage()));
            result.setErrorMsg("\u7efc\u5408\u5ba1\u6838\u5f02\u5e38\uff1a" + e.getMessage());
            return result;
        }
    }
}

