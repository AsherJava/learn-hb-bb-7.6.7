/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.va.bizmeta.controller.MetaDataController
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.va.bizmeta.controller.MetaDataController;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.OperateType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FairValueBillExportTask {
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private MetaDataController metaDataController;
    @Autowired
    private InvestBillDao investBillDao;
    private final int START_COL = 3;

    protected ExportExcelSheet exportExcelSheet(ExportContext context) {
        Map<String, String> columnsMap = this.getColumnsMap();
        ExportExcelSheet sheet = this.exportHead(columnsMap, context);
        if (context.isTemplateExportFlag()) {
            return sheet;
        }
        this.exportData(columnsMap, context, sheet);
        return sheet;
    }

    private Map<String, String> getColumnsMap() {
        JSONObject columnsJsonOjbect = this.getFairValueMetaData();
        HashMap<String, String> columnsMap = new HashMap<String, String>();
        this.getAllKey(columnsJsonOjbect, columnsMap);
        return columnsMap;
    }

    public int getFixedAssertsColumunsSize() {
        Map<String, String> columnsMap = this.getColumnsMap();
        return ((JSONArray)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)columnsMap.get("GC_FVCH_FIXEDITEM")), JSONArray.class)).length();
    }

    private void exportData(Map<String, String> columnsMap, ExportContext context, ExportExcelSheet sheet) {
        List fixedAssetsList = (List)JsonUtils.readValue((String)columnsMap.get("GC_FVCH_FIXEDITEM"), List.class);
        int fixedAssetsSize = fixedAssetsList.size();
        if (null != columnsMap.get("GC_FVCH_OTHERITEM")) {
            List otherAssetsList = (List)JsonUtils.readValue((String)columnsMap.get("GC_FVCH_OTHERITEM"), List.class);
            fixedAssetsList.addAll(otherAssetsList);
        }
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        HashMap<String, List<Map<String, Object>>> fixedAssetsRecordsGroup = new HashMap<String, List<Map<String, Object>>>();
        HashMap<String, List<Map<String, Object>>> otherAssetsRecordsGroup = new HashMap<String, List<Map<String, Object>>>();
        this.getFixedAndOtherAssertsRecordsMap(fixedAssetsRecordsGroup, otherAssetsRecordsGroup, params);
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)"GC_FVCH_FIXEDITEM").stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        List<Map<String, Object>> records = this.investBillDao.listInvestBillsByPaging(params);
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        Set srcIdSet = records.stream().map(record -> record.get("SRCID")).collect(Collectors.toSet());
        List fvchMasteList = InvestBillTool.listByWhere((String[])new String[]{"SRCID"}, (Object[])new Object[]{srcIdSet}, (String)"GC_FVCHBILL");
        Map<String, String> fvchSrcId2BillCodeMap = fvchMasteList.stream().collect(Collectors.toMap(item -> (String)item.get("SRCID"), item -> (String)item.get("BILLCODE")));
        int rowIndex = 1;
        int rowMergeStart = 2;
        for (Map<String, Object> record2 : records) {
            String unitAndInvestUnitCode = ((String)record2.get("UNITCODE")).concat("_").concat((String)record2.get("INVESTEDUNIT"));
            String fvchBillCode = fvchSrcId2BillCodeMap.get(record2.get("SRCID"));
            if (!StringUtils.isEmpty((String)fvchBillCode)) {
                unitAndInvestUnitCode = unitAndInvestUnitCode.concat("_").concat(fvchBillCode);
            }
            List fixedAssetsRecords = fixedAssetsRecordsGroup.get(unitAndInvestUnitCode) == null ? new ArrayList() : (List)fixedAssetsRecordsGroup.get(unitAndInvestUnitCode);
            InvestBillTool.formatBillContent(fixedAssetsRecords, (Map)params, (String)"GC_FVCH_FIXEDITEM", (boolean)false);
            List otherAssetsRecords = otherAssetsRecordsGroup.get(unitAndInvestUnitCode) == null ? new ArrayList() : (List)otherAssetsRecordsGroup.get(unitAndInvestUnitCode);
            InvestBillTool.formatBillContent(otherAssetsRecords, (Map)params, (String)"GC_FVCH_OTHERITEM", (boolean)false);
            int recordsSize = fixedAssetsRecords.size() > otherAssetsRecords.size() ? fixedAssetsRecords.size() : otherAssetsRecords.size();
            for (int i = 0; i < recordsSize; ++i) {
                Map fixedAssertsRecord = i >= fixedAssetsRecords.size() ? null : (Map)fixedAssetsRecords.get(i);
                Map otherAssertsRecord = i >= otherAssetsRecords.size() ? null : (Map)otherAssetsRecords.get(i);
                Object[] values = new Object[fixedAssetsList.size() + 3];
                block7: for (int j = 0; j < values.length; ++j) {
                    String columnCode;
                    switch (j) {
                        case 0: {
                            values[j] = String.valueOf(rowIndex);
                            continue block7;
                        }
                        case 1: {
                            columnCode = "UNITCODE";
                            break;
                        }
                        case 2: {
                            columnCode = "INVESTEDUNIT";
                            break;
                        }
                        default: {
                            Map columnField = (Map)fixedAssetsList.get(j - 3);
                            columnCode = columnField.get("name").toString();
                        }
                    }
                    values[j] = j >= 3 + fixedAssetsSize ? (otherAssertsRecord == null ? "" : otherAssertsRecord.get(columnCode)) : (fixedAssertsRecord == null ? "" : fixedAssertsRecord.get(columnCode));
                    ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
                    if (values[j] == null || "".equals(String.valueOf(values[j])) || define == null || define.getColumnType() != ColumnModelType.DOUBLE && define.getColumnType() != ColumnModelType.INTEGER && define.getColumnType() != ColumnModelType.BIGDECIMAL) continue;
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    values[j] = df.format(values[j]);
                }
                sheet.getRowDatas().add(values);
            }
            if (recordsSize > 1) {
                for (int j = 0; j < 3; ++j) {
                    CellRangeAddress region = new CellRangeAddress(rowMergeStart, rowMergeStart + recordsSize - 1, j, j);
                    sheet.getCellRangeAddresses().add(region);
                }
            }
            rowMergeStart += recordsSize;
            ++rowIndex;
        }
    }

    private void getFixedAndOtherAssertsRecordsMap(Map<String, List<Map<String, Object>>> fixedAssetsRecordsGroup, Map<String, List<Map<String, Object>>> otherAssetsRecordsGroup, Map<String, Object> params) {
        List<Map<String, Object>> fixedAssetsRecords = this.fairValueBillDao.queryFvchFixedItemBills(params);
        this.getAssertsRecordsMap(fixedAssetsRecords, fixedAssetsRecordsGroup);
        List<Map<String, Object>> otherAssetsRecords = this.fairValueBillDao.queryFvchOtherItemBills(params);
        this.getAssertsRecordsMap(otherAssetsRecords, otherAssetsRecordsGroup);
    }

    private void getAssertsRecordsMap(List<Map<String, Object>> assetsRecords, Map<String, List<Map<String, Object>>> assetsRecordsGroup) {
        for (Map<String, Object> record : assetsRecords) {
            String unitCode = record.get("UNITCODE") == null ? "" : (String)record.get("UNITCODE");
            String investedUnitCode = record.get("INVESTEDUNIT") == null ? "" : (String)record.get("INVESTEDUNIT");
            String unitAndInvestUnitCode = unitCode.concat("_").concat(investedUnitCode).concat("_").concat((String)record.get("BILLCODE"));
            if (!assetsRecordsGroup.containsKey(unitAndInvestUnitCode)) {
                assetsRecordsGroup.put(unitAndInvestUnitCode, new ArrayList());
            }
            assetsRecordsGroup.get(unitAndInvestUnitCode).add(record);
        }
    }

    private ExportExcelSheet exportHead(Map<String, String> columnsMap, ExportContext context) {
        List fixedAssetsList = (List)JsonUtils.readValue((String)columnsMap.get("GC_FVCH_FIXEDITEM"), List.class);
        int fixedAssetsColumnsSize = fixedAssetsList.size();
        if (null != columnsMap.get("GC_FVCH_OTHERITEM")) {
            List otherAssetsList = (List)JsonUtils.readValue((String)columnsMap.get("GC_FVCH_OTHERITEM"), List.class);
            fixedAssetsList.addAll(otherAssetsList);
        }
        String[] titles1 = new String[fixedAssetsList.size() + 3];
        String[] titles2 = new String[fixedAssetsList.size() + 3];
        titles2[0] = "\u5e8f\u53f7";
        titles1[0] = "\u5e8f\u53f7";
        titles2[1] = "\u6295\u8d44\u5355\u4f4d";
        titles1[1] = "\u6295\u8d44\u5355\u4f4d";
        titles2[2] = "\u88ab\u6295\u8d44\u5355\u4f4d";
        titles1[2] = "\u88ab\u6295\u8d44\u5355\u4f4d";
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(2), "\u516c\u5141\u4ef7\u503c\u8c03\u6574", Integer.valueOf(2));
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)"GC_FVCH_FIXEDITEM").stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        for (int i = 0; i < fixedAssetsList.size(); ++i) {
            Map columnField = (Map)fixedAssetsList.get(i);
            String columnCode = columnField.get("name").toString();
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
            titles1[i + 3] = i < fixedAssetsColumnsSize ? "\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7" : "\u5176\u4ed6\u8d44\u4ea7";
            titles2[i + 3] = columnField.get("title").toString();
            if (define == null || define.getColumnType() != ColumnModelType.DOUBLE && define.getColumnType() != ColumnModelType.INTEGER && define.getColumnType() != ColumnModelType.BIGDECIMAL) continue;
            sheet.getContentCellStyleCache().put(i + 3, contentAmt);
            sheet.getHeadCellStyleCache().put(i + 3, headAmt);
        }
        sheet.getRowDatas().add(titles1);
        sheet.getRowDatas().add(titles2);
        return sheet;
    }

    private JSONObject getFairValueMetaData() {
        MetaDesignDTO metaDesignDTO = new MetaDesignDTO();
        metaDesignDTO.setDefineCode("GCBILL_B_FVCHBILL");
        metaDesignDTO.setOperateType(OperateType.EXECUTE);
        metaDesignDTO.setTenantName("");
        metaDesignDTO.setDefineVersion(null);
        R r = this.metaDataController.getMetaDesign(metaDesignDTO);
        return (JSONObject)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)r.get((Object)"data")), JSONObject.class);
    }

    private void getAllKey(JSONObject jsonObject, Map<String, String> columnsMap) {
        for (String objectkey : jsonObject.keySet()) {
            if (jsonObject.get(objectkey) == null || "null".equals(jsonObject.get(objectkey).toString()) || StringUtils.isEmpty((String)jsonObject.get(objectkey).toString()) || !this.isJsonObject(jsonObject.get(objectkey).toString()).booleanValue()) continue;
            String jsonStr = jsonObject.get(objectkey).toString();
            Object json = new JSONTokener(jsonStr).nextValue();
            if (json instanceof JSONObject) {
                JSONObject innerObject = (JSONObject)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)jsonStr), JSONObject.class);
                if (innerObject == null) continue;
                if ("binding".equals(objectkey)) {
                    columnsMap.put(innerObject.get("tableName").toString(), innerObject.get("fields").toString());
                    continue;
                }
                this.getAllKey(innerObject, columnsMap);
                continue;
            }
            if (!(json instanceof JSONArray)) continue;
            JSONArray jsonArray = (JSONArray)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)jsonStr), JSONArray.class);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject arrayJSONObject;
                if (!(jsonArray.get(i) instanceof JSONObject) || (arrayJSONObject = jsonArray.getJSONObject(i)) == null) continue;
                this.getAllKey(arrayJSONObject, columnsMap);
            }
        }
    }

    private Boolean isJsonObject(String jsonString) {
        try {
            JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)jsonString), JSONObject.class);
        }
        catch (Exception ex) {
            try {
                JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)jsonString), JSONArray.class);
            }
            catch (Exception ex1) {
                return false;
            }
        }
        return true;
    }
}

