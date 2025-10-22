/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlExportExcelVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlExportExcelVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class SameCtrlRuleExportProcessor {
    private SameCtrlRuleVO ruleNode;
    private String reportSystem;
    private int order;
    private SameCtrlRuleVO data;

    public SameCtrlRuleExportProcessor(SameCtrlRuleVO ruleNode, String reportSystem) {
        this.ruleNode = ruleNode;
        this.reportSystem = reportSystem;
    }

    public List<SameCtrlExportExcelVO> getSheetData() {
        ArrayList<SameCtrlExportExcelVO> allRows = new ArrayList<SameCtrlExportExcelVO>();
        ArrayList<String> ruleTitle = new ArrayList<String>();
        this.order = 1;
        this.getSheetData(this.ruleNode.getChildren(), allRows, ruleTitle);
        return allRows;
    }

    private void getSheetData(List<SameCtrlRuleVO> ruleNode, List<SameCtrlExportExcelVO> allRows, List<String> ruleTitle) {
        ruleNode.forEach(ruleData -> {
            if ("group".equals(ruleData.getDataType())) {
                ruleTitle.add(ruleData.getTitle());
                this.getSheetData(ruleData.getChildren(), allRows, ruleTitle);
                ruleTitle.remove(ruleData.getTitle());
            } else {
                this.data = ruleData;
                this.getRowDatas(allRows, ruleTitle);
            }
        });
    }

    private void getRowDatas(List<SameCtrlExportExcelVO> allRows, List<String> ruleTitle) {
        StringBuilder ruleTitleBuilder = new StringBuilder();
        ruleTitle.forEach(title -> ruleTitleBuilder.append(title + "-"));
        ruleTitleBuilder.append(this.data.getTitle());
        Map mapJson = (Map)JsonUtils.readValue((String)this.data.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        this.getfetchConfig(mapJson, ruleTitleBuilder, this.order, allRows);
        ++this.order;
    }

    private void getfetchConfig(Map<String, Object> fetchConfigMap, StringBuilder ruleTitleBuilder, int order, List<SameCtrlExportExcelVO> allRows) {
        String groupOrder = null;
        Object debitItemStr = fetchConfigMap.get("debitItemList");
        Object creditItemStr = fetchConfigMap.get("creditItemList");
        String ruleTitle = ruleTitleBuilder.toString();
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)debitItemStr), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(debitItemList == null ? null : debitItemList, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u501f");
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)creditItemStr), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        this.getRowData(creditItemList == null ? null : creditItemList, ruleTitle, allRows, groupOrder == null ? String.valueOf(order) : groupOrder, "\u8d37");
    }

    private void getRowData(List<Map<String, Object>> debitOrCreditDatas, String ruleTitle, List<SameCtrlExportExcelVO> allRows, String orderStr, String debitOrCredit) {
        ExportParameter exportParameter = new ExportParameter();
        exportParameter.setTitle(ruleTitle);
        exportParameter.setDebitOrCredit(debitOrCredit);
        exportParameter.setOrder(orderStr);
        if (!CollectionUtils.isEmpty(debitOrCreditDatas)) {
            for (Map<String, Object> rowData : debitOrCreditDatas) {
                exportParameter.setRowData(rowData);
                this.setExportExcelVO(exportParameter, allRows);
            }
        } else {
            this.setExportExcelVO(exportParameter, allRows);
        }
    }

    private void setExportExcelVO(ExportParameter exportParameter, List<SameCtrlExportExcelVO> allRows) {
        SameCtrlExportExcelVO sameCtrlExportExcelVO = new SameCtrlExportExcelVO();
        Map<String, Object> rowData = exportParameter.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            String subjectCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)rowData.get("subjectCode")), (TypeReference)new TypeReference<String>(){});
            BaseDataVO subject = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{this.reportSystem})));
            sameCtrlExportExcelVO.setSubjectCode(subject.getCode());
            sameCtrlExportExcelVO.setSubjectTitle(subject.getTitle());
            String fetchFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)rowData.get("fetchFormula")), (TypeReference)new TypeReference<String>(){});
            sameCtrlExportExcelVO.setFormula(fetchFormula == null ? "" : fetchFormula);
            String investmentUnit = "investmentUnit";
            if (!Objects.isNull(rowData.get(investmentUnit))) {
                String invested = "INVESTED_UNIT";
                String investment = "INVESTMENT_UNIT";
                if (invested.equals(rowData.get(investmentUnit))) {
                    sameCtrlExportExcelVO.setUnit("\u88ab\u6295\u8d44\u5355\u4f4d");
                } else if (investment.equals(rowData.get(investmentUnit))) {
                    sameCtrlExportExcelVO.setUnit("\u6295\u8d44\u5355\u4f4d");
                } else {
                    sameCtrlExportExcelVO.setUnit("");
                }
            } else {
                sameCtrlExportExcelVO.setUnit("");
            }
        }
        sameCtrlExportExcelVO.setIndex(exportParameter.getOrder());
        sameCtrlExportExcelVO.setRuleTitle(exportParameter.getTitle());
        sameCtrlExportExcelVO.setDebitOrCredit(exportParameter.getDebitOrCredit());
        String ruleCondition = this.data.getRuleCondition() == null ? "" : this.data.getRuleCondition();
        sameCtrlExportExcelVO.setRuleCondition(ruleCondition);
        if (Objects.isNull(this.data.getBusinessTypeCode())) {
            sameCtrlExportExcelVO.setBusinessType("");
        } else {
            sameCtrlExportExcelVO.setBusinessType(this.data.getBusinessTypeCode().getTitle());
        }
        sameCtrlExportExcelVO.setRuleType(this.data.getRuleType().getName());
        sameCtrlExportExcelVO.setStartFlag(Boolean.TRUE.equals(this.data.getStartFlag()) ? "\u5426" : "\u662f");
        allRows.add(sameCtrlExportExcelVO);
    }

    static class ExportParameter {
        private String title;
        private String debitOrCredit;
        private String order;
        private Map<String, Object> rowData;

        ExportParameter() {
        }

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

        public Map<String, Object> getRowData() {
            return this.rowData;
        }

        public void setRowData(Map<String, Object> rowData) {
            this.rowData = rowData;
        }
    }
}

