/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

public class FileParamInfo {
    private String fileKey;
    private boolean covered;
    private String groupKey;
    private String fileSecret;
    private String dataLinkKey;
    private boolean imgFieldType;
    private int uploadType;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFileSecret() {
        return this.fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public boolean isImgFieldType() {
        return this.imgFieldType;
    }

    public void setImgFieldType(boolean imgFieldType) {
        this.imgFieldType = imgFieldType;
    }

    public int getUploadType() {
        return this.uploadType;
    }

    public void setUploadType(int uploadType) {
        this.uploadType = uploadType;
    }
}

