/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping2.web.vo.SelectOptionVO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.NrFormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigImportTask {
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;

    public List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigImport(Map<String, Object> params, List<Object[]> excelSheetDataList, String type, StringBuilder errorLog) {
        if (CollectionUtils.isEmpty(excelSheetDataList)) {
            return Collections.emptyList();
        }
        List<NrFormulaSchemeConfigTableVO> formulaContentRowList = null;
        try {
            formulaContentRowList = this.parseExcelContentRow(params, errorLog, excelSheetDataList, type);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6821\u9a8c\u65f6\u53d1\u751f\u5f02\u5e38\uff01", (Throwable)e);
        }
        return formulaContentRowList;
    }

    private List<NrFormulaSchemeConfigTableVO> parseExcelContentRow(Map<String, Object> params, StringBuilder errorLog, List<Object[]> excelSheetDataList, String type) {
        String taskId = (String)params.get("taskId");
        List<String> titleCodes = NrFormulaSchemeConfigUtils.listTitleCode(taskId, type);
        ArrayList<Map<String, String>> rowDataGroupByCodes = new ArrayList<Map<String, String>>();
        for (int i = 1; i < excelSheetDataList.size(); ++i) {
            Object[] rowData = excelSheetDataList.get(i);
            HashMap<String, String> rowDataGroupByCode = new HashMap<String, String>();
            for (int j = 0; j < titleCodes.size(); ++j) {
                rowDataGroupByCode.put(titleCodes.get(j), StringUtils.toViewString((Object)rowData[j]));
            }
            rowDataGroupByCodes.add(rowDataGroupByCode);
        }
        if (rowDataGroupByCodes.isEmpty()) {
            return CollectionUtils.newArrayList();
        }
        return this.listFormulaSchemeConfigTableVO(params, type, errorLog, rowDataGroupByCodes);
    }

    private List<NrFormulaSchemeConfigTableVO> listFormulaSchemeConfigTableVO(Map<String, Object> params, String type, StringBuilder errorLog, List<Map<String, String>> rowDataGroupByCodes) {
        ArrayList<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOList = new ArrayList<NrFormulaSchemeConfigTableVO>();
        String taskId = (String)params.get("taskId");
        String schemeId = (String)params.get("schemeId");
        String entityId = (String)params.get("entityId");
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        Map<String, Object> formulaSchemeData = this.formulaSchemeConfigService.getFormulaSchemesBySchemeId(schemeId, entityId);
        Map<String, Map<String, Object>> currentRowByTitle = this.getCurrentRowByTitle(formulaSchemeData);
        Map<String, Object> fetchSchemes = currentRowByTitle.get("fetchSchemes");
        Map<String, Object> formulaSchemes = currentRowByTitle.get("formulaSchemes");
        Map<String, Object> convertSystemSchemes = currentRowByTitle.get("convertSystemSchemes");
        Map<String, Object> currencyTitleMap = currentRowByTitle.get("currencyTitle");
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId);
        for (int i = 0; i < rowDataGroupByCodes.size(); ++i) {
            String convertSystemSchemeTitle;
            String orgType;
            OrgDO orgDO;
            Map<String, String> rowDataGroupBuCode = rowDataGroupByCodes.get(i);
            String taskTitle = rowDataGroupBuCode.get("taskId");
            if (!taskDefine.getTitle().equals(taskTitle)) {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u62a5\u8868\u4efb\u52a1\uff1a").append(taskTitle).append("\u4e0e\u6240\u9009\u62a5\u8868\u4efb\u52a1\u4e0d\u7b26\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            String schemeTitle = rowDataGroupBuCode.get("schemeId");
            if (!formSchemeDefine.getTitle().equals(schemeTitle)) {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u62a5\u8868\u65b9\u6848\uff1a").append(schemeTitle).append("\u4e0e\u6240\u9009\u62a5\u8868\u65b9\u6848\u4e0d\u7b26\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            String orgCode = rowDataGroupBuCode.get("orgId");
            if (orgCode.indexOf("|") > -1) {
                orgCode = orgCode.split("\\|")[0].trim();
            }
            if ((orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)(orgType = (String)formulaSchemeData.get("orgType")), (String)orgCode).get(0)) != null) {
                Boolean isLeaf = (Boolean)orgDO.get((Object)"isLeaf");
                if ("batchStrategy".equals(type) && isLeaf.booleanValue()) {
                    errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5355\u4f4d\uff1a").append(orgDO.getCode()).append("|").append(orgDO.getName()).append("\u4e0d\u662f\u5408\u5e76\u8282\u70b9\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                    continue;
                }
            } else {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5355\u4f4d\uff1a").append(orgCode).append("\u5728\u6700\u65b0\u5355\u4f4d\u7248\u672c\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new NrFormulaSchemeConfigTableVO(orgDO, null, null, null);
            formulaSchemeConfigTableVO.setTaskId(taskId);
            formulaSchemeConfigTableVO.setSchemeId(schemeId);
            formulaSchemeConfigTableVO.setEntityId(entityId);
            if ("batchStrategy".equals(type)) {
                Map reportTypes = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString(currentRowByTitle.get("reportTypes")), Map.class);
                String bblxTitle = rowDataGroupBuCode.get("bblx");
                if (StringUtils.isEmpty((String)bblxTitle)) {
                    errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u62a5\u8868\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                    continue;
                }
                if (StringUtils.isEmpty((String)((String)reportTypes.get(bblxTitle)))) {
                    errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u62a5\u8868\u7c7b\u578b\uff1a").append(bblxTitle).append("\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                    continue;
                }
                formulaSchemeConfigTableVO.setBblx((String)reportTypes.get(bblxTitle));
                formulaSchemeConfigTableVO.setAssistDim("-");
                if (isExistCurrency) {
                    String currencyTitle = rowDataGroupBuCode.get("currencyCode");
                    if (StringUtils.isEmpty((String)currencyTitle)) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                        continue;
                    }
                    if (!"\u672c\u4f4d\u5e01".equals(currencyTitle)) {
                        String currencyCode = (String)currencyTitleMap.get(currencyTitle);
                        if (StringUtils.isEmpty((String)currencyCode)) {
                            errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\uff1a").append(currencyTitle).append("\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                            continue;
                        }
                        if ("\u5168\u90e8".equals(currencyTitle)) {
                            currencyCode = "MD_CURRENCY@ALL";
                        } else {
                            List currencies = (List)orgDO.get((Object)"currencyids");
                            if (currencies != null && !"".equals(currencies)) {
                                if (!currencies.contains(currencyCode)) {
                                    errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\uff1a").append(currencyTitle).append("\u4e0d\u5c5e\u4e8e\u5355\u4f4d\uff1a").append(orgDO.getCode()).append("\u7684\u62a5\u8868\u5e01\u79cd\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                                    continue;
                                }
                                currencyCode = "MD_CURRENCY@" + currencyCode;
                            }
                        }
                        formulaSchemeConfigTableVO.setAssistDim(currencyCode);
                    }
                }
            } else if ("batchUnit".equals(type)) {
                formulaSchemeConfigTableVO.setAssistDim("-");
                formulaSchemeConfigTableVO.setBblx("-");
                if (isExistCurrency) {
                    String currencyTitle = rowDataGroupBuCode.get("currencyCode");
                    if (StringUtils.isEmpty((String)currencyTitle)) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                        continue;
                    }
                    String currencyCode = (String)currencyTitleMap.get(currencyTitle);
                    if (StringUtils.isEmpty((String)currencyCode)) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\uff1a").append(currencyTitle).append("\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                        continue;
                    }
                    List currencies = (List)orgDO.get((Object)"currencyids");
                    if (currencies != null && !"".equals(currencies)) {
                        if (!currencies.contains(currencyCode)) {
                            errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5e01\u522b\uff1a").append(currencyTitle).append("\u4e0d\u5c5e\u4e8e\u5355\u4f4d\uff1a").append(orgDO.getCode()).append("\u7684\u62a5\u8868\u5e01\u79cd\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                            continue;
                        }
                        currencyCode = "MD_CURRENCY@" + currencyCode;
                    }
                    formulaSchemeConfigTableVO.setAssistDim(currencyCode);
                }
            }
            String fetchScheme = rowDataGroupBuCode.get("fetchScheme");
            if (!StringUtils.isEmpty((String)fetchScheme) && StringUtils.isEmpty((String)((String)fetchSchemes.get(fetchScheme)))) {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u53d6\u6570\u65b9\u6848\uff1a").append(fetchScheme).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            ArrayList<String> fetchAfterSchemeIds = new ArrayList<String>();
            String fetchAfterSchemeTitles = rowDataGroupBuCode.get("fetchAfterScheme");
            if (!StringUtils.isEmpty((String)fetchAfterSchemeTitles)) {
                List<String> fetchAfterSchemeTitleList = Arrays.asList(fetchAfterSchemeTitles.split(";"));
                for (String title : fetchAfterSchemeTitleList) {
                    if (StringUtils.isEmpty((String)((String)formulaSchemes.get(title)))) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u53d6\u6570\u540e\u8fd0\u7b97\uff1a").append(title).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165  \r\n");
                        continue;
                    }
                    fetchAfterSchemeIds.add((String)formulaSchemes.get(title));
                }
            }
            ArrayList<String> convertAfterSchemeIds = new ArrayList<String>();
            String convertAfterSchemeTitles = rowDataGroupBuCode.get("convertAfterScheme");
            if (!StringUtils.isEmpty((String)convertAfterSchemeTitles)) {
                List<String> convertAfterSchemeTitleList = Arrays.asList(convertAfterSchemeTitles.split(";"));
                for (String title : convertAfterSchemeTitleList) {
                    if (StringUtils.isEmpty((String)((String)formulaSchemes.get(title)))) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u6298\u7b97\u540e\u8fd0\u7b97\uff1a").append(title).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165  \r\n");
                        continue;
                    }
                    convertAfterSchemeIds.add((String)formulaSchemes.get(title));
                }
            }
            if (!StringUtils.isEmpty((String)(convertSystemSchemeTitle = rowDataGroupBuCode.get("convertSystemScheme"))) && StringUtils.isEmpty((String)((String)convertSystemSchemes.get(convertSystemSchemeTitle)))) {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5141\u8bb8\u5916\u5e01\u6298\u7b97\uff1a").append(convertSystemSchemeTitle).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            String postingSchemeTitle = rowDataGroupBuCode.get("postingScheme");
            if (!StringUtils.isEmpty((String)postingSchemeTitle) && StringUtils.isEmpty((String)((String)formulaSchemes.get(postingSchemeTitle)))) {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u8fc7\u8d26\uff1a").append(postingSchemeTitle).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            String completeMergeTitles = rowDataGroupBuCode.get("completeMerge");
            ArrayList<String> completeMergeIds = new ArrayList<String>();
            if (!StringUtils.isEmpty((String)completeMergeTitles)) {
                List<String> completeMergeTitleList = Arrays.asList(completeMergeTitles.split(";"));
                for (String title : completeMergeTitleList) {
                    if (StringUtils.isEmpty((String)((String)formulaSchemes.get(title)))) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5b8c\u6210\u5408\u5e76\uff1a").append(title).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                        continue;
                    }
                    completeMergeIds.add((String)formulaSchemes.get(title));
                }
            }
            ArrayList<String> unSaCtDeExtLaYeNumSaPerIds = new ArrayList<String>();
            String unSaCtDeExtLaYeNumSaPerTitles = rowDataGroupBuCode.get("unSaCtDeExtLaYeNumSaPer");
            if (!StringUtils.isEmpty((String)unSaCtDeExtLaYeNumSaPerTitles)) {
                List<String> unSaCtDeExtLaYeNumSaPerTitleList = Arrays.asList(unSaCtDeExtLaYeNumSaPerTitles.split(";"));
                for (String title : unSaCtDeExtLaYeNumSaPerTitleList) {
                    if (StringUtils.isEmpty((String)((String)formulaSchemes.get(title)))) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570\uff1a").append(title).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165  \r\n");
                        continue;
                    }
                    unSaCtDeExtLaYeNumSaPerIds.add((String)formulaSchemes.get(title));
                }
            }
            ArrayList<String> sameCtrlExtAfterSchemeIds = new ArrayList<String>();
            String sameCtrlExtAfterSchemeTitles = rowDataGroupBuCode.get("sameCtrlExtAfterScheme");
            if (!StringUtils.isEmpty((String)sameCtrlExtAfterSchemeTitles)) {
                List<String> sameCtrlExtAfterSchemeTitleList = Arrays.asList(sameCtrlExtAfterSchemeTitles.split(";"));
                for (String title : sameCtrlExtAfterSchemeTitleList) {
                    if (StringUtils.isEmpty((String)((String)formulaSchemes.get(title)))) {
                        errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u540c\u63a7\u3001\u975e\u540c\u63a7\u63d0\u53d6\u540e\u8fd0\u7b97ID\uff1a").append(title).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u62a5\u8868\u65b9\u6848\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165  \r\n");
                        continue;
                    }
                    sameCtrlExtAfterSchemeIds.add((String)formulaSchemes.get(title));
                }
            }
            formulaSchemeConfigTableVO.setFetchSchemeId((String)fetchSchemes.get(fetchScheme));
            formulaSchemeConfigTableVO.setFetchAfterSchemeId(fetchAfterSchemeIds);
            formulaSchemeConfigTableVO.setConvertAfterSchemeId(convertAfterSchemeIds);
            formulaSchemeConfigTableVO.setConvertSystemSchemeId((String)convertSystemSchemes.get(convertSystemSchemeTitle));
            formulaSchemeConfigTableVO.setPostingSchemeId((String)formulaSchemes.get(postingSchemeTitle));
            formulaSchemeConfigTableVO.setCompleteMergeId(completeMergeIds);
            formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPerId(unSaCtDeExtLaYeNumSaPerIds);
            formulaSchemeConfigTableVO.setSameCtrlExtAfterSchemeId(sameCtrlExtAfterSchemeIds);
            formulaSchemeConfigTableVO.setIndex(i + 1);
            formulaSchemeConfigTableVOList.add(formulaSchemeConfigTableVO);
        }
        return formulaSchemeConfigTableVOList;
    }

    private Map<String, Map<String, Object>> getCurrentRowByTitle(Map<String, Object> formulaSchemeData) {
        HashMap<String, Map<String, Object>> formulaTitleMap = new HashMap<String, Map<String, Object>>();
        Map formulaSchemeMap = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeData.get("formulaSchemes")), Map.class);
        Map<String, Object> fetchSchemes = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formulaSchemeMap.get("fetchSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){})).stream().collect(Collectors.toMap(SelectOptionVO::getLabel, SelectOptionVO::getValue));
        Map<String, Object> formulaSchemes = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formulaSchemeMap.get("formulaSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){})).stream().collect(Collectors.toMap(SelectOptionVO::getLabel, SelectOptionVO::getValue));
        Map<String, Object> reportTypes = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formulaSchemeMap.get("reportTypes")), (TypeReference)new TypeReference<List<BaseDataVO>>(){})).stream().collect(Collectors.toMap(BaseDataVO::getTitle, BaseDataVO::getCode));
        Map<String, Object> convertSystemSchemes = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formulaSchemeMap.get("convertSystemSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){})).stream().collect(Collectors.toMap(SelectOptionVO::getLabel, SelectOptionVO::getValue));
        Map iEntityRows = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeData.get("iEntityRows")), Map.class);
        Map<String, Object> currencyTitleMap = new HashMap<String, String>();
        if (iEntityRows.get("MD_CURRENCY") != null) {
            currencyTitleMap = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(iEntityRows.get("MD_CURRENCY")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){})).stream().collect(Collectors.toMap(SelectOptionVO::getLabel, SelectOptionVO::getValue, (value1, value2) -> value2));
        }
        currencyTitleMap.put("\u5168\u90e8", "MD_CURRENCY@ALL");
        formulaTitleMap.put("fetchSchemes", fetchSchemes);
        formulaTitleMap.put("formulaSchemes", formulaSchemes);
        formulaTitleMap.put("reportTypes", reportTypes);
        formulaTitleMap.put("convertSystemSchemes", convertSystemSchemes);
        formulaTitleMap.put("currencyTitle", currencyTitleMap);
        return formulaTitleMap;
    }
}

