/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bill.utils.VerifyUtils
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.value.ListContainer
 */
package com.jiuqi.gcreport.asset.assetbill.bill.action;

import com.jiuqi.gcreport.asset.assetbill.bill.impl.GcAbstractAssetBillModelImpl;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.ListContainer;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DisposalBtnStateAction
extends BillActionBase {
    public String getName() {
        return "bill-disposal-state";
    }

    public String getTitle() {
        return "\u5904\u7f6e(\u4f20\u9012\u6309\u94ae\u72b6\u6001)";
    }

    public String getIcon() {
        return null;
    }

    public void execute(BillModel model, Map<String, Object> params) {
        DataRow dataRow = this.getMaster(model);
        dataRow.setValue("BUTTONACTION", (Object)"DISPOSAL");
        VerifyUtils.verifyBill((BillModel)model, (int)2);
        BillState state = (BillState)model.getMaster().getValue("BILLSTATE", BillState.class);
        if (state != null && (state.getValue() & (BillState.DELETED.getValue() | BillState.CHECKED.getValue())) != 0) {
            throw new BillException("\u5f53\u524d\u5355\u636e\u72b6\u6001\u4e0d\u5141\u8bb8\u7f16\u8f91\uff1a" + state.getTitle());
        }
        model.getData().edit();
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
        return GcAbstractAssetBillModelImpl.class;
    }
}

