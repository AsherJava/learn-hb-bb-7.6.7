/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import java.util.Map;

public class ImpFileParams {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    @Deprecated
    private String attachmentPath;
    private FileFinder fileFinder;
    private Map<String, String> oldAndNewGroupKeyMapping;
    private boolean uploadByFileKey = false;
    private ParamsMapping paramsMapping;
    private FilterDim filterDims;
    private CompletionDim completionDims;

    public ImpFileParams() {
    }

    public ImpFileParams(String dataSchemeKey, String taskKey, String formSchemeKey, String attachmentPath, Map<String, String> oldAndNewGroupKeyMapping, boolean uploadByFileKey) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.attachmentPath = attachmentPath;
        this.oldAndNewGroupKeyMapping = oldAndNewGroupKeyMapping;
        this.uploadByFileKey = uploadByFileKey;
    }

    public ImpFileParams(String dataSchemeKey, String taskKey, String formSchemeKey, String attachmentPath, Map<String, String> oldAndNewGroupKeyMapping, boolean uploadByFileKey, ParamsMapping paramsMapping) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.attachmentPath = attachmentPath;
        this.oldAndNewGroupKeyMapping = oldAndNewGroupKeyMapping;
        this.uploadByFileKey = uploadByFileKey;
        this.paramsMapping = paramsMapping;
    }

    public ImpFileParams(String dataSchemeKey, String taskKey, String formSchemeKey, FileFinder fileFinder, Map<String, String> oldAndNewGroupKeyMapping, boolean uploadByFileKey, FilterDim filterDims, CompletionDim completionDims) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fileFinder = fileFinder;
        this.oldAndNewGroupKeyMapping = oldAndNewGroupKeyMapping;
        this.uploadByFileKey = uploadByFileKey;
        this.filterDims = filterDims;
        this.completionDims = completionDims;
    }

    public ImpFileParams(String dataSchemeKey, String taskKey, String formSchemeKey, FileFinder fileFinder, Map<String, String> oldAndNewGroupKeyMapping, boolean uploadByFileKey, ParamsMapping paramsMapping, FilterDim filterDims, CompletionDim completionDims) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fileFinder = fileFinder;
        this.oldAndNewGroupKeyMapping = oldAndNewGroupKeyMapping;
        this.uploadByFileKey = uploadByFileKey;
        this.paramsMapping = paramsMapping;
        this.filterDims = filterDims;
        this.completionDims = completionDims;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Deprecated
    public String getAttachmentPath() {
        return this.attachmentPath;
    }

    public FileFinder getFileFinder() {
        return this.fileFinder;
    }

    public void setFileFinder(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
    }

    public Map<String, String> getOldAndNewGroupKeyMapping() {
        return this.oldAndNewGroupKeyMapping;
    }

    public boolean isUploadByFileKey() {
        return this.uploadByFileKey;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public FilterDim getFilterDims() {
        return this.filterDims;
    }

    public CompletionDim getCompletionDims() {
        return this.completionDims;
    }
}

