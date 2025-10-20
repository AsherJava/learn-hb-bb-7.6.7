/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor$ExportParameter
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FinancialCheckRuleExportProcessor
extends UnionRuleExportProcessor {
    private String filterFormula;
    private Map<String, Object> fetchConfigMap;

    public FinancialCheckRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        super(data, reportSystemId, order);
    }

    public void getRowDatas(List<ExportExcelVO> allRows, List<String> ruleTitle) {
        StringBuilder ruleTitleBuilder = new StringBuilder();
        ruleTitle.forEach(title -> ruleTitleBuilder.append(title + "-"));
        ruleTitleBuilder.append(this.data.getTitle());
        Map mapJson = (Map)JsonUtils.readValue((String)this.data.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        String fetchConfig = JsonUtils.writeValueAsString(mapJson.get("fetchConfigList"));
        List fetchConfigList = (List)JsonUtils.readValue((String)fetchConfig, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        if (!CollectionUtils.isEmpty(fetchConfigList)) {
            for (int i = 0; i < fetchConfigList.size(); ++i) {
                this.fetchConfigMap = (Map)fetchConfigList.get(i);
                this.ruleOptions = this.getOptionStr(mapJson);
                this.getfetchConfig(this.fetchConfigMap, ruleTitleBuilder, i + 1, this.order, allRows);
            }
        } else {
            this.ruleOptions = this.getOptionStr(mapJson);
            this.getRowData(null, ruleTitleBuilder.toString(), allRows, String.valueOf(this.order), "");
        }
    }

    protected List<ExportExcelVO> getfetchConfig(Map<String, Object> fetchConfigMap, StringBuilder ruleTitleBuilder, int fetchConfigOrder, int order, List<ExportExcelVO> allRows) {
        Object debitConfigOrItemList = null;
        Object creditConfigOrItemList = null;
        String groupOrder = null;
        String ruleTitle = "";
        StringBuilder descriptionString = new StringBuilder();
        String description = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fetchConfigMap.get("description")), (TypeReference)new TypeReference<String>(){});
        if (fetchConfigOrder > 1) {
            String OrderStr = String.valueOf(order);
            String fetchConfigOrderStr = String.valueOf(fetchConfigOrder);
            groupOrder = OrderStr + "." + fetchConfigOrderStr;
        }
        ruleTitle = "".equals(description) ? descriptionString.append((CharSequence)ruleTitleBuilder).append("(" + fetchConfigOrder + ")").toString() : descriptionString.append((CharSequence)ruleTitleBuilder).append("(" + description + ")").toString();
        debitConfigOrItemList = fetchConfigMap.get("debitConfigList");
        creditConfigOrItemList = fetchConfigMap.get("creditConfigList");
        this.filterFormula = fetchConfigMap.get("filterFormula") == null ? "" : fetchConfigMap.get("filterFormula").toString();
        List debitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)debitConfigOrItemList), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(debitConfigLists == null ? null : debitConfigLists, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u501f");
        List creditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)creditConfigOrItemList), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(creditConfigLists == null ? null : creditConfigLists, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u8d37");
        return allRows;
    }

    protected List<ExportExcelVO> setExportExcelVO(UnionRuleExportProcessor.ExportParameter exportParameter, List<ExportExcelVO> allRows) {
        ExportExcelVO exportExcelVO = new ExportExcelVO();
        Map rowData = exportParameter.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            String subjectCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(rowData.get("subjectCode")), (TypeReference)new TypeReference<String>(){});
            BaseDataVO subject = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{this.reportSystemId})));
            if (subject != null) {
                exportExcelVO.setSubjectCode(subject.getCode());
                exportExcelVO.setSubjectTitle(subject.getTitle());
            } else {
                exportExcelVO.setSubjectCode(subjectCode);
                exportExcelVO.setSubjectTitle(subjectCode);
            }
            String fetchFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(rowData.get("fetchFormula")), (TypeReference)new TypeReference<String>(){});
            exportExcelVO.setFormula(fetchFormula == null ? "" : fetchFormula);
            exportExcelVO.setUnit("");
        }
        exportExcelVO.setIndex(exportParameter.getOrder());
        exportExcelVO.setRuleTitle(exportParameter.getTitle());
        exportExcelVO.setDebitOrCredit(exportParameter.getDebitOrCredit());
        String ruleCondition = this.data.getRuleCondition() == null ? "" : this.data.getRuleCondition();
        exportExcelVO.setRuleCondition("1." + ruleCondition + "\n2." + this.filterFormula);
        List applyGcUnits = this.data.getApplyGcUnitMap();
        if (!CollectionUtils.isEmpty(applyGcUnits)) {
            StringBuilder applyUnitCondition = new StringBuilder("");
            applyGcUnits.forEach(unit -> applyUnitCondition.append((String)unit.get("code")).append(" | ").append((String)unit.get("title")).append("\n"));
            exportExcelVO.setApplyGcUnits(applyUnitCondition.toString());
        }
        if (Objects.isNull(this.data.getBusinessTypeCode())) {
            exportExcelVO.setBusinessType("");
        } else {
            exportExcelVO.setBusinessType(this.data.getBusinessTypeCode().getTitle());
        }
        exportExcelVO.setRuleType(this.data.getRuleTypeDescription());
        exportExcelVO.setStartFlag(Boolean.TRUE.equals(this.data.getStartFlag()) ? "\u5426" : "\u662f");
        exportExcelVO.setOptions(this.ruleOptions);
        allRows.add(exportExcelVO);
        return allRows;
    }

    protected List<String> loadCustomerOptions(Map<String, Object> mapJson) {
        ArrayList<String> options = new ArrayList<String>();
        options.add("\u5df2\u5bf9\u8d26: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("checked")) ? "\u662f" : "\u5426") + "\n");
        options.add("\u652f\u6301\u5355\u8fb9\u62b5\u9500: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("unilateralOffsetFlag")) ? "\u662f" : "\u5426") + "\n");
        options.add("\u5df2\u5bf9\u8d26\u91cd\u65b0\u62b5\u9500: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("delCheckedOffsetFlag")) ? "\u662f" : "\u5426") + "\n");
        options.add("\u62b5\u9500\u5dee\u989d\u6309\u6bd4\u4f8b\u5206\u914d: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("proportionOffsetDiffFlag")) ? "\u662f" : "\u5426") + "\n");
        List offsetGroupingFieldS = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("offsetGroupingField")), (TypeReference)new TypeReference<List<String>>(){});
        if (!CollectionUtils.isEmpty(offsetGroupingFieldS)) {
            List<Map<String, String>> groups = FinancialCheckOffsetUtils.getOffsetGroupingField();
            HashMap<String, String> groupMap = new HashMap<String, String>();
            Iterator<Map<String, String>> iterator = groups.iterator();
            while (iterator.hasNext()) {
                Map<String, String> group;
                Map<String, String> realGroup = group = iterator.next();
                groupMap.put(ConverterUtils.getAsString((Object)realGroup.get("key")), ConverterUtils.getAsString((Object)realGroup.get("label")));
            }
            offsetGroupingFieldS = offsetGroupingFieldS.stream().map(field -> {
                if (groupMap.containsKey(field) && StringUtils.hasText((String)groupMap.get(field))) {
                    return field + "|" + (String)groupMap.get(field);
                }
                return field;
            }).collect(Collectors.toList());
        }
        options.add("\u62b5\u9500\u5206\u7ec4\u5b57\u6bb5: " + offsetGroupingFieldS + "\n");
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("debitItemList")), (TypeReference)new TypeReference<List<String>>(){});
        if (!CollectionUtils.isEmpty(debitItemList)) {
            debitItemList = debitItemList.stream().map(subjectCode -> {
                GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_ACCTSUBJECT", subjectCode);
                if (Objects.isNull(subject)) {
                    return subjectCode;
                }
                return subjectCode + "|" + subject.getTitle();
            }).collect(Collectors.toList());
        }
        options.add("\u501f\u65b9\u6570\u636e\u6e90: " + debitItemList + "\n");
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("creditItemList")), (TypeReference)new TypeReference<List<String>>(){});
        if (!CollectionUtils.isEmpty(creditItemList)) {
            creditItemList = creditItemList.stream().map(subjectCode -> {
                GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_ACCTSUBJECT", subjectCode);
                if (Objects.isNull(subject)) {
                    return subjectCode;
                }
                return subjectCode + "|" + subject.getTitle();
            }).collect(Collectors.toList());
        }
        options.add("\u8d37\u65b9\u6570\u636e\u6e90: " + creditItemList + "\n");
        options.add("\u521d\u59cb\u89c4\u5219: " + (Boolean.TRUE.equals(this.data.getInitTypeFlag()) ? "\u662f" : "\u5426") + "\n");
        if (Objects.nonNull(this.fetchConfigMap)) {
            options.addAll(this.getFetchNumSetOption());
        }
        return options;
    }

    private List<String> getFetchNumSetOption() {
        GcBaseData businessType;
        ArrayList<String> options = new ArrayList<String>();
        String businessTypeCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)this.fetchConfigMap.get("businessTypeCode")), (TypeReference)new TypeReference<String>(){});
        if (StringUtils.hasText(businessTypeCode) && Objects.nonNull(businessType = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCBUSINESSTYPE", businessTypeCode))) {
            businessTypeCode = businessTypeCode + "|" + businessType.getTitle();
        }
        options.add("\u53d6\u6570\u8bbe\u7f6e-\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b: " + businessTypeCode + "\n");
        String filterFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)this.fetchConfigMap.get("filterFormula")), (TypeReference)new TypeReference<String>(){});
        options.add("\u81ea\u52a8\u8fc7\u6ee4\u6761\u4ef6: " + filterFormula + "\n");
        String manualFilterFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)this.fetchConfigMap.get("manualFilterFormula")), (TypeReference)new TypeReference<String>(){});
        options.add("\u624b\u52a8\u8fc7\u6ee4\u6761\u4ef6: " + manualFilterFormula + "\n");
        return options;
    }
}

