/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 */
package nr.single.client.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.dataentry.bean.UploadParam;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleUploadParam
extends UploadParam {
    private static final long serialVersionUID = 1L;
    private String singleTaskCode;
    private String singleFlagCode;
    private String singleTaskVersion;
    private int fileIndex;
    private int fileCount;
    private String fileLocation;
    private String asyncTask;

    public String getSingleTaskCode() {
        return this.singleTaskCode;
    }

    public void setSingleTaskCode(String singleTaskCode) {
        this.singleTaskCode = singleTaskCode;
    }

    public String getSingleFlagCode() {
        return this.singleFlagCode;
    }

    public void setSingleFlagCode(String singleFlagCode) {
        this.singleFlagCode = singleFlagCode;
    }

    public String getSingleTaskVersion() {
        return this.singleTaskVersion;
    }

    public void setSingleTaskVersion(String singleTaskVersion) {
        this.singleTaskVersion = singleTaskVersion;
    }

    public int getFileIndex() {
        return this.fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public int getFileCount() {
        return this.fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getAsyncTask() {
        return this.asyncTask;
    }

    public void setAsyncTask(String asyncTask) {
        this.asyncTask = asyncTask;
    }
}

