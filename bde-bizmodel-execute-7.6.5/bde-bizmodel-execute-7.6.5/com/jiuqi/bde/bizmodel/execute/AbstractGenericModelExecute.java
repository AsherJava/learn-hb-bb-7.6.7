/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.IBizModelExecute
 *  com.jiuqi.bde.bizmodel.define.gather.IBaseDataMatcherGather
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.common.constant.DataBaseTypeEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.OrientEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.bizmodel.execute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.IBizModelExecute;
import com.jiuqi.bde.bizmodel.define.gather.IBaseDataMatcherGather;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.DataBaseTypeEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.intf.ConditionMatchRule;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.OrientEnum;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGenericModelExecute
implements IBizModelExecute {
    @Autowired
    protected IBizModelGather bizModelGather;
    @Autowired
    protected DataSourceService dataSourceService;
    @Autowired
    protected IBaseDataMatcherGather baseDataMatcherHandler;
    public static final String NEXT_LINE = System.getProperty("line.separator");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pair<FetchResultDim, List<FetchResult>> doFixedExecute(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey, List<ExecuteSettingVO> executeSettingList) {
        Assert.isNotNull((Object)fetchTaskContext, (String)"\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)fetchSettingCacheKey, (String)"\u53d6\u6570\u65b9\u6848\u7f13\u5b58\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCacheKey.getOptimizeRuleGroup(), (String)"\u4f18\u5316\u89c4\u5219\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DateFormat dateFormatter = DateUtils.createFormatter((String)"yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder logContent = new StringBuilder();
        try {
            this.apeendLog(logContent, dateFormatter, String.format("\u5f00\u59cb\u89e3\u6790\u56fa\u5b9a\u53d6\u6570\uff0c\u4e0a\u4e0b\u6587\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011\uff0c\u53d6\u6570\u914d\u7f6e\uff1a\r\n\u3010%2$s\u3011", fetchTaskContext, fetchSettingCacheKey));
            ModelExecuteContext executeContext = this.rewriteContext(fetchTaskContext, fetchSettingCacheKey);
            this.apeendLog(logContent, dateFormatter, String.format("\u89e3\u6790\u4e0a\u4e0b\u6587\u4fe1\u606f\uff0c\u4e0a\u4e0b\u6587\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011", new Object[]{executeContext}));
            if (CollectionUtils.isEmpty(executeSettingList)) {
                this.apeendLog(logContent, dateFormatter, "\u52a0\u8f7d\u53d6\u6570\u914d\u7f6e\uff0c\u53d6\u6570\u914d\u7f6e\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f");
                this.writeLog(fetchTaskContext.getRequestTaskId(), logContent.toString());
                Pair<FetchResultDim, List<FetchResult>> pair = null;
                return pair;
            }
            this.apeendLog(logContent, dateFormatter, String.format("\u52a0\u8f7d\u53d6\u6570\u914d\u7f6e\uff0c\u53d6\u6570\u914d\u7f6e\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011", executeSettingList));
            List<FetchResult> resultList = this.doExecute(executeContext, executeSettingList, logContent);
            FetchResultDim fetchResultDim = new FetchResultDim(fetchTaskContext.getRequestTaskId(), fetchTaskContext.getFormId(), fetchSettingCacheKey.getRegionId(), fetchTaskContext.getRouteNum());
            Pair pair = new Pair((Object)fetchResultDim, resultList);
            return pair;
        }
        finally {
            this.writeLog(fetchTaskContext.getRequestTaskId(), logContent.toString());
        }
    }

    protected abstract ModelExecuteContext rewriteContext(FetchTaskContext var1, FetchSettingCacheKey var2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pair<FetchResultDim, List<FetchResult>> doFloatExecute(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey, List<Map<String, String>> floatDataList, List<ExecuteSettingVO> orignSettingList) {
        Assert.isNotNull((Object)fetchTaskContext, (String)"\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)fetchSettingCacheKey, (String)"\u53d6\u6570\u65b9\u6848\u7f13\u5b58\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCacheKey.getOptimizeRuleGroup(), (String)"\u4f18\u5316\u89c4\u5219\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        DateFormat dateFormatter = DateUtils.createFormatter((String)"yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder logContent = new StringBuilder();
        try {
            this.apeendLog(logContent, dateFormatter, String.format("\u5f00\u59cb\u89e3\u6790\u6d6e\u52a8\u53d6\u6570\uff0c\u4e0a\u4e0b\u6587\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011\uff0c\u53d6\u6570\u914d\u7f6e\uff1a\r\n\u3010%2$s\u3011", fetchTaskContext, fetchSettingCacheKey));
            ModelExecuteContext executeContext = this.rewriteContext(fetchTaskContext, fetchSettingCacheKey);
            this.apeendLog(logContent, dateFormatter, String.format("\u89e3\u6790\u4e0a\u4e0b\u6587\u4fe1\u606f\uff0c\u4e0a\u4e0b\u6587\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011", new Object[]{executeContext}));
            if (CollectionUtils.isEmpty(floatDataList)) {
                this.apeendLog(logContent, dateFormatter, "\u52a0\u8f7d\u6d6e\u52a8\u884c\u914d\u7f6e\uff0c\u6d6e\u52a8\u884c\u914d\u7f6e\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f");
                this.writeLog(fetchTaskContext.getRequestTaskId(), logContent.toString());
                Pair<FetchResultDim, List<FetchResult>> pair = null;
                return pair;
            }
            this.apeendLog(logContent, dateFormatter, String.format("\u52a0\u8f7d\u6d6e\u52a8\u884c\u914d\u7f6e\uff0c\u6d6e\u52a8\u884c\u914d\u7f6e\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011", floatDataList));
            if (CollectionUtils.isEmpty(orignSettingList)) {
                this.apeendLog(logContent, dateFormatter, "\u52a0\u8f7d\u53d6\u6570\u914d\u7f6e\uff0c\u53d6\u6570\u914d\u7f6e\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f");
                this.writeLog(fetchTaskContext.getRequestTaskId(), logContent.toString());
                Pair<FetchResultDim, List<FetchResult>> pair = null;
                return pair;
            }
            this.apeendLog(logContent, dateFormatter, String.format("\u52a0\u8f7d\u53d6\u6570\u914d\u7f6e\uff0c\u53d6\u6570\u914d\u7f6e\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n\u3010%1$s\u3011", orignSettingList));
            ArrayList<ExecuteSettingVO> executeSettingList = new ArrayList<ExecuteSettingVO>(orignSettingList.size() * floatDataList.size());
            int size = floatDataList.size();
            for (int i = 0; i < size; ++i) {
                Map<String, String> rowData = floatDataList.get(i);
                for (ExecuteSettingVO orignSetting : orignSettingList) {
                    ExecuteSettingVO executeSettingVO = this.rewriteFetchSetting(orignSetting, rowData);
                    executeSettingVO.setFloatOrder(Integer.valueOf(i));
                    executeSettingList.add(executeSettingVO);
                }
            }
            this.apeendLog(logContent, dateFormatter, String.format("\u6d6e\u52a8\u53d6\u6570\u89e3\u6790\u5b8c\u6210\uff0c\u89e3\u6790\u540e\u7684\u53d6\u6570\u914d\u7f6e\uff1a\r\n\u3010%1$s\u3011", executeSettingList));
            Map<String, List<ExecuteSettingVO>> optimGroup = executeSettingList.stream().collect(Collectors.groupingBy(FetchSettingVO::getOptimizeRuleGroup));
            ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
            for (List list : optimGroup.values()) {
                List<FetchResult> eachResultList = this.doExecute(executeContext, list, logContent);
                if (CollectionUtils.isEmpty(eachResultList)) continue;
                resultList.addAll(eachResultList);
            }
            FetchResultDim fetchResultDim = new FetchResultDim(fetchTaskContext.getRequestTaskId(), fetchTaskContext.getFormId(), fetchSettingCacheKey.getRegionId(), fetchTaskContext.getRouteNum());
            Pair pair = new Pair((Object)fetchResultDim, resultList);
            return pair;
        }
        finally {
            this.writeLog(fetchTaskContext.getRequestTaskId(), logContent.toString());
        }
    }

    protected abstract ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO var1, Map<String, String> var2);

    protected String parseFixedAssistKey(String dimensionSetting) {
        List<Dimension> dimSettingList = ModelExecuteUtil.parseDimSettingToList(dimensionSetting);
        StringBuilder fixedAssistKeyBuilder = new StringBuilder();
        for (Dimension dimension : dimSettingList) {
            if (!dimension.getDimRule().equals("EQ") || StringUtils.isEmpty((String)dimension.getDimValue())) continue;
            fixedAssistKeyBuilder.append(dimension.getDimCode()).append(":").append(dimension.getDimValue()).append("|");
        }
        return fixedAssistKeyBuilder.length() == 0 ? "#" : fixedAssistKeyBuilder.toString();
    }

    protected int getSubjectOrient(Map<String, Integer> subjectMap, String fetchSettingSubject, BalanceData balance) {
        if (subjectMap == null || subjectMap.isEmpty()) {
            return OrientEnum.DEBIT.getCode();
        }
        Assert.isNotEmpty((String)fetchSettingSubject);
        if (fetchSettingSubject.contains(":")) {
            String[] settingArr = fetchSettingSubject.split(":");
            if (settingArr.length != 2) {
                return OrientEnum.DEBIT.getCode();
            }
            return this.getOrientByDefault(subjectMap, settingArr[0]);
        }
        if (fetchSettingSubject.contains(",")) {
            String[] subjectSettingArr;
            for (String subjectSetting : subjectSettingArr = fetchSettingSubject.split(",")) {
                if (!balance.getSubjectCode().startsWith(subjectSetting)) continue;
                return this.getOrientByDefault(subjectMap, subjectSetting);
            }
            return OrientEnum.DEBIT.getCode();
        }
        return this.getOrientByDefault(subjectMap, fetchSettingSubject);
    }

    private int getOrientByDefault(Map<String, Integer> subjectMap, String subjectCode) {
        return subjectMap.get(subjectCode) == null ? OrientEnum.DEBIT.getCode().intValue() : subjectMap.get(subjectCode).intValue();
    }

    protected boolean fetchTypeCalcOrient(String fetchType) {
        return FetchTypeEnum.NC.getCode().equals(fetchType) || FetchTypeEnum.WNC.getCode().equals(fetchType) || FetchTypeEnum.C.getCode().equals(fetchType) || FetchTypeEnum.WC.getCode().equals(fetchType) || FetchTypeEnum.YE.getCode().equals(fetchType) || FetchTypeEnum.WYE.getCode().equals(fetchType) || FetchTypeEnum.BQNUM.getCode().equals(fetchType) || FetchTypeEnum.LJNUM.getCode().equals(fetchType) || FetchTypeEnum.WBQNUM.getCode().equals(fetchType) || FetchTypeEnum.WLJNUM.getCode().equals(fetchType);
    }

    protected Map<String, Dimension> parseDimMap(String dimensionSetting) {
        return ModelExecuteUtil.parseDimSettingToMap(dimensionSetting);
    }

    protected Map<String, FilterRule> parseFilterRule(Map<String, Dimension> dimensionMap) {
        HashMap<String, FilterRule> filterRuleMap = new HashMap<String, FilterRule>();
        for (Map.Entry<String, Dimension> dimension : dimensionMap.entrySet()) {
            filterRuleMap.put(dimension.getKey(), FilterRuleUtils.getFilterRule(dimension.getValue().getDimValue()));
        }
        return filterRuleMap;
    }

    protected abstract List<FetchResult> doExecute(ModelExecuteContext var1, List<ExecuteSettingVO> var2, StringBuilder var3);

    protected void setAdjustPeriod(ModelExecuteContext executeContext, BalanceCondition condition) {
        condition.setStartAdjustPeriod(executeContext.getStartAdjustPeriod());
        condition.setEndAdjustPeriod(executeContext.getEndAdjustPeriod());
    }

    private final void apeendLog(StringBuilder logContent, DateFormat dateFormatter, String message) {
        logContent.append(this.nowDateStr(dateFormatter)).append("\t").append(message).append(NEXT_LINE);
    }

    protected String nowDateStr(DateFormat dateFormatter) {
        return dateFormatter.format(System.currentTimeMillis());
    }

    protected String nowDateStr() {
        return DateUtils.createFormatter((String)"yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
    }

    protected Date parseDate(String dateStr) {
        return this.parseDate(dateStr, DateCommonFormatEnum.FULL_DIGIT_BY_DASH);
    }

    protected Date parseDate(String dateStr, DateCommonFormatEnum format) {
        try {
            return DateUtils.createFormatter((DateCommonFormatEnum)format).parse(dateStr);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException(String.format("\u65e5\u671f\u3010%1$s\u3011\u683c\u5f0f\u5316\u51fa\u9519", dateStr));
        }
    }

    protected final void writeLog(String requestTaskId, String msg) {
        BdeLogUtil.recordLog((String)requestTaskId, (String)"\u53d6\u6570\u8fc7\u7a0b\u6982\u8981", null, (String)msg);
    }

    protected ConditionMatchRule getSubjectCondition(BalanceCondition condi, List<ExecuteSettingVO> fetchSettingList) {
        ArrayList<String> subjects = new ArrayList<String>();
        MatchRuleEnum matchRuleEnum = null;
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            if (executeSettingVO.getSubjectCode().contains(",")) {
                subjects.addAll(Arrays.asList(executeSettingVO.getSubjectCode().split(",")));
                continue;
            }
            if (executeSettingVO.getSubjectCode().contains(":")) {
                matchRuleEnum = MatchRuleEnum.RANGE;
                subjects.addAll(Arrays.asList(executeSettingVO.getSubjectCode().split(":")));
                continue;
            }
            subjects.add(executeSettingVO.getSubjectCode());
        }
        if (matchRuleEnum == null) {
            matchRuleEnum = MatchRuleEnum.LIKE;
        }
        subjects.sort(String::compareTo);
        ArrayList<String> optimizationList = new ArrayList<String>();
        List<Object> resultList = new ArrayList();
        String markSubject = (String)subjects.get(0);
        optimizationList.add(markSubject);
        for (String result : subjects) {
            if (result.startsWith(markSubject)) continue;
            markSubject = result;
            optimizationList.add(result);
        }
        if (matchRuleEnum != MatchRuleEnum.RANGE) {
            resultList = optimizationList.stream().map(item -> item + "%").collect(Collectors.toList());
        } else {
            resultList.add(optimizationList.get(0));
            resultList.add((String)optimizationList.get(optimizationList.size() - 1) + "ZZZ");
        }
        return new ConditionMatchRule(matchRuleEnum, resultList);
    }

    protected boolean getDirectFilter(String requestSourceType) {
        return RequestSourceTypeEnum.PENETRATE.getCode().equals(requestSourceType) || RequestSourceTypeEnum.TEST.getCode().equals(requestSourceType);
    }

    protected Map<String, DimensionValue> getDimensionValueMap(String dimensionSetStr) {
        return StringUtils.isEmpty((String)dimensionSetStr) ? new HashMap<String, DimensionValue>() : (Map)JsonUtils.readValue((String)dimensionSetStr, (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
    }

    protected void setDirectFilter(BalanceCondition condi, ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList) {
        boolean directFilter = this.getDirectFilter(executeContext.getRequestSourceType());
        condi.setEnableDirectFilter(directFilter);
        if (directFilter) {
            ConditionMatchRule conditionMatchRule = this.getSubjectCondition(condi, fetchSettingList);
            String tableName = DataBaseTypeEnum.SQL_SERVER.getTypeName().equalsIgnoreCase(this.dataSourceService.getDbType(executeContext.getOrgMapping().getDataSourceCode())) ? "#BDE_TEMP_MAINCODE" : "BDE_TEMP_MAINCODE";
            conditionMatchRule.setTbName(tableName);
            condi.setConditionMatchRule(conditionMatchRule);
        }
    }

    protected BalanceCondition buildBalanceCondition(ModelExecuteContext executeContext) {
        BalanceCondition condi = new BalanceCondition(executeContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), executeContext.getAssTypeList(), executeContext.getOrgMapping(), executeContext.getIncludeUncharged());
        condi.setIncludeAdjustVchr(executeContext.getIncludeAdjustVchr());
        condi.setDimensionValueMap(this.getDimensionValueMap(executeContext.getDimensionSetStr()));
        condi.setOtherEntity(executeContext.getOtherEntity() == null ? new HashMap() : executeContext.getOtherEntity());
        condi.setBblX(executeContext.getBblx());
        condi.setRpUnitType(executeContext.getRpUnitType());
        return condi;
    }
}

