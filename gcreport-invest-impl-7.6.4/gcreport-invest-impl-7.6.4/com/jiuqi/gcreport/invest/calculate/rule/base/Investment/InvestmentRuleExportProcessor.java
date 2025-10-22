/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor$ExportParameter
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.invest.calculate.rule.base.Investment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class InvestmentRuleExportProcessor
extends UnionRuleExportProcessor {
    public InvestmentRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        super(data, reportSystemId, order);
    }

    protected List<ExportExcelVO> setExportExcelVO(UnionRuleExportProcessor.ExportParameter exportParameter, List<ExportExcelVO> allRows) {
        ExportExcelVO exportExcelVO = new ExportExcelVO();
        Map rowData = exportParameter.getRowData();
        this.setExcelVoByRowData(exportExcelVO, rowData);
        exportExcelVO.setIndex(exportParameter.getOrder());
        exportExcelVO.setRuleTitle(exportParameter.getTitle());
        exportExcelVO.setDebitOrCredit(exportParameter.getDebitOrCredit());
        String ruleCondition = this.data.getRuleCondition() == null ? "" : this.data.getRuleCondition();
        exportExcelVO.setRuleCondition(ruleCondition);
        this.setApplyGcUnits(exportExcelVO);
        if (Objects.isNull(this.data.getBusinessTypeCode())) {
            exportExcelVO.setBusinessType("");
        } else {
            exportExcelVO.setBusinessType(this.data.getBusinessTypeCode().getTitle());
        }
        exportExcelVO.setRuleType(this.data.getRuleTypeDescription());
        exportExcelVO.setStartFlag(Boolean.TRUE.equals(this.data.getStartFlag()) ? "\u5426" : "\u662f");
        exportExcelVO.setOptions(this.ruleOptions);
        this.setDimensions(exportParameter, exportExcelVO);
        allRows.add(exportExcelVO);
        return allRows;
    }

    private void setExcelVoByRowData(ExportExcelVO exportExcelVO, Map<String, Object> rowData) {
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
            if ("INVESTED_UNIT".equals(rowData.get("investmentUnit"))) {
                exportExcelVO.setUnit("\u88ab\u6295\u8d44\u5355\u4f4d");
            } else if ("INVESTMENT_UNIT".equals(rowData.get("investmentUnit"))) {
                exportExcelVO.setUnit("\u6295\u8d44\u5355\u4f4d");
            } else {
                exportExcelVO.setUnit("");
            }
        }
    }

    private void setApplyGcUnits(ExportExcelVO exportExcelVO) {
        List applyGcUnits = this.data.getApplyGcUnitMap();
        if (!CollectionUtils.isEmpty(applyGcUnits)) {
            StringBuilder applyUnitCondition = new StringBuilder();
            applyGcUnits.forEach(unit -> applyUnitCondition.append((String)unit.get("code")).append(" | ").append((String)unit.get("title")).append("\n"));
            exportExcelVO.setApplyGcUnits(applyUnitCondition.toString());
        }
    }

    private void setDimensions(UnionRuleExportProcessor.ExportParameter exportParameter, ExportExcelVO exportExcelVO) {
        if (!CollectionUtils.isEmpty(exportParameter.getDimensions())) {
            HashMap<String, String> map = new HashMap<String, String>(8);
            for (String key : exportParameter.getDimensions().keySet()) {
                String value = String.valueOf(exportParameter.getDimensions().get(key));
                if ("INVESTED_UNIT".equals(value)) {
                    map.put(key, "\u88ab\u6295\u8d44\u5355\u4f4d");
                    continue;
                }
                if ("INVESTMENT_UNIT".equals(value)) {
                    map.put(key, "\u6295\u8d44\u5355\u4f4d");
                    continue;
                }
                if ("ACCOUNT".equals(value)) {
                    map.put(key, "\u53f0\u8d26");
                    continue;
                }
                if (null != exportParameter.getDimensions().get(key + "_customizeFormula")) {
                    map.put(key, (String)exportParameter.getDimensions().get(key + "_customizeFormula"));
                    continue;
                }
                map.put(key, "");
            }
            exportExcelVO.setDimensions(map);
        }
    }

    protected List<String> loadCustomerOptions(Map<String, Object> mapJson) {
        ArrayList<String> ruleOptions = new ArrayList<String>();
        ruleOptions.add("\u6a21\u62df\u6743\u76ca\u6cd5\u8c03\u6574: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("equityLawAdjustFlag")) ? "\u662f" : "\u5426") + "\n");
        UnionRuleService unionRuleService = (UnionRuleService)SpringBeanUtils.getBean(UnionRuleService.class);
        Map changeRatioOption = unionRuleService.getChangeRatioOption(this.data.getReportSystem());
        if (ConverterUtils.getAsBooleanValue(changeRatioOption.get("monthlyIncrement"))) {
            List options = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(changeRatioOption.get("changeRatios")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
            List initialMerge = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("initialMerge")), (TypeReference)new TypeReference<List<String>>(){});
            List goingConcern = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("goingConcern")), (TypeReference)new TypeReference<List<String>>(){});
            List dealWith = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)mapJson.get("dealWith")), (TypeReference)new TypeReference<List<String>>(){});
            if (!CollectionUtils.isEmpty(options)) {
                Map<Object, String> optionsMap = options.stream().collect(Collectors.groupingBy(selectOptionVO -> selectOptionVO.getValue(), Collectors.collectingAndThen(Collectors.toList(), list -> ((SelectOptionVO)list.get(0)).getLabel())));
                initialMerge = initialMerge.stream().map(x -> {
                    if (optionsMap.containsKey(x) && StringUtils.hasText((String)optionsMap.get(x))) {
                        return x + "|" + (String)optionsMap.get(x);
                    }
                    return x;
                }).collect(Collectors.toList());
                goingConcern = goingConcern.stream().map(x -> {
                    if (optionsMap.containsKey(x) && StringUtils.hasText((String)optionsMap.get(x))) {
                        return x + "|" + (String)optionsMap.get(x);
                    }
                    return x;
                }).collect(Collectors.toList());
                dealWith = dealWith.stream().map(x -> {
                    if (optionsMap.containsKey(x) && StringUtils.hasText((String)optionsMap.get(x))) {
                        return x + "|" + (String)optionsMap.get(x);
                    }
                    return x;
                }).collect(Collectors.toList());
            }
            ruleOptions.add("\u521d\u6b21\u5408\u5e76\u573a\u666f: " + initialMerge + "\n");
            ruleOptions.add("\u6301\u7eed\u7ecf\u8425\u573a\u666f: " + goingConcern + "\n");
            ruleOptions.add("\u5904\u7f6e\u573a\u666f: " + dealWith + "\n");
        }
        ruleOptions.add("\u521d\u59cb\u89c4\u5219: " + (Boolean.TRUE.equals(this.data.getInitTypeFlag()) ? "\u662f" : "\u5426") + "\n");
        return ruleOptions;
    }
}

