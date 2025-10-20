/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.action.ActionBase
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.Map;

public abstract class BillActionBase
extends ActionBase {
    public void execute(BillModel model, Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    public Object executeReturn(BillModel model, Map<String, Object> params) {
        this.execute(model, params);
        return null;
    }

    public final void execute(Model model, Map<String, Object> params) {
        this.execute((BillModel)model, params);
    }

    public final Object executeReturn(Model model, Map<String, Object> params) {
        return this.executeReturn((BillModel)model, params);
    }

    public Class<? extends BillModel> getDependModel() {
        return BillModel.class;
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_BROWER;
    }
}

