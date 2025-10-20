/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.ruler.impl.FieldRulerPluginType
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.ruler.impl.FieldRulerPluginType;
import org.springframework.stereotype.Component;

@Component
public class FieldRulerPluginTypeImpl
extends FieldRulerPluginType {
    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }

    public boolean canAdd() {
        return false;
    }
}

