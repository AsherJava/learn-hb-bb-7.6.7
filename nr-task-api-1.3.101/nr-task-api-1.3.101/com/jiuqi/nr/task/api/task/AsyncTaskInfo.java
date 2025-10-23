/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.task;

import com.jiuqi.nr.task.api.task.DownloadInfo;
import java.io.Serializable;

public class AsyncTaskInfo
implements Serializable {
    private String id;
    private String category;
    private String moduleName;
    private String detailInfo;
    private DownloadInfo downloadInfo;
    private boolean refresh;

    public AsyncTaskInfo() {
    }

    public AsyncTaskInfo(String id, String category, String moduleName) {
        this.id = id;
        this.category = category;
        this.moduleName = moduleName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public DownloadInfo getDownloadInfo() {
        return this.downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public String getDetailInfo() {
        return this.detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public boolean isRefresh() {
        return this.refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }
}

