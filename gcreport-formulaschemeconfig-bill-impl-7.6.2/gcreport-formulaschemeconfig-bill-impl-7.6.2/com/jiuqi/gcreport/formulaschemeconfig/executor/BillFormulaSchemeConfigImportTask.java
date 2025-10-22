/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  com.jiuqi.nr.mapping2.web.vo.SelectOptionVO
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.BillFormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillFormulaSchemeConfigImportTask {
    @Autowired
    private BillExtractSettingClient settingClient;
    @Autowired
    private BillFormulaSchemeConfigService billFormulaSchemeConfigService;

    public List<BillFormulaSchemeConfigTableVO> formulaSchemeConfigImport(Map<String, Object> params, List<Object[]> excelSheetDataList, String type, StringBuilder errorLog) {
        List<BillFormulaSchemeConfigTableVO> formulaContentRowList = null;
        try {
            formulaContentRowList = this.parseExcelContentRow(params, errorLog, type, excelSheetDataList);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6821\u9a8c\u65f6\u53d1\u751f\u5f02\u5e38\uff01", (Throwable)e);
        }
        return formulaContentRowList;
    }

    private List<BillFormulaSchemeConfigTableVO> parseExcelContentRow(Map<String, Object> params, StringBuilder errorLog, String type, List<Object[]> excelSheetDataList) {
        String billId = (String)params.get("billId");
        List<String> titleCodes = BillFormulaSchemeConfigUtils.listTitleCode();
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
        return this.listFormulaSchemeConfigTableVO(type, errorLog, rowDataGroupByCodes, billId);
    }

    private List<BillFormulaSchemeConfigTableVO> listFormulaSchemeConfigTableVO(String type, StringBuilder errorLog, List<Map<String, String>> rowDataGroupByCodes, String billId) {
        ArrayList<BillFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOList = new ArrayList<BillFormulaSchemeConfigTableVO>();
        Map<String, Object> currentRowByTitle = this.getCurrentRowByTitle(billId);
        Map fetchSchemes = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)currentRowByTitle.get("fetchSchemes")), Map.class);
        for (int i = 0; i < rowDataGroupByCodes.size(); ++i) {
            String masterTableName;
            DataModelColumn column;
            String orgType;
            OrgDO orgDO;
            Map<String, String> rowDataGroupBuCode = rowDataGroupByCodes.get(i);
            String billTitle = rowDataGroupBuCode.get("billId");
            if (!currentRowByTitle.get(billId).equals(billTitle)) {
                errorLog.append("\u7b2c").append(i + 2).append("\u884c\u6570\u636e\u5355\u636e\uff1a").append(billTitle).append("\u4e0e\u6240\u9009\u5355\u636e\u4e0d\u7b26\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            String orgCode = rowDataGroupBuCode.get("orgId");
            if (orgCode.indexOf("|") > -1) {
                orgCode = orgCode.split("\\|")[0].trim();
            }
            if ((orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)(orgType = BillExtractUtil.queryOrgTypeByColumn((DataModelColumn)(column = (DataModelColumn)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getDataModelColumn(masterTableName = (String)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.settingClient.getMasterTableName(billId)), "UNITCODE"))))), (String)orgCode).get(0)) != null) {
                Boolean isLeaf = (Boolean)orgDO.get((Object)"isLeaf");
                if ("batchStrategy".equals(type) && isLeaf.booleanValue()) {
                    errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5355\u4f4d\uff1a").append(orgDO.getCode()).append("|").append(orgDO.getName()).append("\u4e0d\u662f\u5408\u5e76\u8282\u70b9\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                    continue;
                }
            } else {
                errorLog.append("\u7b2c").append(i).append("\u884c\u6570\u636e\u5355\u4f4d\uff1a").append(orgCode).append("\u5728\u6700\u65b0\u5355\u4f4d\u7248\u672c\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            BillFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new BillFormulaSchemeConfigTableVO(orgDO);
            formulaSchemeConfigTableVO.setBillId(billId);
            String fetchScheme = rowDataGroupBuCode.get("fetchScheme");
            if (!StringUtils.isEmpty((String)fetchScheme) && StringUtils.isEmpty((String)((String)fetchSchemes.get(fetchScheme)))) {
                errorLog.append("\u7b2c").append(i + 2).append("\u884c\u6570\u636e\u53d6\u6570\u65b9\u6848\uff1a").append(fetchScheme).append("\u4e0d\u5c5e\u4e8e\u5f53\u524d\u5355\u636e\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165 \r\n");
                continue;
            }
            formulaSchemeConfigTableVO.setFetchSchemeId((String)fetchSchemes.get(fetchScheme));
            formulaSchemeConfigTableVO.setIndex(i + 1);
            formulaSchemeConfigTableVOList.add(formulaSchemeConfigTableVO);
        }
        return formulaSchemeConfigTableVOList;
    }

    private Map<String, Object> getCurrentRowByTitle(String billId) {
        HashMap<String, Object> formulaTitleMap = new HashMap<String, Object>();
        MetaInfoService metaInfoService = (MetaInfoService)SpringContextUtils.getBean(MetaInfoService.class);
        MetaInfoDTO infoDTO = metaInfoService.getMetaInfoByUniqueCode(billId);
        String billTitle = infoDTO.getTitle();
        if (StringUtils.isEmpty((String)billTitle)) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u5355\u636e\uff01");
        }
        formulaTitleMap.put(billId, billTitle);
        Map<String, Object> formulaSchemeMap = this.billFormulaSchemeConfigService.getFetchSchemesByBillId(billId);
        Map<String, Object> fetchSchemes = ((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeMap.get("fetchSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){})).stream().collect(Collectors.toMap(SelectOptionVO::getLabel, SelectOptionVO::getValue));
        formulaTitleMap.put("fetchSchemes", fetchSchemes);
        return formulaTitleMap;
    }
}

