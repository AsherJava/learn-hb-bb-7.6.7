/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.assaging.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.gather.IAgingDataLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceData;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingExecuteContext;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class AssAgingBalanceModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private IAgingDataLoaderGather loaderGather;

    public String getComputationModelCode() {
        return ComputationModelEnum.ASSAGINGBALANCE.getCode();
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        AgingExecuteContext executeContext = (AgingExecuteContext)((Object)BeanConvertUtil.convert((Object)fetchTaskContext, AgingExecuteContext.class, (String[])new String[0]));
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)fetchSettingCacheKey.getOptimizeRuleGroup());
        executeContext.setAgingFetchType(AgingFetchTypeEnum.fromName((String)((String)optimizeMap.get("agingFetchType"))));
        executeContext.setAgingPeriodType(AgingPeriodTypeEnum.fromCode((String)((String)optimizeMap.get("agingRangeType"))));
        executeContext.setAgingStartPeriod(Integer.parseInt(optimizeMap.get("agingRangeStart").toString()));
        executeContext.setAgingEndPeriod(Integer.parseInt(optimizeMap.get("agingRangeEnd").toString()));
        HashMap<String, Dimension> assTypeMap = new HashMap<String, Dimension>(5);
        String dimType = (String)optimizeMap.get("dimType");
        if (!StringUtils.isEmpty((String)dimType)) {
            String[] dimTypeArr;
            for (String str : dimTypeArr = dimType.split(",")) {
                Dimension dimension = new Dimension(str);
                assTypeMap.put(str, dimension);
            }
        }
        for (Dimension dimComb : ModelExecuteUtil.parseAssTypeList(optimizeMap.get("dimComb"))) {
            assTypeMap.put(dimComb.getDimCode(), dimComb);
        }
        Assert.isNotEmpty(assTypeMap, (String)"\u8d26\u9f84\u6a21\u578b\u5fc5\u987b\u5305\u542b\u5ba2\u6237\u3001\u4f9b\u5e94\u5546\u6216\u5ba2\u5546", (Object[])new Object[0]);
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
    protected List<FetchResult> doExecute(ModelExecuteContext orgnExecuteContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        AgingExecuteContext executeContext = (AgingExecuteContext)orgnExecuteContext;
        AgingBalanceCondition condi = new AgingBalanceCondition(orgnExecuteContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), executeContext.getAssTypeList(), executeContext.getOrgMapping(), (boolean)executeContext.getIncludeUncharged());
        condi.setComputationModel(this.getComputationModelCode());
        condi.initAgingArgs(executeContext.getAgingFetchType(), executeContext.getAgingPeriodType(), executeContext.getAgingStartPeriod(), executeContext.getAgingEndPeriod());
        condi.setCallBackIp(executeContext.getCallBackAddress());
        this.setAdjustPeriod(executeContext, condi);
        condi.setDimensionValueMap(this.getDimensionValueMap(executeContext.getDimensionSetStr()));
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        Map<String, Map<String, List<AgingBalanceData>>> balanceData = this.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", balanceData.size()));
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            Map<String, Dimension> dimMap = this.parseDimMap(executeSettingVO.getDimensionSetting());
            Map<String, FilterRule> filterRuleMap = this.parseFilterRule(dimMap);
            FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getSubjectCode());
            FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getExcludeSubjectCode());
            FetchResult result = new FetchResult(executeSettingVO);
            for (Map.Entry<String, Map<String, List<AgingBalanceData>>> balanceKmEntry : balanceData.entrySet()) {
                if (!StringUtils.isEmpty((String)executeSettingVO.getExcludeSubjectCode()) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(excludesubjectFilterRule)) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(subjectFilterRule))) continue;
                for (Map.Entry<String, List<AgingBalanceData>> balanceAssistEntry : balanceKmEntry.getValue().entrySet()) {
                    block3: for (AgingBalanceData assBalance : balanceAssistEntry.getValue()) {
                        if (!StringUtils.isEmpty((String)executeSettingVO.getCurrencyCode()) && !ModelExecuteUtil.match(executeSettingVO.getCurrencyCode(), assBalance.getCurrencyCode())) continue;
                        if (!dimMap.isEmpty()) {
                            for (Map.Entry<String, String> assistEntry : assBalance.getAssistMap().entrySet()) {
                                if (dimMap.get(assistEntry.getKey()) == null) continue;
                                IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(dimMap.get(assistEntry.getKey()).getDimRule());
                                FilterRule assistFilterRule = filterRuleMap.get(assistEntry.getKey());
                                if (assistMatcher.match(matchCondi.withAssistCode(assistEntry.getKey()).withDataCode(assistEntry.getValue()).withFilterRule(assistFilterRule))) continue;
                                continue block3;
                            }
                        }
                        result.addZbValue(ModelExecuteUtil.getFetchVal(assBalance, executeSettingVO.getFetchType()));
                    }
                }
            }
            resultList.add(result);
        }
        return resultList;
    }

    private Map<String, Map<String, List<AgingBalanceData>>> convertResult(AgingBalanceCondition condi, FetchData cache) {
        HashMap<String, Map<String, List<AgingBalanceData>>> result = new HashMap<String, Map<String, List<AgingBalanceData>>>(128);
        for (Object[] rowData : cache.getRowDatas()) {
            String fixedAssistKey;
            String subjectCode = cache.getString(rowData, "SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            StringBuilder assistKey = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if ("EQ".equals(assType.getDimRule())) {
                    optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
                }
                assistMap.put(assType.getDimCode(), cache.getString(rowData, assType.getDimCode()));
                assistKey.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(subjectCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(subjectCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            AgingBalanceData balanceData = new AgingBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
            if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
                balanceData.setHxnc(cache.getBigDecimal(rowData, FetchTypeEnum.HXNC.name()));
                balanceData.setWhxnc(cache.getBigDecimal(rowData, FetchTypeEnum.WHXNC.name()));
            } else {
                balanceData.setHxye(cache.getBigDecimal(rowData, FetchTypeEnum.HXYE.name()));
                balanceData.setWhxye(cache.getBigDecimal(rowData, FetchTypeEnum.WHXYE.name()));
            }
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }

    protected Map<String, Map<String, List<AgingBalanceData>>> loadData(AgingBalanceCondition condi) {
        AbstractAgingDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return this.convertResult(condi, (FetchData)loader.loadData(condi));
    }
}

