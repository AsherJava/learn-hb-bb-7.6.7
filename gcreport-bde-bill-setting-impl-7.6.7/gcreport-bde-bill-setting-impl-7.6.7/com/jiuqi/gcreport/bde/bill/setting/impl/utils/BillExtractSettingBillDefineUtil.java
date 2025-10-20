/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.service.IMetaDataService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillField;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillTable;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillExtractSettingBillDefineUtil {
    private static final Logger log = LoggerFactory.getLogger(BillExtractSettingBillDefineUtil.class);

    public static String getMainTableNameByBillUniqueCode(String billUniqueCode) {
        MetaInfoService metaInfoService = (MetaInfoService)ApplicationContextRegister.getBean(MetaInfoService.class);
        IMetaDataService metaDataService = (IMetaDataService)ApplicationContextRegister.getBean(IMetaDataService.class);
        MetaInfoDTO billDTO = metaInfoService.getMetaInfoByUniqueCode(billUniqueCode);
        if (Objects.isNull(billDTO)) {
            log.error("\u6839\u636e\u5355\u636e\u552f\u4e00\u4ee3\u7801{}\u672a\u67e5\u8be2\u5230\u5355\u636e", (Object)billUniqueCode);
            return "";
        }
        UUID billId = billDTO.getId();
        MetaDataDTO dataDTO = metaDataService.getMetaDataById(billId);
        if (dataDTO != null && dataDTO.getDesignData() != null) {
            String billDesignDataJsonStr = dataDTO.getDesignData();
            return BillExtractSettingBillDefineUtil.getBillMainTableName(billDesignDataJsonStr);
        }
        return "";
    }

    private static String getBillMainTableName(String billDesignDataJsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(billDesignDataJsonStr);
            JSONArray plugins = jsonObject.getJSONArray("plugins");
            for (int i = 0; i < plugins.length(); ++i) {
                JSONObject pluginTypeData = plugins.getJSONObject(i);
                String type = pluginTypeData.getString("type");
                if (!"data".equals(type)) continue;
                JSONArray tableDatas = pluginTypeData.getJSONArray("tables");
                Map<String, BillTable> billTableMap = BillExtractSettingBillDefineUtil.buildFieldMap(tableDatas);
                String mainTable = billTableMap.get("MAIN_TABLE").getTableName();
                return mainTable;
            }
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u5355\u636e\u8868\u5355\u5931\u8d25,\u8be6\u7ec6\u539f\u56e0:{}", (Object)e.getMessage(), (Object)e);
            throw new BusinessRuntimeException("\u89e3\u6790\u5355\u636e\u8868\u5355\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return "";
    }

    private static Map<String, BillTable> buildFieldMap(JSONArray tableDatas) {
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
}

