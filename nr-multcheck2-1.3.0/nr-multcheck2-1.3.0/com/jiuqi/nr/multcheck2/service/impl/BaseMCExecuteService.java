/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResRecord;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.AsyncTaskWightMonitor;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.service.dto.MCSchemeDTO;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BaseMCExecuteService {
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCResultService mcResultService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private IMCOrgService mcOrgService;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    private void progressAndMessage(AsyncTaskMonitor asyncTaskMonitor, double progress, String message, IMCMonitor mcMonitor) {
        if (mcMonitor != null) {
            this.uploadProgressAndMessage(mcMonitor, progress, message);
        } else {
            this.progressAndMessage(asyncTaskMonitor, progress, message);
        }
    }

    protected void uploadProgressAndMessage(IMCMonitor mcMonitor, double progress, String message) {
        if (mcMonitor != null) {
            mcMonitor.progressAndMessage(progress, message);
        }
    }

    protected void progressAndMessage(AsyncTaskMonitor asyncTaskMonitor, double progress, String message) {
        if (asyncTaskMonitor != null) {
            if (asyncTaskMonitor instanceof AsyncTaskWightMonitor) {
                AsyncTaskWightMonitor asyncTaskMonitor1 = (AsyncTaskWightMonitor)asyncTaskMonitor;
                asyncTaskMonitor1.progressAndMessage(asyncTaskMonitor1.getBegin() + asyncTaskMonitor1.getWight() * progress, message);
            } else {
                asyncTaskMonitor.progressAndMessage(progress, message);
            }
        }
    }

    protected MCExecuteResult checkCompleted(AsyncTaskMonitor asyncTaskMonitor, MCExecuteResult result) {
        if (asyncTaskMonitor == null) {
            return null;
        }
        this.progressAndMessage(asyncTaskMonitor, 0.05, "\u5ba1\u6838\u524d\u53c2\u6570\u6821\u9a8c\u5df2\u5b8c\u6210\uff0c\u8bf7\u7a0d\u7b49");
        if (asyncTaskMonitor.isCancel()) {
            result.setCancel(true);
            result.setCancelMsg("\u5ba1\u6838\u9879\u672a\u6267\u884c");
            return result;
        }
        return null;
    }

    protected MultcheckEnvContext buildContext(MCSchemeDTO dto) {
        MCRunVO vo = dto.getVo();
        MultcheckEnvContext context = new MultcheckEnvContext();
        context.setCheckSchemeKey(dto.getKey());
        context.setTaskKey(dto.getTask());
        context.setFormSchemeKey(dto.getFormScheme());
        context.setPeriod(vo.getPeriod());
        context.setOrgList(new ArrayList<String>(dto.getOrgList()));
        context.setOrg(dto.getOrg());
        DimensionValue periodDimension = new DimensionValue();
        periodDimension.setName("DATATIME");
        periodDimension.setValue(vo.getPeriod());
        vo.getDimSetMap().put("DATATIME", periodDimension);
        DimensionValue dwDimension = new DimensionValue();
        String dimensionName = this.entityMetaService.queryEntity(dto.getOrg()).getDimensionName();
        dwDimension.setName(dimensionName);
        dwDimension.setValue(String.join((CharSequence)";", dto.getOrgList()));
        vo.getDimSetMap().put(dimensionName, dwDimension);
        context.setDimSetMap(vo.getDimSetMap());
        return context;
    }

    protected MCExecuteResult processTask(AsyncTaskMonitor asyncTaskMonitor, MCExecuteResult result, Map<String, MultcheckItem> asyncIdToItem, Logger logger, List<String> dynamicDimsForPage, List<MCSchemeDTO> schemeDTOS) throws JobExecutionException {
        return this.processTask(asyncTaskMonitor, result, asyncIdToItem, logger, dynamicDimsForPage, schemeDTOS, null);
    }

    protected MCExecuteResult uploadProcessTask(IMCMonitor mcMonitor, MCExecuteResult result, Map<String, MultcheckItem> asyncIdToItem, Logger logger, List<String> dynamicDimsForPage, List<MCSchemeDTO> schemeDTOS) throws JobExecutionException {
        return this.processTask(null, result, asyncIdToItem, logger, dynamicDimsForPage, schemeDTOS, mcMonitor);
    }

    /*
     * Exception decompiling
     */
    private MCExecuteResult processTask(AsyncTaskMonitor asyncTaskMonitor, MCExecuteResult result, Map<String, MultcheckItem> asyncIdToItem, Logger logger, List<String> dynamicDimsForPage, List<MCSchemeDTO> schemeDTOS, IMCMonitor mcMonitor) throws JobExecutionException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [11[UNCONDITIONALDOLOOP]], but top level block is 15[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean isCancel(AsyncTaskMonitor asyncTaskMonitor, IMCMonitor mcMonitor) {
        if (mcMonitor != null) {
            return mcMonitor.isCancel();
        }
        return asyncTaskMonitor != null && asyncTaskMonitor.isCancel();
    }

    protected void buildOrgDims(List<String> dynamicDimsForPage, DimensionCollection dimensionCollection, Map<String, DimSetDTO> orgDimMap, Map<String, Set<String>> schemeDimSet) {
        for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
            String org = (String)dimensionCombination.getDWDimensionValue().getValue();
            DimSetDTO dimMap = orgDimMap.get(org);
            if (dimMap == null) {
                dimMap = new DimSetDTO();
                orgDimMap.put(org, dimMap);
            }
            for (String dim : dynamicDimsForPage) {
                String dimValue = (String)dimensionCombination.getValue(dim);
                Set<String> dimSet = dimMap.getDims().get(dim);
                if (dimSet == null) {
                    dimSet = new HashSet<String>();
                    dimMap.getDims().put(dim, dimSet);
                }
                dimSet.add(dimValue);
                if (schemeDimSet == null) continue;
                Set<String> dimValueSet = schemeDimSet.get(dim);
                if (dimValueSet == null) {
                    dimValueSet = new HashSet<String>();
                    schemeDimSet.put(dim, dimValueSet);
                }
                dimValueSet.add(dimValue);
            }
        }
    }

    protected void dealItemResult(CheckItemResultDate itemResult, boolean adjust, boolean onlyAdjust, String runId, SchemeExecuteResult seResult, MultcheckItem item, List<MultcheckResItem> resItems, MultcheckResScheme resScheme) {
        List<String> list;
        List<String> successWithExplainOrgs;
        Map<String, FailedOrgInfo> failedOrgs = itemResult.getFailedOrgs();
        if (!CollectionUtils.isEmpty(failedOrgs)) {
            for (Map.Entry<String, FailedOrgInfo> entry : failedOrgs.entrySet()) {
                FailedOrgInfo failedOrgInfo = entry.getValue();
                if (adjust) {
                    List<FailedOrgDimInfo> dimInfo = failedOrgInfo.getDimInfo();
                    if (onlyAdjust) {
                        if (!CollectionUtils.isEmpty(dimInfo) && !StringUtils.hasText(failedOrgInfo.getDesc()) && StringUtils.hasText(dimInfo.get(0).getDesc())) {
                            failedOrgInfo.setDesc(dimInfo.get(0).getDesc());
                            failedOrgInfo.setDimInfo(null);
                        }
                    } else if (!CollectionUtils.isEmpty(dimInfo)) {
                        for (FailedOrgDimInfo dim : dimInfo) {
                            dim.getDims().remove("ADJUST");
                        }
                    }
                }
                seResult.failedMapAdd(entry.getKey(), item.getKey(), failedOrgInfo);
            }
        }
        if (!CollectionUtils.isEmpty(successWithExplainOrgs = itemResult.getSuccessWithExplainOrgs())) {
            for (String string : successWithExplainOrgs) {
                seResult.successWithExplainMapAdd(string, item.getKey());
            }
        }
        if (!CollectionUtils.isEmpty(list = itemResult.getIgnoreOrgs())) {
            for (String org : list) {
                seResult.ignoreMapAdd(org, item.getKey());
            }
        }
        int n2 = CollectionUtils.isEmpty(itemResult.getSuccessOrgs()) ? 0 : itemResult.getSuccessOrgs().size();
        n2 = n2 + (CollectionUtils.isEmpty(itemResult.getSuccessWithExplainOrgs()) ? 0 : itemResult.getSuccessWithExplainOrgs().size());
        MultcheckResItem resItem = new MultcheckResItem();
        resItem.setKey(UUID.randomUUID().toString());
        resItem.setRecordKey(runId);
        resItem.setSchemeKey(item.getScheme());
        resItem.setItemKey(item.getKey());
        resItem.setState(itemResult.getResult());
        resItem.setSuccess(n2);
        resItem.setFailed(CollectionUtils.isEmpty(itemResult.getFailedOrgs()) ? 0 : itemResult.getFailedOrgs().size());
        resItem.setIgnore(CollectionUtils.isEmpty(itemResult.getIgnoreOrgs()) ? 0 : itemResult.getIgnoreOrgs().size());
        resItem.setBegin(itemResult.getBegin());
        resItem.setEnd(itemResult.getEnd());
        resItem.setRunConfig(StringUtils.hasText(itemResult.getRunConfig()) ? itemResult.getRunConfig() : item.getConfig());
        resItems.add(resItem);
        if (resScheme.getBegin().after(itemResult.getBegin())) {
            resScheme.setBegin(itemResult.getBegin());
        }
        if (resScheme.getEnd().before(itemResult.getEnd())) {
            resScheme.setEnd(itemResult.getEnd());
        }
    }

    protected void saveResult(MCRunVO vo, TaskDefine taskDefine, DataScheme dataScheme, List<MultcheckResItem> resItems, List<MultcheckResScheme> resSchemes, String runId, List<String> dynamicFields, int success, int failed, Date beginDate, Logger logger, String schemeKey) {
        String itemTable = this.mcResultService.getTableName("NR_MCR_ITEM_", taskDefine.getKey(), dataScheme);
        this.mcResultService.itemBatchAdd(resItems, itemTable);
        String schemeTable = this.mcResultService.getTableName("NR_MCR_SCHEME_", taskDefine.getKey(), dataScheme);
        this.mcResultService.schemeBatchAdd(resSchemes, schemeTable);
        String recordTable = this.mcResultService.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        MultcheckResRecord record = new MultcheckResRecord();
        record.setKey(runId);
        record.setTask(vo.getTask());
        record.setPeriod(vo.getPeriod());
        record.setSchemeKey(schemeKey);
        if (!CollectionUtils.isEmpty(dynamicFields)) {
            HashMap<String, String> dims = new HashMap<String, String>();
            record.setDims(dims);
            for (String dimKey : vo.getDimSetMap().keySet()) {
                if (!dynamicFields.contains(dimKey)) continue;
                dims.put(dimKey, vo.getDimSetMap().get(dimKey).getValue());
            }
        }
        record.setSource(vo.getSource());
        record.setSuccess(success);
        record.setFailed(failed);
        record.setBegin(beginDate);
        record.setEnd(new Date());
        record.setUser(NpContextHolder.getContext().getUserName());
        this.mcResultService.recordAdd(recordTable, record);
        StringBuilder sb = new StringBuilder();
        sb.append("\u7efc\u5408\u5ba1\u6838\u6267\u884c\uff1a\n").append("\u4efb\u52a1\uff1a").append(taskDefine.getTitle()).append("\n").append("\u65f6\u671f\uff1a").append(vo.getPeriod()).append("\n").append("\u6267\u884c\u5355\u4f4d").append(success + failed).append("\u5bb6\uff0c\u5176\u4e2d\u901a\u8fc7").append(success).append("\u5bb6\uff0c\u5931\u8d25").append(failed).append("\u5bb6\n");
        logger.info(sb.toString());
        LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)(this.getLoggerTitle(logger) + "\u6267\u884c\u6210\u529f"), (String)sb.toString());
    }

    protected String getLoggerTitle(Logger logger) {
        String loggerTitle = "\u7efc\u5408\u5ba1\u6838";
        if (logger.getName().contains("MCExecuteAPPFlowServiceImpl")) {
            loggerTitle = "\u7efc\u5408\u5ba1\u6838APP\u4e0a\u62a5";
        } else if (logger.getName().contains("MCExecuteAPPSchemeServiceImpl")) {
            loggerTitle = "\u7efc\u5408\u5ba1\u6838APP\u65b9\u6848";
        } else if (logger.getName().contains("MCExecuteEntryMultiServiceImpl")) {
            loggerTitle = "\u5f55\u5165\u6279\u91cf\u7efc\u5408\u5ba1\u6838";
        } else if (logger.getName().contains("MCExecuteEntrySingleImpl")) {
            loggerTitle = "\u5f55\u5165\u7efc\u5408\u5ba1\u6838";
        } else if (logger.getName().contains("MCExecuteUploadMultiServiceImpl")) {
            loggerTitle = "\u6279\u91cf\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838";
        } else if (logger.getName().contains("MCExecuteUploadSingleServiceImpl")) {
            loggerTitle = "\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838";
        }
        return loggerTitle;
    }

    protected MCExecuteResult buildMCSchemeDTOForEntry(MCRunVO vo, List<MCLabel> unboundList, List<MCSchemeDTO> schemeDTOS, Logger logger) {
        MCExecuteResult result = new MCExecuteResult();
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByFSAndOrg(vo.getFormScheme(), vo.getOrg());
        if (CollectionUtils.isEmpty(schemes)) {
            result.setError(MultcheckSchemeError.RUN_NOSCHEME);
            return result;
        }
        List<String> orgList = vo.getOrgCodes();
        int count = orgList.size();
        for (MultcheckScheme scheme : schemes) {
            List<String> schemeOrgList;
            List<MultcheckItem> itemInfoList;
            if (SchemeType.FLOW != scheme.getType() || (itemInfoList = this.schemeService.getItemInfoList(scheme.getKey())).isEmpty()) continue;
            MCSchemeDTO schemeDTO = new MCSchemeDTO(scheme);
            if (OrgType.ALL == schemeDTO.getOrgType()) {
                schemeDTOS.add(schemeDTO);
                schemeDTO.setOrgList(new ArrayList<String>(orgList));
                orgList.clear();
                break;
            }
            try {
                schemeOrgList = this.mcOrgService.getOrgsBySchemePeriod(scheme, vo.getPeriod());
            }
            catch (Exception e) {
                logger.error(schemeDTO.getTitle() + "::\u7efc\u5408\u5ba1\u6838\u6267\u884c\u83b7\u53d6\u65b9\u6848\u5355\u4f4d\u5f02\u5e38\uff1a" + e.getMessage(), e);
                result.setError(MultcheckSchemeError.RUN_SCHEMEORG);
                result.setErrorMsg(String.format("\u83b7\u53d6\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u3010%s\u3011\u7684\u5355\u4f4d\u8303\u56f4\u5f02\u5e38\uff1a%s", schemeDTO.getTitle(), e.getMessage()));
                return result;
            }
            schemeOrgList.retainAll(orgList);
            if (!CollectionUtils.isEmpty(schemeOrgList)) {
                schemeDTOS.add(schemeDTO);
                schemeDTO.setOrgList(schemeOrgList);
            }
            orgList.removeAll(schemeOrgList);
            if (!CollectionUtils.isEmpty(orgList)) continue;
            break;
        }
        if (!CollectionUtils.isEmpty(orgList)) {
            try {
                if (count == orgList.size()) {
                    result.setError(MultcheckSchemeError.RUN_ORGNOSCHEME1);
                    return result;
                }
                unboundList.addAll(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), orgList));
            }
            catch (Exception e) {
                logger.error("\u7efc\u5408\u5ba1\u6838\u6267\u884c\u83b7\u53d6\u5355\u4f4d\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return null;
    }

    protected MCExecuteResult buildMCSchemeDTO(MCRunVO vo, List<MCLabel> unboundList, List<MCSchemeDTO> schemeDTOS, List<DataDimension> otherDimsForReport, Logger logger) {
        MCExecuteResult result = new MCExecuteResult();
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByFSAndOrg(vo.getFormScheme(), vo.getOrg());
        if (CollectionUtils.isEmpty(schemes)) {
            result.setError(MultcheckSchemeError.RUN_NOSCHEME);
            return result;
        }
        List<String> orgList = vo.getOrgCodes();
        int count = orgList.size();
        String noDimScheme = "";
        for (MultcheckScheme scheme : schemes) {
            List<String> schemeOrgList;
            List<MultcheckItem> itemInfoList;
            if (SchemeType.FLOW != scheme.getType() || (itemInfoList = this.schemeService.getItemInfoList(scheme.getKey())).isEmpty()) continue;
            MCSchemeDTO schemeDTO = new MCSchemeDTO(scheme);
            if (OrgType.ALL == schemeDTO.getOrgType()) {
                schemeDTOS.add(schemeDTO);
                schemeDTO.setOrgList(new ArrayList<String>(orgList));
                orgList.clear();
                noDimScheme = this.setReportDim(otherDimsForReport, noDimScheme, schemeDTO);
                break;
            }
            try {
                schemeOrgList = this.mcOrgService.getOrgsBySchemePeriod(scheme, vo.getPeriod());
            }
            catch (Exception e) {
                logger.error(schemeDTO.getTitle() + "::\u7efc\u5408\u5ba1\u6838\u6267\u884c\u83b7\u53d6\u65b9\u6848\u5355\u4f4d\u5f02\u5e38\uff1a" + e.getMessage(), e);
                result.setError(MultcheckSchemeError.RUN_SCHEMEORG);
                result.setErrorMsg(String.format("\u83b7\u53d6\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u3010%s\u3011\u7684\u5355\u4f4d\u8303\u56f4\u5f02\u5e38\uff1a%s", schemeDTO.getTitle(), e.getMessage()));
                return result;
            }
            schemeOrgList.retainAll(orgList);
            if (!CollectionUtils.isEmpty(schemeOrgList)) {
                schemeDTOS.add(schemeDTO);
                schemeDTO.setOrgList(schemeOrgList);
                noDimScheme = this.setReportDim(otherDimsForReport, noDimScheme, schemeDTO);
            }
            orgList.removeAll(schemeOrgList);
            if (!CollectionUtils.isEmpty(orgList)) continue;
            break;
        }
        if (StringUtils.hasText(noDimScheme)) {
            result.setError(MultcheckSchemeError.RUN_APP_FLOW_NODIM);
            result.setErrorMsg("\u5ba1\u6838\u65b9\u6848\uff1a" + noDimScheme + " \u672a\u914d\u7f6e\u4e0a\u62a5\u524d\u60c5\u666f");
            return result;
        }
        if (!CollectionUtils.isEmpty(orgList)) {
            try {
                if (count == orgList.size()) {
                    result.setError(MultcheckSchemeError.RUN_ORGNOSCHEME1);
                    return result;
                }
                unboundList.addAll(this.mcOrgService.getOrgLabels(vo.getTask(), vo.getPeriod(), vo.getOrg(), orgList));
            }
            catch (Exception e) {
                logger.error("\u7efc\u5408\u5ba1\u6838\u6267\u884c\u83b7\u53d6\u5355\u4f4d\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return null;
    }

    protected String setReportDim(List<DataDimension> otherDimsForReport, String noDimScheme, MCSchemeDTO schemeDTO) {
        if (!CollectionUtils.isEmpty(otherDimsForReport)) {
            String reportDim = this.schemeService.getReportDim(schemeDTO.getKey());
            if (StringUtils.hasText(reportDim)) {
                try {
                    schemeDTO.setReportDimVO(SerializeUtil.deserializeFromJson(reportDim, ReportDimVO.class));
                    schemeDTO.setReportDim(reportDim);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (schemeDTO.getReportDimVO() == null) {
                noDimScheme = noDimScheme + "\u3010" + schemeDTO.getTitle() + "\u3011";
            }
        }
        return noDimScheme;
    }

    protected String buildFilterDimValue(MCRunVO vo, Map<String, String> allDimAfterFilterMap, String entityId, String dimensionName, String dimValue) throws Exception {
        if (!StringUtils.hasText(dimValue) || dimValue.split(";").length > 1) {
            String key = dimensionName + "@" + dimValue;
            if (allDimAfterFilterMap.containsKey(key)) {
                dimValue = allDimAfterFilterMap.get(key);
            } else {
                List<IEntityRow> entityRows = this.mcDimService.filterEntityValue(entityId, vo.getFormScheme(), vo.getTask(), vo.getPeriod(), dimValue);
                if (!CollectionUtils.isEmpty(entityRows)) {
                    dimValue = entityRows.stream().map(e -> String.valueOf(e.getEntityKeyData())).collect(Collectors.joining(";"));
                }
                allDimAfterFilterMap.put(key, dimValue);
            }
        }
        return dimValue;
    }

    protected String buildGatherAutoNoOrgRecord(MCRunVO vo, TaskDefine taskDefine, DataScheme dataScheme, List<String> dynamicFields, List<String> checkOrgCodes, Date beginDate, String schemeKey) {
        MultcheckResRecord record = new MultcheckResRecord();
        record.setKey(UUID.randomUUID().toString());
        record.setTask(vo.getTask());
        record.setPeriod(vo.getPeriod());
        if (!CollectionUtils.isEmpty(dynamicFields)) {
            HashMap<String, String> dims = new HashMap<String, String>();
            record.setDims(dims);
            for (String dimKey : vo.getDimSetMap().keySet()) {
                if (!dynamicFields.contains(dimKey)) continue;
                dims.put(dimKey, vo.getDimSetMap().get(dimKey).getValue());
            }
        }
        record.setSource(vo.getSource());
        record.setSchemeKey(schemeKey);
        record.setSuccess(checkOrgCodes.size() * -1);
        record.setFailed(0);
        record.setBegin(beginDate);
        record.setEnd(new Date());
        record.setUser(NpContextHolder.getContext().getUserName());
        String recordTable = this.mcResultService.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        this.mcResultService.recordAdd(recordTable, record);
        return record.getKey();
    }

    protected void logByGather(MCRunVO vo, TaskDefine taskDefine, List<String> checkOrgCodes, AsyncTaskMonitor asyncTaskMonitor, Logger logger) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u7efc\u5408\u5ba1\u6838\u6267\u884c\uff1a\n").append("\u81ea\u52a8\u6c47\u603b\u4efb\u52a1\uff1a").append(taskDefine.getTitle()).append("\n").append("\u65f6\u671f\uff1a").append(vo.getPeriod()).append("\n").append("\u6267\u884c\u5355\u4f4d").append(checkOrgCodes).append("\u5bb6\uff0c\u5168\u90e8\u4e3a\u5408\u5e76\u8282\u70b9\uff0c\u9ed8\u8ba4\u5ba1\u6838\u901a\u8fc7\u3002");
        logger.info(sb.toString());
        LogHelper.info((String)"\u7efc\u5408\u5ba1\u6838", (String)(this.getLoggerTitle(logger) + "\u6267\u884c\u6210\u529f"), (String)sb.toString());
        this.progressAndMessage(asyncTaskMonitor, 0.96, "\u5ba1\u6838\u5355\u4f4d\u5747\u4e3a\u5408\u5e76\u8282\u70b9\uff0c\u5ba1\u6838\u5b8c\u6bd5\uff01");
    }
}

