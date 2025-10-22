/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;

public class OnekeyMergeFactory {
    public static GcCenterTask getTaskByClazz(Class<? extends GcCenterTask> clazz) {
        return (GcCenterTask)SpringContextUtils.getBean(clazz);
    }
}

