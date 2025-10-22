/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.designer.planpublish.job;

import com.jiuqi.np.definition.common.UUIDUtils;

public class JobCommon {
    public static String getJobGroupId() {
        return "TASK_PLAN_PUBLISH";
    }

    public static String getMessageJobGroupId() {
        return "TASK_PLAN_PUBLISH_MESSAGE";
    }

    public static String getJobId() {
        return UUIDUtils.getKey();
    }

    public static String getMessageJobId() {
        return UUIDUtils.getKey();
    }
}

