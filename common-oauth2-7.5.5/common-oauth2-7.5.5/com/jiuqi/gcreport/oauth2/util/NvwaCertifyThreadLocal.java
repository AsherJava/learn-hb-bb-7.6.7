/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 */
package com.jiuqi.gcreport.oauth2.util;

import com.jiuqi.nvwa.certification.bean.NvwaCertify;

public class NvwaCertifyThreadLocal {
    private static ThreadLocal<NvwaCertify> ncThreadLocal = new ThreadLocal();

    public static void put(NvwaCertify nc) {
        ncThreadLocal.set(nc);
    }

    public static void clear() {
        ncThreadLocal.remove();
    }

    public static NvwaCertify get() {
        return ncThreadLocal.get();
    }
}

