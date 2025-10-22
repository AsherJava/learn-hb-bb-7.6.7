/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.paramInfo.FileParamInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FileUploadParams
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appName;
    private List<String> sceneList;
    private JtableContext context;
    private Map<String, FileParamInfo> fileParamInfoMap;
    private String fieldKey;
    private String groupKey;
    private boolean covered = false;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<String> getSceneList() {
        return this.sceneList;
    }

    public void setSceneList(List<String> sceneList) {
        this.sceneList = sceneList;
    }

    public Map<String, FileParamInfo> getFileParamInfoMap() {
        return this.fileParamInfoMap;
    }

    public void setFileParamInfoMap(Map<String, FileParamInfo> fileParamInfoMap) {
        this.fileParamInfoMap = fileParamInfoMap;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }
}

