/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.OrientEnum
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.invest.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.invest.AbstractInvestBillDataLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.invest.InvestBillDataLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.invest.InvestBillData;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.OrientEnum;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestBillModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private InvestBillDataLoaderGather loaderGather;

    public String getComputationModelCode() {
        return ComputationModelEnum.INVEST_BILL.getCode();
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        ModelExecuteContext executeContext = ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)fetchSettingCacheKey.getOptimizeRuleGroup());
        executeContext.setAssTypeList(ModelExecuteUtil.parseAssTypeList(optimizeMap.get("dimComb")));
        return executeContext;
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO executeSettingVO = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        executeSettingVO.setSubjectCode(VariableParseUtil.parse((String)executeSettingVO.getSubjectCode(), rowData));
        executeSettingVO.setCurrencyCode(VariableParseUtil.parse((String)executeSettingVO.getCurrencyCode(), rowData));
        executeSettingVO.setVchrUniqueCode(VariableParseUtil.parse((String)executeSettingVO.getVchrUniqueCode(), rowData));
        executeSettingVO.setDimensionSetting(VariableParseUtil.parse((String)executeSettingVO.getDimensionSetting(), rowData));
        return executeSettingVO;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        BalanceCondition condi = new BalanceCondition(executeContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), executeContext.getAssTypeList(), executeContext.getOrgMapping(), executeContext.getIncludeUncharged());
        condi.setComputationModel(this.getComputationModelCode());
        condi.setDimensionValueMap(this.getDimensionValueMap(executeContext.getDimensionSetStr()));
        condi.setOtherEntity(executeContext.getOtherEntity() == null ? new HashMap() : executeContext.getOtherEntity());
        condi.setBblX(executeContext.getBblx());
        condi.setRpUnitType(executeContext.getRpUnitType());
        this.setAdjustPeriod(executeContext, condi);
        condi.setExtParam(executeContext.getExtParam());
        this.setDirectFilter(condi, executeContext, fetchSettingList);
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        Map<String, Map<String, List<InvestBillData>>> balanceData = this.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", balanceData.size()));
        Map<String, Integer> subjectMap = this.loadSubject(condi);
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        int subjectOrient = OrientEnum.DEBIT.getCode();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            Map<String, Dimension> dimMap = this.parseDimMap(executeSettingVO.getDimensionSetting());
            Map<String, FilterRule> filterRuleMap = this.parseFilterRule(dimMap);
            FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getSubjectCode());
            FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getExcludeSubjectCode());
            FetchResult result = new FetchResult(executeSettingVO);
            for (Map.Entry<String, Map<String, List<InvestBillData>>> balanceKmEntry : balanceData.entrySet()) {
                if (!StringUtils.isEmpty((String)executeSettingVO.getExcludeSubjectCode()) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(excludesubjectFilterRule)) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(subjectFilterRule))) continue;
                for (Map.Entry<String, List<InvestBillData>> balanceAssistEntry : balanceKmEntry.getValue().entrySet()) {
                    block3: for (InvestBillData assBalance : balanceAssistEntry.getValue()) {
                        if (!StringUtils.isEmpty((String)executeSettingVO.getCurrencyCode()) && !ModelExecuteUtil.match(executeSettingVO.getCurrencyCode(), assBalance.getCurrencyCode()) || !StringUtils.isEmpty((String)executeSettingVO.getVchrUniqueCode()) && !executeSettingVO.getVchrUniqueCode().equals(assBalance.getVchrUniqueCode())) continue;
                        for (Map.Entry<String, String> assistEntry : assBalance.getAssistMap().entrySet()) {
                            if (dimMap.get(assistEntry.getKey()) == null) continue;
                            IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(dimMap.get(assistEntry.getKey()).getDimRule());
                            FilterRule assistFilterRule = filterRuleMap.get(assistEntry.getKey());
                            if (assistMatcher.match(matchCondi.withAssistCode(assistEntry.getKey()).withDataCode(assistEntry.getValue()).withFilterRule(assistFilterRule))) continue;
                            continue block3;
                        }
                        subjectOrient = this.getSubjectOrient(subjectMap, executeSettingVO.getSubjectCode(), assBalance);
                        if (this.fetchTypeCalcOrient(executeSettingVO.getFetchType()) && subjectOrient < 0) {
                            result.addZbValue(ModelExecuteUtil.getFetchVal(assBalance, executeSettingVO.getFetchType()).negate());
                            continue;
                        }
                        result.addZbValue(ModelExecuteUtil.getFetchVal(assBalance, executeSettingVO.getFetchType()));
                    }
                }
            }
            resultList.add(result);
        }
        return resultList;
    }

    protected Map<String, Map<String, List<InvestBillData>>> loadData(BalanceCondition condi) {
        AbstractInvestBillDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return this.convertResult(condi, (FetchData)loader.loadData(condi));
    }

    protected Map<String, Integer> loadSubject(BalanceCondition condi) {
        AbstractInvestBillDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return loader.loadSubject(condi);
    }

    private Map<String, Map<String, List<InvestBillData>>> convertResult(BalanceCondition condi, FetchData cache) {
        HashMap<String, Map<String, List<InvestBillData>>> result = new HashMap<String, Map<String, List<InvestBillData>>>(128);
        block0: for (Object[] rowData : cache.getRowDatas()) {
            String fixedAssistKey;
            String subjectCode = cache.getString(rowData, "SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if (cache.getString(rowData, assType.getDimCode()) == null) continue block0;
                assistMap.put(assType.getDimCode(), cache.getString(rowData, assType.getDimCode()));
                if (!"EQ".equals(assType.getDimRule())) continue;
                optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(subjectCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(subjectCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            InvestBillData balanceData = new InvestBillData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
            balanceData.setChangeScenario(cache.getString(rowData, "CHANGESCENARIO"));
            balanceData.setVchrUniqueCode(cache.getString(rowData, "VCHRUNIQUECODE"));
            balanceData.setNc(cache.getBigDecimal(rowData, FetchTypeEnum.NC.name()));
            balanceData.setC(cache.getBigDecimal(rowData, FetchTypeEnum.C.name()));
            balanceData.setJf(cache.getBigDecimal(rowData, FetchTypeEnum.JF.name()));
            balanceData.setDf(cache.getBigDecimal(rowData, FetchTypeEnum.DF.name()));
            balanceData.setJl(cache.getBigDecimal(rowData, FetchTypeEnum.JL.name()));
            balanceData.setDl(cache.getBigDecimal(rowData, FetchTypeEnum.DL.name()));
            balanceData.setYe(cache.getBigDecimal(rowData, FetchTypeEnum.YE.name()));
            balanceData.setZsc(cache.getBigDecimal(rowData, FetchTypeEnum.ZSC.name()));
            balanceData.setWnc(cache.getBigDecimal(rowData, FetchTypeEnum.WNC.name()));
            balanceData.setWc(cache.getBigDecimal(rowData, FetchTypeEnum.WC.name()));
            balanceData.setWjf(cache.getBigDecimal(rowData, FetchTypeEnum.WJF.name()));
            balanceData.setWdf(cache.getBigDecimal(rowData, FetchTypeEnum.WDF.name()));
            balanceData.setWjl(cache.getBigDecimal(rowData, FetchTypeEnum.WJL.name()));
            balanceData.setWdl(cache.getBigDecimal(rowData, FetchTypeEnum.WDL.name()));
            balanceData.setWye(cache.getBigDecimal(rowData, FetchTypeEnum.WYE.name()));
            balanceData.setAssistMap(assistMap);
        }
        return result;
    }
}

