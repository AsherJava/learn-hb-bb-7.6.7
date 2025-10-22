/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.snapshot.input.CreateSnapshotInfo;
import java.util.List;

public class BatchCreateSnapshotContext {
    private String taskKey;
    private List<CreateSnapshotInfo> createSnapshotInfos = null;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<CreateSnapshotInfo> getCreateSnapshotInfos() {
        return this.createSnapshotInfos;
    }

    public void setCreateSnapshotInfos(List<CreateSnapshotInfo> createSnapshotInfos) {
        this.createSnapshotInfos = createSnapshotInfos;
    }
}

