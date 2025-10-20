/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ClearItemAction
extends BillActionBase {
    public String getName() {
        return "bill-item-clear";
    }

    public String getTitle() {
        return "\u6e05\u7a7a\u5b50\u8868";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_qingkongzibiao";
    }

    public String getActionPriority() {
        return "013";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        LogUtil.add((String)"\u5355\u636e", (String)"\u6e05\u7a7a\u5b50\u8868", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), (String)String.valueOf(params));
        DataTable itemTable = (DataTable)model.getData().getTables().get((String)params.get("tableName"));
        itemTable.deleteRow(0, itemTable.getRows().size());
    }
}

