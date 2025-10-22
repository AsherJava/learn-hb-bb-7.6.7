/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FileUploadParamInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Long> fileNameSizeMap;
    private List<String> sceneList;
    private String appName;

    public Map<String, Long> getFileNameSizeMap() {
        return this.fileNameSizeMap;
    }

    public void setFileNameSizeMap(Map<String, Long> fileNameSizeMap) {
        this.fileNameSizeMap = fileNameSizeMap;
    }

    public List<String> getSceneList() {
        return this.sceneList;
    }

    public void setSceneList(List<String> sceneList) {
        this.sceneList = sceneList;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}

