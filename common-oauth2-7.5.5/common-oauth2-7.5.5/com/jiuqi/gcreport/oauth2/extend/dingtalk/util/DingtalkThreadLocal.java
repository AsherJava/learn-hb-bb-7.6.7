/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.util;

import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.pojo.DingtalkConfigCombine;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;

public class DingtalkThreadLocal {
    private static ThreadLocal<DingtalkConfigCombine> ncThreadLocal = new ThreadLocal();

    public static void put(DingtalkConfigCombine nc) {
        ncThreadLocal.set(nc);
    }

    public static void clear() {
        ncThreadLocal.remove();
    }

    public static DingtalkConfigCombine get() {
        return ncThreadLocal.get();
    }

    public static DingtalkCertifyExtInfo getExtInfo() {
        DingtalkConfigCombine combine = ncThreadLocal.get();
        if (combine != null) {
            return combine.getExtInfo();
        }
        return null;
    }

    public static NvwaCertify getCertify() {
        DingtalkConfigCombine combine = ncThreadLocal.get();
        if (combine != null) {
            return combine.getNvwaCertify();
        }
        return null;
    }
}

