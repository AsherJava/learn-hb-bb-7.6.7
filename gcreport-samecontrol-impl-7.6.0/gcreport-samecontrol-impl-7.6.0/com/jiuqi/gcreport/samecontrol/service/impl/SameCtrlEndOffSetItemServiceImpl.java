/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao
 *  com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlEndOffSetItemService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

@Service(value="sameCtrlEndOffSetItemServiceImpl")
public class SameCtrlEndOffSetItemServiceImpl
extends SameCtrlOffSetItemServiceImpl
implements SameCtrlEndOffSetItemService {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlEndOffSetItemServiceImpl.class);
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;
    @Autowired
    private SameCtrlChgSettingService sameCtrlChgSettingService;
    @Autowired
    private SameCtrlExtractLogService sameCtrlExtractLogService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;

    @Override
    public List<SameCtrlOffSetItemEO> rewriteDisposeParentOffset(GcActionParamsVO paramsVO, String currMergeUnitCode) {
        return this.sameCtrlOffSetItemDao.rewriteDisposeParentOffset(paramsVO, currMergeUnitCode);
    }

    @Override
    public List<SameCtrlOffSetItemEO> rewriteSameParentUnitOffset(GcActionParamsVO paramsVO, String currMergeUnitCode) {
        return this.sameCtrlOffSetItemDao.rewriteDisposeSameParentUnitOffset(paramsVO, currMergeUnitCode);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void extractData(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlExtractLogVO sameCtrlExtractLog = this.addSameCtrlExtractLogVO(sameCtrlChgEnvContext, SameCtrlExtractOperateEnum.VIRTUALPARENT_EXTRACT);
        try {
            SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
            GcOrgCenterService orgTool = this.getGcOrgTool(cond);
            String netProfitSubjectCode = this.getNetProfitSubjectCode(cond.getTaskId(), cond.getSchemeId(), cond.getSystemId());
            SameCtrlChgOrgEO sameCtrlChgOrgEO = this.getSameCtrlChgOrg(cond, orgTool);
            Map<String, Object> extractInfo = this.getExtractInfo(sameCtrlChgOrgEO.getChangeDate(), sameCtrlChgOrgEO.getDisposalDate(), cond.getPeriodStr());
            if (!Boolean.TRUE.equals(extractInfo.get("isExtract"))) {
                return;
            }
            this.deleteSameCtrlOffset(cond);
            int changeMonth = (Integer)extractInfo.get("changeMonth");
            int extractMonth = (Integer)extractInfo.get("extractMonth");
            if (changeMonth == extractMonth) {
                if (cond.isExtractAllParentsUnitFlag()) {
                    List<GcOrgCacheVO> gcOrgCacheVOS = this.listUnitPaths(cond);
                    for (GcOrgCacheVO item : gcOrgCacheVOS) {
                        if (ChangedOrgTypeEnum.NON_SAME_CTRL_NEW.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType()) || ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType()) && item.getCode().equals(cond.getSameParentCode())) continue;
                        cond.setMergeUnitCode(item.getCode());
                        this.extractEndOffset(extractMonth, cond, sameCtrlChgOrgEO, item, sameCtrlChgEnvContext, netProfitSubjectCode);
                    }
                } else {
                    GcOrgCacheVO mergeUnitVO = orgTool.getOrgByCode(cond.getMergeUnitCode());
                    this.extractEndOffset(extractMonth, cond, sameCtrlChgOrgEO, mergeUnitVO, sameCtrlChgEnvContext, netProfitSubjectCode);
                }
            } else {
                this.extractLastMonthSameCtrlOffSet(sameCtrlChgEnvContext, cond, orgTool, sameCtrlChgOrgEO);
            }
        }
        catch (Exception e) {
            logger.error("\u5904\u7f6e\u65b9\u63d0\u53d6\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage(), e);
            sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.ERROR);
            sameCtrlExtractLog.setInfo("\u5904\u7f6e\u65b9\u63d0\u53d6\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage());
        }
        this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
    }

    private void extractEndOffset(int extractMonth, SameCtrlOffsetCond cond, SameCtrlChgOrgEO sameCtrlChgOrgEO, GcOrgCacheVO mergeUnitVO, SameCtrlChgEnvContext sameCtrlChgEnvContext, String netProfitSubjectCode) {
        int mrecidNum;
        sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u671f\u672b\u62b5\u9500\u5206\u5f55\u63d0\u53d6", cond.getMergeUnitCode(), mergeUnitVO.getTitle()));
        if (extractMonth == 1) {
            mrecidNum = this.extractOffsetInit(cond, mergeUnitVO, sameCtrlChgOrgEO);
        } else {
            Set<String> entryIds = this.extractInputAdjustOffset(cond, sameCtrlChgOrgEO);
            mrecidNum = entryIds.size();
            mrecidNum += this.extractOffSet(cond, netProfitSubjectCode, entryIds);
        }
        sameCtrlChgEnvContext.addResultItem(String.format("\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), mergeUnitVO.getTitle(), mrecidNum));
    }

    private Set<String> extractInputAdjustOffset(SameCtrlOffsetCond cond, SameCtrlChgOrgEO sameCtrlChgOrgEO) {
        Map<String, String> ruleId2TitleMap = this.getRuleId2TitleMap(cond.getSchemeId(), cond.getPeriodStr());
        String systemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(cond.getSchemeId(), cond.getPeriodStr());
        OffsetItemInitQueryParamsVO queryParamsVO = this.getQueryParams(cond);
        queryParamsVO.setRules(this.getInvestAndFvchRuleIds(systemId));
        queryParamsVO.setEffectTypes(Arrays.asList(EFFECTTYPE.YEAR.getCode(), EFFECTTYPE.LONGTERM.getCode()));
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        List gcOffSetItemEOS = this.offsetCoreService.listEOWithFullGroup(queryParamsDTO).getContent();
        Map<String, List<GcOffSetVchrItemAdjustEO>> mecid2OffsetItemMap = gcOffSetItemEOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        for (Map.Entry<String, List<GcOffSetVchrItemAdjustEO>> entry : mecid2OffsetItemMap.entrySet()) {
            List<GcOffSetVchrItemAdjustEO> offsetItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderUtils.newUUIDStr();
            double sumOffsetValue = 0.0;
            for (GcOffSetVchrItemAdjustEO gcOffSetItemEO : offsetItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties(gcOffSetItemEO, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setUnitCode(gcOffSetItemEO.getUnitId());
                sameCtrlOffSetItemEO.setOppUnitCode(gcOffSetItemEO.getOppUnitId());
                sameCtrlOffSetItemEO.setInputUnitCode(cond.getMergeUnitCode());
                YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
                GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(cond.getMergeUnitCode());
                sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
                sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
                sameCtrlOffSetItemEO.setChangedParentCode(sameCtrlChgOrgEO.getChangedParentCode());
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)cond.getDisposalDate()));
                sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_EXTRACT.getCode());
                sameCtrlOffSetItemEO.setOrgType(cond.getOrgType());
                sameCtrlOffSetItemEO.setRuleTitle(StringUtils.isEmpty((String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId())) ? gcOffSetItemEO.getRuleId() : ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()));
                sameCtrlOffSetItemEO.setSrcId(gcOffSetItemEO.getId());
                sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
                sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
            Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        this.sameCtrlOffSetItemDao.addBatch(sameCtrlOffSetItemEOS);
        return mecid2OffsetItemMap.keySet();
    }

    private int extractOffsetInit(SameCtrlOffsetCond cond, GcOrgCacheVO mergeUnitVO, SameCtrlChgOrgEO sameCtrlChgOrgEO) {
        OffsetItemInitQueryParamsVO queryParamVo = this.getQueryParams(cond);
        Map<String, String> ruleId2TitleMap = this.getRuleId2TitleMap(cond.getSchemeId(), cond.getPeriodStr());
        String systemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(cond.getSchemeId(), cond.getPeriodStr());
        queryParamVo.setRules(this.getInvestAndFvchRuleIds(systemId));
        queryParamVo.setTaskId(null);
        queryParamVo.setPeriodStr(null);
        queryParamVo.setSystemId(systemId);
        queryParamVo.setAcctYear(Integer.valueOf(cond.getPeriodStr().substring(0, 4)));
        Pagination page = this.offSetVchrItemInitDao.queryOffsetingEntryEO(queryParamVo);
        Map<String, List<GcOffSetVchrItemInitEO>> mecid2OffsetInitItemMap = page.getContent().stream().collect(Collectors.groupingBy(GcOffSetVchrItemInitEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        for (Map.Entry<String, List<GcOffSetVchrItemInitEO>> entry : mecid2OffsetInitItemMap.entrySet()) {
            List<GcOffSetVchrItemInitEO> offSetInitItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderUtils.newUUIDStr();
            for (GcOffSetVchrItemInitEO item : offSetInitItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties(item, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_EXTRACT.getCode());
                sameCtrlOffSetItemEO.setSrcId(item.getId());
                sameCtrlOffSetItemEO.setUnitCode(item.getUnitId());
                sameCtrlOffSetItemEO.setOppUnitCode(item.getOppUnitId());
                sameCtrlOffSetItemEO.setInputUnitCode(cond.getMergeUnitCode());
                sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVO.getParentStr());
                sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
                sameCtrlOffSetItemEO.setChangedParentCode(sameCtrlChgOrgEO.getChangedParentCode());
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)cond.getDisposalDate()));
                sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
                sameCtrlOffSetItemEO.setOrgType(cond.getOrgType());
                sameCtrlOffSetItemEO.setRuleTitle(StringUtils.isEmpty((String)ruleId2TitleMap.get(item.getRuleId())) ? item.getRuleId() : ruleId2TitleMap.get(item.getRuleId()));
                sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
                sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
        }
        this.sameCtrlOffSetItemDao.addBatch(sameCtrlOffSetItemEOS);
        return mecid2OffsetInitItemMap.keySet().size();
    }

    private void extractLastMonthSameCtrlOffSet(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlOffsetCond cond, GcOrgCenterService orgTool, SameCtrlChgOrgEO sameCtrlChgOrgEO) {
        if (cond.isExtractAllParentsUnitFlag()) {
            List<GcOrgCacheVO> gcOrgCacheVOS = this.listUnitPaths(cond);
            gcOrgCacheVOS.forEach(item -> {
                if (ChangedOrgTypeEnum.NON_SAME_CTRL_DISPOSE.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType()) || !item.getCode().equals(cond.getSameParentCode())) {
                    sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u671f\u672b\u62b5\u9500\u5206\u5f55\u63d0\u53d6", item.getCode(), item.getTitle()));
                    cond.setMergeUnitCode(item.getCode());
                    int mrecidNum = this.extractSameCtrlOffSet(cond);
                    sameCtrlChgEnvContext.addResultItem(String.format("\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", item.getCode(), item.getTitle(), mrecidNum));
                }
            });
        } else {
            GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(cond.getMergeUnitCode());
            sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u671f\u672b\u62b5\u9500\u5206\u5f55\u63d0\u53d6", cond.getMergeUnitCode(), orgCacheVO.getTitle()));
            int mrecidNum = this.extractSameCtrlOffSet(cond);
            sameCtrlChgEnvContext.addResultItem(String.format("\u5904\u7f6e\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), orgCacheVO.getTitle(), mrecidNum));
        }
    }

    private int extractOffSet(SameCtrlOffsetCond cond, String netProfitSubjectCode, Set<String> entryIds) {
        OffsetItemInitQueryParamsVO queryParamsVO = this.getQueryParams(cond);
        queryParamsVO.setRules(SameCtrlManageUtil.getInvestRuleIdListByType(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr(), false));
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        List gcOffSetItemEOS = this.offsetCoreService.listEOWithFullGroup(queryParamsDTO).getContent();
        Set<String> profitLossAndCashSubjectCodeSet = this.getSubjectCode(cond.getSchemeId(), cond.getPeriodStr());
        HashSet mrecidsOfProfitLossAndCash = new HashSet();
        gcOffSetItemEOS.forEach(item -> {
            if (profitLossAndCashSubjectCodeSet.contains(item.getSubjectCode())) {
                mrecidsOfProfitLossAndCash.add(item.getmRecid());
            }
        });
        List<GcOffSetVchrItemAdjustEO> matchOffset = gcOffSetItemEOS.stream().filter(item -> {
            if (mrecidsOfProfitLossAndCash.contains(item.getmRecid())) {
                if (entryIds.contains(item.getmRecid())) {
                    return false;
                }
                if (!profitLossAndCashSubjectCodeSet.contains(item.getSubjectCode())) {
                    item.setSubjectCode(netProfitSubjectCode);
                }
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        this.convertAndSaveSameCtrlOffset(matchOffset, cond, SameCtrlSrcTypeEnum.END_EXTRACT.getCode(), cond.getSchemeId());
        logger.info(" \u671f\u672b\u62b5\u9500\uff1a\u5171\u63d0\u53d6[{}]\u7ec4", (Object)mrecidsOfProfitLossAndCash.size());
        return mrecidsOfProfitLossAndCash.size();
    }

    private int extractSameCtrlOffSet(SameCtrlOffsetCond cond) {
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        String periodStr = cond.getPeriodStr();
        cond.setPeriodStr(this.getLastPeriodStr(cond.getPeriodStr()));
        HashSet<String> mRecids = new HashSet<String>();
        this.sameCtrlOffSetItemDao.queryMrecidsByInputUnit(cond, mRecids, Arrays.asList(SameCtrlSrcTypeEnum.END_EXTRACT.getCode()), Arrays.asList(cond.getMergeUnitCode()));
        List<SameCtrlOffSetItemEO> sameCtrlOffSetItems = this.sameCtrlOffSetItemDao.listSameCtrlOffsets(mRecids);
        Map<String, List<SameCtrlOffSetItemEO>> mecid2SameCtrlOffsetItemMap = sameCtrlOffSetItems.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemEO::getmRecid));
        for (Map.Entry<String, List<SameCtrlOffSetItemEO>> entry : mecid2SameCtrlOffsetItemMap.entrySet()) {
            List<SameCtrlOffSetItemEO> sameCtrlItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderUtils.newUUIDStr();
            for (SameCtrlOffSetItemEO item : sameCtrlItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties((Object)item, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setDefaultPeriod(periodStr);
                sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_EXTRACT.getCode());
                sameCtrlOffSetItemEO.setSrcId(item.getId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
        }
        this.sameCtrlOffSetItemDao.addBatch(sameCtrlOffSetItemEOS);
        return mRecids.size();
    }

    private Map<String, Object> getExtractInfo(Date changeDate, Date disposalDate, String periodStr) {
        HashMap<String, Object> extractInfo = new HashMap<String, Object>();
        Integer changeMonth = DateUtils.getDateFieldValue((Date)changeDate, (int)2);
        int disposalYear = DateUtils.getYearOfDate((Date)disposalDate);
        int disposalMonth = DateUtils.getDateFieldValue((Date)disposalDate, (int)2);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        int extractYear = yearPeriodUtil.getYear();
        Integer extractPeriod = yearPeriodUtil.getPeriod();
        switch (yearPeriodUtil.getType()) {
            case 2: {
                extractPeriod = extractPeriod * 6;
                break;
            }
            case 3: {
                extractPeriod = extractPeriod * 3;
                break;
            }
            case 1: {
                extractPeriod = 12;
                break;
            }
        }
        extractInfo.put("isExtract", true);
        if (extractYear == disposalYear && extractPeriod < changeMonth) {
            extractInfo.put("isExtract", false);
        }
        if (extractYear > disposalYear && extractPeriod > disposalMonth) {
            extractInfo.put("isExtract", false);
        }
        extractInfo.put("extractMonth", extractPeriod);
        extractInfo.put("changeMonth", changeMonth);
        return extractInfo;
    }

    private OffsetItemInitQueryParamsVO getQueryParams(SameCtrlOffsetCond cond) {
        OffsetItemInitQueryParamsVO queryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(cond, queryParamsVO);
        queryParamsVO.setOrgId(cond.getMergeUnitCode());
        queryParamsVO.setUnitIdList(cond.getChangedUnitCodeChilds());
        queryParamsVO.setOrgType(cond.getOrgType());
        ConsolidatedOptionVO conOption = this.consolidatedOptionService.getOptionData(cond.getSystemId());
        String sameCtrlExtractPeriodOffset = conOption.getSameCtrlExtractPeriodOffset();
        if (StringUtils.isEmpty((String)sameCtrlExtractPeriodOffset) || "-1".equals(sameCtrlExtractPeriodOffset)) {
            queryParamsVO.setPeriodStr(this.getLastPeriodStr(cond.getPeriodStr()));
        }
        queryParamsVO.setPageSize(0);
        return queryParamsVO;
    }

    private String getNetProfitSubjectCode(String taskId, String schemeId, String systemId) {
        SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO = this.sameCtrlChgSettingService.getOptionData(taskId, schemeId, systemId);
        String netProfitSubjectCode = sameCtrlSettingOptionVO.getNetProfitSubject().getCode();
        if (StringUtils.isEmpty((String)netProfitSubjectCode)) {
            throw new RuntimeException("\u8bf7\u5728\u540c\u63a7\u8bbe\u7f6e\u4e2d \u9009\u62e9\u51c0\u5229\u6da6\u79d1\u76ee\uff01");
        }
        return netProfitSubjectCode;
    }

    private String getLastPeriodStr(String periodStr) {
        int month = Integer.valueOf(periodStr.substring(periodStr.length() - 2)) - 1;
        String monthStr = month > 9 ? String.valueOf(month) : "0" + month;
        return periodStr.substring(0, periodStr.length() - 2) + monthStr;
    }

    private Set<String> getSubjectCode(String schemeId, String periodStr) {
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(schemeId, periodStr);
        List subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(reportSystemId);
        HashSet<String> subjectCodeSet = new HashSet<String>();
        subjectEOS.forEach(item -> {
            if (item.getAttri().intValue() == SubjectAttributeEnum.PROFITLOSS.getValue() || item.getAttri().intValue() == SubjectAttributeEnum.CASH.getValue()) {
                subjectCodeSet.add(item.getCode());
            }
        });
        return subjectCodeSet;
    }

    private void deleteSameCtrlOffset(SameCtrlOffsetCond cond) {
        this.sameCtrlOffSetItemDao.deleteByCondition(cond, Arrays.asList(SameCtrlSrcTypeEnum.END_EXTRACT.getCode()));
    }

    private List<String> getInvestAndFvchRuleIds(String systemId) {
        List<String> ruleTypes = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        List ruleIds = this.unionRuleService.findAllRuleIdsBySystemIdAndRuleTypes(systemId, ruleTypes);
        return ruleIds;
    }

    private Map<String, String> getRuleId2TitleMap(String schemeId, String periodStr) {
        HashMap<String, String> ruleId2TitleMap = new HashMap<String, String>();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(schemeId, periodStr, null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getTitle()));
        return ruleId2TitleMap;
    }
}

