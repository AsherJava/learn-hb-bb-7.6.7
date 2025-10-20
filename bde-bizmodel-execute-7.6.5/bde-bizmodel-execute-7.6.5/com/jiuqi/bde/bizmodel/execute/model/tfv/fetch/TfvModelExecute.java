/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.SelectColumnLoc;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvExecuteSettingVO;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvOptimKey;
import com.jiuqi.bde.bizmodel.execute.model.tfv.single.TfvDataCondi;
import com.jiuqi.bde.bizmodel.execute.model.tfv.single.TfvLoader;
import com.jiuqi.bde.bizmodel.execute.model.tfv.utils.TfvExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class TfvModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private TfvLoader loader;
    private static final String SELECT_ALIAS_TMPL = "%1$s_R_%2$s";

    public String getComputationModelCode() {
        return "TFV";
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        return ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO fetchSetting = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        fetchSetting.setFormula(VariableParseUtil.parse((String)orignSetting.getFormula(), rowData));
        return fetchSetting;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> fetchSettingList, StringBuilder logContent) {
        Assert.isNotEmpty(fetchSettingList);
        Map variableMap = ContextVariableParseUtil.initContextVariableMap((FetchTaskContext)executeContext);
        Integer columnLoc = 3;
        TfvOptimKey optimKey = null;
        HashSet<String> selectAliasSet = new HashSet<String>(10);
        LinkedHashMap<String, SelectColumnLoc> selectColumnLocMap = new LinkedHashMap<String, SelectColumnLoc>();
        HashSet<String> mainDimValueSet = new HashSet<String>(64);
        HashSet<String> secondDimValueSet = new HashSet<String>(64);
        TfvExecuteSettingVO executeSetting = null;
        HashMap<String, List> executeSettingMap = new HashMap<String, List>(64);
        String sql = null;
        for (ExecuteSettingVO executeSettingVO : fetchSettingList) {
            sql = ContextVariableParseUtil.parse((String)executeSettingVO.getFormula(), (Map)variableMap);
            optimKey = TfvExecuteUtil.parseOptimKey(sql);
            executeSetting = new TfvExecuteSettingVO(executeSettingVO, optimKey);
            executeSettingMap.computeIfAbsent(optimKey.getResultOptimizeFlag(), k -> new ArrayList(16));
            ((List)executeSettingMap.get(optimKey.getResultOptimizeFlag())).add(executeSetting);
            if (!StringUtils.isEmpty((String)optimKey.getMainDimName()) && !StringUtils.isEmpty((String)optimKey.getMainDimValue())) {
                mainDimValueSet.add(optimKey.getMainDimValue());
            }
            if (!StringUtils.isEmpty((String)optimKey.getSecondDimName()) && !StringUtils.isEmpty((String)optimKey.getSecondDimValue())) {
                secondDimValueSet.add(optimKey.getSecondDimValue());
            }
            if (selectColumnLocMap.containsKey(optimKey.getSelectColumn())) continue;
            String alias = this.clacAlias(selectAliasSet, optimKey.getSelectAlias());
            Integer n = columnLoc;
            Integer n2 = columnLoc = Integer.valueOf(columnLoc + 1);
            selectColumnLocMap.put(optimKey.getSelectColumn(), new SelectColumnLoc(optimKey.getSelectColumn(), alias, n, optimKey.getSelectContainsSum()));
        }
        TfvDataCondi condi = new TfvDataCondi(executeContext, fetchSettingList, executeContext.getOrgMapping());
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        List<FetchResult> balanceDataList = this.loader.loadData(condi);
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", balanceDataList.size()));
        return balanceDataList;
    }

    private String clacAlias(Set<String> selectAliasSet, String selectAlias) {
        if (!selectAliasSet.contains(selectAlias)) {
            selectAliasSet.add(selectAlias);
            return selectAlias;
        }
        String rundomAlias = null;
        while (!selectAliasSet.contains(rundomAlias = String.format(SELECT_ALIAS_TMPL, selectAlias, Math.random() * 100.0))) {
        }
        selectAliasSet.add(selectAlias);
        return selectAlias;
    }
}

