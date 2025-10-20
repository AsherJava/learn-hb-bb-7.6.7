/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractSameCtrlRule
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractSameCtrlRule$Item
 *  com.jiuqi.gcreport.samecontrol.enums.InvestmentUnitSameCtrlEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlInvestBillClient;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractSameCtrlRule;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.enums.InvestmentUnitSameCtrlEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.formula.impl.GcSameCtrlFormulaEvalServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlCalcInvestRuleService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlRuleService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlCalcInvestRuleServiceImpl
implements SameCtrlCalcInvestRuleService {
    private final Logger logger = LoggerFactory.getLogger(SameCtrlCalcInvestRuleServiceImpl.class);
    @Autowired
    private SameCtrlRuleService sameCtrlRuleService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private GcSameCtrlFormulaEvalServiceImpl gcSameCtrlFormulaEvalService;
    @Autowired(required=false)
    private SameCtrlInvestBillClient sameCtrlInvestBillClient;

    @Override
    public List<GcOffSetVchrItemAdjustEO> generateGcOffSetVchrItemAdjust(SameCtrlOffsetCond cond, SameCtrlChgOrgEO sameCtrlChgOrgEo) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(cond.getSchemeId(), cond.getPeriodStr());
        ArrayList<SameCtrlRuleTypeEnum> sameCtrlRuleTypeEnumList = new ArrayList<SameCtrlRuleTypeEnum>();
        sameCtrlRuleTypeEnumList.add(SameCtrlRuleTypeEnum.DIRECT_INVESTMENT);
        List<AbstractCommonRule> ruleList = this.sameCtrlRuleService.findRuleListByIsolatedFiledAndRuleTypes(systemId, cond.getTaskId(), sameCtrlRuleTypeEnumList);
        if (CollectionUtils.isEmpty(ruleList)) {
            return Collections.emptyList();
        }
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = tool.getDeepestBaseUnitId(cond.getMergeUnitCode());
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            return Collections.emptyList();
        }
        String virtualCode = sameCtrlChgOrgEo.getVirtualCode();
        DefaultTableEntity mast = this.getInvestMastData(tool, cond);
        ArrayList<GcOffSetVchrItemAdjustEO> offSetItemList = new ArrayList<GcOffSetVchrItemAdjustEO>();
        for (AbstractCommonRule rule : ruleList) {
            offSetItemList.addAll(this.generateOffSetItemByRule(rule, cond, baseUnitCode, virtualCode, mast));
        }
        return offSetItemList;
    }

    private DefaultTableEntity getInvestMastData(GcOrgCenterService tool, SameCtrlOffsetCond cond) {
        GcOrgCacheVO currUnitCacheVO = tool.getOrgByCode(cond.getMergeUnitCode());
        String baseUnitid = currUnitCacheVO.getBaseUnitId();
        if (null == baseUnitid) {
            return null;
        }
        GcOrgCacheVO baseUnitVO = tool.getOrgByCode(baseUnitid);
        HashSet<String> investUnitSet = new HashSet<String>();
        if (baseUnitVO.isLeaf()) {
            investUnitSet.add(baseUnitVO.getCode());
        } else {
            investUnitSet.addAll(this.orgTreeToList(tool.getOrgChildrenTree(baseUnitVO.getCode())));
        }
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)cond.getPeriodStr());
        HashSet<String> investedUnitSet = new HashSet<String>();
        investedUnitSet.add(cond.getChangedUnitCode());
        if (this.sameCtrlInvestBillClient == null) {
            return null;
        }
        List mastByInvestAndInvestedUnit = this.sameCtrlInvestBillClient.getMastByInvestAndInvestedUnit(investUnitSet, investedUnitSet, yearPeriodUtil.getYear(), yearPeriodUtil.getPeriod());
        if (CollectionUtils.isEmpty((Collection)mastByInvestAndInvestedUnit)) {
            return null;
        }
        return (DefaultTableEntity)mastByInvestAndInvestedUnit.get(0);
    }

    private Set<String> orgTreeToList(List<GcOrgCacheVO> orgChildrenTree) {
        if (CollectionUtils.isEmpty(orgChildrenTree)) {
            return Collections.emptySet();
        }
        HashSet<String> investUnitSet = new HashSet<String>();
        for (GcOrgCacheVO orgCacheVO : orgChildrenTree) {
            if (orgCacheVO == null) continue;
            investUnitSet.add(orgCacheVO.getCode());
            if (orgCacheVO.isLeaf()) continue;
            investUnitSet.addAll(this.orgTreeToList(orgCacheVO.getChildren()));
        }
        return investUnitSet;
    }

    private List<GcOffSetVchrItemAdjustEO> generateOffSetItemByRule(AbstractCommonRule abstractCommonRule, SameCtrlOffsetCond cond, String baseUnitCode, String virtualCode, DefaultTableEntity mast) {
        GcOffSetVchrItemDTO offSetVchrItem;
        AbstractSameCtrlRule rule = (AbstractSameCtrlRule)abstractCommonRule;
        DimensionValueSet ruleConditionDimSet = this.initDimensionValueSet(cond);
        if (!this.matchRuleCondition(cond, rule, ruleConditionDimSet, mast)) {
            return Collections.emptyList();
        }
        DimensionValueSet formulaDimSet = new DimensionValueSet(ruleConditionDimSet);
        formulaDimSet.setValue("MD_ORG", (Object)virtualCode);
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        ArrayList<GcOffSetVchrItemDTO> debitRecordList = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> creditRecordList = new ArrayList<GcOffSetVchrItemDTO>();
        String mrecId = UUIDOrderUtils.newUUIDStr();
        ArrayList<AbstractSameCtrlRule.Item> debitPhsFormulaList = new ArrayList<AbstractSameCtrlRule.Item>();
        ArrayList<AbstractSameCtrlRule.Item> creditPhsFormulaList = new ArrayList<AbstractSameCtrlRule.Item>();
        for (AbstractSameCtrlRule.Item item : rule.getDebitItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(mrecId, cond, rule);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, item, formulaDimSet, OrientEnum.D, cond, 0.0, mast);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) {
                this.logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u501f\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), item.getFetchFormula());
                continue;
            }
            debitSum = NumberUtils.sum((Double)debitSum, (Double)offSetVchrItem.getOffSetDebit());
            debitRecordList.add(offSetVchrItem);
        }
        for (AbstractSameCtrlRule.Item item : rule.getCreditItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                creditPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(mrecId, cond, rule);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, item, formulaDimSet, OrientEnum.C, cond, 0.0, mast);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) {
                this.logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), item.getFetchFormula());
                continue;
            }
            creditSum = NumberUtils.sum((Double)creditSum, (Double)offSetVchrItem.getOffSetCredit());
            creditRecordList.add(offSetVchrItem);
        }
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        records.addAll(debitRecordList);
        records.addAll(creditRecordList);
        double diffValue = NumberUtils.sub((Double)debitSum, (Double)creditSum);
        if (!NumberUtils.isZreo((Double)diffValue)) {
            GcOffSetVchrItemDTO phsRecord = this.phsRecord(rule, baseUnitCode, cond, formulaDimSet, mrecId, debitPhsFormulaList, creditPhsFormulaList, diffValue, mast);
            if (null != phsRecord) {
                records.add(phsRecord);
            } else if (diffValue > 0.0) {
                GcOffSetVchrItemDTO offSetVchrItemDTO = (GcOffSetVchrItemDTO)debitRecordList.get(0);
                offSetVchrItemDTO.setDebit(Double.valueOf(offSetVchrItemDTO.getDebit() - diffValue));
                offSetVchrItemDTO.setOffSetDebit(Double.valueOf(offSetVchrItemDTO.getOffSetDebit() - diffValue));
                offSetVchrItemDTO.setDiffd(Double.valueOf(diffValue));
            } else {
                GcOffSetVchrItemDTO offSetVchrItemDTO = (GcOffSetVchrItemDTO)creditRecordList.get(0);
                offSetVchrItemDTO.setCredit(Double.valueOf(offSetVchrItemDTO.getCredit() + diffValue));
                offSetVchrItemDTO.setOffSetCredit(Double.valueOf(offSetVchrItemDTO.getOffSetCredit() + diffValue));
                offSetVchrItemDTO.setDiffc(Double.valueOf(-diffValue));
            }
        }
        if (records.isEmpty()) {
            return Collections.emptyList();
        }
        return records.stream().map(itemDTO -> {
            if (null == itemDTO.getId()) {
                itemDTO.setId(UUIDOrderUtils.newUUIDStr());
            }
            GcOffSetVchrItemAdjustEO adjustEO = OffsetConvertUtil.getInstance().convertDTO2EO(itemDTO);
            String offSetCurr = itemDTO.getOffSetCurr().toUpperCase();
            adjustEO.addFieldValue("DEBIT_" + offSetCurr, adjustEO.getFieldValue("DEBIT" + offSetCurr));
            adjustEO.addFieldValue("CREDIT_" + offSetCurr, adjustEO.getFieldValue("CREDIT" + offSetCurr));
            return adjustEO;
        }).collect(Collectors.toList());
    }

    private boolean matchRuleCondition(SameCtrlOffsetCond cond, AbstractSameCtrlRule rule, DimensionValueSet ruleConditionDimSet, DefaultTableEntity mast) {
        if (StringUtils.isEmpty((String)rule.getRuleCondition())) {
            return true;
        }
        try {
            if (!this.gcSameCtrlFormulaEvalService.checkSameCtrlInvestData(cond, ruleConditionDimSet, rule.getRuleCondition(), mast)) {
                this.logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), rule.getRuleCondition());
                return false;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\u5224\u65ad\u6295\u8d44\u89c4\u5219[" + rule.getTitle() + "]\u9002\u7528\u6761\u4ef6[" + rule.getRuleCondition() + "]\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return true;
    }

    private DimensionValueSet initDimensionValueSet(SameCtrlOffsetCond cond) {
        DimensionValueSet dimSet = DimensionUtils.generateDimSet((Object)cond.getChangedUnitCode(), (Object)cond.getPeriodStr(), (Object)cond.getCurrencyCode(), (Object)cond.getOrgType(), (String)cond.getSelectAdjustCode(), (String)cond.getTaskId());
        return dimSet;
    }

    private GcOffSetVchrItemDTO phsRecord(AbstractSameCtrlRule rule, String baseUnitCode, SameCtrlOffsetCond cond, DimensionValueSet dset, String mrecid, List<AbstractSameCtrlRule.Item> debitPhsFormulaList, List<AbstractSameCtrlRule.Item> creditPhsFormulaList, double diffValue, DefaultTableEntity mast) {
        GcOffSetVchrItemDTO offSetVchrItem;
        for (AbstractSameCtrlRule.Item fetchFormula : debitPhsFormulaList) {
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, cond, rule);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, fetchFormula, dset, OrientEnum.D, cond, -diffValue, mast);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) continue;
            offSetVchrItem.setMemo("\u5e73\u8861\u6570");
            return offSetVchrItem;
        }
        for (AbstractSameCtrlRule.Item fetchFormula : creditPhsFormulaList) {
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, cond, rule);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, fetchFormula, dset, OrientEnum.C, cond, diffValue, mast);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) continue;
            offSetVchrItem.setMemo("\u5e73\u8861\u6570");
            return offSetVchrItem;
        }
        return null;
    }

    private void fillOffSetVchrItem(GcOffSetVchrItemDTO offSetVchrItemDTO, String baseUnitCode, AbstractSameCtrlRule.Item item, DimensionValueSet dimensionValueSet, OrientEnum orientEnum, SameCtrlOffsetCond cond, Double phsValue, DefaultTableEntity mast) {
        String oppUnitId;
        String unitId;
        String subjectCode = item.getSubjectCode();
        String fetchFormula = item.getFetchFormula();
        if (StringUtils.isEmpty((String)fetchFormula)) {
            return;
        }
        if (InvestmentUnitSameCtrlEnum.INVESTMENT_UNIT.equals((Object)item.getInvestmentUnit())) {
            unitId = baseUnitCode;
            oppUnitId = cond.getChangedUnitCode();
        } else {
            unitId = cond.getChangedUnitCode();
            oppUnitId = baseUnitCode;
        }
        offSetVchrItemDTO.setSortOrder(Double.valueOf(item.getSort() == null ? 0.0 : (double)item.getSort().intValue()));
        offSetVchrItemDTO.setUnitId(unitId);
        offSetVchrItemDTO.setOppUnitId(oppUnitId);
        offSetVchrItemDTO.setSubjectCode(subjectCode);
        offSetVchrItemDTO.setSubjectOrient(this.getSubjectOrient(cond.getSchemeId(), cond.getPeriodStr(), subjectCode));
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(fetchFormula), (String)"\u4e0d\u518d\u652f\u6301\u6b64\u5199\u6cd5\uff0cPHS\u9700\u8981\u6539\u6210PHS()", (Object[])new Object[0]);
        double result = this.gcSameCtrlFormulaEvalService.evaluateSameCtrlInvestData(cond, dimensionValueSet, fetchFormula, phsValue, mast);
        if (orientEnum.equals((Object)OrientEnum.D)) {
            offSetVchrItemDTO.setOffSetDebit(Double.valueOf(result));
            offSetVchrItemDTO.setDebit(Double.valueOf(result));
        } else {
            offSetVchrItemDTO.setOffSetCredit(Double.valueOf(result));
            offSetVchrItemDTO.setCredit(Double.valueOf(result));
        }
    }

    private OrientEnum getSubjectOrient(String schemeId, String periodStr, String subjectCode) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        ConsolidatedSubjectEO consolidatedSubjectEo = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).getSubjectByCode(systemId, subjectCode);
        Assert.isNotNull((Object)consolidatedSubjectEo, (String)("\u672a\u627e\u89c1\u79d1\u76ee:" + subjectCode), (Object[])new Object[0]);
        return OrientEnum.valueOf((Integer)consolidatedSubjectEo.getOrient());
    }

    private GcOffSetVchrItemDTO createNewOffSetVchrItem(String mrecid, SameCtrlOffsetCond cond, AbstractSameCtrlRule rule) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)cond.getPeriodStr());
        GcOffSetVchrItemDTO initOffSetVchrItem = new GcOffSetVchrItemDTO();
        initOffSetVchrItem.setmRecid(mrecid);
        initOffSetVchrItem.setDefaultPeriod(cond.getPeriodStr());
        initOffSetVchrItem.setAcctPeriod(Integer.valueOf(yearPeriodUtil.getPeriod()));
        initOffSetVchrItem.setAcctYear(Integer.valueOf(yearPeriodUtil.getYear()));
        initOffSetVchrItem.setTaskId(cond.getTaskId());
        initOffSetVchrItem.setSchemeId(cond.getSchemeId());
        initOffSetVchrItem.setId(UUID.randomUUID().toString());
        initOffSetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        initOffSetVchrItem.setCreateTime(Calendar.getInstance().getTime());
        initOffSetVchrItem.setGcBusinessTypeCode(rule.getBusinessTypeCode());
        initOffSetVchrItem.setRuleId(rule.getTitle());
        initOffSetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        initOffSetVchrItem.setOffSetCurr(cond.getCurrencyCode());
        initOffSetVchrItem.setDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffd(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffc(Double.valueOf(0.0));
        initOffSetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        return initOffSetVchrItem;
    }
}

