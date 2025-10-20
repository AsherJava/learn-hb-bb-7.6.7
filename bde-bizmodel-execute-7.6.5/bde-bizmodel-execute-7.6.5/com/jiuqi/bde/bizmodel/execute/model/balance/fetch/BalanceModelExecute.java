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
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.OrientEnum
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.balance.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.balance.AbstractBalanceDataLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.gather.IBalanceDataLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.OrientEnum;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class BalanceModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private IBalanceDataLoaderGather loaderGather;

    public String getComputationModelCode() {
        return ComputationModelEnum.BALANCE.getCode();
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        return ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO executeSettingVO = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        executeSettingVO.setSubjectCode(VariableParseUtil.parse((String)executeSettingVO.getSubjectCode(), rowData));
        executeSettingVO.setCurrencyCode(VariableParseUtil.parse((String)executeSettingVO.getCurrencyCode(), rowData));
        return executeSettingVO;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        BalanceCondition condi = this.buildBalanceCondition(executeContext);
        condi.setComputationModel(this.getComputationModelCode());
        this.setAdjustPeriod(executeContext, condi);
        this.setDirectFilter(condi, executeContext, fetchSettingList);
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011\r\n", condi));
        List<BalanceData> balanceDataList = this.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e\r\n", balanceDataList.size()));
        Map<String, Integer> subjectMap = this.loadSubject(condi);
        BaseDataMatchCondi matchCondi = new BaseDataMatchCondi(condi.getOrgMapping().getPluginType());
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        HashMap memories = new HashMap();
        int subjectOrient = OrientEnum.DEBIT.getCode();
        IBaseDataMatcher subjectMatcher = this.baseDataMatcherHandler.getSubjectMather();
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            FetchResult result = new FetchResult(executeSettingVO);
            FilterRule subjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getSubjectCode());
            FilterRule excludesubjectFilterRule = FilterRuleUtils.getFilterRule(executeSettingVO.getExcludeSubjectCode());
            for (BalanceData balance : balanceDataList) {
                if (!StringUtils.isEmpty((String)executeSettingVO.getExcludeSubjectCode()) && subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balance.getSubjectCode()).withFilterRule(excludesubjectFilterRule), memories) || !subjectMatcher.match(matchCondi.withAssistCode("MD_ACCTSUBJECT").withDataCode(balance.getSubjectCode()).withFilterRule(subjectFilterRule), memories) || !StringUtils.isEmpty((String)executeSettingVO.getCurrencyCode()) && !ModelExecuteUtil.match(executeSettingVO.getCurrencyCode(), balance.getCurrencyCode())) continue;
                subjectOrient = this.getSubjectOrient(subjectMap, executeSettingVO.getSubjectCode(), balance);
                if (this.fetchTypeCalcOrient(executeSettingVO.getFetchType()) && subjectOrient < 0) {
                    result.addZbValue(ModelExecuteUtil.getFetchVal(balance, executeSettingVO.getFetchType()).negate());
                    continue;
                }
                result.addZbValue(ModelExecuteUtil.getFetchVal(balance, executeSettingVO.getFetchType()));
            }
            resultList.add(result);
        }
        return resultList;
    }

    protected List<BalanceData> loadData(BalanceCondition condi) {
        AbstractBalanceDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return this.convertResult(condi, (FetchData)loader.loadData(condi));
    }

    protected Map<String, Integer> loadSubject(BalanceCondition condi) {
        AbstractBalanceDataLoader loader = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), this.getComputationModelCode());
        return loader.loadSubject(condi);
    }

    private List<BalanceData> convertResult(BalanceCondition condi, FetchData cache) {
        ArrayList<BalanceData> result = new ArrayList<BalanceData>();
        AssBalanceData balanceData = null;
        for (Object[] rowData : cache.getRowDatas()) {
            balanceData = new AssBalanceData();
            balanceData.setSubjectCode(cache.getString(rowData, "SUBJECTCODE"));
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
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
            result.add(balanceData);
        }
        return result;
    }
}

