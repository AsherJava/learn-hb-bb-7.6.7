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
package com.jiuqi.gcreport.lease.leasebill.bill.action;

import com.jiuqi.gcreport.lease.leasebill.bill.impl.AbstractLeaseBillModel;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.ListContainer;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LeaseAutoAddSubBillAction
extends BillActionBase {
    public String getName() {
        return "bill-lease-autoAddSubBill";
    }

    public String getTitle() {
        return "\u751f\u6210\u5b50\u8868";
    }

    public String getIcon() {
        return null;
    }

    public void execute(BillModel model, Map<String, Object> params) {
        DataRow dataRow = this.getMaster(model);
        ((AbstractLeaseBillModel)model).autoAddSubBill(dataRow);
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
        return AbstractLeaseBillModel.class;
    }
}

