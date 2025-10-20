/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import java.util.List;

public interface IBaseFactory {
    public String getJobCategoryId();

    public String getJobCategoryTitle();

    default public String[] getTags() {
        return new String[0];
    }

    public List<ILogGenerator.LogItem> getLastLogsAfter(String var1, long var2, boolean var4) throws Exception;
}

