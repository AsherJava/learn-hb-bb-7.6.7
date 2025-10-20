/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.util.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.check.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckComparatorUtil;
import com.jiuqi.gcreport.inputdata.flexible.utils.CorporateConvertUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTypeEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.util.BaseDataUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class InputDataCheckColumnDataUtils {
    private static DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
    private static InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
    private static ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    public static String[] selectCheckTabColumns = new String[]{"ORGCODE", "OPPUNITID", "CHECKGROUPID", "CHECKTYPE", "UNIONRULEID", "CHECKAMT", "DC", "ID", "SUBJECTCODE", "UNCHECKAMT", "OFFSETSTATE", "AMT", "MEMO", "DATATIME", "REPORTSYSTEMID", "CHECKSTATE", "MDCODE"};
    public static String[] selectUnCheckTabColumns = new String[]{"ORGCODE", "OPPUNITID", "UNIONRULEID", "CHECKAMT", "DC", "ID", "SUBJECTCODE", "UNCHECKAMT", "OFFSETSTATE", "AMT", "MEMO", "DATATIME", "REPORTSYSTEMID", "MDCODE"};
    public static String[] selectAllCheckTabColumns = new String[]{"ORGCODE", "OPPUNITID", "CHECKGROUPID", "UNIONRULEID", "CHECKTYPE", "CHECKAMT", "DC", "ID", "SUBJECTCODE", "UNCHECKAMT", "OFFSETSTATE", "AMT", "MEMO", "DATATIME", "REPORTSYSTEMID", "CHECKSTATE", "MDCODE"};

    public static List<Map<String, Object>> getCheckTableDataByColumns(List<InputDataEO> inputDatas, String[] selectColumns, boolean showDictCode, InputDataCheckCondition inputDataCheckCondition, boolean isCheckTabDataFlag) {
        if (inputDatas.isEmpty()) {
            return null;
        }
        HashMap<String, String> subjectTitleGroupByCode = new HashMap<String, String>();
        HashMap<String, String> ruleTitleGroupByCode = new HashMap<String, String>();
        Map<String, String> fieldCodeGroupByReferTableId = InputDataCheckColumnDataUtils.initFieldCodeGroupByReferTableId(inputDataCheckCondition.getTaskId());
        Map<String, GcOrgCenterService> fieldCodeOrgToolMap = InputDataCheckColumnDataUtils.initFieldCodeOrgToolMap(fieldCodeGroupByReferTableId, inputDataCheckCondition.getDataTime());
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (InputDataEO inputData : inputDatas) {
            HashMap<String, Object> dataGroupByColumnName = new HashMap<String, Object>();
            for (String selectColumn : selectColumns) {
                Object baseDataValue;
                dataGroupByColumnName.put(selectColumn, inputData.getFieldValue(selectColumn));
                if ("ORGCODE".equals(selectColumn)) {
                    dataGroupByColumnName.put("UNITID", inputData.getFieldValue(selectColumn));
                    dataGroupByColumnName.put("UNITTITLE", InputDataCheckColumnDataUtils.getOtherShowColumnOrgTitle("ORGCODE", inputData.getOrgCode(), fieldCodeOrgToolMap, showDictCode));
                    continue;
                }
                if ("OPPUNITID".equals(selectColumn)) {
                    dataGroupByColumnName.put("OPPUNITTITLE", InputDataCheckColumnDataUtils.getOtherShowColumnOrgTitle("OPPUNITID", inputData.getOppUnitId(), fieldCodeOrgToolMap, showDictCode));
                    continue;
                }
                if ("SUBJECTCODE".equals(selectColumn)) {
                    String subjectTitle = InputDataCheckColumnDataUtils.getSubjectTitle(inputData.getReportSystemId(), subjectTitleGroupByCode, inputData.getSubjectCode());
                    String showSubjectTitle = showDictCode ? inputData.getSubjectCode() + "|" + subjectTitle : subjectTitle;
                    dataGroupByColumnName.put("SUBJECTTITLE", showSubjectTitle);
                    continue;
                }
                if ("OFFSETSTATE".equals(selectColumn)) {
                    baseDataValue = InputDataCheckColumnDataUtils.getOtherShowColumnBaseDataTitle("OFFSETSTATE", inputData.getOffsetState(), fieldCodeGroupByReferTableId, showDictCode);
                    dataGroupByColumnName.put("OFFSETSTATETITLE", baseDataValue);
                    continue;
                }
                if ("UNIONRULEID".equals(selectColumn)) {
                    dataGroupByColumnName.put("RULETITLE", InputDataCheckColumnDataUtils.getUnionRuleTitle(ruleTitleGroupByCode, inputData.getUnionRuleId()));
                    continue;
                }
                if ("CHECKTYPE".equals(selectColumn)) {
                    if (StringUtils.isEmpty((String)inputData.getCheckType()) || StringUtils.isEmpty((String)inputData.getCheckState()) || InputDataCheckStateEnum.NOTCHECK.getValue().equals(inputData.getCheckState())) {
                        dataGroupByColumnName.put("CHECKTYPETITLE", null);
                        continue;
                    }
                    dataGroupByColumnName.put("CHECKTYPETITLE", InputDataCheckTypeEnum.MANUAL_ITEM.getValue().equals(inputData.getCheckType()) ? GcI18nUtil.getMessage((String)"gc.inputdata.check.manualchecktype") : GcI18nUtil.getMessage((String)"gc.inputdata.check.autochecktype"));
                    continue;
                }
                if (!"CHECKSTATE".equals(selectColumn)) continue;
                baseDataValue = InputDataCheckColumnDataUtils.getOtherShowColumnBaseDataTitle("CHECKSTATE", inputData.getCheckState(), fieldCodeGroupByReferTableId, false);
                dataGroupByColumnName.put("CHECKSTATETITLE", baseDataValue);
            }
            String recordKey = isCheckTabDataFlag ? InputDataCheckColumnDataUtils.getSumRecordKey(inputData, inputDataCheckCondition.getCheckGatherColumns(), true) : InputDataCheckColumnDataUtils.getSumRecordKey(inputData, inputDataCheckCondition.getCheckGatherColumns(), false);
            dataGroupByColumnName.put("RECORDKEY", recordKey);
            BigDecimal unCheckAmt = Objects.isNull(inputData.getUnCheckAmt()) ? BigDecimal.ZERO : BigDecimal.valueOf(inputData.getUnCheckAmt());
            BigDecimal checkAmt = Objects.isNull(inputData.getCheckAmt()) ? BigDecimal.ZERO : BigDecimal.valueOf(inputData.getCheckAmt());
            BigDecimal checkDiff = unCheckAmt.subtract(checkAmt);
            if (OrientEnum.D.getValue().equals(inputData.getDc())) {
                dataGroupByColumnName.put("DCTITLE", OrientEnum.D.getTitle());
                dataGroupByColumnName.put("CHECKDEBIT", unCheckAmt.doubleValue());
                dataGroupByColumnName.put("CHECKCREDIT", 0.0);
            } else {
                dataGroupByColumnName.put("DCTITLE", OrientEnum.C.getTitle());
                dataGroupByColumnName.put("CHECKDEBIT", 0.0);
                dataGroupByColumnName.put("CHECKCREDIT", unCheckAmt.doubleValue());
            }
            if (isCheckTabDataFlag) {
                dataGroupByColumnName.put("CHECKDIFF", checkDiff.doubleValue());
            } else {
                dataGroupByColumnName.put("CHECKDIFF", null);
            }
            for (String columnName : inputDataCheckCondition.getOtherShowColumns()) {
                Object columnValue = inputData.getFieldValue(columnName);
                if (Objects.isNull(columnValue)) {
                    dataGroupByColumnName.put(columnName, null);
                    continue;
                }
                if (columnValue instanceof String) {
                    String baseDataValue;
                    if ("ORGCODE".equalsIgnoreCase(columnName) || "OPPUNITID".equalsIgnoreCase(columnName) || "MDCODE".equalsIgnoreCase(columnName)) {
                        String orgColumnName = "MDCODE".equalsIgnoreCase(columnName) ? "ORGCODE" : columnName;
                        baseDataValue = InputDataCheckColumnDataUtils.getOtherShowColumnOrgTitle(orgColumnName, columnValue, fieldCodeOrgToolMap, showDictCode);
                    } else if ("SUBJECTOBJ".equalsIgnoreCase(columnName) || "SUBJECTCODE".equalsIgnoreCase(columnName)) {
                        String subjectTitle = InputDataCheckColumnDataUtils.getSubjectTitle(inputData.getReportSystemId(), subjectTitleGroupByCode, inputData.getSubjectCode());
                        baseDataValue = showDictCode ? inputData.getSubjectCode() + "|" + subjectTitle : subjectTitle;
                    } else if ("UNIONRULEID".equalsIgnoreCase(columnName)) {
                        baseDataValue = InputDataCheckColumnDataUtils.getUnionRuleTitle(ruleTitleGroupByCode, inputData.getUnionRuleId());
                    } else if ("CHECKTYPE".equals(columnName)) {
                        baseDataValue = StringUtils.isEmpty((String)inputData.getCheckType()) || StringUtils.isEmpty((String)inputData.getCheckState()) || InputDataCheckStateEnum.NOTCHECK.getValue().equals(inputData.getCheckState()) ? null : (InputDataCheckTypeEnum.MANUAL_ITEM.getValue().equals(inputData.getCheckType()) ? GcI18nUtil.getMessage((String)"gc.inputdata.check.manualchecktype") : GcI18nUtil.getMessage((String)"gc.inputdata.check.autochecktype"));
                    } else {
                        baseDataValue = InputDataCheckColumnDataUtils.getOtherShowColumnBaseDataTitle(columnName, columnValue, fieldCodeGroupByReferTableId, showDictCode);
                        if (StringUtils.isEmpty((String)baseDataValue)) {
                            baseDataValue = InputDataCheckColumnDataUtils.getOtherShowColumnOrgTitle(columnName, columnValue, fieldCodeOrgToolMap, showDictCode);
                        }
                    }
                    dataGroupByColumnName.put(columnName, baseDataValue);
                    continue;
                }
                dataGroupByColumnName.put(columnName, InputDataCheckColumnDataUtils.getOtherShowColumnConverterValue(columnValue));
            }
            if (isCheckTabDataFlag) {
                HashSet<String> checkGroupIds = new HashSet<String>();
                checkGroupIds.add(inputData.getCheckGroupId());
                dataGroupByColumnName.put("CHECKGROUPIDS", checkGroupIds);
            }
            HashSet<String> ids = new HashSet<String>();
            ids.add(inputData.getId());
            dataGroupByColumnName.put("IDS", ids);
            data.add(dataGroupByColumnName);
        }
        return InputDataCheckComparatorUtil.getCompareSumRecordIds(data);
    }

    private static String getOtherShowColumnOrgTitle(String columnName, Object columnValue, Map<String, GcOrgCenterService> fieldCodeOrgToolMap, boolean showDictCode) {
        if (fieldCodeOrgToolMap.isEmpty()) {
            return null;
        }
        String value = ConverterUtils.getAsString((Object)columnValue);
        if (StringUtils.isEmpty((String)value)) {
            return null;
        }
        GcOrgCenterService orgTool = fieldCodeOrgToolMap.get(columnName);
        if (orgTool == null) {
            return value;
        }
        GcOrgCacheVO orgByCode = orgTool.getOrgByCode(value);
        if (orgByCode == null || StringUtils.isEmpty((String)orgByCode.getTitle())) {
            return value;
        }
        return showDictCode ? value + "|" + orgByCode.getTitle() : orgByCode.getTitle();
    }

    private static String getOtherShowColumnConverterValue(Object columnValue) {
        if (columnValue instanceof Number) {
            return NumberUtils.doubleToString((double)NumberUtils.parseDouble((Object)columnValue));
        }
        if (columnValue instanceof Date) {
            return DateUtils.format((Date)((Date)columnValue), (String)"yyyy-MM-dd HH:mm:ss");
        }
        return ConverterUtils.getAsString((Object)columnValue);
    }

    private static String getSubjectTitle(String reportSystemId, Map<String, String> subjectTitleMap, String subjectCode) {
        if (subjectTitleMap.containsKey(subjectCode)) {
            return subjectTitleMap.get(subjectCode);
        }
        ConsolidatedSubjectEO subjectEO = consolidatedSubjectService.getSubjectByCode(reportSystemId, subjectCode);
        if (subjectEO != null) {
            subjectTitleMap.put(subjectCode, subjectEO.getTitle());
            return subjectEO.getTitle();
        }
        return null;
    }

    private static String getUnionRuleTitle(Map<String, String> ruleTitleGroupByCode, String ruleId) {
        if (ruleTitleGroupByCode.containsKey(ruleId)) {
            return ruleTitleGroupByCode.get(ruleId);
        }
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)ruleId);
        if (rule != null) {
            ruleTitleGroupByCode.put(ruleId, rule.getLocalizedName());
            return rule.getLocalizedName();
        }
        return null;
    }

    public static Map<String, String> initFieldCodeGroupByReferTableId(String taskId) {
        HashMap<String, String> fieldCodeGroupByReferTableId = new HashMap<String, String>();
        try {
            TableModelDefine inputDataTableDefine = dataModelService.getTableModelDefineByName(inputDataNameProvider.getTableNameByTaskId(taskId));
            List inputDataDefines = dataModelService.getColumnModelDefinesByTable(inputDataTableDefine.getID());
            for (ColumnModelDefine designFieldDefine : inputDataDefines) {
                TableModelDefine dictField;
                if (StringUtils.isEmpty((String)designFieldDefine.getReferTableID()) || (dictField = dataModelService.getTableModelDefineById(designFieldDefine.getReferTableID())) == null || StringUtils.isEmpty((String)designFieldDefine.getCode())) continue;
                fieldCodeGroupByReferTableId.put(designFieldDefine.getCode(), dictField.getName());
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
        }
        return fieldCodeGroupByReferTableId;
    }

    private static Map<String, GcOrgCenterService> initFieldCodeOrgToolMap(Map<String, String> fieldCodeDictTableMap, String periodStr) {
        if (fieldCodeDictTableMap == null || fieldCodeDictTableMap.isEmpty()) {
            return new HashMap<String, GcOrgCenterService>(2);
        }
        HashMap<String, GcOrgCenterService> fieldCodeOrgToolMap = new HashMap<String, GcOrgCenterService>();
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, periodStr);
        for (String fieldCode : fieldCodeDictTableMap.keySet()) {
            String dictTableName = fieldCodeDictTableMap.get(fieldCode);
            if (StringUtils.isEmpty((String)dictTableName) || !dictTableName.toUpperCase().startsWith("MD_ORG") || dictTableName.toUpperCase().equals("MD_ORG")) continue;
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dictTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yearPeriodObject);
            fieldCodeOrgToolMap.put(fieldCode, orgTool);
        }
        return fieldCodeOrgToolMap;
    }

    private static String getOtherShowColumnBaseDataTitle(String columnName, Object columnValue, Map<String, String> fieldCodeGroupByReferTableId, boolean showDictCode) {
        if (fieldCodeGroupByReferTableId.isEmpty()) {
            return null;
        }
        String dictTableName = fieldCodeGroupByReferTableId.get(columnName);
        if (StringUtils.isEmpty((String)dictTableName)) {
            return null;
        }
        String dictTitle = BaseDataUtils.getDictTitle((String)dictTableName, (String)((String)columnValue));
        if (StringUtils.isEmpty((String)dictTitle)) {
            return null;
        }
        return showDictCode ? columnValue + "|" + dictTitle : dictTitle;
    }

    private static String getSumRecordKey(InputDataEO inputData, List<String> checkGatherColumns, boolean convertToCorporate) {
        StringBuilder keyBuf = new StringBuilder();
        if (CollectionUtils.isEmpty(checkGatherColumns)) {
            String unitAndOppUnitIdStr;
            if (convertToCorporate) {
                CorporateConvertUtils.convertToOffsetUnit(inputData);
                unitAndOppUnitIdStr = InputDataCheckColumnDataUtils.getUnitComb(inputData.getOffsetOrgCode(), inputData.getOffsetOppUnitId());
            } else {
                unitAndOppUnitIdStr = InputDataCheckColumnDataUtils.getUnitComb(inputData.getOrgCode(), inputData.getOppUnitId());
            }
            keyBuf.append(unitAndOppUnitIdStr).append(inputData.getUnionRuleId()).append(inputData.getCheckGroupId());
            return keyBuf.toString();
        }
        for (String column : checkGatherColumns) {
            keyBuf.append(inputData.getFieldValue(column));
        }
        return keyBuf.toString();
    }

    private static String getUnitComb(String unitId, String oppUnitId) {
        StringBuilder buf = new StringBuilder();
        if (unitId.compareTo(oppUnitId) <= 0) {
            return buf.append(unitId).append(",").append(oppUnitId).toString();
        }
        return buf.append(oppUnitId).append(",").append(unitId).toString();
    }

    public static List<Map<String, Object>> listInputDataGroupDc(List<Map<String, Object>> inputDataItems) {
        ArrayList<Map<String, Object>> inputDataGroupItems = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(inputDataItems)) {
            return inputDataGroupItems;
        }
        HashMap<String, List> inputDataGroupByRecordKey = new HashMap<String, List>();
        for (Map<String, Object> inputData : inputDataItems) {
            String recordKey = String.valueOf(inputData.get("RECORDKEY"));
            List items = inputDataGroupByRecordKey.computeIfAbsent(recordKey, item -> new ArrayList());
            items.add(inputData);
        }
        for (String recordKey : inputDataGroupByRecordKey.keySet()) {
            List items = (List)inputDataGroupByRecordKey.get(recordKey);
            Map inputData = (Map)items.get(0);
            if (!StringUtils.isNull((String)String.valueOf(inputData.get("CHECKGROUPID")))) {
                inputDataGroupItems.addAll(items);
                continue;
            }
            Set dcs = items.stream().map(item -> String.valueOf(item.get("DC"))).collect(Collectors.toSet());
            if (dcs.size() <= 1) {
                for (Map item2 : items) {
                    String UnitCombDc = String.valueOf(item2.get("ORGCODE")) + String.valueOf(item2.get("OPPUNITID")) + String.valueOf(item2.get("UNIONRULEID")) + String.valueOf(item2.get("DC"));
                    item2.put("RECORDKEY", UnitCombDc);
                    inputDataGroupItems.add(item2);
                }
                continue;
            }
            inputDataGroupItems.addAll(items);
        }
        return inputDataGroupItems;
    }
}

