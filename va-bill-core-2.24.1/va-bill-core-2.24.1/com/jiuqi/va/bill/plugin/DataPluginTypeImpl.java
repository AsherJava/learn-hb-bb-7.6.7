/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataPluginType
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataPluginType;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import org.springframework.stereotype.Component;

@Component
public class DataPluginTypeImpl
extends DataPluginType {
    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }
}

