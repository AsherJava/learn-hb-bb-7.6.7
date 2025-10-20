/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 */
package com.jiuqi.va.bill.bd.bill.model;

import com.jiuqi.va.bill.bd.bill.model.BasedataApplyBillModel;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasedataApplyBillModelType
extends FixedBillBase {
    public Class<? extends BasedataApplyBillModel> getModelClass() {
        return BasedataApplyBillModel.class;
    }

    public String getName() {
        return "VA_BILLBD_APPLY";
    }

    public String getTitle() {
        return "\u5355\u636e-\u57fa\u7840\u6570\u636e\u7533\u8bf7\u6a21\u578b";
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
    }

    protected void ensureRulerList(BillDefineImpl define, List<RulerItem> rulerList) {
        super.ensureRulerList(define, rulerList);
    }
}

