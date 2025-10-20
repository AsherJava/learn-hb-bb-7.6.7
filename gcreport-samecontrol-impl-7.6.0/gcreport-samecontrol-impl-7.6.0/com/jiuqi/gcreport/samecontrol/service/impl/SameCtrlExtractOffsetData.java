/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
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
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlPeriodDataTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSettingTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
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
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlPeriodDataTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSettingTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

@Service
public class SameCtrlExtractOffsetData
extends SameCtrlOffSetItemServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlExtractOffsetData.class);
    @Autowired
    private SameCtrlChgSettingService sameCtrlChgSettingService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOffSetItemAdjustCoreService gcOffSetItemAdjustCoreService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;

    public void sameCtrlExtractOffset(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlQueryParamsVO sameCtrlQueryParamsVO) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO = this.sameCtrlChgSettingService.getOptionData(sameCtrlOffsetCond.getTaskId(), sameCtrlOffsetCond.getSchemeId(), sameCtrlOffsetCond.getSystemId());
        this.deleteSameCtrlOffset(sameCtrlOffsetCond, sameCtrlExtractManageCond.getSameCtrlSrcTypeEnum());
        List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList = this.queryOffsetByParam(sameCtrlChgEnvContext, sameCtrlQueryParamsVO, sameCtrlSettingOptionVO);
        GcOrgCacheVO virtualCodeVO = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
        String result = String.format("\u5f00\u59cb\u6267\u884c\u865a\u62df\u5355\u4f4d\uff1a%1s | %2s \u540c\u63a7\u63d0\u53d6\u62b5\u9500\u5206\u5f55\u63d0\u53d6", sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
        sameCtrlChgEnvContext.addResultItem(result);
        logger.info(result);
        int count = this.saveSameCtrlExtractData(gcOffSetVchrItemAdjustEOList, sameCtrlChgEnvContext, sameCtrlQueryParamsVO);
        String resultEnd = String.format("\u6536\u8d2d\u65b9\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u540c\u63a7\u63d0\u53d6\u62b5\u9500\u5206\u5f55", sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode(), count);
        sameCtrlChgEnvContext.addResultItem(resultEnd);
        logger.info(resultEnd);
    }

    private List<GcOffSetVchrItemAdjustEO> queryOffsetByParam(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlQueryParamsVO sameCtrlQueryParamsVO, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO) {
        HashSet<String> subjectCodeSet = new HashSet<String>();
        Map<String, String> subjectCodeOfLastYear2CurrYear = this.setOffsetItemParams(sameCtrlChgEnvContext, sameCtrlQueryParamsVO, sameCtrlSettingOptionVO, subjectCodeSet);
        HashSet mrecids = new HashSet();
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(sameCtrlQueryParamsVO, queryParamsDTO);
        queryParamsDTO.setPeriodStr(sameCtrlQueryParamsVO.getSameCtrlPeriodStr());
        queryParamsDTO.setSystemId(null);
        this.gcOffSetItemAdjustCoreService.fillMrecids(queryParamsDTO, null, mrecids);
        List gcOffSetVchrItemAdjustEOS = this.gcOffSetItemAdjustCoreService.listWithFullGroupByMrecids(mrecids);
        logger.info("\u5171\u8bc6\u522b\u5230\u3010" + mrecids.size() + "\u3011\u7ec4\u8c03\u6574\u62b5\u9500\u5206\u5f55\u6570\u636e");
        HashSet mrecidsOfAssetAndDebt = new HashSet();
        gcOffSetVchrItemAdjustEOS.forEach(item -> {
            if (subjectCodeSet.contains(item.getSubjectCode())) {
                mrecidsOfAssetAndDebt.add(item.getmRecid());
            }
            if (subjectCodeOfLastYear2CurrYear.containsKey(item.getSubjectCode())) {
                item.setSubjectCode((String)subjectCodeOfLastYear2CurrYear.get(item.getSubjectCode()));
            }
        });
        List<GcOffSetVchrItemAdjustEO> filterSubjectItemList = gcOffSetVchrItemAdjustEOS.stream().filter(item -> mrecidsOfAssetAndDebt.contains(item.getmRecid())).collect(Collectors.toList());
        return this.filterCommonCodeByUnitAndOppUnit(sameCtrlChgEnvContext, filterSubjectItemList);
    }

    private List<GcOffSetVchrItemAdjustEO> filterCommonCodeByUnitAndOppUnit(SameCtrlChgEnvContext sameCtrlChgEnvContext, List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        String virtualParentCode = sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlChgOrgEO().getVirtualParentCode();
        YearPeriodObject yp = new YearPeriodObject(sameCtrlOffsetCond.getSchemeId(), sameCtrlOffsetCond.getPeriodStr());
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)sameCtrlOffsetCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        return gcOffSetVchrItemAdjustEOList.stream().filter(eo -> {
            if (!(StringUtils.isEmpty((String)virtualParentCode) || StringUtils.isEmpty((String)eo.getUnitId()) || StringUtils.isEmpty((String)eo.getOppUnitId()))) {
                GcOrgCacheVO commonCode = gcOrgCenterService.getCommonUnit(gcOrgCenterService.getOrgByCode(eo.getUnitId()), gcOrgCenterService.getOrgByCode(eo.getOppUnitId()));
                return commonCode != null && virtualParentCode.equals(commonCode.getCode());
            }
            return false;
        }).collect(Collectors.toList());
    }

    private Map<String, String> setOffsetItemParams(SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlQueryParamsVO sameCtrlQueryParamsVO, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO, Set<String> assetAndDebtSubjectCodeSet) {
        SameCtrlSrcTypeEnum sameCtrlSrcTypeEnum = sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlSrcTypeEnum();
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        HashMap<String, String> subjectCodeOfLastYear2CurrYear = new HashMap<String, String>(16);
        if (SameCtrlSrcTypeEnum.ACQUIRER_BEFORE_EXTRACT.getCode().equals(sameCtrlSrcTypeEnum.getCode()) || SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEFORE_EXTRACT.getCode().equals(sameCtrlSrcTypeEnum.getCode())) {
            subjectCodeOfLastYear2CurrYear.putAll(this.setSameCtrlSettingParams(sameCtrlQueryParamsVO, sameCtrlSettingOptionVO, sameCtrlOffsetCond, SameCtrlSettingTypeEnum.YEAR.getCode()));
            assetAndDebtSubjectCodeSet.addAll(this.getSubjectCode(sameCtrlOffsetCond, new ArrayKey(new Object[]{SubjectAttributeEnum.PROFITLOSS.getValue(), SubjectAttributeEnum.CASH.getValue()})));
        } else if (SameCtrlSrcTypeEnum.ACQUIRER_BEGIN_EXTRACT.getCode().equals(sameCtrlSrcTypeEnum.getCode()) || SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEGIN_EXTRACT.getCode().equals(sameCtrlSrcTypeEnum.getCode())) {
            subjectCodeOfLastYear2CurrYear.putAll(this.setSameCtrlSettingParams(sameCtrlQueryParamsVO, sameCtrlSettingOptionVO, sameCtrlOffsetCond, SameCtrlSettingTypeEnum.MONTH.getCode()));
            sameCtrlQueryParamsVO.setRules(SameCtrlManageUtil.getInvestRuleIdListByType(sameCtrlQueryParamsVO.getSchemeId(), sameCtrlQueryParamsVO.getSameCtrlPeriodStr(), false));
            assetAndDebtSubjectCodeSet.addAll(this.getSubjectCode(sameCtrlOffsetCond, new ArrayKey(new Object[]{SubjectAttributeEnum.ASSET.getValue(), SubjectAttributeEnum.DEBT.getValue()})));
        }
        sameCtrlQueryParamsVO.setSameCtrl(true);
        return subjectCodeOfLastYear2CurrYear;
    }

    private int saveSameCtrlExtractData(List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList, SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlQueryParamsVO sameCtrlQueryParamsVO) {
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        HashMap ruleId2TitleMap = new HashMap();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(sameCtrlQueryParamsVO.getSchemeId(), sameCtrlQueryParamsVO.getSameCtrlPeriodStr(), null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getTitle()));
        Map<String, List<GcOffSetVchrItemAdjustEO>> mecid2OffsetItemMap = gcOffSetVchrItemAdjustEOList.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlOffsetCond.getSameCtrlChgId()));
        Assert.isNotNull((Object)((Object)sameCtrlChgOrgEO), (String)"\u672a\u67e5\u8be2\u5230\u540c\u63a7\u53d8\u52a8\u6570\u636e", (Object[])new Object[0]);
        for (Map.Entry<String, List<GcOffSetVchrItemAdjustEO>> entry : mecid2OffsetItemMap.entrySet()) {
            List<GcOffSetVchrItemAdjustEO> offsetItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderSnowUtils.newUUIDStr();
            double sumOffsetValue = 0.0;
            for (GcOffSetVchrItemAdjustEO gcOffSetItemEO : offsetItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties(gcOffSetItemEO, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDOrderSnowUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setUnitCode(gcOffSetItemEO.getUnitId());
                sameCtrlOffSetItemEO.setOppUnitCode(gcOffSetItemEO.getOppUnitId());
                sameCtrlOffSetItemEO.setInputUnitCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
                YearPeriodObject yp = new YearPeriodObject(null, sameCtrlOffsetCond.getPeriodStr());
                GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)sameCtrlOffsetCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
                sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
                sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
                sameCtrlOffSetItemEO.setChangedParentCode(sameCtrlChgOrgEO.getChangedParentCode());
                sameCtrlOffSetItemEO.setDefaultPeriod(sameCtrlOffsetCond.getPeriodStr());
                sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getDisposalDate()));
                sameCtrlOffSetItemEO.setSameCtrlChgId(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getId());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(sameCtrlExtractManageCond.getSameCtrlSrcTypeEnum().getCode());
                String changedUnitTypeId = SameCtrlManageUtil.getOrgTypeId(sameCtrlChgEnvContext, sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getChangedCode());
                sameCtrlOffSetItemEO.setOrgType(changedUnitTypeId);
                sameCtrlOffSetItemEO.setRuleTitle(StringUtils.isEmpty((String)((String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()))) ? gcOffSetItemEO.getRuleId() : (String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()));
                sameCtrlOffSetItemEO.setSrcId(gcOffSetItemEO.getId());
                sameCtrlOffSetItemEO.setTaskId(sameCtrlOffsetCond.getTaskId());
                sameCtrlOffSetItemEO.setSchemeId(sameCtrlOffsetCond.getSchemeId());
                sameCtrlOffSetItemEO.setAdjust(sameCtrlOffsetCond.getSelectAdjustCode());
                sameCtrlOffSetItemEO.setSrcOffsetGroupId(gcOffSetItemEO.getId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
            Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffSetItemEOS);
        return mecid2OffsetItemMap.size();
    }

    private Map<String, String> setSameCtrlSettingParams(SameCtrlQueryParamsVO queryParamsVO, SameCtrlChagSettingOptionVO sameCtrlSettingOptionVO, SameCtrlOffsetCond sameCtrlOffsetCond, String type) {
        HashMap<String, String> subjectCodeOfLastYear2CurrYear = new HashMap<String, String>(16);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)sameCtrlOffsetCond.getPeriodStr());
        int extractMonth = yearPeriodUtil.getPeriod();
        int extractYear = yearPeriodUtil.getYear();
        TaskAndSchemeMapping tempTaskAndSchemeMapping = null;
        List taskAndSchemeMappings = sameCtrlSettingOptionVO.getTaskAndSchemeMappings();
        if (!CollectionUtils.isEmpty((Collection)taskAndSchemeMappings)) {
            List taskAndSchemeMappingList = taskAndSchemeMappings.stream().filter(item -> item.getDataType().equals(type)).collect(Collectors.toList());
            for (TaskAndSchemeMapping item2 : taskAndSchemeMappingList) {
                int fromDateYear = DateUtils.getDateFieldValue((Date)item2.getFromDate(), (int)1);
                int toDateYear = DateUtils.getDateFieldValue((Date)item2.getToDate(), (int)1);
                if (extractYear < fromDateYear || extractYear > toDateYear) continue;
                int fromDate = DateUtils.getDateFieldValue((Date)item2.getFromDate(), (int)2);
                int toDate = DateUtils.getDateFieldValue((Date)item2.getToDate(), (int)2);
                if (extractYear == fromDateYear && extractMonth < fromDate || extractYear == toDateYear && extractMonth > toDate) continue;
                queryParamsVO.setTaskId(item2.getLastYearTaskId());
                queryParamsVO.setSchemeId(item2.getLastYearSchemeId());
                tempTaskAndSchemeMapping = item2;
                this.setSubjectCodeOfLastYear2CurrYear(item2.getSubjectMappings(), subjectCodeOfLastYear2CurrYear);
                break;
            }
        }
        if (null == tempTaskAndSchemeMapping) {
            queryParamsVO.setTaskId(sameCtrlOffsetCond.getTaskId());
            queryParamsVO.setSchemeId(sameCtrlOffsetCond.getSchemeId());
        }
        if (SameCtrlSettingTypeEnum.YEAR.getCode().equals(type)) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            PeriodWrapper periodWrapper = PeriodUtil.getPeriodWrapper((String)sameCtrlOffsetCond.getPeriodStr());
            defaultPeriodAdapter.priorYear(periodWrapper);
            queryParamsVO.setSameCtrlPeriodStr(periodWrapper.toString());
        } else {
            this.setSameCtrlPeriodStr(queryParamsVO, yearPeriodUtil);
        }
        return subjectCodeOfLastYear2CurrYear;
    }

    private void setSubjectCodeOfLastYear2CurrYear(List<SameCtrlChagSubjectMapVO> subjectMappings, Map<String, String> subjectCodeOfLastYear2CurrYear) {
        subjectMappings.forEach(sameCtrlChagSubjectMapVO -> {
            String currYearSubjectCode = sameCtrlChagSubjectMapVO.getCurrYearSubject().getCode();
            sameCtrlChagSubjectMapVO.getLastYearSubjects().forEach(item -> subjectCodeOfLastYear2CurrYear.put(item.getCode(), currYearSubjectCode));
        });
    }

    private void setSameCtrlPeriodStr(SameCtrlQueryParamsVO queryParamsVO, YearPeriodDO yearPeriodUtil) {
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

    private Set<String> getSubjectCode(SameCtrlOffsetCond sameCtrlOffsetCond, ArrayKey arrayKey) {
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(sameCtrlOffsetCond.getTaskId(), sameCtrlOffsetCond.getPeriodStr());
        List subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        HashSet<String> subjectCodeSet = new HashSet<String>();
        subjectEOS.forEach(item -> {
            if (arrayKey.getValues().contains(item.getAttri())) {
                subjectCodeSet.add(item.getCode());
            }
        });
        return subjectCodeSet;
    }
}

