/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.common.base.util.UUIDUtils;

public class Instance {
    private String id = UUIDUtils.newUUIDStr();

    private Instance() {
    }

    public static String getInstanceId() {
        return InnerInstance.INSTANCE.id;
    }

    private static class InnerInstance {
        private static final Instance INSTANCE = new Instance();

        private InnerInstance() {
        }
    }
}

