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
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.xjll.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.gather.IXjllDataLoaderGather;
import com.jiuqi.bde.bizmodel.execute.datamodel.xjll.AbstractXjllDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.xjll.XjllBalanceData;
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
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class XjllBalanceModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private IXjllDataLoaderGather loaderGather;
    public static final String FN_DELIMITER_COLON = ":";
    public static final String FN_DELIMITER_COMMA = ",";

    public String getComputationModelCode() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
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
        executeSettingVO.setCashCode(VariableParseUtil.parse((String)executeSettingVO.getCashCode(), rowData));
        executeSettingVO.setSubjectCode(VariableParseUtil.parse((String)executeSettingVO.getSubjectCode(), rowData));
        executeSettingVO.setCurrencyCode(VariableParseUtil.parse((String)executeSettingVO.getCurrencyCode(), rowData));
        executeSettingVO.setDimensionSetting(VariableParseUtil.parse((String)executeSettingVO.getDimensionSetting(), rowData));
        return executeSettingVO;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        BalanceCondition condi = new BalanceCondition(executeContext.getRequestTaskId(), executeContext.getUnitCode(), this.parseDate(executeContext.getStartDateStr()), this.parseDate(executeContext.getEndDateStr()), executeContext.getAssTypeList(), executeContext.getOrgMapping(), executeContext.getIncludeUncharged());
        condi.setIncludeAdjustVchr(executeContext.getIncludeAdjustVchr());
        condi.setComputationModel(this.getComputationModelCode());
        condi.setCallBackIp(executeContext.getCallBackAddress());
        condi.setDimensionValueMap(this.getDimensionValueMap(executeContext.getDimensionSetStr()));
        this.setAdjustPeriod(executeContext, condi);
        condi.setOtherEntity(executeContext.getOtherEntity() == null ? new HashMap() : executeContext.getOtherEntity());
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011\r\n", condi));
        Map<String, Map<String, List<XjllBalanceData>>> balanceData = this.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e\r\n", balanceData.size()));
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            Map<String, Dimension> dimMap = this.parseDimMap(executeSettingVO.getDimensionSetting());
            Map<String, FilterRule> filterRuleMap = this.parseFilterRule(dimMap);
            FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getSubjectCode());
            FetchResult result = new FetchResult(executeSettingVO);
            for (Map.Entry<String, Map<String, List<XjllBalanceData>>> balanceKmEntry : balanceData.entrySet()) {
                if (!XjllBalanceModelExecute.matchNotIncloudeScopEnd(executeSettingVO.getCashCode(), balanceKmEntry.getKey())) continue;
                for (Map.Entry<String, List<XjllBalanceData>> balanceAssistEntry : balanceKmEntry.getValue().entrySet()) {
                    block3: for (XjllBalanceData assBalance : balanceAssistEntry.getValue()) {
                        if (!StringUtils.isEmpty((String)executeSettingVO.getSubjectCode()) && !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(assBalance.getSubjectCode()).withFilterRule(subjectFilterRule)) || !StringUtils.isEmpty((String)executeSettingVO.getCurrencyCode()) && !ModelExecuteUtil.match(executeSettingVO.getCurrencyCode(), assBalance.getCurrencyCode())) continue;
                        for (Map.Entry<String, String> assistEntry : assBalance.getAssistMap().entrySet()) {
                            if (dimMap.get(assistEntry.getKey()) == null) continue;
                            IBaseDataMatcher assistMatcher = this.baseDataMatcherHandler.getAssistMatcher(dimMap.get(assistEntry.getKey()).getDimRule());
                            FilterRule assistFilterRule = filterRuleMap.get(assistEntry.getKey());
                            if (assistMatcher.match(matchCondi.withAssistCode(assistEntry.getKey()).withDataCode(assistEntry.getValue()).withFilterRule(assistFilterRule))) continue;
                            continue block3;
                        }
                        result.addZbValue(ModelExecuteUtil.getFetchValBySrc(assBalance, executeSettingVO.getFetchType()));
                    }
                }
            }
            resultList.add(result);
        }
        return resultList;
    }

    protected Map<String, Map<String, List<XjllBalanceData>>> loadData(BalanceCondition condi) {
        AbstractXjllDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return this.convertResult(condi, (FetchData)loader.loadData(condi));
    }

    private Map<String, Map<String, List<XjllBalanceData>>> convertResult(BalanceCondition condi, FetchData cache) {
        HashMap<String, Map<String, List<XjllBalanceData>>> result = new HashMap<String, Map<String, List<XjllBalanceData>>>(128);
        block0: for (Object[] rowData : cache.getRowDatas()) {
            String fixedAssistKey;
            String cfItemCode = cache.getString(rowData, "CFITEMCODE");
            if (result.get(cfItemCode) == null) {
                result.put(cfItemCode, new HashMap(512));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if (cache.getString(rowData, assType.getDimCode()) == null) continue block0;
                assistMap.put(assType.getDimCode(), cache.getString(rowData, assType.getDimCode()));
                if (!"EQ".equals(assType.getDimRule())) continue;
                optimAssistKeyBuilder.append(assType.getDimCode()).append(FN_DELIMITER_COLON).append(cache.getString(rowData, assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(cfItemCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(cfItemCode)).put(fixedAssistKey, new ArrayList(64));
            }
            List list = (List)((Map)result.get(cfItemCode)).get(fixedAssistKey);
            XjllBalanceData balanceData = new XjllBalanceData();
            list.add(balanceData);
            balanceData.setCfItemCode(cfItemCode);
            balanceData.setSubjectCode(cache.getString(rowData, "SUBJECTCODE"));
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
            balanceData.setBqnum(cache.getBigDecimal(rowData, FetchTypeEnum.BQNUM.name()));
            balanceData.setLjnum(cache.getBigDecimal(rowData, FetchTypeEnum.LJNUM.name()));
            balanceData.setWbqnum(cache.getBigDecimal(rowData, FetchTypeEnum.WBQNUM.name()));
            balanceData.setWljnum(cache.getBigDecimal(rowData, FetchTypeEnum.WLJNUM.name()));
            balanceData.setAssistMap(assistMap);
        }
        return result;
    }

    private static boolean matchNotIncloudeScopEnd(String setting, String code) {
        Assert.isNotEmpty((String)setting);
        String string = code = code == null ? "" : code;
        if (setting.contains(FN_DELIMITER_COLON)) {
            return XjllBalanceModelExecute.matchRange(setting, code);
        }
        if (setting.contains(FN_DELIMITER_COMMA)) {
            return XjllBalanceModelExecute.matchMultiple(setting, code);
        }
        return XjllBalanceModelExecute.matchSingle(setting, code);
    }

    private static boolean matchSingle(String setting, String code) {
        return code.startsWith(setting);
    }

    private static boolean matchMultiple(String setting, String code) {
        String[] settingArr = setting.split(FN_DELIMITER_COMMA);
        if (settingArr.length == 1) {
            return XjllBalanceModelExecute.matchSingle(setting, code);
        }
        for (String settingStr : settingArr) {
            if (!XjllBalanceModelExecute.matchSingle(settingStr, code)) continue;
            return true;
        }
        return false;
    }

    private static boolean matchRange(String setting, String code) {
        String[] settingArr = setting.split(FN_DELIMITER_COLON);
        if (settingArr.length != 2) {
            return false;
        }
        String begin = settingArr[0];
        String end = settingArr[1];
        return code.compareTo(begin) >= 0 && code.compareTo(end) < 0;
    }
}

