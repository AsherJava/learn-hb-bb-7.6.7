/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.plugin.event.VaAttachmentDataPostEvent;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AttachmentEditAction
extends BillActionBase {
    public String getName() {
        return "bill-attachment-edit";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u7f16\u8f91";
    }

    public String getActionPriority() {
        return "029";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        Map<String, Set<String>> quoteCodeFields = VaAttachmentDataPostEvent.getQuoteCodeFields(model);
        if (quoteCodeFields == null || quoteCodeFields.isEmpty()) {
            return;
        }
        model.getData().edit();
        DataTableNodeContainer tables = model.getData().getTables();
        for (Map.Entry<String, Set<String>> entry : quoteCodeFields.entrySet()) {
            String tableName = entry.getKey();
            Set<String> value = entry.getValue();
            if (value == null || value.isEmpty()) continue;
            DataTable dataTable = (DataTable)tables.get(tableName);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM-");
            String dateString = formatter.format(new Date());
            dataTable.getRows().forEach((index, row) -> value.forEach(fieldName -> {
                String string = row.getString(fieldName);
                if (!StringUtils.hasText(string)) {
                    row.setValue(fieldName, (Object)(dateString + UUID.randomUUID()));
                }
            }));
        }
        model.getData().save();
    }
}

