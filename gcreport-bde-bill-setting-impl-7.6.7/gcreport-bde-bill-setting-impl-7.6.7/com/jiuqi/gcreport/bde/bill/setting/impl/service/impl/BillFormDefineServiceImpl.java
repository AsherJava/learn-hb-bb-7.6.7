/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableData
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableExtractField
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractField
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractParagraph
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.service.IMetaDataService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableData;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableExtractField;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractField;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractParagraph;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillField;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillTable;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFormDefineService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillSettingBuildPublishStateService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BillFormDefineServiceImpl
implements BillFormDefineService {
    private static final Logger log = LoggerFactory.getLogger(BillFormDefineServiceImpl.class);
    @Autowired
    private BillExtractSchemeService schemaService;
    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private BillSettingBuildPublishStateService buildPublishStateService;

    @Override
    public BillFormDefine queryBillDefine(String fetchSchemeId) {
        BillFetchSchemeDTO fetchSchemeDTO = this.schemaService.getById(fetchSchemeId);
        BillFormDefine billFormDefine = new BillFormDefine();
        String billUniqueCode = fetchSchemeDTO.getBillType();
        MetaInfoDTO billDTO = this.metaInfoService.getMetaInfoByUniqueCode(billUniqueCode);
        if (Objects.isNull(billDTO)) {
            log.error("\u6839\u636e\u5355\u636e\u552f\u4e00\u4ee3\u7801{}\u672a\u67e5\u8be2\u5230\u5355\u636e", (Object)billUniqueCode);
            return billFormDefine;
        }
        UUID billId = billDTO.getId();
        MetaDataDTO dataDTO = this.metaDataService.getMetaDataById(billId);
        if (dataDTO != null && dataDTO.getDesignData() != null) {
            String billDesignDataJsonStr = dataDTO.getDesignData();
            billFormDefine = this.parseBillDesignDataJsonStr(billDesignDataJsonStr, billUniqueCode);
            if (!CollectionUtils.isEmpty((Collection)billFormDefine.getMainData())) {
                this.buildPublishStateService.setFieldShowTextAndPublishState(billFormDefine, billUniqueCode, fetchSchemeId);
            }
        } else {
            log.error("\u5355\u636e{}\u8bbe\u8ba1\u4e3a\u7a7a", (Object)billUniqueCode);
            return billFormDefine;
        }
        return billFormDefine;
    }

    @Override
    public BillFormDefine queryBillDefineByBillUniqueCode(String billUniqueCode) {
        BillFormDefine billFormDefine = new BillFormDefine();
        MetaInfoDTO billDTO = this.metaInfoService.getMetaInfoByUniqueCode(billUniqueCode);
        if (ObjectUtils.isEmpty(billDTO)) {
            return billFormDefine;
        }
        UUID billId = billDTO.getId();
        MetaDataDTO dataDTO = this.metaDataService.getMetaDataById(billId);
        if (dataDTO != null && dataDTO.getDesignData() != null) {
            String billDesignDataJsonStr = dataDTO.getDesignData();
            billFormDefine = this.parseBillDesignDataJsonStr(billDesignDataJsonStr, billUniqueCode);
        }
        return billFormDefine;
    }

    private BillFormDefine parseBillDesignDataJsonStr(String billDesignDataJsonStr, String billUniqueCode) {
        BillFormDefine formDefine = new BillFormDefine();
        try {
            JSONObject jsonObject = new JSONObject(billDesignDataJsonStr);
            JSONArray plugins = jsonObject.getJSONArray("plugins");
            HashMap<String, BillTable> billTableMap = new HashMap();
            for (int i = 0; i < plugins.length(); ++i) {
                JSONObject pluginTypeData = plugins.getJSONObject(i);
                String type = pluginTypeData.getString("type");
                if ("data".equals(type)) {
                    JSONArray tableDatas = pluginTypeData.getJSONArray("tables");
                    billTableMap = this.buildFieldMap(tableDatas);
                    List<BillChildTableData> childrenTableData = this.getChildrenTableData(billTableMap);
                    formDefine.setChildrenTable(childrenTableData);
                    continue;
                }
                if (!"view".equals(type)) continue;
                JSONObject template = pluginTypeData.getJSONObject("template");
                JSONArray children = template.getJSONArray("children");
                this.buildMainTableData(formDefine, children, billTableMap);
            }
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u5355\u636e\u8868\u5355{}\u5931\u8d25,\u8be6\u7ec6\u539f\u56e0:{}", billUniqueCode, e.getMessage(), e);
            throw new BusinessRuntimeException("\u89e3\u6790\u5355\u636e\u8868\u5355\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return formDefine;
    }

    private Map<String, BillTable> buildFieldMap(JSONArray tableDatas) {
        HashMap<String, BillTable> tableFieldMap = new HashMap<String, BillTable>();
        for (int tableIndex = 0; tableIndex < tableDatas.length(); ++tableIndex) {
            JSONObject tableData = tableDatas.getJSONObject(tableIndex);
            BillTable billTable = new BillTable();
            HashMap<String, BillField> fieldMap = new HashMap<String, BillField>();
            String tableName = tableData.getString("name");
            String title = tableData.getString("title");
            JSONArray fields = tableData.getJSONArray("fields");
            for (int i = 0; i < fields.length(); ++i) {
                JSONObject field = fields.getJSONObject(i);
                BillField billField = new BillField();
                billField.setFieldName(field.getString("fieldName"));
                billField.setTitle(field.getString("title"));
                billField.setFieldType(field.getString("fieldType"));
                billField.setColumnAttr(field.has("columnAttr") ? field.getString("columnAttr") : "NULL");
                fieldMap.put(billField.getFieldName(), billField);
            }
            billTable.setTableName(tableName);
            billTable.setTableTitle(title);
            billTable.setFields(fieldMap);
            if (!tableData.has("parentId")) {
                tableFieldMap.put("MAIN_TABLE", billTable);
                continue;
            }
            tableFieldMap.put(billTable.getTableName(), billTable);
        }
        return tableFieldMap;
    }

    private List<BillChildTableData> getChildrenTableData(Map<String, BillTable> billTableMap) {
        ArrayList<BillChildTableData> childrenTableData = new ArrayList<BillChildTableData>();
        for (String tableName : billTableMap.keySet()) {
            if ("MAIN_TABLE".equals(tableName)) continue;
            BillChildTableData childTable = new BillChildTableData();
            BillTable billTable = billTableMap.get(tableName);
            childTable.setTableName(tableName);
            childTable.setTableTitle(billTable.getTableTitle());
            ArrayList<BillChildTableExtractField> columns = new ArrayList<BillChildTableExtractField>();
            Map<String, BillField> fieldsWithSystemMap = billTable.getFields();
            for (Map.Entry<String, BillField> billFieldEntry : fieldsWithSystemMap.entrySet()) {
                BillField fieldValue = billFieldEntry.getValue();
                if ("SYSTEM".equals(fieldValue.getColumnAttr())) continue;
                BillChildTableExtractField extractField = new BillChildTableExtractField();
                extractField.setColumnName(fieldValue.getFieldName());
                extractField.setColumnTitle(fieldValue.getTitle());
                extractField.setColumnType(fieldValue.getFieldType());
                columns.add(extractField);
            }
            childTable.setColumns(columns);
            childrenTableData.add(childTable);
        }
        return childrenTableData;
    }

    private void buildMainTableData(BillFormDefine formDefine, JSONArray formDefineChildren, Map<String, BillTable> billTableMap) {
        if (formDefineChildren.isEmpty()) {
            return;
        }
        List<JSONObject> mainTableJsonObjectList = this.getMainTableDataChildren(formDefineChildren, billTableMap.get("MAIN_TABLE").getTableName(), formDefine);
        if (mainTableJsonObjectList.isEmpty()) {
            return;
        }
        formDefine.setMainTableName(billTableMap.get("MAIN_TABLE").getTableName());
        formDefine.setMainTableTitle(billTableMap.get("MAIN_TABLE").getTableTitle());
        formDefine.setMainData(new ArrayList());
        for (JSONObject mainTableJsonObjectItem : mainTableJsonObjectList) {
            List<BillExtractParagraph> mainTableParagraph = this.buildMainTableParagraph(mainTableJsonObjectItem, billTableMap.get("MAIN_TABLE"));
            formDefine.getMainData().addAll(mainTableParagraph);
        }
        List mainData = formDefine.getMainData();
        for (int i = 0; i < mainData.size(); ++i) {
            if (!StringUtils.isEmpty((String)((BillExtractParagraph)mainData.get(i)).getParaTitle()) || CollectionUtils.isEmpty((Collection)((BillExtractParagraph)mainData.get(i)).getParaRowData())) continue;
            ((BillExtractParagraph)mainData.get(i)).setParaTitle("\u4e3b\u8868\u533a\u57df" + (i + 1));
        }
    }

    private List<JSONObject> getMainTableDataChildren(JSONArray formDefineChildren, String mainTableName, BillFormDefine formDefine) {
        ArrayList<JSONObject> MainTableDataChildrenList = new ArrayList<JSONObject>();
        for (int i = 0; i < formDefineChildren.length(); ++i) {
            JSONObject tempMainTableChild = formDefineChildren.getJSONObject(i);
            List<JSONObject> mainTableDataChildren = this.getMainTableDataChildren(tempMainTableChild, mainTableName, formDefine);
            if (mainTableDataChildren.isEmpty()) continue;
            MainTableDataChildrenList.addAll(mainTableDataChildren);
        }
        return MainTableDataChildrenList;
    }

    private List<JSONObject> getMainTableDataChildren(JSONObject tempMainTableChild, String mainTableName, BillFormDefine formDefine) {
        String tableName;
        JSONObject binding;
        JSONArray children;
        JSONObject jsonObject;
        ArrayList<JSONObject> allMainTableJsonObject = new ArrayList<JSONObject>();
        if (!tempMainTableChild.has("children") || tempMainTableChild.getJSONArray("children").length() <= 0) {
            return allMainTableJsonObject;
        }
        if (tempMainTableChild.has("layout") && !tempMainTableChild.getJSONObject("layout").isEmpty() && (jsonObject = (children = tempMainTableChild.getJSONArray("children")).getJSONObject(0)).has("binding") && (binding = jsonObject.getJSONObject("binding")).has("tableName") && mainTableName.equals(tableName = binding.getString("tableName"))) {
            allMainTableJsonObject.add(tempMainTableChild);
            return allMainTableJsonObject;
        }
        children = tempMainTableChild.getJSONArray("children");
        List<JSONObject> mainTableDataChildrenList = this.getMainTableDataChildren(children, mainTableName, formDefine);
        allMainTableJsonObject.addAll(mainTableDataChildrenList);
        return allMainTableJsonObject;
    }

    private List<BillExtractParagraph> buildMainTableParagraph(JSONObject mianTableObject, BillTable mainTableMap) {
        Map<String, BillField> mainTableFieldMap = mainTableMap.getFields();
        JSONObject layout = mianTableObject.getJSONObject("layout");
        int curRegionRowMaxLength = this.computeRowCount(mianTableObject);
        JSONArray mianTableColumnChildRen = mianTableObject.getJSONArray("children");
        ArrayList<BillExtractParagraph> paragraphs = new ArrayList<BillExtractParagraph>();
        BillExtractParagraph paragraph = new BillExtractParagraph();
        paragraphs.add(paragraph);
        ArrayList paraRowData = new ArrayList();
        ArrayList row0 = new ArrayList();
        paraRowData.add(row0);
        paragraph.setParaRowData(paraRowData);
        int paraNum = 0;
        int rowNum = 0;
        ArrayList<String> includedMainTableFieldList = new ArrayList<String>();
        for (int i = 0; i < mianTableColumnChildRen.length(); ++i) {
            BillExtractParagraph currParagraph = (BillExtractParagraph)paragraphs.get(paraNum);
            List currParaRows = currParagraph.getParaRowData();
            List curRowFields = (List)currParaRows.get(rowNum);
            JSONObject jsonObject = mianTableColumnChildRen.getJSONObject(i);
            String type = jsonObject.getString("type");
            if ("v-panel".equals(type)) {
                String title = jsonObject.getString("title");
                if (StringUtils.isEmpty((String)currParagraph.getParaTitle()) && CollectionUtils.isEmpty((Collection)currParagraph.getParaRowData())) {
                    currParagraph.setParaTitle(title);
                    continue;
                }
                ++paraNum;
                rowNum = 0;
                BillExtractParagraph paragraphItem = new BillExtractParagraph();
                paragraphs.add(paragraphItem);
                ArrayList itemParaRowData = new ArrayList();
                paragraphItem.setParaRowData(itemParaRowData);
                ArrayList itemParaRow0 = new ArrayList();
                itemParaRowData.add(itemParaRow0);
                currParagraph = (BillExtractParagraph)paragraphs.get(paraNum);
                currParagraph.setParaTitle(title);
                continue;
            }
            JSONObject binding = jsonObject.getJSONObject("binding");
            String fieldName = binding.getString("fieldName");
            if (!mainTableFieldMap.containsKey(fieldName) || includedMainTableFieldList.contains(fieldName)) continue;
            includedMainTableFieldList.add(fieldName);
            BillExtractField field = new BillExtractField();
            field.setColumnName(fieldName);
            field.setColumnTitle(mainTableFieldMap.get(fieldName).getTitle());
            field.setColumnType(mainTableFieldMap.get(fieldName).getFieldType());
            if (curRowFields.size() < curRegionRowMaxLength) {
                curRowFields.add(field);
                continue;
            }
            ArrayList newRowFields = new ArrayList();
            currParaRows.add(newRowFields);
            curRowFields = (List)currParaRows.get(++rowNum);
            curRowFields.add(field);
        }
        return paragraphs;
    }

    private int computeRowCount(JSONObject mianTableObject) {
        JSONObject layout = mianTableObject.getJSONObject("layout");
        int layoutNum = 1;
        if (layout.has("columns")) {
            JSONObject layoutColumns;
            Object columnsObj = layout.get("columns");
            if (columnsObj instanceof JSONObject) {
                layoutColumns = (JSONObject)columnsObj;
                JSONArray columnsItems = layoutColumns.getJSONArray("items");
                layoutNum = columnsItems.length();
            } else if (columnsObj instanceof JSONArray) {
                layoutColumns = (JSONArray)columnsObj;
                layoutNum = layoutColumns.length();
            } else {
                layoutNum = 3;
                log.error("\u89e3\u6790\u5355\u636e\u8868\u6837\u62a5\u6587\u5931\u8d25\uff0c\u89e3\u6790\u5217\u5206\u5e03\u5931\u8d25:{}", (Object)columnsObj.toString());
                return layoutNum;
            }
        }
        JSONArray mianTableColumnChildRen = mianTableObject.getJSONArray("children");
        int titleNum = 0;
        for (int i = 0; i < mianTableColumnChildRen.length(); ++i) {
            JSONObject jsonObject = mianTableColumnChildRen.getJSONObject(i);
            String type = jsonObject.getString("type");
            if (!Objects.equals(type, "v-label")) continue;
            ++titleNum;
        }
        boolean isLabelType = this.billMainTableApproximatelyHalf(titleNum, mianTableColumnChildRen.length());
        if (titleNum == 0) {
            return layoutNum;
        }
        if (isLabelType) {
            return layoutNum / 2;
        }
        return 3;
    }

    private boolean billMainTableApproximatelyHalf(int titleNum, int totalNum) {
        return this.isApproximatelyHalf(titleNum, totalNum, 2);
    }

    private boolean isApproximatelyHalf(int a, int b, int tolerance) {
        long doubleA = 2L * (long)a;
        long diff = Math.abs(doubleA - (long)b);
        return diff <= (long)tolerance;
    }
}

