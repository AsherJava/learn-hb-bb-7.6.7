/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO
 *  com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.helper;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.enums.MatchingInformationEnum;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRuleConverter;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.GcFinancialCheckFormulaEvalService;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO;
import com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class FcCheckData2OffsetDataHelper {
    private final Logger logger = LoggerFactory.getLogger(FcCheckData2OffsetDataHelper.class);
    private String dataTime;
    private String taskKey;
    private GcOrgCenterService conTool;
    private GcOrgCenterService finalChkTool;
    private ConsolidatedSystemService systemService = (ConsolidatedSystemService)SpringBeanUtils.getBean(ConsolidatedSystemService.class);
    private ConsolidatedTaskService taskCacheService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
    private UnionRuleService unionRuleService = (UnionRuleService)SpringBeanUtils.getBean(UnionRuleService.class);
    private FinancialCheckRuleConverter ruleConverter = (FinancialCheckRuleConverter)SpringBeanUtils.getBean(FinancialCheckRuleConverter.class);
    private IDataAccessProvider provider = (IDataAccessProvider)SpringBeanUtils.getBean(IDataAccessProvider.class);
    private IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
    private Map<String, String> accountSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
    private ConversionSystemTaskDao conversionSystemTaskDao = (ConversionSystemTaskDao)SpringBeanUtils.getBean(ConversionSystemTaskDao.class);
    private RateSchemeMapper mapper = (RateSchemeMapper)SpringBeanUtils.getBean(RateSchemeMapper.class);
    private ConversionRateService conversionRateService = (ConversionRateService)SpringBeanUtils.getBean(ConversionRateService.class);
    private IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private GcFinancialCheckFormulaEvalService inputDataFormulaEvalService = (GcFinancialCheckFormulaEvalService)SpringBeanUtils.getBean(GcFinancialCheckFormulaEvalService.class);
    private GcRelatedItemDao relatedItemDao = (GcRelatedItemDao)SpringBeanUtils.getBean(GcRelatedItemDao.class);
    private String periodStr;

    private FcCheckData2OffsetDataHelper() {
    }

    public static FcCheckData2OffsetDataHelper newInstance(String dataTime) {
        FcCheckData2OffsetDataHelper helper = new FcCheckData2OffsetDataHelper();
        helper.dataTime = dataTime;
        return helper;
    }

    public List<GcOffsetRelatedItemEO> convert(List<GcRelatedItemEO> items) {
        Set<String> allSystemIds = this.listAllSystemIds();
        ArrayList<GcOffsetRelatedItemEO> relatedItemOffsetRelS = new ArrayList<GcOffsetRelatedItemEO>();
        allSystemIds.forEach(systemId -> relatedItemOffsetRelS.addAll(this.convertBySystemId(items, (String)systemId)));
        return relatedItemOffsetRelS;
    }

    public List<GcOffsetRelatedItemEO> convertBySystemId(List<GcRelatedItemEO> items, String systemId) {
        ArrayList<GcOffsetRelatedItemEO> gcOffsetRelatedItemS = new ArrayList<GcOffsetRelatedItemEO>();
        String period = FinancialCheckOffsetUtils.convertPeriodBySystemId(systemId, this.dataTime);
        if (StringUtils.isEmpty((String)period)) {
            return gcOffsetRelatedItemS;
        }
        this.periodStr = period.toString();
        ConsolidatedTaskVO taskVO = this.getTaskVOForSystem(systemId);
        if (taskVO == null) {
            return gcOffsetRelatedItemS;
        }
        this.taskKey = taskVO.getTaskKey();
        HashSet<String> ruleTypes = new HashSet<String>();
        ruleTypes.add(RuleTypeEnum.FINANCIAL_CHECK.getCode());
        List rules = this.unionRuleService.selectRuleListByReportSystemAndRuleTypes(systemId, ruleTypes);
        YearPeriodObject yp = new YearPeriodObject(null, this.periodStr);
        this.conTool = GcOrgPublicTool.getInstance((String)GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.taskKey), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        this.finalChkTool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map<String, GcFcRuleUnOffsetDataDTO> relatedItemEOMap = this.getMemoryConversionMap(FinancialCheckRuleExecutorImpl.convertOrigin2RuleUnOffsetData(items));
        items.forEach(item -> {
            GcOffsetRelatedItemEO relatedItemOffsetRelEO = this.initRelatedItem((GcRelatedItemEO)item, systemId);
            this.convertUnit(relatedItemOffsetRelEO);
            this.convertSubject(relatedItemOffsetRelEO);
            if (CollectionUtils.isEmpty(rules)) {
                relatedItemOffsetRelEO.setUnionRuleId("");
                relatedItemOffsetRelEO.setMatchingInformation(MatchingInformationEnum.NO_FOUND_RULE.getMessage());
            } else {
                Optional<FinancialCheckRuleDTO> first = rules.stream().map(rule -> this.mappingRule((GcRelatedItemEO)item, (AbstractUnionRule)rule)).filter(Objects::nonNull).findFirst();
                if (first.isPresent()) {
                    relatedItemOffsetRelEO.setUnionRuleId(first.get().getId());
                    relatedItemOffsetRelEO.setMatchingInformation("");
                } else {
                    relatedItemOffsetRelEO.setUnionRuleId("");
                    relatedItemOffsetRelEO.setMatchingInformation(MatchingInformationEnum.NO_FOUND_RULE.getMessage());
                }
            }
            this.memoryConversion((GcFcRuleUnOffsetDataDTO)relatedItemEOMap.get(item.getId()), relatedItemOffsetRelEO);
            gcOffsetRelatedItemS.add(relatedItemOffsetRelEO);
        });
        return gcOffsetRelatedItemS;
    }

    private void memoryConversion(GcFcRuleUnOffsetDataDTO unOffsetData, GcOffsetRelatedItemEO relatedItemOffsetRelEO) {
        Integer direct = unOffsetData.getDc();
        if (null == direct) {
            direct = OrientEnum.D.getValue();
        }
        if (direct.equals(OrientEnum.D.getValue())) {
            relatedItemOffsetRelEO.setDebitConversionValue(unOffsetData.getAmt());
        } else {
            relatedItemOffsetRelEO.setCreditConversionValue(unOffsetData.getAmt());
        }
        relatedItemOffsetRelEO.setConversionCurr(unOffsetData.getOffsetCurrency());
        relatedItemOffsetRelEO.setAmt(unOffsetData.getAmt());
        relatedItemOffsetRelEO.setConversionRate(unOffsetData.getConversionRate());
    }

    private Map<String, GcFcRuleUnOffsetDataDTO> getMemoryConversionMap(List<GcFcRuleUnOffsetDataDTO> items) {
        this.memoryConversion(items);
        return items.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity(), (v1, v2) -> v2));
    }

    public Map<String, String> matchUnionRuleBySystem(List<GcOffsetRelatedItemEO> offsetRelatedItems, String systemId) {
        HashMap<String, String> matchResult = new HashMap<String, String>();
        HashSet<String> ruleTypes = new HashSet<String>();
        ruleTypes.add(RuleTypeEnum.FINANCIAL_CHECK.getCode());
        List rules = this.unionRuleService.selectRuleListByReportSystemAndRuleTypes(systemId, ruleTypes);
        if (CollectionUtils.isEmpty(rules)) {
            return matchResult;
        }
        List relatedIds = offsetRelatedItems.stream().map(GcOffsetRelatedItemEO::getRelatedItemId).collect(Collectors.toList());
        List items = this.relatedItemDao.queryByIds(relatedIds);
        if (CollectionUtils.isEmpty(items)) {
            return matchResult;
        }
        items.forEach(item -> {
            Optional<FinancialCheckRuleDTO> first = rules.stream().map(rule -> this.mappingRule((GcRelatedItemEO)item, (AbstractUnionRule)rule)).filter(Objects::nonNull).findFirst();
            if (first.isPresent()) {
                matchResult.put(item.getId(), first.get().getId());
            }
        });
        return matchResult;
    }

    public void memoryConversion(List<GcFcRuleUnOffsetDataDTO> records) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(this.periodStr, this.taskKey);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        if (Objects.isNull(schemePeriodLinkDefine)) {
            this.logger.error("\u83b7\u53d6\u4e0d\u5230\u62a5\u8868\u65b9\u6848");
            return;
        }
        String schemeId = schemePeriodLinkDefine.getSchemeKey();
        ConversionSystemTaskEO conversionSystemTaskEO = this.conversionSystemTaskDao.queryByTaskAndScheme(this.taskKey, schemeId);
        if (Objects.isNull(conversionSystemTaskEO)) {
            this.logger.error("\u83b7\u53d6\u4e0d\u5230\u6298\u7b97\u4f53\u7cfb");
            return;
        }
        Map<String, String> subjectCode2RateTypeMap = this.getSubjectCode2RateTypeMap();
        Map<String, String> accountSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        records.forEach(unOffsetData -> {
            GcOrgCacheVO org = this.conTool.getCommonUnit(this.findConOrgByFinalChkOrg(unOffsetData.getUnitId()), this.findConOrgByFinalChkOrg(unOffsetData.getOppUnitId()));
            if (!Objects.nonNull(org)) {
                return;
            }
            String currency = StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID"));
            if (!StringUtil.equals((String)unOffsetData.getOffsetCurrency(), (String)currency)) {
                String subjectCode = unOffsetData.getSubjectCode();
                String rateType = this.getSubjectRateType(subjectCode, subjectCode2RateTypeMap, accountSubjectCode2ParentCodeMap);
                if (StringUtil.isEmpty((String)rateType)) {
                    this.logger.error("\u7edf\u4e00\u79d1\u76ee\uff1a" + subjectCode + "\u672a\u914d\u7f6e\u6c47\u7387\u7c7b\u578b");
                    unOffsetData.setAmt(Double.valueOf(new BigDecimal("0.00").doubleValue()));
                    return;
                }
                Map rateInfos = this.conversionRateService.getRateInfosByRateTypeCode(conversionSystemTaskEO.getRateSchemeCode(), schemeId, unOffsetData.getOffsetCurrency(), currency, this.periodStr, rateType);
                BigDecimal rate = (BigDecimal)rateInfos.get(rateType);
                if (rate == null) {
                    this.logger.error(MessageFormat.format("\u65f6\u671f\uff1a{0} {1}\u8f6c{2}\u65e0\u5bf9\u5e94\u6c47\u7387\uff0c\u5e01\u79cd\u6298\u7b97\u5931\u8d25", defaultPeriodAdapter.getPeriodTitle(this.periodStr), unOffsetData.getOffsetCurrency(), currency));
                    unOffsetData.setAmt(Double.valueOf(new BigDecimal("0.00").doubleValue()));
                    return;
                }
                unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).multiply(rate).doubleValue()));
                unOffsetData.setOffsetCurrency(currency);
                unOffsetData.setConversionRate(Double.valueOf(rate.doubleValue()));
            }
        });
    }

    public List<GcRelatedItemEO> filterByApplicableCondition(List<GcRelatedItemEO> items, AbstractUnionRule rule, GcCalcArgmentsDTO args) {
        String ruleCondition = rule.getRuleCondition();
        try {
            if (StringUtils.isEmpty((String)ruleCondition)) {
                return items;
            }
            YearPeriodObject yp = new YearPeriodObject(null, args.getPeriodStr());
            this.conTool = GcOrgPublicTool.getInstance((String)GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)args.getTaskId()), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO org = this.conTool.getCommonUnit(this.findConOrgByFinalChkOrg(items.get(0).getUnitId()), this.findConOrgByFinalChkOrg(items.get(0).getOppUnitId()));
            if (!Objects.nonNull(org)) {
                return Collections.EMPTY_LIST;
            }
            return items.stream().filter(item -> {
                Map fields = item.getFields();
                DimensionValueSet ds = DimensionUtils.generateDimSet(fields.get("MDCODE"), (Object)args.getPeriodStr(), (Object)args.getCurrency(), (Object)args.getOrgType(), (String)((String)item.getFieldValue("ADJUST")), (String)args.getTaskId());
                boolean success = this.inputDataFormulaEvalService.checkMxUnOffsetData(ds, ruleCondition, (GcRelatedItemEO)item, args.getTaskId(), args.getPeriodStr());
                return success;
            }).collect(Collectors.toList());
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u516c\u5f0f\u53d1\u751f\u5f02\u5e38", e);
            return Collections.EMPTY_LIST;
        }
    }

    private String getSubjectRateType(String subjectCode, Map<String, String> subjectCode2RateTypeMap, Map<String, String> accountSubjectCode2ParentCodeMap) {
        String rateType = subjectCode2RateTypeMap.get(subjectCode);
        if (StringUtil.isEmpty((String)rateType)) {
            String parentCode = accountSubjectCode2ParentCodeMap.get(subjectCode);
            if (StringUtil.isEmpty((String)parentCode) || StringUtil.equals((String)"-", (String)parentCode)) {
                return null;
            }
            rateType = this.getSubjectRateType(parentCode, subjectCode2RateTypeMap, accountSubjectCode2ParentCodeMap);
            subjectCode2RateTypeMap.put(subjectCode, rateType);
            return rateType;
        }
        int lastIndex = rateType.lastIndexOf(95);
        if (lastIndex != -1) {
            rateType = rateType.substring(0, lastIndex);
        }
        return rateType;
    }

    private Map<String, String> getSubjectCode2RateTypeMap() {
        List schemes = this.mapper.select((Object)new ConvertRateSchemeDO());
        HashMap<String, String> subjectCode2RateTypeMap = new HashMap<String, String>();
        if (null == schemes) {
            return subjectCode2RateTypeMap;
        }
        for (ConvertRateSchemeDO scheme : schemes) {
            subjectCode2RateTypeMap.put(scheme.getSubjectCode(), scheme.getCfRateType());
        }
        return subjectCode2RateTypeMap;
    }

    private void convertUnit(GcOffsetRelatedItemEO item) {
        String localOrgCode = item.getUnitId();
        String oppOrgCode = item.getOppUnitId();
        GcOrgCacheVO oppUnitCacheVO = this.findConOrgByFinalChkOrg(oppOrgCode);
        GcOrgCacheVO localCacheVO = this.findConOrgByFinalChkOrg(localOrgCode);
        item.setGcOppUnitId(this.findConOrgCodeByFinalChkOrg(oppUnitCacheVO));
        item.setGcUnitId(this.findConOrgCodeByFinalChkOrg(localCacheVO));
        if (this.unitIsNull(item)) {
            item.setMatchingInformation("\u5355\u4f4d\u8f6c\u6362\u5931\u8d25\u3002");
        }
    }

    private String findConOrgCodeByFinalChkOrg(GcOrgCacheVO orgCacheVO) {
        if (null == orgCacheVO) {
            return null;
        }
        return orgCacheVO.getCode();
    }

    private GcOrgCacheVO findConOrgByFinalChkOrg(String orgCode) {
        GcOrgCacheVO org = this.conTool.getOrgByCode(orgCode);
        if (Objects.nonNull(org)) {
            return org;
        }
        GcOrgCacheVO localOrg = this.finalChkTool.getOrgByCode(orgCode);
        if (Objects.isNull(localOrg)) {
            this.logger.error("\u5355\u4f4d:" + orgCode + "\u5728" + this.finalChkTool.getCurrOrgType().getTitle() + "\u4e2d\u67e5\u8be2\u4e0d\u5230");
            return null;
        }
        String parentId = localOrg.getParentId();
        if (StringUtil.equals((String)"-", (String)parentId)) {
            this.logger.error("\u5355\u4f4d:" + orgCode + "\u5728\u5408\u5e76\u5355\u4f4d\u57fa\u7840\u6570\u636e\u4e2d\u67e5\u8be2\u4e0d\u5230");
            return null;
        }
        return this.findConOrgByFinalChkOrg(parentId);
    }

    private FinancialCheckRuleDTO mappingRule(GcRelatedItemEO item, AbstractUnionRule rule) {
        FinancialCheckRuleDTO fcRule = (FinancialCheckRuleDTO)rule;
        if (fcRule.isChecked() && !CheckStateEnum.CHECKED.name().equals(item.getChkState()) || !fcRule.isChecked() && CheckStateEnum.CHECKED.name().equals(item.getChkState())) {
            return null;
        }
        try {
            Set<String> allBoundSubjects = this.ruleConverter.getAllBoundSubjects(fcRule);
            if (!allBoundSubjects.contains(item.getSubjectCode())) {
                return null;
            }
            if (this.checkApplicableCondition(item, rule)) {
                return fcRule;
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    private boolean checkApplicableCondition(GcRelatedItemEO item, AbstractUnionRule rule) {
        String ruleCondition = rule.getRuleCondition();
        try {
            if (StringUtils.isEmpty((String)ruleCondition)) {
                return true;
            }
            GcOrgCacheVO org = this.conTool.getCommonUnit(this.findConOrgByFinalChkOrg(item.getUnitId()), this.findConOrgByFinalChkOrg(item.getOppUnitId()));
            if (!Objects.nonNull(org)) {
                return false;
            }
            DimensionValueSet ds = DimensionUtils.generateDimSet((Object)org.getCode(), (Object)this.periodStr, (Object)org.getTypeFieldValue("CURRENCYID"), (Object)org.getOrgTypeId(), (String)((String)item.getFieldValue("ADJUST")), (String)this.taskKey);
            boolean success = this.inputDataFormulaEvalService.checkMxUnOffsetData(ds, ruleCondition, item, this.taskKey, this.periodStr);
            return success;
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u516c\u5f0f\u53d1\u751f\u5f02\u5e38", e);
            return false;
        }
    }

    private boolean unitIsNull(GcOffsetRelatedItemEO item) {
        return Objects.isNull(item.getGcUnitId()) || Objects.isNull(item.getGcOppUnitId());
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private boolean checkFormula(@NotNull DimensionValueSet ds, String formula, @NotNull GcOffsetRelatedItemEO item) {
        GcReportExceutorContext context = this.newExecutorContext();
        context.setOrgId(String.valueOf(item.getFieldValue("COMMONUNIT")));
        context.setTaskId(this.taskKey);
        GcReportDataSet dataSet = context.getDataSet();
        context.setData((DefaultTableEntity)item);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                try {
                    evaluator.prepare((ExecutorContext)context, ds, formula);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                }
                GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
                row.setData((DefaultTableEntity)item);
                try {
                    boolean bl = evaluator.judge((DataRow)row);
                    return bl;
                }
                catch (Exception e) {
                    try {
                        throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u521b\u5efa\u6570\u636e\u96c6\u8868\u8fbe\u5f0f\u53d6\u6570\u63a5\u53e3\u5b9e\u4f8b\u5931\u8d25", (Throwable)e);
        }
    }

    private GcReportExceutorContext newExecutorContext() {
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        try {
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName("GC_OFFSETRELATEDITEM");
            context.setDataSet(new GcReportDataSet(new String[]{"GC_OFFSETRELATEDITEM"}));
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    private void convertSubject(GcOffsetRelatedItemEO offsetRelatedItem) {
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String systemId = offsetRelatedItem.getSystemId();
        Set<String> conSubjectSet = consolidatedSubjectService.listAllSubjectsBySystemId(systemId).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        String conSubjectCode = this.ruleConverter.getConsSubjectCode(offsetRelatedItem.getSubjectCode(), this.accountSubjectCode2ParentCodeMap, conSubjectSet);
        if (Objects.isNull(conSubjectCode)) {
            this.logger.info("\u96c6\u56e2\u79d1\u76ee\uff1a" + offsetRelatedItem.getSubjectCode() + "\u627e\u4e0d\u5230\u76f8\u5e94\u7684\u5408\u5e76\u79d1\u76ee\u6620\u5c04");
            offsetRelatedItem.setMatchingInformation("\u96c6\u56e2\u79d1\u76ee\uff1a" + offsetRelatedItem.getSubjectCode() + "\u627e\u4e0d\u5230\u76f8\u5e94\u7684\u5408\u5e76\u79d1\u76ee\u6620\u5c04");
        } else {
            offsetRelatedItem.setGcSubjectCode(conSubjectCode);
        }
    }

    private GcOffsetRelatedItemEO initRelatedItem(GcRelatedItemEO item, String systemId) {
        GcOffsetRelatedItemEO offsetRelatedItem = new GcOffsetRelatedItemEO();
        BeanUtils.copyProperties(item, offsetRelatedItem);
        offsetRelatedItem.setId(UUIDUtils.newUUIDStr());
        offsetRelatedItem.setRelatedItemId(item.getId());
        offsetRelatedItem.setCheckState(item.getChkState());
        offsetRelatedItem.setRtOffsetCanDel(Integer.valueOf(0));
        offsetRelatedItem.setSystemId(systemId);
        offsetRelatedItem.setDataTime(this.periodStr);
        offsetRelatedItem.setSrcTimestamp(item.getRecordTimestamp());
        List dimensions = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_OFFSETRELATEDITEM");
        List<String> dimensionCodes = dimensions.stream().map(DimensionEO::getCode).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dimensionCodes)) {
            dimensionCodes.forEach(dim -> offsetRelatedItem.addFieldValue(dim, item.getFieldValue(dim)));
        }
        return offsetRelatedItem;
    }

    private Set<String> listAllSystemIds() {
        List consolidatedSystemEOS = this.systemService.getConsolidatedSystemEOS();
        if (CollectionUtils.isEmpty(consolidatedSystemEOS)) {
            return Collections.emptySet();
        }
        return consolidatedSystemEOS.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
    }

    private ConsolidatedTaskVO getTaskVOForSystem(String systemId) {
        List consolidatedTasks = this.taskCacheService.listConsolidatedTaskBySystemIdAndPeriod(systemId, this.periodStr);
        if (CollectionUtils.isEmpty(consolidatedTasks)) {
            this.logger.info("\u83b7\u53d6\u4e0d\u5230\u4f53\u7cfb\u4e0b\u7684\u4efb\u52a1,systemId:[" + systemId + "],dataTime:[" + this.periodStr + "]");
            return null;
        }
        return (ConsolidatedTaskVO)consolidatedTasks.get(0);
    }
}

