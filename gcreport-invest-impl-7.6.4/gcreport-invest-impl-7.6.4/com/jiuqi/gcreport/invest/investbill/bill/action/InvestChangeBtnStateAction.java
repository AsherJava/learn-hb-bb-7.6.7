/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.value.ListContainer
 */
package com.jiuqi.gcreport.invest.investbill.bill.action;

import com.jiuqi.gcreport.invest.investbill.bill.impl.InvestBillModelImpl;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.ListContainer;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InvestChangeBtnStateAction
extends BillActionBase {
    public String getName() {
        return "bill-invest-change-state";
    }

    public String getTitle() {
        return "\u6295\u8d44\u53d8\u52a8(\u4f20\u9012\u6309\u94ae\u72b6\u6001)";
    }

    public String getIcon() {
        return null;
    }

    public void execute(BillModel model, Map<String, Object> params) {
        DataRow dataRow = this.getMaster(model);
        if (null == dataRow) {
            return;
        }
        dataRow.setValue("INVESTBILLSTATE", (Object)"CHANGE");
    }

    private DataRow getMaster(BillModel model) {
        ListContainer rows = model.getData().getMasterTable().getRows();
        return rows.size() == 0 ? null : (DataRow)rows.get(0);
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }

    public String getActionPriority() {
        return "000";
    }

    public Class<? extends BillModel> getDependModel() {
        return InvestBillModelImpl.class;
    }
}

