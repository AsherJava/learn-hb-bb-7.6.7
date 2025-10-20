/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.temp.dto.TaskLog;

public interface GcOnekeyProcessService {
    public void setTaskProcess(String var1, TaskLog var2);

    public void setTaskProcessWithCode(String var1, TaskLog var2, String var3);

    public TaskLog getTaskProcess(String var1);

    public TaskLog getTaskProcessWithCode(String var1, String var2);

    public void removeTaskLog(String var1);
}

