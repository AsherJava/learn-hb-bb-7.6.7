/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataDefineMerge;
import com.jiuqi.va.biz.intf.model.PluginDefine;

public class PluginDefineMerge {
    public static void merge(PluginDefine target, PluginDefine base) {
        if (target.getType().equals("data")) {
            DataDefineMerge.merge((DataDefineImpl)target, (DataDefineImpl)base);
        }
    }
}

