/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.util.bean;

public class SingleAttachmentFailFile {
    private String fileKey;
    private String fileName;
    private String errorReason;
    private String errorMsg;

    public SingleAttachmentFailFile() {
    }

    public SingleAttachmentFailFile(String fileKey, String fileName, String errorMsg) {
        this.fileKey = fileKey;
        this.fileName = fileName;
        this.errorMsg = errorMsg;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorReason() {
        return this.errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}

