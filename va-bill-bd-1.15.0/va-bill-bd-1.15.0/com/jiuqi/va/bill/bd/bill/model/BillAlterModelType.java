/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillDefineImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 */
package com.jiuqi.va.bill.bd.bill.model;

import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillAlterModelType
extends FixedBillBase {
    public Class<? extends BillModelImpl> getModelClass() {
        return BillAlterModel.class;
    }

    public String getName() {
        return "VA_BILLBD_ALTER";
    }

    public String getTitle() {
        return "\u5355\u636e-\u901a\u7528\u53d8\u66f4\u6a21\u578b";
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
    }

    protected void ensureRulerList(BillDefineImpl define, List<RulerItem> rulerList) {
        super.ensureRulerList(define, rulerList);
    }
}

