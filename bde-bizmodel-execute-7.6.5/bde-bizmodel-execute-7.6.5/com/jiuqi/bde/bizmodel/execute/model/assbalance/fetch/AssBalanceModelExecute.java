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
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.jsonwebtoken.lang.Assert
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.assbalance.fetch;

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
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.jsonwebtoken.lang.Assert;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AssBalanceModelExecute
extends AbstractGenericModelExecute {
    private static final String BALANCEORGNENABLE = "DC_BALANCE_ORGN_ENABLE";
    @Autowired
    private IAssBalanceDataLoaderGather loaderGather;

    public String getComputationModelCode() {
        return ComputationModelEnum.ASSBALANCE.getCode();
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
        executeSettingVO.setDimensionSetting(VariableParseUtil.parse((String)executeSettingVO.getDimensionSetting(), rowData));
        return executeSettingVO;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        long startTime = System.currentTimeMillis();
        logContent.append(String.format("\u3010ModelExecute\u3011\u4efb\u52a1\u3010%1$s\u3011\u5f00\u59cb\u6267\u884c\uff1a\u6a21\u578b\u3010%2$s\u3011\uff0c\u5171\u6709%3$s\u6761\u53d6\u6570\u914d\u7f6e%n", executeContext.getTaskId(), this.getComputationModelCode(), fetchSettingList.size()));
        logContent.append(String.format("\u6267\u884c\u5f00\u59cb\u65f6\u95f4\uff1a%1$s%n", new Date(startTime)));
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        BalanceCondition condi = this.buildBalanceCondition(executeContext);
        condi.setComputationModel(this.getComputationModelCode());
        this.setAdjustPeriod(executeContext, condi);
        this.setDirectFilter(condi, executeContext, fetchSettingList);
        logContent.append(String.format("1.\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        FetchData fetchData = this.loadData(condi);
        logContent.append(String.format("2.\u52a0\u8f7d\u6570\u636e\u6210\u529f\uff0c\u5171\u6709%1$s\u6761\u6570\u636e, \u5b57\u6bb5\u6620\u5c04\u4e3a\uff1a%2$s%n", fetchData.getRowDatas().size(), fetchData.getColumns()));
        MultiDimensionCode multiDimensionCode = this.chooseMultiDimensionCode(condi, fetchSettingList, fetchData);
        String dimCode1 = multiDimensionCode.getDimCode(0);
        String dimCode2 = multiDimensionCode.getDimCode(1);
        String dimCode3 = multiDimensionCode.getDimCode(2);
        String dimCode4 = multiDimensionCode.getDimCode(3);
        logContent.append(String.format("3.\u591a\u7ea7\u5206\u7ec4 key \u9009\u62e9\u5b8c\u6210\uff1a%1$s, %2$s, %3$s, %4$s%n", dimCode1, dimCode2, dimCode3, dimCode4));
        Map<String, Map<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>>> balanceData = this.buildMultiLevelMap(condi, multiDimensionCode, fetchData);
        logContent.append("4.\u6784\u5efa\u591a\u7ea7\u5206\u7ec4\u6210\u529f\n");
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        Map<String, Integer> subjectMap = this.loadSubject(condi);
        HashMap<FilterRule, Map<String, Boolean>> memories = new HashMap<FilterRule, Map<String, Boolean>>();
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        logContent.append(String.format("5.\u5f00\u59cb\u5339\u914d\u53d6\u6570\u8bbe\u7f6e\u548c\u6570\u636e\uff0c\u5171\u6709%1$s\u6761\u53d6\u6570\u914d\u7f6e%n", fetchSettingList.size()));
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            String dimensionSetting = executeSettingVO.getDimensionSetting();
            String subjectCode = executeSettingVO.getSubjectCode();
            String excludeSubjectCode = executeSettingVO.getExcludeSubjectCode();
            Map<String, Dimension> dimMap = this.parseDimMap(dimensionSetting);
            Map<String, FilterRule> filterRuleMap = this.parseFilterRule(dimMap);
            FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(subjectCode);
            FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule(excludeSubjectCode);
            FetchResult result = new FetchResult(executeSettingVO);
            for (Map.Entry<String, Map<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>>> balanceDataMap : balanceData.entrySet()) {
                String dataSubjectValue = balanceDataMap.getKey();
                if (!StringUtils.isEmpty((String)excludeSubjectCode) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(dataSubjectValue).withFilterRule(excludesubjectFilterRule), memories) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(dataSubjectValue).withFilterRule(subjectFilterRule), memories)) continue;
                for (Map.Entry<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>> balanceDataMapOne : balanceDataMap.getValue().entrySet()) {
                    String dimValueOne = balanceDataMapOne.getKey();
                    if (this.dimensionNotMatched(dimValueOne, filterRuleMap, dimCode1, dimMap, matchCondi, memories)) continue;
                    for (Map.Entry<String, Map<String, Map<String, List<AssBalanceData>>>> balanceDataMapTwo : balanceDataMapOne.getValue().entrySet()) {
                        String dimValueTwo = balanceDataMapTwo.getKey();
                        if (this.dimensionNotMatched(dimValueTwo, filterRuleMap, dimCode2, dimMap, matchCondi, memories)) continue;
                        for (Map.Entry<String, Map<String, List<AssBalanceData>>> balanceDataMapThree : balanceDataMapTwo.getValue().entrySet()) {
                            String dimValueThree = balanceDataMapThree.getKey();
                            if (this.dimensionNotMatched(dimValueThree, filterRuleMap, dimCode3, dimMap, matchCondi, memories)) continue;
                            for (Map.Entry<String, List<AssBalanceData>> balanceDataMapFour : balanceDataMapThree.getValue().entrySet()) {
                                String dimValueFour = balanceDataMapFour.getKey();
                                if (this.dimensionNotMatched(dimValueFour, filterRuleMap, dimCode4, dimMap, matchCondi, memories)) continue;
                                block6: for (AssBalanceData assBalance : balanceDataMapFour.getValue()) {
                                    if (!StringUtils.isEmpty((String)executeSettingVO.getCurrencyCode()) && !ModelExecuteUtil.match(executeSettingVO.getCurrencyCode(), assBalance.getCurrencyCode())) continue;
                                    for (Map.Entry<String, String> assistEntry : assBalance.getAssistMap().entrySet()) {
                                        String dimCode = assistEntry.getKey();
                                        String dimValue = assistEntry.getValue();
                                        Dimension dimension = dimMap.get(dimCode);
                                        if (dimension == null) continue;
                                        IBaseDataMatcher assistMatcherOther = this.baseDataMatcherHandler.getAssistMatcher(dimension.getDimRule());
                                        FilterRule assistFilterRule = filterRuleMap.get(dimCode);
                                        if (assistMatcherOther.match(matchCondi.withAssistCode(dimCode).withDataCode(dimValue).withFilterRule(assistFilterRule), memories)) continue;
                                        continue block6;
                                    }
                                    int subjectOrient = this.getSubjectOrient(subjectMap, subjectCode, assBalance);
                                    BigDecimal fetchVal = ModelExecuteUtil.getFetchVal(assBalance, executeSettingVO.getFetchType());
                                    BigDecimal bigDecimal = fetchVal = fetchVal == null ? BigDecimal.ZERO : fetchVal;
                                    if (this.fetchTypeCalcOrient(executeSettingVO.getFetchType()) && subjectOrient < 0) {
                                        result.addZbValue(fetchVal.negate());
                                        continue;
                                    }
                                    result.addZbValue(fetchVal);
                                }
                            }
                        }
                    }
                }
            }
            resultList.add(result);
        }
        long end = System.currentTimeMillis();
        logContent.append(String.format("6. \u5339\u914d\u53d6\u6570\u8bbe\u7f6e\u548c\u6570\u636e\u6210\u529f\uff0c\u7ed3\u675f\u65f6\u95f4\uff1a%1$s\uff0c\u5171\u8017\u65f6\uff1a%2$sms%n", new Date(end), end - startTime));
        return resultList;
    }

    protected FetchData loadData(BalanceCondition condi) {
        AbstractAssBalanceDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return (FetchData)loader.loadData(condi);
    }

    protected Map<String, Integer> loadSubject(BalanceCondition condi) {
        AbstractAssBalanceDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return loader.loadSubject(condi);
    }

    private String getAssistDimensionCode(List<Dimension> assTypeList, int index) {
        if (CollectionUtils.isEmpty(assTypeList) || index >= assTypeList.size()) {
            return "";
        }
        return assTypeList.get(index) == null ? "" : assTypeList.get(index).getDimCode();
    }

    private MultiDimensionCode chooseMultiDimensionCode(BalanceCondition condi, List<ExecuteSettingVO> fetchSettingList, FetchData fetchData) {
        List<Dimension> assTypeList = condi.getAssTypeList();
        if (assTypeList.size() <= 4) {
            String[] result = new String[4];
            for (int i = 0; i < 4; ++i) {
                result[i] = this.getAssistDimensionCode(assTypeList, i);
            }
            return new MultiDimensionCode(result[0], result[1], result[2], result[3]);
        }
        Map<String, Long> dimUsageCount = fetchSettingList.stream().filter(vo -> !StringUtils.isEmpty((String)vo.getDimensionSetting())).map(vo -> this.parseDimMap(vo.getDimensionSetting())).flatMap(dimMap -> dimMap.values().stream()).collect(Collectors.groupingBy(Dimension::getDimCode, Collectors.counting()));
        List top4UsedDims = dimUsageCount.entrySet().stream().sorted((a, b) -> ((Long)b.getValue()).compareTo((Long)a.getValue())).map(Map.Entry::getKey).limit(4L).collect(Collectors.toList());
        HashMap<String, Integer> fieldGroupCount = new HashMap<String, Integer>(4);
        List<Object[]> rowDatas = fetchData.getRowDatas();
        for (String dimCode : top4UsedDims) {
            HashSet<String> uniqueValues = new HashSet<String>();
            for (Object[] row : rowDatas) {
                String value = fetchData.getString(row, dimCode);
                if (value == null) continue;
                uniqueValues.add(value);
            }
            fieldGroupCount.put(dimCode, uniqueValues.size());
        }
        List finalDims = fieldGroupCount.entrySet().stream().sorted((a, b) -> ((Integer)b.getValue()).compareTo((Integer)a.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
        Assert.isTrue((finalDims.size() == 4 ? 1 : 0) != 0);
        return new MultiDimensionCode((String)finalDims.get(0), (String)finalDims.get(1), (String)finalDims.get(2), (String)finalDims.get(3));
    }

    private boolean dimensionNotMatched(String dimValue, Map<String, FilterRule> filterRuleMap, String dimCode, Map<String, Dimension> dimMap, BaseDataMatchCondi matchCondi, Map<FilterRule, Map<String, Boolean>> memories) {
        if (StringUtils.isEmpty((String)dimValue)) {
            return false;
        }
        FilterRule filterRule = filterRuleMap.get(dimCode);
        IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(dimMap.get(dimCode).getDimRule());
        return filterRule != null && !assistMatcher.match(matchCondi.withAssistCode(dimCode).withDataCode(dimValue).withFilterRule(filterRule), memories);
    }

    protected Map<String, Map<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>>> buildMultiLevelMap(BalanceCondition condi, MultiDimensionCode multiDimensionCode, FetchData fetchData) {
        Boolean dcBalanceOriginCurrencyEnable = this.isDcBalanceOriginCurrencyEnable();
        HashMap<String, Map<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>>> result = new HashMap<String, Map<String, Map<String, Map<String, Map<String, List<AssBalanceData>>>>>>(128);
        for (Object[] rowData : fetchData.getRowDatas()) {
            String subjectCode = fetchData.getString(rowData, "SUBJECTCODE");
            String currencyCode = fetchData.getString(rowData, "CURRENCYCODE");
            String dimCode1 = fetchData.getString(rowData, multiDimensionCode.getDimCode(0));
            String dimCode2 = fetchData.getString(rowData, multiDimensionCode.getDimCode(1));
            String dimCode3 = fetchData.getString(rowData, multiDimensionCode.getDimCode(2));
            String dimCode4 = fetchData.getString(rowData, multiDimensionCode.getDimCode(3));
            result.computeIfAbsent(subjectCode, k -> new HashMap(64));
            ((Map)result.get(subjectCode)).computeIfAbsent(dimCode1, k -> new HashMap(32));
            ((Map)((Map)result.get(subjectCode)).get(dimCode1)).computeIfAbsent(dimCode2, k -> new HashMap(16));
            ((Map)((Map)((Map)result.get(subjectCode)).get(dimCode1)).get(dimCode2)).computeIfAbsent(dimCode3, k -> new HashMap(8));
            ((Map)((Map)((Map)((Map)result.get(subjectCode)).get(dimCode1)).get(dimCode2)).get(dimCode3)).computeIfAbsent(dimCode4, k -> new ArrayList(16));
            List list = (List)((Map)((Map)((Map)((Map)result.get(subjectCode)).get(dimCode1)).get(dimCode2)).get(dimCode3)).get(dimCode4);
            AssBalanceData balanceData = new AssBalanceData();
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(currencyCode);
            balanceData.setNc(fetchData.getBigDecimal(rowData, FetchTypeEnum.NC.name()));
            balanceData.setC(fetchData.getBigDecimal(rowData, FetchTypeEnum.C.name()));
            balanceData.setJf(fetchData.getBigDecimal(rowData, FetchTypeEnum.JF.name()));
            balanceData.setDf(fetchData.getBigDecimal(rowData, FetchTypeEnum.DF.name()));
            balanceData.setJl(fetchData.getBigDecimal(rowData, FetchTypeEnum.JL.name()));
            balanceData.setDl(fetchData.getBigDecimal(rowData, FetchTypeEnum.DL.name()));
            balanceData.setYe(fetchData.getBigDecimal(rowData, FetchTypeEnum.YE.name()));
            balanceData.setZsc(fetchData.getBigDecimal(rowData, FetchTypeEnum.ZSC.name()));
            balanceData.setWnc(fetchData.getBigDecimal(rowData, FetchTypeEnum.WNC.name()));
            balanceData.setWc(fetchData.getBigDecimal(rowData, FetchTypeEnum.WC.name()));
            balanceData.setWjf(fetchData.getBigDecimal(rowData, FetchTypeEnum.WJF.name()));
            balanceData.setWdf(fetchData.getBigDecimal(rowData, FetchTypeEnum.WDF.name()));
            balanceData.setWjl(fetchData.getBigDecimal(rowData, FetchTypeEnum.WJL.name()));
            balanceData.setWdl(fetchData.getBigDecimal(rowData, FetchTypeEnum.WDL.name()));
            balanceData.setWye(fetchData.getBigDecimal(rowData, FetchTypeEnum.WYE.name()));
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            condi.getAssTypeList().forEach(assType -> assistMap.put(assType.getDimCode(), fetchData.getString(rowData, assType.getDimCode())));
            balanceData.setAssistMap(assistMap);
            list.add(balanceData);
        }
        return result;
    }

    public Boolean isDcBalanceOriginCurrencyEnable() {
        INvwaSystemOptionService sysOptionService = (INvwaSystemOptionService)BeanUtils.getBean(INvwaSystemOptionService.class);
        String valueById = sysOptionService.findValueById(BALANCEORGNENABLE);
        if (StringUtils.isEmpty((String)valueById)) {
            return null;
        }
        if ("1".equals(valueById)) {
            return true;
        }
        return false;
    }

    protected static class MultiDimensionCode {
        private final String[] dimCodes;

        public MultiDimensionCode(String ... dimCodes) {
            this.dimCodes = dimCodes;
        }

        public String getDimCode(int index) {
            if (index >= this.dimCodes.length) {
                return null;
            }
            return this.dimCodes[index];
        }
    }
}

