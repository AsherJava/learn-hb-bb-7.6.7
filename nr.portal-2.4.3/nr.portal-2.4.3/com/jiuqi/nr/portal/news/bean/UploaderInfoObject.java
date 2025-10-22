/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class UploaderInfoObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value="FileGuid")
    private String fileGuid;
    @JsonProperty(value="FileName")
    private String fileName;
    @JsonProperty(value="FileUrl")
    private String fileUrl;
    @JsonProperty(value="ModifiedTime")
    private String modifyTime;

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getFileGuid() {
        return this.fileGuid;
    }

    public void setFileGuid(String fileGuid) {
        this.fileGuid = fileGuid;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

