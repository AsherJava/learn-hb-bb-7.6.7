/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerPluginType
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.ruler.impl.RulerPluginType;
import org.springframework.stereotype.Component;

@Component
public class RulerPluginTypeImp
extends RulerPluginType {
    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }
}

