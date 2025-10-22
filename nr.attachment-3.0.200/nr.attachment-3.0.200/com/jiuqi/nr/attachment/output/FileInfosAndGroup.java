/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.output;

import com.jiuqi.nr.attachment.message.FileInfo;
import java.util.List;

public class FileInfosAndGroup {
    private String groupKey;
    private List<FileInfo> fileInfos;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<FileInfo> getFileInfos() {
        return this.fileInfos;
    }

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }
}

