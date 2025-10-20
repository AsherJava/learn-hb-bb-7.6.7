/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelDefineMerge
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineMerge;

public class BillDefineMerge {
    public static void merge(BillDefineImpl target, BillDefineImpl base) {
        ModelDefineMerge.merge((ModelDefineImpl)target, (ModelDefineImpl)base);
    }
}

