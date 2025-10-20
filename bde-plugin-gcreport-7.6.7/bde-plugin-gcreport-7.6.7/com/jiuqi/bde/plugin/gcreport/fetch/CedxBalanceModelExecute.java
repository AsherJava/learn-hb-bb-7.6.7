/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher
 *  com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceData
 *  com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext
 *  com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
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
package com.jiuqi.bde.plugin.gcreport.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceData;
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
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceCondition;
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceData;
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceLoader;
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
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CedxBalanceModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private CedxBalanceLoader loader;
    private static final String TASK_ID = "taskId";
    private static final String PERIOD_SCHEME = "periodScheme";

    public String getComputationModelCode() {
        return ComputationModelEnum.CEDXBALANCE.getCode();
    }

    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        Assert.isTrue("1".equals(fetchTaskContext.getBblx()), "\u3010\u5408\u5e76\u62a5\u8868\u3011\u63d2\u4ef6\u53ea\u652f\u6301\u5dee\u989d\u5355\u4f4d\u53d6\u6570\uff0c\u8bf7\u68c0\u67e5\u7ec4\u7ec7\u673a\u6784\u62a5\u8868\u7c7b\u578b\u914d\u7f6e");
        ModelExecuteContext executeContext = ModelExecuteUtil.convert2ExecuteContext((FetchTaskContext)fetchTaskContext);
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)fetchSettingCacheKey.getOptimizeRuleGroup());
        HashMap<String, Dimension> assTypeMap = new HashMap<String, Dimension>(5);
        String dimType = (String)optimizeMap.get("dimType");
        if (!StringUtils.isEmpty((String)dimType)) {
            String[] dimTypeArr;
            for (String str : dimTypeArr = dimType.split(";")) {
                Dimension dimension = new Dimension(str);
                assTypeMap.put(str, dimension);
            }
        }
        for (Dimension dimension : ModelExecuteUtil.parseAssTypeList(optimizeMap.get("dimComb"))) {
            assTypeMap.put(dimension.getDimCode(), dimension);
        }
        executeContext.setAssTypeList(assTypeMap.values().stream().sorted(Comparator.comparing(Dimension::getDimCode)).collect(Collectors.toList()));
        fetchTaskContext.setOtherEntity(this.getFormatOtherEntity(fetchTaskContext.getOtherEntity()));
        for (Map.Entry entry : fetchTaskContext.getOtherEntity().entrySet()) {
            if (((String)entry.getKey()).equalsIgnoreCase(PERIOD_SCHEME)) {
                executeContext.setPeriodScheme((String)entry.getValue());
                continue;
            }
            if (!((String)entry.getKey()).equalsIgnoreCase(TASK_ID)) continue;
            executeContext.setTaskId((String)entry.getValue());
        }
        executeContext.setOtherEntity(fetchTaskContext.getOtherEntity());
        return executeContext;
    }

    private HashMap<String, String> getFormatOtherEntity(Map<String, String> otherEntity) {
        if (otherEntity == null) {
            return new HashMap<String, String>();
        }
        HashMap<String, String> otherEntityMap = new HashMap<String, String>(otherEntity.size());
        for (Map.Entry<String, String> entity : otherEntity.entrySet()) {
            if ("ORGTYPE_CODE".equals(entity.getKey())) {
                otherEntityMap.put("ORGTYPE", entity.getValue());
                continue;
            }
            otherEntityMap.put(entity.getKey(), entity.getValue());
        }
        return otherEntityMap;
    }

    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO executeSettingVO = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        executeSettingVO.setSubjectCode(VariableParseUtil.parse((String)executeSettingVO.getSubjectCode(), rowData));
        executeSettingVO.setCurrencyCode(VariableParseUtil.parse((String)executeSettingVO.getCurrencyCode(), rowData));
        executeSettingVO.setDimensionSetting(VariableParseUtil.parse((String)executeSettingVO.getDimensionSetting(), rowData));
        return executeSettingVO;
    }

    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        CedxBalanceCondition condi = new CedxBalanceCondition(executeContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), executeContext.getAssTypeList(), executeContext.getOrgMapping(), executeContext.getIncludeUncharged());
        condi.setComputationModel(this.getComputationModelCode());
        condi.initCedxArgs(executeContext.getTaskId(), executeContext.getPeriodScheme(), executeContext.getOtherEntity());
        condi.setCallBackIp(executeContext.getCallBackAddress());
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", new Object[]{condi}));
        Map<String, Map<String, List<CedxBalanceData>>> balanceDataList = this.convertResult(condi, this.loader.loadData(condi));
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", balanceDataList.size()));
        Map<String, Integer> subjectMap = this.loader.loadSubject(condi);
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            FetchResult result = new FetchResult(executeSettingVO);
            result.addZbValue(this.sumByData(executeSettingVO, balanceDataList, subjectMap, condi.getOrgMapping().getPluginType()));
            resultList.add(result);
        }
        return resultList;
    }

    private BigDecimal sumByData(ExecuteSettingVO fetchSetting, Map<String, Map<String, List<CedxBalanceData>>> balanceData, Map<String, Integer> subjectMap, String pluginType) {
        FetchTypeEnum fetchType = FetchTypeEnum.fromName((String)fetchSetting.getFetchType());
        if (fetchType == FetchTypeEnum.UNKNOWN) {
            throw new BusinessRuntimeException(String.format("\u53d6\u6570\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", fetchSetting.getFetchType()));
        }
        BigDecimal zbValue = BigDecimal.ZERO;
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(pluginType);
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule((String)fetchSetting.getSubjectCode());
        FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule((String)fetchSetting.getExcludeSubjectCode());
        Map dimMap = this.parseDimMap(fetchSetting.getDimensionSetting());
        Map filterRuleMap = this.parseFilterRule(dimMap);
        for (Map.Entry<String, Map<String, List<CedxBalanceData>>> balanceKmEntry : balanceData.entrySet()) {
            if (!StringUtils.isEmpty((String)fetchSetting.getExcludeSubjectCode()) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(excludesubjectFilterRule)) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balanceKmEntry.getKey()).withFilterRule(subjectFilterRule))) continue;
            for (Map.Entry<String, List<CedxBalanceData>> balanceAssistEntry : balanceKmEntry.getValue().entrySet()) {
                block2: for (CedxBalanceData assBalance : balanceAssistEntry.getValue()) {
                    if (!StringUtils.isEmpty((String)fetchSetting.getCurrencyCode()) && !ModelExecuteUtil.match((String)fetchSetting.getCurrencyCode(), (String)assBalance.getCurrencyCode())) continue;
                    for (Map.Entry assistEntry : assBalance.getAssistMap().entrySet()) {
                        if (dimMap.get(assistEntry.getKey()) == null) continue;
                        IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(((Dimension)dimMap.get(assistEntry.getKey())).getDimRule());
                        FilterRule assistFilterRule = (FilterRule)filterRuleMap.get(assistEntry.getKey());
                        if (assistMatcher.match(matchCondi.withAssistCode((String)assistEntry.getKey()).withDataCode((String)assistEntry.getValue()).withFilterRule(assistFilterRule))) continue;
                        continue block2;
                    }
                    if (this.fetchTypeCalcOrient(fetchSetting.getFetchType()) && this.getSubjectOrient(subjectMap, fetchSetting.getSubjectCode(), (BalanceData)assBalance) < 0) {
                        zbValue = NumberUtils.sum((BigDecimal)zbValue, (BigDecimal)ModelExecuteUtil.getFetchVal((Object)((Object)assBalance), (String)fetchSetting.getFetchType()).negate());
                        continue;
                    }
                    zbValue = NumberUtils.sum((BigDecimal)zbValue, (BigDecimal)ModelExecuteUtil.getFetchVal((Object)((Object)assBalance), (String)fetchSetting.getFetchType()));
                }
            }
        }
        return zbValue;
    }

    protected boolean fetchTypeCalcOrient(String fetchType) {
        switch (FetchTypeEnum.getEnumByCode((String)fetchType)) {
            case DXNC: 
            case DXYE: {
                return true;
            }
            case DXJNC: 
            case DXDNC: 
            case DXJYH: 
            case DXDYH: {
                return false;
            }
        }
        throw new IllegalArgumentException("\u4e0d\u5b58\u5728\u53d6\u6570\u7c7b\u578b\u53c2\u6570" + fetchType);
    }

    public Map<String, Map<String, List<CedxBalanceData>>> convertResult(CedxBalanceCondition condi, FetchData fetchData) {
        HashMap<String, Map<String, List<CedxBalanceData>>> result = new HashMap<String, Map<String, List<CedxBalanceData>>>(128);
        block0: for (Object[] rowData : fetchData.getRowDatas()) {
            String fixedAssistKey;
            String subjectCode = fetchData.getString(rowData, "SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if (fetchData.getString(rowData, assType.getDimCode()) == null) continue block0;
                assistMap.put(assType.getDimCode(), fetchData.getString(rowData, assType.getDimCode()));
                if (!"EQ".equals(assType.getDimRule())) continue;
                optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(fetchData.getString(rowData, assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(subjectCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(subjectCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            CedxBalanceData balanceData = new CedxBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(fetchData.getString(rowData, "CURRENCYCODE"));
            balanceData.setOrient(fetchData.getBigDecimal(rowData, "ORIENT").intValue());
            balanceData.setDxnc(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXNC.name()));
            balanceData.setDxjnc(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXJNC.name()));
            balanceData.setDxdnc(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXDNC.name()));
            balanceData.setDxye(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXYE.name()));
            balanceData.setDxjyh(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXJYH.name()));
            balanceData.setDxdyh(fetchData.getBigDecimal(rowData, FetchTypeEnum.DXDYH.name()));
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(fixedAssistKey);
        }
        return result;
    }
}

