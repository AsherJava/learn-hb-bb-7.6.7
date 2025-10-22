/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import java.util.Map;

public class FilesUploadReturnInfo {
    private Map<String, FileUploadReturnInfo> fileUploadReturnInfoMap;
    private boolean allIsSuccess = true;

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
}

