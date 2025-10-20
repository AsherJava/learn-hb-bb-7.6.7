/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
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
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO
 *  com.jiuqi.gcreport.samecontrol.enums.InvestmentUnitSameCtrlEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.xlib.runtime.Assert
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.google.common.collect.Lists;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
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
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlRuleDao;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractSameCtrlRule;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import com.jiuqi.gcreport.samecontrol.enums.InvestmentUnitSameCtrlEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.formula.impl.GcSameCtrlFormulaEvalServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlExtractInvestExecuteRule
extends SameCtrlOffSetItemServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlExtractInvestExecuteRule.class);
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired(required=false)
    private SameCtrlInvestBillClient sameCtrlInvestBillClient;
    @Autowired
    private SameCtrlRuleDao sameCtrlRuleDao;
    @Autowired
    private IDataAccessProvider provider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private GcSameCtrlFormulaEvalServiceImpl gcSameCtrlFormulaEvalService;
    @Autowired
    private ConsolidatedTaskService taskService;

    public void sameCtrlExtractInvest(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        this.deleteSameCtrlOffset(sameCtrlOffsetCond, sameCtrlExtractManageCond.getSameCtrlSrcTypeEnum());
        DefaultTableEntity defaultTableEntity = this.queryInvestByParam(sameCtrlExtractManageCond.getGcOrgCenterService(), sameCtrlOffsetCond, sameCtrlExtractManageCond);
        if (defaultTableEntity == null) {
            throw new BusinessRuntimeException("\u672a\u67e5\u8be2\u5230\u53f0\u8d26\u6570\u636e!");
        }
        List<DefaultTableEntity> itemDefaultTableEntityList = SameCtrlManageUtil.getItemByInvestMasterId(defaultTableEntity.getId());
        List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList = this.executeSameCtrlRule(sameCtrlChgEnvContext, defaultTableEntity, itemDefaultTableEntityList);
        SameCtrlChgOrgEO sameCtrlChgOrgEO = sameCtrlExtractManageCond.getSameCtrlChgOrgEO();
        GcOrgCacheVO virtualCodeVO = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlChgOrgEO.getVirtualCode());
        String result = String.format("\u5f00\u59cb\u6267\u884c\u865a\u62df\u5355\u4f4d\uff1a%1s | %2s \u53f0\u8d26\u6570\u636e\u63d0\u53d6", sameCtrlChgOrgEO.getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlChgOrgEO.getVirtualCode());
        sameCtrlChgEnvContext.addResultItem(result);
        logger.info(result);
        int count = this.saveSameCtrlExtractData(gcOffSetVchrItemAdjustEOList, sameCtrlChgEnvContext, sameCtrlExtractManageCond);
        String endResult = String.format("\u865a\u62df\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u53f0\u8d26\u6570\u636e", sameCtrlChgOrgEO.getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlChgOrgEO.getVirtualCode(), count);
        sameCtrlChgEnvContext.addResultItem(endResult);
        logger.info(endResult);
    }

    private DefaultTableEntity queryInvestByParam(GcOrgCenterService orgTool, SameCtrlOffsetCond sameCtrlOffsetCond, SameCtrlExtractManageCond sameCtrlExtractManageCond) {
        if (StringUtils.isEmpty((String)sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualParentCode())) {
            throw new BusinessRuntimeException("\u672a\u8bbe\u7f6e\u5bf9\u5e94\u865a\u62df\u5355\u4f4d");
        }
        GcOrgCacheVO currUnitCacheVO = orgTool.getOrgByCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualParentCode());
        String baseUnitid = currUnitCacheVO.getBaseUnitId();
        if (null == baseUnitid) {
            throw new BusinessRuntimeException("\u672a\u8bbe\u7f6e\u3010" + sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualParentCode() + "\u3011\u672c\u90e8\u5355\u4f4d");
        }
        GcOrgCacheVO baseUnitVO = orgTool.getOrgByCode(baseUnitid);
        HashSet<String> investUnitSet = new HashSet<String>();
        if (baseUnitVO.isLeaf()) {
            investUnitSet.add(baseUnitVO.getCode());
        } else {
            investUnitSet.addAll(this.orgTreeToList(orgTool.getOrgChildrenTree(baseUnitVO.getCode())));
        }
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform((String)sameCtrlOffsetCond.getSchemeId(), (String)sameCtrlOffsetCond.getPeriodStr());
        if (StringUtils.isEmpty((String)sameCtrlExtractManageCond.getAcquirerCode())) {
            throw new BusinessRuntimeException("\u672a\u8bbe\u7f6e\u3010\u540c\u63a7\u865a\u62df\u8868\u3011\u53d8\u52a8\u5355\u4f4d\u4fe1\u606f\uff0c\u65e0\u6cd5\u83b7\u53d6\u3010\u540c\u63a7\u865a\u62df\u8868\u3011\u865a\u62df\u5355\u4f4d");
        }
        HashSet<String> investedUnitSet = new HashSet<String>();
        investedUnitSet.add(sameCtrlExtractManageCond.getAcquirerCode());
        if (this.sameCtrlInvestBillClient == null) {
            return null;
        }
        List<DefaultTableEntity> mastByInvestAndInvestedUnit = SameCtrlManageUtil.getMastByInvestAndInvestedUnit(investUnitSet, investedUnitSet, yearPeriodUtil.getYear(), yearPeriodUtil.getPeriod());
        if (CollectionUtils.isEmpty(mastByInvestAndInvestedUnit)) {
            return null;
        }
        return mastByInvestAndInvestedUnit.get(0);
    }

    private List<GcOffSetVchrItemAdjustEO> executeSameCtrlRule(SameCtrlChgEnvContext sameCtrlChgEnvContext, DefaultTableEntity defaultTableEntity, List<DefaultTableEntity> itemDefaultTableEntityList) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        ArrayList<SameCtrlRuleTypeEnum> sameCtrlRuleTypeEnumList = new ArrayList<SameCtrlRuleTypeEnum>();
        sameCtrlRuleTypeEnumList.add(sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlRuleTypeEnum());
        List<AbstractCommonRule> ruleList = this.findRuleListByIsolatedFiledAndRuleTypes(sameCtrlExtractManageCond.getSystemId(), sameCtrlOffsetCond.getTaskId(), sameCtrlRuleTypeEnumList);
        if (CollectionUtils.isEmpty(ruleList)) {
            return Collections.emptyList();
        }
        YearPeriodObject yp = new YearPeriodObject(null, sameCtrlOffsetCond.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)sameCtrlOffsetCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = tool.getDeepestBaseUnitId(sameCtrlOffsetCond.getMergeUnitCode());
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            return Collections.emptyList();
        }
        String virtualCode = sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode();
        ArrayList<GcOffSetVchrItemAdjustEO> offSetItemList = new ArrayList<GcOffSetVchrItemAdjustEO>();
        if (StringUtils.isEmpty((String)sameCtrlExtractManageCond.getAcquirerCode())) {
            throw new BusinessRuntimeException("\u672a\u8bbe\u7f6e\u3010\u540c\u63a7\u865a\u62df\u8868\u3011\u53d8\u52a8\u5355\u4f4d\u4fe1\u606f\uff0c\u65e0\u6cd5\u83b7\u53d6\u3010\u540c\u63a7\u865a\u62df\u8868\u3011\u865a\u62df\u5355\u4f4d");
        }
        String virtualCodeNew = sameCtrlExtractManageCond.getAcquirerCode();
        for (AbstractCommonRule rule : ruleList) {
            offSetItemList.addAll(this.generateOffSetItemByRule(rule, sameCtrlChgEnvContext, baseUnitCode, virtualCode, defaultTableEntity, virtualCodeNew, itemDefaultTableEntityList));
        }
        return offSetItemList;
    }

    public List<GcOffSetVchrItemAdjustEO> generateOffSetItemByRule(AbstractCommonRule abstractCommonRule, SameCtrlChgEnvContext sameCtrlChgEnvContext, String baseUnitCode, String virtualCode, DefaultTableEntity mast, String virtualCodeNew, List<DefaultTableEntity> itemDefaultTableEntityList) {
        GcOffSetVchrItemDTO offSetVchrItem;
        DimensionValueSet ruleConditionDimSet;
        AbstractSameCtrlRule rule;
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        if (!this.matchRuleCondition(cond, rule = (AbstractSameCtrlRule)abstractCommonRule, ruleConditionDimSet = this.initDimensionValueSet(sameCtrlChgEnvContext), mast)) {
            return Collections.emptyList();
        }
        DimensionValueSet formulaDimSet = new DimensionValueSet(ruleConditionDimSet);
        formulaDimSet.setValue("MD_ORG", (Object)virtualCodeNew);
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        ArrayList<GcOffSetVchrItemDTO> debitRecordList = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> creditRecordList = new ArrayList<GcOffSetVchrItemDTO>();
        String mrecId = UUIDOrderSnowUtils.newUUIDStr();
        ArrayList<AbstractSameCtrlRule.Item> debitPhsFormulaList = new ArrayList<AbstractSameCtrlRule.Item>();
        ArrayList<AbstractSameCtrlRule.Item> creditPhsFormulaList = new ArrayList<AbstractSameCtrlRule.Item>();
        for (AbstractSameCtrlRule.Item item : rule.getDebitItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(mrecId, cond, rule, mast);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, item, formulaDimSet, OrientEnum.D, sameCtrlChgEnvContext, 0.0, mast, itemDefaultTableEntityList);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) {
                logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u501f\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), item.getFetchFormula());
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
            offSetVchrItem = this.createNewOffSetVchrItem(mrecId, cond, rule, mast);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, item, formulaDimSet, OrientEnum.C, sameCtrlChgEnvContext, 0.0, mast, itemDefaultTableEntityList);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) {
                logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), item.getFetchFormula());
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
            GcOffSetVchrItemDTO phsRecord = this.phsRecord(rule, baseUnitCode, sameCtrlChgEnvContext, formulaDimSet, mrecId, debitPhsFormulaList, creditPhsFormulaList, diffValue, mast, itemDefaultTableEntityList);
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

    private GcOffSetVchrItemDTO phsRecord(AbstractSameCtrlRule rule, String baseUnitCode, SameCtrlChgEnvContext sameCtrlChgEnvContext, DimensionValueSet dset, String mrecid, List<AbstractSameCtrlRule.Item> debitPhsFormulaList, List<AbstractSameCtrlRule.Item> creditPhsFormulaList, double diffValue, DefaultTableEntity mast, List<DefaultTableEntity> itemDefaultTableEntityList) {
        GcOffSetVchrItemDTO offSetVchrItem;
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        for (AbstractSameCtrlRule.Item fetchFormula : debitPhsFormulaList) {
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, cond, rule, mast);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, fetchFormula, dset, OrientEnum.D, sameCtrlChgEnvContext, -diffValue, mast, itemDefaultTableEntityList);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) continue;
            offSetVchrItem.setMemo("\u5e73\u8861\u6570");
            return offSetVchrItem;
        }
        for (AbstractSameCtrlRule.Item fetchFormula : creditPhsFormulaList) {
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, cond, rule, mast);
            this.fillOffSetVchrItem(offSetVchrItem, baseUnitCode, fetchFormula, dset, OrientEnum.C, sameCtrlChgEnvContext, diffValue, mast, itemDefaultTableEntityList);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) continue;
            offSetVchrItem.setMemo("\u5e73\u8861\u6570");
            return offSetVchrItem;
        }
        return null;
    }

    private void fillOffSetVchrItem(GcOffSetVchrItemDTO offSetVchrItemDTO, String baseUnitCode, AbstractSameCtrlRule.Item item, DimensionValueSet dimensionValueSet, OrientEnum orientEnum, SameCtrlChgEnvContext sameCtrlChgEnvContext, Double phsValue, DefaultTableEntity mast, List<DefaultTableEntity> itemDefaultTableEntityList) {
        String oppUnitId;
        String unitId;
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
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
        double result = this.evaluateSameCtrlInvestData(sameCtrlChgEnvContext, dimensionValueSet, fetchFormula, phsValue, mast, itemDefaultTableEntityList);
        if (orientEnum.equals((Object)OrientEnum.D)) {
            offSetVchrItemDTO.setOffSetDebit(Double.valueOf(result));
            offSetVchrItemDTO.setDebit(Double.valueOf(result));
        } else {
            offSetVchrItemDTO.setOffSetCredit(Double.valueOf(result));
            offSetVchrItemDTO.setCredit(Double.valueOf(result));
        }
    }

    public double evaluateSameCtrlInvestData(SameCtrlChgEnvContext sameCtrlChgEnvContext, DimensionValueSet dset, String fetchFormula, Double phsValue, DefaultTableEntity mast, List<DefaultTableEntity> itemDefaultTableEntityList) {
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return 0.0;
        }
        AbstractData data = this.getSameCtrlInvestData(sameCtrlChgEnvContext, dset, fetchFormula, phsValue, mast, itemDefaultTableEntityList);
        return GcAbstractData.getDoubleValue((AbstractData)data);
    }

    private OrientEnum getSubjectOrient(String schemeId, String periodStr, String subjectCode) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        ConsolidatedSubjectEO consolidatedSubjectEo = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).getSubjectByCode(systemId, subjectCode);
        Assert.isNotNull((Object)consolidatedSubjectEo, (String)("\u672a\u627e\u89c1\u79d1\u76ee:" + subjectCode), (Object[])new Object[0]);
        return OrientEnum.valueOf((Integer)consolidatedSubjectEo.getOrient());
    }

    private GcOffSetVchrItemDTO createNewOffSetVchrItem(String mrecid, SameCtrlOffsetCond cond, AbstractSameCtrlRule rule, DefaultTableEntity mast) {
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
        initOffSetVchrItem.setSrcOffsetGroupId(mast.getId());
        return initOffSetVchrItem;
    }

    private boolean matchRuleCondition(SameCtrlOffsetCond cond, AbstractSameCtrlRule rule, DimensionValueSet ruleConditionDimSet, DefaultTableEntity mast) {
        if (StringUtils.isEmpty((String)rule.getRuleCondition())) {
            return true;
        }
        try {
            if (!this.gcSameCtrlFormulaEvalService.checkSameCtrlInvestData(cond, ruleConditionDimSet, rule.getRuleCondition(), mast)) {
                logger.info("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\uff1a\u6295\u8d44\u89c4\u5219[{}]\uff0c\u53d8\u52a8\u5355\u4f4d[{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getTitle(), cond.getChangedUnitCode(), rule.getRuleCondition());
                return false;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u540c\u63a7\u81ea\u52a8\u62b5\u9500\u5224\u65ad\u6295\u8d44\u89c4\u5219[" + rule.getTitle() + "]\u9002\u7528\u6761\u4ef6[" + rule.getRuleCondition() + "]\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return true;
    }

    private DimensionValueSet initDimensionValueSet(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        String changedUnitTypeId = SameCtrlManageUtil.getOrgTypeId(sameCtrlChgEnvContext, cond.getChangedUnitCode());
        return DimensionUtils.generateDimSet((Object)cond.getChangedUnitCode(), (Object)cond.getPeriodStr(), (Object)cond.getCurrencyCode(), (Object)changedUnitTypeId, (String)cond.getSelectAdjustCode(), (String)cond.getTaskId());
    }

    public List<AbstractCommonRule> findRuleListByIsolatedFiledAndRuleTypes(String reportSystemId, String taskId, Collection<SameCtrlRuleTypeEnum> ruleTypeEnumList) {
        com.jiuqi.xlib.runtime.Assert.notNull((Object)reportSystemId, (String)"\u5408\u5e76\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        List<SameCtrlRuleEO> unionRuleEOList = this.sameCtrlRuleDao.findRuleList(reportSystemId, taskId);
        if (CollectionUtils.isEmpty(ruleTypeEnumList)) {
            return unionRuleEOList.stream().map(this::convertDTO).collect(Collectors.toList());
        }
        ArrayList finalResult = Lists.newArrayList();
        for (SameCtrlRuleEO entity : unionRuleEOList) {
            SameCtrlRuleTypeEnum ruleTypeEnum = SameCtrlRuleTypeEnum.codeOf((String)entity.getRuleType());
            if (!ruleTypeEnumList.contains(ruleTypeEnum)) continue;
            finalResult.add(this.convertDTO(entity));
        }
        return finalResult;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData getSameCtrlInvestData(SameCtrlChgEnvContext sameCtrlChgEnvContext, DimensionValueSet dset, String fetchFormula, Double phsValue, DefaultTableEntity mast, List<DefaultTableEntity> itemDefaultTableEntityList) {
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_INVESTBILL", "GC_INVESTBILLITEM"});
        DimensionValueSet investDs = this.getInvestDs(sameCtrlChgEnvContext, dset);
        if (!CollectionUtils.isEmpty(itemDefaultTableEntityList)) {
            for (DefaultTableEntity data : itemDefaultTableEntityList) {
                GcReportDataRow row = dataSet.add();
                row.setData(data);
            }
        }
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.setData(mast);
            context.setItems(itemDefaultTableEntityList);
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName("GC_INVESTBILL");
            context.setTaskId(cond.getTaskId());
            context.setSchemeId(cond.getSchemeId());
            context.setOrgId(cond.getMergeUnitCode());
            context.setPhsValue(phsValue);
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, cond.getSchemeId());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, investDs, fetchFormula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(mast);
            row.setDatas(itemDefaultTableEntityList);
            AbstractData abstractData = evaluator.evaluate((DataRow)row);
            return abstractData;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(fetchFormula + "\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    private DimensionValueSet getInvestDs(SameCtrlChgEnvContext sameCtrlChgEnvContext, DimensionValueSet dset) {
        DimensionValueSet investDs = new DimensionValueSet(dset);
        Object orgObj = investDs.getValue("MD_ORG");
        if (orgObj == null) {
            return investDs;
        }
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrgCacheVO = instance.getOrgByCode(orgObj.toString());
        investDs.setValue("MD_ORG", (Object)gcOrgCacheVO.getId());
        String orgTypeId = SameCtrlManageUtil.getOrgTypeId(sameCtrlChgEnvContext, gcOrgCacheVO.getId());
        investDs.setValue("MD_GCORGTYPE", (Object)orgTypeId);
        return investDs;
    }

    private AbstractCommonRule convertDTO(SameCtrlRuleEO sameCtrlRuleEO) {
        AbstractCommonRule rule;
        String jsonString = sameCtrlRuleEO.getJsonString();
        if (!Objects.equals(1, sameCtrlRuleEO.getLeafFlag())) {
            SameCtrlGroupRuleDTO rule2 = new SameCtrlGroupRuleDTO();
            BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule2);
            rule2.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
            rule2.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
            rule2.setRuleType(null);
            return rule2;
        }
        SameCtrlRuleTypeEnum ruleType = SameCtrlRuleTypeEnum.codeOf((String)sameCtrlRuleEO.getRuleType());
        if (SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DirectInvestmentDTO.class);
        } else if (SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DisposerInvestmentDTO.class);
        } else {
            throw new BusinessRuntimeException("\u65e0\u6548\u7684\u89c4\u5219\u7c7b\u578b");
        }
        assert (rule != null);
        BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule);
        rule.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
        rule.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
        rule.setRuleType(ruleType);
        return rule;
    }

    private int saveSameCtrlExtractData(List<GcOffSetVchrItemAdjustEO> gcOffSetItemEOS, SameCtrlChgEnvContext sameCtrlChgEnvContext, SameCtrlExtractManageCond sameCtrlExtractManageCond) {
        SameCtrlOffsetCond cond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        String sameCtrlSrcType = sameCtrlExtractManageCond.getSameCtrlSrcTypeEnum().getCode();
        HashMap ruleId2TitleMap = new HashMap();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(cond.getSchemeId(), cond.getPeriodStr(), null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getTitle()));
        Map<String, List<GcOffSetVchrItemAdjustEO>> mecid2OffsetItemMap = gcOffSetItemEOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)cond.getSameCtrlChgId()));
        Assert.isNotNull((Object)((Object)sameCtrlChgOrgEO), (String)"\u672a\u67e5\u8be2\u5230\u540c\u63a7\u53d8\u52a8\u6570\u636e", (Object[])new Object[0]);
        for (Map.Entry<String, List<GcOffSetVchrItemAdjustEO>> entry : mecid2OffsetItemMap.entrySet()) {
            List<GcOffSetVchrItemAdjustEO> offsetItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderSnowUtils.newUUIDStr();
            double sumOffsetValue = 0.0;
            for (GcOffSetVchrItemAdjustEO gcOffSetItemEO : offsetItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties(gcOffSetItemEO, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setUnitCode(gcOffSetItemEO.getUnitId());
                sameCtrlOffSetItemEO.setOppUnitCode(gcOffSetItemEO.getOppUnitId());
                sameCtrlOffSetItemEO.setInputUnitCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
                YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
                GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
                GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
                sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
                sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
                sameCtrlOffSetItemEO.setChangedParentCode(sameCtrlChgOrgEO.getChangedParentCode());
                sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
                sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getDisposalDate()));
                sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
                sameCtrlOffSetItemEO.setSameCtrlSrcType(sameCtrlSrcType);
                String changedUnitTypeId = SameCtrlManageUtil.getOrgTypeId(sameCtrlChgEnvContext, sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getChangedCode());
                sameCtrlOffSetItemEO.setOrgType(changedUnitTypeId);
                sameCtrlOffSetItemEO.setRuleTitle(StringUtils.isEmpty((String)((String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()))) ? gcOffSetItemEO.getRuleId() : (String)ruleId2TitleMap.get(gcOffSetItemEO.getRuleId()));
                sameCtrlOffSetItemEO.setSrcId(gcOffSetItemEO.getId());
                sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
                sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
                sameCtrlOffSetItemEO.setAdjust(cond.getSelectAdjustCode());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
            Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffSetItemEOS);
        return mecid2OffsetItemMap.size();
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
}

