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
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.djye.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.assbalance.AbstractAssBalanceDataLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.gather.IAssBalanceDataLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
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
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class DjyeBalanceModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private IAssBalanceDataLoaderGather loaderGather;

    public String getComputationModelCode() {
        return ComputationModelEnum.DJYEBALANCE.getCode();
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        ModelExecuteContext executeContext = ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)fetchSettingCacheKey.getOptimizeRuleGroup());
        HashMap<String, Dimension> assTypeMap = new HashMap<String, Dimension>(5);
        String dimType = (String)optimizeMap.get("dimType");
        if (StringUtils.isEmpty((String)dimType)) {
            return executeContext;
        }
        String[] dimTypeArr = dimType.split(",");
        for (String str : dimTypeArr) {
            Dimension dimension = new Dimension(str, Boolean.valueOf(false));
            assTypeMap.put(str, dimension);
        }
        for (Dimension dimComb : ModelExecuteUtil.parseAssTypeList(optimizeMap.get("dimComb"))) {
            assTypeMap.put(dimComb.getDimCode(), dimComb);
        }
        executeContext.setAssTypeList(assTypeMap.values().stream().sorted(Comparator.comparing(Dimension::getDimCode)).collect(Collectors.toList()));
        return executeContext;
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO executeSettingVO = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        executeSettingVO.setSubjectCode(VariableParseUtil.parse((String)executeSettingVO.getSubjectCode(), rowData));
        executeSettingVO.setCurrencyCode(VariableParseUtil.parse((String)executeSettingVO.getCurrencyCode(), rowData));
        executeSettingVO.setDimensionSetting(VariableParseUtil.parse((String)executeSettingVO.getDimensionSetting(), rowData));
        return executeSettingVO;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        List<Dimension> dimensionList = executeContext.getAssTypeList().stream().filter(dimension -> !dimension.getDimCode().equals("SUBJECTCODE")).collect(Collectors.toList());
        BalanceCondition condi = new BalanceCondition(executeContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), dimensionList, executeContext.getOrgMapping(), executeContext.getIncludeUncharged());
        condi.setComputationModel(this.getComputationModelCode());
        condi.setCallBackIp(executeContext.getCallBackAddress());
        this.setDirectFilter(condi, executeContext, fetchSettingList);
        this.setAdjustPeriod(executeContext, condi);
        condi.setDimensionValueMap(this.getDimensionValueMap(executeContext.getDimensionSetStr()));
        condi.setOtherEntity(executeContext.getOtherEntity() == null ? new HashMap() : executeContext.getOtherEntity());
        condi.setBblX(executeContext.getBblx());
        condi.setRpUnitType(executeContext.getRpUnitType());
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        Map<String, Map<String, List<AssBalanceData>>> balanceDataList = this.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", balanceDataList.size()));
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            FetchResult result = new FetchResult(executeSettingVO);
            result.addZbValue(this.sumByData(matchCondi, executeSettingVO, balanceDataList));
            resultList.add(result);
        }
        return resultList;
    }

    private BigDecimal sumByData(BaseDataMatchCondi matchCondi, ExecuteSettingVO fetchSetting, Map<String, Map<String, List<AssBalanceData>>> balanceData) {
        FetchTypeEnum fetchType = FetchTypeEnum.fromName((String)fetchSetting.getFetchType());
        if (fetchType == FetchTypeEnum.UNKNOWN) {
            throw new BusinessRuntimeException(String.format("\u53d6\u6570\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", fetchSetting.getFetchType()));
        }
        BigDecimal zbValue = BigDecimal.ZERO;
        Map<String, Dimension> dimMap = this.parseDimMap(fetchSetting.getDimensionSetting());
        HashMap<String, BigDecimal> sumVal = new HashMap<String, BigDecimal>(128);
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        BigDecimal val = BigDecimal.ZERO;
        Map<String, FilterRule> filterRuleMap = this.parseFilterRule(dimMap);
        FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(fetchSetting.getSubjectCode());
        FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule(fetchSetting.getExcludeSubjectCode());
        for (Map.Entry<String, Map<String, List<AssBalanceData>>> balanceKmEntry : balanceData.entrySet()) {
            if (!StringUtils.isEmpty((String)fetchSetting.getExcludeSubjectCode()) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(excludesubjectFilterRule)) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(subjectFilterRule))) continue;
            for (Map.Entry<String, List<AssBalanceData>> balanceAssistEntry : balanceKmEntry.getValue().entrySet()) {
                block5: for (AssBalanceData assBalance : balanceAssistEntry.getValue()) {
                    if (!StringUtils.isEmpty((String)fetchSetting.getCurrencyCode()) && !ModelExecuteUtil.match(fetchSetting.getCurrencyCode(), assBalance.getCurrencyCode())) continue;
                    for (Map.Entry<String, String> assistEntry : assBalance.getAssistMap().entrySet()) {
                        if (dimMap.get(assistEntry.getKey()) == null) continue;
                        IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(dimMap.get(assistEntry.getKey()).getDimRule());
                        FilterRule assistFilterRule = filterRuleMap.get(assistEntry.getKey());
                        if (assistMatcher.match(matchCondi.withAssistCode(assistEntry.getKey()).withDataCode(assistEntry.getValue()).withFilterRule(assistFilterRule))) continue;
                        continue block5;
                    }
                    val = this.getVal(fetchType, assBalance);
                    sumVal.computeIfAbsent(assBalance.getAssistKey(), key -> BigDecimal.ZERO);
                    sumVal.put(assBalance.getAssistKey(), NumberUtils.sum((BigDecimal)((BigDecimal)sumVal.get(assBalance.getAssistKey())), (BigDecimal)val));
                }
            }
        }
        block7: for (BigDecimal sVal : sumVal.values()) {
            switch (fetchType) {
                case JNC: 
                case JYH: 
                case WJNC: 
                case WJYH: {
                    if (sVal.compareTo(BigDecimal.ZERO) <= 0) continue block7;
                    zbValue = NumberUtils.sum((BigDecimal)zbValue, (BigDecimal)sVal);
                    continue block7;
                }
            }
            if (sVal.compareTo(BigDecimal.ZERO) >= 0) continue;
            zbValue = NumberUtils.sum((BigDecimal)zbValue, (BigDecimal)sVal.abs());
        }
        return zbValue.abs();
    }

    private BigDecimal getVal(FetchTypeEnum fetchType, AssBalanceData assBalance) {
        switch (fetchType) {
            case JNC: 
            case DNC: {
                return assBalance.getNc();
            }
            case JYH: 
            case DYH: {
                return assBalance.getYe();
            }
            case WJNC: 
            case WDNC: {
                return assBalance.getWnc();
            }
            case WJYH: 
            case WDYH: {
                return assBalance.getWye();
            }
        }
        return BigDecimal.ZERO;
    }

    protected Map<String, Map<String, List<AssBalanceData>>> loadData(BalanceCondition condi) {
        AbstractAssBalanceDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return this.convertResult(condi, (FetchData)loader.loadData(condi));
    }

    private Map<String, Map<String, List<AssBalanceData>>> convertResult(BalanceCondition condi, FetchData cache) {
        HashMap<String, Map<String, List<AssBalanceData>>> result = new HashMap<String, Map<String, List<AssBalanceData>>>(128);
        block0: for (Object[] rowData : cache.getRowDatas()) {
            String subjectCode = cache.getString(rowData, "SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            StringBuilder assistKey = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if (StringUtils.isEmpty((String)cache.getString(rowData, assType.getDimCode()))) continue block0;
                if ("EQ".equals(assType.getDimRule())) {
                    optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
                }
                assistMap.put(assType.getDimCode(), cache.getString(rowData, assType.getDimCode()));
                assistKey.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
            }
            String fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            ((Map)result.get(subjectCode)).computeIfAbsent(fixedAssistKey, k -> new ArrayList(32));
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            AssBalanceData balanceData = new AssBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setOrient(cache.getBigDecimal(rowData, "ORIENT").intValue());
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
            balanceData.setNc(cache.getBigDecimal(rowData, FetchTypeEnum.NC.name()));
            balanceData.setC(cache.getBigDecimal(rowData, FetchTypeEnum.C.name()));
            balanceData.setJf(cache.getBigDecimal(rowData, FetchTypeEnum.JF.name()));
            balanceData.setDf(cache.getBigDecimal(rowData, FetchTypeEnum.DF.name()));
            balanceData.setJl(cache.getBigDecimal(rowData, FetchTypeEnum.JL.name()));
            balanceData.setDl(cache.getBigDecimal(rowData, FetchTypeEnum.DL.name()));
            balanceData.setYe(cache.getBigDecimal(rowData, FetchTypeEnum.YE.name()));
            balanceData.setWnc(cache.getBigDecimal(rowData, FetchTypeEnum.WNC.name()));
            balanceData.setWc(cache.getBigDecimal(rowData, FetchTypeEnum.WC.name()));
            balanceData.setWjf(cache.getBigDecimal(rowData, FetchTypeEnum.WJF.name()));
            balanceData.setWdf(cache.getBigDecimal(rowData, FetchTypeEnum.WDF.name()));
            balanceData.setWjl(cache.getBigDecimal(rowData, FetchTypeEnum.WJL.name()));
            balanceData.setWdl(cache.getBigDecimal(rowData, FetchTypeEnum.WDL.name()));
            balanceData.setWye(cache.getBigDecimal(rowData, FetchTypeEnum.WYE.name()));
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }
}

