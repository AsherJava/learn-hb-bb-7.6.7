/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 */
package com.jiuqi.va.bill.extention;

import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.ruler.control.VaAbolishActionRuler;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BillModelType
extends FixedBillBase {
    @Autowired(required=false)
    private VaAbolishActionRuler vaAbolishActionRuler;

    @Override
    public Class<? extends BillModelImpl> getModelClass() {
        return BillModelImpl.class;
    }

    public String getName() {
        return "BillModelType";
    }

    public String getTitle() {
        return "\u57fa\u7c7b-VA\u5355\u636e\u6a21\u578b";
    }

    @Override
    protected void declareData(DataDeclare<?> dataDeclare) {
    }

    @Override
    protected void ensureRulerList(BillDefineImpl define, List<RulerItem> rulerList) {
        super.ensureRulerList(define, rulerList);
        if (this.vaAbolishActionRuler != null) {
            rulerList.add(this.vaAbolishActionRuler);
        }
    }
}

