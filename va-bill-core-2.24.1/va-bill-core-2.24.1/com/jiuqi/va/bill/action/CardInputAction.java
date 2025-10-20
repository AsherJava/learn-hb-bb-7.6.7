/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataTable
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataTable;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CardInputAction
extends BillActionBase {
    public String getName() {
        return "bill-card-input";
    }

    public String getTitle() {
        return "\u5361\u7247\u5f55\u5165";
    }

    public String getActionPriority() {
        return "018";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_kapianluru";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        String tableName = (String)params.get("tableName");
        DataTable dataTable = (DataTable)model.getData().getTables().find(tableName);
        if (dataTable.getRows().size() == 0) {
            dataTable.appendRow();
        }
    }
}

