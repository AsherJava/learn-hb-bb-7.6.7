/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.param;

import com.jiuqi.nr.attachment.param.FileSumUploadInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSumUploadParam {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination targetDim;
    private Map<String, List<FileSumUploadInfo>> fileSumUploadInfos = new HashMap<String, List<FileSumUploadInfo>>();
    private String sameNameProcessMode;
    private String coverUploadProcessMode;

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

    public Map<String, List<FileSumUploadInfo>> getFileSumUploadInfos() {
        return this.fileSumUploadInfos;
    }

    public void setFileSumUploadInfos(Map<String, List<FileSumUploadInfo>> fileSumUploadInfos) {
        this.fileSumUploadInfos = fileSumUploadInfos;
    }

    public String getSameNameProcessMode() {
        return this.sameNameProcessMode;
    }

    public void setSameNameProcessMode(String sameNameProcessMode) {
        this.sameNameProcessMode = sameNameProcessMode;
    }

    public String getCoverUploadProcessMode() {
        return this.coverUploadProcessMode;
    }

    public void setCoverUploadProcessMode(String coverUploadProcessMode) {
        this.coverUploadProcessMode = coverUploadProcessMode;
    }
}

