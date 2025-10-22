/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.param;

import com.jiuqi.nr.data.engine.gather.GatherDataTable;
import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import java.util.List;

public class FileSumContext {
    private String taskKey;
    private String formSchemeKey;
    private GatherDataTable gatherDataTable;
    private List<FileSumInfo> fileSumInfos;

    public FileSumContext() {
    }

    public FileSumContext(String taskKey, List<FileSumInfo> fileSumInfos, String formSchemeKey) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fileSumInfos = fileSumInfos;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<FileSumInfo> getFileSumInfos() {
        return this.fileSumInfos;
    }

    public void setFileSumInfos(List<FileSumInfo> fileSumInfos) {
        this.fileSumInfos = fileSumInfos;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public GatherDataTable getGatherDataTable() {
        return this.gatherDataTable;
    }

    public void setGatherDataTable(GatherDataTable gatherDataTable) {
        this.gatherDataTable = gatherDataTable;
    }
}

