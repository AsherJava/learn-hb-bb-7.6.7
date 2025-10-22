/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.asynctask.entity;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchAsyncTaskQueryInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<AsyncTaskQueryInfo> asyncTaskInfos = new ArrayList<AsyncTaskQueryInfo>();

    public List<AsyncTaskQueryInfo> getAsyncTaskInfos() {
        return this.asyncTaskInfos;
    }
}

