/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula
 *  com.jiuqi.va.biz.intf.workflow.detection.ParamExtract
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 */
package com.jiuqi.va.bill.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula;
import com.jiuqi.va.biz.intf.workflow.detection.ParamExtract;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BillRegexParamExtract
implements ParamExtract {
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private DataModelClient dataModelClient;
    private static final String FORMULA_PARSE_PATTERN = "([A-Z][A-Z0-9_]{0,27})\\[([A-Z][A-Z0-9_]{0,29})\\]";
    private static final String UNIT_CODE = "UNITCODE";
    private static final String UNDERLINE = "_";
    private static final String FILTER = "FILTER";
    private static final String RANGE = "RANGE";

    public String getModule() {
        return "BILL";
    }

    public Map<String, Object> getWorkflowDetectionParam(WorkflowDetectionFormula workflowDetectionFormula) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ArrayList<Map<String, Object>> paramInfoList = new ArrayList<Map<String, Object>>();
        ArrayList<String> orgCodes = new ArrayList<String>();
        resultMap.put("paramInfoList", paramInfoList);
        resultMap.put("orgCodes", orgCodes);
        boolean orgFlag = false;
        String unitCode = workflowDetectionFormula.getUnitCode();
        if (StringUtils.hasText(unitCode)) {
            orgCodes.addAll(Arrays.asList(unitCode.split(",")));
        }
        ArrayList<Map<String, Object>> formulaList = new ArrayList<Map<String, Object>>();
        ArrayList<String> tableNameList = new ArrayList<String>();
        String bizDefine = workflowDetectionFormula.getBizDefine();
        this.prepareBillInfo(bizDefine, formulaList, tableNameList);
        HashMap<String, List<DataModelColumn>> dataModelColumnCacheMap = new HashMap<String, List<DataModelColumn>>();
        ArrayList<String> billFieldCache = new ArrayList<String>();
        List workflowVariables = workflowDetectionFormula.getWorkflowVariables();
        for (Map workflowVariable : workflowVariables) {
            Map valueFormulaMap;
            String expression;
            Object valueFormula = workflowVariable.get("valueformula");
            if (!(valueFormula instanceof Map) || !StringUtils.hasText(expression = (String)(valueFormulaMap = (Map)valueFormula).get("expression"))) continue;
            Pattern pattern = Pattern.compile(FORMULA_PARSE_PATTERN);
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
                String key;
                String tableName = matcher.group(1);
                String columnName = matcher.group(2);
                if (!tableNameList.contains(tableName)) continue;
                if (UNIT_CODE.equals(columnName)) {
                    orgFlag = true;
                    continue;
                }
                List<DataModelColumn> dataModelColumnList = BillUtils.getDataModelColumnList(dataModelColumnCacheMap, tableName);
                Optional<DataModelColumn> dataModelColumnOptional = dataModelColumnList.stream().filter(item -> columnName.equals(item.getColumnName())).findFirst();
                if (!dataModelColumnOptional.isPresent() || billFieldCache.contains(key = tableName + UNDERLINE + columnName)) continue;
                billFieldCache.add(key);
                DataModelColumn dataModelColumn = dataModelColumnOptional.get();
                Map<String, Object> paramInfoMap = BillRegexParamExtract.getParamInfoMap(dataModelColumn, tableName, columnName, tableNameList, formulaList);
                paramInfoList.add(paramInfoMap);
            }
        }
        resultMap.put("orgFlag", orgFlag);
        return resultMap;
    }

    private static Map<String, Object> getParamInfoMap(DataModelColumn dataModelColumn, String tableName, String columnName, List<String> tableNameList, List<Map<String, Object>> formulaList) {
        HashMap<String, Object> paramInfo = new HashMap<String, Object>();
        paramInfo.put("paramName", dataModelColumn.getColumnName());
        paramInfo.put("paramTitle", dataModelColumn.getColumnTitle());
        DataModelType.ColumnType columnType = dataModelColumn.getColumnType();
        paramInfo.put("paramType", columnType);
        if (columnType == DataModelType.ColumnType.INTEGER || columnType == DataModelType.ColumnType.NUMERIC) {
            String name = tableName + UNDERLINE + columnName + UNDERLINE + RANGE;
            formulaList.stream().filter(map -> name.equals(map.get("name"))).findFirst().ifPresent(map -> {
                HashMap<String, Integer> range = new HashMap<String, Integer>();
                String expression = (String)map.get("expression");
                boolean endFlag = false;
                for (int i = 0; i < expression.length() - 1; ++i) {
                    if (expression.charAt(i) != '<') continue;
                    if (expression.charAt(i + 1) == '=') {
                        if (endFlag) {
                            range.put("maxEqual", Integer.parseInt(expression.substring(i + 2)));
                        } else {
                            range.put("minEqual", Integer.parseInt(expression.substring(0, i)));
                        }
                    } else if (endFlag) {
                        range.put("max", Integer.parseInt(expression.substring(i + 1)));
                    } else {
                        range.put("min", Integer.parseInt(expression.substring(0, i)));
                    }
                    endFlag = true;
                }
                paramInfo.put("range", range);
            });
        }
        paramInfo.put("mapping", dataModelColumn.getMapping());
        Integer mappingType = dataModelColumn.getMappingType();
        paramInfo.put("mappingType", mappingType);
        if (mappingType != null && (mappingType == 1 || mappingType == 2 || mappingType == 4)) {
            String name = tableName + UNDERLINE + columnName + UNDERLINE + FILTER;
            formulaList.stream().filter(map -> name.equals(map.get("name"))).findFirst().ifPresent(map -> {
                String expression = (String)map.get("expression");
                HashMap<String, String> range = new HashMap<String, String>();
                range.put("expression", expression);
                paramInfo.put("range", range);
            });
        }
        boolean subParamFlag = !tableName.equals(tableNameList.get(0));
        paramInfo.put("subParamFlag", subParamFlag);
        if (subParamFlag) {
            paramInfo.put("subTableName", tableName);
        }
        return paramInfo;
    }

    private void prepareBillInfo(String bizDefine, List<Map<String, Object>> formulaList, List<String> tableNameList) {
        MetaDesignDTO mdDTO = new MetaDesignDTO();
        mdDTO.setDefineCode(bizDefine);
        R r = this.metaDataClient.getMetaDesign(mdDTO);
        if ((Integer)r.get((Object)"code") == 0) {
            ObjectMapper mapper = new ObjectMapper();
            String data = String.valueOf(r.get((Object)"data"));
            MetaDesignDTO metaDesignDTO = null;
            try {
                metaDesignDTO = (MetaDesignDTO)mapper.readValue(data, MetaDesignDTO.class);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String datas = metaDesignDTO.getDatas();
            if (!StringUtils.hasText(datas)) {
                return;
            }
            List jsonArray = (List)JSONUtil.parseMap((String)datas).get("plugins");
            if (jsonArray != null) {
                for (Map json : jsonArray) {
                    if (!CollectionUtils.isEmpty(tableNameList) && !CollectionUtils.isEmpty(formulaList)) break;
                    if ("data".equals(json.get("type")) && json.get("tables") != null) {
                        List tableJsonArray = (List)json.get("tables");
                        for (Map jsonTable : tableJsonArray) {
                            tableNameList.add((String)jsonTable.get("name"));
                        }
                    }
                    if (!"ruler".equals(json.get("type")) || json.get("formulas") == null) continue;
                    formulaList.addAll((List)json.get("formulas"));
                }
            }
        }
    }
}

