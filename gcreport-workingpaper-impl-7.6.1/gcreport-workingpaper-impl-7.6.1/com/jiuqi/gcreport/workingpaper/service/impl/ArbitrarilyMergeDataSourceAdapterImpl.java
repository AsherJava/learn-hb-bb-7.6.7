/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.EndCarryForwardDataSourceAdapter
 *  com.jiuqi.gcreport.offsetitem.task.LossGainTaskImpl
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.EndCarryForwardDataSourceAdapter;
import com.jiuqi.gcreport.offsetitem.task.LossGainTaskImpl;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgTemporaryBatchDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.querytask.impl.ArbitrarilyMergeQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeOffSetItemAdjustService;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArbitrarilyMergeDataSourceAdapterImpl
implements EndCarryForwardDataSourceAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArbitrarilyMergeDataSourceAdapterImpl.class);
    @Autowired
    private ArbitrarilyMergeOffSetItemAdjustService amOffSetItemAdjustService;
    @Autowired
    private ArbitrarilyMergeQueryTaskImpl arbitrarilyMergeQueryTask;
    @Autowired
    private ArbitrarilyMergeOrgTemporaryBatchDao orgTemporaryBatchDao;
    @Autowired
    private ConsolidatedOptionCacheService optionCacheService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    public String getName() {
        return "ArbitrarilyMergeApp";
    }

    public boolean match(QueryParamsVO queryParamsVO) {
        return Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge());
    }

    @Transactional(rollbackFor={Exception.class})
    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO queryParamsDTO) {
        WorkingPaperQueryCondition condition = this.convertParam2Condition(queryParamsDTO);
        if (Boolean.TRUE.equals(queryParamsDTO.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary(condition, orgTypeMap);
        }
        if (StringUtils.isEmpty((String)queryParamsDTO.getSystemId())) {
            Assert.isNotNull((Object)queryParamsDTO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String systemId = this.consolidatedTaskService.getSystemIdBySchemeId(queryParamsDTO.getSchemeId(), queryParamsDTO.getPeriodStr());
            queryParamsDTO.setSystemId(systemId);
        }
        Assert.isNotNull((Object)queryParamsDTO.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        queryParamsDTO.setOrgComSupLength(condition.getOrgComSupLength());
        queryParamsDTO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsDTO.setOrgIds(condition.getOrgIds());
        List<GcOffSetVchrItemAdjustEO> items = this.amOffSetItemAdjustService.listWithOnlyItems(queryParamsDTO);
        List<ArbitrarilyMergeOffSetVchrItemAdjustEO> ryItems = this.amOffSetItemAdjustService.listWithOnlyItemsByRy(queryParamsDTO);
        items.addAll(this.convertRyItem2ItemEO(ryItems));
        if (Boolean.TRUE.equals(queryParamsDTO.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)queryParamsDTO.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(queryParamsDTO.getOrgBatchId());
        }
        return items;
    }

    @Transactional(rollbackFor={Exception.class})
    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO queryParamsDTO) {
        WorkingPaperQueryCondition condition = this.convertParam2Condition(queryParamsDTO);
        if (Boolean.TRUE.equals(queryParamsDTO.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary(condition, orgTypeMap);
        }
        if (StringUtils.isEmpty((String)queryParamsDTO.getSystemId())) {
            Assert.isNotNull((Object)queryParamsDTO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String systemId = this.consolidatedTaskService.getSystemIdBySchemeId(queryParamsDTO.getSchemeId(), queryParamsDTO.getPeriodStr());
            queryParamsDTO.setSystemId(systemId);
        }
        Assert.isNotNull((Object)queryParamsDTO.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        queryParamsDTO.setOrgComSupLength(condition.getOrgComSupLength());
        queryParamsDTO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsDTO.setOrgIds(condition.getOrgIds());
        PaginationDto<GcOffSetVchrItemAdjustEO> paginationDto = this.amOffSetItemAdjustService.listWithFullGroup(queryParamsDTO);
        if (Boolean.TRUE.equals(queryParamsDTO.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)queryParamsDTO.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(queryParamsDTO.getOrgBatchId());
        }
        return paginationDto;
    }

    public Set<String> deleteOffsetEntrys(QueryParamsVO queryParamsVO) {
        WorkingPaperQueryCondition condition = this.convertParam2Condition(this.convertQueryVO2DTO(queryParamsVO));
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary(condition, orgTypeMap);
            queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
            queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
            queryParamsVO.setOrgIds(condition.getOrgIds());
        }
        Set<String> mrecids = this.amOffSetItemAdjustService.deleteOffsetEntrys(queryParamsVO);
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)queryParamsVO.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(queryParamsVO.getOrgBatchId());
        }
        return mrecids;
    }

    public void deleteByOffsetGroupIdsAndSrcType(Set<String> mrecids, int srcTypeValue, QueryParamsVO queryParamsVO) {
        WorkingPaperQueryCondition condition = this.convertParam2Condition(this.convertQueryVO2DTO(queryParamsVO));
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            HashMap<String, String> orgTypeMap = new HashMap<String, String>();
            this.arbitrarilyMergeQueryTask.handleSelectUnitToTemporary(condition, orgTypeMap);
            queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
            queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
            queryParamsVO.setOrgIds(condition.getOrgIds());
        }
        this.amOffSetItemAdjustService.deleteByOffsetGroupIdsAndSrcType(mrecids, srcTypeValue, (GcTaskBaseArguments)queryParamsVO);
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) && !StringUtils.isEmpty((String)queryParamsVO.getOrgBatchId())) {
            this.orgTemporaryBatchDao.deleteAllOrgTemporaryData(queryParamsVO.getOrgBatchId());
        }
    }

    public Boolean saveEndCarryForward(QueryParamsVO queryParamsVO, LossGainOffsetVO lossGainOffsetVO) {
        List currLossGainResult = lossGainOffsetVO.currLossGainResult();
        List currDeferredIncomeTaxResult = lossGainOffsetVO.currDeferredIncomeTaxResult();
        List currMinorityRecoveryResult = lossGainOffsetVO.currMinorityRecoveryResult();
        List currReclassifyResultMap = lossGainOffsetVO.getCurrReclassifyResult();
        List<GcOffSetVchrItemDTO> currReclassifyResult = this.convertOffsetMap2DTO(currReclassifyResultMap);
        List currReduceReclassifyResultMap = lossGainOffsetVO.getCurrReduceReclassifyResult();
        List<GcOffSetVchrItemDTO> currReduceReclassifyResult = this.convertOffsetMap2DTO(currReduceReclassifyResultMap);
        LossGainTaskImpl lossGainTask = (LossGainTaskImpl)SpringContextUtils.getBean(LossGainTaskImpl.class);
        queryParamsVO = LossGainTaskImpl.lossGainSimpleParam((QueryParamsVO)queryParamsVO);
        String systemId = this.consolidatedTaskService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        Assert.isNotNull((Object)systemId, (String)"\u672a\u627e\u89c1\u4f53\u7cfb", (Object[])new Object[0]);
        ConsolidatedOptionVO optionVO = this.optionCacheService.getConOptionBySystemId(systemId);
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.SUBJECT_RECLASSIFY.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.SUBJECT_REDUCE_RECLASSIFY.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        Set<String> mrecids = this.deleteOffsetEntrys(queryParamsVO);
        this.deleteByOffsetGroupIdsAndSrcType(mrecids, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue(), queryParamsVO);
        int lossGainCount = this.saveOffsetVchrItems(currLossGainResult, optionVO);
        int deferredIncomeTaxCount = this.saveOffsetVchrItems(currDeferredIncomeTaxResult, optionVO);
        int minorityRecoveryCount = this.saveOffsetVchrItems(currMinorityRecoveryResult, optionVO);
        int currReclassifyResultNum = this.saveOffsetVchrItems(currReclassifyResult, optionVO);
        int currReduceReclassifyResultNum = this.saveOffsetVchrItems(currReduceReclassifyResult, optionVO);
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(queryParamsVO.getPeriodStr()));
        String taskTitle = GcOffsetItemUtils.getTaskTitle((String)queryParamsVO.getTaskId());
        String logTitle = String.format("\u4efb\u610f\u5408\u5e76-\u671f\u672b\u7ed3\u8f6c-\u4efb\u52a1%1$s-\u65f6\u671f%2$s", taskTitle, periodTitle);
        String userName = NpContextHolder.getContext().getUser().getName();
        String logs = String.format("\u7ed3\u8f6c\u635f\u76ca:%1$d\u7ec4;\n\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f:%2$d\u7ec4;\n\u9012\u5ef6\u6240\u5f97\u7a0e:%3$d\u7ec4;\n\u4f59\u989d\u91cd\u5206\u7c7b:%4$d\u7ec4;\n\u62b5\u51cf\u91cd\u5206\u7c7b:%5$d\u7ec4;\n\u4efb\u52a1%6$s;\u65f6\u671f%7$s;\u64cd\u4f5c\u4eba:%8$s;", lossGainCount, minorityRecoveryCount, deferredIncomeTaxCount, currReclassifyResultNum, currReduceReclassifyResultNum, taskTitle, periodTitle, userName);
        LogHelper.info((String)"\u5408\u5e76-\u4efb\u610f\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)logTitle, (String)logs);
        LOGGER.info("\u4efb\u610f\u5408\u5e76-\u671f\u672b\u7ed3\u8f6c\u4fdd\u5b58\u62b5\u9500\u5206\u5f55\u660e\u7ec6\uff1a\n {}", (Object)logs);
        return true;
    }

    private List<GcOffSetVchrItemDTO> convertOffsetMap2DTO(List<Map<String, Object>> list) {
        ArrayList<GcOffSetVchrItemDTO> result = new ArrayList<GcOffSetVchrItemDTO>();
        list.forEach(item -> {
            item.replace("ORIENT", item.get("ORIENT").equals(1) ? OrientEnum.D : OrientEnum.C);
            item.replace("SUBJECTORIENT", item.get("SUBJECTORIENT").equals(1) ? OrientEnum.D : OrientEnum.C);
            item.replace("OFFSETDEBIT", ConverterUtils.getAsDouble(item.get("OFFSETDEBIT")));
            item.replace("OFFSETCREDIT", ConverterUtils.getAsDouble(item.get("OFFSETCREDIT")));
            GcOffSetVchrItemDTO gcOffSetVchrItemDTO = (GcOffSetVchrItemDTO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)item), GcOffSetVchrItemDTO.class);
            result.add(gcOffSetVchrItemDTO);
        });
        return result;
    }

    private int saveOffsetVchrItems(List<GcOffSetVchrItemDTO> vchrItemDTOS, ConsolidatedOptionVO optionVO) {
        int offsetEntryCount = 0;
        if (!CollectionUtils.isEmpty(vchrItemDTOS)) {
            Map<String, List<GcOffSetVchrItemDTO>> groups = vchrItemDTOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemDTO::getmRecid));
            for (List<GcOffSetVchrItemDTO> oneGroup : groups.values()) {
                GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO();
                offSetVchrDTO.setItems(oneGroup);
                offSetVchrDTO.setMrecid(oneGroup.get(0).getmRecid());
                this.amOffSetItemAdjustService.save(offSetVchrDTO);
                ++offsetEntryCount;
            }
        }
        return offsetEntryCount;
    }

    private WorkingPaperQueryCondition convertParam2Condition(QueryParamsDTO queryParamsDTO) {
        WorkingPaperQueryCondition condition = new WorkingPaperQueryCondition();
        BeanUtils.copyProperties(queryParamsDTO, condition);
        return condition;
    }

    private QueryParamsDTO convertQueryVO2DTO(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        return queryDTO;
    }

    private List<GcOffSetVchrItemAdjustEO> convertRyItem2ItemEO(List<ArbitrarilyMergeOffSetVchrItemAdjustEO> ryItems) {
        ArrayList<GcOffSetVchrItemAdjustEO> result = new ArrayList<GcOffSetVchrItemAdjustEO>();
        if (CollectionUtils.isEmpty(ryItems)) {
            return result;
        }
        ryItems.forEach(item -> {
            GcOffSetVchrItemAdjustEO eo = new GcOffSetVchrItemAdjustEO();
            BeanUtils.copyProperties(item, eo);
            eo.setOrgType(item.getUnitVersion());
            eo.setOffSetCredit(ConverterUtils.getAsDouble(item.getFields().get("OFFSET_CREDIT_" + item.getOffSetCurr())));
            eo.setOffSetDebit(ConverterUtils.getAsDouble(item.getFields().get("OFFSET_DEBIT_" + item.getOffSetCurr())));
            result.add(eo);
        });
        return result;
    }
}

