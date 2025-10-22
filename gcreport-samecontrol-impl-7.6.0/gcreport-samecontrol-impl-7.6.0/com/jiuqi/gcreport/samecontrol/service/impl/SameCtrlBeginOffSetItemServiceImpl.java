/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlPeriodDataTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSettingTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
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
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlPeriodDataTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSettingTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlBeginOffSetItemService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlCalcInvestRuleService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
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

@Service(value="sameCtrlBeginOffSetItemServiceImpl")
public class SameCtrlBeginOffSetItemServiceImpl
extends SameCtrlOffSetItemServiceImpl
implements SameCtrlBeginOffSetItemService {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlBeginOffSetItemServiceImpl.class);
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private SameCtrlChgSettingService sameCtrlChgSettingService;
    @Autowired
    private SameCtrlCalcInvestRuleService sameCtrlCalcInvestRuleService;
    @Autowired
    private SameCtrlExtractLogService sameCtrlExtractLogService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public Map<String, Double> calcBeginSubjectCode2Offset(String defaultPeriod, String orgType, GcOrgCacheVO currMergeOrgCacheVO) {
        List<SameCtrlOffSetItemEO> plusList = this.sameCtrlOffSetItemDao.listBeginInputUnitParentsOffset(defaultPeriod, orgType, currMergeOrgCacheVO);
        List<SameCtrlOffSetItemEO> minusList = this.sameCtrlOffSetItemDao.listBeginSameParentUnitOffset(defaultPeriod, orgType, currMergeOrgCacheVO.getCode());
        return this.calcDiff(plusList, minusList);
    }

    @Override
    public Map<String, Double> calcBeginSubjectCode2OffsetLimitYear(String defaultPeriod, String orgType, GcOrgCacheVO currMergeOrgCacheVO) {
        List<SameCtrlOffSetItemEO> plusList = this.sameCtrlOffSetItemDao.listBeginInputUnitParentsOffsetLimitYear(defaultPeriod, orgType, currMergeOrgCacheVO);
        List<SameCtrlOffSetItemEO> minusList = this.sameCtrlOffSetItemDao.listBeginSameParentUnitOffsetLimitYear(defaultPeriod, orgType, currMergeOrgCacheVO.getCode());
        return this.calcDiff(plusList, minusList);
    }

    private Map<String, Double> calcDiff(List<SameCtrlOffSetItemEO> plusList, List<SameCtrlOffSetItemEO> minusList) {
        HashMap<String, Double> subjectCode2OffsetValueMap = new HashMap<String, Double>(128);
        for (SameCtrlOffSetItemEO item : plusList) {
            MapUtils.add(subjectCode2OffsetValueMap, (Object)item.getSubjectCode(), (Double)item.getOffSetDebit());
            MapUtils.sub(subjectCode2OffsetValueMap, (Object)item.getSubjectCode(), (Double)item.getOffSetCredit());
        }
        for (SameCtrlOffSetItemEO item : minusList) {
            MapUtils.sub(subjectCode2OffsetValueMap, (Object)item.getSubjectCode(), (Double)item.getOffSetDebit());
            MapUtils.add(subjectCode2OffsetValueMap, (Object)item.getSubjectCode(), (Double)item.getOffSetCredit());
        }
        return subjectCode2OffsetValueMap;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void extractData(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlExtractLogVO sameCtrlExtractLog = this.addSameCtrlExtractLogVO(sameCtrlChgEnvContext, SameCtrlExtractOperateEnum.CHANGEDPARENT_EXTRACT);
        try {
            SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
            GcOrgCenterService orgTool = this.getGcOrgTool(cond);
            SameCtrlChgOrgEO sameCtrlChgOrgEO = this.getSameCtrlChgOrg(cond, orgTool);
            if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType())) {
                SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO = this.sameCtrlChgSettingService.getOptionData(cond.getTaskId(), cond.getSchemeId(), cond.getSystemId());
                this.checkSameCtrlSettingOption(sameCtrlSettingOptionVO);
                Set<String> mergeUnitCodeSet = this.getMergeUnitCodeSet(cond, orgTool);
                for (String mergeUnitCode : mergeUnitCodeSet) {
                    if (ChangedOrgTypeEnum.NON_SAME_CTRL_NEW.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType()) || ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlChgOrgEO.getChangedOrgType()) && mergeUnitCode.equals(cond.getSameParentCode())) continue;
                    cond.setMergeUnitCode(mergeUnitCode);
                    this.deleteSameCtrlOffset(cond);
                    String mergeUnitTitle = this.getMergeUnitTitle(cond);
                    this.extractSameCtrlOffSetOfBeginAsset(cond, sameCtrlSettingOptionVO, sameCtrlChgOrgEO, sameCtrlChgEnvContext, mergeUnitTitle);
                    this.extractSameCtrlOffSetOfProfitlossAndCash(cond, sameCtrlSettingOptionVO, sameCtrlChgOrgEO, sameCtrlChgEnvContext, mergeUnitTitle);
                    this.generateSameCtrlOffSetOfInvest(cond, sameCtrlChgOrgEO, sameCtrlChgEnvContext, mergeUnitTitle);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u6536\u8d2d\u65b9\u63d0\u53d6\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage(), e);
            sameCtrlChgEnvContext.setSuccessFlag(false);
            sameCtrlExtractLog.setInfo("\u5904\u7f6e\u65b9\u63d0\u53d6\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage());
        }
        this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
    }

    private Set<String> getMergeUnitCodeSet(SameCtrlOffsetCond cond, GcOrgCenterService orgTool) {
        Set<String> mergeUnitCodeSet = new HashSet<String>();
        if (cond.isExtractAllParentsUnitFlag()) {
            List<GcOrgCacheVO> gcOrgCacheVOS = this.listUnitPaths(cond);
            mergeUnitCodeSet = gcOrgCacheVOS.stream().map(item -> item.getCode()).collect(Collectors.toSet());
        } else {
            mergeUnitCodeSet.add(cond.getMergeUnitCode());
        }
        return mergeUnitCodeSet;
    }

    private void deleteSameCtrlOffset(SameCtrlOffsetCond cond) {
        this.sameCtrlOffSetItemDao.deleteByCondition(cond, Arrays.asList(SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode(), SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode(), SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode()));
    }

    private void generateSameCtrlOffSetOfInvest(SameCtrlOffsetCond cond, SameCtrlChgOrgEO sameCtrlChgOrgEO, SameCtrlChgEnvContext sameCtrlChgEnvContext, String mergeUnitTitle) {
        sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u671f\u521d\u6295\u8d44\u62b5\u9500\u5206\u5f55\u63d0\u53d6", cond.getMergeUnitCode(), mergeUnitTitle));
        if (StringUtils.isEmpty((String)cond.getMergeUnitCode())) {
            return;
        }
        if (DateUtils.getYearOfDate((Date)sameCtrlChgOrgEO.getChangeDate()) != Integer.parseInt(cond.getPeriodStr().substring(0, 4))) {
            return;
        }
        if (sameCtrlChgOrgEO == null) {
            return;
        }
        if (!cond.getMergeUnitCode().equals(sameCtrlChgOrgEO.getChangedParentCode())) {
            return;
        }
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO firstSameParentOrg = tool.getCommonUnit(tool.getOrgByCode(cond.getChangedUnitCode()), tool.getOrgByCode(sameCtrlChgOrgEO.getVirtualParentCode()));
        if (firstSameParentOrg == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5904\u7f6e\u5355\u4f4d\u548c\u53d8\u52a8\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7");
        }
        if (cond.getMergeUnitCode().equals(firstSameParentOrg.getCode())) {
            return;
        }
        List<GcOffSetVchrItemAdjustEO> adjustList = this.sameCtrlCalcInvestRuleService.generateGcOffSetVchrItemAdjust(cond, sameCtrlChgOrgEO);
        if (CollectionUtils.isEmpty(adjustList)) {
            return;
        }
        this.convertAndSaveSameCtrlOffset(adjustList, cond, SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode(), cond.getSchemeId());
        HashSet mrecids = new HashSet();
        adjustList.forEach(item -> mrecids.add(item.getmRecid()));
        sameCtrlChgEnvContext.addResultItem(String.format("\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), mergeUnitTitle, mrecids.size()));
    }

    private void extractSameCtrlOffSetOfBeginAsset(SameCtrlOffsetCond cond, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO, SameCtrlChgOrgEO sameCtrlChgOrgEO, SameCtrlChgEnvContext sameCtrlChgEnvContext, String mergeUnitTitle) {
        sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u671f\u521d\u8d44\u4ea7\u8d1f\u503a\u62b5\u9500\u5206\u5f55\u63d0\u53d6", cond.getMergeUnitCode(), mergeUnitTitle));
        if (!this.isExtract(sameCtrlChgOrgEO.getChangeDate(), sameCtrlChgOrgEO.getDisposalDate(), cond.getPeriodStr())) {
            logger.info("\u671f\u521d\u8d44\u4ea7\u8d1f\u503a\u62b5\u9500\u5206\u5f55\u4e0d\u6ee1\u8db3\u63d0\u53d6\u6761\u4ef6: \u53d8\u52a8\u65e5\u671f\u6216\u8005\u5904\u7f6e\u65e5\u671f\u672a\u8bbe\u7f6e");
            return;
        }
        SameCtrlQueryParamsVO queryParamsVO = this.getQueryParams(cond);
        Map<String, String> subjectCodeOfLastYear2CurrYear = this.getSameCtrlSettingParams(queryParamsVO, sameCtrlSettingOptionVO, cond.getPeriodStr(), SameCtrlSettingTypeEnum.MONTH.getCode());
        String undistributedProfitSubjectCode = sameCtrlSettingOptionVO.getUndividendProfitSubject().getCode();
        queryParamsVO.setRules(SameCtrlManageUtil.getInvestRuleIdListByType(queryParamsVO.getSchemeId(), queryParamsVO.getSameCtrlPeriodStr(), false));
        Set<String> assetAndDebtSubjectCodeSet = this.getSubjectCode(cond.getSchemeId(), cond.getPeriodStr(), new ArrayKey(new Object[]{SubjectAttributeEnum.ASSET.getValue(), SubjectAttributeEnum.DEBT.getValue()}));
        Set<String> profitLossSubjectCodeSet = this.getSubjectCode(cond.getSchemeId(), cond.getPeriodStr(), new ArrayKey(new Object[]{SubjectAttributeEnum.PROFITLOSS.getValue()}));
        HashSet mrecids = new HashSet();
        queryParamsVO.setSameCtrl(true);
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        this.offsetCoreService.fillMrecids(queryParamsDTO, null, mrecids);
        List gcOffSetVchrItemAdjustEOS = this.offsetCoreService.listWithFullGroupByMrecids(mrecids);
        HashSet mrecidsOfAssetAndDebt = new HashSet();
        gcOffSetVchrItemAdjustEOS.forEach(item -> {
            if (assetAndDebtSubjectCodeSet.contains(item.getSubjectCode())) {
                mrecidsOfAssetAndDebt.add(item.getmRecid());
            }
        });
        List<GcOffSetVchrItemAdjustEO> matchOffset = gcOffSetVchrItemAdjustEOS.stream().filter(item -> {
            if (mrecidsOfAssetAndDebt.contains(item.getmRecid())) {
                if (assetAndDebtSubjectCodeSet.contains(item.getSubjectCode())) {
                    return true;
                }
                if (profitLossSubjectCodeSet.contains(item.getSubjectCode())) {
                    item.setSubjectCode(undistributedProfitSubjectCode);
                } else if (subjectCodeOfLastYear2CurrYear.containsKey(item.getSubjectCode())) {
                    item.setSubjectCode((String)subjectCodeOfLastYear2CurrYear.get(item.getSubjectCode()));
                }
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        this.convertAndSaveSameCtrlOffset(matchOffset, cond, SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode(), queryParamsVO.getSchemeId());
        sameCtrlChgEnvContext.addResultItem(String.format("\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), mergeUnitTitle, mrecidsOfAssetAndDebt.size()));
        logger.info(" \u671f\u521d\u62b5\u9500\uff1a\u8d44\u4ea7\u8d1f\u503a\u7c7b\u79d1\u76ee\u62b5\u9500\u5206\u5f55: \u5171\u63d0\u53d6[{}]\u7ec4", (Object)mrecidsOfAssetAndDebt.size());
    }

    private void extractSameCtrlOffSetOfProfitlossAndCash(SameCtrlOffsetCond cond, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO, SameCtrlChgOrgEO sameCtrlChgOrgEO, SameCtrlChgEnvContext sameCtrlChgEnvContext, String mergeUnitTitle) {
        sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u4e0a\u5e74\u540c\u671f\u5229\u6da6\u73b0\u91d1\u6d41\u91cf\u8c03\u6574\u62b5\u9500\u5206\u5f55\u63d0\u53d6", cond.getMergeUnitCode(), mergeUnitTitle));
        SameCtrlQueryParamsVO queryParamsVO = this.getQueryParams(cond);
        int disposalYear = DateUtils.getYearOfDate((Date)sameCtrlChgOrgEO.getDisposalDate());
        int disposalMonth = DateUtils.getDateFieldValue((Date)sameCtrlChgOrgEO.getDisposalDate(), (int)2);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)cond.getPeriodStr());
        int extractYear = yearPeriodUtil.getYear();
        Integer extractMonth = yearPeriodUtil.getPeriod();
        if (extractYear == disposalYear || extractYear + 1 == disposalYear && extractMonth < disposalMonth) {
            Map<String, String> subjectCodeOfLastYear2CurrYear = this.getSameCtrlSettingParams(queryParamsVO, sameCtrlSettingOptionVO, cond.getPeriodStr(), SameCtrlSettingTypeEnum.YEAR.getCode());
            queryParamsVO.setSameCtrlPeriodStr(Integer.valueOf(cond.getPeriodStr().substring(0, 4)) - 1 + cond.getPeriodStr().substring(4));
            queryParamsVO.setSameCtrl(true);
            HashSet mrecids = new HashSet();
            QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
            BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
            this.offsetCoreService.fillMrecids(queryParamsDTO, null, mrecids);
            List gcOffSetVchrItemAdjustEOS = this.offsetCoreService.listWithFullGroupByMrecids(mrecids);
            String netProfitSubjectCode = sameCtrlSettingOptionVO.getNetProfitSubject().getCode();
            Set<String> profitLossAndCashSubjectCodeSet = this.getSubjectCode(cond.getSchemeId(), cond.getPeriodStr(), new ArrayKey(new Object[]{SubjectAttributeEnum.PROFITLOSS.getValue(), SubjectAttributeEnum.CASH.getValue()}));
            Set<String> assetAndDebtSubjectCodeSet = this.getSubjectCode(cond.getSchemeId(), cond.getPeriodStr(), new ArrayKey(new Object[]{SubjectAttributeEnum.ASSET.getValue(), SubjectAttributeEnum.DEBT.getValue()}));
            HashSet mrecidsOfProfitLossAndCash = new HashSet();
            gcOffSetVchrItemAdjustEOS.forEach(item -> {
                if (profitLossAndCashSubjectCodeSet.contains(item.getSubjectCode())) {
                    mrecidsOfProfitLossAndCash.add(item.getmRecid());
                }
            });
            List<GcOffSetVchrItemAdjustEO> matchOffset = gcOffSetVchrItemAdjustEOS.stream().filter(item -> {
                if (mrecidsOfProfitLossAndCash.contains(item.getmRecid())) {
                    if (profitLossAndCashSubjectCodeSet.contains(item.getSubjectCode())) {
                        return true;
                    }
                    if (assetAndDebtSubjectCodeSet.contains(item.getSubjectCode())) {
                        item.setSubjectCode(netProfitSubjectCode);
                    } else if (subjectCodeOfLastYear2CurrYear.containsKey(item.getSubjectCode())) {
                        item.setSubjectCode((String)subjectCodeOfLastYear2CurrYear.get(item.getSubjectCode()));
                    } else {
                        item.setSubjectCode(netProfitSubjectCode);
                    }
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            this.convertAndSaveSameCtrlOffset(matchOffset, cond, SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode(), queryParamsVO.getSchemeId());
            sameCtrlChgEnvContext.addResultItem(String.format("\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), mergeUnitTitle, mrecidsOfProfitLossAndCash.size()));
            logger.info("\u671f\u521d\u62b5\u9500\uff1a\u4e0a\u5e74\u540c\u671f\u5229\u6da6\u73b0\u91d1\u6d41\u91cf\u7c7b: \u5171\u63d0\u53d6[{}]\u7ec4", (Object)mrecidsOfProfitLossAndCash.size());
        } else {
            int mrecidNum = this.extractFromSameCtrlOffset(cond);
            sameCtrlChgEnvContext.addResultItem(String.format("\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", cond.getMergeUnitCode(), mergeUnitTitle, mrecidNum));
        }
    }

    private int extractFromSameCtrlOffset(SameCtrlOffsetCond cond) {
        String[] columnNamesInDB = new String[]{"defaultPeriod", "sameCtrlSrcType", "inputUnitCode"};
        Object[] values = new Object[]{cond.getPeriodStr(), SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode(), cond.getMergeUnitCode()};
        List<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = this.sameCtrlOffSetItemDao.queryOffsetRecordsByWhere(columnNamesInDB, values);
        Map<String, List<SameCtrlOffSetItemEO>> mecid2SameCtrlOffsetItemMap = sameCtrlOffSetItemEOS.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> newSameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        for (Map.Entry<String, List<SameCtrlOffSetItemEO>> entry : mecid2SameCtrlOffsetItemMap.entrySet()) {
            List<SameCtrlOffSetItemEO> sameCtrlItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderUtils.newUUIDStr();
            for (SameCtrlOffSetItemEO item : sameCtrlItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties((Object)item, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode());
                sameCtrlOffSetItemEO.setSrcId(item.getId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                newSameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
        }
        this.sameCtrlOffSetItemDao.addBatch(sameCtrlOffSetItemEOS);
        return mecid2SameCtrlOffsetItemMap.keySet().size();
    }

    private boolean isExtract(Date changeDate, Date disposalDate, String periodStr) {
        int changeYear = DateUtils.getYearOfDate((Date)changeDate);
        Integer changeMonth = DateUtils.getDateFieldValue((Date)changeDate, (int)2);
        int disposalYear = DateUtils.getYearOfDate((Date)disposalDate);
        int disposalMonth = DateUtils.getDateFieldValue((Date)disposalDate, (int)2);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        int extractYear = yearPeriodUtil.getYear();
        Integer extractMonth = yearPeriodUtil.getPeriod();
        switch (yearPeriodUtil.getType()) {
            case 2: {
                extractMonth = extractMonth * 6;
                break;
            }
            case 3: {
                extractMonth = extractMonth * 3;
                break;
            }
            case 1: {
                extractMonth = 12;
                break;
            }
        }
        if (extractYear == disposalYear) {
            if (changeMonth == 1 && extractMonth == 1) {
                return false;
            }
            if (extractMonth < changeMonth) {
                return false;
            }
        }
        return extractYear <= disposalYear || extractMonth <= disposalMonth;
    }

    private Map<String, String> getSameCtrlSettingParams(SameCtrlQueryParamsVO queryParamsVO, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO, String periodStr, String type) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        int extractMonth = yearPeriodUtil.getPeriod();
        int extractYear = yearPeriodUtil.getYear();
        HashMap<String, String> subjectCodeOfLastYear2CurrYear = new HashMap<String, String>(16);
        List taskAndSchemeMappings = sameCtrlSettingOptionVO.getTaskAndSchemeMappings();
        List taskAndSchemeMappingList = taskAndSchemeMappings.stream().filter(item -> item.getDataType().equals(type)).collect(Collectors.toList());
        TaskAndSchemeMapping tempTaskAndSchemeMapping = null;
        for (TaskAndSchemeMapping item2 : taskAndSchemeMappingList) {
            int fromDateYear = DateUtils.getDateFieldValue((Date)item2.getFromDate(), (int)1);
            int toDateYear = DateUtils.getDateFieldValue((Date)item2.getFromDate(), (int)1);
            if (fromDateYear < extractYear || extractYear > toDateYear) continue;
            int fromDate = DateUtils.getDateFieldValue((Date)item2.getFromDate(), (int)2);
            int toDate = DateUtils.getDateFieldValue((Date)item2.getToDate(), (int)2);
            if (fromDate > extractMonth || extractMonth > toDate) continue;
            queryParamsVO.setTaskId(item2.getLastYearTaskId());
            queryParamsVO.setSchemeId(item2.getLastYearSchemeId());
            if (SameCtrlSettingTypeEnum.MONTH.getCode().equals(type)) {
                tempTaskAndSchemeMapping = item2;
            }
            this.setSubjectCodeOfLastYear2CurrYear(item2.getSubjectMappings(), subjectCodeOfLastYear2CurrYear);
            break;
        }
        if (SameCtrlSettingTypeEnum.MONTH.getCode().equals(type)) {
            try {
                FormSchemeDefine schemeDefine = this.iRunTimeViewController.getFormScheme(queryParamsVO.getSchemeId());
                PeriodType periodType = schemeDefine.getPeriodType();
                String name = periodType.name();
                if (SameCtrlPeriodDataTypeEnum.MONTH.name().equals(name) || "DEFAULT".equals(name)) {
                    queryParamsVO.setSameCtrlPeriodStr(yearPeriodUtil.getYear() - 1 + "Y0012");
                }
                if (SameCtrlPeriodDataTypeEnum.YEAR.name().equals(periodType.name())) {
                    queryParamsVO.setSameCtrlPeriodStr(yearPeriodUtil.getYear() - 1 + "N0001");
                }
                if (SameCtrlPeriodDataTypeEnum.HALF_YEAR.name().equals(periodType.name())) {
                    queryParamsVO.setSameCtrlPeriodStr(yearPeriodUtil.getYear() - 1 + "H0002");
                }
                if (SameCtrlPeriodDataTypeEnum.SEASON.name().equals(periodType.name())) {
                    queryParamsVO.setSameCtrlPeriodStr(yearPeriodUtil.getYear() - 1 + "J0004");
                }
            }
            catch (Exception e) {
                logger.error("\u540c\u63a7\u8bbe\u7f6e\u53c2\u6570\uff1a\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u65f6\u671f\u7c7b\u578b\u51fa\u73b0\u9519\u8bef", (Object)e.getMessage());
            }
        }
        return subjectCodeOfLastYear2CurrYear;
    }

    private void setSubjectCodeOfLastYear2CurrYear(List<SameCtrlChagSubjectMapVO> subjectMappings, Map<String, String> subjectCodeOfLastYear2CurrYear) {
        subjectMappings.forEach(sameCtrlChagSubjectMapVO -> {
            String currYearSubjectCode = sameCtrlChagSubjectMapVO.getCurrYearSubject().getCode();
            sameCtrlChagSubjectMapVO.getLastYearSubjects().forEach(item -> subjectCodeOfLastYear2CurrYear.put(item.getCode(), currYearSubjectCode));
        });
    }

    private SameCtrlQueryParamsVO getQueryParams(SameCtrlOffsetCond cond) {
        SameCtrlQueryParamsVO queryParamsVO = new SameCtrlQueryParamsVO();
        BeanUtils.copyProperties(cond, queryParamsVO);
        queryParamsVO.setOrgId(cond.getMergeUnitCode());
        queryParamsVO.setUnitIdList(cond.getChangedUnitCodeChilds());
        queryParamsVO.setOrgType(cond.getOrgType());
        return queryParamsVO;
    }

    private Set<String> getSubjectCode(String schemeId, String periodStr, ArrayKey arrayKey) {
        String reportSystemId = this.consolidatedTaskService.getSystemIdBySchemeId(schemeId, periodStr);
        List subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(reportSystemId);
        HashSet<String> subjectCodeSet = new HashSet<String>();
        subjectEOS.forEach(item -> {
            if (arrayKey.getValues().contains(item.getAttri())) {
                subjectCodeSet.add(item.getCode());
            }
        });
        return subjectCodeSet;
    }

    private void checkSameCtrlSettingOption(SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO) {
        SameCtrlChagSettingBaseDataVO netProfitSubject = sameCtrlSettingOptionVO.getNetProfitSubject();
        SameCtrlChagSettingBaseDataVO undividendProfitSubject = sameCtrlSettingOptionVO.getUndividendProfitSubject();
        if (netProfitSubject == null || StringUtils.isEmpty((String)netProfitSubject.getCode())) {
            throw new RuntimeException("\u8bf7\u5728\u540c\u63a7\u8bbe\u7f6e\u9009\u9879\u4e2d \u9009\u62e9\u51c0\u5229\u6da6\u79d1\u76ee\uff01");
        }
        if (undividendProfitSubject == null || StringUtils.isEmpty((String)undividendProfitSubject.getCode())) {
            throw new RuntimeException("\u8bf7\u5728\u540c\u63a7\u8bbe\u7f6e\u9009\u9879\u4e2d \u9009\u62e9\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee\uff01");
        }
    }

    private String getMergeUnitTitle(SameCtrlOffsetCond cond) {
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(cond.getMergeUnitCode());
        return orgCacheVO == null ? "" : orgCacheVO.getTitle();
    }
}

