/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fileupload.FileUploadReturnInfo
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import java.util.Map;

public class FilesCheckUploadResult {
    private Map<String, FileUploadReturnInfo> fileUploadReturnInfoMap;
    private boolean allIsSuccess = true;
    private String groupKey;

    public Map<String, FileUploadReturnInfo> getFileUploadReturnInfoMap() {
        return this.fileUploadReturnInfoMap;
    }

    public void setFileUploadReturnInfoMap(Map<String, FileUploadReturnInfo> fileUploadReturnInfoMap) {
        this.fileUploadReturnInfoMap = fileUploadReturnInfoMap;
    }

    public boolean isAllIsSuccess() {
        return this.allIsSuccess;
    }

    public void setAllIsSuccess(boolean allIsSuccess) {
        this.allIsSuccess = allIsSuccess;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

