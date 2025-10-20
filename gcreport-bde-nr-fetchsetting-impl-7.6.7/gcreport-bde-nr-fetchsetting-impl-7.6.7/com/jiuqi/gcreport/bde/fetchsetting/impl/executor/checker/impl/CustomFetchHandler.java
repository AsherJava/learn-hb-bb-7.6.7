/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchHandler
extends AbstractBizModelHandler {
    @Value(value="${jiuqi.bde.setting.custom-fetch.delimiter:;}")
    private String delimiter;

    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getCustomFetch())) {
            excelRowFetchSettingVO.setErrorLog("\u81ea\u5b9a\u4e49\u53d6\u6570\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return true;
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }

    @Override
    public ExcelRowFetchSettingVO exportDataHandle(FixedFetchSourceRowSettingVO fixedFetchSourceRowSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO excelRowFetchSettingVO = new ExcelRowFetchSettingVO(fixedFetchSourceRowSettingVO);
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)bizModelDTO;
        excelRowFetchSettingVO.setCustomFetch(this.getCustomStr(customBizModelDTO, fixedFetchSourceRowSettingVO));
        excelRowFetchSettingVO.setFormula("");
        return excelRowFetchSettingVO;
    }

    @Override
    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO rowImpSettingVO = new ExcelRowFetchSettingVO();
        BeanUtils.copyProperties((Object)excelRowFetchSettingVO, (Object)rowImpSettingVO);
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)bizModelDTO;
        this.analysisCustom(rowImpSettingVO, customBizModelDTO, excelRowFetchSettingVO.getCustomFetch());
        return rowImpSettingVO;
    }

    private String getCustomStr(CustomBizModelDTO customBizModelDTO, FixedFetchSourceRowSettingVO fetchSourceRowSettingVO) {
        StringBuilder customStr = new StringBuilder();
        customStr.append(((SelectField)customBizModelDTO.getSelectFieldMap().get(fetchSourceRowSettingVO.getFetchType())).getFieldName()).append("{");
        Map condiMap = (Map)JsonUtils.readValue((String)fetchSourceRowSettingVO.getFormula(), (TypeReference)new TypeReference<Map<String, String>>(){});
        StringJoiner stringJoiner = new StringJoiner(this.delimiter);
        for (Map.Entry condi : condiMap.entrySet()) {
            if (StringUtils.isEmpty((String)((String)condi.getValue()))) continue;
            stringJoiner.add(((CustomCondition)customBizModelDTO.getCustomConditionMap().get(condi.getKey())).getParamsName() + "|(" + (String)condi.getValue() + ")");
        }
        customStr.append(stringJoiner).append("}");
        return customStr.toString();
    }

    private void analysisCustom(ExcelRowFetchSettingVO excelRowFetchSettingVO, CustomBizModelDTO customBizModelDTO, String customStr) {
        String fetchTypeName = customStr.substring(0, customStr.indexOf("{"));
        for (SelectField selectField : customBizModelDTO.getSelectFieldList()) {
            if (!selectField.getFieldName().equals(fetchTypeName)) continue;
            excelRowFetchSettingVO.setFetchType(selectField.getFieldCode());
        }
        Map customNameMap = customBizModelDTO.getCustomConditions().stream().collect(Collectors.toMap(CustomCondition::getParamsName, Function.identity(), (K1, K2) -> K1));
        String fetchConditionStr = customStr.substring(customStr.indexOf("{") + 1, customStr.lastIndexOf("}"));
        ArrayList fetchConditions = StringUtils.isEmpty((String)fetchConditionStr) ? new ArrayList() : Arrays.asList(fetchConditionStr.split("\\" + this.delimiter));
        LinkedHashMap<String, String> conditionMap = new LinkedHashMap<String, String>();
        for (String fetchCondition : fetchConditions) {
            String[] condition = fetchCondition.split("\\|");
            conditionMap.put(((CustomCondition)customNameMap.get(condition[0])).getParamsCode(), condition[1].substring(condition[1].indexOf("(") + 1, condition[1].contains(")") ? condition[1].lastIndexOf(")") : condition[1].length()));
        }
        excelRowFetchSettingVO.setFormula(JsonUtils.writeValueAsString(conditionMap));
    }
}

