/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.billcore.web.BillInfoController
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor$ExportParameter
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.lease.rule.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.billcore.web.BillInfoController;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MasterBillRuleExportProcessor
extends UnionRuleExportProcessor {
    public MasterBillRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        super(data, reportSystemId, order);
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
        Map dimensions = exportParameter.getDimensions();
        if (!CollectionUtils.isEmpty(dimensions)) {
            HashMap<String, String> map = new HashMap<String, String>(8);
            for (String key : dimensions.keySet()) {
                if (Objects.isNull(dimensions.get(key))) continue;
                String value = dimensions.get(key).toString();
                if ("UNITCODE".equals(value)) {
                    map.put(key, "\u672c\u65b9\u5355\u4f4d");
                    continue;
                }
                if ("OPPUNITCODE".equals(value)) {
                    map.put(key, "\u5bf9\u65b9\u5355\u4f4d");
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
        exportExcelVO.setOptions(this.ruleOptions);
        allRows.add(exportExcelVO);
        return allRows;
    }

    protected List<String> loadCustomerOptions(Map<String, Object> mapJson) {
        Map<Object, String> billDefineMap;
        BillInfoController billInfoController;
        BusinessResponseEntity billDefines;
        ArrayList<String> ruleOptions = new ArrayList<String>();
        String billDefineId = ConverterUtils.getAsString((Object)mapJson.get("billDefineId"));
        if (StringUtils.hasText(billDefineId) && (billDefines = (billInfoController = (BillInfoController)SpringBeanUtils.getBean(BillInfoController.class)).getBillDefines()).isSuccess() && !CollectionUtils.isEmpty((Collection)billDefines.getData()) && (billDefineMap = ((List)billDefines.getData()).stream().collect(Collectors.groupingBy(selectOptionVO -> selectOptionVO.getValue(), Collectors.collectingAndThen(Collectors.toList(), list -> ((SelectOptionVO)list.get(0)).getLabel())))).containsKey(billDefineId) && StringUtils.hasText(billDefineMap.get(billDefineId))) {
            billDefineId = billDefineId + "|" + billDefineMap.get(billDefineId);
        }
        ruleOptions.add("\u5355\u636e\u5b9a\u4e49: " + (StringUtils.hasText(billDefineId) ? billDefineId : "") + "\n");
        return ruleOptions;
    }
}

