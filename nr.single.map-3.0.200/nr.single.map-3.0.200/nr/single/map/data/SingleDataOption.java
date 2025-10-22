/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data;

public class SingleDataOption {
    private boolean uploadEntityData;
    private boolean uploadReportData;
    private boolean uploadCheckData;
    private boolean uploadEntityCheck;
    private boolean uploadQueryCheck;

    public SingleDataOption() {
        this.SelectAll();
    }

    public void SelectAll() {
        this.uploadEntityData = true;
        this.uploadReportData = true;
        this.uploadCheckData = true;
        this.uploadEntityCheck = true;
        this.uploadQueryCheck = true;
    }

    public void NotSelectAll() {
        this.uploadEntityData = false;
        this.uploadReportData = false;
        this.uploadCheckData = false;
        this.uploadEntityCheck = false;
        this.uploadQueryCheck = false;
    }

    public boolean isUploadEntityData() {
        return this.uploadEntityData;
    }

    public void setUploadEntityData(boolean uploadEntityData) {
        this.uploadEntityData = uploadEntityData;
    }

    public boolean isUploadReportData() {
        return this.uploadReportData;
    }

    public void setUploadReportData(boolean uploadReportData) {
        this.uploadReportData = uploadReportData;
    }

    public boolean isUploadCheckData() {
        return this.uploadCheckData;
    }

    public void setUploadCheckData(boolean uploadCheckData) {
        this.uploadCheckData = uploadCheckData;
    }

    public boolean isUploadEntityCheck() {
        return this.uploadEntityCheck;
    }

    public void setUploadEntityCheck(boolean uploadEntityCheck) {
        this.uploadEntityCheck = uploadEntityCheck;
    }

    public boolean isUploadQueryCheck() {
        return this.uploadQueryCheck;
    }

    public void setUploadQueryCheck(boolean uploadQueryCheck) {
        this.uploadQueryCheck = uploadQueryCheck;
    }
}

