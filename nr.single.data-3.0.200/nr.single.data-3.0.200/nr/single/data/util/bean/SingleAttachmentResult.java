/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.util.bean;

import java.util.ArrayList;
import java.util.List;
import nr.single.data.util.bean.SingleAttachmentFailFile;

public class SingleAttachmentResult {
    private boolean success;
    private String groupKey;
    private String message;
    private List<SingleAttachmentFailFile> failedFileList;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<SingleAttachmentFailFile> getFailedFileList() {
        if (this.failedFileList == null) {
            this.failedFileList = new ArrayList<SingleAttachmentFailFile>();
        }
        return this.failedFileList;
    }

    public void setFailedFileList(List<SingleAttachmentFailFile> failedFileList) {
        this.failedFileList = failedFileList;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

