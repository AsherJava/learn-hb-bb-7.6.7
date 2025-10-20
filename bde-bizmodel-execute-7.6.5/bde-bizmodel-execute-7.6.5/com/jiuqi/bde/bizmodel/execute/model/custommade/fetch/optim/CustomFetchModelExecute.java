/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.impl.model.service.impl.CustomBizModelManageServiceImpl
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.CustomFetchExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.CustomFetchModelLoader;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchExecuteSetting;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchResultFactory;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.ICustomFetchResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.CustomBizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchModelExecute
extends AbstractGenericModelExecute {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFetchModelExecute.class);
    @Autowired
    private CustomFetchModelLoader customFetchModelLoader;
    @Autowired
    private CustomBizModelManageServiceImpl customBizModeManageService;

    public String getComputationModelCode() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        ModelExecuteContext modelExecuteContext = ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
        modelExecuteContext.setOptimizeRuleGroup(fetchSettingCacheKey.getOptimizeRuleGroup());
        return modelExecuteContext;
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO fetchSetting = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        Map<String, String> escapeMap = this.transEscapeRowData(rowData);
        fetchSetting.setFormula(VariableParseUtil.parse((String)orignSetting.getFormula(), escapeMap));
        return fetchSetting;
    }

    private Map<String, String> transEscapeRowData(Map<String, String> rowData) {
        HashMap<String, String> escapeMap = new HashMap<String, String>();
        for (Map.Entry<String, String> rowEntry : rowData.entrySet()) {
            if (rowEntry.getValue() == null || !rowEntry.getValue().contains("\"")) continue;
            escapeMap.put(rowEntry.getKey(), rowEntry.getValue().replace("\"", "\\\""));
        }
        if (!escapeMap.isEmpty()) {
            HashMap<String, String> newRowData = new HashMap<String, String>();
            newRowData.putAll(rowData);
            newRowData.putAll(escapeMap);
            return newRowData;
        }
        return rowData;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> executeSettingList, StringBuilder logContent) {
        if (CollectionUtils.isEmpty(executeSettingList)) {
            throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u53d6\u6570\u8bbe\u7f6e");
        }
        String optimizeRuleGroup = executeContext.getOptimizeRuleGroup();
        Map optimzeRuleItem = (Map)JsonUtils.readValue((String)optimizeRuleGroup, (TypeReference)new TypeReference<Map<String, String>>(){});
        String fetchSourceCode = (String)optimzeRuleItem.get("FETCH_SOURCE_CODE");
        Assert.isNotEmpty((String)fetchSourceCode, (String)"\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        CustomBizModelDTO bizModel = this.customBizModeManageService.getByCode(fetchSourceCode);
        List<CustomFetchExecuteSetting> customFetchSettingList = this.parse2CustomFetchSetting(bizModel, executeSettingList);
        CustomFetchCondi condi = new CustomFetchCondi(executeContext, bizModel, customFetchSettingList, executeContext.getOrgMapping());
        AbstractCustomFetchResult fetchResult = this.customFetchModelLoader.loadData(condi);
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>(executeSettingList.size());
        ICustomFetchResultCalculator resultCalculator = CustomFetchResultFactory.createCalculator(bizModel, fetchResult);
        for (CustomFetchExecuteSetting fetchSetting : customFetchSettingList) {
            resultList.add(resultCalculator.doCalc(fetchSetting));
        }
        return resultList;
    }

    private List<CustomFetchExecuteSetting> parse2CustomFetchSetting(CustomBizModelDTO bizModel, List<ExecuteSettingVO> executeSettingList) {
        ArrayList<CustomFetchExecuteSetting> customFetchSettingList = new ArrayList<CustomFetchExecuteSetting>(executeSettingList.size());
        Map extParam = null;
        CustomFetchExecuteSetting executeSetting = null;
        for (ExecuteSettingVO executeSettingVO : executeSettingList) {
            Map map = extParam = executeSettingVO.getExtParam() == null ? new HashMap() : executeSettingVO.getExtParam();
            if (bizModel.getSelectFieldMap().get(executeSettingVO.getFetchType()) == null) {
                throw new BusinessRuntimeException(String.format("%1$s\u4e1a\u52a1\u6a21\u578b\u3010%2$s\u3011\u6ca1\u6709\u5305\u542b\u53d6\u6570\u5b57\u6bb5\u3010%3$s\u3011\uff0c\u8bf7\u68c0\u67e5\u6a21\u578b\u662f\u5426\u88ab\u4fee\u6539", CustomFetchExecuteUtil.getFieldDefineInfo(executeSettingVO), bizModel.getName(), executeSettingVO.getFetchType()));
            }
            executeSetting = (CustomFetchExecuteSetting)((Object)BeanConvertUtil.convert((Object)executeSettingVO, CustomFetchExecuteSetting.class, (String[])new String[0]));
            Map formulaMap = (Map)JsonUtils.readValue((String)executeSettingVO.getFormula(), (TypeReference)new TypeReference<Map<String, String>>(){});
            HashMap<String, CustomCondition> customConditionMap = new HashMap<String, CustomCondition>();
            CustomCondition customCondition = null;
            for (Map.Entry condiEntry : formulaMap.entrySet()) {
                if (bizModel.getCustomConditionMap().get(condiEntry.getKey()) == null) {
                    if (StringUtils.isEmpty((String)((String)formulaMap.get(condiEntry.getKey())))) {
                        if (!BdeLogUtil.isDebug()) continue;
                        LOGGER.info("{}\u4e1a\u52a1\u6a21\u578b\u3010{}\u3011\u6ca1\u6709\u5305\u542b\u53d6\u6570\u6761\u4ef6\u3010{}\u3011\uff0c\u53d6\u6570\u8bbe\u7f6e\u5305\u542b\u5bf9\u5e94\u6761\u4ef6(\u503c\u4e3a\u7a7a)\uff0c\u53d6\u6570\u8bbe\u7f6e\u53ef\u80fd\u5b58\u5728\u9519\u8bef\u914d\u7f6e", CustomFetchExecuteUtil.getFieldDefineInfo(executeSettingVO), bizModel.getName(), condiEntry.getKey());
                        continue;
                    }
                    throw new BusinessRuntimeException(String.format("%1$s\u4e1a\u52a1\u6a21\u578b\u3010%2$s\u3011\u6ca1\u6709\u5305\u542b\u53d6\u6570\u6761\u4ef6\u3010%3$s\u3011\u503c\u3010%4$s\u3011\uff0c\u8bf7\u68c0\u67e5\u6a21\u578b\u662f\u5426\u88ab\u4fee\u6539", CustomFetchExecuteUtil.getFieldDefineInfo(executeSettingVO), bizModel.getName(), condiEntry.getKey(), formulaMap.get(condiEntry.getKey())));
                }
                customCondition = (CustomCondition)bizModel.getCustomConditionMap().get(condiEntry.getKey());
                String paramsCode = customCondition.getParamsCode();
                if (StringUtils.isEmpty((String)((String)formulaMap.get(paramsCode)))) continue;
                CustomCondition condition = (CustomCondition)BeanConvertUtil.convert((Object)customCondition, CustomCondition.class, (String[])new String[0]);
                String ruleCode = customCondition.getRuleCode();
                if (!StringUtils.isEmpty((String)((String)extParam.get(customCondition.getParamsCode())))) {
                    ruleCode = MatchingRuleEnum.getEnumByCode((String)((String)extParam.get(customCondition.getParamsCode()))).getRuleCode();
                }
                condition.setRuleCode(ruleCode);
                condition.setValue((String)formulaMap.get(paramsCode));
                customConditionMap.put(paramsCode, condition);
            }
            executeSetting.setCustomConditionMap(customConditionMap);
            executeSetting.setSelectField((SelectField)bizModel.getSelectFieldMap().get(executeSettingVO.getFetchType()));
            customFetchSettingList.add(executeSetting);
        }
        return customFetchSettingList;
    }
}

