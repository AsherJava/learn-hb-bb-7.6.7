/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor$ExportParameter
 *  com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.gcreport.calculate.rule.floatline;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FloatLineRuleExportProcessor
extends UnionRuleExportProcessor {
    private Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    private IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
    private FormDefine formDefine;

    public FloatLineRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        super(data, reportSystemId, order);
    }

    public void getRowDatas(List<ExportExcelVO> allRows, List<String> ruleTitle) {
        StringBuilder ruleTitleBuilder = new StringBuilder();
        ruleTitle.forEach(title -> ruleTitleBuilder.append(title + "-"));
        ruleTitleBuilder.append(this.data.getTitle());
        Map mapJson = (Map)JsonUtils.readValue((String)this.data.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        this.ruleOptions = this.getOptionStr(mapJson);
        String floatLineDataRegion = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("floatLineDataRegion")), String.class);
        if (!StringUtils.isEmpty(floatLineDataRegion)) {
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(floatLineDataRegion);
            this.formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
        } else {
            this.formDefine = null;
        }
        this.getfetchConfig(mapJson, ruleTitleBuilder, 0, this.order, allRows);
    }

    protected List<String> loadCustomerOptions(Map<String, Object> mapJson) {
        DataRegionDefine dataRegionDefine;
        ArrayList<String> ruleOptions = new ArrayList<String>();
        String fetchUnit = ConverterUtils.getAsString((Object)mapJson.get("fetchUnit"));
        ruleOptions.add("\u53d6\u6570\u5355\u4f4d: " + (StringUtils.hasText(fetchUnit) ? FetchRangeEnum.codeOf((String)fetchUnit).getName() : "") + "\n");
        String floatLineDataRegion = ConverterUtils.getAsString((Object)mapJson.get("floatLineDataRegion"));
        if (StringUtils.hasText(floatLineDataRegion) && Objects.nonNull(dataRegionDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryDataRegionDefine(floatLineDataRegion))) {
            floatLineDataRegion = floatLineDataRegion + "|" + dataRegionDefine.getTitle();
        }
        ruleOptions.add("\u9009\u62e9\u6d6e\u52a8\u884c: " + (StringUtils.hasText(floatLineDataRegion) ? floatLineDataRegion : "") + "\n");
        ruleOptions.add("\u62b5\u9500\u5dee\u989d\u6309\u6bd4\u4f8b\u5206\u914d: " + (ConverterUtils.getAsBooleanValue((Object)mapJson.get("proportionOffsetDiffFlag")) ? "\u662f" : "\u5426") + "\n");
        return ruleOptions;
    }

    protected List<ExportExcelVO> setExportExcelVO(UnionRuleExportProcessor.ExportParameter exportParameter, List<ExportExcelVO> allRows) {
        ExportExcelVO exportExcelVO = new ExportExcelVO();
        Map rowData = exportParameter.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            try {
                if (!Objects.isNull(rowData.get("subject"))) {
                    FieldDefine fieldDefineSubject = this.iDataDefinitionRuntimeController.queryFieldDefine(rowData.get("subject").toString());
                    exportExcelVO.setSubjectCode(this.formDefine.getFormCode() + "|" + fieldDefineSubject.getCode());
                } else {
                    exportExcelVO.setSubjectCode("");
                }
            }
            catch (Exception e) {
                if ("\u501f".equals(exportParameter.getDebitOrCredit())) {
                    throw new BusinessRuntimeException("\u67e5\u8be2\u501f\u65b9\u79d1\u76ee\u6570\u636e\u5b57\u6bb5\u5f02\u5e38\u3002");
                }
                throw new BusinessRuntimeException("\u67e5\u8be2\u8d37\u65b9\u79d1\u76ee\u6570\u636e\u5b57\u6bb5\u5f02\u5e38\u3002");
            }
            String fetchFormula = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(rowData.get("fetchFormula")), (TypeReference)new TypeReference<String>(){});
            exportExcelVO.setFormula(fetchFormula == null ? "" : fetchFormula);
            try {
                if (!Objects.isNull(rowData.get("unit"))) {
                    FieldDefine fieldDefineUnit = this.iDataDefinitionRuntimeController.queryFieldDefine(rowData.get("unit").toString());
                    exportExcelVO.setUnit(this.formDefine.getFormCode() + "|" + fieldDefineUnit.getCode());
                } else {
                    exportExcelVO.setUnit("");
                }
            }
            catch (Exception e) {
                if ("\u501f".equals(exportParameter.getDebitOrCredit())) {
                    throw new BusinessRuntimeException("\u67e5\u8be2\u501f\u65b9\u5355\u4f4d\u6570\u636e\u5b57\u6bb5\u5f02\u5e38\u3002");
                }
                throw new BusinessRuntimeException("\u67e5\u8be2\u8d37\u65b9\u5355\u4f4d\u6570\u636e\u5b57\u6bb5\u5f02\u5e38\u3002");
            }
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
        Map dimensions = exportParameter.getDimensions();
        if (!CollectionUtils.isEmpty(dimensions)) {
            HashMap<String, String> map = new HashMap<String, String>(8);
            for (String key : dimensions.keySet()) {
                if (Objects.isNull(dimensions.get(key))) continue;
                String value = dimensions.get(key).toString();
                try {
                    if (StringUtils.isEmpty(value)) {
                        map.put(key, "");
                        continue;
                    }
                    if (key.endsWith("_customizeFormula")) {
                        map.put(key.split("_customizeFormula")[0], value);
                        continue;
                    }
                    FieldDefine fieldDefineDimension = this.iDataDefinitionRuntimeController.queryFieldDefine(value);
                    map.put(key, fieldDefineDimension.getTitle());
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            exportExcelVO.setDimensions(map);
        }
        allRows.add(exportExcelVO);
        return allRows;
    }
}

