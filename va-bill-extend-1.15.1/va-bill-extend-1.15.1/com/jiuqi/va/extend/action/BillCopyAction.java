/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.plugin.AttachmentPluginDefineImpl
 *  com.jiuqi.va.biz.domain.CheckFieldResult
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.extend.action;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.plugin.AttachmentPluginDefineImpl;
import com.jiuqi.va.biz.domain.CheckFieldResult;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.extend.domain.BillReuseField;
import com.jiuqi.va.extend.plugin.reuse.ReuseFieldPluginDefineImpl;
import com.jiuqi.va.extend.service.BillCopyActionExtend;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component(value="vaBillCopyAction")
@ConditionalOnMissingClass(value={"com.jiuqi.cfas.fo.bill.action.FoBillCopyAction"})
public class BillCopyAction
extends BillActionBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillCopyAction.class);
    private static final List<String> notCopyTables = new ArrayList<String>();
    private static final List<String> notCopyModels = new ArrayList<String>();
    private static final String MD_ORG = "MD_ORG_";
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;

    @Autowired(required=false)
    private void setCopyActionExtends(List<BillCopyActionExtend> copyActionExtends) {
        if (copyActionExtends == null) {
            return;
        }
        for (BillCopyActionExtend copyActionExtend : copyActionExtends) {
            notCopyTables.addAll(copyActionExtend.notCopyTables());
        }
        for (BillCopyActionExtend copyActionExtend : copyActionExtends) {
            notCopyModels.addAll(copyActionExtend.notCopyModels());
        }
    }

    public String getName() {
        return "bill-copy";
    }

    public String getTitle() {
        return "\u590d\u5236";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_fuzhi";
    }

    public String getActionPriority() {
        return "024";
    }

    public Object executeReturn(BillModel model, Map<String, Object> actionParam) {
        long l = System.currentTimeMillis();
        R r = this.copyBill(model);
        LOGGER.info("\u590d\u5236\u8017\u65f6\uff1a{}", (Object)(System.currentTimeMillis() - l));
        return r;
    }

    private R copyBill(BillModel model) {
        Map tempDisplayedFieldList;
        ReuseFieldPluginDefineImpl defineImpl = null;
        Plugin reuseFieldComponent = (Plugin)model.getPlugins().find("reuseFieldComponent");
        if (reuseFieldComponent == null) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.please.config.reusePlugin"));
        }
        if (notCopyModels.contains(model.getDefine().getModelType())) {
            return R.error((String)BillExtend18nUtil.getMessage("va.billextend.not.copy"));
        }
        defineImpl = (ReuseFieldPluginDefineImpl)reuseFieldComponent.getDefine();
        HashMap<String, Map<String, Object>> params = new HashMap<String, Map<String, Object>>();
        if (defineImpl != null && !CollectionUtils.isEmpty(defineImpl.getReUseFields())) {
            int size = defineImpl.getReUseFields().size();
            for (int i = 0; i < size; ++i) {
                params.put(String.valueOf(i), defineImpl.getReUseFields().get(i).getMap());
            }
        }
        ViewDefineImpl view = (ViewDefineImpl)((Plugin)model.getPlugins().get("view")).getDefine();
        String schemeCode = model.getContext().getSchemeCode();
        if (!StringUtils.hasText(schemeCode)) {
            schemeCode = view.getDefaultSchemeCode();
        }
        if (CollectionUtils.isEmpty(tempDisplayedFieldList = (Map)view.getSchemeFields().get(schemeCode))) {
            LOGGER.info("\u754c\u9762\u65e0bind\u5b57\u6bb5");
            return R.ok();
        }
        DataTableNodeContainer tables = model.getData().getTables();
        Map<String, List<Map<String, Object>>> tableData = this.getCopiedDataFromOldBill(model, params);
        model.add();
        tables.forEach((index, table) -> {
            if (table.getDefine().getParentId() == null) {
                return;
            }
            int deleteRows = table.getRows().size();
            for (int i = deleteRows - 1; i >= 0; --i) {
                table.deleteRow(i);
            }
        });
        HashMap<String, List<? extends DataField>> mapOrg = new HashMap<String, List<? extends DataField>>();
        tables.forEach((index, table) -> {
            String newTableName = table.getName();
            if (notCopyTables.contains(newTableName)) {
                return;
            }
            List orgFields = table.getFields().stream().filter(item -> item.getDefine().getRefTableName() != null && item.getDefine().getRefTableName().startsWith(MD_ORG)).collect(Collectors.toList());
            mapOrg.put(newTableName, orgFields.isEmpty() ? null : orgFields);
        });
        BillCopyAction.handleParams(model, params, mapOrg, tableData, (DataTableNodeContainer<? extends DataTable>)tables, tempDisplayedFieldList);
        ReuseFieldPluginDefineImpl finalDefineImpl = defineImpl;
        this.deleteRows(model, finalDefineImpl);
        return this.executeAttach(model, schemeCode, defineImpl, tableData);
    }

    private R executeAttach(BillModel model, String schemeCode, ReuseFieldPluginDefineImpl defineImpl, Map<String, List<Map<String, Object>>> tableData) {
        if (!StringUtils.hasText(schemeCode)) {
            return null;
        }
        AttachmentPluginDefineImpl attachment = (AttachmentPluginDefineImpl)((Plugin)model.getPlugins().get("attachment")).getDefine();
        Map quoteCodes = (Map)attachment.getQuoteCodes().get(schemeCode);
        if (quoteCodes == null) {
            quoteCodes = (Map)attachment.getQuoteCodes().get("defautScheme");
        }
        if (quoteCodes == null) {
            return null;
        }
        if (defineImpl == null || !Boolean.TRUE.equals(defineImpl.getCopyAttachment())) {
            BillCopyAction.removeQuotecode(model, quoteCodes);
        } else {
            Map<String, String> oldQuoteCode = BillCopyAction.getQuotecodes(quoteCodes, defineImpl, tableData);
            ArrayList<AttachmentBizDO> attParams = new ArrayList<AttachmentBizDO>();
            for (String s : oldQuoteCode.keySet()) {
                AttachmentBizDO attachmentBizDO = new AttachmentBizDO();
                attachmentBizDO.setQuotecode(s);
                attParams.add(attachmentBizDO);
            }
            if (attParams.isEmpty()) {
                return null;
            }
            R r = this.vaAttachmentFeignClient.copyFiles(attParams);
            if (r.getCode() == 0) {
                r.put("oldQuoteCode", oldQuoteCode);
                return r;
            }
            LOGGER.error(r.getMsg());
        }
        return null;
    }

    private void deleteRows(BillModel model, ReuseFieldPluginDefineImpl finalDefineImpl) {
        if (finalDefineImpl != null) {
            model.getData().getTables().forEach((index, table) -> {
                if (table.getDefine().getParentId() == null) {
                    return;
                }
                ArrayList deleteRows = new ArrayList();
                table.getRows().forEach((indexRo, row) -> {
                    if (this.rowIsNull(finalDefineImpl.getReUseFields(), (DataRow)row, table.getDefine().getName())) {
                        deleteRows.add(indexRo);
                    }
                });
                for (int i = deleteRows.size() - 1; i >= 0; --i) {
                    if (table.getDefine().getName().endsWith("_M")) continue;
                    table.deleteRow(((Integer)deleteRows.get(i)).intValue());
                }
            });
        }
    }

    private static Map<String, String> getQuotecodes(Map<String, Set<String>> quoteCodes, ReuseFieldPluginDefineImpl defineImpl, Map<String, List<Map<String, Object>>> tableData) {
        ArrayList<String> quoteCodesList = new ArrayList<String>();
        for (String s : quoteCodes.keySet()) {
            Set<String> strings = quoteCodes.get(s);
            for (String string : strings) {
                quoteCodesList.add(s + ":" + string);
            }
        }
        HashMap<String, String> oldQuoteCode = new HashMap<String, String>();
        List<BillReuseField> reUseFields = defineImpl.getReUseFields();
        for (BillReuseField reUseField : reUseFields) {
            if (!quoteCodesList.contains(reUseField.getTableCode() + ":" + reUseField.getFieldCode())) continue;
            List<Map<String, Object>> rows = tableData.get(reUseField.getTableCode());
            for (int i = 0; i < rows.size(); ++i) {
                Map<String, Object> dataRow = rows.get(i);
                String string = (String)dataRow.get(reUseField.getFieldCode());
                if (!StringUtils.hasText(string)) continue;
                oldQuoteCode.put(string, i + ":" + reUseField.getTableCode() + ":" + reUseField.getFieldCode());
            }
        }
        return oldQuoteCode;
    }

    private static void removeQuotecode(BillModel model, Map<String, Set<String>> quoteCodes) {
        for (String s : quoteCodes.keySet()) {
            Set<String> strings = quoteCodes.get(s);
            DataTable table = model.getTable(s);
            for (String string : strings) {
                for (int i = 0; i < table.getRows().size(); ++i) {
                    ((DataRow)table.getRows().get(i)).setValue(string, null);
                }
            }
        }
    }

    private static void handleParams(BillModel model, Map<String, Map<String, Object>> params, Map<String, List<? extends DataField>> mapOrg, Map<String, List<Map<String, Object>>> tableData, DataTableNodeContainer<? extends DataTable> tables, Map<String, Set<String>> tempDisplayedFieldList) {
        for (int j = 0; j < params.size(); ++j) {
            String mappingValue;
            Set<String> fields;
            DataRow dataRow;
            int i;
            Map<String, Object> paramValueMap = params.get(String.valueOf(j));
            String paramTableName = (String)paramValueMap.get("tableCode");
            String paramFieldCode = (String)paramValueMap.get("fieldCode");
            DataTable newTable = model.getTable(paramTableName);
            if (mapOrg.containsKey(paramTableName)) {
                List<? extends DataField> orgFields = mapOrg.get(paramTableName);
                List<Map<String, Object>> list = tableData.get(paramTableName);
                String masterTable = ((DataTable)tables.get(0)).getName();
                for (i = 0; i < list.size(); ++i) {
                    Map<String, Object> row = list.get(i);
                    if (!masterTable.equals(paramTableName)) {
                        newTable.appendRow();
                    }
                    if (orgFields == null) continue;
                    dataRow = (DataRow)newTable.getRows().get(i);
                    for (DataField dataField : orgFields) {
                        String orgFieldName = dataField.getName();
                        if (!row.containsKey(orgFieldName)) continue;
                        dataRow.setValue(orgFieldName, row.get(orgFieldName));
                    }
                }
                mapOrg.remove(paramTableName);
            }
            if (!tempDisplayedFieldList.containsKey(paramTableName) || (fields = tempDisplayedFieldList.get(paramTableName)) == null || !fields.contains(paramFieldCode) || (mappingValue = (String)paramValueMap.get("mapping")) != null && mappingValue.startsWith(MD_ORG)) continue;
            List<Map<String, Object>> rowList = tableData.get(paramTableName);
            for (i = 0; i < rowList.size(); ++i) {
                Object fieldValue = rowList.get(i).get(paramFieldCode);
                if (fieldValue == null) continue;
                dataRow = (DataRow)newTable.getRows().get(i);
                DataField dataField = (DataField)newTable.getFields().find(paramFieldCode);
                if (dataField == null) {
                    LOGGER.error("\u5b57\u6bb5{}\u5728\u8868{}\u4e2d\u4e0d\u5b58\u5728", (Object)paramFieldCode, (Object)paramTableName);
                    continue;
                }
                Integer n = (Integer)paramValueMap.get("mappingType");
                if (n != null && (1 == n || 2 == n || 4 == n)) {
                    List checkFieldResults = dataRow.checkFieldValue(paramFieldCode, fieldValue);
                    if (CollectionUtils.isEmpty(checkFieldResults)) {
                        dataRow.setValue(paramFieldCode, fieldValue);
                        continue;
                    }
                    LOGGER.error("\u5b57\u6bb5{}\u5728\u8868{}\u4e2d\u8d4b\u503c\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", paramFieldCode, paramTableName, ((CheckFieldResult)checkFieldResults.get(0)).getErrorData().getTitle());
                    continue;
                }
                dataRow.setValueWithCheck(paramFieldCode, fieldValue);
            }
        }
    }

    private Map<String, List<Map<String, Object>>> getCopiedDataFromOldBill(BillModel model, Map<String, Map<String, Object>> params) {
        HashMap<String, List<Map<String, Object>>> copiedData = new HashMap<String, List<Map<String, Object>>>();
        if (model.getData() == null || model.getData().getTables() == null) {
            return copiedData;
        }
        int j = model.getData().getTables().size();
        for (int i = 0; i < j; ++i) {
            DataTable table = (DataTable)model.getData().getTables().get(i);
            if (notCopyTables.contains(table.getDefine().getName()) || table.getRows() == null) continue;
            this.processTable(copiedData, table, params);
        }
        return copiedData;
    }

    private void processTable(Map<String, List<Map<String, Object>>> copiedData, DataTable table, Map<String, Map<String, Object>> params) {
        ArrayList<HashMap<String, Object>> tables = new ArrayList<HashMap<String, Object>>();
        copiedData.put(table.getDefine().getName(), tables);
        NamedContainer fields = table.getFields();
        int j = table.getRows().size();
        for (int i = 0; i < j; ++i) {
            DataRow dataRow = (DataRow)table.getRows().get(i);
            if (!(dataRow instanceof DataRowImpl)) {
                LOGGER.error("\u5355\u636e\u590d\u5236\uff0c\u6570\u636e\u884c\u7c7b\u578b\u4e0d\u5339\u914d\uff1a{}", (Object)dataRow.getClass());
                continue;
            }
            DataRowImpl dataRowImpl = (DataRowImpl)dataRow;
            HashMap<String, Object> datas = new HashMap<String, Object>();
            tables.add(datas);
            this.processDataRow(datas, dataRowImpl, (NamedContainer<? extends DataField>)fields, params, table.getDefine().getName());
        }
    }

    private void processDataRow(Map<String, Object> datas, DataRowImpl dataRow, NamedContainer<? extends DataField> fields, Map<String, Map<String, Object>> params, String tableName) {
        if (params == null || params.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Map<String, Object>> param : params.entrySet()) {
            Map<String, Object> field = param.getValue();
            String tableCode = (String)field.get("tableCode");
            if (!tableCode.equals(tableName)) continue;
            String fieldCode = (String)field.get("fieldCode");
            Object mappingType = field.get("mappingType");
            if (mappingType == null) {
                datas.put(fieldCode, dataRow.getValue(fieldCode));
                continue;
            }
            DataField dataField = (DataField)fields.find(fieldCode);
            if (dataField == null) {
                LOGGER.error("\u5355\u636e\u590d\u5236\uff0c\u672a\u627e\u5230\u5b57\u6bb5\uff1a{}", (Object)fieldCode);
                continue;
            }
            DataFieldDefineImpl define = (DataFieldDefineImpl)dataField.getDefine();
            if (define == null) {
                LOGGER.error("\u5355\u636e\u590d\u5236\uff0c\u672a\u627e\u5230\u5b57\u6bb5\u5b9a\u4e49\uff1a{}", (Object)fieldCode);
                continue;
            }
            if (define.isBillPenetrate()) {
                LOGGER.info("\u5355\u636e\u590d\u5236\uff0c\u8054\u67e5\u5b57\u6bb5\u8df3\u8fc7\uff1a{}", (Object)fieldCode);
                continue;
            }
            if (!define.isMultiChoice()) {
                datas.put(fieldCode, dataRow.getValue(fieldCode));
                continue;
            }
            datas.put(fieldCode, dataRow.getMultiValue(fieldCode));
        }
    }

    private boolean rowIsNull(List<BillReuseField> reUseFields, DataRow row, String name) {
        boolean[] isHave = new boolean[]{true};
        reUseFields.forEach(field -> {
            try {
                if (!name.equals(field.getTableCode())) {
                    return;
                }
                if (row.getValue(field.getFieldCode()) != null) {
                    isHave[0] = false;
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        return isHave[0];
    }
}

