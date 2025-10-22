/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.financialcheckImpl.datatrace.base;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.financial.cache.FinancialCheckRuleCacheService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckBalanceExecutorHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckItemExecutorHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.GcFinancialCheckFormulaEvalService;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.impl.GcRelatedOffsetVoucherItemServiceImpl;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class FinancialCheckUnionRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private FinancialCheckRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private ExecutorContext context;
    private static final String ALLLIST = "ALL";
    private DimensionValueSet dimensionValueSet;
    private GcFinancialCheckFormulaEvalService inputDataFormulaEvalService;
    private OffsetRelatedItemDao offsetRelatedItemDao;
    private GcRelatedItemDao relatedItemDao;
    private ConsolidatedSubjectService consolidatedSubjectService;
    private GcRelatedOffsetVoucherItemService gcRelatedOffsetVoucherItemService;
    private FinancialCheckFetchConfig.Item currFetchItem;
    private Map<String, List<GcFcRuleUnOffsetDataDTO>> recordMap;
    private List<GcFcRuleUnOffsetDataDTO> dataSForTra;
    private GcFcRuleUnOffsetDataDTO inputData;
    private List<GcFcRuleUnOffsetDataDTO> dataSForFormula = new ArrayList<GcFcRuleUnOffsetDataDTO>();
    private String tableName;

    public static FinancialCheckUnionRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        FinancialCheckUnionRuleTraceProcessor processor = new FinancialCheckUnionRuleTraceProcessor(offsetItem, (FinancialCheckRuleDTO)rule, taskArg);
        return processor;
    }

    public FinancialCheckUnionRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, FinancialCheckRuleDTO rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = ((FinancialCheckRuleCacheService)SpringBeanUtils.getBean(FinancialCheckRuleCacheService.class)).getRule(rule);
        this.rule.setUnilateralOffsetFlag(Boolean.valueOf(true));
        this.taskArg = taskArg;
        this.inputDataFormulaEvalService = (GcFinancialCheckFormulaEvalService)SpringBeanUtils.getBean(GcFinancialCheckFormulaEvalService.class);
        this.offsetRelatedItemDao = (OffsetRelatedItemDao)SpringBeanUtils.getBean(OffsetRelatedItemDao.class);
        this.relatedItemDao = (GcRelatedItemDao)SpringBeanUtils.getBean(GcRelatedItemDao.class);
        this.consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        this.gcRelatedOffsetVoucherItemService = (GcRelatedOffsetVoucherItemService)SpringBeanUtils.getBean(GcRelatedOffsetVoucherItemService.class);
        this.dimensionValueSet = DimensionUtils.generateDimSet((Object)this.getUnionCommonUnit(), (Object)offsetItem.getDefaultPeriod(), (Object)offsetItem.getOffSetCurr(), (Object)taskArg.getOrgType(), (String)((String)offsetItem.getFieldValue("ADJUST")), (String)offsetItem.getTaskId());
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByPeriodAndTask(taskArg.getPeriodStr(), taskArg.getTaskId());
            if (Objects.isNull(schemePeriodLinkDefine)) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u4e0d\u5230\u62a5\u8868\u65b9\u6848");
            }
            this.taskArg.setSchemeId(schemePeriodLinkDefine.getSchemeKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        this.recordMap = this.groupByDc(rule);
    }

    public List<FetchItemDTO> getFetchItem() {
        FinancialCheckFetchConfig flexibleFetchConfig = this.rule.getFetchConfigList().stream().filter(fetchConfig -> StringUtils.equalsAny((String)fetchConfig.getFetchSetGroupId(), (String[])new String[]{this.offsetItem.getFetchSetGroupId()})).findFirst().orElse(null);
        if (Objects.isNull(flexibleFetchConfig)) {
            return null;
        }
        List itemList = this.offsetItem.getOrient() == 1 ? flexibleFetchConfig.getDebitConfigList() : flexibleFetchConfig.getCreditConfigList();
        return itemList.stream().filter(item -> item.getSubjectCode().equals(this.offsetItem.getSubjectCode())).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = new GcReportExceutorContext();
            try {
                this.context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            }
            catch (ParseException e) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u73af\u5883\u51c6\u5907\u51fa\u9519\u3002", (Throwable)e);
            }
        }
        this.context.setDefaultGroupName(this.tableName);
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        if (CollectionUtils.isEmpty(this.recordMap)) {
            return AbstractData.valueOf((double)0.0);
        }
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
            return this.inputDataFormulaEvalService.evaluateSumUnOffsetAbstractData(this.dimensionValueSet, formula, this.dataSForFormula);
        }
        if (Objects.isNull(this.inputData)) {
            return AbstractData.valueOf((double)0.0);
        }
        return this.inputDataFormulaEvalService.evaluateMxUnOffsetAbstractData(this.dimensionValueSet, formula, this.inputData, this.dataSForFormula);
    }

    private String getUnionCommonUnit() {
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetItem.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO localOrg = tool.getOrgByCode(this.offsetItem.getUnitId());
        Assert.isNotNull((Object)localOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u4ee3\u7801\u201c" + this.offsetItem.getUnitId() + "\u201d\u65f6\u671f\uff1a\u201c" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        GcOrgCacheVO oppOrg = tool.getOrgByCode(this.offsetItem.getOppUnitId());
        Assert.isNotNull((Object)oppOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u4ee3\u7801\u201c" + this.offsetItem.getOppUnitId() + "\u201d\u65f6\u671f\uff1a\u201d" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        GcOrgCacheVO mergeOrg = tool.getCommonUnit(localOrg, oppOrg);
        Assert.isNotNull((Object)mergeOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u201c" + this.offsetItem.getUnitId() + "\u201d\u4e0e\u201c" + this.offsetItem.getOppUnitId() + "\u201d\u65f6\u671f\uff1a\u201d" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        return mergeOrg.getCode();
    }

    private List<GcFcRuleUnOffsetDataDTO> getInputDataS(FinancialCheckRuleDTO rule) {
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataMaps;
        this.tableName = "GC_RELATED_ITEM";
        List<GcOffsetRelatedItemEO> items = this.offsetRelatedItemDao.listByOffsetGroupId(Arrays.asList(this.offsetItem.getSrcOffsetGroupId()));
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        GcCalcArgmentsDTO argmentsDTO = new GcCalcArgmentsDTO();
        BeanUtils.copyProperties(this.taskArg, argmentsDTO);
        if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            Set<String> ids = items.stream().map(GcOffsetRelatedItemEO::getRelatedItemId).collect(Collectors.toSet());
            if (rule.isChecked()) {
                List<GcRelatedOffsetVoucherItemEO> offsetVoucherItemEOS = this.gcRelatedOffsetVoucherItemService.queryByIds(ids);
                unOffsetDataMaps = new FinancialCheckItemExecutorHelper((AbstractUnionRule)rule, argmentsDTO).coreCalc(GcRelatedOffsetVoucherItemServiceImpl.convert2UnOffsetData(offsetVoucherItemEOS));
            } else {
                List relatedItems = this.relatedItemDao.queryByIds(ids);
                unOffsetDataMaps = new FinancialCheckItemExecutorHelper((AbstractUnionRule)rule, argmentsDTO).coreCalc(FinancialCheckRuleExecutorImpl.convertOrigin2RuleUnOffsetData(relatedItems));
            }
        } else {
            unOffsetDataMaps = new FinancialCheckBalanceExecutorHelper((AbstractUnionRule)rule, argmentsDTO).coreCalc(FinancialCheckRuleExecutorImpl.convertOffset2RuleUnOffsetData(items));
        }
        assert (this.consolidatedSubjectService != null);
        Set srcDebitSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(rule.getSrcDebitSubjectCodeList(), rule.getReportSystem());
        for (GcFcRuleUnOffsetDataDTO unOffsetData : unOffsetDataMaps) {
            String subjectCode = unOffsetData.getSubjectCode();
            ConsolidatedSubjectEO consolidatedSubject = this.consolidatedSubjectService.getSubjectByCode(rule.getReportSystem(), subjectCode);
            if (Objects.isNull(consolidatedSubject)) {
                throw new BusinessRuntimeException("\u6839\u636e\u79d1\u76ee\u4ee3\u7801" + subjectCode + "\u67e5\u627e\u4e0d\u5230\u5408\u5e76\u79d1\u76ee");
            }
            if (!Objects.equals(consolidatedSubject.getOrient(), unOffsetData.getDc())) {
                unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).negate().doubleValue()));
            }
            if (srcDebitSubjectCodes.contains(subjectCode)) {
                unOffsetData.setDc(OrientEnum.D.getValue());
            } else {
                unOffsetData.setDc(OrientEnum.C.getValue());
            }
            if (!rule.isChecked() || !ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) continue;
            unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).negate().doubleValue()));
        }
        return unOffsetDataMaps;
    }

    public void initFetchItem(FetchItemDTO item) {
        this.currFetchItem = (FinancialCheckFetchConfig.Item)item.getFetchItem();
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        this.dataSForTra = this.getListForTra(fetchType);
        this.dataSForFormula = this.getListForFormual(fetchType);
        this.inputData = this.dataSForTra.stream().filter(inputData -> inputData.getId().equals(this.offsetItem.getSrcId())).findFirst().orElse(null);
    }

    public boolean takeOverTrace(FetchItemDTO item) {
        return ObjectUtils.isEmpty(item.getFormula());
    }

    public OffsetAmtTraceResultVO traceByTakeOver() {
        if (CollectionUtils.isEmpty(this.recordMap)) {
            return null;
        }
        OffsetAmtTraceResultVO result = new OffsetAmtTraceResultVO();
        OffsetAmtTraceItemVO offsetAmtTraceItem = new OffsetAmtTraceItemVO();
        result.setOffsetAmtTraceItems(Arrays.asList(offsetAmtTraceItem));
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        result.setFunction(fetchType.getName());
        offsetAmtTraceItem.setExpression(fetchType.getName());
        if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
            List allInputData = this.dataSForFormula.stream().filter(inputData -> inputData.getSubjectCode().equals(this.currFetchItem.getSubjectCode())).collect(Collectors.toList());
            double amtSum = 0.0;
            for (GcFcRuleUnOffsetDataDTO inputItem : allInputData) {
                amtSum += inputItem.getAmt().doubleValue();
            }
            result.setAmt(Double.valueOf(amtSum));
            offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)amtSum, (int)10, (int)2, (boolean)true));
        } else {
            if (Objects.isNull(this.inputData)) {
                return null;
            }
            Double offsetAmt = this.inputData.getAmt();
            result.setAmt(offsetAmt);
            offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)offsetAmt, (int)10, (int)2, (boolean)true));
        }
        return result;
    }

    private Map<String, List<GcFcRuleUnOffsetDataDTO>> groupByDc(FinancialCheckRuleDTO rule) {
        List<GcFcRuleUnOffsetDataDTO> inputItems = this.getInputDataS(rule);
        if (CollectionUtils.isEmpty(inputItems)) {
            return null;
        }
        HashMap<String, List<GcFcRuleUnOffsetDataDTO>> group = new HashMap<String, List<GcFcRuleUnOffsetDataDTO>>(16);
        ArrayList debitList = new ArrayList();
        ArrayList creditList = new ArrayList();
        group.put(OrientEnum.D.getCode(), debitList);
        group.put(OrientEnum.C.getCode(), creditList);
        group.put(ALLLIST, new ArrayList<GcFcRuleUnOffsetDataDTO>(inputItems));
        if (CollectionUtils.isEmpty(inputItems)) {
            return group;
        }
        inputItems.forEach(record -> {
            if (record.getDc().equals(OrientEnum.D.getValue())) {
                debitList.add(record);
            } else if (record.getDc().equals(OrientEnum.C.getValue())) {
                creditList.add(record);
            }
        });
        return group;
    }

    private List<GcFcRuleUnOffsetDataDTO> getListForFormual(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_SUM) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_SUM) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL || fetchType == FetchTypeEnum.SUM || fetchType == FetchTypeEnum.DEBIT_DETAIL || fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }

    private List<GcFcRuleUnOffsetDataDTO> getListForTra(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_DETAIL) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }
}

