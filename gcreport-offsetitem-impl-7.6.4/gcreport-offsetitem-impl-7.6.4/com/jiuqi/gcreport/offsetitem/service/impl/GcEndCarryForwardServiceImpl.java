/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.DeferredIncomeTax
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.DeferredIncomeTax;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.EndCarryForwardDataSourceServiceImpl;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.task.DeferredIncomeTaxTaskImpl;
import com.jiuqi.gcreport.offsetitem.task.GcReclassifyTask;
import com.jiuqi.gcreport.offsetitem.task.IMinorityLossGainRecoveryTask;
import com.jiuqi.gcreport.offsetitem.task.LossGainTaskImpl;
import com.jiuqi.gcreport.offsetitem.utils.BaseDataUtils;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO;
import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class GcEndCarryForwardServiceImpl
extends GcOffSetItemAdjustServiceAbstract
implements GcEndCarryForwardService {
    private static final Logger logger = LoggerFactory.getLogger(GcEndCarryForwardServiceImpl.class);
    @Autowired
    private DeferredIncomeTaxTaskImpl deferredIncomeTaxTask;
    @Autowired
    private LossGainTaskImpl lossGainTask;
    @Autowired
    private GcOffSetItemAdjustCoreService adjustingCoreService;
    @Autowired
    private GcOffSetItemAdjustCoreServiceImpl offsetCoreService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private GcI18nHelper gcI18nHelper;
    @Autowired
    private Collection<GcReclassifyTask> reclassifyTasks;
    @Autowired
    private EndCarryForwardDataSourceServiceImpl endCarryForwardDataSourceService;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    ThreadLocal<String> systemIdLocal = new ThreadLocal();
    ThreadLocal<ConsolidatedOptionVO> optionVOLocal = new ThreadLocal();
    ThreadLocal<String> diffUnitIdLocal = new ThreadLocal();
    ThreadLocal<Map<String, String>> accountFieldCode2DictTableMapLocal = new ThreadLocal();
    private static final String DEFFERED_INCOME_TAX = "deffered";
    private static final String MINORITY_LOSS_GAIN_RECOVERY = "minority";
    private static final String LOSS_GAIN = "loss";
    private static final String TOTAL_DATA = "total";

    @Override
    public EndCarryForwardResultVO queryEndCarryForward(QueryParamsVO queryParamsVO) {
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(queryParamsVO.getPeriodStr()));
        String taskTitle = GcOffsetItemUtils.getTaskTitle(queryParamsVO.getTaskId());
        logger.info("\u5f00\u59cb\u67e5\u8be2\u671f\u672b\u7ed3\u8f6c\u6570\u636e\uff0c\u67e5\u8be2\u4efb\u52a1: {},\u67e5\u8be2\u65f6\u671f: {}", (Object)taskTitle, (Object)periodTitle);
        queryParamsVO = this.simpleParam(queryParamsVO);
        List<GcOffSetVchrItemAdjustEO> offsetVchrItems = this.queryData(queryParamsVO);
        List<GcOffSetVchrItemAdjustEO> lastRecords = this.loadLastRecords(queryParamsVO);
        EndCarryForwardDataPoolVO dataPool = this.groupAndConvert(offsetVchrItems, queryParamsVO);
        dataPool = this.calculate(dataPool, queryParamsVO);
        EndCarryForwardResultVO resultVO = this.cleanItemData(dataPool, lastRecords, queryParamsVO);
        this.queryReclassifyData(queryParamsVO, resultVO);
        logger.info("\u67e5\u8be2\u671f\u672b\u7ed3\u8f6c\u6570\u636e\u5b8c\u6210");
        return resultVO;
    }

    @Override
    public Boolean saveEndCarryForward(QueryParamsVO queryParamsVO, LossGainOffsetVO lossGainOffsetVO) {
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(queryParamsVO.getPeriodStr()));
        String taskTitle = GcOffsetItemUtils.getTaskTitle(queryParamsVO.getTaskId());
        logger.info("\u5f00\u59cb\u4fdd\u5b58\u671f\u672b\u7ed3\u8f6c\u6570\u636e\uff0c\u4fdd\u5b58\u4efb\u52a1: {},\u4fdd\u5b58\u65f6\u671f: {}", (Object)taskTitle, (Object)periodTitle);
        queryParamsVO = this.simpleParam(queryParamsVO);
        this.initFieldValue(queryParamsVO);
        this.deleteLastOffsetEntrys(queryParamsVO);
        int lossGainCount = this.saveOffsetVchrItems(lossGainOffsetVO.currLossGainResult());
        int minorityRecoveryCount = this.saveOffsetVchrItems(lossGainOffsetVO.currMinorityRecoveryResult());
        int deferredIncomeTaxCount = this.saveOffsetVchrItems(lossGainOffsetVO.currDeferredIncomeTaxResult());
        GcActionParamsVO actionParamsVO = new GcActionParamsVO();
        BeanUtils.copyProperties(queryParamsVO, actionParamsVO);
        StringBuilder reclassifyTaskLog = new StringBuilder();
        for (GcReclassifyTask task : this.reclassifyTasks) {
            int reclassifyEntryCount = task.doTask(actionParamsVO, Collections.singletonList(queryParamsVO.getOrgId()), new TaskLog(new OnekeyProgressDataImpl(UUIDOrderUtils.newUUIDStr())));
            reclassifyTaskLog.append(String.format("%1$s:%2$d\u7ec4;\n", task.name(), reclassifyEntryCount));
        }
        String logTitle = String.format("\u671f\u672b\u7ed3\u8f6c-\u4efb\u52a1%1$s-\u65f6\u671f%2$s", taskTitle, periodTitle);
        String userName = NpContextHolder.getContext().getUser().getName();
        String logs = String.format("\u7ed3\u8f6c\u635f\u76ca:%1$d\u7ec4;\n\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f:%2$d\u7ec4;\n\u9012\u5ef6\u6240\u5f97\u7a0e:%3$d\u7ec4;\n%4$s\u4efb\u52a1%5$s;\u65f6\u671f%6$s;\u64cd\u4f5c\u4eba:%7$s;", lossGainCount, minorityRecoveryCount, deferredIncomeTaxCount, reclassifyTaskLog.toString(), taskTitle, periodTitle, userName);
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)logTitle, (String)logs);
        logger.info("\u4fdd\u5b58\u671f\u672b\u7ed3\u8f6c\u6570\u636e\u5b8c\u6210");
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean doLossGain(QueryParamsVO queryParamsVO) {
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(queryParamsVO.getPeriodStr()));
        String taskTitle = GcOffsetItemUtils.getTaskTitle(queryParamsVO.getTaskId());
        logger.info("\u5f00\u59cb\u4fdd\u5b58\u671f\u672b\u7ed3\u8f6c\u6570\u636e\uff0c\u4fdd\u5b58\u4efb\u52a1: {},\u4fdd\u5b58\u65f6\u671f: {}", (Object)taskTitle, (Object)periodTitle);
        queryParamsVO = this.simpleParam(queryParamsVO);
        this.initFieldValue(queryParamsVO);
        this.deleteLastOffsetEntrys(queryParamsVO);
        EndCarryForwardDataPoolVO dataPool = this.getOffsetEntrys(queryParamsVO);
        int lossGainCount = this.saveOffsetVchrItems(dataPool.getLossGainGroup().getEndCarryForwarditem());
        int minorityRecoveryCount = this.saveOffsetVchrItems(dataPool.getMinorityRecoveryGroup().getEndCarryForwarditem());
        int deferredIncomeTaxCount = this.saveOffsetVchrItems(dataPool.getDeferredIncomeTaxGroup().getEndCarryForwarditem());
        GcActionParamsVO actionParamsVO = new GcActionParamsVO();
        BeanUtils.copyProperties(queryParamsVO, actionParamsVO);
        StringBuilder reclassifyTaskLog = new StringBuilder();
        String logTitle = String.format("\u671f\u672b\u7ed3\u8f6c-\u4efb\u52a1%1$s-\u65f6\u671f%2$s", taskTitle, periodTitle);
        String userName = NpContextHolder.getContext().getUser().getName();
        String logs = String.format("\u7ed3\u8f6c\u635f\u76ca:%1$d\u7ec4;\n\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f:%2$d\u7ec4;\n\u9012\u5ef6\u6240\u5f97\u7a0e:%3$d\u7ec4;\n%4$s\u4efb\u52a1%5$s;\u65f6\u671f%6$s;\u64cd\u4f5c\u4eba:%7$s;", lossGainCount, minorityRecoveryCount, deferredIncomeTaxCount, reclassifyTaskLog.toString(), taskTitle, periodTitle, userName);
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)logTitle, (String)logs);
        logger.info("\u4fdd\u5b58\u671f\u672b\u7ed3\u8f6c\u6570\u636e\u5b8c\u6210");
        return true;
    }

    private void deleteLastOffsetEntrys(QueryParamsVO queryParamsVO) {
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        this.offSetItemAdjustService.deleteOffsetEntrys(queryParamsVO);
    }

    private EndCarryForwardDataPoolVO getOffsetEntrys(QueryParamsVO queryParamsVO) {
        queryParamsVO = this.simpleParam(queryParamsVO);
        List<GcOffSetVchrItemAdjustEO> offsetVchrItems = this.queryData(queryParamsVO);
        EndCarryForwardDataPoolVO dataPool = this.groupAndConvert(offsetVchrItems, queryParamsVO);
        dataPool = this.calculate(dataPool, queryParamsVO);
        return dataPool;
    }

    private int saveOffsetVchrItems(List<GcOffSetVchrItemDTO> vchrItemDTOS) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        int offsetEntryCount = 0;
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty(vchrItemDTOS)) {
            Map<String, List<GcOffSetVchrItemDTO>> groups = vchrItemDTOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemDTO::getmRecid));
            ArrayList<GcOffSetVchrDTO> list = new ArrayList<GcOffSetVchrDTO>();
            for (List<GcOffSetVchrItemDTO> oneGroup : groups.values()) {
                GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO();
                offSetVchrDTO.setItems(oneGroup);
                offSetVchrDTO.setMrecid(oneGroup.get(0).getmRecid());
                offSetVchrDTO.setNeedDelete(false);
                list.add(offSetVchrDTO);
            }
            this.adjustingCoreService.batchSave(list);
            ++offsetEntryCount;
        }
        return offsetEntryCount;
    }

    @Override
    public Pagination<Map<String, Object>> listMinRecoveryPentrateDatas(MinorityRecoveryParamsVO queryParamsVO) {
        MinorityRecoveryParamsVO lossGainSimpleParam = this.simpleParam((QueryParamsVO)queryParamsVO);
        this.initFieldValue((QueryParamsVO)lossGainSimpleParam);
        lossGainSimpleParam.setQueryAllColumns(false);
        lossGainSimpleParam.setLossGain(queryParamsVO.getLossGain());
        lossGainSimpleParam.setMinorityTotalType(queryParamsVO.getMinorityTotalType());
        List<GcOffSetVchrItemAdjustEO> assetOffsetList = this.queryData((QueryParamsVO)lossGainSimpleParam);
        Map<String, List<GcOffSetVchrItemAdjustEO>> stringListMap = this.groupByRecords(assetOffsetList, (QueryParamsVO)queryParamsVO);
        Map<String, List<String>> idMaps = this.getIdMaps(stringListMap, lossGainSimpleParam);
        Set srcOffsetMrecids = assetOffsetList.stream().map(GcOffSetVchrItemAdjustEO::getmRecid).collect(Collectors.toSet());
        Set<String> srcOffsetIds = assetOffsetList.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        Pagination pagination = new Pagination(this.offsetCoreService.listWithFullGroupByMrecids(queryParamsDTO, srcOffsetMrecids), Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        Pagination offsetPage = this.offsetCoreService.assembleOffsetEntry(pagination, (QueryParamsVO)queryParamsVO);
        List offsetDatas = offsetPage.getContent();
        HashSet<String> offsetIds = new HashSet<String>();
        Set<String> mRecids = this.filterPentrateOffsetDatas(queryParamsVO, offsetDatas, offsetIds, srcOffsetIds, idMaps);
        offsetPage.setTotalElements(Integer.valueOf(mRecids.size()));
        this.pageOffsetByMrecids(queryParamsVO.getPageNum(), queryParamsVO.getPageSize(), mRecids);
        offsetDatas = offsetDatas.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        String pentrateType = queryParamsVO.getCurrShowTypeValue();
        if ("2".equals(pentrateType)) {
            offsetDatas = offsetDatas.stream().filter(offset -> offsetIds.contains(offset.get("ID"))).collect(Collectors.toList());
        }
        offsetDatas = this.setRowSpanAndSort(offsetDatas);
        offsetPage.setContent(offsetDatas);
        return offsetPage;
    }

    private List<GcOffSetVchrItemAdjustEO> queryData(QueryParamsVO queryParamsVO) {
        this.initFieldValue(queryParamsVO);
        List<GcOffSetVchrItemAdjustEO> itemAdjustEOS = this.queryItemData(queryParamsVO);
        return itemAdjustEOS;
    }

    private List<GcOffSetVchrItemAdjustEO> queryItemData(QueryParamsVO queryParamsVO) {
        queryParamsVO.setQueryAllColumns(true);
        queryParamsVO.getOtherShowColumns().add("ID");
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue());
        queryParamsVO.setForbidOffSetSrcTypes(offSetSrcTypes);
        Set profitLossSubjectCodes = this.subjectService.listAllCodesByAttr(this.systemIdLocal.get(), SubjectAttributeEnum.getEnumByValue((Integer)SubjectAttributeEnum.PROFITLOSS.getValue()));
        queryParamsVO.setSubjectCodes(new ArrayList(profitLossSubjectCodes));
        List gcOffSetVchrItemAdjustEOS = this.endCarryForwardDataSourceService.listEOWithFullGroup(queryParamsVO, true).getContent();
        return gcOffSetVchrItemAdjustEOS;
    }

    private List<GcOffSetVchrItemAdjustEO> loadLastRecords(QueryParamsVO queryParamsVO) {
        MinorityRecoveryParamsVO lastQueryParamsVO = this.simpleParam(queryParamsVO);
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue());
        lastQueryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        lastQueryParamsVO.getOtherShowColumns().addAll(queryParamsVO.getOtherShowColumns());
        lastQueryParamsVO.getOtherShowColumns().add("OFFSETSRCTYPE");
        List lastRecords = this.endCarryForwardDataSourceService.listEOWithFullGroup((QueryParamsVO)lastQueryParamsVO, true).getContent();
        return lastRecords;
    }

    private EndCarryForwardDataPoolVO calculate(EndCarryForwardDataPoolVO dataPool, QueryParamsVO queryParamsVO) {
        ConsolidatedTaskVO taskOption = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskByTaskKeyAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        if (this.allowDeferredIncomeTaxByOption(taskOption)) {
            this.deferredIncomeTaxTask.calculate(dataPool, queryParamsVO, this.optionVOLocal.get());
        }
        if (this.allowDo(taskOption)) {
            if (!Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
                Assert.isNotNull((Object)this.diffUnitIdLocal.get(), (String)GcI18nUtil.getMessage((String)"gc.offsetitem.adjustingentry.showoffset.lossGainRecovery.noDiffUnit", (Object[])new String[]{queryParamsVO.getOrgId()}), (Object[])new Object[0]);
            }
            IMinorityLossGainRecoveryTask recoveryTask = (IMinorityLossGainRecoveryTask)SpringContextUtils.getBean(IMinorityLossGainRecoveryTask.class);
            recoveryTask.calculate(dataPool, queryParamsVO, this.optionVOLocal.get());
        }
        if (taskOption.getEnableLossGain().booleanValue()) {
            Assert.isNotNull((Object)this.optionVOLocal.get().getIntermediateSubjectCode(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.midSubjectEmpty"), (Object[])new Object[0]);
            if (!Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
                Assert.isNotNull((Object)this.diffUnitIdLocal.get(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.noDiffUnit", (Object[])new String[]{queryParamsVO.getOrgId()}), (Object[])new Object[0]);
            }
            Assert.isNotNull((Object)this.optionVOLocal.get().getUndistributedProfitSubjectCode(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.undisSubjectEmpty"), (Object[])new Object[0]);
            this.lossGainTask.calculate(dataPool, queryParamsVO, this.optionVOLocal.get());
        }
        return dataPool;
    }

    private boolean allowDo(ConsolidatedTaskVO taskOption) {
        if (!taskOption.getEnableMinLossGainRecovery().booleanValue()) {
            return false;
        }
        DeferredIncomeTax diTax = this.optionVOLocal.get().getDiTax();
        if (null == diTax || com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)diTax.getAssetSubjects()) || org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)diTax.getZbCode())) {
            return false;
        }
        return !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)diTax.getMinorityEquitySubject()) && !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)diTax.getMinorityLossGainSubject());
    }

    @Override
    public boolean allowDeferredIncomeTaxByOption(ConsolidatedTaskVO taskOption) {
        if (!taskOption.getEnableLossGain().booleanValue() || !taskOption.getEnableDeferredIncomeTax().booleanValue()) {
            return false;
        }
        DeferredIncomeTax diTax = this.optionVOLocal.get().getDiTax();
        if (null == diTax || com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)diTax.getAssetSubjects()) || StringUtils.isEmpty((String)diTax.getZbCode())) {
            return false;
        }
        if (StringUtils.isEmpty((String)diTax.getPositiveDebitSubject()) || StringUtils.isEmpty((String)diTax.getPositiveCreditSubject())) {
            return false;
        }
        return !StringUtils.isEmpty((String)diTax.getNegativeDebitSubject()) && !StringUtils.isEmpty((String)diTax.getNegativeCreditSubject());
    }

    @Override
    public boolean allowDeferredIncomeTax(QueryParamsVO paramsVO) {
        ConsolidatedTaskVO taskOption = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (this.optionVOLocal.get() == null) {
            Assert.isNotNull((Object)paramsVO.getSystemId(), (String)"\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            ConsolidatedOptionVO optionVO = this.optionService.getOptionData(paramsVO.getSystemId());
            this.optionVOLocal.set(optionVO);
        }
        return this.allowDeferredIncomeTaxByOption(taskOption);
    }

    private EndCarryForwardResultVO cleanItemData(EndCarryForwardDataPoolVO dataPool, List<GcOffSetVchrItemAdjustEO> lastRecords, QueryParamsVO queryParamsVO) {
        LossGainOffsetVO lossGainOffsetVO = new LossGainOffsetVO();
        this.fillLastData(lossGainOffsetVO, lastRecords);
        lossGainOffsetVO.setCurrDeferredIncomeTaxResult(dataPool.getDeferredIncomeTaxGroup().getEndCarryForwarditem() == null ? new ArrayList() : dataPool.getDeferredIncomeTaxGroup().getEndCarryForwarditem());
        lossGainOffsetVO.setCurrMinorityRecoveryResult(dataPool.getMinorityRecoveryGroup().getEndCarryForwarditem() == null ? new ArrayList() : dataPool.getMinorityRecoveryGroup().getEndCarryForwarditem());
        lossGainOffsetVO.setCurrLossGainResult(dataPool.getLossGainGroup().getEndCarryForwarditem() == null ? new ArrayList() : dataPool.getLossGainGroup().getEndCarryForwarditem());
        lossGainOffsetVO.setOtherShowColumns(this.getManageAccColumns());
        this.setViewProps(lossGainOffsetVO, queryParamsVO);
        MinorityRecoveryTableVO minorityRecoveryTableVO = this.buildTableVO(dataPool);
        this.pageSort(lossGainOffsetVO);
        EndCarryForwardResultVO resultVO = new EndCarryForwardResultVO();
        resultVO.setMinorityRecoveryTableVO(minorityRecoveryTableVO);
        resultVO.setLossGainOffsetVO(lossGainOffsetVO);
        return resultVO;
    }

    private void pageSort(LossGainOffsetVO lossGainOffsetVO) {
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getLastDeferredIncomeTaxResult());
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getCurrDeferredIncomeTaxResult());
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getLastMinorityRecoveryResult());
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getCurrMinorityRecoveryResult());
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getLastLossGainResult());
        OffsetConvertUtil.setObjectRowSpanAndSort(lossGainOffsetVO.getCurrLossGainResult());
    }

    private MinorityRecoveryTableVO buildTableVO(EndCarryForwardDataPoolVO dataPool) {
        List deferredRowData = dataPool.getDeferredIncomeTaxGroup().getRowData();
        List minorityRecoveryRowData = dataPool.getMinorityRecoveryGroup().getRowData();
        List lossGainRowData = dataPool.getLossGainGroup().getRowData();
        ArrayList<MinorityRecoveryRowVO> downStream = new ArrayList<MinorityRecoveryRowVO>();
        ArrayList<MinorityRecoveryRowVO> againstStream = new ArrayList<MinorityRecoveryRowVO>();
        ArrayList<MinorityRecoveryRowVO> horizStream = new ArrayList<MinorityRecoveryRowVO>();
        for (MinorityRecoveryRowVO row : minorityRecoveryRowData) {
            if (row.getMinorityType() == 0) {
                downStream.add(row);
                continue;
            }
            if (row.getMinorityType() == 1) {
                againstStream.add(row);
                continue;
            }
            horizStream.add(row);
        }
        MinorityRecoveryTableVO minorityRecoveryTableVO = new MinorityRecoveryTableVO();
        minorityRecoveryTableVO.getDeferredIncomeTax().addAll(deferredRowData);
        minorityRecoveryTableVO.getDownStream().addAll(downStream);
        minorityRecoveryTableVO.getAgainstStream().addAll(againstStream);
        minorityRecoveryTableVO.getHorizStream().addAll(horizStream);
        minorityRecoveryTableVO.getLossGain().addAll(lossGainRowData);
        minorityRecoveryTableVO.sumTotalRow();
        IMinorityLossGainRecoveryTask recoveryTask = (IMinorityLossGainRecoveryTask)SpringContextUtils.getBean(IMinorityLossGainRecoveryTask.class);
        minorityRecoveryTableVO.setFractionDigits(Integer.valueOf(recoveryTask.getCompreEquityRatioFractionDigits()));
        return minorityRecoveryTableVO;
    }

    private void queryReclassifyData(QueryParamsVO queryParamsVO, EndCarryForwardResultVO resultVO) {
        queryParamsVO = this.simpleParam(queryParamsVO);
        queryParamsVO.getOtherShowColumns().add("SUBJECTORIENT");
        for (GcReclassifyTask task : this.reclassifyTasks) {
            task.queryReclassifyData(queryParamsVO, resultVO.getLossGainOffsetVO());
        }
    }

    private void fillLastData(LossGainOffsetVO lossGainOffsetVO, List<GcOffSetVchrItemAdjustEO> lastRecords) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        List managementAccountingFieldCodes = optionVO.getManagementAccountingFieldCodes();
        Collections.reverse(lastRecords);
        lastRecords.stream().forEach(item -> {
            GcOffSetVchrItemDTO gcOffSetVchrItemDTO = new GcOffSetVchrItemDTO();
            for (String key : managementAccountingFieldCodes) {
                gcOffSetVchrItemDTO.addFieldValue(key, item.getFieldValue(key));
            }
            BeanUtils.copyProperties(item, gcOffSetVchrItemDTO);
            Integer offsetSrcType = item.getOffSetSrcType();
            if (null == offsetSrcType) {
                return;
            }
            if (((Object)offsetSrcType).equals(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue())) {
                lossGainOffsetVO.getLastDeferredIncomeTaxResult().add(gcOffSetVchrItemDTO);
            } else if (((Object)offsetSrcType).equals(OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue())) {
                lossGainOffsetVO.getLastMinorityRecoveryResult().add(gcOffSetVchrItemDTO);
            } else {
                lossGainOffsetVO.getLastLossGainResult().add(gcOffSetVchrItemDTO);
            }
        });
    }

    private List<DesignFieldDefineVO> getManageAccColumns() {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        ArrayList<DesignFieldDefineVO> otherShowColumns = new ArrayList<DesignFieldDefineVO>();
        if (null == optionVO.getManagementAccountingFieldCodes()) {
            return otherShowColumns;
        }
        try {
            for (String code : optionVO.getManagementAccountingFieldCodes()) {
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode("GC_OFFSETVCHRITEM");
                ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), code);
                DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
                designFieldDefineVO.setKey(code);
                String localTitle = this.gcI18nHelper.getMessage(columnModelDefine.getID());
                designFieldDefineVO.setLabel(org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)localTitle) ? columnModelDefine.getTitle() : localTitle);
                designFieldDefineVO.setType(columnModelDefine.getColumnType());
                TableModelDefine refTable = dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                if (refTable != null) {
                    designFieldDefineVO.setDictTableName(refTable.getName());
                }
                otherShowColumns.add(designFieldDefineVO);
            }
        }
        catch (Exception e) {
            logger.warn("\u83b7\u53d6\u7ba1\u7406\u4f1a\u8ba1\u5b57\u6bb5\u5931\u8d25", e);
        }
        return otherShowColumns;
    }

    private EndCarryForwardDataPoolVO groupAndConvert(List<GcOffSetVchrItemAdjustEO> offsetVchrItems, QueryParamsVO queryParamsVO) {
        EndCarryForwardDataPoolVO dataPoolVO = new EndCarryForwardDataPoolVO();
        Map<String, List<GcOffSetVchrItemAdjustEO>> stringListMap = this.groupByRecords(offsetVchrItems, queryParamsVO);
        List<GcOffSetVchrItemAdjustEO> deferredIncomeTaxList = stringListMap.get(DEFFERED_INCOME_TAX);
        List<GcOffSetVchrItemAdjustEO> minorityLossGainRecoveryList = stringListMap.get(MINORITY_LOSS_GAIN_RECOVERY);
        List<GcOffSetVchrItemAdjustEO> lossGainList = stringListMap.get(LOSS_GAIN);
        dataPoolVO = this.formatStructure(deferredIncomeTaxList, minorityLossGainRecoveryList, lossGainList, queryParamsVO);
        return dataPoolVO;
    }

    private Map<String, List<GcOffSetVchrItemAdjustEO>> groupByRecords(List<GcOffSetVchrItemAdjustEO> offsetVchrItems, QueryParamsVO queryParamsVO) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        ArrayList deferredIncomeTaxList = new ArrayList();
        ArrayList minorityLossGainRecoveryList = new ArrayList();
        ArrayList lossGainList = new ArrayList();
        Map<String, List<GcOffSetVchrItemAdjustEO>> mrecid2OffsetMap = offsetVchrItems.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        HashSet deferredSubjectCodes = com.jiuqi.common.base.util.CollectionUtils.newHashSet((Collection)optionVO.getDiTax().getAssetSubjects());
        HashSet minoritySubjectCodes = com.jiuqi.common.base.util.CollectionUtils.newHashSet((Collection)optionVO.getDiTax().getLossGainRecoverySubjects());
        Set profitLossSubjectCodes = this.subjectService.listAllCodesByAttr(this.systemIdLocal.get(), SubjectAttributeEnum.getEnumByValue((Integer)SubjectAttributeEnum.PROFITLOSS.getValue()));
        mrecid2OffsetMap.forEach((mrecid, offSets) -> {
            boolean containsNonProfitLoss = offSets.stream().map(GcOffSetVchrItemAdjustEO::getSubjectCode).anyMatch(subjectCode -> !profitLossSubjectCodes.contains(subjectCode));
            if (containsNonProfitLoss) {
                IExpression diTaxExpression = GcFormulaUtils.getExpression((GcTaskBaseArguments)this.getTaskBaseArguments((GcOffSetVchrItemAdjustEO)offSets.get(0)), (String)optionVO.getDiTax().getDiTaxFilterFormula());
                IExpression lgRecoveryExpression = GcFormulaUtils.getExpression((GcTaskBaseArguments)this.getTaskBaseArguments((GcOffSetVchrItemAdjustEO)offSets.get(0)), (String)optionVO.getDiTax().getLgRecoveryFilterFormula());
                for (GcOffSetVchrItemAdjustEO offSet : offSets) {
                    GcTaskBaseArguments taskBaseArguments = this.getTaskBaseArguments(offSet);
                    if (profitLossSubjectCodes.contains(offSet.getSubjectCode())) {
                        lossGainList.add(offSet);
                        continue;
                    }
                    if (!deferredSubjectCodes.contains(offSet.getSubjectCode()) || !GcFormulaUtils.checkByExpression((IExpression)diTaxExpression, (GcTaskBaseArguments)taskBaseArguments, (DefaultTableEntity)offSet)) continue;
                    if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
                        deferredIncomeTaxList.add(offSet);
                    }
                    if (minoritySubjectCodes.contains(offSet.getSubjectCode()) && GcFormulaUtils.checkByExpression((IExpression)lgRecoveryExpression, (GcTaskBaseArguments)taskBaseArguments, (DefaultTableEntity)offSet)) {
                        minorityLossGainRecoveryList.add(offSet);
                        continue;
                    }
                    deferredIncomeTaxList.add(offSet);
                }
            }
        });
        HashMap<String, List<GcOffSetVchrItemAdjustEO>> resMap = new HashMap<String, List<GcOffSetVchrItemAdjustEO>>();
        resMap.put(DEFFERED_INCOME_TAX, deferredIncomeTaxList);
        resMap.put(MINORITY_LOSS_GAIN_RECOVERY, minorityLossGainRecoveryList);
        resMap.put(LOSS_GAIN, lossGainList);
        return resMap;
    }

    private EndCarryForwardDataPoolVO formatStructure(List<GcOffSetVchrItemAdjustEO> deferredIncomeTaxList, List<GcOffSetVchrItemAdjustEO> minorityLossGainRecoveryList, List<GcOffSetVchrItemAdjustEO> lossGainList, QueryParamsVO queryParamsVO) {
        Map<ArrayKey, BigDecimal> deferredMap = this.sumOffsetValueByManageAccDim(deferredIncomeTaxList, false);
        Map<ArrayKey, BigDecimal> minorityMap = this.sumOffsetValueByManageAccDim(minorityLossGainRecoveryList, false);
        Map<ArrayKey, BigDecimal> lossGainMap = this.sumOffsetValueByManageAccDim(lossGainList, true);
        List<MinorityRecoveryRowVO> deferredRowList = this.convert2row(deferredMap);
        List<MinorityRecoveryRowVO> minorityRecoveryRowList = this.convert2row(minorityMap);
        List<MinorityRecoveryRowVO> lossGainRowList = this.convert2row(lossGainMap);
        this.appendTitleInfo(deferredRowList, queryParamsVO);
        this.appendTitleInfo(minorityRecoveryRowList, queryParamsVO);
        this.appendTitleInfo(lossGainRowList, queryParamsVO);
        EndCarryForwardDataPoolVO dataPoolVO = new EndCarryForwardDataPoolVO();
        dataPoolVO.getDeferredIncomeTaxGroup().setRowData(deferredRowList);
        dataPoolVO.getMinorityRecoveryGroup().setRowData(minorityRecoveryRowList);
        dataPoolVO.getLossGainGroup().setRowData(lossGainRowList);
        return dataPoolVO;
    }

    private void appendTitleInfo(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap<String, String> unitCode2TitleCache = new HashMap<String, String>(64);
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            String unitTitle = OrgPeriodUtil.getUnitTitle(unitCode2TitleCache, rowVO.getUnitCode(), orgCenterService);
            rowVO.setUnitTitle(unitTitle);
            String oppUnitTitle = OrgPeriodUtil.getUnitTitle(unitCode2TitleCache, rowVO.getOppUnitCode(), orgCenterService);
            rowVO.setOppUnitTitle(oppUnitTitle);
            this.setSubjectTitle(this.systemIdLocal.get(), rowVO, subject2TitleCache);
        }
    }

    private void setSubjectTitle(String systemId, MinorityRecoveryRowVO rowVO, Map<String, String> subject2TitleCache) {
        List allSubjectEos;
        String subjectCode = rowVO.getSubjectCode();
        if (null == subjectCode) {
            return;
        }
        if (CollectionUtils.isEmpty(subject2TitleCache) && !CollectionUtils.isEmpty(allSubjectEos = this.subjectService.listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == subject2TitleCache.get(subjectCode)) {
            ConsolidatedSubjectEO subject = this.subjectService.getSubjectByCode(systemId, subjectCode);
            String title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        rowVO.setSubjectTitle(subject2TitleCache.get(subjectCode));
    }

    private Map<ArrayKey, BigDecimal> sumOffsetValueByManageAccDim(List<GcOffSetVchrItemAdjustEO> offsetItems, boolean isLossGain) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        ArrayList<String> manaAccFieldCodes = new ArrayList<String>(16);
        manaAccFieldCodes.add("OPPUNITID");
        manaAccFieldCodes.add("UNITID");
        manaAccFieldCodes.add("SUBJECTCODE");
        manaAccFieldCodes.addAll(optionVO.getManagementAccountingFieldCodes());
        HashMap<ArrayKey, BigDecimal> offsetValueMap = new HashMap<ArrayKey, BigDecimal>();
        HashSet assetSubjectCodes = com.jiuqi.common.base.util.CollectionUtils.newHashSet((Collection)optionVO.getDiTax().getAssetSubjects());
        for (GcOffSetVchrItemAdjustEO record : offsetItems) {
            ArrayKey key;
            if (isLossGain) {
                record.setSubjectCode(optionVO.getUndistributedProfitSubjectCode());
                key = this.getCombinedKey(manaAccFieldCodes, record);
                MapUtils.add(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetDebit()));
                MapUtils.sub(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetCredit()));
                continue;
            }
            if (!assetSubjectCodes.contains(record.getSubjectCode())) continue;
            key = this.getCombinedKey(manaAccFieldCodes, record);
            MapUtils.add(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetCredit()));
            MapUtils.sub(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetDebit()));
        }
        return offsetValueMap;
    }

    private List<MinorityRecoveryRowVO> convert2row(Map<ArrayKey, BigDecimal> combinedKey2offsetValueMap) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        ArrayList<MinorityRecoveryRowVO> minorityRecoveryRowList = new ArrayList<MinorityRecoveryRowVO>();
        List managementAccountingFieldCodes = optionVO.getManagementAccountingFieldCodes();
        for (Map.Entry<ArrayKey, BigDecimal> entry : combinedKey2offsetValueMap.entrySet()) {
            ArrayKey key = entry.getKey();
            BigDecimal offsetAmt = entry.getValue();
            if (BigDecimal.ZERO.compareTo(offsetAmt) == 0) continue;
            MinorityRecoveryRowVO minorityRecoveryRowVO = new MinorityRecoveryRowVO();
            int i = 0;
            minorityRecoveryRowVO.setOppUnitCode((String)key.get(i++));
            minorityRecoveryRowVO.setUnitCode((String)key.get(i++));
            minorityRecoveryRowVO.setSubjectCode((String)key.get(i++));
            int keySize = key.size();
            int beginIndex = i;
            while (i < keySize) {
                minorityRecoveryRowVO.addFieldValue((String)managementAccountingFieldCodes.get(i - beginIndex), (Object)this.getShowColumnDictTitle(key.get(i), (String)managementAccountingFieldCodes.get(i - beginIndex)));
                ++i;
            }
            minorityRecoveryRowVO.setOffsetAmt(offsetAmt);
            minorityRecoveryRowList.add(minorityRecoveryRowVO);
        }
        return minorityRecoveryRowList;
    }

    private String getShowColumnDictTitle(Object value, String otherShowColumn) {
        if (value == null || org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)value.toString())) {
            return "";
        }
        if ("SUBJECTORIENT".equals(otherShowColumn)) {
            return Integer.valueOf(value.toString()) == 1 ? "\u501f" : "\u8d37";
        }
        if ("EFFECTTYPE".equals(otherShowColumn)) {
            return EFFECTTYPE.getTitleByCode((String)value.toString());
        }
        Map<String, String> accountFieldCode2DictTableMap = this.accountFieldCode2DictTableMapLocal.get();
        String dictTableName = accountFieldCode2DictTableMap.get(otherShowColumn);
        if (dictTableName == null) {
            return value.toString();
        }
        String dictTitle = BaseDataUtils.getDictTitle(dictTableName, (String)value);
        if (!org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)dictTitle)) {
            return value + "|" + dictTitle;
        }
        return value.toString();
    }

    private ArrayKey getCombinedKey(List<String> manaAccFieldCodes, GcOffSetVchrItemAdjustEO record) {
        ArrayList<String> keys = new ArrayList<String>(16);
        for (String upperCode : manaAccFieldCodes) {
            keys.add(ConverterUtils.getAsString((Object)record.getFieldValue(upperCode), (String)""));
        }
        return new ArrayKey(keys);
    }

    private GcTaskBaseArguments getTaskBaseArguments(GcOffSetVchrItemAdjustEO offsetEO) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setPeriodStr(offsetEO.getDefaultPeriod());
        arguments.setCurrency(null == offsetEO.getOffSetCurr() ? "CNY" : offsetEO.getOffSetCurr());
        arguments.setOrgType(null == offsetEO.getOrgType() ? "MD_ORG_CORPORATE" : offsetEO.getOrgType());
        arguments.setOrgId(offsetEO.getUnitId());
        arguments.setTaskId(offsetEO.getTaskId());
        arguments.setSelectAdjustCode(offsetEO.getAdjust());
        return arguments;
    }

    private void initFieldValue(QueryParamsVO queryParamsVO) {
        String systemId = this.consolidatedTaskService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.systemIdLocal.set(systemId);
        Assert.isNotNull((Object)systemId, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"), (Object[])new Object[0]);
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(systemId);
        this.optionVOLocal.set(optionVO);
        this.initFieldCode2TableMap();
        queryParamsVO.getOtherShowColumns().addAll(optionVO.getManagementAccountingFieldCodes());
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            return;
        }
        this.initDiffUnitId(queryParamsVO);
    }

    public MinorityRecoveryParamsVO simpleParam(QueryParamsVO queryParamsVO) {
        MinorityRecoveryParamsVO newQueryParamsVO = new MinorityRecoveryParamsVO();
        newQueryParamsVO.setTaskId(queryParamsVO.getTaskId());
        newQueryParamsVO.setSchemeId(queryParamsVO.getSchemeId());
        newQueryParamsVO.setSystemId(queryParamsVO.getSystemId());
        newQueryParamsVO.setPeriodStr(queryParamsVO.getPeriodStr());
        newQueryParamsVO.setAcctYear(queryParamsVO.getAcctYear());
        newQueryParamsVO.setAcctPeriod(queryParamsVO.getAcctPeriod());
        newQueryParamsVO.setCurrency(queryParamsVO.getCurrency());
        newQueryParamsVO.setOrgId(queryParamsVO.getOrgId());
        newQueryParamsVO.setOrgIds(queryParamsVO.getOrgIds());
        newQueryParamsVO.setOrgType(queryParamsVO.getOrgType());
        newQueryParamsVO.setSelectAdjustCode(queryParamsVO.getSelectAdjustCode());
        newQueryParamsVO.setArbitrarilyMerge(queryParamsVO.getArbitrarilyMerge());
        return newQueryParamsVO;
    }

    private void initDiffUnitId(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = instance.getOrgByCode(queryParamsVO.getOrgId());
        String diffUnitId = hbOrg.getDiffUnitId();
        this.diffUnitIdLocal.set(diffUnitId);
    }

    private void initFieldCode2TableMap() {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        HashMap<String, String> accountFieldCode2DictTableMap = new HashMap<String, String>();
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)optionVO.getManagementAccountingFieldCodes())) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
                for (String column : optionVO.getManagementAccountingFieldCodes()) {
                    TableModelDefine tableModelDefine;
                    ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), column);
                    if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)columnModelDefine.getReferTableID()) || (tableModelDefine = dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID())) == null || org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)tableModelDefine.getCode())) continue;
                    accountFieldCode2DictTableMap.put(column, tableModelDefine.getName());
                }
                this.accountFieldCode2DictTableMapLocal.set(accountFieldCode2DictTableMap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setViewProps(LossGainOffsetVO lossGainOffsetVO, QueryParamsVO queryParamsVO) {
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.getLastLossGainResult());
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.currLossGainResult());
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.getLastMinorityRecoveryResult());
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.currMinorityRecoveryResult());
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.getLastDeferredIncomeTaxResult());
        this.setViewPropsDTO(queryParamsVO, lossGainOffsetVO.currDeferredIncomeTaxResult());
    }

    private void setViewPropsDTO(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemDTO> records) {
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty(records)) {
            Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns());
            YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            for (GcOffSetVchrItemDTO itemDTO : records) {
                Map record = itemDTO.getFields();
                this.setViewPropsRecord(queryParamsVO, fieldCode2DictTableMap, tool, record);
            }
        }
    }

    private void setViewPropsRecord(QueryParamsVO queryParamsVO, Map<String, String> fieldCode2DictTableMap, GcOrgCenterService tool, Map<String, Object> record) {
        ConsolidatedOptionVO optionVO = this.optionVOLocal.get();
        String systemId = this.systemIdLocal.get();
        HashMap unitId2TitleCache = new HashMap();
        HashMap subject2TitleCache = new HashMap();
        boolean showDictCode = "1".equals(optionVO.getShowDictCode());
        Integer orient = this.getOrient(record);
        record.put("ORIENT", orient);
        record.put("UNITTITLE", this.getUnitTitle((String)record.get("UNITID"), unitId2TitleCache, tool));
        record.put("OPPUNITTITLE", this.getUnitTitle((String)record.get("OPPUNITID"), unitId2TitleCache, tool));
        this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
        if (orient == OrientEnum.C.getValue()) {
            record.put("OFFSETDEBIT", "");
            Object value = record.get("OFFSETCREDIT");
            if (value instanceof Double) {
                record.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)((Double)value)));
            }
        } else {
            record.put("OFFSETCREDIT", "");
            Object value = record.get("OFFSETDEBIT");
            if (value instanceof Double) {
                record.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)((Double)value)));
            }
        }
        this.setOtherShowColumnDictTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
    }

    private Integer getOrient(Map<String, Object> record) {
        String offsetcredit;
        String offsetdebit;
        String string = offsetdebit = record.get("OFFSETDEBIT") == null ? null : String.valueOf(record.get("OFFSETDEBIT"));
        if (!org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)offsetdebit)) {
            try {
                double debitValue = Double.parseDouble(offsetdebit.replace(",", ""));
                if (debitValue != 0.0) {
                    return 1;
                }
            }
            catch (NumberFormatException e) {
                logger.warn("OFFSETDEBIT\u6570\u636e\u8f6c\u6362\u5931\u8d25: {}", (Object)offsetdebit, (Object)e);
            }
        }
        String string2 = offsetcredit = record.get("OFFSETCREDIT") == null ? null : String.valueOf(record.get("OFFSETCREDIT"));
        if (!org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)offsetcredit)) {
            try {
                double creditValue = Double.parseDouble(offsetcredit.replace(",", ""));
                if (creditValue != 0.0) {
                    return -1;
                }
            }
            catch (NumberFormatException e) {
                logger.warn("OFFSETCREDIT\u6570\u636e\u8f6c\u6362\u5931\u8d25: {}", (Object)offsetcredit, (Object)e);
            }
        }
        return (Integer)record.get("ORIENT");
    }

    private Map<String, List<String>> getIdMaps(Map<String, List<GcOffSetVchrItemAdjustEO>> stringListMap, MinorityRecoveryParamsVO queryParamsVO) {
        List<GcOffSetVchrItemAdjustEO> deferredIncomeTaxList = stringListMap.get(DEFFERED_INCOME_TAX);
        List<GcOffSetVchrItemAdjustEO> minorityLossGainRecoveryList = stringListMap.get(MINORITY_LOSS_GAIN_RECOVERY);
        List<GcOffSetVchrItemAdjustEO> lossGainList = stringListMap.get(LOSS_GAIN);
        HashMap<String, List<String>> idMaps = new HashMap<String, List<String>>();
        idMaps.put(DEFFERED_INCOME_TAX, deferredIncomeTaxList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        idMaps.put(MINORITY_LOSS_GAIN_RECOVERY, minorityLossGainRecoveryList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        idMaps.put(LOSS_GAIN, lossGainList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        List allIds = stringListMap.values().stream().flatMap(Collection::stream).map(DefaultTableEntity::getId).collect(Collectors.toList());
        idMaps.put(TOTAL_DATA, allIds);
        return idMaps;
    }

    private Set<String> filterPentrateOffsetDatas(MinorityRecoveryParamsVO queryParamsVO, List<Map<String, Object>> offsetDatas, Set<String> offsetIds, Set<String> srcOffsetIds, Map<String, List<String>> idMaps) {
        List subjectCodes = queryParamsVO.getSubjectCodes();
        List<Object> tempOffsetDatas = new ArrayList();
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)subjectCodes)) {
            if (queryParamsVO.getOffsetType() != null) {
                List<Object> ids = new ArrayList();
                switch (queryParamsVO.getOffsetType()) {
                    case 0: {
                        ids = idMaps.get(MINORITY_LOSS_GAIN_RECOVERY);
                        break;
                    }
                    case 1: {
                        ids = idMaps.get(DEFFERED_INCOME_TAX);
                        break;
                    }
                    case 2: {
                        ids = idMaps.get(LOSS_GAIN);
                    }
                }
                ArrayList finalIds = ids;
                tempOffsetDatas = offsetDatas.stream().filter(offset -> this.filterCurrRowDatas((Map<String, Object>)offset, queryParamsVO, finalIds)).collect(Collectors.toList());
            }
        } else {
            YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
            GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            String baseUnitCode = orgCenterService.getDeepestBaseUnitId(queryParamsVO.getOrgId());
            switch (queryParamsVO.getMinorityTotalType()) {
                case "allTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(TOTAL_DATA)).contains(offset.get("ID"))).collect(Collectors.toList());
                    break;
                }
                case "lossGainTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(LOSS_GAIN)).contains(offset.get("ID"))).collect(Collectors.toList());
                    break;
                }
                case "deferredIncomeTaxTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(DEFFERED_INCOME_TAX)).contains(offset.get("ID"))).collect(Collectors.toList());
                    break;
                }
                case "unrealizedGainLossTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(MINORITY_LOSS_GAIN_RECOVERY)).contains(offset.get("ID"))).collect(Collectors.toList());
                    break;
                }
                case "downStreamTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(MINORITY_LOSS_GAIN_RECOVERY)).contains(offset.get("ID")) && org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)baseUnitCode) && baseUnitCode.equals(offset.get("OPPUNITID")) && !baseUnitCode.equals(offset.get("UNITID"))).collect(Collectors.toList());
                    break;
                }
                case "againstStreamTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(MINORITY_LOSS_GAIN_RECOVERY)).contains(offset.get("ID")) && org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)baseUnitCode) && baseUnitCode.equals(offset.get("UNITID"))).collect(Collectors.toList());
                    break;
                }
                case "horizStreamTotal": {
                    tempOffsetDatas = offsetDatas.stream().filter(offset -> ((List)idMaps.get(MINORITY_LOSS_GAIN_RECOVERY)).contains(offset.get("ID")) && org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)baseUnitCode) || !baseUnitCode.equals(offset.get("OPPUNITID")) && !baseUnitCode.equals(offset.get("UNITID"))).collect(Collectors.toList());
                    break;
                }
            }
        }
        tempOffsetDatas = tempOffsetDatas.stream().filter(offset -> srcOffsetIds.contains(offset.get("ID"))).collect(Collectors.toList());
        offsetIds.addAll(tempOffsetDatas.stream().map(offset -> (String)offset.get("ID")).collect(Collectors.toSet()));
        return tempOffsetDatas.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
    }

    private boolean filterCurrRowDatas(Map<String, Object> offset, MinorityRecoveryParamsVO queryParamsVO, List<String> ids) {
        if (!ids.contains(offset.get("ID"))) {
            return false;
        }
        List subjectCodes = queryParamsVO.getSubjectCodes();
        if (!queryParamsVO.getLossGain().booleanValue() && subjectCodes != null && !subjectCodes.contains(offset.get("SUBJECTCODE"))) {
            return false;
        }
        List unitIdList = queryParamsVO.getUnitIdList();
        if (unitIdList != null && !unitIdList.contains(offset.get("UNITID"))) {
            return false;
        }
        List oppUnitIdList = queryParamsVO.getOppUnitIdList();
        if (oppUnitIdList != null && !oppUnitIdList.contains(offset.get("OPPUNITID"))) {
            return false;
        }
        Map offsetFilterMap = queryParamsVO.getOffsetFilterCondition();
        for (Map.Entry offsetEntry : offsetFilterMap.entrySet()) {
            String fieldValue;
            String fieldCode = ((String)offsetEntry.getKey()).toUpperCase();
            String offsetValue = offset.get(fieldCode) == null ? "" : offset.get(fieldCode);
            String string = fieldValue = offsetEntry.getValue() == null ? "" : offsetEntry.getValue();
            if (offsetValue.toString().contains(fieldValue.toString()) || fieldValue.toString().contains(offsetValue.toString())) continue;
            return false;
        }
        return true;
    }

    public void pageOffsetByMrecids(int pageNum, int pageSize, Set<String> mRecids) {
        if (pageNum > 0 && pageSize > 0) {
            List tempmRecids = mRecids.stream().sorted(Comparator.comparing(mRecid -> mRecid)).collect(Collectors.toList());
            int mRecidsSize = tempmRecids.size();
            int begin = (pageNum - 1) * pageSize <= mRecidsSize ? (pageNum - 1) * pageSize : 0;
            int range = pageNum * pageSize > mRecidsSize ? mRecidsSize : pageNum * pageSize;
            tempmRecids = tempmRecids.subList(begin, range);
            mRecids.clear();
            mRecids.addAll(tempmRecids);
        }
    }
}

