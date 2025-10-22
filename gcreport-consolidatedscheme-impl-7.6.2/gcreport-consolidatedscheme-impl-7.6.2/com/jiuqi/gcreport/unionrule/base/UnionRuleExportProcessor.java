/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.unionrule.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class UnionRuleExportProcessor {
    protected UnionRuleVO data;
    protected String reportSystemId;
    protected int order;
    protected final String debit = "\u501f";
    protected String ruleOptions = "";
    protected IDataDefinitionRuntimeController iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);

    public UnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        this.data = data;
        this.reportSystemId = reportSystemId;
        this.order = order;
    }

    public void getRowDatas(List<ExportExcelVO> allRows, List<String> ruleTitle) {
        StringBuilder ruleTitleBuilder = new StringBuilder();
        ruleTitle.forEach(title -> ruleTitleBuilder.append(title + "-"));
        ruleTitleBuilder.append(this.data.getTitle());
        Map mapJson = (Map)JsonUtils.readValue((String)this.data.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        this.ruleOptions = this.getOptionStr(mapJson);
        this.getfetchConfig(mapJson, ruleTitleBuilder, 0, this.order, allRows);
    }

    protected String getOptionStr(Map<String, Object> mapJson) {
        List<String> options = this.loadBaseOptions();
        options.addAll(this.loadCustomerOptions(mapJson));
        StringBuilder ruleOptions = new StringBuilder();
        if (!CollectionUtils.isEmpty(options)) {
            for (int i = 0; i < options.size(); ++i) {
                ruleOptions.append(i + 1).append(".").append(options.get(i));
            }
        }
        return ruleOptions.toString();
    }

    protected List<String> loadBaseOptions() {
        ArrayList<String> ruleOptions = new ArrayList<String>();
        ruleOptions.add("\u662f\u5426\u542f\u7528\u5bb9\u5dee: " + (Boolean.TRUE.equals(this.data.getEnableToleranceFlag()) ? "\u662f" : "\u5426") + "\n");
        if (this.data.getEnableToleranceFlag().booleanValue()) {
            ruleOptions.add("\u5bb9\u5dee\u65b9\u5f0f: " + (StringUtils.hasText(this.data.getToleranceType()) ? ToleranceTypeEnum.codeOf((String)this.data.getToleranceType()).getName() : "") + "\n");
            ruleOptions.add("\u62b5\u9500\u91d1\u989d\u7b56\u7565: " + (StringUtils.hasText(this.data.getOffsetType()) ? OffsetTypeEnum.codeOf((String)this.data.getOffsetType()).getName() : "") + "\n");
            ruleOptions.add("\u81ea\u5b9a\u4e49\u62b5\u9500\u516c\u5f0f: " + (Objects.isNull(this.data.getOffsetFormula()) ? "" : this.data.getOffsetFormula()) + "\n");
            ruleOptions.add("\u5bb9\u5dee\u8303\u56f4: " + this.data.getToleranceRange() + "\n");
        }
        return ruleOptions;
    }

    protected List<String> loadCustomerOptions(Map<String, Object> mapJson) {
        return new ArrayList<String>();
    }

    protected List<ExportExcelVO> getfetchConfig(Map<String, Object> fetchConfigMap, StringBuilder ruleTitleBuilder, int fetchConfigOrder, int order, List<ExportExcelVO> allRows) {
        Object debitConfigOrItemList = null;
        Object creditConfigOrItemList = null;
        String groupOrder = null;
        String ruleTitle = "";
        debitConfigOrItemList = fetchConfigMap.get("debitItemList");
        creditConfigOrItemList = fetchConfigMap.get("creditItemList");
        ruleTitle = ruleTitleBuilder.toString();
        List debitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)debitConfigOrItemList), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(debitConfigLists == null ? null : debitConfigLists, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u501f");
        List creditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)creditConfigOrItemList), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(creditConfigLists == null ? null : creditConfigLists, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u8d37");
        return allRows;
    }

    protected List<ExportExcelVO> getRowData(List<Map<String, Object>> debitOrCreditDatas, String ruleTitle, List<ExportExcelVO> allRows, String orderStr, String debitOrCredit) {
        ExportParameter exportParameter = new ExportParameter();
        exportParameter.setTitle(ruleTitle);
        exportParameter.setDebitOrCredit(debitOrCredit);
        exportParameter.setOrder(orderStr);
        if (!CollectionUtils.isEmpty(debitOrCreditDatas)) {
            for (int j = 0; j < debitOrCreditDatas.size(); ++j) {
                Map<String, Object> rowData = debitOrCreditDatas.get(j);
                Map dimensionsMap = null;
                if (!StringUtils.isEmpty(rowData.get("dimensions"))) {
                    dimensionsMap = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)rowData.get("dimensions")), (TypeReference)new TypeReference<Map<String, Object>>(){});
                }
                exportParameter.setDimensions(dimensionsMap);
                exportParameter.setRowData(rowData);
                this.setExportExcelVO(exportParameter, allRows);
            }
        } else {
            this.setExportExcelVO(exportParameter, allRows);
        }
        return allRows;
    }

    protected List<ExportExcelVO> setExportExcelVO(ExportParameter exportParameter, List<ExportExcelVO> allRows) {
        ExportExcelVO exportExcelVO = new ExportExcelVO();
        Map<String, Object> rowData = exportParameter.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            String subjectCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)rowData.get("subjectCode")), (TypeReference)new TypeReference<String>(){});
            BaseDataVO subject = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{this.reportSystemId})));
            if (subject != null) {
                exportExcelVO.setSubjectCode(subject.getCode());
                exportExcelVO.setSubjectTitle(subject.getTitle());
            } else {
                exportExcelVO.setSubjectCode(subjectCode);
                exportExcelVO.setSubjectTitle(subjectCode);
            }
            String fetchFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)rowData.get("fetchFormula")), (TypeReference)new TypeReference<String>(){});
            exportExcelVO.setFormula(fetchFormula == null ? "" : fetchFormula);
            exportExcelVO.setUnit("");
        }
        exportExcelVO.setIndex(exportParameter.getOrder());
        exportExcelVO.setRuleTitle(exportParameter.getTitle());
        exportExcelVO.setDebitOrCredit(exportParameter.getDebitOrCredit());
        String ruleCondition = this.data.getRuleCondition() == null ? "" : this.data.getRuleCondition();
        exportExcelVO.setRuleCondition(ruleCondition);
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

    public static class ExportParameter {
        protected String title;
        protected String debitOrCredit;
        protected String order;
        protected Map<String, Object> dimensions;
        protected Map<String, Object> rowData;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDebitOrCredit() {
            return this.debitOrCredit;
        }

        public void setDebitOrCredit(String debitOrCredit) {
            this.debitOrCredit = debitOrCredit;
        }

        public String getOrder() {
            return this.order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public Map<String, Object> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, Object> dimensions) {
            this.dimensions = dimensions;
        }

        public Map<String, Object> getRowData() {
            return this.rowData;
        }

        public void setRowData(Map<String, Object> rowData) {
            this.rowData = rowData;
        }
    }
}

