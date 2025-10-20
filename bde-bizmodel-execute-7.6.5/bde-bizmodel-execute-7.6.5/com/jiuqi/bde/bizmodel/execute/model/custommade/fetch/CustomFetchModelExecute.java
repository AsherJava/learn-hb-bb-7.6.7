/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchExecuteSettingVO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchMemoryLoader;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchQueryLoader;
import com.jiuqi.bde.bizmodel.execute.model.custommade.model.CustomFetchComputationModel;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.VariableParseUtil;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
class CustomFetchModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private CustomFetchMemoryLoader customFetchMemoryLoader;
    @Autowired
    private CustomFetchQueryLoader customFetchQueryLoader;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private CustomFetchComputationModel customFetchComputationModel;

    CustomFetchModelExecute() {
    }

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
        fetchSetting.setFormula(VariableParseUtil.parse((String)orignSetting.getFormula(), rowData));
        fetchSetting.setOptimizeRuleGroup(this.customFetchComputationModel.getOptimizeRuleGroup((FetchSettingVO)fetchSetting));
        return fetchSetting;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> executeSettingList, StringBuilder logContent) {
        if (CollectionUtils.isEmpty(executeSettingList)) {
            throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u53d6\u6570\u8bbe\u7f6e");
        }
        List bizModelList = this.bizModelService.listByCategory(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode());
        Map bizModelMap = bizModelList.stream().collect(Collectors.toMap(BizModelDTO::getCode, Function.identity(), (K1, K2) -> K1));
        ArrayList<CustomFetchExecuteSettingVO> customFetchExecuteSettingVOS = new ArrayList<CustomFetchExecuteSettingVO>();
        HashMap extParam = new HashMap();
        for (ExecuteSettingVO executeSettingVO : executeSettingList) {
            extParam = executeSettingVO.getExtParam() == null ? new HashMap() : executeSettingVO.getExtParam();
            CustomBizModelDTO bizModel = (CustomBizModelDTO)bizModelMap.get(executeSettingVO.getFetchSourceCode());
            if (bizModel == null) {
                throw new BusinessRuntimeException(String.format("\u6839\u636ecode\u3010%1$s\u3011\u672a\u627e\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6a21\u578b\u914d\u7f6e", executeSettingVO.getFetchSourceCode()));
            }
            CustomFetchExecuteSettingVO customFetchExecuteSettingVO = (CustomFetchExecuteSettingVO)((Object)BeanConvertUtil.convert((Object)executeSettingVO, CustomFetchExecuteSettingVO.class, (String[])new String[0]));
            Map formalMap = (Map)JsonUtils.readValue((String)executeSettingVO.getFormula(), (TypeReference)new TypeReference<Map<String, String>>(){});
            HashMap<String, CustomCondition> customConditionMap = new HashMap<String, CustomCondition>();
            for (CustomCondition customCondition : bizModel.getCustomConditions()) {
                String paramsCode = customCondition.getParamsCode();
                if (StringUtils.isEmpty((String)((String)formalMap.get(paramsCode)))) continue;
                CustomCondition condition = (CustomCondition)BeanConvertUtil.convert((Object)customCondition, CustomCondition.class, (String[])new String[0]);
                String ruleCode = customCondition.getRuleCode();
                if (!StringUtils.isEmpty((String)((String)extParam.get(customCondition.getParamsCode())))) {
                    ruleCode = MatchingRuleEnum.getEnumByCode((String)((String)extParam.get(customCondition.getParamsCode()))).getRuleCode();
                }
                condition.setRuleCode(ruleCode);
                condition.setValue((String)formalMap.get(paramsCode));
                customConditionMap.put(paramsCode, condition);
            }
            customFetchExecuteSettingVO.setBizModel(bizModel);
            customFetchExecuteSettingVO.setCustomConditionMap(customConditionMap);
            if (!extParam.isEmpty()) {
                customFetchExecuteSettingVO.setOptimizeRuleGroup(this.customFetchComputationModel.getOptimizeRuleGroup((FetchSettingVO)customFetchExecuteSettingVO));
            }
            customFetchExecuteSettingVOS.add(customFetchExecuteSettingVO);
            Assert.isNotNull(bizModel.getSelectFieldMap().get(executeSettingVO.getFetchType()), (String)"\u6839\u636e\u53d6\u6570\u5b57\u6bb5\u672a\u627e\u5230\u4e1a\u52a1\u6a21\u578b\u4e2d\u5bf9\u5e94\u7684\u5b57\u6bb5", (Object[])new Object[0]);
            customFetchExecuteSettingVO.setSelectField((SelectField)bizModel.getSelectFieldMap().get(executeSettingVO.getFetchType()));
        }
        Map<String, List<CustomFetchExecuteSettingVO>> optimGroup = customFetchExecuteSettingVOS.stream().collect(Collectors.groupingBy(FetchSettingVO::getOptimizeRuleGroup));
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        for (List<CustomFetchExecuteSettingVO> optimFetchSettingList : optimGroup.values()) {
            CustomFetchCondi condi = new CustomFetchCondi(executeContext, optimFetchSettingList, executeContext.getOrgMapping());
            if (optimFetchSettingList.size() == 1) {
                resultList.addAll(this.customFetchQueryLoader.loadData(condi));
                continue;
            }
            resultList.addAll(this.customFetchMemoryLoader.loadData(condi));
        }
        return resultList;
    }
}

