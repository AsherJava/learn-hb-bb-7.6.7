/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.query.basedata.dto.DCBaseDataDTO
 *  com.jiuqi.va.query.sql.basedata.QueryBaseDataTitleHandler
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 */
package com.jiuqi.va.query.common.service;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.query.basedata.dto.DCBaseDataDTO;
import com.jiuqi.va.query.sql.basedata.QueryBaseDataTitleHandler;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class DCQueryBaseDataUtil {
    private DCQueryBaseDataUtil() {
    }

    public static List<Map<String, Object>> resultCodeToTitle(List<Map<String, Object>> data, List<TemplateFieldSettingVO> fields) {
        try {
            QueryBaseDataTitleHandler queryBaseDataTitleHandler = DCQuerySpringContextUtils.getBean(QueryBaseDataTitleHandler.class);
            data = queryBaseDataTitleHandler.codeToTitle(data, fields);
        }
        catch (NoSuchBeanDefinitionException e) {
            return data;
        }
        return data;
    }

    public static List<Map<String, Object>> resultCodeToTitle(List<Map<String, Object>> data, List<TemplateFieldSettingVO> fields, Map<String, Map<String, DCBaseDataDTO>> cacheMap) {
        try {
            QueryBaseDataTitleHandler queryBaseDataTitleHandler = DCQuerySpringContextUtils.getBean(QueryBaseDataTitleHandler.class);
            data = queryBaseDataTitleHandler.codeToTitle(data, fields, cacheMap);
        }
        catch (NoSuchBeanDefinitionException e) {
            return data;
        }
        return data;
    }

    public static List<String> listObjectCodeByTableNameAndAuth(String tableName) {
        try {
            QueryBaseDataTitleHandler queryBaseDataTitleHandler = DCQuerySpringContextUtils.getBean(QueryBaseDataTitleHandler.class);
            return queryBaseDataTitleHandler.listObjectCodeByTableNameAndAuth(tableName);
        }
        catch (NoSuchBeanDefinitionException e) {
            return Collections.emptyList();
        }
    }

    public static List<String> listObjectCodeByTableNameAndAuth(Map<String, Object> paramMap, TemplateParamsVO paramsVO, List<TemplateParamsVO> cols) {
        String tableName = paramsVO.getRefTableName();
        String filterCondition = paramsVO.getFilterCondition();
        List driverItems = paramsVO.getDriverItems();
        Boolean enableAuth = paramsVO.getEnableAuth();
        if (!StringUtils.hasText(tableName)) {
            return Collections.emptyList();
        }
        if (tableName.toUpperCase().startsWith("EM_")) {
            return DCQueryBaseDataUtil.listObjectCodeByTableNameAndAuth(tableName);
        }
        Map configParamMap = paramsVO.getConfigParamMap();
        Object stopflag = configParamMap.get("stopflag");
        if (tableName.toUpperCase().startsWith("MD_ORG")) {
            OrgDataClient orgDataClient;
            List rows;
            OrgDTO map = new OrgDTO();
            map.put("vaBizFormula", true);
            OrgDTO orgDTO = map;
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
            orgDTO.setAuthType(Boolean.TRUE.equals(enableAuth) ? OrgDataOption.AuthType.ACCESS : OrgDataOption.AuthType.NONE);
            orgDTO.setCategoryname(tableName);
            orgDTO.setExpression(filterCondition);
            if (!ObjectUtils.isEmpty(stopflag)) {
                orgDTO.setStopflag(Integer.valueOf(stopflag.toString()));
            }
            if (CollectionUtils.isEmpty(rows = (orgDataClient = DCQuerySpringContextUtils.getBean(OrgDataClient.class)).list(orgDTO).getRows())) {
                return Collections.emptyList();
            }
            return rows.stream().map(OrgDO::getCode).collect(Collectors.toList());
        }
        BaseDataDefineDO baseDataDefine = DCQueryBaseDataUtil.getBaseDataDefine(tableName);
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
        baseDataDTO.setAuthType(Boolean.TRUE.equals(enableAuth) ? BaseDataOption.AuthType.ACCESS : BaseDataOption.AuthType.NONE);
        if (!ObjectUtils.isEmpty(stopflag)) {
            baseDataDTO.setStopflag(Integer.valueOf(stopflag.toString()));
        }
        List<Object> baseDatas = new ArrayList();
        Optional<TemplateParamsVO> first = cols.stream().filter(TemplateParamsVO::getUnitCodeFlag).findFirst();
        Object unitcode = null;
        if (first.isPresent()) {
            unitcode = paramMap.get(first.get().getName());
        }
        if (unitcode == null) {
            unitcode = ShiroUtil.getUser().getLoginUnit();
        }
        String filterExpression = "";
        if (unitcode instanceof String) {
            baseDataDTO.setUnitcode((String)unitcode);
        } else if (unitcode != null && baseDataDefine.getSharetype() != 0) {
            baseDataDTO.setIgnoreShareFields(Boolean.valueOf(true));
            filterExpression = filterExpression + "[UNITCODE] in " + DCQueryBaseDataUtil.getVariablesForArray(unitcode);
        }
        if (CollectionUtils.isEmpty(driverItems)) {
            baseDatas = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, null);
        } else {
            TemplateParamsVO diverTemplate = (TemplateParamsVO)driverItems.get(0);
            Object driverValue = paramMap.get(diverTemplate.getName());
            if (!ObjectUtils.isEmpty(driverValue)) {
                baseDatas = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, driverValue);
            } else if (StringUtils.hasText(diverTemplate.getFilterCondition()) && ObjectUtils.isEmpty(driverValue)) {
                BaseDataDTO dto = DCQueryBaseDataUtil.getBaseDataDTO(diverTemplate, unitcode);
                List<BaseDataDO> driverBaseDatas = DCQueryBaseDataUtil.getRangeOfBaseData(dto, diverTemplate.getFilterCondition(), filterExpression, null);
                List<Object> objectcodes = new ArrayList();
                if (!CollectionUtils.isEmpty(driverBaseDatas)) {
                    objectcodes = driverBaseDatas.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
                }
                baseDatas = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, objectcodes);
            } else {
                baseDatas = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, null);
            }
        }
        return baseDatas.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
    }

    public static List<String> checkValuesExistByAuth(List<String> values, Map<String, Object> paramMap, TemplateParamsVO paramsVO, List<TemplateParamsVO> cols) {
        List<BaseDataDO> baseDataDOList;
        String tableName = paramsVO.getRefTableName();
        String filterCondition = paramsVO.getFilterCondition();
        List driverItems = paramsVO.getDriverItems();
        Boolean enableAuth = paramsVO.getEnableAuth();
        if (!StringUtils.hasText(tableName)) {
            return values;
        }
        if (tableName.toUpperCase().startsWith("EM_")) {
            return values;
        }
        Map configParamMap = paramsVO.getConfigParamMap();
        Object stopflag = configParamMap.get("stopflag");
        if (!enableAuth.booleanValue() && !StringUtils.hasText(filterCondition)) {
            return values;
        }
        if (tableName.toUpperCase().startsWith("MD_ORG")) {
            OrgDataClient orgDataClient;
            List rows;
            OrgDTO map = new OrgDTO();
            map.put("vaBizFormula", true);
            OrgDTO orgDTO = map;
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
            orgDTO.setAuthType(enableAuth != false ? OrgDataOption.AuthType.ACCESS : OrgDataOption.AuthType.NONE);
            orgDTO.setCategoryname(tableName);
            orgDTO.setExpression(filterCondition);
            orgDTO.setOrgCodes(values);
            if (!ObjectUtils.isEmpty(stopflag)) {
                orgDTO.setStopflag(Integer.valueOf(stopflag.toString()));
            }
            if (CollectionUtils.isEmpty(rows = (orgDataClient = DCQuerySpringContextUtils.getBean(OrgDataClient.class)).list(orgDTO).getRows())) {
                return new ArrayList<String>();
            }
            return rows.stream().map(OrgDO::getCode).collect(Collectors.toList());
        }
        BaseDataDefineDO baseDataDefine = DCQueryBaseDataUtil.getBaseDataDefine(tableName);
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
        baseDataDTO.setAuthType(enableAuth != false ? BaseDataOption.AuthType.ACCESS : BaseDataOption.AuthType.NONE);
        baseDataDTO.setBaseDataCodes(values);
        if (!ObjectUtils.isEmpty(stopflag)) {
            baseDataDTO.setStopflag(Integer.valueOf(stopflag.toString()));
        }
        Optional<TemplateParamsVO> first = cols.stream().filter(TemplateParamsVO::getUnitCodeFlag).findFirst();
        Object unitcode = null;
        if (first.isPresent()) {
            unitcode = paramMap.get(first.get().getName());
        }
        if (unitcode == null) {
            unitcode = ShiroUtil.getUser().getLoginUnit();
        }
        String filterExpression = "";
        if (unitcode instanceof String) {
            baseDataDTO.setUnitcode((String)unitcode);
        } else if (unitcode != null && baseDataDefine.getSharetype() != 0) {
            baseDataDTO.setIgnoreShareFields(Boolean.valueOf(true));
            filterExpression = filterExpression + "[UNITCODE] in " + DCQueryBaseDataUtil.getVariablesForArray(unitcode);
        }
        if (CollectionUtils.isEmpty(driverItems)) {
            baseDataDOList = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, null);
        } else {
            TemplateParamsVO diverTemplate = (TemplateParamsVO)driverItems.get(0);
            Object driverValue = paramMap.get(diverTemplate.getName());
            if (ObjectUtils.isEmpty(driverValue)) {
                if (StringUtils.hasText(diverTemplate.getFilterCondition()) && ObjectUtils.isEmpty(driverValue)) {
                    BaseDataDTO dto = DCQueryBaseDataUtil.getBaseDataDTO(diverTemplate, unitcode);
                    List<BaseDataDO> driverBaseDataList = DCQueryBaseDataUtil.getRangeOfBaseData(dto, diverTemplate.getFilterCondition(), filterExpression, null);
                    List<Object> objectcodes = new ArrayList();
                    if (!CollectionUtils.isEmpty(driverBaseDataList)) {
                        objectcodes = driverBaseDataList.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
                    }
                    baseDataDOList = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, objectcodes);
                } else {
                    baseDataDOList = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, null);
                }
            } else {
                baseDataDOList = DCQueryBaseDataUtil.getRangeOfBaseData(baseDataDTO, filterCondition, filterExpression, driverValue);
            }
        }
        return baseDataDOList.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
    }

    private static BaseDataDTO getBaseDataDTO(TemplateParamsVO templateParamsVO, Object unitCodeObject) {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
        dto.setAuthType(Boolean.TRUE.equals(templateParamsVO.getEnableAuth()) ? BaseDataOption.AuthType.ACCESS : BaseDataOption.AuthType.NONE);
        dto.setTableName(templateParamsVO.getRefTableName());
        if (unitCodeObject instanceof String) {
            dto.setUnitcode((String)unitCodeObject);
        } else {
            dto.setIgnoreShareFields(Boolean.valueOf(true));
        }
        return dto;
    }

    private static List<BaseDataDO> getRangeOfBaseData(BaseDataDTO baseDataDO, String conditionExpression, String filterExpression, Object driverValue) {
        if (driverValue != null) {
            if (Objects.nonNull(conditionExpression)) {
                conditionExpression = conditionExpression.replace("=", " in ").replaceAll("\\[PARAM_._([A-Za-z_0-9]+)\\]", DCQueryBaseDataUtil.getVariablesForArray(driverValue));
            }
            conditionExpression = StringUtils.hasText(filterExpression) && StringUtils.hasText(conditionExpression) ? conditionExpression + " AND " + filterExpression : filterExpression;
            baseDataDO.put("vaBizFormula", (Object)true);
            baseDataDO.setExpression(conditionExpression);
        } else if (StringUtils.hasText(filterExpression)) {
            baseDataDO.put("vaBizFormula", (Object)true);
            baseDataDO.setExpression(filterExpression);
        }
        BaseDataClient baseDataClient = DCQuerySpringContextUtils.getBean(BaseDataClient.class);
        PageVO pageVO = baseDataClient.list(baseDataDO);
        if (Objects.isNull(pageVO)) {
            return Collections.emptyList();
        }
        return Optional.ofNullable(pageVO.getRows()).orElse(Collections.emptyList());
    }

    private static String getVariablesForArray(Object value) {
        StringBuilder builder = new StringBuilder("{");
        if (value instanceof List) {
            ArrayList values = (ArrayList)value;
            builder.append("'" + values.stream().collect(Collectors.joining("','")) + "'");
        } else {
            builder.append("'" + value.toString() + "'");
        }
        return builder.append("}").toString();
    }

    private static BaseDataDefineDO getBaseDataDefine(String tableName) {
        BaseDataDefineClient baseDataDefineClient = DCQuerySpringContextUtils.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setName(tableName);
        return baseDataDefineClient.get(baseDataDefineDTO);
    }
}

