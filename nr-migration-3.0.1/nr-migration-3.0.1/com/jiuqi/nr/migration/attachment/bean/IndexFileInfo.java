/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.attachment.bean;

import com.jiuqi.nr.migration.attachment.bean.AttachmentInfo;
import java.util.List;

public class IndexFileInfo {
    private String packageId;
    private int count;
    private List<AttachmentInfo> data;

    public IndexFileInfo() {
    }

    public IndexFileInfo(String packageId, int count, List<AttachmentInfo> data) {
        this.packageId = packageId;
        this.count = count;
        this.data = data;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AttachmentInfo> getData() {
        return this.data;
    }

    public void setData(List<AttachmentInfo> data) {
        this.data = data;
    }
}

