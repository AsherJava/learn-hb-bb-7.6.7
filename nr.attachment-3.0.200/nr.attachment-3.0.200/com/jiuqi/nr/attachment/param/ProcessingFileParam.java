/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.param.FileSumInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.param;

import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class ProcessingFileParam {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination targetDim;
    private List<FileSumInfo> fileSumInfos;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getTargetDim() {
        return this.targetDim;
    }

    public void setTargetDim(DimensionCombination targetDim) {
        this.targetDim = targetDim;
    }

    public List<FileSumInfo> getFileSumInfos() {
        return this.fileSumInfos;
    }

    public void setFileSumInfos(List<FileSumInfo> fileSumInfos) {
        this.fileSumInfos = fileSumInfos;
    }
}

