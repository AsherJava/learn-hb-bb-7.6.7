/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.task;

import java.util.ArrayList;
import java.util.List;

public interface MergeTaskOrderProvider {
    public List<String> getTaskTypesOrderByBBLX(String var1);

    default public List<String> orderTasks(List<String> taskTypes, String bblx) {
        ArrayList<String> result = new ArrayList<String>();
        List<String> orderdTaskTypes = this.getTaskTypesOrderByBBLX(bblx);
        for (String taskCode : orderdTaskTypes) {
            if (!taskTypes.contains(taskCode)) continue;
            result.add(taskCode);
        }
        return result;
    }
}

