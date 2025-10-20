/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelDefineDeclare
 *  com.jiuqi.va.biz.intf.model.Declare
 *  com.jiuqi.va.biz.intf.model.DeclareHost
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineDeclare;
import com.jiuqi.va.biz.intf.model.Declare;
import com.jiuqi.va.biz.intf.model.DeclareHost;

public class BillDeclare<T extends DeclareHost<? super BillDefineImpl>>
extends ModelDefineDeclare<BillDefineImpl, T>
implements BillDefine,
Declare<BillDefineImpl, T> {
    public BillDeclare(T host) {
        super(host, BillDefineImpl.class);
    }

    public DataDeclare<BillDeclare<T>> declareData() {
        return new DataDeclare((DeclareHost)this);
    }

    public DataDefineImpl getData() {
        return (DataDefineImpl)this.getPlugins().get(DataDefineImpl.class);
    }
}

