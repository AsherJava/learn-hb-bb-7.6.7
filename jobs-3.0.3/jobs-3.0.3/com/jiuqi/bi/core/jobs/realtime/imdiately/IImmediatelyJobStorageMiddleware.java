/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IImmediatelyJobStorageMiddleware {
    public void patch(ImmediatelyJobInfo var1);

    public void put(ImmediatelyJobInfo var1) throws JobExecutionException;

    public ImmediatelyJobInfo get(String var1);

    default public Map<String, ImmediatelyJobInfo> gets(List<String> instanceIds) {
        HashMap<String, ImmediatelyJobInfo> result = new HashMap<String, ImmediatelyJobInfo>();
        for (String key : instanceIds) {
            result.put(key, this.get(key));
        }
        return result;
    }

    public void remove(ImmediatelyJobInfo var1);

    public int getMaxConcurrentJobCount(String var1);

    public int getRunningJobCount(String var1);

    public Map<String, Integer> collectNodeRunningJobCount();
}

